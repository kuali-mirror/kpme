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
package org.kuali.kpme.core.bo.salarygroup.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.salarygroup.SalGroup;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class SalGroupDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements SalGroupDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("hrSalGroup")
            .build();

	@Override
	public void saveOrUpdate(SalGroup salGroup) {
		this.getPersistenceBrokerTemplate().store(salGroup);		
	}

	@Override
	public SalGroup getSalGroup(String salGroup, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("hrSalGroup", salGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(SalGroup.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SalGroup.class, EQUAL_TO_FIELDS, false));
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(SalGroup.class, root);
		SalGroup s = (SalGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return s;
	}

	@Override
	public SalGroup getSalGroup(String hrSalGroupId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrSalGroupId", hrSalGroupId);
		
		Query query = QueryFactory.newQuery(SalGroup.class, crit);
		return (SalGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	@Override
	public int getSalGroupCount(String salGroup) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrSalGroup", salGroup);
		Query query = QueryFactory.newQuery(SalGroup.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<SalGroup> getSalGroups(String hrSalGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<SalGroup> results = new ArrayList<SalGroup>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(hrSalGroup)) {
            root.addLike("hrSalGroup", hrSalGroup);
        }
        
        if (StringUtils.isNotBlank(descr)) {
            root.addLike("descr", descr);
        }
        
        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", DateTime.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);

        if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(SalGroup.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SalGroup.class, EQUAL_TO_FIELDS, false));
        }

        Query query = QueryFactory.newQuery(SalGroup.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
	
}