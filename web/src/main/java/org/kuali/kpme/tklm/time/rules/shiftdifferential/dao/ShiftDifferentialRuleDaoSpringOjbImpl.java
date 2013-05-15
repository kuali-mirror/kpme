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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ShiftDifferentialRuleDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements ShiftDifferentialRuleDao {
	
	@Override
	public ShiftDifferentialRule findShiftDifferentialRule(String id) {
		Object o = this.getPersistenceBrokerTemplate().getObjectById(ShiftDifferentialRule.class, id);
		
		return (ShiftDifferentialRule)o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShiftDifferentialRule> findShiftDifferentialRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, LocalDate asOfDate) {
		List<ShiftDifferentialRule> list = new ArrayList<ShiftDifferentialRule>();

		Criteria root = new Criteria();

		root.addEqualTo("location", location);
		root.addEqualTo("hrSalGroup", hrSalGroup);
		root.addEqualTo("payGrade", payGrade);
		root.addEqualTo("pyCalendarGroup", pyCalendarGroup);

//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("location")
//                .add("hrSalGroup")
//                .add("payGrade")
//                .add("pyCalendarGroup")
//                .add("earnCode")
//                .build();
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(ShiftDifferentialRule.class, asOfDate, ShiftDifferentialRule.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(ShiftDifferentialRule.class, ShiftDifferentialRule.EQUAL_TO_FIELDS, false));


		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(ShiftDifferentialRule.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			list.addAll(c);
		}
		
		return list;
	}

	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule) {
		this.getPersistenceBrokerTemplate().store(shiftDifferentialRule);
	}

	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules) {
		for (ShiftDifferentialRule sdr : shiftDifferentialRules) {
			saveOrUpdate(sdr);
		}
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<ShiftDifferentialRule> getShiftDifferentialRules(String location, String hrSalGroup, String payGrade, LocalDate fromEffdt, LocalDate toEffdt, 
    															 String active, String showHistory) {
    	
		List<ShiftDifferentialRule> results = new ArrayList<ShiftDifferentialRule>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(location)) {
            root.addLike("location", location);
        }
        
        if (StringUtils.isNotBlank(hrSalGroup)) {
            root.addLike("hrSalGroup", hrSalGroup);
        }
        
        if (StringUtils.isNotBlank(payGrade)) {
            root.addLike("payGrade", payGrade);
        }

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", DateTime.now().toDate());
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
            root.addAndCriteria(effectiveDateFilter);
        }
        
        Query query = QueryFactory.newQuery(ShiftDifferentialRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}