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
package org.kuali.kpme.core.earncode.group.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupBo;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupDefinitionBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EarnCodeGroupDaoOjbImpl extends PlatformAwareDaoBaseOjb implements EarnCodeGroupDao {
    
	@Override
	public EarnCodeGroupBo getEarnCodeGroup(String earnGroup, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("earnCodeGroup", earnGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroupBo.class, asOfDate, EarnCodeGroupBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroupBo.class, EarnCodeGroupBo.BUSINESS_KEYS, false));
//		root.addEqualTo("active", true);
		//do not include the summary setup earn groups

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
				
		Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, root);
		EarnCodeGroupBo earnGroupObj  = (EarnCodeGroupBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnCodeGroupBo getEarnCodeGroupSummaryForEarnCode(String earnCode, LocalDate asOfDate) {
		Criteria root = new Criteria();
		Criteria earnCodeJoin = new Criteria();
		
		earnCodeJoin.addEqualToField("hrEarnCodeGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnCodeGroupId");
		earnCodeJoin.addEqualTo("UPPER(earnCode)", earnCode.toUpperCase());
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnCodeGroupDefinitionBo.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_code_group_id"});
		
		root.addEqualTo("hrEarnCodeGroupId",earnCodeSubQuery);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroupBo.class, asOfDate, EarnCodeGroupBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroupBo.class, EarnCodeGroupBo.BUSINESS_KEYS, false));
//		root.addEqualTo("active", true);
		root.addEqualTo("showSummary", true);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, root);
		EarnCodeGroupBo earnGroupObj  = (EarnCodeGroupBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnCodeGroupBo getEarnCodeGroupForEarnCode(String earnCode, LocalDate asOfDate) {
		Criteria root = new Criteria();
		Criteria earnCodeJoin = new Criteria();

		earnCodeJoin.addEqualToField("hrEarnCodeGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnCodeGroupId");
		earnCodeJoin.addEqualTo("UPPER(earnCode)", earnCode.toUpperCase());
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnCodeGroupDefinitionBo.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_code_group_id"});
		
		root.addEqualTo("hrEarnCodeGroupId",earnCodeSubQuery);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroupBo.class, asOfDate, EarnCodeGroupBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroupBo.class, EarnCodeGroupBo.BUSINESS_KEYS, false));
//		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, root);
		EarnCodeGroupBo earnGroupObj  = (EarnCodeGroupBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}
	
	// KPME-2529
	@Override
	public List<EarnCodeGroupBo> getEarnCodeGroupsForEarnCode(String earnCode, LocalDate asOfDate) {
		
		Criteria root = new Criteria();
		Criteria earnCodeJoin = new Criteria();

		earnCodeJoin.addEqualToField("hrEarnCodeGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnCodeGroupId");
		earnCodeJoin.addEqualTo("UPPER(earnCode)", earnCode.toUpperCase());
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnCodeGroupDefinitionBo.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_code_group_id"});
		
		root.addEqualTo("hrEarnCodeGroupId",earnCodeSubQuery);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeGroupBo.class, asOfDate, EarnCodeGroupBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroupBo.class, EarnCodeGroupBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, root);
	      
	    List<EarnCodeGroupBo> results = new ArrayList<EarnCodeGroupBo>();
	    results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
	    
	    return results;
	}

	@Override
	public EarnCodeGroupBo getEarnCodeGroup(String hrEarnCodeGroupId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrEarnCodeGroupId", hrEarnCodeGroupId);
		
		Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, crit);
		return (EarnCodeGroupBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getEarnCodeGroupCount(String earnCodeGroup) {
		Criteria crit = new Criteria();
	    crit.addEqualTo("earnCodeGroup", earnCodeGroup);
	    Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, crit);
	    return this.getPersistenceBrokerTemplate().getCount(query);
	}
	@Override
	public int getNewerEarnCodeGroupCount(String earnCodeGroup, LocalDate effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCodeGroup", earnCodeGroup);
		crit.addEqualTo("active", "Y");
		crit.addGreaterThan("effectiveDate", effdt.toDate());
		Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	public List<EarnCodeBo> getEarnCodeGroups(String earnCodeGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
        List<EarnCodeBo> results = new ArrayList<EarnCodeBo>();
        
        Criteria root = new Criteria();
        
        if (StringUtils.isNotBlank(earnCodeGroup)) {
            root.addLike("UPPER(earnCodeGroup)", earnCodeGroup.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(descr)) {
            root.addLike("UPPER(descr)", descr.toUpperCase()); // KPME-2695
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

        if (StringUtils.equals(showHist, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(EarnCodeGroupBo.class, effectiveDateFilter, EarnCodeGroupBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeGroupBo.class, EarnCodeGroupBo.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(EarnCodeGroupBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
	}
	
}
