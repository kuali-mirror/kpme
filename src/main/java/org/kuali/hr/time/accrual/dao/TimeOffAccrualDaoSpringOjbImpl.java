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
package org.kuali.hr.time.accrual.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.accrual.TimeOffAccrual;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimeOffAccrualDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TimeOffAccrualDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("principalId")
            .add("accrualCategory")
            .build();

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimeOffAccrualDaoSpringOjbImpl.class);

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
		root.addEqualTo("principalId", principalId);

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(TimeOffAccrual.class, EQUAL_TO_FIELDS, false));

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
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(TimeOffAccrual.class, asOfDate, EQUAL_TO_FIELDS, false));

        root.addEqualTo("principalId", principalId);

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

    @Override
    public List<TimeOffAccrual> getTimeOffAccruals(String principalId, String accrualCategory) {
        Criteria crit = new Criteria();

        List<TimeOffAccrual> results = new ArrayList<TimeOffAccrual>();

        if(StringUtils.isNotBlank(principalId) && StringUtils.isNotEmpty(principalId)){
            crit.addLike("principalId", principalId);
        }
        if(StringUtils.isNotBlank(accrualCategory)){
            crit.addLike("accrualCategory", accrualCategory);
        }

        Query query = QueryFactory.newQuery(TimeOffAccrual.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        results.addAll(c);

        return results;
    }
}
