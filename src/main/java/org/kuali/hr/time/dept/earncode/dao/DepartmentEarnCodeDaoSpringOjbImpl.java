package org.kuali.hr.time.dept.earncode.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.util.TKUtils;
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
		
		// KPME-856, commented out the following line, when geting max(effdt) for each earnCode, do not need to limit to active entries.
		//effdt.addEqualTo("active", true);
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
		root.addOrderBy("dept",false);
		root.addOrderBy("hrSalGroup",false);
		
		
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
	public DepartmentEarnCode getDepartmentEarnCode(String hrDeptEarnCodeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrDeptEarnCodeId", hrDeptEarnCodeId);
		
		Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
		return (DepartmentEarnCode)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public List<DepartmentEarnCode> searchDepartmentEarnCodes(String dept, String salGroup, String earnCode, String location,
						java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory) {
		List<DepartmentEarnCode> results = new ArrayList<DepartmentEarnCode>();

        Criteria crit = new Criteria();
        Criteria effdtCrit = new Criteria();
        Criteria timestampCrit = new Criteria();

        if (fromEffdt != null) {
            crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }

        if (toEffdt != null) {
            crit.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }

        if (StringUtils.isNotEmpty(dept)) {
            crit.addLike("dept", dept);
        }

        if (StringUtils.isNotEmpty(salGroup)) {
            crit.addLike("hrSalGroup", salGroup);
        }

        if (StringUtils.isNotEmpty(earnCode)) {
            crit.addLike("earnCode", earnCode);
        }

        if (StringUtils.isNotEmpty(location)) {
            crit.addLike("location", location);
        }

        if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "Y")) {
            effdtCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            effdtCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdtCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
            timestampCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            timestampCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestampCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
           
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")) {
            effdtCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            effdtCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdtCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            timestampCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestampCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
           
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } else if (StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)) {
            effdtCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            effdtCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdtCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            timestampCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestampCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
           
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);

            Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        } //return all active records from the database
        else if (StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)) {
            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", true);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }
        //return all inactive records in the database
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")) {
            effdtCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            effdtCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdtCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            timestampCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestampCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
           
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            crit.addEqualTo("effectiveDate", effdtSubQuery);
            crit.addEqualTo("timestamp", timestampSubQuery);

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        //return the most effective inactive rows if there are no active rows <= the curr date
        else if (StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")) {
            effdtCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            effdtCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            effdtCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdtCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, effdtCrit);
            effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

            timestampCrit.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            timestampCrit.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            timestampCrit.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestampCrit.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
           
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DepartmentEarnCode.class, timestampCrit);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

            Criteria activeFilter = new Criteria(); // Inner Join For Activity
            activeFilter.addEqualTo("active", false);
            crit.addAndCriteria(activeFilter);
            Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
            Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
            results.addAll(c);
        }

        return results;
		
	}
	
	@Override
	public int getDepartmentEarnCodeCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, java.sql.Date effdt,String hrDeptEarnCodeId) {
		Criteria crit = new Criteria();
      crit.addEqualTo("dept", dept);
      crit.addEqualTo("hrSalGroup", salGroup);
      crit.addEqualTo("earnCode", earnCode);
      crit.addEqualTo("employee", employee);
      crit.addEqualTo("approver", approver);
      crit.addEqualTo("location", location);
      crit.addEqualTo("active", active);
      crit.addEqualTo("effectiveDate", effdt);
      if(hrDeptEarnCodeId != null) {
    	  crit.addEqualTo("hr_dept_earn_code_id", hrDeptEarnCodeId);
      }
      Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
      return this.getPersistenceBrokerTemplate().getCount(query);
	}
	@Override
	public int getNewerDeptEarnCodeCount(String earnCode, Date effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", earnCode);
		crit.addEqualTo("active", "Y");
		crit.addGreaterThan("effectiveDate", effdt);
		Query query = QueryFactory.newQuery(DepartmentEarnCode.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
