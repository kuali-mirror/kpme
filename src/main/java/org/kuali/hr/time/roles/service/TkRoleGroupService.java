package org.kuali.hr.time.roles.service;

import org.kuali.hr.time.roles.TkRoleGroup;

import java.util.List;

public interface TkRoleGroupService {

	public void saveOrUpdate(TkRoleGroup roleGroup);
	/**
	 * Save or Update a List of TkRole objects
	 * @param roles
	 */
	public void saveOrUpdate(List<TkRoleGroup> roleGroups);

    
    /**
     * Fetches Role by principal id
     */
    public TkRoleGroup getRoleGroup(String principalId);
    
    public void populateRoles(TkRoleGroup tkRoleGroup);

    List<TkRoleGroup> getRoleGroups(String principalId, String principalName, String workArea, String dept, String roleName);
}
