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
package org.kuali.hr.time.overtime.weekly.rule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class WeeklyOvertimeRuleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements WeeklyOvertimeRuleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WeeklyOvertimeRule> findWeeklyOvertimeRules(Date asOfDate) {
		List<WeeklyOvertimeRule> list = new ArrayList<WeeklyOvertimeRule>();

		Criteria root = new Criteria();

        ImmutableList<String> fields = new ImmutableList.Builder<String>()
                .add("convertFromEarnGroup")
                .add("convertToEarnCode")
                .add("maxHoursEarnGroup")
                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WeeklyOvertimeRule.class, asOfDate, fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WeeklyOvertimeRule.class, fields, false));
//		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		QueryByCriteria query = new QueryByCriteria(WeeklyOvertimeRule.class, root);
		query.addOrderByAscending("step");

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			list.addAll(c);
		}
				
		return list;
	}

	@Override
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule) {
		this.getPersistenceBrokerTemplate().store(weeklyOvertimeRule);
	}

	@Override
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules) {
		for (WeeklyOvertimeRule wor : weeklyOvertimeRules) {
			saveOrUpdate(wor);
		}
	}

	@Override
	public WeeklyOvertimeRule getWeeklyOvertimeRule(String tkWeeklyOvertimeRuleId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkWeeklyOvertimeRuleId", tkWeeklyOvertimeRuleId);
		
		Query query = QueryFactory.newQuery(WeeklyOvertimeRule.class, crit);
		return (WeeklyOvertimeRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
