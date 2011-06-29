package org.kuali.hr.time.dept.earncode.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

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
	public List<DepartmentEarnCode> getDepartmentEarnCodes(String department, String tkSalGroup, String location, Date asOfDate) {
		List<DepartmentEarnCode> decs = new LinkedList<DepartmentEarnCode>();

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		
		Criteria deptCrit = new Criteria();
		Criteria salGroupCrit = new Criteria();
		Criteria locationCrit = new Criteria();
		
		deptCrit.addEqualTo("dept", "%");
		
		salGroupCrit.addEqualTo("tkSalGroup", "%");
		
		locationCrit.addEqualTo("location", "%");
		
		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		effdt.addOrCriteria(deptCrit);
		effdt.addOrCriteria(salGroupCrit);
		effdt.addOrCriteria(locationCrit);
		effdt.addEqualToField("tkSalGroup", Criteria.PARENT_QUERY_PREFIX + "tkSalGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
		if ( !location.trim().isEmpty() ){
			effdt.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
			effdt.addOrCriteria(locationCrit);
		}
		
		// effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("tkSalGroup", Criteria.PARENT_QUERY_PREFIX + "tkSalGroup");
		timestamp.addOrCriteria(deptCrit);
		timestamp.addOrCriteria(salGroupCrit);
		
		timestamp.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
		if ( !location.trim().isEmpty() ){
			timestamp.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
			timestamp.addOrCriteria(locationCrit);
		}
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		// timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		
		
		root.addEqualTo("dept", department);
		root.addEqualTo("tkSalGroup", tkSalGroup);
		root.addOrCriteria(deptCrit);
		root.addOrCriteria(salGroupCrit);
		
		if ( !location.trim().isEmpty() ){
			root.addEqualTo("location", location);
			root.addOrCriteria(locationCrit);
		}
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		
		// root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		root.addOrderBy("dept",false);
		root.addOrderBy("tkSalGroup", false);
		root.addOrderBy("location", false);
		
		
		Query query = QueryFactory.newQuery(DepartmentEarnCode.class, root);
		
		@SuppressWarnings("rawtypes")
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			decs.addAll(c);
		}
		
		//Now we can have duplicates so remove any that match more than once
		Map<String,DepartmentEarnCode> earnCodeToDecMap = new HashMap<String,DepartmentEarnCode>();
		for(DepartmentEarnCode dec : decs){
			if(!earnCodeToDecMap.containsKey(dec.getEarnCode())){
				earnCodeToDecMap.put(dec.getEarnCode(), dec);
			}
		}
		
		decs.removeAll(decs);
		decs.addAll(earnCodeToDecMap.values());

		return decs;
	}
	
}
