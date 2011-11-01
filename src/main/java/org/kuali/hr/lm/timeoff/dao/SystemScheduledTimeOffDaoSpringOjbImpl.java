package org.kuali.hr.lm.timeoff.dao;


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


}
