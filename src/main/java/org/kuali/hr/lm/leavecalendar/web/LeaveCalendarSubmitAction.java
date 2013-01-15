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
package org.kuali.hr.lm.leavecalendar.web;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LeaveCalendarSubmitAction extends TkAction {
    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;

        String principal = TKContext.getPrincipalId();
        UserRoles roles = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId());

        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId());
        if (!roles.isDocumentWritable(document)) {
            throw new AuthorizationException(principal, "LeaveCalendarSubmitAction", "");
        }
    }
    
    public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String documentId = request.getParameter("documentId");
    	String action = request.getParameter("action");
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;
        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
            	
            	List<String> leaveApproveIds = TkServiceLocator.getBalanceTransferService().getAccrualCategoryRuleIdsForEligibleTransfers(document,
            			LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE);
            	leaveApproveIds.addAll(TkServiceLocator.getBalanceTransferService().getAccrualCategoryRuleIdsForEligibleTransfers(document,
            			LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END));
            	//Waterfall transfers? What order do transfers occur?
            	//ACTION_AT_MAX_BALANCE == LOSE
            	//if prompting the user to submit forfeiture. Transfer amount field must be locked, read only.
            	//
            	if(!leaveApproveIds.isEmpty()) {
            		//There exist accrual categories that have exceeded their maximum balance
            		//It is required that if a max balance limit exists for any particular accrual category with frequency LEAVE_APPROVE
            		//and if the balance for such an accrual category exceeds that limit at the time this function
            		//is called, the balance on that accrual category MUST be brought back to, AT MOST, its limit.
            		//This conditional need not append every accrual category over max balance to its request string.
            		//If there exists more than one accrual category that triggers a transfer on LEAVE_APPROVE
            		//Each one shall be handled in its own redirect to BalanceTransfer.do.
            		//Should it be the case that the transfer fails, or is otherwise prevented from executing,
            		//as it currently stands, an infinite redirect loop could occur.
            		//More work needs to be done in order to ensure or at least increase the likely hood that this doesn't occur.
            		StringBuilder sb = new StringBuilder();
            		int categoryCounter = 0;
            		ActionRedirect redirect = new ActionRedirect();

            		for(String accrualRuleId : leaveApproveIds) {
            			sb.append("&accrualCategory"+categoryCounter+"="+accrualRuleId);
            		}
            		redirect.setPath("/BalanceTransfer.do?"+request.getQueryString()+sb.toString());
            		return redirect;
            	}

                TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        } else if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getLeaveCalendarService().disapproveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        }
        ActionRedirect rd = new ActionRedirect(mapping.findForward("leaveCalendarRedirect"));
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(document, document.getAsOfDate());
        rd.addParameter("documentId", documentId);

        return rd;
    }

    public ActionForward approveApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;
        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.INITIATED.getCode())) {
                TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), TkConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                TkServiceLocator.getLeaveCalendarService().disapproveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        }

        TKContext.getUser().clearTargetUser();
        return new ActionRedirect(mapping.findForward("approverRedirect"));

    }

}
