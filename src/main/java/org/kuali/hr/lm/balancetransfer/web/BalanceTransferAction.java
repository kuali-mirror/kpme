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
package org.kuali.hr.lm.balancetransfer.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.balancetransfer.validation.BalanceTransferValidationUtils;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferAction extends TkAction {

	public ActionForward balanceTransferOnLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		//if action was submit, execute the transfer
		BalanceTransferForm btf = (BalanceTransferForm) form;
		BalanceTransfer balanceTransfer = btf.getBalanceTransfer();
	
		boolean valid = BalanceTransferValidationUtils.validateTransfer(balanceTransfer);
		
		//if transfer amount has changed, and the resulting change produces forfeiture
		//or changes the forfeiture amount, prompt for confirmation with the amount of
		//forfeiture that the entered amount would produce.

		if(valid) {
			
			String accrualRuleId = balanceTransfer.getAccrualCategoryRule();
			
			String documentId = balanceTransfer.getLeaveCalendarDocumentId();
			TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
			LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
			CalendarEntries calendarEntry = null;
			String strutsActionForward = "";
			String methodToCall = "approveLeaveCalendar";
			if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
				throw new RuntimeException("No document found");
			}
			else if(ObjectUtils.isNotNull(tsdh)) {
				//Throws runtime exception, separate action forwards for timesheet/leave calendar transfers.
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
			if(TKUtils.getCurrentDate().after(DateUtils.addSeconds(calendarEntry.getEndPeriodDate(),-1)))
				effectiveDate = new Date(DateUtils.addSeconds(calendarEntry.getEndPeriodDate(),-1).getTime());
			// if submitting a delinquent calendar, use the calendar's end period date for the effective date.
			// could adjust the end period date by subtracting a day so that the leave blocks appear on the month in question.
			
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(balanceTransfer.getPrincipalId(), new java.sql.Date(DateUtils.addDays(balanceTransfer.getEffectiveDate(),1).getTime()));
			LeaveSummaryRow transferRow = ls.getLeaveSummaryRowForAccrualCategory(accrualRule.getLmAccrualCategoryId());
			BalanceTransfer defaultBT = TkServiceLocator.getBalanceTransferService().initializeTransfer(balanceTransfer.getPrincipalId(), accrualRuleId, transferRow.getAccruedBalance(), balanceTransfer.getEffectiveDate());
			if(balanceTransfer.getTransferAmount().compareTo(defaultBT.getTransferAmount()) != 0) {
				//employee changed the transfer amount, recalculate forfeiture.
				//Note: transfer form has been validated.
				balanceTransfer = defaultBT.adjust(balanceTransfer.getTransferAmount());
				// showing the adjusted balance transfer via the execution of another forward
				// would cause a loop that would break only if the original transfer amount was re-established in the form.
				// javascript must be written if the forfeited amount is to be updated on the form object.
				// an alternative to javascript would be to render a "re-calculate" button attached to a dedicated action forward method.
				// must re-set leaveCalendarDocumentId, as balanceTransfer is now just an adjustment of the default initialized BT with no leave calendar doc id.
				balanceTransfer.setLeaveCalendarDocumentId(documentId);
			}

			TkServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
			
			if(ObjectUtils.isNotNull(documentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
						StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
					ActionForward forward = new ActionForward(mapping.findForward(strutsActionForward));
					forward.setPath(forward.getPath()+"?documentId="+documentId+"&action=R&methodToCall="+methodToCall);
					return forward;
				}
				else
					return mapping.findForward("closeBalanceTransferDoc");
			}
			else
				return mapping.findForward("closeBalanceTransferDoc");
		}
		else //show user errors.
			return mapping.findForward("basic");
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		BalanceTransferForm btf = (BalanceTransferForm) form;
		BalanceTransfer bt = btf.getBalanceTransfer();

		if(btf.isSstoTransfer()) {
			return mapping.findForward("closeBalanceTransferDoc");
		}
		
		String accrualCategoryRuleId = bt.getAccrualCategoryRule();
		AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
		String actionFrequency = accrualRule.getMaxBalanceActionFrequency();
		
		if(StringUtils.equals(actionFrequency,LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) 
			return mapping.findForward("closeBalanceTransferDoc");
		else 
			if(StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
					StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				String documentId = bt.getLeaveCalendarDocumentId();
				TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
				LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
				String strutsActionForward = "";
				if(ObjectUtils.isNull(tsdh) && ObjectUtils.isNull(lcdh)) {
					strutsActionForward = "/";
				}
				else if(ObjectUtils.isNotNull(tsdh)) {
					//Throws runtime exception, separate action forwards for timesheet/leave calendar transfers.
					strutsActionForward = mapping.findForward("timesheetCancel").getPath() + "?documentId=" + bt.getLeaveCalendarDocumentId();
				}
				else {
					strutsActionForward = mapping.findForward("leaveCalendarCancel").getPath() + "?documentId=" + bt.getLeaveCalendarDocumentId();
				}

				ActionRedirect redirect = new ActionRedirect();
				redirect.setPath(strutsActionForward);
				return redirect;
			}
			else
				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
	}
	
	//Entry point for BalanceTransfer.do for accrual category rule triggered transfers with action frequency On Demand.
	//May be better suited in the LeaveCalendarAction class.
	public ActionForward balanceTransferOnDemand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GlobalVariables.getMessageMap().putWarning("document.transferAmount","balanceTransfer.transferAmount.adjust");

		BalanceTransferForm btf = (BalanceTransferForm) form;
		//the leave calendar document that triggered this balance transfer.
		String documentId = request.getParameter("documentId");
		String leaveBlockId = request.getParameter("accrualRuleId");
		String timesheet = request.getParameter("timesheet");
		boolean isTimesheet = false;
		if(StringUtils.equals(timesheet, "true")) {
			btf.isTimesheet(true);
			isTimesheet = true;
		}
		if(ObjectUtils.isNotNull(leaveBlockId)) {
			LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
			AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
			if(ObjectUtils.isNotNull(aRule)) {
				//should somewhat safegaurd against url fabrication.
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
					throw new RuntimeException("attempted to execute on-demand balance transfer for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
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
					
					Date effectiveDate = TKUtils.getCurrentDate();
					if(TKUtils.getCurrentDate().after(DateUtils.addSeconds(calendarEntry.getEndPeriodDate(),-1)))
						effectiveDate = new Date(DateUtils.addSeconds(calendarEntry.getEndPeriodDate(),-1).getTime());

					LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(principalId, new java.sql.Date(DateUtils.addDays(lb.getLeaveDate(),1).getTime()));
					LeaveSummaryRow transferRow = ls.getLeaveSummaryRowForAccrualCtgy(lb.getAccrualCategory());
					BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeTransfer(principalId, aRule.getLmAccrualCategoryRuleId(), transferRow.getAccruedBalance(), lb.getLeaveDate());
					balanceTransfer.setLeaveCalendarDocumentId(documentId);
					if(ObjectUtils.isNotNull(balanceTransfer)) {
						if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {	
							// this particular combination of action / action frequency does not particularly make sense
							// unless for some reason users still need to be prompted to submit the loss.
							// For now, we treat as though it is a valid use-case.
							//TkServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
							// May need to update to save the business object to KPME's tables for record keeping.
							balanceTransfer = TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
							// May need to update to save the business object to KPME's tables for record keeping.
							LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(balanceTransfer.getForfeitedLeaveBlockId());
							forfeitedLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
							TkServiceLocator.getLeaveBlockService().updateLeaveBlock(forfeitedLeaveBlock, principalId);
							return mapping.findForward("closeBalanceTransferDoc");
						}
						else {
							ActionForward forward = mapping.findForward("basic");
							btf.setLeaveCalendarDocumentId(documentId);
							btf.setBalanceTransfer(balanceTransfer);
							btf.setTransferAmount(balanceTransfer.getTransferAmount());
							return forward;
						}
					}
					else
						throw new RuntimeException("could not initialize a balance transfer");

				}
			}
			else
				throw new RuntimeException("No rule for this accrual category could be found");
		}
		else
			throw new RuntimeException("No accrual category rule id has been sent in the request.");
	}

	//Entry point for BalanceTransfer.do for accrual category rule triggered transfers with action frequency Leave Approve.
	//TODO: Rename method to differentiate from ActionForward with same name in LeaveCalendarSubmit.
	public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","balanceTransfer.transferAmount.adjust");
		BalanceTransferForm btf = (BalanceTransferForm) form;

		int categoryCounter = 0;
		List<String> transferableAccrualCategoryRules = new ArrayList<String>();
		String leaveBlockId = request.getParameter("accrualCategory0");
		while(ObjectUtils.isNotNull(leaveBlockId)) {
			//TODO: Get rid of this loop
			categoryCounter++;
			transferableAccrualCategoryRules.add(leaveBlockId);
			leaveBlockId = request.getParameter("accrualCategory"+categoryCounter);
		}

		//Bad.... User must be prompted for each transfer that needs to be made.
		//For now, assuming not more than one accrual category is eligible for transfer.
		if(!transferableAccrualCategoryRules.isEmpty()) {
			//This is the leave calendar document that triggered this balance transfer.
			String leaveCalendarDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
			
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(DateUtils.addSeconds(lcd.getCalendarEntry().getEndPeriodDate(),-1)))
				effectiveDate = new Date(DateUtils.addSeconds(lcd.getCalendarEntry().getEndPeriodDate(),-1).getTime());
			
			leaveBlockId = transferableAccrualCategoryRules.get(0);
			LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);			
			LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(lcd.getPrincipalId(), new java.sql.Date(DateUtils.addDays(lb.getLeaveDate(),1).getTime()));

			LeaveSummaryRow transferRow = leaveSummary.getLeaveSummaryRowForAccrualCtgy(lb.getAccrualCategory());
			BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeTransfer(lcd.getPrincipalId(), lb.getAccrualCategoryRuleId(), transferRow.getAccruedBalance(), lb.getLeaveDate());
			balanceTransfer.setLeaveCalendarDocumentId(leaveCalendarDocumentId);

			if(ObjectUtils.isNotNull(balanceTransfer)) {

				AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {

					//TkServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
					balanceTransfer = TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
					// May need to update to save the business object to KPME's tables for record keeping.
					LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(balanceTransfer.getForfeitedLeaveBlockId());
					KRADServiceLocator.getBusinessObjectService().save(balanceTransfer);
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
					btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
					btf.setBalanceTransfer(balanceTransfer);
					btf.setTransferAmount(balanceTransfer.getTransferAmount());
					return forward;
				}

			}
			throw new RuntimeException("could not initialize balance transfer");

		}
		else
			throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
	}
	
	public ActionForward approveTimesheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","balanceTransfer.transferAmount.adjust");
		BalanceTransferForm btf = (BalanceTransferForm) form;

		int categoryCounter = 0;
		List<String> transferableAccrualCategoryRules = new ArrayList<String>();
		String leaveBlockId = request.getParameter("accrualCategory0");
		List<LeaveBlock> infractingLeaveBlocks = new ArrayList<LeaveBlock>();
		while(ObjectUtils.isNotNull(leaveBlockId)) {
			//TODO: Get rid of this loop
			infractingLeaveBlocks.add(TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId));
			categoryCounter++;
			transferableAccrualCategoryRules.add(leaveBlockId);
			leaveBlockId = request.getParameter("accrualCategory"+categoryCounter);
		}

		//Bad.... User must be prompted for each transfer that needs to be made.
		//For now, assuming not more than one accrual category is eligible for transfer.
		if(!transferableAccrualCategoryRules.isEmpty()) {
			//This is the leave calendar document that triggered this balance transfer.
			String timesheetDocumentId = request.getParameter("documentId");
			ActionForward forward = new ActionForward(mapping.findForward("basic"));
			TimesheetDocument tsd = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
			CalendarEntries timeCalendarEntry = tsd.getCalendarEntry();
			
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(DateUtils.addSeconds(timeCalendarEntry.getEndPeriodDate(),-1)))
				effectiveDate = new Date(DateUtils.addSeconds(timeCalendarEntry.getEndPeriodDate(),-1).getTime());
				
			
			leaveBlockId = transferableAccrualCategoryRules.get(0);
			LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
			LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(tsd.getPrincipalId(), new java.sql.Date(DateUtils.addDays(lb.getLeaveDate(),1).getTime()));
			LeaveSummaryRow transferRow = leaveSummary.getLeaveSummaryRowForAccrualCtgy(lb.getAccrualCategory());
			BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeTransfer(tsd.getPrincipalId(), lb.getAccrualCategoryRuleId(), transferRow.getAccruedBalance(), lb.getLeaveDate());
			balanceTransfer.setLeaveCalendarDocumentId(timesheetDocumentId);

			if(ObjectUtils.isNotNull(balanceTransfer)) {
				AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
					// TODO: Redirect user to prompt stating excess leave will be forfeited and ask for confirmation.
					// Do not submit the object to workflow for this max balance action.
					balanceTransfer = TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
					KRADServiceLocator.getBusinessObjectService().save(balanceTransfer);

					// May need to update to save the business object to KPME's tables for record keeping.
					LeaveBlock forfeitedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(balanceTransfer.getForfeitedLeaveBlockId());
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
					btf.setLeaveCalendarDocumentId(timesheetDocumentId);
					btf.setBalanceTransfer(balanceTransfer);
					btf.setTransferAmount(balanceTransfer.getTransferAmount());
					return forward;
				}

			}
			throw new RuntimeException("could not initialize balance transfer");

		}
		else
			throw new RuntimeException("unable to fetch the accrual category that triggerred this transfer");
	}
	
	public ActionForward closeBalanceTransferDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("closeBalanceTransferDoc");
	}
	
	/* Delete system scheduled time off usage leave block from Leave or Time Calendar 
	 */
	public ActionForward deleteSSTOLeaveBlock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BalanceTransferForm btf = (BalanceTransferForm) form;
		buildBalanceTransferForLeaveBlock(btf, request.getParameter("leaveBlockId"));
	
		return new ActionForward(mapping.findForward("basic"));
	}
	
	/* Build balance transfer based on the to-be-deleted leave block 
	 */
	private void buildBalanceTransferForLeaveBlock(BalanceTransferForm btf, String lbId) {
		LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lbId);
		// this leave block is a ssto usage block, need to use it fo find the accrualed leave block which has a positive amount
		if(lb == null && StringUtils.isEmpty(lb.getScheduleTimeOffId())) {
			throw new RuntimeException("could not find the System Scheduled Time Off leave block that needs to be transferred!");	
		}
		SystemScheduledTimeOff ssto = TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(lb.getScheduleTimeOffId());
		BigDecimal amountTransferred = ssto.getTransferConversionFactor() == null ? lb.getLeaveAmount() : lb.getLeaveAmount().multiply(ssto.getTransferConversionFactor());
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(ssto.getTransfertoEarnCode(), lb.getLeaveDate());
		
		BalanceTransfer bt = new BalanceTransfer();
		bt.setTransferAmount(lb.getLeaveAmount().abs());	// the usage leave block's leave amount is negative
		bt.setFromAccrualCategory(lb.getAccrualCategory());
		bt.setAmountTransferred(amountTransferred.abs());
		bt.setToAccrualCategory(ec.getAccrualCategory());
		bt.setSstoId(lb.getScheduleTimeOffId());
		bt.setEffectiveDate(lb.getLeaveDate());
		bt.setPrincipalId(lb.getPrincipalId());
		
		btf.setBalanceTransfer(bt);
		btf.setTransferAmount(bt.getTransferAmount());
		GlobalVariables.getMessageMap().putWarning("document.newMaintainableObj.transferAmount","balanceTransfer.transferSSTO", 
				bt.getTransferAmount().toString(), bt.getAmountTransferred().toString());
	}
	/*
	 * Submit a balance transfer document when deleting a ssto usage leave block from current Leave/time calendar
	 * delete both accrued and usage ssto leave blocks, a pending transferred leave block is created by the BT doc
	 */
	public ActionForward balanceTransferOnSSTO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		BalanceTransferForm btf = (BalanceTransferForm) form;
		BalanceTransfer bt = btf.getBalanceTransfer();
		
		if(StringUtils.isEmpty(bt.getSstoId())) {
			throw new RuntimeException("System Scheduled Time Off not found for this balance transfer!");
		}
		List<LeaveBlock> lbList = TkServiceLocator.getLeaveBlockService().getSSTOLeaveBlocks(bt.getPrincipalId(), bt.getSstoId(), bt.getEffectiveDate());
		if(CollectionUtils.isEmpty(lbList) || (CollectionUtils.isNotEmpty(lbList) && lbList.size() != 2)) {
			throw new RuntimeException("There should be 2 system scheduled time off leave blocks!");
		}
		TkServiceLocator.getBalanceTransferService().submitToWorkflow(bt);
		// delete both SSTO accrualed and usage leave blocks
		for(LeaveBlock lb : lbList) {
			TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(lb.getLmLeaveBlockId(), lb.getPrincipalId());
		}
		return mapping.findForward("closeBalanceTransferDoc");
	}

}
