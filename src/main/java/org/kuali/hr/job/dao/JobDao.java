package org.kuali.hr.job.dao;

import org.kuali.hr.job.Job;

import java.util.Date;
import java.util.List;

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
	public List<Job> getActiveJobsForPosition(String positionNbr, Date asOfDate);
	
	/**
	 * Fetch active jobs that are incumbents of the payType
	 * @param hrPayType
	 * @param asOfDate
	 * @return
	 */
	public List<Job> getActiveJobsForPayType(String hrPayType, Date asOfDate);
	
	/**
	 * Get job based on id
	 * @param hrJobId
	 * @return
	 */
	public Job getJob(String hrJobId);
	
	/**
	 * Get job with max(jobNumber) for a certain principalId
	 * @param principalId
	 * @return
	 */
	public Job getMaxJob(String principalId);
}
