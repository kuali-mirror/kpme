package org.kuali.hr.lm.accrual.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.accrual.PrincipalAccrualRan;
import org.kuali.hr.time.util.TKUtils;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PrincipalAccrualRanDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements PrincipalAccrualRanDao{
	
	@Override
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);

		Query query = QueryFactory.newQuery(PrincipalAccrualRan.class, crit);
		return (PrincipalAccrualRan)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public void updatePrincipalAccrualRanInfo(String principalId) {
		PrincipalAccrualRan par = this.getLastPrincipalAccrualRan(principalId);
		if(par == null) { // no data for last ran
			par = new PrincipalAccrualRan();
			par.setPrincipalId(principalId);
			par.setLastRanTs(TKUtils.getCurrentTimestamp());
		} else {
			par.setLastRanTs(TKUtils.getCurrentTimestamp());
		}
		
		this.getPersistenceBrokerTemplate().store(par);
	}

}
