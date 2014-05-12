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
package org.kuali.kpme.core.salarygroup.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.api.util.Truth;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SalaryGroupDaoOjbImpl extends PlatformAwareDaoBaseOjb implements SalaryGroupDao {
    @Override
    public void saveOrUpdate(SalaryGroupBo salaryGroup) {
        this.getPersistenceBrokerTemplate().store(salaryGroup);		
	}

	@Override
	public SalaryGroupBo getSalaryGroup(String salGroup, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("hrSalGroup", salGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(SalaryGroupBo.class, asOfDate, SalaryGroupBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SalaryGroupBo.class, SalaryGroupBo.BUSINESS_KEYS, false));
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(SalaryGroupBo.class, root);
		SalaryGroupBo s = (SalaryGroupBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return s;
	}

	@Override
	public SalaryGroupBo getSalaryGroup(String hrSalGroupId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrSalGroupId", hrSalGroupId);
		
		Query query = QueryFactory.newQuery(SalaryGroupBo.class, crit);
		return (SalaryGroupBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	@Override
	public int getSalGroupCount(String salGroup) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrSalGroup", salGroup);
		Query query = QueryFactory.newQuery(SalaryGroupBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<SalaryGroupBo> getSalaryGroups(String hrSalGroup, String institution, String location, String leavePlan, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory, String benefitEligible, String leaveEligible, String percentTime) {
        List<SalaryGroupBo> results = new ArrayList<SalaryGroupBo>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(hrSalGroup)) {
            root.addLike("UPPER(hrSalGroup)", hrSalGroup.toUpperCase()); // KPME-2695
        }

        // KPME-2695/2710
        if (StringUtils.isNotBlank(institution)) {
            root.addLike("UPPER(institution)", institution.toUpperCase());
        }
        if (StringUtils.isNotBlank(location)) {
            root.addLike("UPPER(location)", location.toUpperCase());
        }
        if (StringUtils.isNotBlank(leavePlan)) {
            root.addLike("UPPER(leavePlan)", leavePlan.toUpperCase());
        }
        
        
        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", DateTime.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);

        if (StringUtils.isNotBlank(active)) {
            Criteria activeFilter = new Criteria();
            if (Truth.strToBooleanIgnoreCase(active)) {
                activeFilter.addEqualTo("active", true);
            } else {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }

        if (StringUtils.isNotBlank(benefitEligible)) {
            Criteria benefitEligibleFilter = new Criteria();
            benefitEligibleFilter.addEqualTo("benefitsEligible", benefitEligible);
            root.addAndCriteria(benefitEligibleFilter);
        }

        if (StringUtils.isNotBlank(leaveEligible)) {
            Criteria leaveEligibleFilter = new Criteria();
            leaveEligibleFilter.addEqualTo("leaveEligible", leaveEligible);
            root.addAndCriteria(leaveEligibleFilter);
        }

        if (StringUtils.isNotBlank(percentTime)) {
            Criteria percentTimeFilter = new Criteria();
            percentTimeFilter.addEqualTo("percentTime", percentTime);
            root.addAndCriteria(percentTimeFilter);
        }

        if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(SalaryGroupBo.class, effectiveDateFilter, SalaryGroupBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SalaryGroupBo.class, SalaryGroupBo.BUSINESS_KEYS, false));
        }

        Query query = QueryFactory.newQuery(SalaryGroupBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
	
}