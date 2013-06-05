package org.kuali.kpme.pm.position.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.position.Position;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionDao {

	@Override
	public Position getPosition(String id) {
		 Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", id);

        Query query = QueryFactory.newQuery(Position.class, crit);
        return (Position) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	
}
