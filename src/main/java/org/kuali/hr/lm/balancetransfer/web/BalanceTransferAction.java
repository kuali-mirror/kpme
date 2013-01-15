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

/**
 * TODO:
 * 
 * Apply only to current pay period on leave calendar?
 * When leave calendar has already been routed for approval/submitted, "Transfer" button for action frequency ON_DEMAND accrual categories should not be rendered.
 * 		What needs to be done if action frequency is on-demand, and the user disregards the transfer notifications; auto-lose? display dialog?
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
	 * @throws Exception 
	 */
	//rename to "transfer"
	//TODO: Route Balance Transfer Document, save document for records keeping, etc.
	//TODO: When user changes transfer amount derived during object creation, update forfeiture if applicable.
	//TODO: Write tests.
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
			AccrualCategoryRule accrualRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(accrualRuleId);
			String leaveCalendarDocumentId = balanceTransfer.getLeaveCalendarDocumentId();
			LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
			//throws exception
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
				effectiveDate = lcd.getCalendarEntry().getEndPeriodDate();
			LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(balanceTransfer.getPrincipalId(), lcd.getCalendarEntry());
			BalanceTransfer defaultBT = TkServiceLocator.getBalanceTransferService().initializeAccrualGeneratedBalanceTransfer(balanceTransfer.getPrincipalId(), accrualRuleId, ls, effectiveDate);
			if(balanceTransfer.getTransferAmount().compareTo(defaultBT.getTransferAmount()) != 0) {
				//employee changed the transfer amount. Transfer amount is NOT negative, and transfer amount is below the
				//adjusted maximum transfer amount for the accrual category. i.e., valid.
				//Must recalculate forfeiture.
				balanceTransfer = defaultBT.adjust(balanceTransfer.getTransferAmount());
			}
			//try/catch BalanceTransferException?
			//TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
			balanceTransfer.setStatus(TkConstants.ROUTE_STATUS.ENROUTE);
			//update status in BalanceTransferWorkFlowAttributes
			//move transfer to BalanceTransferWorkflowAttribute to ensure transfer does not occur unless the document was successfully routed.
			MaintenanceDocument document = KRADServiceLocatorWeb.getMaintenanceDocumentService().setupNewMaintenanceDocument(BalanceTransfer.class.getName(),
					"BalanceTransferDocumentType",KRADConstants.MAINTENANCE_NEW_ACTION);
			
			document.getDocumentHeader().setDocumentDescription("Accrual Triggered Transfer");
			Map<String,String[]> params = new HashMap<String,String[]>();
			
			KRADServiceLocatorWeb.getMaintenanceDocumentService().setupMaintenanceObject(document, KRADConstants.MAINTENANCE_NEW_ACTION, params);
			BalanceTransfer btObj = (BalanceTransfer) document.getNewMaintainableObject().getDataObject();
			
			btObj.setAccrualCategoryRule(balanceTransfer.getAccrualCategoryRule());
			btObj.setEffectiveDate(balanceTransfer.getEffectiveDate());
			btObj.setForfeitedAmount(balanceTransfer.getForfeitedAmount());
			btObj.setFromAccrualCategory(balanceTransfer.getFromAccrualCategory());
			btObj.setLeaveCalendarDocumentId(balanceTransfer.getLeaveCalendarDocumentId());
			btObj.setPrincipalId(balanceTransfer.getPrincipalId());
			btObj.setToAccrualCategory(balanceTransfer.getToAccrualCategory());
			btObj.setTransferAmount(balanceTransfer.getTransferAmount());
			
			//document.getOldMaintainableObject().setDataObject(btObj);
			//btObj = TkServiceLocator.getBalanceTransferService().transfer(btObj);
			document.getNewMaintainableObject().setDataObject(btObj);
			KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
			document.getDocumentHeader().getWorkflowDocument().saveDocument("");

			document.getDocumentHeader().getWorkflowDocument().route("");
			//catch workflow exception, return to calendar?
			//KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
			
			ActionRedirect redirect = new ActionRedirect();
			if(ObjectUtils.isNotNull(leaveCalendarDocumentId)) {
				if(StringUtils.equals(accrualRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)) {
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
			}
			else
				throw new RuntimeException("No rule for this accrual category could be found");
		}
		else
			throw new RuntimeException("No accrual category rule id has been sent in the request.");
		
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
		LeaveSummary ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());
		Date effectiveDate = TKUtils.getCurrentDate();
		if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
			effectiveDate = lcd.getCalendarEntry().getEndPeriodDate();
		BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeAccrualGeneratedBalanceTransfer(lcd.getPrincipalId(), accrualRuleId, ls, effectiveDate);

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
			categoryCounter++;
			transferableAccrualCategoryRules.add(accrualRuleId);
			accrualRuleId = request.getParameter("accrualCategory"+categoryCounter);
		}
		//This is the leave calendar document that triggered this balance transfer.
		String leaveCalendarDocumentId = request.getParameter("documentId");
		LeaveCalendarDocument lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(leaveCalendarDocumentId);
		LeaveSummary leaveSummary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(lcd.getPrincipalId(), lcd.getCalendarEntry());
		//Bad.... User must be prompted for each transfer that needs to be made.
		//For now, assuming not more than one accrual category is eligible for transfer.
		if(!transferableAccrualCategoryRules.isEmpty()) {
			Date effectiveDate = TKUtils.getCurrentDate();
			if(TKUtils.getCurrentDate().after(lcd.getCalendarEntry().getEndPeriodDate()))
				effectiveDate = lcd.getCalendarEntry().getEndPeriodDate();
			accrualRuleId = transferableAccrualCategoryRules.get(0);
			BalanceTransfer balanceTransfer = TkServiceLocator.getBalanceTransferService().initializeAccrualGeneratedBalanceTransfer(lcd.getPrincipalId(), accrualRuleId, leaveSummary, effectiveDate);
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
