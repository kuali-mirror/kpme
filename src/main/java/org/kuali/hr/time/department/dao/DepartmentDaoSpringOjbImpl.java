package org.kuali.hr.time.department.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.department.Department;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DepartmentDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements DepartmentDao {

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
	public Department getDepartment(Long hrDeptId) {
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
}
