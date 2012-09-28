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
package org.kuali.hr.time.accrual.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeOffAccrualkDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TimeOffAccrualDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimeOffAccrualkDaoSpringOjbImpl.class);

	public void saveOrUpdate(TimeOffAccrual timeOffAccrual) {
		this.getPersistenceBrokerTemplate().store(timeOffAccrual);
	}

	public void saveOrUpdate(List<TimeOffAccrual> timeOffAccrualList) {
		if (timeOffAccrualList != null) {
			for (TimeOffAccrual timeOffAccrual : timeOffAccrualList) {
				this.getPersistenceBrokerTemplate().store(timeOffAccrual);
			}
		}
	}
	
	public List<TimeOffAccrual> getTimeOffAccruals (String principalId) {
		
		List<TimeOffAccrual> timeOffAccruals = new LinkedList<TimeOffAccrual>();
		
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdt.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeOffAccrual.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);

		Query query = QueryFactory.newQuery(TimeOffAccrual.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			timeOffAccruals.addAll(c);
		}

		return timeOffAccruals;
	}

	@Override
	public TimeOffAccrual getTimeOffAccrual(Long laTimeOffAccrualId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualId", laTimeOffAccrualId);
		
		Query query = QueryFactory.newQuery(TimeOffAccrual.class, crit);
		return (TimeOffAccrual)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

	}
	
	// KPME-1011
	public List<TimeOffAccrual> getActiveTimeOffAccruals (String principalId, Date asOfDate) {
			List<TimeOffAccrual> timeOffAccruals = new LinkedList<TimeOffAccrual>();
			
			Criteria root = new Criteria();
			Criteria effdt = new Criteria();

			// OJB's awesome sub query setup part 1
			effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
			effdt.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
			effdt.addLessOrEqualThan("effectiveDate", asOfDate);
			ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(TimeOffAccrual.class, effdt);
			effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

			root.addEqualTo("principalId", principalId);
			root.addEqualTo("effectiveDate", effdtSubQuery);
			
			Query query = QueryFactory.newQuery(TimeOffAccrual.class, root);
			Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
			
			if (c != null) {
				timeOffAccruals.addAll(c);
			}
			
			return timeOffAccruals;
		}
	
	@Override
	public int getTimeOffAccrualCount(String accrualCategory, Date effectiveDate, String principalId, String lmAccrualId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("accrualCategory", accrualCategory);
		crit.addEqualTo("effectiveDate", effectiveDate);
		crit.addEqualTo("principalId", principalId);
		if(lmAccrualId != null) {
			crit.addEqualTo("lmAccrualId", lmAccrualId);
		}
		Query query = QueryFactory.newQuery(TimeOffAccrual.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
