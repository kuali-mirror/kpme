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
package org.kuali.kpme.core.bo.job.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.job.Job;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface JobService {

	/**
	 * Updates or saves a job
	 * @param job
	 */
    @CacheEvict(value={Job.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(Job job);
	
	/**
	 * Updates or saves a list of jobs
	 * @param jobList
	 */
    @CacheEvict(value={Job.CACHE_NAME}, allEntries = true)
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
    @Cacheable(value= Job.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<Job> getJobs(String principalId, LocalDate asOfDate);
	
	/**
	 * Provides a job by specific job number, principal ID and as of Date combination. 
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'jobNumber=' + #p1 + '|' + 'asOfDate=' + #p2")
	public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate);
	
	/**
	 * Provides a job by specific job number, principal ID and as of Date combination, and check details will throw error if required. 
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'jobNumber=' + #p1 + '|' + 'asOfDate=' + #p2 + '|' + 'chkDetails=' + #p3")
	public Job getJob(String principalId, Long jobNumber, LocalDate asOfDate, boolean chkDetails);
	
	/**
	 * For a given principal ID, the job that is marked "primary" is returned 
	 * here.
	 * 
	 * @param principalId The principal under investigation
	 * @param asOfDate Run the request as of this date. 
	 * @return
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'{getPrimaryJob}' + 'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
	public Job getPrimaryJob(String principalId, LocalDate asOfDate);
	
	/**
	 * 
	 * @param hrPayType
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'hrPayType=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<Job> getActiveJobsForPayType(String hrPayType, LocalDate asOfDate);
	
	/**
	 * Get job by the unique id
	 * @param hrJobId
	 * @return
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'hrJobId=' + #p0")
	public Job getJob(String hrJobId);
	
	/**
	 * Get the max jobnumber job for this principal
	 * @param principalId
	 * @return
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'principalId=' + #p0")
	public Job getMaxJob(String principalId);

    List<Job> getJobs(String userPrincipalId, String principalId, String firstName, String lastName, String jobNumber,
                      String dept, String positionNbr, String payType,
                      LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    public int getJobCount(String principalId, Long jobNumber, String dept);
    
	/**
	 * Get list of active jobs eligible for leave for given principal and date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    public List<Job> getActiveLeaveJobs(String principalId, LocalDate asOfDate);
    
    /**
	 * Get sum of fte of given jobs
	 * @param jobs
	 * @return
	 */
    public BigDecimal getFteSumForJobs(List<Job> jobs);
    
    /**
     * Returns FTE for all active LM eligible jobs.
     * @param PrincipalId
     * @return
     */
    public BigDecimal getFteSumForAllActiveLeaveEligibleJobs(String PrincipalId, LocalDate asOfDate);
    /**
	 * Get sum of standard hours of given jobs
	 * @param jobs
	 * @return
	 */
    public BigDecimal getStandardHoursSumForJobs(List<Job> jobs);
    
    /**
	 * Get list of all active jobs eligible for leave for given principal and date range
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    public List<Job> getAllActiveLeaveJobs(String principalId, LocalDate asOfDate);
    
    public List<Job> getInactiveLeaveJobs(Long jobNumber, LocalDate endDate);
    
    public List<Job> getAllInActiveLeaveJobsInRange(String principalId, LocalDate endDate);
    
    /*
     * Get the job entry with the max timestamp for given pricipalId
     */
    public Job getMaxTimestampJob(String principalId);
    
	
	/**
	 * Returns all of the principal ids actively particpating in a job in the given position number
	 * 
	 * @param positionNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Job.CACHE_NAME, key="'positionNumber=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<String> getPrincipalIdsInPosition(String positionNbr, LocalDate asOfDate);
    
}
