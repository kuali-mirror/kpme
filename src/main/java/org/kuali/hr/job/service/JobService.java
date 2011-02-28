package org.kuali.hr.job.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.job.Job;

public interface JobService {

	public void saveOrUpdate(Job job);
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
	
}
