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
package org.kuali.kpme.tklm.time.rules.timecollection.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class TimeCollectionRuleDaoServiceImpl extends PlatformAwareDaoBaseOjb implements TimeCollectionRuleDaoService {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("workArea")
            .add("dept")
            .add("payType")
            .build();

    /*
      * Returns valid TimeCollectionRule based on dept,workArea, and asOfDate
      * dept and work area are wildcardable values
      * @see org.kuali.kpme.tklm.time.rules.timecollection.dao.TimeCollectionRuleDaoService#getTimeCollectionRule(java.lang.String dept,
      * java.lang.Long workArea, org.joda.time.LocalDate asOfDate)
      */

    @Override
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, LocalDate asOfDate) {


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

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, LocalDate asOfDate) {
        Criteria root = new Criteria();
        ImmutableList<String> fields = new ImmutableList.Builder<String>()
                .add("workArea")
                .add("dept")
                .build();

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TimeCollectionRule.class, asOfDate, fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TimeCollectionRule.class, fields, false));
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
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, LocalDate asOfDate) {


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

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, String payType, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("payType", payType);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TimeCollectionRule.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TimeCollectionRule.class, EQUAL_TO_FIELDS, false));
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(TimeCollectionRule.class, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TimeCollectionRule.class, EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
}
