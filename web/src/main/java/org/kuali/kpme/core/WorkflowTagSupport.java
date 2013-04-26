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
package org.kuali.kpme.core;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.document.CalendarDocumentHeaderContract;
import org.kuali.kpme.core.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.tklm.common.TKContext;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class WorkflowTagSupport {

    public String getTimesheetDocumentId() {
        return TKContext.getCurrentTimesheetDocumentId();
    }

    public String getLeaveCalendarDocumentId() {
        return TKContext.getCurrentLeaveCalendarDocumentId();
    }

    public boolean isDisplayingTimesheetRouteButton() {
    	return isDisplayingRouteButton(TKContext.getCurrentTimesheetDocument());
    }

    public boolean isDisplayingLeaveRouteButton() {
    	return isDisplayingRouteButton(TKContext.getCurrentLeaveCalendarDocument());
    }
    
    public boolean isDisplayingCurrentPeriodRouteButtonWithNoDelinquencies() {
    	LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
    	if (LocalDate.now().toDate().after(DateUtils.addMilliseconds(doc.getCalendarEntry().getBeginPeriodDate(),1)) &&
    			LocalDate.now().toDate().before(DateUtils.addMilliseconds(doc.getCalendarEntry().getEndPeriodDate(), -1))) {
    		if (!isDelinquent(doc) && isDisplayingRouteButton(doc)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    
    public boolean isDisplayingCurrentPeriodTimesheetRouteButtonWithNoDelinquencies() {
    	TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
    	if (LocalDate.now().toDate().after(DateUtils.addMilliseconds(doc.getCalendarEntry().getBeginPeriodDate(),1)) &&
    			LocalDate.now().toDate().before(DateUtils.addMilliseconds(doc.getCalendarEntry().getEndPeriodDate(), -1))) {
    		if (isDisplayingRouteButton(doc)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }

    private boolean isDisplayingRouteButton(TimesheetDocument timesheetDocument) {
    	boolean isDisplayingRouteButton = false;
    	
    	if (timesheetDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = timesheetDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
	        
	        if (ObjectUtils.equals(documentStatus, DocumentStatus.INITIATED) || ObjectUtils.equals(documentStatus, DocumentStatus.SAVED)) {
	        	isDisplayingRouteButton = TkServiceLocator.getTKPermissionService().canSubmitTimesheet(principalId, documentId);
	        }
    	}
    	
        return isDisplayingRouteButton;
    }
    
    private boolean isDisplayingRouteButton(LeaveCalendarDocument leaveCalendarDocument) {
    	boolean isDisplayingRouteButton = false;
    	
    	if (leaveCalendarDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = leaveCalendarDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
	        
	        if (ObjectUtils.equals(documentStatus, DocumentStatus.INITIATED) || ObjectUtils.equals(documentStatus, DocumentStatus.SAVED)) {
	        	isDisplayingRouteButton = LmServiceLocator.getLMPermissionService().canSubmitLeaveCalendar(principalId, documentId);
	        }
    	}
    	
        return isDisplayingRouteButton;
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteTimesheetButtonEnabled() {
        return isRouteButtonEnabled(TKContext.getCurrentTimesheetDocument());
    }

    /**
     * Disable the 'route' button if the document has already been routed.
     * @return true if the route button should render as enabled.
     */
    public boolean isRouteLeaveButtonEnabled() {
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
        return isRouteButtonEnabled(doc) && !isDelinquent(doc) 
        		&& (LmServiceLocator.getLMPermissionService().canViewLeaveTabsWithEStatus() && LocalDate.now().toDate().compareTo(doc.getDocumentHeader().getEndDate()) > 0);
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
    private boolean isDelinquent(LeaveCalendarDocument doc) {
        String principalId = doc.getDocumentHeader().getPrincipalId();
        List<LeaveCalendarDocumentHeader> lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getSubmissionDelinquentDocumentHeaders(principalId, new DateTime(doc.getAsOfDate()).plusSeconds(1));
        if (lcdh.isEmpty()){
            return false;        // no delinquncy
        } else
            return true;        // all previous leave document are final or enroute.
    }
    
    public boolean isDisplayingTimesheetApprovalButtons() {
        return isDisplayingApprovalButtons(TKContext.getCurrentTimesheetDocument());
    }

    public boolean isDisplayingLeaveApprovalButtons() {
        return isDisplayingApprovalButtons(TKContext.getCurrentLeaveCalendarDocument());
    }

    private boolean isDisplayingApprovalButtons(TimesheetDocument timesheetDocument) {
    	boolean isDisplayingApprovalButtons = false;
    	
    	if (timesheetDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = timesheetDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(timesheetDocument.getDocumentHeader().getDocumentStatus());
	        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(principalId, documentId);
	        
	        if (!ObjectUtils.equals(documentStatus, DocumentStatus.FINAL) && !tookActionAlready) {
	        	isDisplayingApprovalButtons = TkServiceLocator.getTKPermissionService().canApproveTimesheet(principalId, documentId)
	        			|| TkServiceLocator.getTKPermissionService().canSuperUserAdministerTimesheet(principalId, documentId);
	        }
    	}
    	
        return isDisplayingApprovalButtons;    
    }
    
    private boolean isDisplayingApprovalButtons(LeaveCalendarDocument leaveCalendarDocument) {
    	boolean isDisplayingApprovalButtons = false;
    	
    	if (leaveCalendarDocument != null) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    		String documentId = leaveCalendarDocument.getDocumentHeader().getDocumentId();
	        DocumentStatus documentStatus = DocumentStatus.fromCode(leaveCalendarDocument.getDocumentHeader().getDocumentStatus());
	        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(principalId, documentId);
	        
	        if (!ObjectUtils.equals(documentStatus, DocumentStatus.FINAL) && !tookActionAlready) {
	        	isDisplayingApprovalButtons = LmServiceLocator.getLMPermissionService().canApproveLeaveCalendar(principalId, documentId)
	        			|| LmServiceLocator.getLMPermissionService().canSuperUserAdministerLeaveCalendar(principalId, documentId);
	        }
    	}
    	
        return isDisplayingApprovalButtons;
    }

    public boolean isApprovalTimesheetButtonsEnabled() {
        TimesheetDocument doc = TKContext.getCurrentTimesheetDocument();
        return isApprovalButtonsEnabled(doc);
    }

    public boolean isApprovalLeaveButtonsEnabled() {
        LeaveCalendarDocument doc = TKContext.getCurrentLeaveCalendarDocument();
        return isApprovalButtonsEnabled(doc) && LmServiceLocator.getLeaveCalendarService().isReadyToApprove(doc);
    }

    private boolean isApprovalButtonsEnabled(CalendarDocumentContract doc) {
        CalendarDocumentHeaderContract dh = doc.getDocumentHeader();
        boolean isEnroute = StringUtils.isNotBlank(dh.getDocumentStatus()) && dh.getDocumentStatus().equals(HrConstants.ROUTE_STATUS.ENROUTE);
        if(isEnroute){
            DocumentRouteHeaderValue routeHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(dh.getDocumentId());
            boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(TKContext.getPrincipalId(), routeHeader, new SecuritySession(TKContext.getPrincipalId()));
            boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(TKContext.getPrincipalId(), dh.getDocumentId());
            return !tookActionAlready && authorized;
        }
        return false;
    }

    public String getRouteAction() { return HrConstants.DOCUMENT_ACTIONS.ROUTE; }
    public String getApproveAction() { return HrConstants.DOCUMENT_ACTIONS.APPROVE; }
    public String getDisapproveAction() { return HrConstants.DOCUMENT_ACTIONS.DISAPPROVE; }
}
