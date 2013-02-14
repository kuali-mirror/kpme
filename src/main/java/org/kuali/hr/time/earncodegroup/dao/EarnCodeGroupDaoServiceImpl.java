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
package org.kuali.hr.time.earncodegroup.dao;

import java.sql.Date;

import com.google.common.collect.ImmutableList;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.earncodegroup.EarnCodeGroupDefinition;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EarnCodeGroupDaoServiceImpl extends PlatformAwareDaoBaseOjb implements EarnCodeGroupDaoService {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("earnCodeGroup")
            .build();

	@Override
	public EarnCodeGroup getEarnCodeGroup(String earnGroup, Date asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("earnCodeGroup", earnGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroup.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroup.class, EQUAL_TO_FIELDS, false));
//		root.addEqualTo("active", true);
		//do not include the summary setup earn groups

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
				
		Query query = QueryFactory.newQuery(EarnCodeGroup.class, root);
		EarnCodeGroup earnGroupObj  = (EarnCodeGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnCodeGroup getEarnCodeGroupSummaryForEarnCode(String earnCode, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria earnCodeJoin = new Criteria();
		
		earnCodeJoin.addEqualToField("hrEarnCodeGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnCodeGroupId");
		earnCodeJoin.addEqualTo("earnCode", earnCode);
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnCodeGroupDefinition.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_code_group_id"});
		
		root.addEqualTo("hrEarnCodeGroupId",earnCodeSubQuery);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroup.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroup.class, EQUAL_TO_FIELDS, false));
//		root.addEqualTo("active", true);
		root.addEqualTo("showSummary", true);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(EarnCodeGroup.class, root);
		EarnCodeGroup earnGroupObj  = (EarnCodeGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnCodeGroup getEarnCodeGroupForEarnCode(String earnCode, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria earnCodeJoin = new Criteria();

		earnCodeJoin.addEqualToField("hrEarnCodeGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnCodeGroupId");
		earnCodeJoin.addEqualTo("earnCode", earnCode);
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnCodeGroupDefinition.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_code_group_id"});
		
		root.addEqualTo("hrEarnCodeGroupId",earnCodeSubQuery);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroup.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroup.class, EQUAL_TO_FIELDS, false));
//		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(EarnCodeGroup.class, root);
		EarnCodeGroup earnGroupObj  = (EarnCodeGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnCodeGroup getEarnCodeGroup(String hrEarnCodeGroupId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrEarnCodeGroupId", hrEarnCodeGroupId);
		
		Query query = QueryFactory.newQuery(EarnCodeGroup.class, crit);
		return (EarnCodeGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getEarnCodeGroupCount(String earnCodeGroup) {
		Criteria crit = new Criteria();
	    crit.addEqualTo("earnCodeGroup", earnCodeGroup);
	    Query query = QueryFactory.newQuery(EarnCodeGroup.class, crit);
	    return this.getPersistenceBrokerTemplate().getCount(query);
	}
	@Override
	public int getNewerEarnCodeGroupCount(String earnCodeGroup, Date effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCodeGroup", earnCodeGroup);
		crit.addEqualTo("active", "Y");
		crit.addGreaterThan("effectiveDate", effdt);
		Query query = QueryFactory.newQuery(EarnCodeGroup.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
