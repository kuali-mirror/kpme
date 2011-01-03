package org.kuali.hr.job.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.job.Job;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class JobDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements JobDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(JobDaoSpringOjbImpl.class);

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
	
	public Job getPrimaryJob(String principalId, Date payPeriodEndDate){
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		effdt.addLessOrEqualThan("effectiveDate", payPeriodEndDate);
		effdt.addEqualTo("active", true);
		effdt.addEqualTo("principalId", principalId);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
		
		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		timestamp.addEqualTo("principalId", principalId);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
		
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);
		root.addEqualTo("primaryIndicator", true);
		
		Query query = QueryFactory.newQuery(Job.class, root);
		
		return (Job)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Job> getJobs(String principalId, Date payPeriodEndDate) {
		List<Job> jobs = new LinkedList<Job>();
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		effdt.addLessOrEqualThan("effectiveDate", payPeriodEndDate);
		effdt.addEqualTo("active", true);
		effdt.addEqualTo("principalId", principalId);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
		
		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		timestamp.addEqualTo("principalId", principalId);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
		
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);
		
		Query query = QueryFactory.newQuery(Job.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			jobs.addAll(c);
		}
		
		
		
		return jobs;
	}
	
	public Job getJob(String principalId, Long jobNumber, Date asOfDate){
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		effdt.addEqualTo("principalId", principalId);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Job.class, effdt);
		effdtSubQuery.setAttributes(new String[]{"max(effdt)"});
		
		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("jobNumber", Criteria.PARENT_QUERY_PREFIX + "jobNumber");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		timestamp.addEqualTo("principalId", principalId);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Job.class, timestamp);
		timestampSubQuery.setAttributes(new String[]{"max(timestamp)"});
		
		root.addEqualTo("principalId", principalId);
		root.addEqualTo("jobNumber", jobNumber);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);
		
		Query query = QueryFactory.newQuery(Job.class, root);
		Job job = (Job)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return job;
	}
}
