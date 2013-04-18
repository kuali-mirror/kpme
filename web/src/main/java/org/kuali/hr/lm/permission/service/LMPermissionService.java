package org.kuali.hr.lm.permission.service;

import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.assignment.Assignment;

public interface LMPermissionService {

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
	 * Checks whether the given {@code principalId} can view the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can view the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canViewLeaveCalendar(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can view the given {@code assignment} attached to the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * @param assignment The assignment attached to the document
	 * 
	 * @return true if {@code principalId} can view the given {@code assignment} attached to the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canViewLeaveCalendarAssignment(String principalId, String documentId, Assignment assignment);

	/**
	 * Checks whether the given {@code principalId} can edit the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can edit the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canEditLeaveCalendar(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can edit the given {@code assignment} attached to the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * @param assignment The assignment attached to the document
	 * 
	 * @return true if {@code principalId} can edit the given {@code assignment} attached to the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canEditLeaveCalendarAssignment(String principalId, String documentId, Assignment assignment);

	/**
	 * Checks whether the given {@code principalId} can submit the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can submit the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canSubmitLeaveCalendar(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can approve the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can approve the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canApproveLeaveCalendar(String principalId, String documentId);

	/**
	 * Checks whether the given {@code principalId} can super user administer the LeaveCalendar specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can super user administer the LeaveCalendar specified by {@code documentId}, false otherwise.
	 */
	boolean canSuperUserAdministerLeaveCalendar(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can view the LeaveRequest specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can view the LeaveRequest specified by {@code documentId}, false otherwise.
	 */
	boolean canViewLeaveRequest(String principalId, String documentId);

	/**
	 * Checks whether the given {@code principalId} can edit the LeaveRequest specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can edit the LeaveRequest specified by {@code documentId}, false otherwise.
	 */
	boolean canEditLeaveRequest(String principalId, String documentId);

	/**
	 * Checks whether the given {@code principalId} can submit the LeaveRequest specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can submit the LeaveRequest specified by {@code documentId}, false otherwise.
	 */
	boolean canSubmitLeaveRequest(String principalId, String documentId);
	
	/**
	 * Checks whether the given {@code principalId} can approve the LeaveRequest specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can approve the LeaveRequest specified by {@code documentId}, false otherwise.
	 */
	boolean canApproveLeaveRequest(String principalId, String documentId);

	/**
	 * Checks whether the given {@code principalId} can super user administer the LeaveRequest specified by {@code documentId}.
	 * 
	 * @param principalId The person to check
	 * @param documentId The id of the document
	 * 
	 * @return true if {@code principalId} can super user administer the LeaveRequest specified by {@code documentId}, false otherwise.
	 */
	boolean canSuperUserAdministerLeaveRequest(String principalId, String documentId);

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
	
	boolean canEditLeaveBlock(String principalId, LeaveBlock leaveBlock);

	boolean canDeleteLeaveBlock(String principalId, LeaveBlock leaveBlock);

	boolean canViewLeaveTabsWithEStatus();

	boolean canViewLeaveTabsWithNEStatus();
	
	/**
	 * Determine if given leave block is a accrual generated System Scheduled Timeoff usage that can be banked/transferred
	 * and if the leave block is on the current leave calendar entry of the current targeted user
	 * @param leaveBlock
	 * @return
	 */
	boolean canBankOrTransferSSTOUsage(LeaveBlock leaveBlock);
	
	/**
	 * Determine if given leave block is a accrual generated System Scheduled Timeoff usage that can be transferred
	 * and if the leave block is on the current leave calendar entry of the current targeted user
	 * @param lb
	 * @return
	 */
	boolean canTransferSSTOUsage(LeaveBlock leaveBlock);
	
	/**
	 * Determine if given leave block is a accrual generated System Scheduled Timeoff usage that can be banked
	 * and if the leave block is on the current leave calendar entry of the current targeted user
	 * @param leaveBlock
	 * @return
	 */
	boolean canBankSSTOUsage(LeaveBlock leaveBlock);

}