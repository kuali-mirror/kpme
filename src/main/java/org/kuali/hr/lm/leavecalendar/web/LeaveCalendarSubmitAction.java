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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.workflow.service.LeaveCalendarDocumentHeaderService;
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

import edu.emory.mathcs.backport.java.util.Collections;

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
        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
            	
        		Map<String,Set<LeaveBlock>> eligibilities = TkServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(document.getCalendarEntry(), document.getPrincipalId());
        		
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
                TkServiceLocator.getLeaveCalendarService().routeLeaveCalendar(TKContext.getTargetPrincipalId(), document);
            }
        } else if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.APPROVE)) {
            if (TkServiceLocator.getLeaveCalendarService().isReadyToApprove(document)) {
                if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                    TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
                }
            } else {
                //ERROR!!!!
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
            //Todo:  check for unfinalized BalanceTransfer on current leave calendar.
            if (TkServiceLocator.getLeaveCalendarService().isReadyToApprove(document)) {
                if (document.getDocumentHeader().getDocumentStatus().equals(DocumentStatus.ENROUTE.getCode())) {
                    TkServiceLocator.getLeaveCalendarService().approveLeaveCalendar(TKContext.getPrincipalId(), document);
                }
            } else {
                //ERROR!!!!
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
