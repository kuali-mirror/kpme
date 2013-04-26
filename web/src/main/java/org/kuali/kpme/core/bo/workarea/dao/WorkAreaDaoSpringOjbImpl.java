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
package org.kuali.kpme.core.bo.workarea.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.google.common.collect.ImmutableList;

public class WorkAreaDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements WorkAreaDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("workArea")
            .build();

    @Override
    public WorkArea getWorkArea(Long workArea, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("workArea", workArea);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkArea.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkArea.class, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(WorkArea.class, root);
		return (WorkArea) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<WorkArea> getWorkArea(String department, LocalDate asOfDate) {
        Criteria root = new Criteria();

        root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(WorkArea.class, asOfDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkArea.class, EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(WorkArea.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<WorkArea> wal = new ArrayList<WorkArea>(c.size());
        wal.addAll(c);
        return wal;
    }

    @Override
    public void saveOrUpdate(WorkArea workArea) {
    	this.getPersistenceBrokerTemplate().store(workArea);
    }

	@Override
	public WorkArea getWorkArea(String tkWorkAreaId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkWorkAreaId", tkWorkAreaId);
		
		Query query = QueryFactory.newQuery(WorkArea.class, crit);
		return (WorkArea)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public Long getNextWorkAreaKey(){
		 return KRADServiceLocator.getSequenceAccessorService().getNextAvailableSequenceNumber("tk_work_area_key_s");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<WorkArea> getWorkAreas(String dept, String workArea, String description, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
		List<WorkArea> results = new ArrayList<WorkArea>();
		
		Criteria root = new Criteria();

		if (StringUtils.isNotBlank(dept)) {
			root.addLike("dept", dept);
		}
		
		if (StringUtils.isNotBlank(workArea)) {
			root.addLike("workArea", workArea);
		}
		
		if (StringUtils.isNotBlank(description)) {
			root.addLike("description", description);
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(WorkArea.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(WorkArea.class, EQUAL_TO_FIELDS, false));
		}
		
        Query query = QueryFactory.newQuery(WorkArea.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
		
		return results;
	}

	@Override
	public int getWorkAreaCount(String dept, Long workArea) {
		Criteria crit = new Criteria();
		if(dept != null) {
			crit.addEqualTo("dept", dept);
		}
		crit.addEqualTo("workArea", workArea);
		Query query = QueryFactory.newQuery(WorkArea.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query); 
	}
}
