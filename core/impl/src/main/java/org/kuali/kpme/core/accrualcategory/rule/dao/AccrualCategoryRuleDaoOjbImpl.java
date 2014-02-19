/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.accrualcategory.rule.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRuleBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class AccrualCategoryRuleDaoOjbImpl extends PlatformAwareDaoBaseOjb implements AccrualCategoryRuleDao {
	
    @SuppressWarnings("unchecked")
	public List <AccrualCategoryRuleBo> getActiveAccrualCategoryRules(String accrualCategoryId) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryId", accrualCategoryId);
		
		Query query = QueryFactory.newQuery(AccrualCategoryRuleBo.class, crit);
		return (List<AccrualCategoryRuleBo>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    	
    }
    
    public AccrualCategoryRuleBo getAccrualCategoryRule(String lmAccrualCategoryRuleId) {
    	Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryRuleId", lmAccrualCategoryRuleId);
		
		Query query = QueryFactory.newQuery(AccrualCategoryRuleBo.class, crit);
		return (AccrualCategoryRuleBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    public List <AccrualCategoryRuleBo> getActiveRulesForAccrualCategoryId(String accrualCategoryId) {
    	List<AccrualCategoryRuleBo> rules = new ArrayList<AccrualCategoryRuleBo>();
		
		Criteria root = new Criteria();
		root.addEqualTo("lmAccrualCategoryId", accrualCategoryId);
		root.addEqualTo("active", true);
		Query query = QueryFactory.newQuery(AccrualCategoryRuleBo.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			rules.addAll(c);
		}
		return rules;
    }
    
    public List <AccrualCategoryRuleBo> getInActiveRulesForAccrualCategoryId(String accrualCategoryId) {
    	List<AccrualCategoryRuleBo> rules = new ArrayList<AccrualCategoryRuleBo>();
		
		Criteria root = new Criteria();
		root.addEqualTo("lmAccrualCategoryId", accrualCategoryId);
		root.addEqualTo("active", false);
		Query query = QueryFactory.newQuery(AccrualCategoryRuleBo.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			rules.addAll(c);
		}
		return rules;
    }
 
}
