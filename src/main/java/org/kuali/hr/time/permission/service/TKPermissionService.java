package org.kuali.hr.time.permission.service;

import java.util.Map;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TKPermissionService {
	
	boolean isAuthorized(String principalId, String permissionName);
	
	boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification);

	boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea);
	
	boolean isAuthorizedInDepartment(String principalId, String permissionName, String department);

	boolean isAuthorizedInLocation(String principalId, String permissionName, String location);

	boolean canViewTimesheet(String principalId, String documentId);

	boolean canEditTimesheet(String principalId, String documentId);

	boolean canSubmitTimesheet(String principalId, String documentId);
	
	boolean canApproveTimesheet(String principalId, String documentId);

	boolean canSuperUserAdministerTimesheet(String principalId, String documentId);

    boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails);
	
    boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification);

	boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea);

	boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department);

	boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location);
	
	boolean canEditTimeBlock(String principalId, TimeBlock timeBlock);
	
	boolean canEditTimeBlockAllFields(String principalId, TimeBlock timeBlock);

	boolean canDeleteTimeBlock(String principalId, TimeBlock timeBlock);

	boolean canEditOvertimeEarnCode(TimeBlock timeBlock);

	boolean canEditRegEarnCode(TimeBlock timeBlock);

	boolean canViewTimeTabs();

}