package org.kuali.hr.time.dept.earncode.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.*;

public class DepartmentEarnCodeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements DepartmentEarnCodeDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(DepartmentEarnCodeDaoSpringOjbImpl.class);

	public void saveOrUpdate(DepartmentEarnCode deptErncd) {
		this.getPersistenceBrokerTemplate().store(deptErncd);
	}

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList) {
		if (deptErncdList != null) {
			for (DepartmentEarnCode deptErncd : deptErncdList) {
				this.getPersistenceBrokerTemplate().store(deptErncd);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String hrSalGroup, String location, Date asOfDate) {
		List<DepartmentEarnCode> decs = new LinkedList<DepartmentEarnCode>();

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		
		Criteria deptCrit = new Criteria();
		Criteria salGroupCrit = new Criteria();
		Criteria locationCrit = new Criteria();
		
		deptCrit.addEqualTo("dept", "%");
		salGroupCrit.addEqualTo("hrSalGroup", "%");
		locationCrit.addEqualTo("location", "%");
		
		Criteria deptCrit2 = new Criteria();
		deptCrit2.addEqualTo("dept", department);
		deptCrit2.addOrCriteria(deptCrit);
		root.addAndCriteria(deptCrit2);
		
		Criteria salGroupCrit2 = new Criteria();
		salGroupCrit2.addEqualTo("hrSalGroup", hrSalGroup);
		salGroupCrit2.addOrCriteria(salGroupCrit);
		root.addAndCriteria(salGroupCrit2);
		
		Criteria locationCrit2 = new Criteria();
		if ( !location.trim().isEmpty() ){
			locationCrit2.addEqualTo("location", location);
			locationCrit2.addOrCriteria(locationCrit);
			root.addAndCriteria(locationCrit2);
		}
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		effdt.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
		effdt.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		if ( !location.trim().isEmpty() ){
			effdt.addAndCriteria(locationCrit2);
			effdt.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
		}
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
		timestamp.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
		if ( !location.trim().isEmpty() ){
			timestamp.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
		}
		timestamp.addEqualTo("active", true);
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{ "max(timestamp)" });
		
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		
		root.addOrderBy("earnCode", true);
		
		Query query = QueryFactory.newQuery(DepartmentEarnCode.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			decs.addAll(c);
		}
		
		//Now we can have duplicates so remove any that match more than once
		Set<String> aSet = new HashSet<String>();
		List<DepartmentEarnCode> aList = new ArrayList<DepartmentEarnCode>();
		for(DepartmentEarnCode dec : decs){
			if(!aSet.contains(dec.getEarnCode())){
				aList.add(dec);
				aSet.add(dec.getEarnCode());
			} 
		}
		return aList;
	}

	@Override
	public DepartmentEarnCode getDepartmentEarnCode(Long hrDeptEarnCodeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrDeptEarnCodeId", hrDeptEarnCodeId);
		
		Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
		return (DepartmentEarnCode)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
}
