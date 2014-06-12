/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.service.permission;

import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;

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
     * @param groupKeyCode The group key code qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given department, false otherwise.
	 */
	boolean isAuthorizedInDepartment(String principalId, String permissionName, String department, String groupKeyCode, DateTime asOfDate);

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
	 * @param permissionTemplateName The name of the permission
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
	 * @param permissionTemplateName The name of the permission
	 * @param department The department qualifier
     * @param groupKeyCode The group key code qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given department, false otherwise.
	 */
	boolean isAuthorizedByTemplateInDepartment(String principalId, String namespaceCode, String permissionTemplateName, String department, String groupKeyCode, DateTime asOfDate);

	/**
	 * Checks whether the given {@code principalId} is authorized to perform {@code permissionName} for the given location.
	 * 
	 * @param principalId The person to check the permission for
	 * @param permissionTemplateName The name of the permission
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the permission
	 * 
	 * @return true if {@code principalId} is authorized to perform {@code permissionName} for the given location, false otherwise.
	 */
	boolean isAuthorizedByTemplateInLocation(String principalId, String namespaceCode, String permissionTemplateName, String location, DateTime asOfDate);

	boolean canEditTimeBlock(String principalId, TimeBlockContract timeBlock);

    boolean canEditTimeBlockAllFields(String principalId, TimeBlockContract timeBlock);

    boolean canDeleteTimeBlock(String principalId, TimeBlockContract timeBlock);

    boolean canEditOvertimeEarnCode(String principalId, TimeBlockContract timeBlock);
    
    /**
     * Checks whether the given {@code principalId} has systemAdmin/TimeSystemAdmin/TimeLocationAdmin roles on given {@code aTimeBlock}
     * @param principalId
     * @param aTimeBlock
     * @return
     */
	public boolean userHasTimeSysLocationAdminRoles(String principalId,TimeBlockContract aTimeBlock);
	/**
     * Checks whether the given {@code principalId} has edit permission roles on given {@code aTimeBlock}
     * @param principalId
     * @param aTimeBlock
     * @return
     */
    public boolean userHasRolesToEditTimeBlock(String principalId, TimeBlockContract aTimeBlock);
    
    /**
     * Checks whether the given {@code principalId} has PayrollProcessor/PayrollProcessorDelegate roles on given {@code dept} and {@code asOfDate}
     * @param principalId
     * @param dept
     * @param asOfDate
     * @return
     */
    public boolean isPayrollProcessorForDepartment(String principalId, String dept, String groupKeyCode, DateTime asOfDate);
    
    /**
     * Checks whether the given {@code principalId} has Approver/ApproverDelegate roles on given {@code workArea} and {@code asOfDate}
     * @param principalId
     * @param workArea
     * @param asOfDate
     * @return
     */
    public boolean isApproverForWorkArea(String principalId, Long workArea, DateTime asOfDate);
}
