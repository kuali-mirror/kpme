package org.kuali.hr.core.permission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.service.DepartmentService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;

public abstract class KPMEPermissionServiceBase {
	
	public abstract boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification);
		
	public abstract boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification);
	
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
    
	public boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
    }

	public boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
	}

	public boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
	}
    
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentType, String documentId, DocumentStatus documentStatus, List<Assignment> assignments) {
    	boolean isAuthorized = false;
    	
    	for (Assignment assignment : assignments) {
        	Department departmentObj = getDepartmentService().getDepartment(assignment.getDept(), assignment.getEffectiveDate());
        	
        	Long workArea = assignment.getWorkArea();
        	String department = assignment.getDept();
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
            if (isAuthorizedByTemplateInWorkArea(principalId, namespaceCode, permissionTemplateName, workArea, documentType, documentId, documentStatus)
                	|| isAuthorizedByTemplateInDepartment(principalId, namespaceCode, permissionTemplateName, department, documentType, documentId, documentStatus)
                	|| isAuthorizedByTemplateInLocation(principalId, namespaceCode, permissionTemplateName, location, documentType, documentId, documentStatus)) {
            	isAuthorized = true;
            	break;
            }
        }

        return isAuthorized;
    }
    
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();

    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea, String documentTypeName, String documentId, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, String documentTypeName, String documentId, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, String documentTypeName, String documentId, DocumentStatus documentStatus) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification);
    }
    
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, Map<String, String> qualification) {
		qualification.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, documentId);
    	
    	Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentTypeName);
		permissionDetails.put(KimConstants.AttributeConstants.ROUTE_STATUS_CODE, documentStatus.getCode());
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification);
    }
    
	public abstract DepartmentService getDepartmentService();

}