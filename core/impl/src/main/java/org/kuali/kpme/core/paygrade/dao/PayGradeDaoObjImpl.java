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
package org.kuali.kpme.core.paygrade.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.paygrade.PayGradeBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PayGradeDaoObjImpl  extends PlatformAwareDaoBaseOjb implements PayGradeDao {
    
	@Override
	public PayGradeBo getPayGrade(String payGrade, String salGroup, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("payGrade", payGrade);
		root.addEqualTo("salGroup", salGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PayGradeBo.class, asOfDate, PayGradeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayGradeBo.class, PayGradeBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(PayGradeBo.class, root);
		
		PayGradeBo pg = (PayGradeBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return pg;
	}

	@Override
	public PayGradeBo getPayGrade(String hrPayGradeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayGradeId", hrPayGradeId);
		
		Query query = QueryFactory.newQuery(PayGradeBo.class, crit);
		
		return (PayGradeBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public int getPayGradeCount(String payGrade) {
		Criteria crit = new Criteria();
		crit.addEqualTo("payGrade", payGrade);
		Query query = QueryFactory.newQuery(PayGradeBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<PayGradeBo> getPayGrades(String payGrade, String payGradeDescr, String salGroup, String groupKeyCode, String active, String showHistory) {
        List<PayGradeBo> results = new ArrayList<PayGradeBo>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(payGrade)) {
            root.addLike("UPPER(payGrade)", payGrade.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(payGradeDescr)) {
            root.addLike("UPPER(description)", payGradeDescr.toUpperCase()); // KPME-2695
        }
        
        // KPME-2700
        if (StringUtils.isNotBlank(salGroup)) {
            root.addLike("UPPER(salGroup)", salGroup.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(groupKeyCode)) {
        	root.addLike("groupKeyCode", groupKeyCode);
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(PayGradeBo.class, PayGradeBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayGradeBo.class, PayGradeBo.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(PayGradeBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<PayGradeBo> getPayGradesForSalaryGroup(String salaryGroup, LocalDate asOfDate) {
        List<PayGradeBo> results = new ArrayList<PayGradeBo>();

		Criteria root = new Criteria();

		root.addEqualTo("salGroup", salaryGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PayGradeBo.class, asOfDate, PayGradeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayGradeBo.class, PayGradeBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(PayGradeBo.class, root);
		
		results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
		
		return results;
	}

}
