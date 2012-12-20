/**
 * Copyright 2004-2012 The Kuali Foundation
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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.balancetransfer.validation.BalanceTransferValidationUtils;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.web.LeaveCalendarSubmitForm;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

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
	public ActionForward balanceTransferOnLeaveApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//if action was submit, execute the transfer
		BalanceTransferForm btf = (BalanceTransferForm) form;

		//This instance should have been instantiated during this Action classes 
		BalanceTransfer balanceTransfer = btf.getBalanceTransfer();
		boolean valid = BalanceTransferValidationUtils.validateTransfer(balanceTransfer);
		//TODO: Javascript notification if transfer amount was changed, and newly entered
		//transfer amount would produce forfeiture.

		if(valid) {
			AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());
			BigDecimal maxBalance = accrualRule.getMaxBalance();
			
			TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
			String leaveCalendarDocumentId = balanceTransfer.getLeaveCalendarDocumentId();
			ActionRedirect redirect = new ActionRedirect();
			
			redirect.setPath(mapping.findForward("success").getPath());
	
			if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
				redirect.addParameter("documentId", leaveCalendarDocumentId);
				redirect.addParameter("action",TkConstants.DOCUMENT_ACTIONS.ROUTE);
				redirect.addParameter("methodToCall","approveLeaveCalendar");
				return redirect;
			}
		}
		
		return mapping.findForward("basic");
		//TkServiceLocator.getBalanceTransferService().transfer(bt);
		//Update query string, remove accrualCategory parameter that triggered this action
		//decrement any additional accrualCategory parameters that exist in query string.
		//pass control back to execute.
		//if action was cancel, return user to LeaveCalendar.do
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("cancel");
	}
	
	/*	
	@Override
	public ActionForward promptBeforeValidation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String methodToCall) throws Exception {
		// TODO Auto-generated method stub
		// Need to prompt user for any reason?
		// Accrual Category Max Balance triggers a warning message on leave calendar, and provides a button "Transfer"
		// to user. All values except for transfer amount are read-only and can be derived from the accrual category rule.
		// User clicks button, and is prompted "Transfer X from Y to Z?" Confirm -> Action transfer. No -> Exit. Edit -> Action Edit
		// Transfer Amount. On submit, if applicable, "N hours have been forfeited".
		return mapping.findForward("confirmationPrompt");
	}*/

/*	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("***********************");
		System.out.println("* Action Forward Save *");
		System.out.println("***********************");
		return super.save(mapping, form, request, response);
	}

	@Override
	public ActionForward route(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("************************");
		System.out.println("* Action Forward Route *");
		System.out.println("************************");
		return super.route(mapping, form, request, response);
	}*/

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
			BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().getTransferOnLeaveApprove(lcd.getPrincipalId(), accrualRuleId, ls);
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
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);

		return forward;
		//request.setAttribute("methodToCall", "balanceTransferOnLeaveApproval");
		//Form for initial transfer is in place.
		//return super.execute(mapping, form, request, response);
	}

}
