package org.kuali.hr.job.service;

import org.kuali.hr.job.Job;

import java.util.Date;
import java.util.List;

public interface JobService {

	/**
	 * Updates or saves a job
	 * @param job
	 */
	public void saveOrUpdate(Job job);
	
	/**
	 * Updates or saves a list of jobs
	 * @param jobList
	 */
	public void saveOrUpdate(List<Job> jobList);
	
	/**
	 * Provides a list of current jobs that are valid relative to the provided effective date.  
	 * Timestamp of row creation is taken into account when two rows with the same job number
	 * have the same effective date.
	 * 
	 * Assignments are NOT populated on this object.  We may want to consider removing 
	 * getAssignments().
	 * 
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public List<Job> getJobs(String principalId, Date asOfDate);
	
	/**
	 * Provides a job by specific job number, principal ID and as of Date combination. 
	 */
	public Job getJob(String principalId, Long jobNumber, Date asOfDate);
	
	/**
	 * Provides a job by specific job number, principal ID and as of Date combination, and check details will throw error if required. 
	 */
	public Job getJob(String principalId, Long jobNumber, Date asOfDate, boolean chkDetails);
	
	/**
	 * For a given principal ID, the job that is marked "primary" is returned 
	 * here.
	 * 
	 * @param principalId The principal under investigation
	 * @param asOfDate Run the request as of this date. 
	 * @return
	 */
	public Job getPrimaryJob(String principalId, Date asOfDate);
	
	/**
	 * 
	 * @param positionNbr
	 * @param asOfDate
	 * @return
	 */
	public List<Job> getActiveJobsForPosition(String positionNbr, Date asOfDate);
	
	/**
	 * 
	 * @param hrPayType
	 * @param asOfDate
	 * @return
	 */
	public List<Job> getActiveJobsForPayType(String hrPayType, Date asOfDate);
	
	/**
	 * Get job by the unique id
	 * @param hrJobId
	 * @return
	 */
	public Job getJob(String hrJobId);
	
	/**
	 * Get the max jobnumber job for this principal
	 * @param principalId
	 * @return
	 */
	public Job getMaxJob(String principalId);

    List<Job> getJobs(String principalId, String firstName, String lastName, String jobNumber,
                      String dept, String positionNbr, String payType,
                      java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory);
    
    public int getJobCount(String principalId, Long jobNumber);
}
