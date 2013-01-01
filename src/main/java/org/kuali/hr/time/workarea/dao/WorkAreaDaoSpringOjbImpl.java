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
package org.kuali.hr.time.workarea.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class WorkAreaDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements WorkAreaDao {

    @Override
    public WorkArea getWorkArea(Long workArea, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

		root.addEqualTo("workArea", workArea);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(WorkArea.class, root);
		return (WorkArea) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<WorkArea> getWorkArea(String department, Date asOfDate) {
        Criteria root = new Criteria();
        Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
        effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        effdt.addLessOrEqualThan("effectiveDate", asOfDate);
        ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
        effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

        timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
        timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("dept", department);
        root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

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
	public List<WorkArea> getWorkAreas(String dept, String workArea, String workAreaDescr, Date fromEffdt, Date toEffdt, String active, String showHistory) {
		List<WorkArea> results = new ArrayList<WorkArea>();
		
		Criteria root = new Criteria();

		if (StringUtils.isNotBlank(dept)) {
			root.addLike("dept", dept);
		}
		
		if (StringUtils.isNotBlank(workArea)) {
			root.addLike("workArea", workArea);
		}
		
		if (StringUtils.isNotBlank(workAreaDescr)) {
			root.addLike("description", workAreaDescr);
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
			effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
			effdtSubQuery.setAttributes(new String[]{"max(effectiveDate)"});
			root.addEqualTo("effectiveDate", effdtSubQuery);
			
			Criteria timestamp = new Criteria();
			timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
			ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
			timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
			root.addEqualTo("timestamp", timestampSubQuery);
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
