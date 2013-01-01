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
package org.kuali.hr.lm.earncodesec.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EarnCodeSecurityDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements EarnCodeSecurityDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(EarnCodeSecurityDaoSpringOjbImpl.class);

	public void saveOrUpdate(EarnCodeSecurity earnCodeSec) {
		this.getPersistenceBrokerTemplate().store(earnCodeSec);
	}

	public void saveOrUpdate(List<EarnCodeSecurity> ernCdSecList) {
		if (ernCdSecList != null) {
			for (EarnCodeSecurity ernCdSec : ernCdSecList) {
				this.getPersistenceBrokerTemplate().store(ernCdSec);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hrSalGroup, String location, Date asOfDate) {
		List<EarnCodeSecurity> decs = new LinkedList<EarnCodeSecurity>();

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
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EarnCodeSecurity.class, effdt);
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
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EarnCodeSecurity.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{ "max(timestamp)" });
		
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		
		root.addOrderBy("earnCode", true);
		root.addOrderBy("dept",false);
		root.addOrderBy("hrSalGroup",false);
		
		
		Query query = QueryFactory.newQuery(EarnCodeSecurity.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			decs.addAll(c);
		}
		
		//Now we can have duplicates so remove any that match more than once
		Set<String> aSet = new HashSet<String>();
		List<EarnCodeSecurity> aList = new ArrayList<EarnCodeSecurity>();
		for(EarnCodeSecurity dec : decs){
			if(!aSet.contains(dec.getEarnCode())){
				aList.add(dec);
				aSet.add(dec.getEarnCode());
			} 
		}
		return aList;
	}

	@Override
	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrEarnCodeSecurityId", hrEarnCodeSecId);
		
		Query query = QueryFactory.newQuery(EarnCodeSecurity.class, crit);
		return (EarnCodeSecurity)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location, 
														   java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory) {
		
		List<EarnCodeSecurity> results = new ArrayList<EarnCodeSecurity>();

        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (StringUtils.isNotBlank(salGroup)) {
            root.addLike("hrSalGroup", salGroup);
        }

        if (StringUtils.isNotBlank(earnCode)) {
            root.addLike("earnCode", earnCode);
        }

        if (StringUtils.isNotBlank(location)) {
            root.addLike("location", location);
        }

        if (fromEffdt != null) {
            root.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }

        if (toEffdt != null) {
            root.addLessOrEqualThan("effectiveDate", toEffdt);
        } else {
            root.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
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

        if (StringUtils.equals(showHistory, "N")) {
            Criteria effdt = new Criteria();
            effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            effdt.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            effdt.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            effdt.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EarnCodeSecurity.class, effdt);
            effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
            root.addEqualTo("effectiveDate", effdtSubQuery);
            
            Criteria timestamp = new Criteria();
            timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
            timestamp.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
            timestamp.addEqualToField("earnCode", Criteria.PARENT_QUERY_PREFIX + "earnCode");
            timestamp.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
            timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
            ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EarnCodeSecurity.class, timestamp);
            timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
            root.addEqualTo("timestamp", timestampSubQuery);
        }
        
        Query query = QueryFactory.newQuery(EarnCodeSecurity.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
		
	}
	
	@Override
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
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
    	  crit.addEqualTo("hrEarnCodeSecurityId", hrDeptEarnCodeId);
      }
      Query query = QueryFactory.newQuery(EarnCodeSecurity.class, crit);
      return this.getPersistenceBrokerTemplate().getCount(query);
	}
	@Override
	public int getNewerEarnCodeSecurityCount(String earnCode, Date effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", earnCode);
		crit.addEqualTo("active", "Y");
		crit.addGreaterThan("effectiveDate", effdt);
		Query query = QueryFactory.newQuery(EarnCodeSecurity.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
