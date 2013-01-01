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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.ObjectUtils;

/**
 * TODO:
 * 
 * Apply only to current pay period on leave calendar.
 * When leave calendar has already been routed for approval/submitted, "Transfer" button for action frequency ON_DEMAND accrual categories should not be rendered.
 * 		What needs to be done if action frequency is on-demand, and the user disregards the transfer notifications; auto-lose? display dialog?
 * ADD warning messages on leave calendar when an accrual category has exceeded its rules defined max balance.
 * 		 
 * @author dgodfrey
 *
 */
public class BalanceTransferAction extends TkAction {

	/**
	 * ActionForward called when user submits balance transfer document.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	//rename to "transfer"
	//TODO: Route Balance Transfer Document, save document for records keeping, etc.
	//TODO: When user changes transfer amount derived during object creation, update forfeiture if applicable.
	//TODO: Write tests.
	public ActionForward balanceTransferOnLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//if action was submit, execute the transfer
		BalanceTransferForm btf = (BalanceTransferForm) form;

		BalanceTransfer balanceTransfer = btf.getBalanceTransfer();
		boolean valid = BalanceTransferValidationUtils.validateTransfer(balanceTransfer);
		
		//if transfer amount has changed, and the resulting change produces forfeiture
		//or changes the forfeiture amount, prompt for confirmation with the amount of
		//forfeiture that the entered amount would produce.

		if(valid) {
			String accrualRuleId = balanceTransfer.getAccrualCategoryRule();

			AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			
			//try/catch BalanceTransferException?
			TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
			
			String leaveCalendarDocumentId = balanceTransfer.getLeaveCalendarDocumentId();
			ActionRedirect redirect = new ActionRedirect();
			if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
					redirect.setPath(mapping.findForward("success").getPath());
					redirect.addParameter("documentId", leaveCalendarDocumentId);
					redirect.addParameter("action",TkConstants.DOCUMENT_ACTIONS.ROUTE);
					redirect.addParameter("methodToCall","approveLeaveCalendar");
					return redirect;
				}
				else
					return mapping.findForward("closeBalanceTransferDoc");
			}
			else
				return mapping.findForward("closeBalanceTransferDoc");
		}
		else
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
			if(StringUtils.equals(actionFrequency, LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
				ActionRedirect redirect = new ActionRedirect();
				redirect.setPath(mapping.findForward("cancel").getPath());
				redirect.addParameter("documentId", btf.getLeaveCalendarDocumentId());
		
				return redirect;
			}
			else
				throw new RuntimeException("Action should only be reachable through triggers with frequency ON_DEMAND or LEAVE_APPROVE");
	}
	
	public ActionForward balanceTransferOnDemand(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BalanceTransferForm btf = (BalanceTransferForm) form;
		//thee leave calendar document that triggered this balance transfer.
		String leaveCalendarDocumentId = request.getParameter("documentId");
		String accrualRuleId = request.getParameter("accrualRuleId");
		if(ObjectUtils.isNotNull(accrualRuleId)) {
			AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			if(ObjectUtils.isNotNull(aRule)) {
				//should somewhat safegaurd against url fabrication.
				if(!StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND))
					throw new RuntimeException("attempted to execute on-demand balance transfer for accrual category with action frequency " + aRule.getMaxBalanceActionFrequency());
			}
			else
				throw new RuntimeException("No rule for this accrual category could be found");
		}
		else
			throw new RuntimeException("No accrual category rule id has been sent in the request.");
		
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
		LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());

		BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeAccrualGeneratedBalanceTransfer(lcd.getPrincipalId(), accrualRuleId, ls);

		btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
		if(ObjectUtils.isNotNull(balanceTransfer)) {
			balanceTransfer.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
			btf.setBalanceTransfer(balanceTransfer);
			btf.setTransferAmount(balanceTransfer.getTransferAmount());
		}
		else {
			//put error and send back to leave calendar?
			//display error, render only cancel button?
		}
		//return mapping.findForward("basic");

		return mapping.findForward("basic");
	}

	//TODO: Rename method to differentiate from ActionForward with same name in LeaveCalendarSubmit.
	public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BalanceTransferForm btf = (BalanceTransferForm) form;
		
		int categoryCounter = 0;
		List<String> transferableAccrualCategoryRules = new ArrayList<String>();
		String accrualRuleId = request.getParameter("accrualCategory0");
		while(ObjectUtils.isNotNull(accrualRuleId)) {
			categoryCounter++;
			transferableAccrualCategoryRules.add(accrualRuleId);
			accrualRuleId = request.getParameter("accrualCategory"+categoryCounter);
		}
		//This is the leave calendar document that triggered this balance transfer.
		String leaveCalendarDocumentId = request.getParameter("documentId");
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
		LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());
		//Bad.... User must be prompted for each transfer that needs to be made.
		//For now, assuming not more than one accrual category is eligible for transfer.
		if(!transferableAccrualCategoryRules.isEmpty()) {
			accrualRuleId = transferableAccrualCategoryRules.get(0);
			BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeAccrualGeneratedBalanceTransfer(lcd.getPrincipalId(), accrualRuleId, ls);
			btf.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
			if(ObjectUtils.isNotNull(balanceTransfer)) {
				balanceTransfer.setLeaveCalendarDocumentId(leaveCalendarDocumentId);
				btf.setBalanceTransfer(balanceTransfer);
				btf.setTransferAmount(balanceTransfer.getTransferAmount());
			}
			else {
				//put error and send back to leave calendar?
				//display error, render only cancel button?
			}
			//return mapping.findForward("basic");
		}

		return mapping.findForward("basic");
	}
	
	public ActionForward closeBalanceTransferDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("closeBalanceTransferDoc");
	}

}
