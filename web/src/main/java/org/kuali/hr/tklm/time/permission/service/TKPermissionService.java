package org.kuali.hr.tklm.time.permission.service;

import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;

public interface TKPermissionService {

	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName}.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionName The name of the permission
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName}, false otherwise.
	 */
	boolean isAuthorized(String principalId, String permissionName, DateTime asOfDate);
	
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
	boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification, DateTime asOfDate);

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
	boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea, DateTime asOfDate);
	
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
	boolean isAuthorizedInDepartment(String principalId, String permissionName, String department, DateTime asOfDate);

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
	boolean isAuthorizedInLocation(String principalId, String permissionName, String location, DateTime asOfDate);

	/**
	 * Checks whether the given {@code principalId} can view the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can view the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canViewTimesheet(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can view the given {@code assignment} attached to the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * @param assignment The assignment attached to the document
	 * 
	 * @return true if {@code principalId} can view the given {@code assignment} attached to the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canViewTimesheetAssignment(String principalId, String documentId, Assignment assignment);

	/**
	 * Checks whether the given {@code principalId} can edit the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can edit the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canEditTimesheet(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can edit the given {@code assignment} attached to the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * @param assignment The assignment attached to the document
	 * 
	 * @return true if {@code principalId} can edit the given {@code assignment} attached to the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canEditTimesheetAssignment(String principalId, String documentId, Assignment assignment);

	/**
	 * Checks whether the given {@code principalId} can submit the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can submit the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canSubmitTimesheet(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can approve the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can approve the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canApproveTimesheet(String principalId, String documentId);

	/**
	 * Checks whether the given {@code principalId} can super user administer the Timesheet specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can super user administer the Timesheet specified by {@code documentId}, false otherwise.
	 */
	boolean canSuperUserAdministerTimesheet(String principalId, String documentId);

	/**
	 * Checks whether the given {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName} for the given permission details.
	 * 
	 * @param principalId The person to check the permission for
	 * @param namespaceCode The namespace for the permission template
	 * @param permissionTemplateName The name of the permission template
	 * @param permissionDetails The map of permission details for the permission
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform any permission templated by {@code permissionTemplateName}, false otherwise.
	 */
    boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, DateTime asOfDate);
	
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
    boolean isAuthorizedByTemplate(String principalId, String namespaceCode, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification, DateTime asOfDate);

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
	boolean isAuthorizedByTemplateInWorkArea(String principalId, String namespaceCode, String permissionTemplateName, Long workArea, DateTime asOfDate);

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
	boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, DateTime asOfDate);

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
	boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, DateTime asOfDate);
	
	boolean canEditTimeBlock(String principalId, TimeBlock timeBlock);
	
	boolean canEditTimeBlockAllFields(String principalId, TimeBlock timeBlock);

	boolean canDeleteTimeBlock(String principalId, TimeBlock timeBlock);

	boolean canEditOvertimeEarnCode(TimeBlock timeBlock);

	boolean canEditRegEarnCode(TimeBlock timeBlock);

	boolean canViewTimeTabs();

}