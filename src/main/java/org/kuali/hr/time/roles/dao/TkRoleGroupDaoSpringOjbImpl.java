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
