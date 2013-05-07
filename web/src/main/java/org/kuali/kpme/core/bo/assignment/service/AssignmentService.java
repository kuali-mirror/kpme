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
package org.kuali.kpme.core.bo.assignment.service;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.document.calendar.CalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.springframework.cache.annotation.Cacheable;

public interface AssignmentService {
	/**
	 * Fetches a list of Assignments for a given principal Id as of a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Assignment.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
    public List<Assignment> getAssignments(String principalId, LocalDate asOfDate);
    /**
     * Reverse lookup of an assignment based on the assignment key and the document
     * @param timesheetDocument
     * @param assignmentKey
     * @return
     */
    public Assignment getAssignment(TimesheetDocument timesheetDocument, String assignmentKey);
    /**
     * Reverse lookup of an assignment based on the assignment id
     * @param tkAssignmentId
     * @return
     */
    @Cacheable(value= Assignment.CACHE_NAME, key="'tkAssignmentId=' + #p0")
    public Assignment getAssignment(String tkAssignmentId);
    /**
     * Get Assignment Description key based off of description
     * @param assignmentDesc
     * @return
     */
    public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentDesc);
    /**
     * Get all assignment descriptions for a document
     * @param td
     * @param clockOnlyAssignments
     * @return
     */
    public Map<String,String> getAssignmentDescriptions(CalendarDocument td, boolean clockOnlyAssignments);
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
    @Cacheable(value= Assignment.CACHE_NAME, key="'workArea=' + #p0 + '|' + 'asOfDate=' + #p1")
	public List<Assignment> getActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate);

	/**
	 * Get active assignments for all users for the current date
	 * CAUTION this method will return a lot of data in a normal production env
	 * It is intended to only be used in a batch setting
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Assignment.CACHE_NAME, key="'asOfDate=' + #p0")
	public List<Assignment> getActiveAssignments(LocalDate asOfDate);


    /**
     * For a given AssignmentDescriptionKey return the matching assignment.
     * @param key
     * @return
     */
    public Assignment getAssignment(AssignmentDescriptionKey key, LocalDate asOfDate);

    
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
    public List<Assignment> getAssignmentsByPayEntry(String principalId, CalendarEntry payCalendarEntry);
    /**
     * Get assignments for Time Calendar by calendar entry
     * @param principalId
     * @param calendarEntry
     * @return
     */
    public List<Assignment> getAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntry calendarEntry);
    /**
     * Get assignments for Leave Calendar by calendar entry
     * @param principalId
     * @param calendarEntry
     * @return
     */
    public List<Assignment> getAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntry calendarEntry);
    
    /**
	 * KPME-1129 Kagata
	 * Get a list of active assignments based on principalId and jobNumber as of a particular date 
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= Assignment.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'jobNumber=' + #p1 + '|' + 'asOfDate=' + #p2")
    public List<Assignment> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate);

    List<Assignment> searchAssignments(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                    String dept, String workArea, String active, String showHistory);
    
    
    /**
     * Get all assignment descriptions for a document
     * @param td
     * @param clockOnlyAssignments
     * @return
     */
    public Map<String,String> getAssignmentDescriptions(LeaveCalendarDocument lcd);
    
    /**
     * Get all assignment descriptions for given list of Assignments
     * @param assignments
     * @return
     */
    public Map<String, String> getAssignmentDescriptionsForAssignments(List<Assignment>  assignments);
    
    public Assignment getAssignment(LeaveCalendarDocument leaveCalendarDocument, String assignmentKey);
    
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
     * Get assignment that applies to primary job of employee
     * to be used in calculating system scheduled time off
     * @param timesheetDocument
     * @param payEndDate
     * @return
     */
	public Assignment getAssignmentToApplyScheduledTimeOff(TimesheetDocument timesheetDocument, LocalDate payEndDate);
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
}
