package org.kuali.hr.time.roles.service;

import java.util.List;

import org.kuali.hr.time.roles.TkRoleGroup;

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

}
