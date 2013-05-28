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
package org.kuali.kpme.core.job.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

/**
 * Represents an implementation of {@link JobDao}.
 */
public class JobDaoOjbImpl extends PlatformAwareDaoBaseOjb implements JobDao {
  

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(JobDaoOjbImpl.class);

    public void saveOrUpdate(Job job) {
        this.getPersistenceBrokerTemplate().store(job);
    }

    public void saveOrUpdate(List<Job> jobList) {
        if (jobList != null) {
            for (Job job : jobList) {
                this.saveOrUpdate(job);
            }
        }
    }

    public Job getPrimaryJob(String principalId, LocalDate payPeriodEndDate) {
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, payPeriodEndDate, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("primaryIndicator", true);

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);

        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Job> getJobs(String principalId, LocalDate payPeriodEndDate) {
        List<Job> jobs = new LinkedList<Job>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, payPeriodEndDate, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);


        Query query = QueryFactory.newQuery(Job.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            jobs.addAll(c);
        }


        return jobs;
    }

    public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate) {
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, asOfDate, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);
        Job job = (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        return job;
    }

    @SuppressWarnings("unchecked")
    public List<Job> getActiveJobsForPayType(String hrPayType, LocalDate asOfDate) {
        Criteria root = new Criteria();
        root.addEqualTo("hrPayType", hrPayType);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, asOfDate, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);
        return (List<Job>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    }

    @Override
    public Job getJob(String hrJobId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("hrJobId", hrJobId);

        Query query = QueryFactory.newQuery(Job.class, crit);
        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public Job getMaxJob(String principalId) {
        Criteria root = new Criteria();
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        ReportQueryByCriteria jobNumberSubQuery = QueryFactory.newReportQuery(Job.class, crit);
        jobNumberSubQuery.setAttributes(new String[]{"max(jobNumber)"});

        crit.addEqualTo("principalId", principalId);
        root.addEqualTo("jobNumber", jobNumberSubQuery);

        Query query = QueryFactory.newQuery(Job.class, root);
        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Job> getJobs(String principalId, String jobNumber, String dept, String positionNumber, String hrPayType, LocalDate fromEffdt, LocalDate toEffdt, 
    						 String active, String showHistory) {

        List<Job> results = new ArrayList<Job>();

        Criteria root = new Criteria();
        
        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("principalId", principalId);
        }

        if (StringUtils.isNotBlank(jobNumber)) {
            root.addLike("jobNumber", jobNumber);
        }

        if (StringUtils.isNotBlank(dept)) {
            root.addLike("dept", dept);
        }

        if (StringUtils.isNotBlank(positionNumber)) {
            root.addLike("positionNumber", positionNumber);
        }

        if (StringUtils.isNotBlank(hrPayType)) {
            root.addLike("hrPayType", hrPayType);
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
            ImmutableList<String> fields = new ImmutableList.Builder<String>()
                    .add("principalId")
                    .add("jobNumber")
                    .add("dept")
                    .add("positionNumber")
                    .add("hrPayType")
                    .build();
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(Job.class, effectiveDateFilter, Job.EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(Job.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    @Override
    public int getJobCount(String principalId, Long jobNumber, String dept) {
    	Criteria crit = new Criteria();
    	crit.addEqualTo("jobNumber", jobNumber);
    	if(principalId != null) {
    		crit.addEqualTo("principalId", principalId);
    	}
		if(dept != null) {
			crit.addEqualTo("dept", dept);
		}
		Query query = QueryFactory.newQuery(Job.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
    }
    
    @SuppressWarnings("unchecked")
	public List<Job> getActiveLeaveJobs(String principalId, LocalDate asOfDate) {
    	Criteria root = new Criteria();

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("eligibleForLeave", true);
        root.addEqualTo("active", true);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, asOfDate, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));
        
        Query query = QueryFactory.newQuery(Job.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        // remove jobs that has been inactive from the list
        List<Job> jobList = new ArrayList<Job>();
        jobList.addAll(c);
        List<Job> aList = new ArrayList<Job>();
        aList.addAll(jobList);
        for(Job aJob : aList) {
        	List<Job> inactiveJobs = this.getInactiveLeaveJobs(aJob.getJobNumber(), aJob.getEffectiveLocalDate());
        	if(!inactiveJobs.isEmpty()) {
        		jobList.remove(aJob);
        	}
        }
    	return jobList;
    }
    
    @Override
    @SuppressWarnings("unchecked")
	public List<Job> getInactiveLeaveJobs(Long jobNumber, LocalDate asOfDate) {
    	Criteria root = new Criteria();
        ImmutableList<String> fields = new ImmutableList.Builder<String>()
                .add("jobNumber")
                .build();

        root.addEqualTo("jobNumber", jobNumber);
        root.addEqualTo("eligibleForLeave", true);
        root.addEqualTo("active", false);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, asOfDate, fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, fields, false));
        
        Query query = QueryFactory.newQuery(Job.class, root);
        return (List<Job>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);    	
    }
    
    @Override
    public List<Job> getAllActiveLeaveJobs(String principalId, LocalDate asOfDate) {
    	Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addLessOrEqualThan("effectiveDate", asOfDate.toDate());
        root.addEqualTo("active", true);
        root.addEqualTo("eligibleForLeave", true);

        Query query = QueryFactory.newQuery(Job.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        List<Job> jobs = new LinkedList<Job>();
        if (c != null) {
            jobs.addAll(c);
        }
        return jobs;
    }
    
    public List<Job> getAllInActiveLeaveJobsInRange(String principalId, LocalDate endDate) {
    	Criteria root = new Criteria();    	
        root.addEqualTo("principalId", principalId);
        root.addLessOrEqualThan("effectiveDate", endDate.toDate());
        root.addEqualTo("active", false);
        Query query = QueryFactory.newQuery(Job.class, root);
        return (List<Job>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);    	
    }
    
    @Override
    public Job getMaxTimestampJob(String principalId) {
    	Criteria root = new Criteria();
        Criteria crit = new Criteria();
        
        crit.addEqualTo("principalId", principalId);
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, crit);
        timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("timestamp", timestampSubQuery);

        Query query = QueryFactory.newQuery(Job.class, root);
        return (Job) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    

    @SuppressWarnings("unchecked")
    public List<String> getPrincipalIdsInPosition(String positionNumber, LocalDate asOfDate) {
        Set<String> principalIdsInPosition = new HashSet<String>();
    	
    	Criteria root = new Criteria();
        root.addEqualTo("positionNumber", positionNumber);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Job.class, asOfDate, Job.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Job.class, Job.EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(Job.class, root);
        Collection<Job> jobs = getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        for (Job job : jobs) {
        	principalIdsInPosition.add(job.getPrincipalId());
        }
        
        return new ArrayList<String>(principalIdsInPosition);
    }
    
}