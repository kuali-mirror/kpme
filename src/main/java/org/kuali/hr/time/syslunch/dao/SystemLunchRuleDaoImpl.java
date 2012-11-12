/**
 * Copyright 2004-2012 The Kuali Foundation
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
import java.util.Collection;
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
    public List<SystemLunchRule> getSystemLunchRules(Date fromEffdt, Date toEffdt, String active, String showHistory) {

        Criteria crit = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        List<SystemLunchRule> results = new ArrayList<SystemLunchRule>();

        if(fromEffdt != null){
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if(toEffdt != null){
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory,"Y")){
            Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        // $$$
        else if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")){
//            effdt.addEqualToField("tkShiftDiffRuleId", Criteria.PARENT_QUERY_PREFIX + "tkShiftDiffRuleId");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
            effdt.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
            effdt.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);

        }
        return results;

    }

}
