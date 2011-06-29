package org.kuali.hr.time.roles.dao;

import org.kuali.hr.time.roles.TkRoleGroup;

import java.util.List;

public interface TkRoleGroupDao {

	/**
	 * A role to update/save
	 * @param role
	 */
	public void saveOrUpdateRoleGroup(TkRoleGroup roleGroup);

	/**
	 * A list of roles to save.
	 * @param roles
	 * @return
	 */
	public void saveOrUpdateRoleGroups(List<TkRoleGroup> roleGroups);
	
	public TkRoleGroup getRoleGroup(String principalId);
}
