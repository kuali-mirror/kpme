package org.kuali.hr.pm.salarygroup.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.pm.salarygroup.SalaryGroup;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SalaryGroupDaoObjImpl extends PlatformAwareDaoBaseOjb implements SalaryGroupDao {

	@Override
	public SalaryGroup getSalaryGroupById(String pmSalaryGroupId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmSalaryGroupId", pmSalaryGroupId);

        Query query = QueryFactory.newQuery(SalaryGroup.class, crit);
        return (SalaryGroup) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
