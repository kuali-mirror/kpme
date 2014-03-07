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
package org.kuali.kpme.core.paytype.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PayTypeDaoOjbImpl extends PlatformAwareDaoBaseOjb implements PayTypeDao {
    
	public void saveOrUpdate(PayTypeBo payType) {
		this.getPersistenceBrokerTemplate().store(payType);
	}

	public void saveOrUpdate(List<PayTypeBo> payTypeList) {
		if (payTypeList != null) {
			for (PayTypeBo payType : payTypeList) {
				this.getPersistenceBrokerTemplate().store(payType);
			}
		}
	}

	public PayTypeBo getPayType(String payType, LocalDate effectiveDate) {
		Criteria currentRecordCriteria = new Criteria();

		currentRecordCriteria.addEqualTo("payType", payType);
        currentRecordCriteria.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PayTypeBo.class, effectiveDate, PayTypeBo.BUSINESS_KEYS, false));
        currentRecordCriteria.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayTypeBo.class, PayTypeBo.BUSINESS_KEYS, false));
		
//		Criteria activeFilter = new Criteria(); // Inner Join For Activity
//		activeFilter.addEqualTo("active", true);
//		currentRecordCriteria.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PayTypeBo.class, currentRecordCriteria);
		return (PayTypeBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


	@Override
	public PayTypeBo getPayType(String hrPayTypeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayTypeId", hrPayTypeId);
		
		Query query = QueryFactory.newQuery(PayTypeBo.class, crit);
		return (PayTypeBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getPayTypeCount(String payType) {
		Criteria crit = new Criteria();
		crit.addEqualTo("payType", payType);
		Query query = QueryFactory.newQuery(PayTypeBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<PayTypeBo> getPayTypes(String payType, String regEarnCode, String descr, String location, String institution, String flsaStatus,
    		String payFrequency, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<PayTypeBo> results = new ArrayList<PayTypeBo>();
        
        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(payType)) {
            root.addLike("UPPER(paytype)", payType.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(regEarnCode)) {
            root.addLike("UPPER(regEarnCode)", regEarnCode.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(descr)) {
            root.addLike("UPPER(descr)", descr.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(location)) {
            root.addLike("UPPER(location)", location.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(institution)) {
            root.addLike("UPPER(institution)", institution.toUpperCase()); // KPME-2695
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PayTypeBo.class, effectiveDateFilter, PayTypeBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayTypeBo.class, PayTypeBo.BUSINESS_KEYS, false));
        }

        Query query = QueryFactory.newQuery(PayTypeBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
}
