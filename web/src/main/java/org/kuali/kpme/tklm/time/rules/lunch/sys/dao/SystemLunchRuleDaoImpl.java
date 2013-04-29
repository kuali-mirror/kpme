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
package org.kuali.kpme.tklm.time.rules.lunch.sys.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.tklm.time.rules.lunch.sys.SystemLunchRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SystemLunchRuleDaoImpl  extends PlatformAwareDaoBaseOjb implements SystemLunchRuleDao {

	@Override
	public SystemLunchRule getSystemLunchRule(LocalDate asOfDate) {
		Criteria root = new Criteria();

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(SystemLunchRule.class, asOfDate, Collections.EMPTY_LIST, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SystemLunchRule.class, Collections.EMPTY_LIST, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(SystemLunchRule.class, root);
		return (SystemLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public SystemLunchRule getSystemLunchRule(String tkSystemLunchRuleId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkSystemLunchRuleId", tkSystemLunchRuleId);
		
		Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
		
		return (SystemLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<SystemLunchRule> getSystemLunchRules(LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<SystemLunchRule> results = new ArrayList<SystemLunchRule>();
        
        Criteria root = new Criteria();

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);
        
        if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }

        if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(SystemLunchRule.class, effectiveDateFilter, Collections.EMPTY_LIST, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SystemLunchRule.class, Collections.EMPTY_LIST, false));
        }
        
        Query query = QueryFactory.newQuery(SystemLunchRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}
