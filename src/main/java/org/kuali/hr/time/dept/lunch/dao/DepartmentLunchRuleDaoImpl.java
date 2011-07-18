package org.kuali.hr.time.dept.lunch.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class DepartmentLunchRuleDaoImpl  extends PersistenceBrokerDaoSupport implements DepartmentLunchRuleDao {

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
	public DeptLunchRule getDepartmentLunchRule(Long tkDeptLunchRuleId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkDeptLunchRuleId", tkDeptLunchRuleId);
		
		Query query = QueryFactory.newQuery(DeptLunchRule.class, crit);
		return (DeptLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
