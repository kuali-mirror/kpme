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
package org.kuali.hr.time.detail.web;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import edu.emory.mathcs.backport.java.util.Collections;

public class TimesheetSubmitAction extends TkAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;

        String principal = TKContext.getPrincipalId();
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());

        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());
        if (!roles.isDocumentWritable(document)) {
            throw new AuthorizationException(principal, "TimesheetSubmitAction", "");
        }
    }




    public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimesheetSubmitActionForm tsaf = (TimesheetSubmitActionForm)form;
        TimesheetDocument document = TkServiceLocator.getTimesheetService().getTimesheetDocument(tsaf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(tsaf.getAction(), TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
            	
            	boolean nonExemptLE = TkServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(document.getPrincipalId(),
            				TkConstants.FLSA_STATUS_NON_EXEMPT, true);
            	if(nonExemptLE) {
            		Map<String,Set<LeaveBlock>> eligibilities = TkServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(document.getCalendarEntry(), document.getPrincipalId());
            		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(document.getPrincipalId(), document.getCalendarEntry().getEndPeriodDate());
					Calendar cal = pha.getLeaveCalObj();
					if(cal == null)
						throw new RuntimeException("Principal is without a leave calendar");
					List<CalendarEntries> leaveCalEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesEndingBetweenBeginAndEndDate(cal.getHrCalendarId(), document.getCalendarEntry().getBeginPeriodDate(), document.getCalendarEntry().getEndPeriodDate());
					CalendarEntries yearEndLeaveEntry = null;
					CalendarEntries leaveLeaveEntry = null;
					if(!leaveCalEntries.isEmpty()) {
						for(CalendarEntries leaveEntry : leaveCalEntries) {
							if(TkServiceLocator.getLeavePlanService().isLastCalendarPeriodOfLeavePlan(leaveEntry, pha.getLeavePlan(), document.getCalendarEntry().getEndPeriodDate()));
								yearEndLeaveEntry = leaveEntry;
							if(leaveEntry.getEndPeriodDate().compareTo(document.getCalendarEntry().getEndPeriodDate()) <= 0)
								leaveLeaveEntry = leaveEntry;
						}
					}
            		ActionRedirect transferRedirect = new ActionRedirect();
            		ActionRedirect payoutRedirect = new ActionRedirect();
            		//TKContext.getHttpServletRequest().setAttribute("eligibility", arg1)
    				
    				List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
    				List<LeaveBlock> eligiblePayouts = new ArrayList<LeaveBlock>();
            		Interval interval = new Interval(document.getCalendarEntry().getBeginPeriodDate().getTime(), document.getCalendarEntry().getEndPeriodDate().getTime());

	        		for(Entry<String,Set<LeaveBlock>> entry : eligibilities.entrySet()) {
	        			
	            		for(LeaveBlock lb : entry.getValue()) {
	            			if(interval.contains(lb.getLeaveDate().getTime())) {
	            				//maxBalanceViolations should, if a violation exists, return a leave block with leave date either current date, or the end period date - 1 days.
		        				AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
	
		            			if(ObjectUtils.isNotNull(aRule)
		            					&& !StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) {
		            				if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
		            					//the final calendar period of the leave plan should end within this time sheet 
		            					if(ObjectUtils.isNotNull(yearEndLeaveEntry)) {
		            						//the max balance infractions must fall within the time period, but also before the final leave period's
		            						//end date.
			            					if(lb.getLeaveDate().before(yearEndLeaveEntry.getEndPeriodDate())
			            							&& lb.getLeaveDate().compareTo(document.getCalendarEntry().getBeginPeriodDate()) >= 0) {
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
		            					if(ObjectUtils.isNotNull(leaveLeaveEntry)) {
		            						//the max balance infractions must fall within the time period, but also before the leave periods
		            						//end date to be triggered.
			            					if(lb.getLeaveDate().before(leaveLeaveEntry.getEndPeriodDate())
			            							&& lb.getLeaveDate().compareTo(document.getCalendarEntry().getBeginPeriodDate()) >= 0) {
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
            	//TODO: check for max balance actions that could occur when no leave block is present on the calendar entry.
            	//i.e. a change in service intervals where the new interval's rule lowers the balance limit.
                TkServiceLocator.getTimesheetService().routeTimesheet(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
        	if(TkServiceLocator.getTimesheetService().isReadyToApprove(document)) {
	            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
	                TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), document);
	            }
        	} else {
        		//ERROR!!!
        	}
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
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
        if (StringUtils.equals(tsaf.getAction(), TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.INITIATED.getCode())) {
                TkServiceLocator.getTimesheetService().routeTimesheet(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
        	if(TkServiceLocator.getTimesheetService().isReadyToApprove(document)) {
	            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
	                TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), document);
	            }
        	} else {
        		//ERROR!!!
        	}
        } else if (StringUtils.equals(tsaf.getAction(), TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getTimesheetService().disapproveTimesheet(TKContext.getPrincipalId(), document);
            }
        }
        TKContext.getUser().clearTargetUser();
        return new ActionRedirect(mapping.findForward("approverRedirect"));


    }
}
