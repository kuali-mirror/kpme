/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.earncode.security.dao;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.earncode.security.EarnCodeSecurityBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.*;

public class EarnCodeSecurityDaoOjbImpl extends PlatformAwareDaoBaseOjb implements EarnCodeSecurityDao {
    
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(EarnCodeSecurityDaoOjbImpl.class);

	public void saveOrUpdate(EarnCodeSecurityBo earnCodeSec) {
		this.getPersistenceBrokerTemplate().store(earnCodeSec);
	}

	public void saveOrUpdate(List<EarnCodeSecurityBo> ernCdSecList) {
		if (ernCdSecList != null) {
			for (EarnCodeSecurityBo ernCdSec : ernCdSecList) {
				this.getPersistenceBrokerTemplate().store(ernCdSec);
			}
		}
	}

/*
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<EarnCodeSecurityBo> getEarnCodeSecurities(String department, String hrSalGroup, String location, LocalDate asOfDate) {
		List<EarnCodeSecurityBo> decs = new LinkedList<EarnCodeSecurityBo>();

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
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeSecurityBo.class, asOfDate, fields.build(), false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeSecurityBo.class, fields.build(), false));
		
		root.addOrderBy("earnCode", true);
		root.addOrderBy("dept",false);
		root.addOrderBy("hrSalGroup",false);
		
		
		Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			decs.addAll(c);
		}
		
		//Now we can have duplicates so remove any that match more than once
		Set<String> aSet = new HashSet<String>();
		List<EarnCodeSecurityBo> aList = new ArrayList<EarnCodeSecurityBo>();
		for(EarnCodeSecurityBo dec : decs){
			if(!aSet.contains(dec.getEarnCode())){
				aList.add(dec);
				aSet.add(dec.getEarnCode());
			} 
		}
		return aList;
	}
*/

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public List<EarnCodeSecurityBo> getEarnCodeSecurities(String department, String hrSalGroup, String location, LocalDate asOfDate, String groupKeyCode) {
        List<EarnCodeSecurityBo> decs = new LinkedList<EarnCodeSecurityBo>();

        Criteria root = new Criteria();

        Criteria deptCrit = new Criteria();
        Criteria salGroupCrit = new Criteria();
        Criteria locationCrit = new Criteria();
        Criteria groupKeyCodeCrit = new Criteria();

        deptCrit.addEqualTo("dept", "%");
        salGroupCrit.addEqualTo("hrSalGroup", "%");
        locationCrit.addEqualTo("location", "%");
        groupKeyCodeCrit.addEqualTo("groupKeyCode", "%");

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

        Criteria groupKeyCodeCrit2 = new Criteria();

        //if ((groupKeyCode != null) && (! groupKeyCode.trim().isEmpty()))
        if (! groupKeyCode.trim().isEmpty())
        {
            groupKeyCodeCrit2.addEqualTo("groupKeyCode", groupKeyCode);
            groupKeyCodeCrit2.addOrCriteria(groupKeyCodeCrit);
            root.addAndCriteria(groupKeyCodeCrit2);
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
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeSecurityBo.class, asOfDate, fields.build(), false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeSecurityBo.class, fields.build(), false));

        root.addOrderBy("earnCode", true);
        root.addOrderBy("dept",false);
        root.addOrderBy("hrSalGroup",false);


        Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, root);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            decs.addAll(c);
        }

        //Now we can have duplicates so remove any that match more than once
        Set<String> aSet = new HashSet<String>();
        List<EarnCodeSecurityBo> aList = new ArrayList<EarnCodeSecurityBo>();
        for(EarnCodeSecurityBo dec : decs){
            if(!aSet.contains(dec.getEarnCode())){
                aList.add(dec);
                aSet.add(dec.getEarnCode());
            }
        }
        return aList;
    }

	@Override
	public EarnCodeSecurityBo getEarnCodeSecurity(String hrEarnCodeSecId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrEarnCodeSecurityId", hrEarnCodeSecId);
		
		Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, crit);
		return (EarnCodeSecurityBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<EarnCodeSecurityBo> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location, LocalDate fromEffdt, LocalDate toEffdt, 
														   String active, String showHistory, String groupKeyCode) {
		
		List<EarnCodeSecurityBo> results = new ArrayList<EarnCodeSecurityBo>();

        Criteria root = new Criteria();

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("UPPER(dept)", dept.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(salGroup)) {
            root.addLike("UPPER(hrSalGroup)", salGroup.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(earnCode)) {
            root.addLike("UPPER(earnCode)", earnCode.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(location)) {
            root.addLike("UPPER(location)", location.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(groupKeyCode)) {
            root.addLike("UPPER(groupKeyCode)", groupKeyCode.toUpperCase());
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(EarnCodeSecurityBo.class, effectiveDateFilter, EarnCodeSecurityBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeSecurityBo.class, EarnCodeSecurityBo.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}
	
	@Override
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String payrollProcessor, String location,
			String active, LocalDate effdt, String hrDeptEarnCodeId, String groupKeyCode) {
		Criteria crit = new Criteria();
      crit.addEqualTo("dept", dept);
      crit.addEqualTo("hrSalGroup", salGroup);
      crit.addEqualTo("earnCode", earnCode);
      crit.addEqualTo("employee", employee);
      crit.addEqualTo("approver", approver);
      crit.addEqualTo("payrollProcessor", payrollProcessor);
      crit.addEqualTo("location", location);
      crit.addEqualTo("active", active);
      crit.addEqualTo("groupKeyCode", groupKeyCode);

      if(effdt != null) {
    	  crit.addEqualTo("effectiveDate", effdt.toDate());
      }
      else {
    	  return 0;
      }
      if(hrDeptEarnCodeId != null) {
    	  crit.addEqualTo("hrEarnCodeSecurityId", hrDeptEarnCodeId);
      }
      Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, crit);
      return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	@Override
	public int getNewerEarnCodeSecurityCount(String earnCode, LocalDate effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", earnCode);
		crit.addEqualTo("active", "Y");
		if(effdt != null) {
			crit.addGreaterThan("effectiveDate", effdt.toDate());
		}
		else {
			return 0;
		}
		Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	
	@Override
	public List<EarnCodeSecurityBo> getEarnCodeSecurityList(String dept, String salGroup, String earnCode, String employee, String approver, String payrollProcessor, String location,
			String active, LocalDate effdt, String groupKeyCode) {
	  Criteria crit = new Criteria();
      crit.addEqualTo("earnCode", earnCode);
      if(StringUtils.isNotEmpty(employee)) {
    	  crit.addEqualTo("employee", employee);
      }
      if(StringUtils.isNotEmpty(approver)) {
    	  crit.addEqualTo("approver", approver);
      }
      if(StringUtils.isNotEmpty(payrollProcessor)) {
    	  crit.addEqualTo("payrollProcessor", payrollProcessor);
      }

    if (StringUtils.isNotEmpty(groupKeyCode)) {
        crit.addEqualTo("groupKeyCode", groupKeyCode);
    }

      crit.addEqualTo("active", active);
      crit.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeSecurityBo.class, effdt, EarnCodeSecurityBo.BUSINESS_KEYS, false));
      
      Query query = QueryFactory.newQuery(EarnCodeSecurityBo.class, crit);
      
      List<EarnCodeSecurityBo> results = new ArrayList<EarnCodeSecurityBo>();
      results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

   // dept and salGroup allow wildcards,
      List<EarnCodeSecurityBo> finalResults = new ArrayList<EarnCodeSecurityBo>();
      for(EarnCodeSecurityBo aSecurity : results) {
    	 if((StringUtils.isNotEmpty(dept) && ValidationUtils.wildCardMatch(aSecurity.getDept(), dept))
    		 && (StringUtils.isNotEmpty(salGroup) && ValidationUtils.wildCardMatch(aSecurity.getHrSalGroup(), salGroup))) {
    		 // location is not a required field, it allows wild card
    		 if(StringUtils.isEmpty(location)) {
    			 finalResults.add(aSecurity);
    		 } else if(ValidationUtils.wildCardMatch(aSecurity.getLocation(), location)) {
    			 finalResults.add(aSecurity);
    		 }
    	 }
      }
      
      return finalResults;
	}
}
