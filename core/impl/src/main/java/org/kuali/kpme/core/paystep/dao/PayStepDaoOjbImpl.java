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
package org.kuali.kpme.core.paystep.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.core.paystep.PayStep;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PayStepDaoOjbImpl extends PlatformAwareDaoBaseOjb implements
		PayStepDao {

	
	@Override
	public PayStep getPayStepById(String payStepId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPayStepId", payStepId);

        Query query = QueryFactory.newQuery(PayStep.class, crit);
        return (PayStep) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayStep> getPaySteps(String payStep, String institution,
			String campus, String salaryGroup, String payGrade, String active) {
		List<PayStep> results = new ArrayList<PayStep>();
		
		Criteria crit = new Criteria();
		if(StringUtils.isNotBlank(payStep))
			crit.addEqualTo("payStep", payStep);
		if(StringUtils.isNotBlank(institution)
				&& !StringUtils.equals(institution, HrConstants.WILDCARD_CHARACTER))
			crit.addEqualTo("institution", institution);
		if(StringUtils.isNotBlank(campus)
				&& !StringUtils.equals(campus, HrConstants.WILDCARD_CHARACTER))
			crit.addEqualTo("campus", campus);
		if(StringUtils.isNotBlank(salaryGroup))
			crit.addEqualTo("salaryGroup", salaryGroup);
		if(StringUtils.isNotBlank(payGrade))
			crit.addEqualTo("payGrade", payGrade);
		
		Criteria activeFilter = new Criteria();
		if(StringUtils.isNotBlank(active)) {
			activeFilter.addEqualTo("active",active);
		}
		else
			activeFilter.addNotNull("active");

		crit.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(PayStep.class, crit);
		
		results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
		
		return results;
	}

}
