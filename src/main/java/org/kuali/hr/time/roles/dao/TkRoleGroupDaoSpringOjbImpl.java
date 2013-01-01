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
package org.kuali.hr.time.roles.dao;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.roles.TkRoleGroup;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TkRoleGroupDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TkRoleGroupDao {
 
	@Override
	public void saveOrUpdateRoleGroup(TkRoleGroup roleGroup) {
		this.getPersistenceBrokerTemplate().store(roleGroup);
	}

	@Override
	public void saveOrUpdateRoleGroups(List<TkRoleGroup> roleGroups) {
		if (roleGroups != null) {
			for (TkRoleGroup role : roleGroups) {
				saveOrUpdateRoleGroup(role);
			}
		}
	}

	@Override
	public TkRoleGroup getRoleGroup(String principalId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("principalId", principalId);

		return (TkRoleGroup) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkRoleGroup.class, currentRecordCriteria));
	}
 

}
