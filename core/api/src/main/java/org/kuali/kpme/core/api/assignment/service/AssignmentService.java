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

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

public interface AssignmentService {
	/**
	 * Fetches a list of Assignments for a given principal Id as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<Assignment> getAssignments(String principalId, LocalDate asOfDate);

    /**
     * Reverse lookup of an assignment based on the assignment id
     * @param tkAssignmentId
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'tkAssignmentId=' + #p0")
    public Assignment getAssignment(String tkAssignmentId);
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
	public Map<String,String> getAssignmentDescriptions(Assignment assignment);
	/**
	 * Get all active assignments for a work area
	 * @param workArea
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'workArea=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<Assignment> getActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate);

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
	public List<Assignment> getActiveAssignments(LocalDate asOfDate);


    /**
     * For a given AssignmentDescriptionKey return the matching assignment.
     * @param key
     * @return
     */
    public Assignment getAssignmentForTargetPrincipal(AssignmentDescriptionKey key, LocalDate asOfDate);

    
    /**
     * Fetch principal id and key as of a particular date
     * @param principalId
     * @param key
     * @param asOfDate
     * @return
     */
    public Assignment getAssignment(String principalId, AssignmentDescriptionKey key, LocalDate asOfDate);
    
    /**
     * Get assignments by pay calendar entry
     * @param principalId
     * @param payCalendarEntry
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentsByPayEntry}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public List<Assignment> getAssignmentsByPayEntry(String principalId, CalendarEntry payCalendarEntry);
    /**
     * Get assignments for Time Calendar by calendar entry
     * @param principalId
     * @param calendarEntry
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentsByCalEntryForTimeCalendar}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public Map<LocalDate, List<Assignment>> getAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntry calendarEntry);

    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAllAssignmentsByCalEntryForTimeCalendar}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public List<Assignment> getAllAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntry calendarEntry);
    /**
     * Get assignments for Leave Calendar by calendar entry
     * @param principalId
     * @param calendarEntry
     * @return
     */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentsByCalEntryForLeaveCalendar}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public Map<LocalDate, List<Assignment>> getAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntry calendarEntry);

    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAllAssignmentsByCalEntryForLeaveCalendar}' + 'principalId=' + #p0 + '|' + 'payCalendarEntry=' + #p1.getHrCalendarEntryId()")
    public List<Assignment> getAllAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntry calendarEntry);

    /**
	 * KPME-1129 Kagata
	 * Get a list of active assignments based on principalId and jobNumber as of a particular date 
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'jobNumber=' + #p1 + '|' + 'asOfDate=' + #p2")
    public List<Assignment> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate);

    //public List<Assignment> filterLookupAssignments(List<Assignment> rawResults, String userPrincipalId);


    /**
     * Get all assignment descriptions for given list of Assignments
     * @param assignments
     * @return
     */
    public Map<String, String> getAssignmentDescriptionsForAssignments(List<Assignment>  assignments);
    
    public Assignment getAssignment(List<Assignment> assignments, String assignmentKey, LocalDate beginDate);
    
    public Assignment getMaxTimestampAssignment(String principalId);
    
    /**
     * Filter the given list of assignments with given criteria
     * @param assignments
     * @param flsaStatus
     * @param chkForLeaveEligible
     * @return List<Assignment>
     */
    public List<Assignment> filterAssignments(List<Assignment> assignments, String flsaStatus, boolean chkForLeaveEligible);
    
	/**
	 * Get list of unique principalIds with given workarea list and dates
	 * @param workAreaList
	 * @param effdt
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<String> getPrincipalIds(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate);
	
	public List<Assignment> getAssignments(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate);

    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentDescription}' + 'principalId=' + #p0 + '|' + 'jobNumber=' + #p1 + '|' + 'workArea=' + #p2 + '|' + 'task=' + #p3 + '|' + 'asOfDate=' + #p4")
    public String getAssignmentDescription(String principalId, String groupKeyCode, Long jobNumber, Long workArea, Long task, LocalDate asOfDate);

//    @Cacheable(value= AssignmentContract.CACHE_NAME, key="'{getAssignmentDescriptionForAssignment}' + 'assignmentId=' + #p0.getId() + '|' + 'asOfDate=' + #p4")
    public String getAssignmentDescriptionForAssignment(Assignment assignment, LocalDate asOfDate);

    //@Cacheable(value= Assignment.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'clockOnlyAssignments=' + #p1 + '|' + 'asOfDate=' + #p2 + '|' + 'userPrincipalId=' + T(org.kuali.hr.time.util.TKContext).getPrincipalId()")
    //public Map<String, String> getAssignmentDescriptionsForDay(String principalId, boolean clockOnlyAssignments, LocalDate asOfDate );

    @Cacheable(value= Assignment.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'calendarEntry=' + #p1.getHrCalendarEntryId()")
    public Map<LocalDate, List<Assignment>> getAssignmentHistoryForCalendarEntry(String principalId, CalendarEntry calendarEntry);

    @Cacheable(value= Assignment.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2")
    public Map<LocalDate, List<Assignment>> getAssignmentHistoryBetweenDays(String principalId, LocalDate beginDate, LocalDate endDate);

    public List<Assignment> filterAssignmentListForUser(String userPrincipalId, List<Assignment> assignments);

    /**
     * Get list of assignments for a user for a period between the current date and the current date minus the value
     * assigned to the configuration property kpme.tklm.target.employee.time.limit
     *
     * This list is unfiltered for FLSA status
     *
     * @param principalId principalId of user
     * @return list of Assignments
     */
    @Cacheable(value= Assignment.CACHE_NAME, key="'{getRecentAssignments}' + 'principalId=' + #p0")
    public List<Assignment> getRecentAssignments(String principalId);

    /**
     * Get list of assignments for a user for a period between the start and end dates
     *
     * This list is unfiltered for FLSA status
     *
     * @param principalId principalId of user
     * @return list of Assignments
     */
    @Cacheable(value= Assignment.CACHE_NAME, key="'{getRecentAssignmentsBetweenDays}' + 'principalId=' + #p0 + '|' + 'startDate=' + #p1 + '|' + 'endDate=' + #p2")
    public List<Assignment> getRecentAssignmentsBetweenDays(String principalId, LocalDate startDate, LocalDate endDate);
}
