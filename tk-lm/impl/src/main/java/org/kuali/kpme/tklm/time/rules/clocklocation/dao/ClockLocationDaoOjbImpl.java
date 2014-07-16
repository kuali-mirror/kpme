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
package org.kuali.kpme.tklm.time.rules.clocklocation.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRuleIpAddress;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClockLocationDaoOjbImpl extends PlatformAwareDaoBaseOjb implements ClockLocationDao{
     public List<ClockLocationRule> getClockLocationRule(String groupKeyCode, String dept, Long workArea, String principalId, Long jobNumber, LocalDate asOfDate){
		Criteria root = new Criteria();
		
		root.addEqualTo("groupKeyCode", groupKeyCode);
		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(ClockLocationRule.class, asOfDate, ClockLocationRule.BUSINESS_KEYS, false));
		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(ClockLocationRule.class, ClockLocationRule.BUSINESS_KEYS, false));
		//root.addEqualTo("active", true);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(ClockLocationRule.class, root);
		List<ClockLocationRule> clockLocationRules = (List<ClockLocationRule>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(clockLocationRules==null){
			clockLocationRules = new ArrayList<ClockLocationRule>();
		}
		for(ClockLocationRule clr : clockLocationRules ) {
			this.populateIPAddressesForCLR(clr);
		}
		return clockLocationRules;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClockLocationRule> getNewerVersionClockLocationRule(String groupKeyCode, 
			String dept, Long workArea, String principalId, Long jobNumber,
			LocalDate asOfDate) {
		Criteria root = new Criteria();
		
		root.addEqualTo("groupKeyCode", groupKeyCode);
		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
		root.addGreaterThan("effectiveDate", asOfDate.toDate());
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(ClockLocationRule.class, root);
		List<ClockLocationRule> clockLocationRules = (List<ClockLocationRule>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(clockLocationRules==null){
			clockLocationRules = new ArrayList<ClockLocationRule>();
		}
		for(ClockLocationRule clr : clockLocationRules ) {
			this.populateIPAddressesForCLR(clr);
		}
		return clockLocationRules;
	}
	
	public ClockLocationRule getClockLocationRule(String tkClockLocationRuleId){
	        Criteria criteria = new Criteria();
	        criteria.addEqualTo("tkClockLocationRuleId", tkClockLocationRuleId);
	        ClockLocationRule clr = (ClockLocationRule) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(
	        							ClockLocationRule.class, criteria));
	        if(clr != null) {
	        	this.populateIPAddressesForCLR(clr);
	        }
	        return clr;
	}
	
	// get ip address from tk_ip_addresses table for this ClockLocationRule
	@SuppressWarnings("unchecked")
	public void populateIPAddressesForCLR(ClockLocationRule clr) {
		if(clr.getTkClockLocationRuleId() == null) {
			return;
		}
		Criteria root = new Criteria();
		root.addEqualTo("tkClockLocationRuleId", clr.getTkClockLocationRuleId().toString());
		Query query = QueryFactory.newQuery(ClockLocationRuleIpAddress.class, root);
		List<ClockLocationRuleIpAddress> ipAddresses = (List<ClockLocationRuleIpAddress>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		clr.setIpAddresses(ipAddresses);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<ClockLocationRule> getClockLocationRules(String groupKeyCode, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber, String dept, String workArea, 
    													 String active, String showHistory) {

        List<ClockLocationRule> results = new ArrayList<ClockLocationRule>();
        
        Criteria root = new Criteria();

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

        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("UPPER(principalId)", principalId.toUpperCase()); // KPME-2695 in case principal id is not a number
        }

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("UPPER(dept)", dept.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(groupKeyCode)) {
        	root.addLike("groupKeyCode", groupKeyCode);
        }
        
        if (StringUtils.isNotBlank(jobNumber)) {
            OjbSubQueryUtil.addNumericCriteria(root, "jobNumber", jobNumber);
        }

        if (StringUtils.isNotBlank(dept)
                && StringUtils.isBlank(workArea)) {
            Criteria workAreaCriteria = new Criteria();
            LocalDate asOfDate = toEffdt != null ? toEffdt : LocalDate.now();
            List<Long> workAreasForDept = HrServiceLocator.getWorkAreaService().getWorkAreasForDepartment(dept, asOfDate);
            workAreasForDept.add(-1L); //keep wild card records in mind
            if (CollectionUtils.isNotEmpty(workAreasForDept)) {
                workAreaCriteria.addIn("workArea", workAreasForDept);
            }
            root.addAndCriteria(workAreaCriteria);
        }

        if (StringUtils.isNotBlank(workArea)) {
            OjbSubQueryUtil.addNumericCriteria(root, "workArea", workArea);
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
    		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(ClockLocationRule.class, effectiveDateFilter, ClockLocationRule.BUSINESS_KEYS, false));
    		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(ClockLocationRule.class, ClockLocationRule.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(ClockLocationRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

}
