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
package org.kuali.hr.lm.leavepayout.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.lm.leavepayout.validation.LeavePayoutValidationUtils;
import org.kuali.hr.lm.leavepayout.web.LeavePayoutForm;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeavePayoutAction extends TkAction {

	public ActionForward leavePayoutOnLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//if action was submit, execute the payout
		LeavePayoutForm lpf = (LeavePayoutForm) form;
		LeavePayout leavePayout = lpf.getLeavePayout();
	
		boolean valid = LeavePayoutValidationUtils.validateTransfer(leavePayout);
		
		//if payout amount has changed, and the resulting change produces forfeiture
		//or changes the forfeiture amount, prompt for confirmation with the amount of
		//forfeiture that the entered amount would produce.

		if(valid) {
			
			String accrualRuleId = leavePayout.getAccrualCategoryRule();
			
			String documentId = leavePayout.getLeaveCalendarDocumentId();
			TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
			LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
			CalendarEntries calendarEntry = null;
			String strutsActionForward = "";
			String methodToCall = "approveLeaveCalendar";
			if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
				throw new RuntimeException("No document found");
			}
			else if(ObjectUtils.isNotNull(tsdh)) {
				//Throws runtime exception, separate action forwards for timesheet/leave calendar payouts.
				TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				calendarEntry = tsd.getCalendarEntry();
				strutsActionForward = "timesheetTransferSuccess";
				methodToCall = "approveTimesheet";
			}
			else {
				LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
				calendarEntry = lcd.getCalendarEntry();
				strutsActionForward = "leaveCalendarTransferSuccess";
				methodToCall = "approveLeaveCalendar";
			}
			
			if(ObjectUtils.isNull(calendarEntry)) {
				throw new RuntimeException("Could not retreive calendar entry for document " + documentId);
			}
			
			AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(calendarEntry.getEndPeriodDate()))
				effectiveDate = new Date(DateUtils.addMinutes(calendarEntry.getEndPeriodDate(),-1).getTime());
			// if submitting a delinquent calendar, use the calendar's end period date for the effective date.
			// could adjust the end period date by subtracting a day so that the leave blocks appear on the month in question.
			
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(leavePayout.getPrincipalId(), calendarEntry);
			LeaveSummaryRow payoutRow = ls.getLeaveSummaryRowForAccrualCategory(accrualRule.getLmAccrualCategoryId());
			LeavePayout defaultBT = TkServiceLocator.getLeavePayoutService().initializePayout(leavePayout.getPrincipalId(), accrualRuleId, payoutRow.getAccruedBalance(), effectiveDate);
			if(leavePayout.getPayoutAmount().compareTo(defaultBT.getPayoutAmount()) != 0) {
				//employee changed the payout amount, recalculate forfeiture.
				//Note: payout form has been validated.
				leavePayout = defaultBT.adjust(leavePayout.getPayoutAmount());
				// showing the adjusted balance payout via the execution of another forward
				// would cause a loop that would break only if the original payout amount was re-established in the form.
				// javascript must be written if the forfeited amount is to be updated on the form object.
				// an alternative to javascript would be to render a "re-calculate" button attached to a dedicated action forward method.
				// must re-set leaveCalendarDocumentId, as leavePayout is now just an adjustment of the default initialized BT with no leave calendar doc id.
				leavePayout.setLeaveCalendarDocumentId(documentId);
			}

			TkServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
			
			if(ObjectUtils.isNotNull(documentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
						StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
					ActionForward forward = new ActionForward(mapping.findForward(strutsActionForward));
					forward.setPath(forward.getPath()+"?documentId="+documentId+"&action=R&methodToCall="+methodToCall);
					return forward;
				}
				else
					return mapping.findForward("closeLeavePayoutDoc");
			}
			else
				return mapping.findForward("closeLeavePayoutDoc");
		}
		else //show user errors.
			return mapping.findForward("basic");
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		LeavePayoutForm lpf = (LeavePayoutForm) form;
		LeavePayout bt = lpf.getLeavePayout();
		String accrualCategoryRuleId = bt.getAccrualCategoryRule();
		AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
		String actionFrequency = accrualRule.getMaxBalanceActionFrequency();
		
		if(StringUtils.equals(actionFrequency,LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			return mapping.findForward("closeLeavePayoutDoc");
		else 
			if(StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
					StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				ActionRedirect redirect = new ActionRedirect();
				redirect.setPath(mapping.findForward("cancel").getPath());
				redirect.addParameter("documentId", bt.getLeaveCalendarDocumentId());
				return redirect;
			}
			else
				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
	}
	
	//Entry point for LeavePayout.do for accrual category rule triggered payouts with action frequency On Demand.
	//May be better suited in the LeaveCalendarAction class.
	public ActionForward leavePayoutOnDemand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GlobalVariables.getMessageMap().putWarning("document.payoutAmount","leavePayout.payoutAmount.adjust");

		LeavePayoutForm lpf = (LeavePayoutForm) form;
		//the leave calendar document that triggered this balance payout.
		String documentId = request.getParameter("documentId");
		String accrualRuleId = request.getParameter("accrualRuleId");
		String timesheet = request.getParameter("timesheet");
		boolean isTimesheet = false;
		if(StringUtils.equals(timesheet, "true")) {
			lpf.isTimesheet(true);
			isTimesheet = true;
		}
		if(ObjectUtils.isNotNull(accrualRuleId)) {
			AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			if(ObjectUtils.isNotNull(aRule)) {
				//should somewhat safegaurd against url fabrication.
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
					throw new RuntimeException("attempted to execute on-demand balance payout for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
				else {
					TimesheetDocument tsd = null;
					LeaveCalendarDocument lcd = null;
					String principalId = null;
					CalendarEntries calendarEntry = null;
					
					if(isTimesheet) {
						tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
						principalId = tsd.getPrincipalId();
						calendarEntry = tsd.getCalendarEntry();
					}
					else {
						lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
						principalId = lcd.getPrincipalId();
						calendarEntry = lcd.getCalendarEntry();
					}
					LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
					
					Date effectiveDate = TKUtils.getCurrentDate();
					if(TKUtils.getCurrentDate().after(calendarEntry.getEndPeriodDate()))
						effectiveDate = new Date(DateUtils.addMinutes(calendarEntry.getEndPeriodDate(),-1).getTime());

					LeaveSummaryRow payoutRow = ls.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
					LeavePayout leavePayout = TkServiceLocator.getLeavePayoutService().initializePayout(principalId, accrualRuleId, payoutRow.getAccruedBalance(), effectiveDate);
					leavePayout.setLeaveCalendarDocumentId(documentId);
					if(ObjectUtils.isNotNull(leavePayout)) {
						if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {	
							// this particular combination of action / action frequency does not particularly make sense
							// unless for some reason users still need to be prompted to submit the loss.
							// For now, we treat as though it is a valid use-case.
							//TkServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
							// May need to update to save the business object to KPME's tables for record keeping.
							leavePayout = TkServiceLocator.getLeavePayoutService().payout(leavePayout);
							// May need to update to save the business object to KPME's tables for record keeping.
							LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
							forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
							TkServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, principalId);
							return mapping.findForward("closeLeavePayoutDoc");
						}
						else {
							ActionForward forward = mapping.findForward("basic");
							lpf.setLeaveCalendarDocumentId(documentId);
							lpf.setLeavePayout(leavePayout);
							lpf.setPayoutAmount(leavePayout.getPayoutAmount());
							return forward;
						}
					}
					else
						throw new RuntimeException("could not initialize a balance payout");

				}
			}
			else
				throw new RuntimeException("No rule for this accrual category could be found");
		}
		else
			throw new RuntimeException("No accrual category rule id has been sent in the request.");
	}

	//Entry point for LeavePayout.do for accrual category rule triggered payouts with action frequency Leave Approve.
	//TODO: Rename method to differentiate from ActionForward with same name in LeaveCalendarSubmit.
	public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.payoutAmount","leavePayout.payoutAmount.adjust");
		LeavePayoutForm lpf = (LeavePayoutForm) form;

		int categoryCounter = 0;
		List<String> payoutableAccrualCategoryRules = new ArrayList<String>();
		String accrualRuleId = request.getParameter("accrualCategory0");
		while(ObjectUtils.isNotNull(accrualRuleId)) {
			//TODO: Get rid of this loop
			categoryCounter++;
			payoutableAccrualCategoryRules.add(accrualRuleId);
			accrualRuleId = request.getParameter("accrualCategory"+categoryCounter);
		}

		//Bad.... User must be prompted for each payout that needs to be made.
		//For now, assuming not more than one accrual category is eligible for payout.
		if(!payoutableAccrualCategoryRules.isEmpty()) {
			//This is the leave calendar document that triggered this balance payout.
			String leaveCalendarDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
			LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());
			
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
				effectiveDate = new Date(DateUtils.addMinutes(lcd.getCalendarEntry().getEndPeriodDate(),-1).getTime());
			
			accrualRuleId = payoutableAccrualCategoryRules.get(0);
			AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			LeaveSummaryRow payoutRow = leaveSummary.getLeaveSummaryRowForAccrualCategory(aRule.getLmAccrualCategoryId());
			LeavePayout leavePayout = TkServiceLocator.getLeavePayoutService().initializePayout(lcd.getPrincipalId(), accrualRuleId, payoutRow.getAccruedBalance(), effectiveDate);
			leavePayout.setLeaveCalendarDocumentId(leaveCalendarDocumentId);

			if(ObjectUtils.isNotNull(leavePayout)) {
				AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {

					//TkServiceLocator.getLeavePayoutService().submitToWorkflow(leavePayout);
					leavePayout = TkServiceLocator.getLeavePayoutService().payout(leavePayout);
					// May need to update to save the business object to KPME's tables for record keeping.
					LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
					forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
					TkServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, lcd.getPrincipalId());
					
					ActionRedirect redirect = new ActionRedirect();
					if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
						if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
								StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
							ActionForward loseForward = new ActionForward(mapping.findForward("leaveCalendarTransferSuccess"));
							loseForward.setPath(loseForward.getPath()+"?documentId="+leaveCalendarDocumentId+"&action=R&methodToCall=approveLeaveCalendar");
							return loseForward;
						}
						//on demand handled in separate action forward.
					}

				} else {
					lpf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
					lpf.setLeavePayout(leavePayout);
					lpf.setPayoutAmount(leavePayout.getPayoutAmount());
					return forward;
				}

			}
			throw new RuntimeException("could not initialize balance payout");

		}
		else
			throw new RuntimeException("unable to fetch the accrual category that triggerred this payout");
	}
	
	public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.payoutAmount","leavePayout.payoutAmount.adjust");
		LeavePayoutForm lpf = (LeavePayoutForm) form;

		int categoryCounter = 0;
		List<String> payoutableAccrualCategoryRules = new ArrayList<String>();
		String accrualRuleId = request.getParameter("accrualCategory0");
		while(ObjectUtils.isNotNull(accrualRuleId)) {
			//TODO: Get rid of this loop
			categoryCounter++;
			payoutableAccrualCategoryRules.add(accrualRuleId);
			accrualRuleId = request.getParameter("accrualCategory"+categoryCounter);
		}

		//Bad.... User must be prompted for each payout that needs to be made.
		//For now, assuming not more than one accrual category is eligible for payout.
		if(!payoutableAccrualCategoryRules.isEmpty()) {
			//This is the leave calendar document that triggered this balance payout.
			String timesheetDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
			LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(tsd.getPrincipalId(), tsd.getCalendarEntry());
			
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(tsd.getCalendarEntry().getEndPeriodDate()))
				effectiveDate = new Date(DateUtils.addMinutes(tsd.getCalendarEntry().getEndPeriodDate(),-1).getTime());
			
			accrualRuleId = payoutableAccrualCategoryRules.get(0);
			AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			LeaveSummaryRow payoutRow = leaveSummary.getLeaveSummaryRowForAccrualCategory(accrualRule.getLmAccrualCategoryId());
			LeavePayout leavePayout = TkServiceLocator.getLeavePayoutService().initializePayout(tsd.getPrincipalId(), accrualRuleId, payoutRow.getAccruedBalance(), effectiveDate);
			leavePayout.setLeaveCalendarDocumentId(timesheetDocumentId);

			if(ObjectUtils.isNotNull(leavePayout)) {
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
					// TODO: Redirect user to prompt stating excess leave will be forfeited and ask for confirmation.
					// Do not submit the object to workflow for this max balance action.
					leavePayout = TkServiceLocator.getLeavePayoutService().payout(leavePayout);
					// May need to update to save the business object to KPME's tables for record keeping.
					LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leavePayout.getForfeitedLeaveBlockId());
					forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
					TkServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, tsd.getPrincipalId());
					ActionRedirect redirect = new ActionRedirect();
					if(ObjectUtils.isNotNull(timesheetDocumentId)) {
						if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
								StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
							ActionForward loseForward = new ActionForward(mapping.findForward("timesheetTransferSuccess"));
							loseForward.setPath(loseForward.getPath()+"?documentId="+timesheetDocumentId+"&action=R&methodToCall=approveTimesheet");
							return loseForward;
						}
						//on demand handled in separate action forward.
					}

				} else {
					lpf.setLeaveCalendarDocumentId(timesheetDocumentId);
					lpf.setLeavePayout(leavePayout);
					lpf.setPayoutAmount(leavePayout.getPayoutAmount());
					return forward;
				}

			}
			throw new RuntimeException("could not initialize balance payout");

		}
		else
			throw new RuntimeException("unable to fetch the accrual category that triggerred this payout");
	}
	
	public ActionForward closeLeavePayoutDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("closeLeavePayoutDoc");
	}
}
