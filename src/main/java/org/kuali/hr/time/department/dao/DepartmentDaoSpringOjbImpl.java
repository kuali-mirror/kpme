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
package org.kuali.hr.time.department.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.department.Department;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class DepartmentDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements DepartmentDao {

	@Override
	public void saveOrUpdate(Department dept) {
		this.getPersistenceBrokerTemplate().store(dept);
	}

	@Override
	public Department getDepartment(String department, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Department.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Department.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("dept", department);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

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
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Department.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Department.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("location", location);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

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
	public Department getDepartment(String hrDeptId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrDeptId", hrDeptId);
		
		Query query = QueryFactory.newQuery(Department.class, crit);
		return (Department)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public List<Department> getDepartmentByLocation(String location) {
		Criteria crit = new Criteria();
		crit.addEqualTo("location", location);
		
		Query query = QueryFactory.newQuery(Department.class, crit);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<Department> d = new ArrayList<Department>(c.size());
        d.addAll(c);

		return d;		
	}
	
	@Override
	public int getDepartmentCount(String department) {
		Criteria crit = new Criteria();
		crit.addEqualTo("dept", department);
		Query query = QueryFactory.newQuery(Department.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
