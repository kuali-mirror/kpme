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
package org.kuali.hr.tklm.leave.calendar.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.Interval;
import org.kuali.hr.core.HrConstants;
import org.kuali.hr.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.web.HrAction;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;


public class LeaveCalendarSubmitAction extends HrAction {
    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm) form;

        String principalId = GlobalVariables.getUserSession().getPrincipalId();
        String documentId = lcf.getDocumentId();
        
        if (!LmServiceLocator.getLMPermissionService().canEditLeaveCalendar(principalId, documentId)) {
            throw new AuthorizationException(principalId, "LeaveCalendarSubmitAction", "");
        }
    }
    
    public ActionForward approveLeaveCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String documentId = request.getParameter("documentId");
    	String action = request.getParameter("action");
        LeaveCalendarDocument document = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
            	
        		Map<String,Set<LeaveBlock>> eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(document.getCalendarEntry(), document.getPrincipalId());
        		
        		ActionRedirect transferRedirect = new ActionRedirect();
        		ActionRedirect payoutRedirect = new ActionRedirect();
				
				List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
				List<LeaveBlock> eligiblePayouts = new ArrayList<LeaveBlock>();
        		Interval interval = new Interval(document.getCalendarEntry().getBeginPeriodDate().getTime(), document.getCalendarEntry().getEndPeriodDate().getTime());
        		for(Entry<String,Set<LeaveBlock>> entry : eligibilities.entrySet()) {
        			
            		for(LeaveBlock lb : entry.getValue()) {
            			if(interval.contains(lb.getLeaveDate().getTime())) {
	            			//maxBalanceViolations should, if a violation exists, return a leave block with leave date either current date, or the end period date - 1 days.
                    		PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(document.getPrincipalId(), lb.getLeaveLocalDate());
	        				AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
	
	            			if(ObjectUtils.isNotNull(aRule)
	            					&& !StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND)) {
	            				if(StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE)
	            						|| (StringUtils.equals(aRule.getMaxBalanceActionFrequency(),LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)
	            								&& HrServiceLocator.getLeavePlanService().isLastCalendarPeriodOfLeavePlan(document.getCalendarEntry(),pha.getLeavePlan(),lb.getLeaveLocalDate())))
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
                LmServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
            if (LmServiceLocator.getLeaveCalendarService().isReadyToApprove(document)) {
                if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                    LmServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
                }
            } else {
                //ERROR!!!!
            }
        } else if (StringUtils.equals(action, HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                LmServiceLocator.getLeaveCalendarService().disapproveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        }
        ActionRedirect rd = new ActionRedirect(mapping.findForward("leaveCalendarRedirect"));
        TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(document, document.getAsOfDate());
        rd.addParameter("documentId", documentId);

        return rd;
    }

    public ActionForward approveApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;
        LeaveCalendarDocument document = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(lcf.getDocumentId());

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(lcf.getAction(), HrConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.INITIATED.getCode())) {
                LmServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(lcf.getAction(), HrConstants.DOCUMENT_ACTIONS.APPROVE)) {
            //Todo:  check for unfinalized BalanceTransfer on current leave calendar.
            if (LmServiceLocator.getLeaveCalendarService().isReadyToApprove(document)) {
                if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                    LmServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
                }
            } else {
                //ERROR!!!!
            }

        } else if (StringUtils.equals(lcf.getAction(), HrConstants.DOCUMENT_ACTIONS.DISAPPROVE)) {
            if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                LmServiceLocator.getLeaveCalendarService().disapproveLeaveCalendar(TKContext.getPrincipalId(), document);
            }
        }

        TKContext.clearTargetUser();
        return new ActionRedirect(mapping.findForward("approverRedirect"));

    }

}
