package org.kuali.hr.core.role.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.rice.kim.api.role.RoleMember;

public interface HRRoleService {
	
	String getRoleIdByName(String roleName);

	boolean principalHasRole(String principalId, String roleName, DateTime asOfDate);

	boolean principalHasRole(String principalId, String roleName, Map<String, String> qualification, DateTime asOfDate);

	boolean principalHasRoleInWorkArea(String principalId, String roleName, Long workArea, DateTime asOfDate);

	boolean principalHasRoleInDepartment(String principalId, String roleName, String department, DateTime asOfDate);

	boolean principalHasRoleInLocation(String principalId, String roleName, String location, DateTime asOfDate);

	List<RoleMember> getRoleMembers(String roleName, DateTime asOfDate, boolean getActiveOnly);

	List<RoleMember> getRoleMembers(String roleName, Map<String, String> qualification, DateTime asOfDate, boolean getActiveOnly);

	List<RoleMember> getRoleMembersInWorkArea(String roleName, Long workArea, DateTime asOfDate, boolean getActiveOnly);

	List<RoleMember> getRoleMembersInDepartment(String roleName, String department, DateTime asOfDate, boolean getActiveOnly);

	List<RoleMember> getRoleMembersInLocation(String roleName, String location, DateTime asOfDate, boolean getActiveOnly);

	List<Long> getWorkAreasForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly);

	List<String> getDepartmentsForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly);

	List<String> getLocationsForPrincipalInRole(String principalId, String roleName, DateTime asOfDate, boolean getActiveOnly);

}