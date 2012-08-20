package org.kuali.hr.lm.leavedonation.dao;


import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveDonationDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveDonationDao {

	private static final Logger LOG = Logger.getLogger(LeaveDonationDaoSpringOjbImpl.class);

	@Override
	public LeaveDonation getLeaveDonation(String lmLeaveDonationId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeaveDonationId", lmLeaveDonationId);
		Query query = QueryFactory.newQuery(LeaveDonation.class, crit);
		return (LeaveDonation) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
}
