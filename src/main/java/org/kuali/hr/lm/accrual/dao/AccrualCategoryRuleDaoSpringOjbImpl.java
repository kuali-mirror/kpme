package org.kuali.hr.lm.accrual.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class AccrualCategoryRuleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements AccrualCategoryRuleDao {
	
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
    
    public List <AccrualCategoryRule> getActiveRulesForAccrualCategoryId(String accrualCategoryId, Date asOfDate) {
    	List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		
		Criteria root = new Criteria();
		root.addEqualTo("lmAccrualCategoryId", accrualCategoryId);
		root.addEqualTo("active", true);
		Query query = QueryFactory.newQuery(AccrualCategoryRule.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			rules.addAll(c);
		}
		return rules;
    }
    
    public List <AccrualCategoryRule> getInActiveRulesForAccrualCategoryId(String accrualCategoryId, Date asOfDate) {
    	List<AccrualCategoryRule> rules = new ArrayList<AccrualCategoryRule>();
		
		Criteria root = new Criteria();
		root.addEqualTo("lmAccrualCategoryId", accrualCategoryId);
		root.addEqualTo("active", false);
		Query query = QueryFactory.newQuery(AccrualCategoryRule.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			rules.addAll(c);
		}
		return rules;
    }
 
}
