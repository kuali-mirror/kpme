package org.kuali.kpme.pm.positiondepartment.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionDepartmentDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionDepartmentDao {

	@Override
	public PositionDepartment getPositionDepartmentById(
			String pmPositionDeptId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionDeptId", pmPositionDeptId);

        Query query = QueryFactory.newQuery(PositionDepartment.class, crit);
        return (PositionDepartment) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


}
