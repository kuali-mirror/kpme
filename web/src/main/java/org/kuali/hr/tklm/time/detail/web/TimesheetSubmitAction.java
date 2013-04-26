/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.tklm.time.detail.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.bo.calendar.Calendar;
import org.kuali.hr.core.bo.calendar.entry.CalendarEntry;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.web.HrAction;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class TimesheetSubmitAction extends HrAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
    	
    	String principalId = GlobalVariables.getUserSession().getPrincipalId();
    	String documentId = tsaf.getDocumentId();

        if (!TkServiceLocator.getTKPermissionService().canEditTimesheet(principalId, documentId)) {
            throw new AuthorizationException(principalId, "TimesheetSubmitAction", "");
        }
    }

    public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
            	
            	boolean nonExemptLE = LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(document.getPrincipalId(),
            				TkConstants.FLSA_STATUS_NON_EXEMPT, true);
            	if(nonExemptLE) {
            		Map<String,Set<LeaveBlock>> eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(document.getCalendarEntry(), document.getPrincipalId());
            		PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(document.getPrincipalId(), document.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
					Calendar cal = pha.getLeaveCalObj();
					if(cal == null) {
						//non exempt leave eligible employee without a leave calendar?
						throw new RuntimeException("Principal is without a leave calendar");
                    }
    				List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
    				List<LeaveBlock> eligiblePayouts = new ArrayList<LeaveBlock>();
            		Interval interval = new Interval(document.getCalendarEntry().getBeginPeriodDate().getTime(), document.getCalendarEntry().getEndPeriodDate().getTime());

	        		for(Entry<String,Set<LeaveBlock>> entry : eligibilities.entrySet()) {
	        			
	            		for(LeaveBlock lb : entry.getValue()) {
	            			if(interval.contains(lb.getLeaveDate().getTime())) {
	            				//maxBalanceViolations should, if a violation exists, return a leave block with leave date either current date, or the end period date - 1 days.
		        				AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
	
		            			if(ObjectUtils.isNotNull(aRule)
		            					&& !StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) {
		            				if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
		            					DateTime rollOverDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(pha.getLeavePlan(), document.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate());
		            					//the final calendar period of the leave plan should end within this time sheet 
		            					if(interval.contains(rollOverDate.minusDays(1).getMillis())) {
		            						//only leave blocks belonging to the calendar entry being submitted may reach this point
		            						//if the infraction occurs before the relative end date of the leave plan year, then action will be executed.
			            					if(lb.getLeaveDate().before(rollOverDate.toDate())) {
						            			if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.PAYOUT)) {
						            				eligiblePayouts.add(lb);
						            			}
						            			else if(StringUtils.equals(aRule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.TRANSFER)
						            					|| StringUtils.equals(aRule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
						            				eligibleTransfers.add(lb);
						            			}
			            					}
		            					}
		            				}
		            				if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
		            					//a leave period should end within the time period.
		            					CalendarEntry leaveEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(cal.getHrCalendarId(), new DateTime(lb.getLeaveDate()));
		            					if(ObjectUtils.isNotNull(leaveEntry)) {
		            						//only leave blocks belonging to the calendar entry being submitted may reach this point.
		            						//if the infraction occurs before the end of the leave calendar entry, then action will be executed.
			            					if(interval.contains(DateUtils.addDays(leaveEntry.getEndPeriodDate(),-1).getTime())) {

						            			if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.PAYOUT)) {
						            				eligiblePayouts.add(lb);
						            			}
						            			else if(StringUtils.equals(aRule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.TRANSFER)
						            					|| StringUtils.equals(aRule.getActionAtMaxBalance(), LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
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
                TkServiceLocator.getTimesheetService().routeTimesheet(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
        	if(TkServiceLocator.getTimesheetService().isReadyToApprove(document)) {
	            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
	                TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), document);
	            }
        	} else {
        		//ERROR!!!
        	}
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(TKContext.getPrincipalId(), document);
            }
        }
        
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(document, document.getAsOfDate());
        ActionRedirect rd = new ActionRedirect(mapping.findForward("timesheetRedirect"));
        rd.addParameter("documentId", tsaf.getDocumentId());

        return rd;
    }

    public ActionForward approveApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.INITIATED.getCode())) {
                TkServiceLocator.getTimesheetService().routeTimesheet(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
        	if(TkServiceLocator.getTimesheetService().isReadyToApprove(document)) {
	            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
	                TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), document);
	            }
        	} else {
        		//ERROR!!!
        	}
        } else if (StringUtils.equals(tsaf.getAction(), HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(TKContext.getPrincipalId(), document);
            }
        }
        TKContext.clearTargetUser();
        return new ActionRedirect(mapping.findForward("approverRedirect"));


    }
}
