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
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

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
    public List<Department> getDepartments(String chart, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Department.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Department.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("chart", chart);
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
	public Department getDepartment(Long tkDeptId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkDeptId", tkDeptId);
		
		Query query = QueryFactory.newQuery(Department.class, crit);
		return (Department)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
}
