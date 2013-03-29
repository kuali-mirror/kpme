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
package org.kuali.hr.time.overtime.daily.rule.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class DailyOvertimeRuleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements DailyOvertimeRuleDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("location")
            .add("payType")
            .add("dept")
            .add("workArea")
            .build();

	@Override
	public DailyOvertimeRule findDailyOvertimeRule(String location, String paytype, String dept, Long workArea, Date asOfDate) {
		DailyOvertimeRule dailyOvertimeRule;

		Criteria root = new Criteria();
        java.sql.Date effDate = null;
        if (asOfDate != null) {
            effDate = new java.sql.Date(asOfDate.getTime());
        }
		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("location", location);
		root.addEqualTo("paytype", paytype);
		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DailyOvertimeRule.class, effDate, EQUAL_TO_FIELDS, false));
		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DailyOvertimeRule.class, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(DailyOvertimeRule.class, root);
		dailyOvertimeRule = (DailyOvertimeRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return dailyOvertimeRule;
	}

	@Override
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule) {
		this.getPersistenceBrokerTemplate().store(dailyOvertimeRule);
	}

	@Override
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules) {
		for (DailyOvertimeRule dor : dailyOvertimeRules) {
			saveOrUpdate(dor);
		}
	}

	@Override
	public DailyOvertimeRule getDailyOvertimeRule(String tkDailyOvertimeRuleId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkDailyOvertimeRuleId", tkDailyOvertimeRuleId);
		
		Query query = QueryFactory.newQuery(DailyOvertimeRule.class, crit);
		return (DailyOvertimeRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public List<DailyOvertimeRule> getDailyOvertimeRules(String dept, String workArea, String location, Date fromEffdt, Date toEffdt, String active, String showHist) {
        List<DailyOvertimeRule> results = new ArrayList<DailyOvertimeRule>();
        
        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (StringUtils.isNotBlank(workArea)) {
            root.addLike("workArea", workArea);
        }
        

        if (StringUtils.isNotBlank(location)) {
            root.addLike("location", location);
        }
        
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

        if (StringUtils.equals(showHist, "N")) {
    		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(DailyOvertimeRule.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
    		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DailyOvertimeRule.class, EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(DailyOvertimeRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

}
