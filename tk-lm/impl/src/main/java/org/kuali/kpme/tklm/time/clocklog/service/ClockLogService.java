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
package org.kuali.kpme.tklm.time.clocklog.service;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

public interface ClockLogService {
	/**
	 * Save clock log 
	 * @param clockLog
	 */
    public ClockLog saveClockLog(ClockLog clockLog);
    /**
     * Fetch last clock log for principal id
     * @param principalId
     * @return
     */
    public ClockLog getLastClockLog(String principalId);
    
    /**
     * Fetch last clock log for principal id and clock action
     * @param principalId
     * @param clockAction
     * @return
     */
    public ClockLog getLastClockLog(String principalId, String clockAction);
    
	/**
	 * Fetch last clock log for principal id, assignment details, and calendar entry
	 * @param principalId
	 * @param jobNumber
	 * @param workArea
	 * @param task
	 * @param calendarEntry
	 * @return
	 */
	public ClockLog getLastClockLog(String principalId, String jobNumber, String workArea, String task, CalendarEntryContract calendarEntry);

	/**
	 * Process clock log created
	 * @param clockDateTime
	 * @param assignment
	 * @param pe
	 * @param ip
	 * @param asOfDate
	 * @param td
	 * @param clockAction
	 * @param principalId
	 * @return
	 */
	@CacheEvict(value={AssignmentBo.CACHE_NAME}, allEntries = true)
    ClockLog processClockLog(DateTime clockDateTime, Assignment assignment, CalendarEntryContract pe, String ip, LocalDate asOfDate, TimesheetDocument td, String clockAction, boolean runRules, String principalId);
    
    /**
     * Fetch clock log by id
     * @param tkClockLogId
     * @return
     */
    public ClockLog getClockLog(String tkClockLogId);

    @CacheEvict(value={AssignmentBo.CACHE_NAME}, allEntries = true)
    ClockLog processClockLog(DateTime clockDateTime, Assignment assignment, CalendarEntryContract pe, String ip, LocalDate asOfDate, TimesheetDocument td, String clockAction, boolean runRules, String principalId, String userPrincipalId);

    @CacheEvict(value={AssignmentBo.CACHE_NAME}, allEntries = true)
    public void deleteClockLogsForDocumentId(String documentId);
    
    /**
     * Get warning messages for clock actions taken from unapproved IP address on given timesheet document
     * @param timeBlocks
     * @return List<String>
     */
    public List<String> getUnapprovedIPWarning(List<TimeBlock> timeBlocks);
    
    /** 
     * Build an unapproved IP address warning message for  based on given ClockLog 
     * @param cl
     * @return
     */
    public String buildUnapprovedIPWarning(ClockLog cl);
}
