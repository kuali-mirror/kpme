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
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.springframework.cache.annotation.Cacheable;

public interface KPMERoleServiceHelper {
	
	@Cacheable(value=RoleMember.Cache.NAME, key="'{getRoleMembersCached}' + 'namespace=' + #p0 + '|' + 'roleName=' + #p1 + '|' + 'qualification=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).mapKey(#p2)  + '|' + 'asOfDate=' + #p3  + '|' + 'isActiveOnly=' + #p4")
	public List<RoleMember> getRoleMembersCached(String namespaceCode, String roleName, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly);

	public List<RoleMember> getPrimaryRoleMembers(Role role, Map<String, String> qualification, DateTime asOfDate, boolean isActiveOnly);

	public boolean isMemberOfGroup(String principalId, Group group, DateTime asOfDate, boolean activeOnly);

}