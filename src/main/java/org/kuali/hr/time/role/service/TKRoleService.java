package org.kuali.hr.time.role.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.rice.kim.api.role.RoleMember;

public interface TKRoleService {
	
	/**
	 * Gets the id for the role {@code roleName}.
	 * 
	 * @param roleName The name of the role
	 * 
	 * @return the id for the role {@code roleName}.
	 */
	String getRoleIdByName(String roleName);

	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName}, false otherwise.
	 */
	boolean principalHasRole(String principalId, String roleName, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given role qualifications.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param qualification The map of role qualifiers for the person
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName}, false otherwise.
	 */
	boolean principalHasRole(String principalId, String roleName, Map<String, String> qualification, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given work area.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given work area, false otherwise.
	 */
	boolean principalHasRoleInWorkArea(String principalId, String roleName, Long workArea, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given department.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given department, false otherwise.
	 */
	boolean principalHasRoleInDepartment(String principalId, String roleName, String department, DateTime asOfDate);
	
	/**
	 * Checks whether the given {@code principalId} has the role {@code roleName} depending on the given location.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the role
	 * 
	 * @return true if {@code principalId} has the role {@code roleName} for the given location, false otherwise.
	 */
	boolean principalHasRoleInLocation(String principalId, String roleName, String location, DateTime asOfDate);
	
	/**
	 * Gets the members of the role {@code roleName}.
	 * 
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName}.
	 */
	List<RoleMember> getRoleMembers(String roleName, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given role qualifiers.
	 * 
	 * @param roleName The name of the role
	 * @param qualification The map of role qualifiers
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName}.
	 */
	List<RoleMember> getRoleMembers(String roleName, Map<String, String> qualification, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given work area.
	 * 
	 * @param roleName The name of the role
	 * @param workArea The work area qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given work area.
	 */
	List<RoleMember> getRoleMembersInWorkArea(String roleName, Long workArea, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given department.
	 * 
	 * @param roleName The name of the role
	 * @param department The department qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given department.
	 */
	List<RoleMember> getRoleMembersInDepartment(String roleName, String department, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the members of the role {@code roleName} for the given location.
	 * 
	 * @param roleName The name of the role
	 * @param location The location qualifier
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of role members in the role {@code roleName} for the given location.
	 */
	List<RoleMember> getRoleMembersInLocation(String roleName, String location, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the work areas for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of work areas for the given {@code principalId} in the role {@code roleName}.
	 */
	List<Long> getWorkAreasForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the departments for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of departments for the given {@code principalId} in the role {@code roleName}.
	 */
	List<String> getDepartmentsForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly);

	/**
	 * Gets the locations for the given {@code principalId} in the role {@code roleName}.
	 * 
	 * @param principalId The person to check the role for
	 * @param roleName The name of the role
	 * @param asOfDate The effective date of the role
	 * @param getActiveOnly Whether or not to get only active role members
	 * 
	 * @return the list of locations for the given {@code principalId} in the role {@code roleName}.
	 */
	List<String> getLocationsForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly);

}