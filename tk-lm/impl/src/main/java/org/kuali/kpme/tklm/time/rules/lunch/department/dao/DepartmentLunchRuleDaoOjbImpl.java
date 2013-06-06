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
package org.kuali.kpme.tklm.time.rules.lunch.department.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentLunchRuleDaoOjbImpl extends PlatformAwareDaoBaseOjb implements DepartmentLunchRuleDao {
    @Override
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, 
												Long jobNumber, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DeptLunchRule.class, asOfDate, DeptLunchRule.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DeptLunchRule.class, DeptLunchRule.EQUAL_TO_FIELDS, false));
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
    @SuppressWarnings("unchecked")
    public List<DeptLunchRule> getDepartmentLunchRules(String dept, String workArea, String principalId, String jobNumber, 
    												   LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<DeptLunchRule> results = new ArrayList<DeptLunchRule>();
        
        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }
        
        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("principalId", principalId);
        }
        
        if (StringUtils.isNotBlank(jobNumber)) {
            root.addLike("jobNumber", jobNumber);
        }
        
        if (StringUtils.isNotBlank(dept)) {
            Criteria workAreaCriteria = new Criteria();
            LocalDate asOfDate = LocalDate.now();
            Collection<WorkArea> workAreasForDept = HrServiceLocator.getWorkAreaService().getWorkAreas(dept,asOfDate);
            if (CollectionUtils.isNotEmpty(workAreasForDept)) {
                List<Long> longWorkAreas = new ArrayList<Long>();
                for(WorkArea cwa : workAreasForDept){
                    longWorkAreas.add(cwa.getWorkArea());
                }
                workAreaCriteria.addIn("workArea", longWorkAreas);
            }
            root.addAndCriteria(workAreaCriteria);
        }
        
        if (StringUtils.isNotBlank(workArea)) {
        	OjbSubQueryUtil.addNumericCriteria(root, "workArea", workArea);
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

        if (StringUtils.equals(showHistory, "N")) {
        	root.addAndCriteria(effectiveDateFilter);
        }
        if (StringUtils.equals(showHistory, "N")) {
        	root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(DeptLunchRule.class, effectiveDateFilter, DeptLunchRule.EQUAL_TO_FIELDS, false));
        	root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DeptLunchRule.class, DeptLunchRule.EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(DeptLunchRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}
