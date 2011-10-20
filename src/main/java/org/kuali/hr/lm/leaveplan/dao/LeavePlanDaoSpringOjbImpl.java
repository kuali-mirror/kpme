package org.kuali.hr.lm.leaveplan.dao;


import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeavePlanDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements LeavePlanDao {

	private static final Logger LOG = Logger.getLogger(LeavePlanDaoSpringOjbImpl.class);

	@Override
	public LeavePlan getLeavePlan(Long lmLeavePlanId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeavePlanId", lmLeavePlanId);
		Query query = QueryFactory.newQuery(LeavePlan.class, crit);
		return (LeavePlan) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


}
