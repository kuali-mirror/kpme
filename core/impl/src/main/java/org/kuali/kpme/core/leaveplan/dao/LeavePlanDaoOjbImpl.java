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
package org.kuali.kpme.core.leaveplan.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeavePlanDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LeavePlanDao {
   
	@Override
	public LeavePlanBo getLeavePlan(String lmLeavePlanId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeavePlanId", lmLeavePlanId);
		Query query = QueryFactory.newQuery(LeavePlanBo.class, crit);
		return (LeavePlanBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


    @Override
    public List<LeavePlanBo> getLeavePlans(List<String> leavePlan, LocalDate asOfDate) {
        LeavePlanBo lp = null;

        Criteria root = new Criteria();
        root.addIn("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LeavePlanBo.class, asOfDate, LeavePlanBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeavePlanBo.class, LeavePlanBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(LeavePlanBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        List<LeavePlanBo> lps = new LinkedList<LeavePlanBo>();
        if (c != null) {
            lps.addAll(c);
        }
        return lps;
    }

	@Override
	public LeavePlanBo getLeavePlan(String leavePlan, LocalDate asOfDate) {
		LeavePlanBo lp = null;

		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LeavePlanBo.class, asOfDate, LeavePlanBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeavePlanBo.class, LeavePlanBo.BUSINESS_KEYS, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(LeavePlanBo.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			lp = (LeavePlanBo) obj;
		}

		return lp;
	}

	@Override
	public int getNumberLeavePlan(String leavePlan) {
		Criteria crit = new Criteria();
		crit.addEqualTo("leavePlan", leavePlan);
		Query query = QueryFactory.newQuery(LeavePlanBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	@Override
	public List<LeavePlanBo> getAllActiveLeavePlan(String leavePlan, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", true);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());

        Query query = QueryFactory.newQuery(LeavePlanBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlanBo> lps = new LinkedList<LeavePlanBo>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
		
	}
	
	@Override
	public List<LeavePlanBo> getAllInActiveLeavePlan(String leavePlan, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", false);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
        
        Query query = QueryFactory.newQuery(LeavePlanBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlanBo> lps = new LinkedList<LeavePlanBo>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<LeavePlanBo> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, LocalDate fromEffdt, LocalDate toEffdt,
    									 String active, String showHistory) {

        List<LeavePlanBo> results = new ArrayList<LeavePlanBo>();
        
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(leavePlan)) {
        	root.addLike("UPPER(leavePlan)", leavePlan.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(calendarYearStart)) {
        	root.addEqualTo("calendarYearStart", calendarYearStart);
        }
        
        if (StringUtils.isNotBlank(descr)) {
        	root.addLike("UPPER(descr)", descr.toUpperCase()); // KPME-2695
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(LeavePlanBo.class, effectiveDateFilter, LeavePlanBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeavePlanBo.class, LeavePlanBo.BUSINESS_KEYS, false));
        }

        Query query = QueryFactory.newQuery(LeavePlanBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

	@Override
	public List<LeavePlanBo> getLeavePlansNeedsScheduled(int thresholdDays,
			LocalDate asOfDate) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd");
        DateTime current = asOfDate.toDateTimeAtStartOfDay();
        DateTime windowStart = current.minusDays(thresholdDays);
        DateTime windowEnd = current.plusDays(thresholdDays);
        Criteria root = new Criteria();
        String start = fmt.print(windowStart);
        String end = fmt.print(windowEnd);

        if (end.compareTo(start) >=0) {
            root.addGreaterOrEqualThan("batchPriorYearCarryOverStartDate", start);
            root.addLessOrEqualThan("batchPriorYearCarryOverStartDate", end);
        } else {
            Criteria subCrit = new Criteria();
            Criteria beginCrit = new Criteria();
            Criteria endCrit = new Criteria();
            beginCrit.addGreaterOrEqualThan("batchPriorYearCarryOverStartDate", start);
            endCrit.addLessOrEqualThanField("batchPriorYearCarryOverStartDate", end);
            subCrit.addOrCriteria(beginCrit);
            subCrit.addOrCriteria(endCrit);
            root.addAndCriteria(subCrit);
        }

        root.addEqualTo("active", true);

        Query query = QueryFactory.newQuery(LeavePlanBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        List<LeavePlanBo> leavePlans = new ArrayList<LeavePlanBo>(c.size());
        leavePlans.addAll(c);

        return leavePlans;
	}
    
}