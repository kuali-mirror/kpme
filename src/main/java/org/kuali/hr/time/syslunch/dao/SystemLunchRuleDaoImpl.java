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
package org.kuali.hr.time.syslunch.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SystemLunchRuleDaoImpl  extends PlatformAwareDaoBaseOjb implements SystemLunchRuleDao {

	@Override
	public SystemLunchRule getSystemLunchRule(Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        //effdt.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

        //timestamp.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

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
    public List<SystemLunchRule> getSystemLunchRules(Date fromEffdt, Date toEffdt, String active, String showHistory) {
        List<SystemLunchRule> results = new ArrayList<SystemLunchRule>();
        
        Criteria root = new Criteria();

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt);
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
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
            Criteria effdt = new Criteria();
            effdt.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
            root.addEqualTo("effectiveDate", effdtSubQuery);
            
            Criteria timestamp = new Criteria();
            timestamp.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            root.addEqualTo("timestamp", timestampSubQuery);
        }
        
        Query query = QueryFactory.newQuery(SystemLunchRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}
