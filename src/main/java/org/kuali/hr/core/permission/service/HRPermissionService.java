package org.kuali.hr.core.permission.service;

import java.util.Map;

public interface HRPermissionService {

	boolean isAuthorized(String principalId, String permissionName);
	
	boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification);
	
	boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea);
	
	boolean isAuthorizedInDepartment(String principalId, String permissionName, String department);

	boolean isAuthorizedInLocation(String principalId, String permissionName, String location);
	
    boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails);
	
    boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification);

	boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea);

	boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department);

	boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location);

}