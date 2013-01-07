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
package org.kuali.hr.time.paytype.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PayTypeDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements PayTypeDao {

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
    @SuppressWarnings("unchecked")
    public List<PayType> getPayTypes(String payType, String regEarnCode, String descr, Date fromEffdt, Date toEffdt, String active, String showHistory) {
        List<PayType> results = new ArrayList<PayType>();
        
        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(payType)) {
            root.addLike("payType", payType);
        }
        
        if (StringUtils.isNotBlank(regEarnCode)) {
            root.addLike("regEarnCode", regEarnCode);
        }

        if (StringUtils.isNotBlank(descr)) {
            root.addLike("descr", descr);
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
        	effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
        	effdt.addAndCriteria(effectiveDateFilter);
        	ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayType.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
            root.addEqualTo("effectiveDate", effdtSubQuery);
            
            Criteria timestamp = new Criteria();
            timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
            timestamp.addAndCriteria(effectiveDateFilter);
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayType.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            root.addEqualTo("timestamp", timestampSubQuery);
        }

        Query query = QueryFactory.newQuery(PayType.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
}
