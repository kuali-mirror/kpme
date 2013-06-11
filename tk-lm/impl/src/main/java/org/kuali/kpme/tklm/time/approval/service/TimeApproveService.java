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
package org.kuali.kpme.tklm.time.approval.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;


public interface TimeApproveService {

    /**
     * Obtains a Map of Lists of ApprovalTimeSummaryRows. The Key to the map is
     * the PayCalendar Group name.
     *
     * @param calGroup Specify a calendar group to filter by.
     * @return A Map<String, List<ApprovalTimeSummaryRow>> container.
     */
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(String calGroup, List<String> principalIds, List<String> payCalendarLabels, CalendarEntry payCalendarEntry);

    /**
     * Used to determine if there are notes on a document
     * @param documentNumber
     * @return list of note objects
     */
	public List<Note> getNotesForDocument(String documentNumber);

    public Map<String, BigDecimal> getHoursToPayDayMap(String principalId, DateTime payEndDate, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, List<LeaveBlock> leaveBlocks, Long workArea, CalendarEntry payCalendarEntry, Calendar payCalendar, DateTimeZone dateTimeZone, List<Interval> dayIntervals);

	public Map<String, BigDecimal> getHoursToFlsaWeekMap(String principalId, DateTime payEndDate, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, List<LeaveBlock> leaveBlocks, Long workArea, CalendarEntry payCalendarEntry, Calendar payCalendar, DateTimeZone dateTimeZone, List<Interval> dayIntervals);
        
    /**
     * Method to create a map that contains the principal's id and corresponding timesheet document header.
     *
     * @param payBeginDate
     * @param payEndDate
     * @return A PrincipalId to TimesheetDocumentHeader mapping.
     */
    Map<String, TimesheetDocumentHeader> getPrincipalDocumentHeader(List<String> principalIds, DateTime payBeginDate, DateTime payEndDate);

    public DocumentRouteHeaderValue getRouteHeader(String documentId);
    
    /**
     * Get a list of unique principal ids with given criteria
     * used to populate tables in Time approval page 
     * @param workAreaList
     * @param calendarGroup
     * @param effdt
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<String> getTimePrincipalIdsWithSearchCriteria(List<String> workAreaList, String calendarGroup, LocalDate effdt, LocalDate beginDate, LocalDate endDate);
}
