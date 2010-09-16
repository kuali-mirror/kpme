package org.kuali.hr.job.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.job.Job;

public interface JobDao {

	public void saveOrUpdate(Job job);

	public void saveOrUpdate(List<Job> jobList);
	
	/**
	 * Provides a list of current jobs that are valid relative to the provided effective date.  
	 * Timestamp of row creation is taken into account when two rows with the same job number
	 * have the same effective date. 
	 * 
	 * @param principalId
	 * @param payPeriodEndDate
	 * @return
	 */
	public List<Job> getJobs(String principalId, Date payPeriodEndDate);
	
	public Job getJob(String principalId, Long jobNumber, Date asOfDate);

}
