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
package org.kuali.hr.time.clock.location.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clock.location.ClockLocationRuleIpAddress;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClockLocationDaoOjbImpl extends PlatformAwareDaoBaseOjb implements ClockLocationDao{
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .add("workArea")
            .add("jobNumber")
            .add("principalId")
            .build();

	public List<ClockLocationRule> getClockLocationRule(String dept, Long workArea, String principalId, Long jobNumber, Date asOfDate){
		Criteria root = new Criteria();

		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(ClockLocationRule.class, asOfDate, EQUAL_TO_FIELDS, false));
		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(ClockLocationRule.class, EQUAL_TO_FIELDS, false));
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
	public List<ClockLocationRule> getNewerVersionClockLocationRule(
			String dept, Long workArea, String principalId, Long jobNumber,
			Date asOfDate) {
		Criteria root = new Criteria();
		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
		root.addGreaterThan("effectiveDate", asOfDate);
		
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
    public List<ClockLocationRule> getClockLocationRules(Date fromEffdt, Date toEffdt, String principalId, String jobNumber, String dept, String workArea, 
    													 String active, String showHistory) {

        List<ClockLocationRule> results = new ArrayList<ClockLocationRule>();
        
        Criteria root = new Criteria();

        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt);
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt);
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
        }
        root.addAndCriteria(effectiveDateFilter);

        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("principalId", principalId);
        }

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (StringUtils.isNotBlank(jobNumber)) {
            root.addLike("jobNumber", jobNumber);
        }

        if (StringUtils.isNotBlank(dept)) {
            Criteria workAreaCriteria = new Criteria();
            Date asOfDate = toEffdt != null ? toEffdt : TKUtils.getCurrentDate();
            Collection<WorkArea> workAreasForDept = TkServiceLocator.getWorkAreaService().getWorkAreas(dept,asOfDate);
            if (CollectionUtils.isNotEmpty(workAreasForDept)) {
                List<Long> longWorkAreas = new ArrayList<Long>();
                for(WorkArea cwa : workAreasForDept){
                    longWorkAreas.add(cwa.getWorkArea());
                }
                workAreaCriteria.addIn("workArea", longWorkAreas);
            }
            root.addAndCriteria(workAreaCriteria);
        }

        if (StringUtils.isNotBlank(workArea)) {
            root.addLike("workArea", workArea);
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
    		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(ClockLocationRule.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
    		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(ClockLocationRule.class, EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(ClockLocationRule.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

}
