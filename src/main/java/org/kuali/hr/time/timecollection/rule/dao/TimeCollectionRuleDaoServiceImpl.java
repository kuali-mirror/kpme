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
package org.kuali.hr.time.timecollection.rule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeCollectionRuleDaoServiceImpl extends PlatformAwareDaoBaseOjb implements TimeCollectionRuleDaoService {

    /*
      * Returns valid TimeCollectionRule based on dept,workArea, and asOfDate
      * dept and work area are wildcardable values
      * @see org.kuali.hr.time.timecollection.rule.dao.TimeCollectionRuleDaoService#getTimeCollectionRule(java.lang.String dept,
      * java.lang.Long workArea, java.sql.Date asOfDate)
      */

    @Override
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, Date asOfDate) {


        TimeCollectionRule timeCollectionRule = new TimeCollectionRule();

        //First call confirm no exact match
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }
        //Try with dept wildcarded *
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with work area wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with everything wildcarded
        return getTimeCollectionRuleWildCarded("%", -1L, asOfDate);
    }

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
        effdt.addEqualTo("dept", dept);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
        timestamp.addEqualTo("dept", dept);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

    @Override
    public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("tkTimeCollectionRuleId", tkTimeCollectionRuleId);

        Query query = QueryFactory.newQuery(TimeCollectionRule.class, crit);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

    /* Jira 1152
      * Returns valid TimeCollectionRule based on dept, workArea, payType, and asOfDate
      * dept, work area, and payType can be wildcardable values
      */
    @Override
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, Date asOfDate) {


        TimeCollectionRule timeCollectionRule = new TimeCollectionRule();

        //First call confirm no exact match
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }
        //Try with dept wildcarded *
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with work area wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with dept and workArea wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", -1L, payType, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with dept and payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with workArea and payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, "%", asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with everything wildcarded
        return getTimeCollectionRuleWildCarded("%", -1L, "%", asOfDate);
    }

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, String payType, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
        effdt.addEqualTo("dept", dept);
        effdt.addEqualTo("payType", payType);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
        timestamp.addEqualTo("dept", dept);
        timestamp.addEqualTo("payType", payType);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("payType", payType);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

	@Override
    @SuppressWarnings("unchecked")
    public List<TimeCollectionRule> getTimeCollectionRules(String dept, Long workArea, String payType, String active, String showHistory) {
        List<TimeCollectionRule> results = new ArrayList<TimeCollectionRule>();

        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (workArea != null) {
            root.addLike("workArea", workArea);
        }
        
        if (StringUtils.isNotBlank(payType)) {
            root.addLike("payType", payType);
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
        
        if (StringUtils.equals(showHistory, "N")) {
	        Criteria effdt = new Criteria();
	        effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
	        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
	        effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
	        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, effdt);
	        effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
	        root.addEqualTo("effectiveDate", effdtSubQuery);
	        
	        Criteria timestamp = new Criteria();
	        timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
	        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
	        timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
	        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
	        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(TimeCollectionRule.class, timestamp);
	        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
	        root.addEqualTo("timestamp", timestampSubQuery);
        }
        
        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
}
