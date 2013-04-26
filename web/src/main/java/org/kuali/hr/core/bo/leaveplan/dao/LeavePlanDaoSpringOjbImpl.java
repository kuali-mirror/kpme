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
package org.kuali.hr.core.bo.leaveplan.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.leaveplan.LeavePlan;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class LeavePlanDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeavePlanDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("leavePlan")
            .build();

	@Override
	public LeavePlan getLeavePlan(String lmLeavePlanId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeavePlanId", lmLeavePlanId);
		Query query = QueryFactory.newQuery(LeavePlan.class, crit);
		return (LeavePlan) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public LeavePlan getLeavePlan(String leavePlan, LocalDate asOfDate) {
		LeavePlan lp = null;

		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LeavePlan.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeavePlan.class, EQUAL_TO_FIELDS, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(LeavePlan.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			lp = (LeavePlan) obj;
		}

		return lp;
	}

	@Override
	public int getNumberLeavePlan(String leavePlan) {
		Criteria crit = new Criteria();
		crit.addEqualTo("leavePlan", leavePlan);
		Query query = QueryFactory.newQuery(LeavePlan.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	@Override
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", true);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());

        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlan> lps = new LinkedList<LeavePlan>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
		
	}
	
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", false);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
        
        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlan> lps = new LinkedList<LeavePlan>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, LocalDate fromEffdt, LocalDate toEffdt, 
    									 String active, String showHistory) {

        List<LeavePlan> results = new ArrayList<LeavePlan>();
        
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(leavePlan)) {
        	root.addLike("leavePlan", leavePlan);
        }
        
        if (StringUtils.isNotBlank(calendarYearStart)) {
        	root.addEqualTo("calendarYearStart", calendarYearStart);
        }
        
        if (StringUtils.isNotBlank(descr)) {
        	root.addLike("descr", descr);
        }
        
        if (StringUtils.isNotBlank(planningMonths)) {
        	root.addEqualTo("planningMonths", planningMonths);
        }
        
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(LeavePlan.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeavePlan.class, EQUAL_TO_FIELDS, false));
        }

        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

	@Override
	public List<LeavePlan> getLeavePlansNeedsScheduled(int thresholdDays,
			LocalDate asOfDate) {

        Criteria root = new Criteria();

        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        List<LeavePlan> leavePlans = new ArrayList<LeavePlan>(c.size());
        leavePlans.addAll(c);

        return leavePlans;
	}
    
}