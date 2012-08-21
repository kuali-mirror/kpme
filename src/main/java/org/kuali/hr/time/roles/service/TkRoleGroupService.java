package org.kuali.hr.time.roles.service;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface TkRoleGroupService {
    @CacheEvict(value={TkRole.CACHE_NAME, TkRoleGroup.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(TkRoleGroup roleGroup);
	/**
	 * Save or Update a List of TkRole objects
	 * @param roleGroups
	 */
    @CacheEvict(value={TkRole.CACHE_NAME, TkRoleGroup.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<TkRoleGroup> roleGroups);

    
    /**
     * Fetches Role by principal id
     */
    @Cacheable(value= TkRoleGroup.CACHE_NAME, key="'principalId=' + #p0")
    public TkRoleGroup getRoleGroup(String principalId);
    
    public void populateRoles(TkRoleGroup tkRoleGroup);

    @Cacheable(value= TkRoleGroup.CACHE_NAME,
            key="'principalId=' + #p0" +
                    "+ '|' + 'principalName=' + #p1" +
                    "+ '|' + 'workArea=' + #p2" +
                    "+ '|' + 'dept=' + #p3" +
                    "+ '|' + 'roleName=' + #p4")
    List<TkRoleGroup> getRoleGroups(String principalId, String principalName, String workArea, String dept, String roleName);
}
