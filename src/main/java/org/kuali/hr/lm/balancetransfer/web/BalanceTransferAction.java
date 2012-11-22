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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;

public class BalanceTransferAction extends TkAction {


	public ActionForward docHandler(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		// if transfer type is transfer, forward to balancetransfer
		// if transfer type is payout, forward to payout.
		
		return mapping.findForward("basic");
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
	
	public ActionForward balanceTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		return mapping.findForward("basic");
	}
	
	public ActionForward balancePayout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		return mapping.findForward("basic");
	}

/*	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return super.save(mapping, form, request, response);
	}

	@Override
	public ActionForward route(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return super.route(mapping, form, request, response);
	}*/

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		// Will be used to populate the form.
		return super.execute(mapping, form, request, response);
	}

	public ActionForward someAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("basic");
	}

}
