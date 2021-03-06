/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.service.permission;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.HRPermissionService;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.util.KRADConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HRPermissionServiceImpl extends HrPermissionServiceBase implements HRPermissionService {
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
	}
	
	@Override
	public boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification, DateTime asOfDate) {
		return getPermissionService().isAuthorized(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), permissionName, qualification);
	}
	
    @Override
	public boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, DateTime asOfDate) {
		Map<String, String> qualification = new HashMap<String, String>();
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
	}
	
    @Override
	public boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification, DateTime asOfDate) {
		return getPermissionService().isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
	}
	
    @Override
    public boolean canApproveCalendarDocument(String principalId, CalendarDocumentContract calendarDocument) {
    	boolean canApproveLeaveCalendar = false;
    	
    	ValidActions validActions = KewApiServiceLocator.getWorkflowDocumentActionsService().determineValidActions(((CalendarDocument)calendarDocument).getDocumentId(), principalId);
    	
    	if (validActions.getValidActions() != null) {
    		canApproveLeaveCalendar = validActions.getValidActions().contains(ActionType.APPROVE);
    	}
    	
    	return canApproveLeaveCalendar;
    }
	
    @Override
    public boolean canViewCalendarDocument(String principalId, CalendarDocumentContract calendarDocument) {
    	return canSuperUserAdministerCalendarDocument(principalId, calendarDocument) 
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, (CalendarDocument)calendarDocument);
    }
    
    @Override
    public boolean canViewCalendarDocumentAssignment(String principalId, CalendarDocumentContract calendarDocument, Assignment assignment) {
    	return canSuperUserAdministerCalendarDocument(principalId, (CalendarDocument)calendarDocument)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, (CalendarDocument)calendarDocument, assignment);
    }
    
    @Override
    public boolean canEditCalendarDocument(String principalId, CalendarDocumentContract calendarDocument) {
    	return canSuperUserAdministerCalendarDocument(principalId, (CalendarDocument)calendarDocument) 
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, (CalendarDocument)calendarDocument);
    }
    
    @Override
    public boolean canEditCalendarDocumentAssignment(String principalId, CalendarDocumentContract calendarDocument, Assignment assignment) {
    	return canSuperUserAdministerCalendarDocument(principalId, (CalendarDocument)calendarDocument)
    			|| isAuthorizedByTemplate(principalId, KRADConstants.KNS_NAMESPACE, KimConstants.PermissionTemplateNames.EDIT_DOCUMENT, (CalendarDocument)calendarDocument, assignment);
    }
    
    @Override
    public boolean canSubmitCalendarDocument(String principalId, CalendarDocumentContract calendarDocument) {
        return canSuperUserAdministerCalendarDocument(principalId, (CalendarDocument)calendarDocument) 
        		|| isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT, (CalendarDocument)calendarDocument);
    }
    
    @Override
    public boolean canSuperUserAdministerCalendarDocument(String principalId, CalendarDocumentContract calendarDocument) {
        return isAuthorizedByTemplate(principalId, KRADConstants.KUALI_RICE_WORKFLOW_NAMESPACE, "Administer Routing for Document", (CalendarDocument)calendarDocument);
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, CalendarDocument calendarDocument) {
    	boolean isAuthorizedByTemplate = false;

    	if (calendarDocument != null) {
    		String documentTypeName = calendarDocument.getCalendarType();
        	DocumentStatus documentStatus = DocumentStatus.fromCode(calendarDocument.getDocumentHeader().getDocumentStatus());
            List<Assignment> assignments = new ArrayList<Assignment>();
            if (permissionTemplateName.equals(KimConstants.PermissionTemplateNames.EDIT_DOCUMENT)
                    || permissionTemplateName.equals(KimConstants.PermissionTemplateNames.ROUTE_DOCUMENT)) {
                //only allows routing/editing of timesheets with active assignments in the pay period
                LocalDate startDate = calendarDocument.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate();
                LocalDate endDate = calendarDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate();
                List<Assignment> assignmentsActiveInCalendarEntry= HrServiceLocator.getAssignmentService().getRecentAssignmentsBetweenDays(calendarDocument.getPrincipalId(), startDate, endDate);
                for (Assignment assignment : calendarDocument.getAllAssignments()) {
                    if (assignmentsActiveInCalendarEntry.contains(assignment)) {
                        assignments.add(assignment);
                    }
                }
            } else {
                assignments.addAll(calendarDocument.getAllAssignments());
            }
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, calendarDocument.getDocumentId(), documentStatus, assignments, calendarDocument.getCalendarEntry().getEndPeriodFullDateTime());
    	}
    	
    	return isAuthorizedByTemplate;
    }
    
    private boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, CalendarDocument calendarDocument, Assignment assignment) {
    	boolean isAuthorizedByTemplate = false;

    	if (calendarDocument != null) {
    		String documentTypeName = calendarDocument.getCalendarType();
        	DocumentStatus documentStatus = DocumentStatus.fromCode(calendarDocument.getDocumentHeader().getDocumentStatus());
        	
        	isAuthorizedByTemplate = isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, calendarDocument.getDocumentId(), documentStatus, assignment, calendarDocument.getCalendarEntry().getEndPeriodFullDateTime());
    	}
    	
    	return isAuthorizedByTemplate;
    }

    @Override
    public boolean canViewTimeTabs() {
    	boolean canViewTimeTabs = false;
    	LocalDate asOfDate = LocalDate.now();
    	String flsaStatus = HrConstants.FLSA_STATUS_NON_EXEMPT;
    	// find active assignments as of currentDate
    	String principalId = HrContext.getTargetPrincipalId();
    	if(isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, false)) {
    		//find timecalendar defined
    		canViewTimeTabs = isCalendarDefined("payCalendar", principalId, asOfDate, false);
    	}
    	return canViewTimeTabs;
    }
    
    private boolean isActiveAssignmentFoundOnJobFlsaStatus(String principalId, String flsaStatus, boolean chkForLeaveEligible) {
    	boolean isActiveAssFound = false;
    	LocalDate asOfDate = LocalDate.now();
     	List<Assignment> activeAssignments = HrServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);
     	if(activeAssignments != null && !activeAssignments.isEmpty()) {
     		for(Assignment assignment : activeAssignments) {
     			if(assignment != null && assignment.getJob() != null && assignment.getJob().getFlsaStatus() != null && assignment.getJob().getFlsaStatus().equalsIgnoreCase(flsaStatus)) {
     				if(chkForLeaveEligible) {
     					isActiveAssFound = assignment.getJob().isEligibleForLeave();
     					if(!isActiveAssFound){
     						continue;
     					}
     				}
     				isActiveAssFound = true;
     				break;
     			}  
     		}
     	}
    	return isActiveAssFound;
    }
    
    private boolean isCalendarDefined(String calendarType, String principalId, LocalDate asOfDate, boolean chkForLeavePlan){
    	boolean calDefined = false;
    	PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
    	if(principalHRAttributes != null) {
    		if(calendarType.equalsIgnoreCase("payCalendar")) {
    			calDefined = principalHRAttributes.getPayCalendar() != null ? true : false;
    		} else if(calendarType.equalsIgnoreCase("leaveCalendar")) {
    			calDefined = principalHRAttributes.getLeaveCalendar() != null ? true : false;
    			if(calDefined && chkForLeavePlan) {
    				calDefined = principalHRAttributes.getLeavePlan() != null ? true : false;
    			}
    		} 
    	}
    	return calDefined;
    }
    

    
    @Override
    public boolean canViewLeaveTabsWithEStatus() {
    	boolean canViewLeaveTabs = false;
    	String principalId = HrContext.getTargetPrincipalId();
    	LocalDate asOfDate = LocalDate.now();
    	boolean leaveCalNPlanDefined = isCalendarDefined("leaveCalendar", principalId, asOfDate, true);
    	String flsaStatus = HrConstants.FLSA_STATUS_EXEMPT;
    	boolean activeAss = isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, true);
    	canViewLeaveTabs = activeAss && leaveCalNPlanDefined;
    	return canViewLeaveTabs;
    }
    
    @Override
    public boolean canViewLeaveTabsWithNEStatus() {
    	boolean canViewLeaveTabs = false;
    	LocalDate asOfDate = LocalDate.now();
    	String flsaStatus = HrConstants.FLSA_STATUS_NON_EXEMPT;
    	// find active assignments as of currentDate
    	String principalId = HrContext.getTargetPrincipalId();
    	boolean activeAss = isActiveAssignmentFoundOnJobFlsaStatus(principalId, flsaStatus, true);
    	// chk leave plan defined
    	boolean leaveCalNPlanDefined = isCalendarDefined("leaveCalendar", principalId, asOfDate, true);
    	boolean timeCalDefined = isCalendarDefined("payCalendar", principalId, asOfDate, false);
    	canViewLeaveTabs = activeAss && leaveCalNPlanDefined && timeCalDefined;
    	return canViewLeaveTabs;
    }

    @Override
    public CalendarBlockPermissions getTimeBlockPermissions(String blockId) {
        return new CalendarBlockPermissions(blockId);
    }

    @Override
    public CalendarBlockPermissions updateTimeBlockPermissions(CalendarBlockPermissions calendarBlockPermissions) {
        return calendarBlockPermissions;
    }

    @Override
    public CalendarBlockPermissions getLeaveBlockPermissions(String blockId) {
        return new CalendarBlockPermissions(blockId);
    }

    @Override
    public CalendarBlockPermissions updateLeaveBlockPermissions(CalendarBlockPermissions calendarBlockPermissions) {
        return calendarBlockPermissions;
    }

	@Override
	public boolean canViewEdossierTabs() {
		return true;
	}
	
}