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
package org.kuali.hr.paygrade.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.paygrade.PayGrade;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PayGradeDaoSpringObjImpl  extends PlatformAwareDaoBaseOjb implements PayGradeDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("payGrade")
            .build();

	@Override
	public PayGrade getPayGrade(String payGrade, Date asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("payGrade", payGrade);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PayGrade.class, new java.sql.Date(asOfDate.getTime()), EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayGrade.class, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(PayGrade.class, root);
		
		PayGrade pg = (PayGrade)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return pg;
	}

	@Override
	public PayGrade getPayGrade(String hrPayGradeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayGradeId", hrPayGradeId);
		
		Query query = QueryFactory.newQuery(PayGrade.class, crit);
		
		return (PayGrade)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public int getPayGradeCount(String payGrade) {
		Criteria crit = new Criteria();
		crit.addEqualTo("payGrade", payGrade);
		Query query = QueryFactory.newQuery(PayGrade.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<PayGrade> getPayGrades(String payGrade, String payGradeDescr, String active) {
        List<PayGrade> results = new ArrayList<PayGrade>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(payGrade)) {
            root.addLike("payGrade", payGrade);
        }
        
        if (StringUtils.isNotBlank(payGradeDescr)) {
            root.addLike("description", payGradeDescr);
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
        
        Query query = QueryFactory.newQuery(PayGrade.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<PayGrade> getPayGradesForSalaryGroup(String salaryGroup,
			Date asOfDate) {
        List<PayGrade> results = new ArrayList<PayGrade>();

		Criteria root = new Criteria();

		root.addEqualTo("salGroup", salaryGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PayGrade.class, new java.sql.Date(asOfDate.getTime()), EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PayGrade.class, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(PayGrade.class, root);
		
		results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
		
		return results;
	}

}
