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

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class WorkflowTagSupport {

    public static boolean isTimesheetRouteButtonDisplaying(String documentId) {
    	boolean isTimesheetRouteButtonDisplaying = false;
    	
    	if (StringUtils.isNotBlank(documentId)) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
	        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(documentId);
	        
	        if (DocumentStatus.INITIATED.equals(documentStatus) || DocumentStatus.SAVED.equals(documentStatus)) {
	        	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
	        	isTimesheetRouteButtonDisplaying = HrServiceLocator.getHRPermissionService().canSubmitCalendarDocument(principalId, timesheetDocument);
	        }
    	}
    	
        return isTimesheetRouteButtonDisplaying;
    }
    
    public static boolean isLeaveCalendarRouteButtonDisplaying(String documentId) {
    	boolean isLeaveCalendarRouteButtonDisplaying = false;
    	
    	if (StringUtils.isNotBlank(documentId)) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
	        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(documentId);
	        
	        if (DocumentStatus.INITIATED.equals(documentStatus) || DocumentStatus.SAVED.equals(documentStatus)) {
	        	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
	        	isLeaveCalendarRouteButtonDisplaying = HrServiceLocator.getHRPermissionService().canSubmitCalendarDocument(principalId, leaveCalendarDocument);
	        }
    	}
    	
        return isLeaveCalendarRouteButtonDisplaying;
    }

    public static boolean isTimesheetRouteButtonEnabled(String documentId) {
        return isRouteButtonEnabled(documentId);
    }

    public static boolean isLeaveCalendarRouteButtonEnabled(String documentId) {
    	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
        return isRouteButtonEnabled(documentId) && isNotDelinquent(documentId) 
        		&& (HrServiceLocator.getHRPermissionService().canViewLeaveTabsWithEStatus() 
        		&& LocalDate.now().toDate().compareTo(leaveCalendarDocument.getDocumentHeader().getEndDate()) > 0);
    }

    private static boolean isNotDelinquent(String documentId) {
    	boolean isNotDelinquent = false;
    	
    	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
        if (leaveCalendarDocument != null) {
	    	String principalId = leaveCalendarDocument.getPrincipalId();
	        DateTime beforeDate = leaveCalendarDocument.getAsOfDate().toDateTimeAtStartOfDay().plusSeconds(1);
	        isNotDelinquent = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getSubmissionDelinquentDocumentHeaders(principalId, beforeDate).isEmpty();
        }
        
        return isNotDelinquent;
    }
    
    public static boolean isTimesheetApprovalButtonsDisplaying(String documentId) {
    	boolean isTimesheetApprovalButtonsDisplaying = false;
    	
    	if (StringUtils.isNotBlank(documentId)) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
	        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(documentId);
	        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(principalId, documentId);
	        
	        if (!DocumentStatus.FINAL.equals(documentStatus) && !tookActionAlready) {
	        	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
	        	isTimesheetApprovalButtonsDisplaying = HrServiceLocator.getHRPermissionService().canApproveCalendarDocument(principalId, timesheetDocument)
	        			|| HrServiceLocator.getHRPermissionService().canSuperUserAdministerCalendarDocument(principalId, timesheetDocument);
	        }
    	}
    	
        return isTimesheetApprovalButtonsDisplaying;    
    }

    public static boolean isLeaveCalendarApprovalButtonsDisplaying(String documentId) {
    	boolean isLeaveCalendarApprovalButtonsDisplaying = false;
    	
    	if (StringUtils.isNotBlank(documentId)) {
    		String principalId = GlobalVariables.getUserSession().getPrincipalId();
	        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(documentId);
	        boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(principalId, documentId);
	        
	        if (!DocumentStatus.FINAL.equals(documentStatus) && !tookActionAlready) {
	        	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
	        	isLeaveCalendarApprovalButtonsDisplaying = HrServiceLocator.getHRPermissionService().canApproveCalendarDocument(principalId, leaveCalendarDocument)
	        			|| HrServiceLocator.getHRPermissionService().canSuperUserAdministerCalendarDocument(principalId, leaveCalendarDocument);
	        }
    	}
    	
        return isLeaveCalendarApprovalButtonsDisplaying;
    }

    public static boolean isTimesheetApprovalButtonsEnabled(String documentId) {
        return isApprovalButtonsEnabled(documentId);
    }

    public static boolean isLeaveCalendarApprovalButtonsEnabled(String documentId) {
    	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
        return isApprovalButtonsEnabled(documentId) && LmServiceLocator.getLeaveCalendarService().isReadyToApprove(leaveCalendarDocument);
    }
    
    private static boolean isRouteButtonEnabled(String documentId) {
    	DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(documentId);
        return DocumentStatus.INITIATED.equals(documentStatus) || DocumentStatus.SAVED.equals(documentStatus);
    }

    private static boolean isApprovalButtonsEnabled(String documentId) {
    	boolean isApprovalButtonsEnabled = false;
    	
        DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(documentId);
        if (DocumentStatus.ENROUTE.equals(documentStatus)) {
            DocumentRouteHeaderValue routeHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentId);
            boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(HrContext.getPrincipalId(), routeHeader, new SecuritySession(HrContext.getPrincipalId()));
            boolean tookActionAlready = KEWServiceLocator.getActionTakenService().hasUserTakenAction(HrContext.getPrincipalId(), documentId);
            isApprovalButtonsEnabled = !tookActionAlready && authorized;
        }
        
        return isApprovalButtonsEnabled;
    }

}