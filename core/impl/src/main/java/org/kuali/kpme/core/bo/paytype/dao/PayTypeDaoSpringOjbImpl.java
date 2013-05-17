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
package org.kuali.kpme.core.bo.paytype.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
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

	public PayType getPayType(String payType, LocalDate effectiveDate) {
		Criteria currentRecordCriteria = new Criteria();

		currentRecordCriteria.addEqualTo("payType", payType);
        currentRecordCriteria.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PayType.class, effectiveDate, PayType.EQUAL_TO_FIELDS, false));
        currentRecordCriteria.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayType.class, PayType.EQUAL_TO_FIELDS, false));
		
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
    public List<PayType> getPayTypes(String payType, String regEarnCode, String descr, String campus, String institution, String flsaStatus,
    		String payFrequency, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
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
        
        if (StringUtils.isNotBlank(campus)) {
            root.addLike("campus", campus);
        }
        
        if (StringUtils.isNotBlank(institution)) {
            root.addLike("institution", institution);
        }
        
        if (StringUtils.isNotBlank(flsaStatus)) {
            root.addLike("flsaStatus", flsaStatus);
        }
        
        if (StringUtils.isNotBlank(payFrequency)) {
            root.addLike("payFrequency", payFrequency);
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PayType.class, effectiveDateFilter, PayType.EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayType.class, PayType.EQUAL_TO_FIELDS, false));
        }

        Query query = QueryFactory.newQuery(PayType.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
}
