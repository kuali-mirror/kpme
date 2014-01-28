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
package org.kuali.kpme.core.api.assignment.service;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.springframework.cache.annotation.Cacheable;

public interface AssignmentService {
	/**
	 * Fetches a list of Assignments for a given principal Id as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<? extends AssignmentContract> getAssignments(String principalId, LocalDate asOfDate);

    /**
     * Reverse lookup of an assignment based on the assignment id
     * @param tkAssignmentId
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'tkAssignmentId=' + #p0")
    public AssignmentContract getAssignment(String tkAssignmentId);
    /**
     * Get Assignment Description key based off of description
     * @param assignmentDesc
     * @return
     */
    public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentDesc);
    /**
     * Get all assignment descriptions for an assignment
     * @param assignment
     * @return
     */
	public Map<String,String> getAssignmentDescriptions(AssignmentContract assignment);
	/**
	 * Get all active assignments for a work area
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'workArea=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<? extends AssignmentContract> getActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate);

    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getPrincipalIdsInActiveAssigmentsForWorkArea}' + 'workArea=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<String> getPrincipalIdsInActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate);

    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getPrincipalIdsInActiveAssigmentsForWorkAreas}' + 'workAreas=' + T(org.kuali.rice.core.api.cache.CacheKeyUtils).key(#p0) + '|' + 'asOfDate=' + #p1")
    public List<String> getPrincipalIdsInActiveAssignmentsForWorkAreas(List<Long> workAreas, LocalDate asOfDate);

	/**
	 * Get active assignments for all users for the current date
	 * CAUTION this method will return a lot of data in a normal production env
	 * It is intended to only be used in a batch setting
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'asOfDate=' + #p0")
	public List<? extends AssignmentContract> getActiveAssignments(LocalDate asOfDate);


    /**
     * For a given AssignmentDescriptionKey return the matching assignment.
     * @param key
     * @return
     */
    public AssignmentContract getAssignmentForTargetPrincipal(AssignmentDescriptionKey key, LocalDate asOfDate);

    
    /**
     * Fetch principal id and key as of a particular date
     * @param principalId
     * @param key
     * @param asOfDate
     * @return
     */
    public AssignmentContract getAssignment(String principalId, AssignmentDescriptionKey key, LocalDate asOfDate);
    
    /**
     * Get assignments by pay calendar entry
     * @param principalId
     * @param payCalendarEntry
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentsByPayEntry}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public List<? extends AssignmentContract> getAssignmentsByPayEntry(String principalId, CalendarEntryContract payCalendarEntry);
    /**
     * Get assignments for Time Calendar by calendar entry
     * @param principalId
     * @param calendarEntry
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentsByCalEntryForTimeCalendar}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public List<? extends AssignmentContract> getAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntryContract calendarEntry);
    /**
     * Get assignments for Leave Calendar by calendar entry
     * @param principalId
     * @param calendarEntry
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentsByCalEntryForLeaveCalendar}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public List<? extends AssignmentContract> getAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntryContract calendarEntry);
    
    /**
	 * KPME-1129 Kagata
	 * Get a list of active assignments based on principalId and jobNumber as of a particular date 
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'jobNumber=' + #p1 + '|' + 'asOfDate=' + #p2")
    public List<? extends AssignmentContract> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate);

    List<? extends AssignmentContract> searchAssignments(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                    String dept, String workArea, String active, String showHistory);
    
    
    /**
     * Get all assignment descriptions for given list of Assignments
     * @param assignments
     * @return
     */
    public Map<String, String> getAssignmentDescriptionsForAssignments(List<? extends AssignmentContract>  assignments);
    
    public AssignmentContract getAssignment(List<? extends AssignmentContract> assignments, String assignmentKey, LocalDate beginDate);
    
    public AssignmentContract getMaxTimestampAssignment(String principalId);
    
    /**
     * Filter the given list of assignments with given criteria
     * @param assignments
     * @param flsaStatus
     * @param chkForLeaveEligible
     * @return List<Assignment>
     */
    public List<? extends AssignmentContract> filterAssignments(List<? extends AssignmentContract> assignments, String flsaStatus, boolean chkForLeaveEligible);
    
	/**
	 * Get list of unique principalIds with given workarea list and dates
	 * @param workAreaList
	 * @param effdt
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> getPrincipalIds(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate);
	
	public List<? extends AssignmentContract> getAssignments(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate);

    public String getAssignmentDescription(String principalId, Long jobNumber, Long workArea, Long task, LocalDate asOfDate);
}
