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

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
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
			String leaveCalendarDocumentId = btf.getLeaveCalendarDocumentId();
			
			AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);

			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
				effectiveDate = new Date(DateUtils.addMinutes(lcd.getCalendarEntry().getEndPeriodDate(),-1).getTime());
			// if submitting a delinquent calendar, use the calendar's end period date for the effective date.
			// could adjust the end period date by subtracting a day so that the leave blocks appear on the month in question.
			
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(balanceTransfer.getPrincipalId(), lcd.getCalendarEntry());
			BalanceTransfer defaultBT = TkServiceLocator.getBalanceTransferService().initializeTransfer(balanceTransfer.getPrincipalId(), accrualRuleId, ls, effectiveDate);
			if(balanceTransfer.getTransferAmount().compareTo(defaultBT.getTransferAmount()) != 0) {
				//employee changed the transfer amount, recalculate forfeiture.
				//Note: transfer form has been validated. d
				balanceTransfer = defaultBT.adjust(balanceTransfer.getTransferAmount());
				// showing the adjusted balance transfer via the execution of another forward
				// would cause a loop that would break only if the original transfer amount was re-established in the form.
				// javascript must be written if the forfeited amount is to be updated on the form object.
				// an alternative to javascript would be to render a "re-calculate" button attached to a dedicated action forward method.
				//balanceTransfer.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
			}

			TkServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
			
			if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
						StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
					ActionForward forward = new ActionForward(mapping.findForward("success"));
					forward.setPath(forward.getPath()+"?documentId="+leaveCalendarDocumentId+"&action=R&methodToCall=approveLeaveCalendar");
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
		String accrualCategoryRuleId = bt.getAccrualCategoryRule();
		AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualCategoryRuleId);
		String actionFrequency = accrualRule.getMaxBalanceActionFrequency();
		
		if(StringUtils.equals(actionFrequency,LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
			return mapping.findForward("closeBalanceTransferDoc");
		else 
			if(StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
					StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				ActionRedirect redirect = new ActionRedirect();
				redirect.setPath(mapping.findForward("cancel").getPath());
				redirect.addParameter("documentId", btf.getLeaveCalendarDocumentId());
		
				return redirect;
			}
			else
				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
	}
	
	//Entry point for BalanceTransfer.do for accrual category rule triggered transfers with action frequency On Demand.
	public ActionForward balanceTransferOnDemand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GlobalVariables.getMessageMap().putWarning("document.transferAmount","balanceTransfer.transferAmount.adjust");

		BalanceTransferForm btf = (BalanceTransferForm) form;
		//the leave calendar document that triggered this balance transfer.
		String leaveCalendarDocumentId = request.getParameter("documentId");
		String accrualRuleId = request.getParameter("accrualRuleId");
		if(ObjectUtils.isNotNull(accrualRuleId)) {
			AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			if(ObjectUtils.isNotNull(aRule)) {
				//should somewhat safegaurd against url fabrication.
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
					throw new RuntimeException("attempted to execute on-demand balance transfer for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
				else {
					LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
					LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());
					
					Date effectiveDate = TKUtils.getCurrentDate();
					if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
						effectiveDate = new Date(DateUtils.addMinutes(lcd.getCalendarEntry().getEndPeriodDate(),-1).getTime());

					BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeTransfer(lcd.getPrincipalId(), accrualRuleId, ls, effectiveDate);
					btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
					if(ObjectUtils.isNotNull(balanceTransfer)) {
						if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
							//TODO: Prompt the user stating that they are FORFEITING excess leave and ask for confirmation.						
							TkServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
							return mapping.findForward("closeBalanceTransferDoc");
						}
						else {
							btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
							btf.setBalanceTransfer(balanceTransfer);
							btf.setTransferAmount(balanceTransfer.getTransferAmount());
							return mapping.findForward("basic");
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
		String accrualRuleId = request.getParameter("accrualCategory0");
		while(ObjectUtils.isNotNull(accrualRuleId)) {
			//TODO: Get rid of this loop
			categoryCounter++;
			transferableAccrualCategoryRules.add(accrualRuleId);
			accrualRuleId = request.getParameter("accrualCategory"+categoryCounter);
		}

		//Bad.... User must be prompted for each transfer that needs to be made.
		//For now, assuming not more than one accrual category is eligible for transfer.
		if(!transferableAccrualCategoryRules.isEmpty()) {
			//This is the leave calendar document that triggered this balance transfer.
			String leaveCalendarDocumentId = request.getParameter("documentId");
			LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
			LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());
			
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
				effectiveDate = new Date(DateUtils.addMinutes(lcd.getCalendarEntry().getEndPeriodDate(),-1).getTime());
			
			accrualRuleId = transferableAccrualCategoryRules.get(0);
			BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeTransfer(lcd.getPrincipalId(), accrualRuleId, leaveSummary, effectiveDate);
			btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);

			if(ObjectUtils.isNotNull(balanceTransfer)) {
				AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
				if(StringUtils.equals(accrualRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE)) {
					// TODO: Redirect user to prompt stating excess leave will be forfeited and ask for confirmation.
					TkServiceLocator.getBalanceTransferService().submitToWorkflow(balanceTransfer);
					ActionRedirect redirect = new ActionRedirect();
					if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
						if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE) ||
								StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
							ActionForward forward = new ActionForward(mapping.findForward("success"));
							forward.setPath(forward.getPath()+"?documentId="+leaveCalendarDocumentId+"&action=R&methodToCall=approveLeaveCalendar");
							return forward;
						}
						//on demand handled in separate action forward.
					}

				} else {
					btf.setBalanceTransfer(balanceTransfer);
					btf.setTransferAmount(balanceTransfer.getTransferAmount());
					return mapping.findForward("basic");
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

}
