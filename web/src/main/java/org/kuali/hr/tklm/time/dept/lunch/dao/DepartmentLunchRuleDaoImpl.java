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
package org.kuali.hr.tklm.time.dept.lunch.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.core.workarea.WorkArea;
import org.kuali.hr.tklm.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentLunchRuleDaoImpl  extends PlatformAwareDaoBaseOjb implements DepartmentLunchRuleDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .add("workArea")
            .add("principalId")
            .add("jobNumber")
            .build();

	@Override
	public DeptLunchRule getDepartmentLunchRule(String dept, Long workArea, String principalId, 
												Long jobNumber, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(DeptLunchRule.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(DeptLunchRule.class, EQUAL_TO_FIELDS, false));
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
    public List<DeptLunchRule> getDepartmentLunchRules(String dept, String workArea, String principalId, String jobNumber, String active) {
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
            Collection<WorkArea> workAreasForDept = TkServiceLocator.getWorkAreaService().getWorkAreas(dept,asOfDate);
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
            root.addLike("workArea", workArea);
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
        
        Query query = QueryFactory.newQuery(DeptLunchRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}
