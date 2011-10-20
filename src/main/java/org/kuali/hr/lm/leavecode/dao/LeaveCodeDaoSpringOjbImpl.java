package org.kuali.hr.lm.leavecode.dao;


import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeaveCodeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements LeaveCodeDao {

	private static final Logger LOG = Logger.getLogger(LeaveCodeDaoSpringOjbImpl.class);

	@Override
	public LeaveCode getLeaveCode(Long lmLeaveCodeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeaveCodeId", lmLeaveCodeId);
		Query query = QueryFactory.newQuery(LeaveCode.class, crit);
		return (LeaveCode) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


}
