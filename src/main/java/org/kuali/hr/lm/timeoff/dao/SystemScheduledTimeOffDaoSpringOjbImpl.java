/**
 * Copyright 2004-2012 The Kuali Foundation
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
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SystemScheduledTimeOffDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements SystemScheduledTimeOffDao {

	private static final Logger LOG = Logger.getLogger(SystemScheduledTimeOffDaoSpringOjbImpl.class);

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
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(Date fromEffdt, Date toEffdt, String earnCode, String fromAccruedDate,
                                                                   String toAccruedDate, String fromSchTimeOffDate, String toSchTimeOffDate, String active, String showHistory) {
        Criteria crit = new Criteria();
        Criteria effdtCrit = new Criteria();
        Criteria timestampCrit = new Criteria();

        List<SystemScheduledTimeOff> results = new ArrayList<SystemScheduledTimeOff>();

        if (StringUtils.isNotEmpty(earnCode)) {
            crit.addLike("earnCode", earnCode);
        }
        //Effective Date criteria
        if (fromEffdt != null) {
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if (toEffdt != null) {
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }
        //Accrued Date Criteria
        if (fromAccruedDate != null && StringUtils.isNotBlank(fromAccruedDate)) {
            crit.addGreaterOrEqualThan("accruedDate", fromAccruedDate);
        }
        if (toAccruedDate != null && StringUtils.isNotBlank(toAccruedDate)) {
            crit.addLessOrEqualThan("accruedDate", toAccruedDate);
        } //else {
//            crit.addLessOrEqualThan("accruedDate", TKUtils.getCurrentDate());
//        }
        // Scheduled Time Off Date Criteria
        if (fromSchTimeOffDate != null && StringUtils.isNotBlank(fromSchTimeOffDate)) {
            crit.addGreaterOrEqualThan("scheduledTimeOffDate", fromSchTimeOffDate);
        }
        if (toSchTimeOffDate != null && StringUtils.isNotBlank(toSchTimeOffDate)) {
            crit.addLessOrEqualThan("scheduledTimeOffDate", toSchTimeOffDate);
        } //else {
//            crit.addLessOrEqualThan("scheduledTimeOffDate", TKUtils.getCurrentDate());
//        }

        if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "Y")) {

//            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
//            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdtCrit);
//            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
//
//            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
//            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestampCrit);
//            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
//
//            crit.addEqualTo("effectiveDate", effdtSubQuery);
//            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")) {

            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});

            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)) {
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});

            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);

            Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if (StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")) {
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});

            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")) {
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});

            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemScheduledTimeOff.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);

        }
        return results;
    }

}
