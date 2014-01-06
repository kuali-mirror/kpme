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
package org.kuali.kpme.core.service.role;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.rice.kim.api.role.RoleMember;
import org.springframework.cache.annotation.Cacheable;

public interface KPMERoleService {

	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName}, false otherwise.
	 */
    @Cacheable(value=RoleMember.Cache.NAME, key="'{principalHasRole}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'asOfDate=' + #p3")
	boolean principalHasRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given role qualifications.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName}, false otherwise.
	 */
    @Cacheable(value=RoleMember.Cache.NAME, key="'{principalHasRole}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2 + '|' + 'qualification=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).mapKey(#p3)  + '|' + 'asOfDate=' + #p4")
	boolean principalHasRole(String principalId, String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given work area.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given work area, false otherwise.
	 */
    @Cacheable(value=RoleMember.Cache.NAME, key="'{principalHasRoleInWorkArea}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'workArea=' + #p3 + '|' + 'asOfDate=' + #p4")
	boolean principalHasRoleInWorkArea(String principalId, String namespaceCode, String roleName, Long workArea, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given department.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given department, false otherwise.
	 */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{principalHasRoleInDepartment}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'department=' + #p3 + '|' + 'asOfDate=' + #p4")
	boolean principalHasRoleInDepartment(String principalId, String namespaceCode, String roleName, String department, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given location.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given location, false otherwise.
	 */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{principalHasRoleInLocation}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'location=' + #p3 + '|' + 'asOfDate=' + #p4")
	boolean principalHasRoleInLocation(String principalId, String namespaceCode, String roleName, String location, DateTime asOfDate);
	
	/**
	 * Gets the members of the role {@code roleName}.
	 * 
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName}.
	 */
	List<RoleMember> getRoleMembers(String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given role qualifiers.
	 * 
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param qualification The map of role qualifiers
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName}.
	 */
	List<RoleMember> getRoleMembers(String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given work area.
	 * 
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given work area.
	 */
	List<RoleMember> getRoleMembersInWorkArea(String namespaceCode, String roleName, Long workArea, DateTime asOfDate, boolean isActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given department.
	 * 
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given department.
	 */
	List<RoleMember> getRoleMembersInDepartment(String namespaceCode, String roleName, String department, DateTime asOfDate, boolean isActiveOnly);

	List<RoleMember> getRoleMembersInPosition(String namespaceCode, String roleName, String positionNumber, DateTime asOfDate, boolean isActiveOnly);
	/**
	 * Gets the members of the role {@code roleName} for the given location.
	 * 
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given location.
	 */
	List<RoleMember> getRoleMembersInLocation(String namespaceCode, String roleName, String location, DateTime asOfDate, boolean isActiveOnly);

	/**
	 * Gets the work areas for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param activeOnly Whether or not to get only active role members
	 * 
	 * @return the list of work areas for the given {@code principalId} in the role {@code roleName}.
	 */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{getWorkAreasForPrincipalInRole}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'asOfDate=' + #p3 + '|' + 'activeOnly=' + #p4")
	List<Long> getWorkAreasForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean activeOnly);

    /**
     * Gets the work areas for the given {@code principalId} in the role {@code roleName}.
     *
     * @param principalId The person to check the role for
     * @param roleIds The list of roleIds
     * @param asOfDate The effective date of the role
     * @param activeOnly Whether or not to get only active role members
     *
     * @return the list of work areas for the given {@code principalId} in the role {@code roleName}.
     */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{getWorkAreasForPrincipalInRoles}' + 'principal=' + #p0 + '|' + 'roleIds=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).key(#p1) + '|' + 'asOfDate=' + #p3 + '|' + 'activeOnly=' + #p4")
    List<Long> getWorkAreasForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean activeOnly);

    /**
     * Gets the departments for the given {@code principalId} in the roles {@code roleIds}.
     *
     * @param principalId The person to check the role for
     * @param roleIds The list of roleIds
     * @param asOfDate The effective date of the role
     * @param activeOnly Whether or not to get only active role members
     *
     * @return the list of work areas for the given {@code principalId} in the roles {@code roleIds}.
     */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{getDepartmentsForPrincipalInRoles}' + 'principal=' + #p0 + '|' + 'roleIds=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).key(#p1) + '|' + 'asOfDate=' + #p3 + '|' + 'activeOnly=' + #p4")
    List<String> getDepartmentsForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean activeOnly);

    /**
     * Gets the locations for the given {@code principalId} in the roles {@code roleIds}.
     *
     * @param principalId The person to check the role for
     * @param roleIds The list of roleIds
     * @param asOfDate The effective date of the role
     * @param activeOnly Whether or not to get only active role members
     *
     * @return the list of work areas for the given {@code principalId} in the roles {@code roleIds}.
     */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{getLocationsForPrincipalInRoles}' + 'principal=' + #p0 + '|' + 'roleIds=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).key(#p1) + '|' + 'asOfDate=' + #p3 + '|' + 'activeOnly=' + #p4")
    List<String> getLocationsForPrincipalInRoles(String principalId, List<String> roleIds, DateTime asOfDate, boolean activeOnly);


	/**
	 * Gets the departments for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param isActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of departments for the given {@code principalId} in the role {@code roleName}.
	 */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{getDepartmentsForPrincipalInRole}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'asOfDate=' + #p3 + '|' + 'isActiveOnly=' + #p4")
    List<String> getDepartmentsForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly);

	/**
	 * Gets the locations for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param namespaceCode The namespace of the role
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param isActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of locations for the given {@code principalId} in the role {@code roleName}.
	 */
    @Cacheable(value= RoleMember.Cache.NAME, key="'{getLocationsForPrincipalInRole}' + 'principal=' + #p0 + '|' + 'namespace=' + #p1 + '|' + 'roleName=' + #p2  + '|' + 'asOfDate=' + #p3 + '|' + 'isActiveOnly=' + #p4")
    List<String> getLocationsForPrincipalInRole(String principalId, String namespaceCode, String roleName, DateTime asOfDate, boolean isActiveOnly);

}