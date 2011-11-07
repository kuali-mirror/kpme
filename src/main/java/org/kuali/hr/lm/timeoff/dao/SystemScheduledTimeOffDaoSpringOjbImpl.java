package org.kuali.hr.lm.timeoff.dao;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class SystemScheduledTimeOffDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements SystemScheduledTimeOffDao {

	private static final Logger LOG = Logger.getLogger(SystemScheduledTimeOffDaoSpringOjbImpl.class);

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOff(Long lmSystemScheduledTimeOffId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmSystemScheduledTimeOffId", lmSystemScheduledTimeOffId);
		Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
		return (SystemScheduledTimeOff) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, Date startDate, Date endDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addBetween("accruedDate", startDate, endDate);
		return (List<SystemScheduledTimeOff>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(SystemScheduledTimeOff.class, root));
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, Date startDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addEqualTo("accruedDate", startDate);
		return (SystemScheduledTimeOff)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(SystemScheduledTimeOff.class, root));
	}

}
