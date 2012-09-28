/**
 * Copyright 2004-2012 The Kuali Foundation
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
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class WorkAreaDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements WorkAreaDao {

    private static final Logger LOG = Logger.getLogger(WorkAreaDaoSpringOjbImpl.class);

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
	public List<WorkArea> getWorkAreas(String dept, String workArea,
			String workAreaDescr, Date fromEffdt, Date toEffdt, String active,
			String showHistory) {
		Criteria crit = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		
		List<WorkArea> results = new ArrayList<WorkArea>();
		if(StringUtils.isNotBlank(dept)){
			crit.addLike("dept", dept);
		}
		if(StringUtils.isNotBlank(workArea)){
			crit.addLike("workArea", workArea);
		}
		if(StringUtils.isNotBlank(workAreaDescr)){
			crit.addLike("description", workAreaDescr);
		}
		if(fromEffdt != null){
			crit.addGreaterOrEqualThan("effectiveDate", fromEffdt);
		} 
		if(toEffdt != null){
			crit.addLessOrEqualThan("effectiveDate", toEffdt);
		} else {
			crit.addLessOrEqualThan("effectiveDate", TKUtils.getCurrentDate());
		}
		
		if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory,"Y")){
	        Query query = QueryFactory.newQuery(WorkArea.class, crit);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	        results.addAll(c);
		}
		else if(StringUtils.isEmpty(active) && StringUtils.equals(showHistory, "N")){
			effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			if(toEffdt != null){
				effdt.addLessOrEqualThan("effectiveDate", toEffdt);
			}
			ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
			effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

			timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
			ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
			timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
			if(StringUtils.isNotBlank(workArea)){
				crit.addEqualTo("workArea", workArea);
			}
			crit.addEqualTo("effectiveDate", effdtSubQuery);
			crit.addEqualTo("timestamp", timestampSubQuery);

	        Query query = QueryFactory.newQuery(WorkArea.class, crit);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	        results.addAll(c);
		}
		
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("N", showHistory)){
			effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			if(toEffdt != null){
				effdt.addLessOrEqualThan("effectiveDate", toEffdt);
			}
			ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
			effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

			timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
			ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
			timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
			if(StringUtils.isNotBlank(workArea)){
				crit.addEqualTo("workArea", workArea);
			}
			crit.addEqualTo("effectiveDate", effdtSubQuery);
			crit.addEqualTo("timestamp", timestampSubQuery);

			Criteria activeFilter = new Criteria(); // Inner Join For Activity
			activeFilter.addEqualTo("active", true);
			crit.addAndCriteria(activeFilter);
	        Query query = QueryFactory.newQuery(WorkArea.class, crit);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	        results.addAll(c);
		} //return all active records from the database
		else if(StringUtils.equals(active, "Y") && StringUtils.equals("Y", showHistory)){
			Criteria activeFilter = new Criteria(); // Inner Join For Activity
			activeFilter.addEqualTo("active", true);
			crit.addAndCriteria(activeFilter);
	        Query query = QueryFactory.newQuery(WorkArea.class, crit);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	        results.addAll(c);
		} 
		//return all inactive records in the database
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "Y")){
			Criteria activeFilter = new Criteria(); // Inner Join For Activity
			activeFilter.addEqualTo("active", false);
			crit.addAndCriteria(activeFilter);
	        Query query = QueryFactory.newQuery(WorkArea.class, crit);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	        results.addAll(c);
		}
		
		//return the most effective inactive rows if there are no active rows <= the curr date
		else if(StringUtils.equals(active, "N") && StringUtils.equals(showHistory, "N")){
			effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			if(toEffdt != null){
				effdt.addLessOrEqualThan("effectiveDate", toEffdt);
			}
			ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WorkArea.class, effdt);
			effdtSubQuery.setAttributes(new String[]{"max(effdt)"});

			timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
			timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
			ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WorkArea.class, timestamp);
			timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
			if(StringUtils.isNotBlank(workArea)){
				crit.addEqualTo("workArea", workArea);
			}
			crit.addEqualTo("effectiveDate", effdtSubQuery);
			crit.addEqualTo("timestamp", timestampSubQuery);

			Criteria activeFilter = new Criteria(); // Inner Join For Activity
			activeFilter.addEqualTo("active", false);
			crit.addAndCriteria(activeFilter);
	        Query query = QueryFactory.newQuery(WorkArea.class, crit);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	        results.addAll(c);
			
		}
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
