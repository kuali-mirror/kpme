package org.kuali.hr.core.permission.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.department.service.DepartmentService;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;

public abstract class KPMEPermissionServiceBase {
	
	private DepartmentService departmentService;
	private WorkAreaService workAreaService;
	
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given role qualifications.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName}, false otherwise.
	 */
	public abstract boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given permission details and role qualifications.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param permissionDetails The map of permission details for the permission
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName}, false otherwise.
	 */
	public abstract boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given work area.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given work area, false otherwise.
	 */
    public boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given department.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given department, false otherwise.
	 */
    public boolean isAuthorizedInDepartment(String principalId, String permissionName, String department, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given location.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given location, false otherwise.
	 */
    public boolean isAuthorizedInLocation(String principalId, String permissionName, String location, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
		return isAuthorized(principalId, permissionName, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area, false otherwise.
	 */
	public boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea, DateTime asOfDate) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
    }

	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department, false otherwise.
	 */
	public boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, DateTime asOfDate) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
		Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
	}

	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location, false otherwise.
	 */
	public boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, DateTime asOfDate) {
		Map<String, String> permissionDetails = new HashMap<String, String>();
		
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
		return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
	}
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param assignments The list of assignments associated with the document
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, List<Assignment> assignments) {
    	boolean isAuthorized = false;
    	
    	for (Assignment assignment : assignments) {
            if (isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, assignment)) {
            	isAuthorized = true;
            	break;
            }
        }

        return isAuthorized;
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param assignment The assignment associated with the document
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, Assignment assignment) {
    	boolean isAuthorized = false;
    	
		Long workArea = assignment.getWorkArea();
    	WorkArea workAreaObj = getWorkAreaService().getWorkArea(workArea, assignment.getEffectiveLocalDate());
		
		String department = workAreaObj != null ? workAreaObj.getDept() : null;
    	Department departmentObj = getDepartmentService().getDepartment(department, assignment.getEffectiveLocalDate());
    	
    	String location = departmentObj != null ? departmentObj.getLocation() : null;
    	
        if (isAuthorizedByTemplateInWorkArea(principalId, namespaceCode, permissionTemplateName, workArea, documentTypeName, documentId, documentStatus, new DateTime(assignment.getEffectiveDate()))
            	|| isAuthorizedByTemplateInDepartment(principalId, namespaceCode, permissionTemplateName, department, documentTypeName, documentId, documentStatus, new DateTime(assignment.getEffectiveDate()))
            	|| isAuthorizedByTemplateInLocation(principalId, namespaceCode, permissionTemplateName, location, documentTypeName, documentId, documentStatus, new DateTime(assignment.getEffectiveDate()))) {
        	isAuthorized = true;
        }
        
        return isAuthorized;
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();

    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area and document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param workArea The work area qualifier
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given work area and document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea));
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department and document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param department The department qualifier
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given department and document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location and document information.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param location The location qualifier
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given location and document information, false otherwise.
	 */
    protected boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, String documentTypeName, String documentId, DocumentStatus documentStatus, DateTime asOfDate) {
    	Map<String, String> qualification = new HashMap<String, String>();
		qualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, documentTypeName, documentId, documentStatus, qualification, asOfDate);
    }
    
	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information and role qualifiers.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param documentTypeName The type of the document
	 * @param documentId The id of the document
	 * @param documentStatus The status of the document
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given document information and role qualifiers, false otherwise.
	 */
    protected boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, String documentTypeName, String documentId, DocumentStatus documentStatus, Map<String, String> qualification, DateTime asOfDate) {
		qualification.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, documentId);
    	
    	Map<String, String> permissionDetails = new HashMap<String, String>();
		permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentTypeName);
		permissionDetails.put(KimConstants.AttributeConstants.ROUTE_STATUS_CODE, documentStatus.getCode());
    	
    	return isAuthorizedByTemplate(principalId, namespaceCode, permissionTemplateName, permissionDetails, qualification, asOfDate);
    }
    
    public DepartmentService getDepartmentService() {
    	return departmentService;
    }
    
    public void setDepartmentService(DepartmentService departmentService) {
    	this.departmentService = departmentService;
    }
    
    public WorkAreaService getWorkAreaService() {
    	return workAreaService;
    }
    
    public void setWorkAreaService(WorkAreaService workAreaService) {
    	this.workAreaService = workAreaService;
    }

}