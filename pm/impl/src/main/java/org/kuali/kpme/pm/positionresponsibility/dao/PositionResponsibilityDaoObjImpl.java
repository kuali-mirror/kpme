package org.kuali.kpme.pm.positionresponsibility.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.positionresponsibility.PositionResponsibility;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionResponsibilityDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionResponsibilityDao {

	@Override
	public PositionResponsibility getPositionResponsibilityById(
			String positionResponsibilityId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("positionResponsibilityId", positionResponsibilityId);

        Query query = QueryFactory.newQuery(PositionResponsibility.class, crit);
        return (PositionResponsibility) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
