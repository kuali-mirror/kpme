/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.graceperiod.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class GracePeriodDaoImpl extends PlatformAwareDaoBaseOjb implements GracePeriodDao {
	public GracePeriodRule getGracePeriodRule(Date asOfDate){
		GracePeriodRule gracePeriodRule = null;
		
		Criteria root = new Criteria();

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(GracePeriodRule.class, asOfDate, Collections.EMPTY_LIST, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(GracePeriodRule.class, Collections.EMPTY_LIST, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(GracePeriodRule.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			gracePeriodRule = (GracePeriodRule) obj;
		}
		
		return gracePeriodRule;
	}

	@Override
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkGracePeriodRuleId", tkGracePeriodId);
		
		Query query = QueryFactory.newQuery(GracePeriodRule.class, crit);
		return (GracePeriodRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<GracePeriodRule> getGracePeriodRules(String hourFactor, String active) {
        List<GracePeriodRule> results = new ArrayList<GracePeriodRule>();
        
        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(hourFactor)) {
            root.addLike("hourFactor", hourFactor);
        }
        
        if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }
        
        Query query = QueryFactory.newQuery(GracePeriodRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
	
}
