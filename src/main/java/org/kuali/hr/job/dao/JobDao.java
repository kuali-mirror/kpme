package org.kuali.hr.job.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.job.Job;

public interface JobDao {

	/**
	 * Saves or Updates a Job
	 * @param job
	 */
	public void saveOrUpdate(Job job);
	/**
	 * Saves or updates a job list 
	 * @param jobList
	 */
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
	/**
	 * 
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return a Job per the critieria passed in
	 */
	public Job getJob(String principalId, Long jobNumber, Date asOfDate);
	
	/**
	 * Get Primary Job as indicated by primary indicator on Job table
	 * @param principalId
	 * @param payPeriodEndDate
	 * @return
	 */
	public Job getPrimaryJob(String principalId, Date payPeriodEndDate);
	/**
	 * Fetch active jobs that are incumbents of the position
	 * @param positionNbr
	 * @param asOfDate
	 * @return
	 */
	public List<Job> getActiveJobsForPosition(Long positionNbr, Date asOfDate);
}
