package org.kuali.kpme.core.service.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.rice.kim.api.permission.PermissionService;

public class HRPermissionServiceImpl extends HrPermissionServiceBase implements HRPermissionService {
	
	private PermissionService permissionService;
	
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
	
	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
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
    	String flsaStatus = TkConstants.FLSA_STATUS_EXEMPT;
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
	
}