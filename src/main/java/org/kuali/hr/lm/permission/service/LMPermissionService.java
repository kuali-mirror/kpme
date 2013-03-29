package org.kuali.hr.lm.permission.service;

import java.util.Map;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.rice.kew.api.document.DocumentStatus;

public interface LMPermissionService {

	boolean isAuthorized(String principalId, String permissionName);
	
	boolean isAuthorized(String principalId, String permissionName, Map<String, String> qualification);
	
	boolean isAuthorizedInWorkArea(String principalId, String permissionName, Long workArea);

	boolean isAuthorizedInDepartment(String principalId, String permissionName, String department);

	boolean isAuthorizedInLocation(String principalId, String permissionName, String location);
	
	boolean canViewLeaveCalendar(String principalId, String documentId);

	boolean canEditLeaveCalendar(String principalId, String documentId);

	boolean canSubmitLeaveCalendar(String principalId, String documentId);
	
	boolean canApproveLeaveCalendar(String principalId, String documentId);

	boolean canSuperUserAdministerLeaveCalendar(String principalId, String documentId);
	
	boolean canViewLeaveRequest(String principalId, String documentId);

	boolean canEditLeaveRequest(String principalId, String documentId);

	boolean canSubmitLeaveRequest(String principalId, String documentId);
	
	boolean canApproveLeaveRequest(String principalId, String documentId);

	boolean canSuperUserAdministerLeaveRequest(String principalId, String documentId);

    boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, Map<String, String> permissionDetails);
	
    boolean isAuthorizedByTemplate(String principalId, String permissionTemplateName, Map<String, String> permissionDetails, Map<String, String> qualification);
    
    boolean isAuthorizedByTemplateInWorkArea(String principalId, String permissionTemplateName, Long workArea, DocumentStatus documentStatus);
    
    boolean isAuthorizedByTemplateInDepartment(String principalId, String permissionTemplateName, String department, DocumentStatus documentStatus);
    
    boolean isAuthorizedByTemplateInLocation(String principalId, String permissionTemplateName, String location, DocumentStatus documentStatus);

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