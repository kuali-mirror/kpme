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
package org.kuali.kpme.tklm.time.rules.timecollection.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeCollectionRuleDaoServiceImpl extends PlatformAwareDaoBaseOjb implements TimeCollectionRuleDaoService {

    @Override
    public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("tkTimeCollectionRuleId", tkTimeCollectionRuleId);

        Query query = QueryFactory.newQuery(TimeCollectionRule.class, crit);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

    /* Jira 1152
      * Returns valid TimeCollectionRule based on dept, workArea, payType, groupKeyCode, and asOfDate
      * dept, work area, and payType can be wildcardable values
      */
    @Override
    public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, String groupKeyCode, LocalDate asOfDate) {


        TimeCollectionRule timeCollectionRule = new TimeCollectionRule();

        //First call confirm no exact match
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, payType, groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }
        //Try with dept wildcarded *
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, payType, groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with work area wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, payType, groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, workArea, "%", groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with dept and workArea wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", -1L, payType, groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with dept and payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded("%", workArea, "%", groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with workArea and payType wildcarded
        timeCollectionRule = getTimeCollectionRuleWildCarded(dept, -1L, "%", groupKeyCode, asOfDate);
        if (timeCollectionRule != null) {
            return timeCollectionRule;
        }

        //Try with everything wildcarded
        return getTimeCollectionRuleWildCarded("%", -1L, "%", groupKeyCode, asOfDate);
    }

    private TimeCollectionRule getTimeCollectionRuleWildCarded(String dept, Long workArea, String payType, String groupKeyCode, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("dept", dept);
        root.addEqualTo("workArea", workArea);
        root.addEqualTo("payType", payType);
        root.addEqualTo("groupKeyCode", groupKeyCode);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TimeCollectionRule.class, asOfDate, TimeCollectionRule.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TimeCollectionRule.class, TimeCollectionRule.BUSINESS_KEYS, false));
//		root.addEqualTo("active", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        return (TimeCollectionRule) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }

	@Override
    @SuppressWarnings("unchecked")
    public List<TimeCollectionRule> getTimeCollectionRules(String dept, Long workArea, String payType, String groupKeyCode, String active, String showHistory) {
        List<TimeCollectionRule> results = new ArrayList<TimeCollectionRule>();

        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (workArea != null) {
            OjbSubQueryUtil.addNumericCriteria(root, "workArea", workArea.toString());
        }
        
        if (StringUtils.isNotBlank(payType)) {
            root.addLike("payType", payType);
        }
        
        if (StringUtils.isNotBlank(groupKeyCode)) {
        	root.addLike("groupKeyCode", groupKeyCode);
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(TimeCollectionRule.class, TimeCollectionRule.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(TimeCollectionRule.class, TimeCollectionRule.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(TimeCollectionRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
}
