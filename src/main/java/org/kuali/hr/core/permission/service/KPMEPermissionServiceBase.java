package org.kuali.hr.core.permission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.core.permission.KPMEDocumentStatus;
import org.kuali.hr.core.permission.KPMEPermissionTemplateAttribute;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.service.DepartmentService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;

public abstract class KPMEPermissionServiceBase {
	
	public abstract boolean isSystemUser(String principalId);
	
	public abstract boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification);
		
	public abstract boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification);
	
    public boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
		return isAuthorized(principalId, permissionName, qualification);
    }
    
    public boolean isAuthorizedInDepartment(String principalId, String permissionName, String department) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
		return isAuthorized(principalId, permissionName, qualification);
    }
    
    public boolean isAuthorizedInLocation(String principalId, String permissionName, String location) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
		return isAuthorized(principalId, permissionName, qualification);
    }
    
    protected boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, String documentType, DocumentStatus documentStatus, String documentPrincipalId, List<Assignment> assignments) {
    	boolean isAuthorized = false;
    	
    	if (isSystemUser(principalId)) {
    		isAuthorized = isAuthorizedByTemplate(principalId, permissionTemplateName, documentType, documentStatus, documentPrincipalId);
    	} else {
            for (Assignment assignment : assignments) {
            	Department departmentObj = getDepartmentService().getDepartment(assignment.getDept(), assignment.getEffectiveDate());
            	
            	Long workArea = assignment.getWorkArea();
            	String department = assignment.getDept();
            	String location = departmentObj != null ? departmentObj.getLocation() : null;
            	
                if (isAuthorizedByTemplateInWorkArea(principalId, permissionTemplateName, workArea, documentType, documentStatus)
	                	|| isAuthorizedByTemplateInDepartment(principalId, permissionTemplateName, department, documentType, documentStatus)
	                	|| isAuthorizedByTemplateInLocation(principalId, permissionTemplateName, location, documentType, documentStatus)) {
                	isAuthorized = true;
                	break;
                }
            }
    	}

        return isAuthorized;
    }
    
    protected boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, String documentTypeName, DocumentStatus documentStatus, String ownerPrincipalId) {
    	Map<String, String> qualification = new HashMap<String, String>();

    	return isAuthorizedByTemplate(principalId, permissionTemplateName, documentTypeName, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplateInWorkArea(String principalId, String permissionTemplateName, Long workArea, String documentTypeName, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
    	return isAuthorizedByTemplate(principalId, permissionTemplateName, documentTypeName, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplateInDepartment(String principalId, String permissionTemplateName, String department, String documentTypeName, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
    	return isAuthorizedByTemplate(principalId, permissionTemplateName, documentTypeName, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplateInLocation(String principalId, String permissionTemplateName, String location, String documentTypeName, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
    	return isAuthorizedByTemplate(principalId, permissionTemplateName, documentTypeName, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, String documentTypeName, DocumentStatus documentStatus, Map<String, String> qualification) {
    	KPMEDocumentStatus kpmeDocumentStatus = KPMEDocumentStatus.getKPMEDocumentStatus(documentStatus);
		
		Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentTypeName);
		permissionDetails.put(KPMEPermissionTemplateAttribute.KPME_DOCUMENT_STATUS.getPermissionTemplateAttributeName(), kpmeDocumentStatus.name());
    	
    	return isAuthorizedByTemplate(principalId, permissionTemplateName, permissionDetails, qualification);
    }
    
	public abstract DepartmentService getDepartmentService();

}