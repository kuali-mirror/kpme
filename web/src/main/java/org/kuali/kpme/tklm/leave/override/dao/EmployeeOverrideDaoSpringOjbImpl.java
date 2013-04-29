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
package org.kuali.kpme.tklm.leave.override.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class EmployeeOverrideDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements EmployeeOverrideDao{
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("principalId")
            .add("leavePlan")
            .add("accrualCategory")
            .add("overrideType")
            .build();

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeOverride> getEmployeeOverrides(String principalId, LocalDate asOfDate) {
        List<EmployeeOverride> employeeOverrides = new ArrayList<EmployeeOverride>();
        Criteria root = new Criteria();

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EmployeeOverride.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EmployeeOverride.class, EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(EmployeeOverride.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	employeeOverrides.addAll(c);
        }
        return employeeOverrides;
    }
	
	@Override
	public EmployeeOverride getEmployeeOverride(String principalId, String leavePlan, String accrualCategory, String overrideType, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("accrualCategory", accrualCategory);
        root.addEqualTo("overrideType", overrideType);

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EmployeeOverride.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EmployeeOverride.class, EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(EmployeeOverride.class, root);
        return (EmployeeOverride) getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public EmployeeOverride getEmployeeOverride(String lmEmployeeOverrideId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("lmEmployeeOverrideId", lmEmployeeOverrideId);
        Query query = QueryFactory.newQuery(EmployeeOverride.class, crit);
        return (EmployeeOverride) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<EmployeeOverride> getEmployeeOverrides(String principalId, String leavePlan, String accrualCategory, String overrideType,
                                                       LocalDate fromEffdt, LocalDate toEffdt, String active) {

        List<EmployeeOverride> results = new ArrayList<EmployeeOverride>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(principalId)) {
        	root.addEqualTo("principalId",principalId);
        }
        
        if (StringUtils.isNotBlank(leavePlan)) {
        	root.addEqualTo("leavePlan",leavePlan);
        }
        
        if (StringUtils.isNotBlank(accrualCategory)) {
        	root.addEqualTo("accrualCategory",accrualCategory);
        }
        
        if (StringUtils.isNotBlank(overrideType)) {
        	root.addEqualTo("overrideType",overrideType);
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

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(EmployeeOverride.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EmployeeOverride.class, EQUAL_TO_FIELDS, false));

        Query query = QueryFactory.newQuery(EmployeeOverride.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}