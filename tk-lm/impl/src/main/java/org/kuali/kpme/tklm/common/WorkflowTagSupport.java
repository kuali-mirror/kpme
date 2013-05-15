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
package org.kuali.kpme.tklm.common;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class WorkflowTagSupport {

    public String getTimesheetDocumentId() {
        return HrContext.getCurrentTimesheetDocumentId();
    }

    public String getLeaveCalendarDocumentId() {
        return HrContext.getCurrentLeaveCalendarDocumentId();
    }

    public boolean isDisplayingTimesheetRouteButton() {
    	return isDisplayingTimesheetRouteButton(HrContext.getCurrentTimesheetDocument());
    }

    public boolean isDisplayingLeaveRouteButton() {
    	return isDisplayingLeaveRouteButton(HrContext.getCurrentLeaveCalendarDocument());
    }
    
    public boolean isDisplayingCurrentPeriodRouteButtonWithNoDelinquencies() {
    	CalendarDocument doc = HrContext.getCurrentLeaveCalendarDocument();
    	if (LocalDate.now().toDate().after(DateUtils.addMilliseconds(doc.getCalendarEntry().getBeginPeriodDate(),1)) &&
    			LocalDate.now().toDate().before(DateUtils.addMilliseconds(doc.getCalendarEntry().getEndPeriodDate(), -1))) {
    		if (!isDelinquent(doc) && isDisplayingLeaveRouteButton(doc)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    
    public boolean isDisplayingCurrentPeriodTimesheetRouteButtonWithNoDelinquencies() {
    	CalendarDocument doc = HrContext.getCurrentTimesheetDocument();
    	if (LocalDate.now().toDate().after(DateUtils.addMilliseconds(doc.getCalendarEntry().getBeginPeriodDate(),1)) &&
    			LocalDate.now().toDate().before(DateUtils.addMilliseconds(doc.getCalendarEntry().getEndPeriodDate(), -1))) {
    		if (isDisplayingTimesheetRouteButton(doc)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }

    private boolean isDisplayingTimesheetRouteButton(CalendarDocument calendarDocument) {
    	boolean isDisplayingRouteButton = false;
    	
    	if (calendarDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = calendarDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(calendarDocument.getDocumentHeader().getDocumentStatus());
	        
	        if (ObjectUtils.equals(documentStatus, DocumentStatus.INITIATED) || ObjectUtils.equals(documentStatus, DocumentStatus.SAVED)) {
	        	isDisplayingRouteButton = HrServiceLocator.getHRPermissionService().canSubmitCalendarDocument(principalId, calendarDocument);
	        }
    	}
    	
        return isDisplayingRouteButton;
    }
    
    private boolean isDisplayingLeaveRouteButton(CalendarDocument leaveCalendarDocument) {
    	boolean isDisplayingRouteButton = false;
    	
    	if (leaveCalendarDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = leaveCalendarDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
	        
	        if (ObjectUtils.equals(documentStatus, DocumentStatus.INITIATED) || ObjectUtils.equals(documentStatus, DocumentStatus.SAVED)) {
	        	isDisplayingRouteButton = HrServiceLocator.getHRPermissionService().canSubmitCalendarDocument(principalId, leaveCalendarDocument);
	        }
    	}
    	
        return isDisplayingRouteButton;
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteTimesheetButtonEnabled() {
        return isRouteButtonEnabled(HrContext.getCurrentTimesheetDocument());
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteLeaveButtonEnabled() {
        CalendarDocument doc = HrContext.getCurrentLeaveCalendarDocument();
        return isRouteButtonEnabled(doc) && !isDelinquent(doc) 
        		&& (HrServiceLocator.getHRPermissionService().canViewLeaveTabsWithEStatus() && LocalDate.now().toDate().compareTo(doc.getDocumentHeader().getEndDate()) > 0);
    }

    private boolean isRouteButtonEnabled(CalendarDocumentContract doc) {
        CalendarDocumentHeaderContract dh = doc.getDocumentHeader();
        return dh.getDocumentStatus().equals(HrConstants.ROUTE_STATUS.INITIATED)
                || dh.getDocumentStatus().equals(HrConstants.ROUTE_STATUS.SAVED);
    }

    /**
     * checks for delinquncies in target ee's calendar.
     * @param doc
     * @return true if there are previous non-routed or non-final documents
     */
    private boolean isDelinquent(CalendarDocument doc) {
        String principalId = doc.getPrincipalId();
        if (LmServiceLocator.getLeaveCalendarDocumentHeaderService().getSubmissionDelinquentDocumentHeaders(principalId, doc.getAsOfDate().toDateTimeAtStartOfDay().plusSeconds(1)).isEmpty()){
        	//HrServiceLocator.getCalendarDocumentHeaderService()??
            return false;        // no delinquncy
        } else
            return true;        // all previous leave document are final or enroute.
    }
    
    public boolean isDisplayingTimesheetApprovalButtons() {
        return isDisplayingTimesheetApprovalButtons(HrContext.getCurrentTimesheetDocument());
    }

    public boolean isDisplayingLeaveApprovalButtons() {
        return isDisplayingApprovalButtons(HrContext.getCurrentLeaveCalendarDocument());
    }

    private boolean isDisplayingTimesheetApprovalButtons(CalendarDocument timesheetDocument) {
    	boolean isDisplayingApprovalButtons = false;
    	
    	if (timesheetDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = timesheetDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
	        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(principalId, documentId);
	        
	        if (!ObjectUtils.equals(documentStatus, DocumentStatus.FINAL) && !tookActionAlready) {
	        	isDisplayingApprovalButtons = HrServiceLocator.getHRPermissionService().canApproveCalendarDocument(principalId, timesheetDocument)
	        			|| HrServiceLocator.getHRPermissionService().canSuperUserAdministerCalendarDocument(principalId, timesheetDocument);
	        }
    	}
    	
        return isDisplayingApprovalButtons;    
    }
    
    private boolean isDisplayingApprovalButtons(CalendarDocument leaveCalendarDocument) {
    	boolean isDisplayingApprovalButtons = false;
    	
    	if (leaveCalendarDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = leaveCalendarDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
	        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(principalId, documentId);
	        
	        if (!ObjectUtils.equals(documentStatus, DocumentStatus.FINAL) && !tookActionAlready) {
	        	isDisplayingApprovalButtons = HrServiceLocator.getHRPermissionService().canApproveCalendarDocument(principalId, leaveCalendarDocument)
	        			|| HrServiceLocator.getHRPermissionService().canSuperUserAdministerCalendarDocument(principalId, leaveCalendarDocument);
	        }
    	}
    	
        return isDisplayingApprovalButtons;
    }

    public boolean isApprovalTimesheetButtonsEnabled() {
        CalendarDocument doc = HrContext.getCurrentTimesheetDocument();
        return isApprovalButtonsEnabled(doc);
    }

    public boolean isApprovalLeaveButtonsEnabled() {
        CalendarDocument doc = HrContext.getCurrentLeaveCalendarDocument();
        return isApprovalButtonsEnabled(doc) && LmServiceLocator.getLeaveCalendarService().isReadyToApprove(doc);
    }

    private boolean isApprovalButtonsEnabled(CalendarDocumentContract doc) {
        CalendarDocumentHeaderContract dh = doc.getDocumentHeader();
        boolean isEnroute = StringUtils.isNotBlank(dh.getDocumentStatus()) && dh.getDocumentStatus().equals(HrConstants.ROUTE_STATUS.ENROUTE);
        if(isEnroute){
            DocumentRouteHeaderValue routeHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(dh.getDocumentId());
            boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(HrContext.getPrincipalId(), routeHeader, new SecuritySession(HrContext.getPrincipalId()));
            boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(HrContext.getPrincipalId(), dh.getDocumentId());
            return !tookActionAlready && authorized;
        }
        return false;
    }

    public String getRouteAction() { return HrConstants.DOCUMENT_ACTIONS.ROUTE; }
    public String getApproveAction() { return HrConstants.DOCUMENT_ACTIONS.APPROVE; }
    public String getDisapproveAction() { return HrConstants.DOCUMENT_ACTIONS.DISAPPROVE; }
}
