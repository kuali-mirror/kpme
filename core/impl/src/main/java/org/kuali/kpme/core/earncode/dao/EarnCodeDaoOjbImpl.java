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
package org.kuali.kpme.core.earncode.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EarnCodeDaoOjbImpl extends PlatformAwareDaoBaseOjb implements EarnCodeDao {
   private static final Logger LOG = Logger.getLogger(EarnCodeDaoOjbImpl.class);

	public void saveOrUpdate(EarnCodeBo earnCode) {
		this.getPersistenceBrokerTemplate().store(earnCode);
	}

	public void saveOrUpdate(List<EarnCodeBo> earnCodeList) {
		if (earnCodeList != null) {
			for (EarnCodeBo earnCode : earnCodeList) {
				this.getPersistenceBrokerTemplate().store(earnCode);
			}
		}
	}

	public EarnCodeBo getEarnCodeById(String earnCodeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrEarnCodeId", earnCodeId);
		return (EarnCodeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EarnCodeBo.class, crit));
	}

	@Override
	public EarnCodeBo getEarnCode(String earnCode, LocalDate asOfDate) {
		EarnCodeBo ec = null;

		Criteria root = new Criteria();

		root.addEqualTo("earnCode", earnCode);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeBo.class, asOfDate, EarnCodeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeBo.class, EarnCodeBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(EarnCodeBo.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			ec = (EarnCodeBo) obj;
		}

		return ec;
	}

	@Override
	public List<EarnCodeBo> getOvertimeEarnCodes(LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("ovtEarnCode", "Y");
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeBo.class, asOfDate, EarnCodeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeBo.class, EarnCodeBo.BUSINESS_KEYS, false));
//		root.addEqualTo("active", true);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(EarnCodeBo.class, root);
		List<EarnCodeBo> ovtEarnCodes = (List<EarnCodeBo>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		return ovtEarnCodes;
	}
	
	@Override
	public int getEarnCodeCount(String earnCode) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", earnCode);
		Query query = QueryFactory.newQuery(EarnCodeBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
	
	@Override
	public int getNewerEarnCodeCount(String earnCode, LocalDate effdt) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCode", earnCode);
		crit.addEqualTo("active", "Y");
		if(effdt != null) {
			crit.addGreaterThan("effectiveDate", effdt.toDate());
		}
		Query query = QueryFactory.newQuery(EarnCodeBo.class, crit);
       	return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
	public List<EarnCodeBo> getEarnCodes(String leavePlan, LocalDate asOfDate) {
		List<EarnCodeBo> earnCodes = new ArrayList<EarnCodeBo>();
		Criteria root = new Criteria();

        List<String> fields = new ArrayList<String>();
        fields.add("earnCode");
        fields.add("leavePlan");
		root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EarnCodeBo.class, asOfDate, fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeBo.class, fields, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(EarnCodeBo.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			earnCodes.addAll(c);
		}	
		return earnCodes;
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<EarnCodeBo> getEarnCodes(String earnCode, String ovtEarnCode, String descr, String leavePlan, String accrualCategory, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<EarnCodeBo> results = new ArrayList<EarnCodeBo>();
        
        Criteria root = new Criteria();
        
        if (StringUtils.isNotBlank(earnCode)) {
            root.addLike("earnCode", earnCode);
        }
        
        if (StringUtils.isNotBlank(ovtEarnCode)) {
            root.addEqualTo("ovtEarnCode", ovtEarnCode);
        }
        
        if (StringUtils.isNotBlank(descr)) {
            root.addLike("UPPER(descr)", descr.toUpperCase()); // KPME-2695
        }

        if (StringUtils.isNotBlank(leavePlan)) {
        	root.addLike("UPPER(leavePlan)", leavePlan.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(accrualCategory)) {
        	root.addLike("UPPER(accrualCategory)", accrualCategory.toUpperCase()); // KPME-2695
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(EarnCodeBo.class, effectiveDateFilter, EarnCodeBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EarnCodeBo.class, EarnCodeBo.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(EarnCodeBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
	
}