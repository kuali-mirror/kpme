/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.timesheet.web;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;

public class TimesheetSubmitAction extends KPMEAction {

	private static final Logger LOG = Logger.getLogger(TimesheetSubmitAction.class);
    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
    	
    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
    	String documentId = tsaf.getDocumentId();
    	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
        if (!HrServiceLocator.getHRPermissionService().canEditCalendarDocument(principalId, timesheetDocument)
        		&& !StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.REFRESH)) {
            throw new AuthorizationException(principalId, "TimesheetSubmitAction", "");
        }
    }

    public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
        List<String> errorList = new ArrayList<String>();
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());
        
        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
            	
            	boolean nonExemptLE = LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(document.getPrincipalId(),
            				HrConstants.FLSA_STATUS_NON_EXEMPT, true);
            	if(nonExemptLE) {
            		Map<String,Set<LeaveBlockContract>> eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(document.getCalendarEntry(), document.getPrincipalId());
            		PrincipalHRAttributes pha = (PrincipalHRAttributes) HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(document.getPrincipalId(), document.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
					Calendar cal = pha.getLeaveCalObj();
					if(cal == null) {
						//non exempt leave eligible employee without a leave calendar?
						LOG.error("Principal is without a leave calendar");
						GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "principal.without.leavecal");
						return mapping.findForward("basic");
//						throw new RuntimeException("Principal is without a leave calendar");
                    }
    				List<LeaveBlockContract> eligibleTransfers = new ArrayList<LeaveBlockContract>();
    				List<LeaveBlockContract> eligiblePayouts = new ArrayList<LeaveBlockContract>();
            		Interval interval = new Interval(document.getCalendarEntry().getBeginPeriodDate().getTime(), document.getCalendarEntry().getEndPeriodDate().getTime());

	        		for(Entry<String,Set<LeaveBlockContract>> entry : eligibilities.entrySet()) {
	        			
	            		for(LeaveBlockContract lb : entry.getValue()) {
	            			if(interval.contains(lb.getLeaveDateTime())) {
	            				//maxBalanceViolations should, if a violation exists, return a leave block with leave date either current date, or the end period date - 1 days.
		        				AccrualCategoryRuleContract aRule = lb.getAccrualCategoryRule();
	
		            			if(ObjectUtils.isNotNull(aRule)
		            					&& !StringUtils.equals(aRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) {
		            				if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
		            					DateTime rollOverDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(pha.getLeavePlan(), document.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate());
		            					//the final calendar period of the leave plan should end within this time sheet 
		            					if(interval.contains(rollOverDate.minusDays(1).getMillis())) {
		            						//only leave blocks belonging to the calendar entry being submitted may reach this point
		            						//if the infraction occurs before the relative end date of the leave plan year, then action will be executed.
			            					if(lb.getLeaveDateTime().isBefore(rollOverDate)) {
						            			if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT)) {
						            				eligiblePayouts.add(lb);
						            			}
						            			else if(StringUtils.equals(aRule.getActionAtMaxBalance(), HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER)
						            					|| StringUtils.equals(aRule.getActionAtMaxBalance(), HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
						            				eligibleTransfers.add(lb);
						            			}
			            					}
		            					}
		            				}
		            				if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),HrConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
		            					//a leave period should end within the time period.
		            					CalendarEntry leaveEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(cal.getHrCalendarId(), lb.getLeaveDateTime());
		            					if(ObjectUtils.isNotNull(leaveEntry)) {
		            						//only leave blocks belonging to the calendar entry being submitted may reach this point.
		            						//if the infraction occurs before the end of the leave calendar entry, then action will be executed.
			            					if(interval.contains(DateUtils.addDays(leaveEntry.getEndPeriodDate(),-1).getTime())) {

						            			if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT)) {
						            				eligiblePayouts.add(lb);
						            			}
						            			else if(StringUtils.equals(aRule.getActionAtMaxBalance(), HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER)
						            					|| StringUtils.equals(aRule.getActionAtMaxBalance(), HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
						            				eligibleTransfers.add(lb);
						            			}
			            					}
		            					}
		            				}
		            			}
	            			}
	            		}
	        		}
            		ActionRedirect transferRedirect = new ActionRedirect();
            		ActionRedirect payoutRedirect = new ActionRedirect();
            		if(!eligibleTransfers.isEmpty()) {
                		transferRedirect.setPath("/BalanceTransfer.do?"+request.getQueryString());
                		request.getSession().setAttribute("eligibilities", eligibleTransfers);
                		return transferRedirect;
            		}
            		if(!eligiblePayouts.isEmpty()) {
                		payoutRedirect.setPath("/LeavePayout.do?"+request.getQueryString());
                		request.getSession().setAttribute("eligibilities", eligiblePayouts);
                		return payoutRedirect;
            		}
            	}
            	ClockLog clockLog = TkServiceLocator.getClockLogService().getLastClockLog(HrContext.getTargetPrincipalId());
                if(clockLog!=null){
                	String lastClockLogAction = clockLog.getClockAction();
                	if(lastClockLogAction.equals("CI") || lastClockLogAction.equals("LI")){
                		errorList.add("Please Clock Out/Lunch Out to submit timesheet for approval");
                	}else{
                		TkServiceLocator.getTimesheetService().routeTimesheet(HrContext.getTargetPrincipalId(), document);
                	}
                }else{ 
                	TkServiceLocator.getTimesheetService().routeTimesheet(HrContext.getTargetPrincipalId(), document);
                }
            }
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
        	if(TkServiceLocator.getTimesheetService().isReadyToApprove(document)) {
	            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                    if(TkServiceLocator.getTimesheetService().isTimesheetValid(document)) {
                        TkServiceLocator.getTimesheetService().approveTimesheet(HrContext.getPrincipalId(), document);
                    } else {
                        errorList.add("Timesheet " + document.getDocumentId() + " could not be approved as it contains errors, see time detail for more info");
                    }
	            }
        	} else {
        		//ERROR!!!
        	}
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
           		TkServiceLocator.getTimesheetService().disapproveTimesheet(HrContext.getPrincipalId(), document);
            }
        }
        
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(document, document.getDocEndDate());
        ActionRedirect rd = new ActionRedirect(mapping.findForward("timesheetRedirect"));
        rd.addParameter("documentId", tsaf.getDocumentId());
        if(CollectionUtils.isNotEmpty(errorList)) {
        	rd.addParameter("actionMessages", errorList);
        }
        return rd;
    }

    public ActionForward approveApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
    	List<String> errorList = new ArrayList<String>();
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.INITIATED.getCode())) {
                TkServiceLocator.getTimesheetService().routeTimesheet(HrContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
        	if(TkServiceLocator.getTimesheetService().isReadyToApprove(document)) {
	            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                    if(TkServiceLocator.getTimesheetService().isTimesheetValid(document)) {
                        TkServiceLocator.getTimesheetService().approveTimesheet(HrContext.getPrincipalId(), document);
                    } else {
                        errorList.add("Timesheet " + document.getDocumentId() + " could not be approved as it contains errors, see time detail for more info");
                    }
	            }
        	} else {
        		//ERROR!!!
        	}
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(HrContext.getPrincipalId(), document);
            }
        }
        HrContext.clearTargetUser();
        ActionRedirect redirect =new ActionRedirect(mapping.findForward("approverRedirect"));
        if(tsaf.getSelectedPayCalendarGroup() != null && StringUtils.isNotEmpty(tsaf.getSelectedPayCalendarGroup())) {
        	redirect.addParameter("selectedPayCalendarGroup", tsaf.getSelectedPayCalendarGroup());
        }
        if(tsaf.getSelectedDept() != null && StringUtils.isNotEmpty(tsaf.getSelectedDept())) {
        	redirect.addParameter("selectedDept", tsaf.getSelectedDept());
        }
        if(tsaf.getSelectedWorkArea() != null && StringUtils.isNotEmpty(tsaf.getSelectedWorkArea())) {
        	redirect.addParameter("selectedWorkArea", tsaf.getSelectedWorkArea());
        }
        if(tsaf.getSelectedPayPeriod() != null && StringUtils.isNotEmpty(tsaf.getSelectedPayPeriod())) {
        	redirect.addParameter("selectedPayPeriod", tsaf.getSelectedPayPeriod());
        }
        if(CollectionUtils.isNotEmpty(errorList)) {
        	redirect.addParameter("errorMessageList", errorList);
        }
        return redirect;
    }
}
