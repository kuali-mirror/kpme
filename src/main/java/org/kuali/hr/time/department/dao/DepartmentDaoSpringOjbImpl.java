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
package org.kuali.hr.time.department.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.department.Department;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements DepartmentDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .build();

	@Override
	public void saveOrUpdate(Department dept) {
		this.getPersistenceBrokerTemplate().store(dept);
	}

	@Override
	public Department getDepartment(String department, Date asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Department.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Department.class, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(Department.class, root);

		Department d = (Department)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		return d;
	}

    @Override
    public List<Department> getDepartments(String location, Date asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("location", location);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Department.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Department.class, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);


		Query query = QueryFactory.newQuery(Department.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<Department> d = new ArrayList<Department>(c.size());
        d.addAll(c);

		return d;
    }

	@Override
	@SuppressWarnings("unchecked")
    public List<Department> getDepartments(String dept, String location, String departmentDescr, String active) {
        List<Department> results = new ArrayList<Department>();
        
        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }
        
        if (StringUtils.isNotBlank(location)) {
            root.addLike("location", location);
        }
        
        if (StringUtils.isNotBlank(departmentDescr)) {
            root.addLike("description", departmentDescr);
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
        
        Query query = QueryFactory.newQuery(Department.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

	@Override
	public Department getDepartment(String hrDeptId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrDeptId", hrDeptId);
		
		Query query = QueryFactory.newQuery(Department.class, crit);
		return (Department)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getDepartmentCount(String department) {
		Criteria crit = new Criteria();
		crit.addEqualTo("dept", department);
		Query query = QueryFactory.newQuery(Department.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
