package org.kuali.hr.lm.accrual.dao;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class AccrualCategoryRuleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements AccrualCategoryRuleDao {
	
    @SuppressWarnings("unchecked")
	public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryId", accrualCategoryId);
		
		Query query = QueryFactory.newQuery(AccrualCategoryRule.class, crit);
		return (List<AccrualCategoryRule>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    	
    }
    
    public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryRuleId", lmAccrualCategoryRuleId);
		
		Query query = QueryFactory.newQuery(AccrualCategoryRule.class, crit);
		return (AccrualCategoryRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
 
}
