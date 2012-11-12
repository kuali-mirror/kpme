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
package org.kuali.hr.time.paytype.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PayTypeDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements PayTypeDao {

	private static final Logger LOG = Logger.getLogger(PayTypeDaoSpringOjbImpl.class);

	public void saveOrUpdate(PayType payType) {
		this.getPersistenceBrokerTemplate().store(payType);
	}

	public void saveOrUpdate(List<PayType> payTypeList) {
		if (payTypeList != null) {
			for (PayType payType : payTypeList) {
				this.getPersistenceBrokerTemplate().store(payType);
			}
		}
	}

	public PayType getPayType(String payType, Date effectiveDate) {
		Criteria currentRecordCriteria = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		
		effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
		effdt.addLessOrEqualThan("effectiveDate", effectiveDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayType.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayType.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		currentRecordCriteria.addEqualTo("payType", payType);
		currentRecordCriteria.addEqualTo("effectiveDate", effdtSubQuery);
		currentRecordCriteria.addEqualTo("timestamp", timestampSubQuery);
		
//		Criteria activeFilter = new Criteria(); // Inner Join For Activity
//		activeFilter.addEqualTo("active", true);
//		currentRecordCriteria.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PayType.class, currentRecordCriteria);
		return (PayType)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


	@Override
	public PayType getPayType(String hrPayTypeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayTypeId", hrPayTypeId);
		
		Query query = QueryFactory.newQuery(PayType.class, crit);
		return (PayType)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getPayTypeCount(String payType) {
		Criteria crit = new Criteria();
		crit.addEqualTo("payType", payType);
		Query query = QueryFactory.newQuery(PayType.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

    @Override
    public List<PayType> getPayTypes(String payType, String regEarnCode, String descr, Date fromEffdt, Date toEffdt, String active, String showHistory) {

        Criteria crit = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        List<PayType> results = new ArrayList<PayType>();
        if(StringUtils.isNotBlank(regEarnCode)){
            crit.addLike("regEarnCode", regEarnCode);
        }
        if(StringUtils.isNotBlank(payType) && StringUtils.isNotEmpty(payType)){
            crit.addLike("payType", payType);
        }
        if(StringUtils.isNotBlank(descr)){
            crit.addLike("descr", descr);
        }
        if(fromEffdt != null){
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if(toEffdt != null){
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory,"Y")){
            Query query = QueryFactory.newQuery(PayType.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        else if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")){
            effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayType.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayType.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(PayType.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
            effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayType.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayType.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(PayType.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(PayType.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(PayType.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
            effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            if(toEffdt != null){
                effdt.addLessOrEqualThan("effectiveDate", toEffdt);
            }
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayType.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayType.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(PayType.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);

        }
        return results;
    }
}
