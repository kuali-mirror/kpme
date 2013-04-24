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
package org.kuali.hr.core.earncodesec.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.hr.core.earncodesec.EarnCodeSecurity;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class EarnCodeSecurityDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements EarnCodeSecurityDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .add("hrSalGroup")
            .add("earnCode")
            .add("location")
            .build();

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
	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hrSalGroup, String location, LocalDate asOfDate) {
		List<EarnCodeSecurity> decs = new LinkedList<EarnCodeSecurity>();

		Criteria root = new Criteria();
		
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
        ImmutableList.Builder<String> fields = new ImmutableList.Builder<String>()
                .add("dept")
                .add("hrSalGroup")
                .add("earnCode");
        if ( !location.trim().isEmpty() ){
            fields.add("location");
        }
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeSecurity.class, asOfDate, fields.build(), false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeSecurity.class, fields.build(), false));
		
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
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location, LocalDate fromEffdt, LocalDate toEffdt, 
														   String active, String showHistory) {
		
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

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }

        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);

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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(EarnCodeSecurity.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeSecurity.class, EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(EarnCodeSecurity.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}
	
	@Override
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, LocalDate effdt, String hrDeptEarnCodeId) {
		Criteria crit = new Criteria();
      crit.addEqualTo("dept", dept);
      crit.addEqualTo("hrSalGroup", salGroup);
      crit.addEqualTo("earnCode", earnCode);
      crit.addEqualTo("employee", employee);
      crit.addEqualTo("approver", approver);
      crit.addEqualTo("location", location);
      crit.addEqualTo("active", active);
      crit.addEqualTo("effectiveDate", effdt.toDate());
      if(hrDeptEarnCodeId != null) {
    	  crit.addEqualTo("hrEarnCodeSecurityId", hrDeptEarnCodeId);
      }
      Query query = QueryFactory.newQuery(EarnCodeSecurity.class, crit);
      return this.getPersistenceBrokerTemplate().getCount(query);
	}
	@Override
	public int getNewerEarnCodeSecurityCount(String earnCode, LocalDate effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", earnCode);
		crit.addEqualTo("active", "Y");
		crit.addGreaterThan("effectiveDate", effdt.toDate());
		Query query = QueryFactory.newQuery(EarnCodeSecurity.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
