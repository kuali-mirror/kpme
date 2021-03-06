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
package org.kuali.kpme.core.accrualcategory.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategoryBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class AccrualCategoryDaoOjbImpl extends PlatformAwareDaoBaseOjb implements AccrualCategoryDao {
   
    @Override
    public AccrualCategoryBo getAccrualCategory(String accrualCategory, LocalDate asOfDate) {
    	AccrualCategoryBo accrlCategory = null;
		Criteria root = new Criteria();

        root.addEqualTo("accrualCategory", accrualCategory);
		root.addEqualTo("active",true);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AccrualCategoryBo.class, asOfDate, AccrualCategoryBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AccrualCategoryBo.class, AccrualCategoryBo.BUSINESS_KEYS, false));
		
		Query query = QueryFactory.newQuery(AccrualCategoryBo.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		if (obj != null) {
			accrlCategory = (AccrualCategoryBo) obj;
		}

		return accrlCategory;
    }
    
    //@Override
    //public AccrualCategoryBo saveOrUpdate(AccrualCategoryBo accrualCategory) {
    //	return this.getPersistenceBrokerTemplate().store(accrualCategory);
    //}

	@Override
	public AccrualCategoryBo getAccrualCategory(String lmAccrualCategoryId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmAccrualCategoryId", lmAccrualCategoryId);
		
		Query query = QueryFactory.newQuery(AccrualCategoryBo.class, crit);
		return (AccrualCategoryBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
	}

	@Override
	// KPME-1011
    public List<AccrualCategoryBo> getActiveAccrualCategories(LocalDate asOfDate) {
		List<AccrualCategoryBo> accrualCategories = new ArrayList<AccrualCategoryBo>();
		Criteria root = new Criteria();
		Criteria timestampSubCrit = new Criteria();
		
		timestampSubCrit.addEqualToField("accrualCategory", Criteria.PARENT_QUERY_PREFIX + "accrualCategory");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(AccrualCategoryBo.class, timestampSubCrit);
		timestampSubQuery.setAttributes(new String[]{ "max(timestamp)" });
		
		root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active",true);
		
		Query query = QueryFactory.newQuery(AccrualCategoryBo.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			accrualCategories.addAll(c);
		}

		return accrualCategories;
    }

	@Override
    @SuppressWarnings("unchecked")
	public List<AccrualCategoryBo> getAccrualCategories(String accrualCategory, String descr, String leavePlan, String accrualEarnInterval,
													  String unitOfTime, String minPercentWorked, LocalDate fromEffdt, LocalDate toEffdt, String active, 
													  String showHistory) {
        
        List<AccrualCategoryBo> results = new ArrayList<AccrualCategoryBo>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(accrualCategory)) {
            root.addLike("UPPER(accrualCategory)", accrualCategory.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(descr)) {
            root.addLike("UPPER(descr)", descr.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(leavePlan)) {
        	root.addLike("UPPER(leavePlan)", leavePlan.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(accrualEarnInterval)) {
        	root.addLike("accrualEarnInterval", accrualEarnInterval);
        }
        
        if (StringUtils.isNotBlank(unitOfTime)) {
        	root.addLike("unitOfTime", unitOfTime);
        }
        
        if (StringUtils.isNotBlank(minPercentWorked)) {
        	root.addLike("minPercentWorked", minPercentWorked);
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(AccrualCategoryBo.class, effectiveDateFilter, AccrualCategoryBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AccrualCategoryBo.class, AccrualCategoryBo.BUSINESS_KEYS, false));
        }

        Query query = QueryFactory.newQuery(AccrualCategoryBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
    
	public List<AccrualCategoryBo> getActiveAccrualCategories(String leavePlan, LocalDate asOfDate){
		List<AccrualCategoryBo> accrualCategories = new ArrayList<AccrualCategoryBo>();
		
		Criteria root = new Criteria();

		root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(AccrualCategoryBo.class, asOfDate, AccrualCategoryBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(AccrualCategoryBo.class, AccrualCategoryBo.BUSINESS_KEYS, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(AccrualCategoryBo.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			accrualCategories.addAll(c);
		}
		return accrualCategories;
	}
    
    @Override
	public List<AccrualCategoryBo> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate){
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
		root.addNotEqualTo("accrualEarnInterval", "N");
		root.addEqualTo("active", true);
		
		Query query = QueryFactory.newQuery(AccrualCategoryBo.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<AccrualCategoryBo> accrualCategories = new ArrayList<AccrualCategoryBo>();

		if (c != null) {
			accrualCategories.addAll(c);
		}
		return accrualCategories;
	}
	
	@Override
	public List <AccrualCategoryBo> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addNotEqualTo("accrualEarnInterval", "N");
		root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
		root.addEqualTo("active", false);
		Query query = QueryFactory.newQuery(AccrualCategoryBo.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		List<AccrualCategoryBo> accrualCategories = new ArrayList<AccrualCategoryBo>();

		if (c != null) {
			accrualCategories.addAll(c);
		}
		return accrualCategories;
	}
}
