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
package org.kuali.hr.lm.leaveplan.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeavePlanDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeavePlanDao {

	@Override
	public LeavePlan getLeavePlan(String lmLeavePlanId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeavePlanId", lmLeavePlanId);
		Query query = QueryFactory.newQuery(LeavePlan.class, crit);
		return (LeavePlan) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate) {
		LeavePlan lp = null;

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(LeavePlan.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(LeavePlan.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("leavePlan", leavePlan);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		
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
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", true);
        root.addLessOrEqualThan("effectiveDate", asOfDate);

        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<LeavePlan> lps = new LinkedList<LeavePlan>();
        if (c != null) {
        	lps.addAll(c);
        }
        return lps;
		
	}
	
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("active", false);
        root.addLessOrEqualThan("effectiveDate", asOfDate);
        
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
    public List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, Date fromEffdt, Date toEffdt, 
    									 String active, String showHistory) {

        List<LeavePlan> results = new ArrayList<LeavePlan>();
        
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(leavePlan)) {
        	root.addEqualTo("leavePlan", leavePlan);
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

        if (StringUtils.equals(showHistory, "N")) {
            Criteria effdt = new Criteria();
            Criteria timestamp = new Criteria();
            effdt.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
            effdt.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(LeavePlan.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
            root.addEqualTo("effectiveDate", effdtSubQuery);

            timestamp.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
            timestamp.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(LeavePlan.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            root.addEqualTo("timestamp", timestampSubQuery);
        }

        Query query = QueryFactory.newQuery(LeavePlan.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
    
}