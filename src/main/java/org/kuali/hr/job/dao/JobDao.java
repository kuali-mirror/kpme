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

    List<Job> getJobs(String principalId, String jobNumber,
                      String dept, String positionNbr, String payType,
                      java.sql.Date fromEffdt, java.sql.Date toEffdt, String active, String showHistory);
    
    /**
     * Fetch the count of the jobs with the given principalId and jobNumber
     * @param principalId
     * @param jobNumber
     * @return the count of the jobs with the given principalId and jobNumber
     */
    public int getJobCount(String principalId, Long jobNumber, String dept);
    
    public List<Job> getActiveLeaveJobs(String principalId, Date asOfDate);
    
    public List<Job> getAllActiveLeaveJobs(String principalId, Date asOfDate);
    
    public List<Job> getInactiveLeaveJobs(Long jobNumber, Date startDate, Date endDate);
    
    public List<Job> getAllInActiveLeaveJobsInRange(String principalId, Date startDate, Date endDate);
    
    public Job getMaxTimestampJob(String principalId);
}
