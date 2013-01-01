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
