package org.kuali.hr.lm.accrual.dao;


import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.accrual.LeaveAccrualCategory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeaveAccrualCategoryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements LeaveAccrualCategoryDao {

	private static final Logger LOG = Logger.getLogger(LeaveAccrualCategoryDaoSpringOjbImpl.class);

	@Override
	public LeaveAccrualCategory getLeaveAccrualCategory(Long lmAccrualCategoryId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryId", lmAccrualCategoryId);
		Query query = QueryFactory.newQuery(LeaveAccrualCategory.class, crit);
		return (LeaveAccrualCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


}
