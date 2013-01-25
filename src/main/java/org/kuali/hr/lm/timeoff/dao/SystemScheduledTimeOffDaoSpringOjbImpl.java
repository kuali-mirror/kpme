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
package org.kuali.hr.lm.timeoff.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SystemScheduledTimeOffDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements SystemScheduledTimeOffDao {

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmSystemScheduledTimeOffId", lmSystemScheduledTimeOffId);
		Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
		return (SystemScheduledTimeOff) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, Date startDate, Date endDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addBetween("accruedDate", startDate, endDate);
		return (List<SystemScheduledTimeOff>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(SystemScheduledTimeOff.class, root));
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, Date startDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addEqualTo("accruedDate", startDate);
		return (SystemScheduledTimeOff)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(SystemScheduledTimeOff.class, root));
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(Date fromEffdt, Date toEffdt, String earnCode, Date fromAccruedDate,Date toAccruedDate, 
    															   Date fromSchTimeOffDate, Date toSchTimeOffDate, String active, String showHistory) {
        
    	List<SystemScheduledTimeOff> results = new ArrayList<SystemScheduledTimeOff>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(earnCode)) {
            root.addLike("earnCode", earnCode);
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

        if (fromAccruedDate != null) {
            root.addGreaterOrEqualThan("accruedDate", fromAccruedDate);
        }
        if (toAccruedDate != null) {
            root.addLessOrEqualThan("accruedDate", toAccruedDate);
        }

        if (fromSchTimeOffDate != null) {
            root.addGreaterOrEqualThan("scheduledTimeOffDate", fromSchTimeOffDate);
        }
        if (toSchTimeOffDate != null) {
            root.addLessOrEqualThan("scheduledTimeOffDate", toSchTimeOffDate);
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
            effdt.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdt.addEqualToField("accruedDate", Criteria.PARENT_QUERY_PREFIX + "accruedDate");
            effdt.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
            root.addEqualTo("effectiveDate", effdtSubQuery);
            
            Criteria timestamp = new Criteria();
            timestamp.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestamp.addEqualToField("accruedDate", Criteria.PARENT_QUERY_PREFIX + "accruedDate");
            timestamp.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            root.addEqualTo("timestamp", timestampSubQuery);
        }
        
        Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
	
	@Override
    @SuppressWarnings("unchecked")
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffsForLeavePlan(Date fromAccruedDate,Date toAccruedDate, String leavePlan) {
    	List<SystemScheduledTimeOff> results = new ArrayList<SystemScheduledTimeOff>();
    	Criteria root = new Criteria();

        if (fromAccruedDate != null) {
            root.addGreaterOrEqualThan("accruedDate", fromAccruedDate);
        }
        if (toAccruedDate != null) {
            root.addLessOrEqualThan("accruedDate", toAccruedDate);
        }
        
        if(StringUtils.isNotEmpty(leavePlan)) {
        	root.addEqualTo("leavePlan", leavePlan);
        }
    	Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
       
        Criteria effdt = new Criteria();
        effdt.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
        effdt.addEqualToField("accruedDate", Criteria.PARENT_QUERY_PREFIX + "accruedDate");
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
        root.addEqualTo("effectiveDate", effdtSubQuery);
        
        Criteria timestamp = new Criteria();
        timestamp.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
        timestamp.addEqualToField("accruedDate", Criteria.PARENT_QUERY_PREFIX + "accruedDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
        root.addEqualTo("timestamp", timestampSubQuery);
        
        Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

}
