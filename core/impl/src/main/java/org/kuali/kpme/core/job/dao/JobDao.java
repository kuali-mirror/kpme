/**
 * Copyright 2004-2014 The Kuali Foundation
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

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.job.JobBo;

public interface JobDao {

	/**
	 * Saves or Updates a Job
	 * @param job
	 */
	public void saveOrUpdate(JobBo job);
	/**
	 * Saves or updates a job list 
	 * @param jobList
	 */
	public void saveOrUpdate(List<JobBo> jobList);
	
	/**
	 * Provides a list of current jobs that are valid relative to the provided effective date.  
	 * Timestamp of row creation is taken into account when two rows with the same job number
	 * have the same effective date. 
	 * 
	 * @param principalId
	 * @param payPeriodEndDate
	 * @return
	 */
	public List<JobBo> getJobs(String principalId, LocalDate payPeriodEndDate);
	/**
	 * 
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return a Job per the critieria passed in
	 */
	public JobBo getJob(String principalId, Long jobNumber, LocalDate asOfDate);
	
	/**
	 * Get Primary Job as indicated by primary indicator on Job table
	 * @param principalId
	 * @param payPeriodEndDate
	 * @return
	 */
	public JobBo getPrimaryJob(String principalId, LocalDate payPeriodEndDate);
	
	/**
	 * Fetch active jobs that are incumbents of the payType
	 * @param hrPayType
	 * @param asOfDate
	 * @return
	 */
	public List<JobBo> getActiveJobsForPayType(String hrPayType, LocalDate asOfDate);
	
	/**
	 * Get job based on id
	 * @param hrJobId
	 * @return
	 */
	public JobBo getJob(String hrJobId);
	
	/**
	 * Get job with max(jobNumber) for a certain principalId
	 * @param principalId
	 * @return
	 */
	public JobBo getMaxJob(String principalId);

    List<JobBo> getJobs(String principalId, String jobNumber, String dept, String positionNbr, String payType, LocalDate fromEffdt, LocalDate toEffdt, String active,
    				  String showHistory);
    
    /**
     * Fetch the count of the jobs with the given principalId and jobNumber
     * @param principalId
     * @param jobNumber
     * @return the count of the jobs with the given principalId and jobNumber
     */
    public int getJobCount(String principalId, Long jobNumber, String dept);
    
    public List<JobBo> getActiveLeaveJobs(String principalId, LocalDate asOfDate);
    
    public List<JobBo> getAllActiveLeaveJobs(String principalId, LocalDate asOfDate);
    
    public List<JobBo> getInactiveLeaveJobs(Long jobNumber, String principalId, LocalDate endDate);
    
    public List<JobBo> getAllInActiveLeaveJobsInRange(String principalId, LocalDate endDate);
    
    public JobBo getMaxTimestampJob(String principalId);
    
	/**
	 * Returns all of the principal ids actively particpating in a job in the given position number
	 * 
	 * @param positionNumber
	 * @param asOfDate
	 * @return
	 */
	public List<String> getPrincipalIdsInPosition(String positionNumber, LocalDate asOfDate);
	
}