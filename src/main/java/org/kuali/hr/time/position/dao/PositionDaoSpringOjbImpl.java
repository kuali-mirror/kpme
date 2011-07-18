package org.kuali.hr.time.position.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.position.Position;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PositionDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements PositionDao {

	@Override
	public Position getPosition(Long hrPositionId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPositionId", hrPositionId);
		
		Query query = QueryFactory.newQuery(Position.class, crit);
		return (Position)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
