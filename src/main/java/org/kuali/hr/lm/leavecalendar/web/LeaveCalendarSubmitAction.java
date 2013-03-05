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
        LeaveCalendarSubmitForm lcf = (LeaveCalendarSubmitForm)form;
        LeaveCalendarDocument document = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);

        // Switched to grab the target (chain, resolution: target -> backdoor -> actual) user.
        // Approvals still using backdoor > actual
        if (StringUtils.equals(action, TkConstants.DOCUMENT_ACTIONS.ROUTE)) {
            if (DocumentStatus.INITIATED.getCode().equals(document.getDocumentHeader().getDocumentStatus())
                    || DocumentStatus.SAVED.getCode().equals(document.getDocumentHeader().getDocumentStatus())) {
        		Map<String,Set<LeaveBlock>> eligibilities = TkServiceLocator.getBalanceTransferService().getNewEligibleTransfers(document.getCalendarEntry(), document.getPrincipalId());
        		List<LeaveBlock> eligibleTransfers = new ArrayList<LeaveBlock>();
        		eligibleTransfers.addAll(eligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE));
    			if(!eligibleTransfers.isEmpty()) {
					Collections.sort(eligibleTransfers, new Comparator() {
						
						@Override
						public int compare(Object o1, Object o2) {
							LeaveBlock l1 = (LeaveBlock) o1;
							LeaveBlock l2 = (LeaveBlock) o2;
							return l1.getLeaveDate().compareTo(l2.getLeaveDate());
						}
						
					});
            		int categoryCounter = 0;
    				StringBuilder sb = new StringBuilder();
            		ActionRedirect redirect = new ActionRedirect();
            		Interval interval = new Interval(document.getCalendarEntry().getBeginPeriodDate().getTime(), document.getCalendarEntry().getEndPeriodDate().getTime());
            		for(LeaveBlock lb : eligibleTransfers) {
            			//leave approve frequency max balance actions cannot happen without a leave block existing on the current
            			//calendar that exceeds the balance limit.
            			//the max balance action shall be handled within the calendar that contains the leave block!
            			if(interval.contains(lb.getLeaveDate().getTime()))
            				sb.append("&accrualCategory"+categoryCounter+"="+lb.getLmLeaveBlockId());
            		}
            		if(!StringUtils.isEmpty(sb.toString())) {
	            		redirect.setPath("/BalanceTransfer.do?"+request.getQueryString()+sb.toString());
	            		return redirect;
            		}
    			}
        		eligibleTransfers.clear();
        		eligibleTransfers.addAll(eligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END));
    			if(!eligibleTransfers.isEmpty()) {
					Collections.sort(eligibleTransfers, new Comparator() {
						
						@Override
						public int compare(Object o1, Object o2) {
							LeaveBlock l1 = (LeaveBlock) o1;
							LeaveBlock l2 = (LeaveBlock) o2;
							return l1.getLeaveDate().compareTo(l2.getLeaveDate());
						}
						
					});
            		int categoryCounter = 0;
    				StringBuilder sb = new StringBuilder();
            		ActionRedirect redirect = new ActionRedirect();
            		for(LeaveBlock lb : eligibleTransfers) {
            			//year end transfers are not dependent on whether or not the most recent leave block that exceeded the limit
            			//is contained within the calendar interval.
           				sb.append("&accrualCategory"+categoryCounter+"="+lb.getLmLeaveBlockId());
            		}
            		if(!StringUtils.isEmpty(sb.toString())) {
	            		redirect.setPath("/BalanceTransfer.do?"+request.getQueryString()+sb.toString());
	            		return redirect;
            		}
    			}
        		eligibilities = TkServiceLocator.getLeavePayoutService().getNewEligiblePayouts(document.getCalendarEntry(),document.getPrincipalId());
        		List<LeaveBlock> eligiblePayouts = new ArrayList<LeaveBlock>();
        		eligiblePayouts.addAll(eligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.LEAVE_APPROVE));
    			if(!eligiblePayouts.isEmpty()) {
					Collections.sort(eligiblePayouts, new Comparator() {
						
						@Override
						public int compare(Object o1, Object o2) {
							LeaveBlock l1 = (LeaveBlock) o1;
							LeaveBlock l2 = (LeaveBlock) o2;
							return l1.getLeaveDate().compareTo(l2.getLeaveDate());
						}
						
					});
    				int categoryCounter = 0;
            		StringBuilder sb = new StringBuilder();
            		ActionRedirect redirect = new ActionRedirect();
            		Interval interval = new Interval(document.getCalendarEntry().getBeginPeriodDate().getTime(), document.getCalendarEntry().getEndPeriodDate().getTime());
            		for(LeaveBlock lb : eligiblePayouts) {
            			//leave approve frequency max balance actions cannot happen without a leave block existing on the current
            			//calendar that exceeds the balance limit.
            			//the max balance action shall be handled within the calendar that contains the leave block!
            			if(interval.contains(lb.getLeaveDate().getTime()))
            				sb.append("&accrualCategory"+categoryCounter+"="+lb.getLmLeaveBlockId());
            		}
            		if(!StringUtils.isEmpty(sb.toString())) {
	            		redirect.setPath("/LeavePayout.do?"+request.getQueryString()+sb.toString());
	            		return redirect;
            		}
    			}
    			eligiblePayouts.clear();
        		eligiblePayouts.addAll(eligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END));
    			if(!eligiblePayouts.isEmpty()) {
					Collections.sort(eligiblePayouts, new Comparator() {
						
						@Override
						public int compare(Object o1, Object o2) {
							LeaveBlock l1 = (LeaveBlock) o1;
							LeaveBlock l2 = (LeaveBlock) o2;
							return l1.getLeaveDate().compareTo(l2.getLeaveDate());
						}
						
					});
    				int categoryCounter = 0;
            		StringBuilder sb = new StringBuilder();
            		ActionRedirect redirect = new ActionRedirect();
            		for(LeaveBlock lb : eligiblePayouts) {
            			//year end transfers are not dependent on whether or not the most recent leave block that exceeded the limit
            			//is contained within the calendar interval.
            			sb.append("&accrualCategory"+categoryCounter+"="+lb.getLmLeaveBlockId());
            		}
            		if(!StringUtils.isEmpty(sb.toString())) {
	            		redirect.setPath("/LeavePayout.do?"+request.getQueryString()+sb.toString());
	            		return redirect;
            		}
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
