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
package org.kuali.hr.time.dept.lunch.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentLunchRuleDaoImpl  extends PlatformAwareDaoBaseOjb implements DepartmentLunchRuleDao {

	@Override
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, 
												Long jobNumber, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DeptLunchRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DeptLunchRule.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(DeptLunchRule.class, root);
		return (DeptLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		

	}

	@Override
	public DeptLunchRule getDepartmentLunchRule(String tkDeptLunchRuleId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkDeptLunchRuleId", tkDeptLunchRuleId);
		
		Query query = QueryFactory.newQuery(DeptLunchRule.class, crit);
		return (DeptLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

    @Override
    public List<DeptLunchRule> getDepartmentLunchRules(String dept, String workArea, String principalId, String jobNumber, String active) {

        Criteria crit = new Criteria();
        List<DeptLunchRule> results = new ArrayList<DeptLunchRule>();

        if(StringUtils.isNotBlank(dept) && StringUtils.isNotEmpty(dept)){
            crit.addLike("dept", dept);
        }
        if (StringUtils.isNotEmpty(principalId)) {
            crit.addLike("principalId", principalId);
        }
        if (StringUtils.isNotEmpty(jobNumber)) {
            crit.addLike("jobNumber", jobNumber);
        }
        if (StringUtils.isNotEmpty(dept)) {
            Criteria workAreaCriteria = new Criteria();
            Date asOfDate = TKUtils.getCurrentDate();
            Collection<WorkArea> workAreasForDept = TkServiceLocator.getWorkAreaService().getWorkAreas(dept,asOfDate);
            if (CollectionUtils.isNotEmpty(workAreasForDept)) {
                List<Long> longWorkAreas = new ArrayList<Long>();
                for(WorkArea cwa : workAreasForDept){
                    longWorkAreas.add(cwa.getWorkArea());
                }
                workAreaCriteria.addIn("workArea", longWorkAreas);
            }
            crit.addAndCriteria(workAreaCriteria);
        }
        if (StringUtils.isNotEmpty(workArea)) {
            crit.addLike("workArea", workArea);
        }
        if (StringUtils.isEmpty(active)) {
            Query query = QueryFactory.newQuery(DeptLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.equals(active, "Y")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(DeptLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.equals(active, "N")) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(DeptLunchRule.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        return results;
    }

}
