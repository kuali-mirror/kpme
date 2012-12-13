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
package org.kuali.hr.lm.employeeoverride.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EmployeeOverrideDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements EmployeeOverrideDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeOverride> getEmployeeOverrides(String principalId, Date asOfDate) {
        List<EmployeeOverride> employeeOverrides = new ArrayList<EmployeeOverride>();
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        effdt.addEqualTo("principalId", principalId);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EmployeeOverride.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        timestamp.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EmployeeOverride.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

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
	public EmployeeOverride getEmployeeOverride(String lmEmployeeOverrideId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("lmEmployeeOverrideId", lmEmployeeOverrideId);
        Query query = QueryFactory.newQuery(EmployeeOverride.class, crit);
        return (EmployeeOverride) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<EmployeeOverride> getEmployeeOverrides(String principalId, String leavePlan, String accrualCategory, String overrideType,
                                                       Date fromEffdt, Date toEffdt, String active) {

        Criteria crit = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        List<EmployeeOverride> results= new ArrayList<EmployeeOverride>();

        if (StringUtils.isNotBlank(principalId)) {
            crit.addEqualTo("principalId",principalId);
        }
        if (StringUtils.isNotBlank(leavePlan)) {
            crit.addEqualTo("leavePlan",leavePlan);
        }
        if (StringUtils.isNotBlank(accrualCategory)) {
            crit.addEqualTo("accrualCategory",accrualCategory);
        }
        if (StringUtils.isNotBlank(overrideType)) {
            crit.addEqualTo("overrideType",overrideType);
        }
        if (fromEffdt != null) {
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }

        if (toEffdt != null) {
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if (StringUtils.isNotBlank(active)) {
            Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            crit.addAndCriteria(activeFilter);
        }
        // Getting records with the most recent effective date or most recent timestamp
        // Uniqueness based on principalId, leavePlan, accrualCategory and overrideType
        effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        effdt.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
        effdt.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
        effdt.addEqualToField("overrideType", Criteria.PARENT_QUERY_PREFIX + "overrideType");
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EmployeeOverride.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
        crit.addEqualTo("effectiveDate", effdtSubQuery);

        timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
        timestamp.addEqualToField("leavePlan", Criteria.PARENT_QUERY_PREFIX + "leavePlan");
        timestamp.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
        timestamp.addEqualToField("overrideType", Criteria.PARENT_QUERY_PREFIX + "overrideType");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EmployeeOverride.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
        crit.addEqualTo("timestamp", timestampSubQuery);

        Query query = QueryFactory.newQuery(EmployeeOverride.class, crit);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}
