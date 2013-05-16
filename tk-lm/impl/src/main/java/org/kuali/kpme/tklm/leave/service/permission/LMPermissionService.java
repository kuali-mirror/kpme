/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.service.permission;

import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

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
