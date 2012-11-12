/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.roles.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public interface TkRoleService {
	/**
	 * Fetch all roles for a given Principal ID as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<TkRole> getRoles(String principalId, Date asOfDate);
	
	/**
	 * Fetch all inactive roles for a given Principal ID as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'{getInactiveRoles}' + 'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<TkRole> getInactiveRoles(String principalId, Date asOfDate);
	/**
	 * Fetch all roles for a given Principal ID and Role Name as of a particular date
	 * @param principalId
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'asOfDate=' + #p2")
	public List<TkRole> getRoles(String principalId, String roleName, Date asOfDate);
	/**
	 * Fetch all Roles for a given Work Area
	 * @param workArea
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'workArea=' + #p0")
	public List<TkRole> getWorkAreaRoles(Long workArea);
    /**
	 * Fetch all Roles for a given work area and role name as of a particular date
	 * @param workArea
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'workArea=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'asOfDate=' + #p2")
	public List<TkRole> getWorkAreaRoles(Long workArea, String roleName, Date asOfDate);
	/**
	 * Fetch all Inactive Roles for a given work area
	 * @param workArea
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'{getInActiveWorkAreaRoles}' + 'workArea=' + #p0")
	public List<TkRole> getInActiveWorkAreaRoles(Long workArea);
    /**
	 * Fetch all Inactive Roles for a given work area and role name as of a particular date
	 * @param workArea
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'{getInActiveWorkAreaRoles}' + 'workArea=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'asOfDate=' + #p2")
	public List<TkRole> getInActiveWorkAreaRoles(Long workArea, String roleName, Date asOfDate);
	/**
	 * Fetch all Roles for a given Department and Role Name as of a particular date
	 * @param department
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'department=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'asOfDate=' + #p2")
	public List<TkRole> getDepartmentRoles(String department, String roleName, Date asOfDate);
	
	/**
	 * Fetch all Inactive Roles for a given Department and Role Name as of a particular date
	 * @param department
	 * @param roleName
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'{getDepartmentInactiveRoles}' + 'department=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'asOfDate=' + #p2")
	public List<TkRole> getDepartmentInactiveRoles(String department, String roleName, Date asOfDate);
	/**
	 * Fetch all Roles for a given Department
	 * @param department
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'department=' + #p0")
	public List<TkRole> getDepartmentRoles(String department);
	/**
	 * Fetch all Inactive Roles for a given Department
	 * @param department
	 * @return
	 */
    @Cacheable(value= TkRole.CACHE_NAME, key="'{getDepartmentInactiveRoles}' + 'department=' + #p0")
	public List<TkRole> getDepartmentInactiveRoles(String department);
	/**
	 * Save or Update a given TkRole
	 * @param role
	 */
    @CacheEvict(value={TkRole.CACHE_NAME, TkRoleGroup.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(TkRole role);
	/**
	 * Save or Update a List of TkRole objects
	 * @param roles
	 */
    @CacheEvict(value={TkRole.CACHE_NAME, TkRoleGroup.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<TkRole> roles);

    /**
     * Gets the list of principal IDs responsible for the provided assignment /
     * role name combination.
     *
     * @param assignment the assignment to query
     * @param roleName The role we are interested in.
     * @param asOfDate effective date
     * @return
     */
    public List<String> getResponsibleParties(Assignment assignment, String roleName, Date asOfDate);

    /**
     * Provides a unique set of work areas that this user is an approver for.
     * @param principalId The principal to retrieve roles for.
     * @param asOfDate effective date
     * @return A Set of Long work area numbers.
     */
    @Cacheable(value= TkRole.CACHE_NAME, key="'{getWorkAreasForApprover}' + 'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
    public Set<Long> getWorkAreasForApprover(String principalId, Date asOfDate);

    /**
     * Provides a unique set of principal ids that have active assignments in the
     * given work areas.
     * @param workAreas A set of work area numbers.
     * @param asOfDate effective date
     * @return A Set of String principal IDs.
     */
    public Set<String> getActivePrincipalsForWorkAreas(Set<Long> workAreas, Date asOfDate);
    
    /**
     * Fetches Role by primary key
     */
    @Cacheable(value= TkRole.CACHE_NAME, key="'tkRoleId=' + #p0")
    public TkRole getRole(String tkRoleId);
    
    /**
     * Fetches Role by position number
     */
    @Cacheable(value= TkRole.CACHE_NAME, key="'positionNumber=' + #p0")
    public TkRole getRolesByPosition(String positionNumber);

    @Cacheable(value= TkRole.CACHE_NAME, key="'{getInactiveRolesByPosition}' + 'positionNumber=' + #p0")
    public TkRole getInactiveRolesByPosition(String positionNumber);

    @Cacheable(value= TkRole.CACHE_NAME, key="'{getPositionRolesForWorkArea}' + 'workArea=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<TkRole> getPositionRolesForWorkArea(Long workArea, Date asOfDate);

    @Cacheable(value= TkRole.CACHE_NAME,
            key="'principalId=' + #p0" +
                    "+ '|' + 'asOfDate=' + #p1" +
                    "+ '|' + 'roleName=' + #p2" +
                    "+ '|' + 'workArea=' + #p3" +
                    "+ '|' + 'department=' + #p4")
    List<TkRole> getRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department);
}
