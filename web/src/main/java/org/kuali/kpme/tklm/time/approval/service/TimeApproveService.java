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
import java.util.SortedSet;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.person.TKPerson;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.springframework.cache.annotation.Cacheable;


public interface TimeApproveService {

    /**
     * Obtains a Map of Lists of ApprovalTimeSummaryRows. The Key to the map is
     * the PayCalendar Group name.
     *
     * @param payBeginDate
     * @param payEndDate
     * @param calGroup Specify a calendar group to filter by.
     * @return A Map<String, List<ApprovalTimeSummaryRow>> container.
     */
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(DateTime payBeginDate, DateTime payEndDate, String calGroup, List<TKPerson> principalIds, List<String> payCalendarLabels, CalendarEntry payCalendarEntry);
	


//	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, List<String> principalIds);

    @Cacheable(value= KPMEConstants.KPME_GLOBAL_CACHE_NAME, key="'{PayCalendarLabelsForApprovalTab}' + 'payBeginDate=' + #p0 + '|' + 'payEndDate=' + #p1")
	public List<String> getPayCalendarLabelsForApprovalTab(DateTime payBeginDate, DateTime payEndDate);

    /**
     * Method to obtain all of the active Pay Calendar Group names for the current
     * user / approver.
     * We used SortedSet here since we only want unique values while keeping the order.
     * Besides, we also need to get the first value as the default pay calendar group in some cases.
     * There is not get() method in the Set interface.
     *
     * @param payBeginDate
     * @param payEndDate
     * @return
     */
    public SortedSet<String> getApproverPayCalendarGroups(DateTime payBeginDate, DateTime payEndDate);

    /**
     * Used to determine if there are notes on a document
     * @param documentNumber
     * @return list of note objects
     */
	public List<Note> getNotesForDocument(String documentNumber);

    public Map<String, BigDecimal> getHoursToPayDayMap(String principalId, DateTime payEndDate, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, Long workArea, CalendarEntry payCalendarEntry, Calendar payCalendar, DateTimeZone dateTimeZone, List<Interval> dayIntervals);

	public Map<String, BigDecimal> getHoursToFlsaWeekMap(String principalId, DateTime payEndDate, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, Long workArea, CalendarEntry payCalendarEntry, Calendar payCalendar, DateTimeZone dateTimeZone, List<Interval> dayIntervals);
    /**
     * Method to provide a mapping of PayCalendarGroupNames to PayCalendarEntries to
     * allow for various starting points in Approval Tab Navigation.
     *
     * @param currentDate The current date. This method will search for active
     * assignments for this approver active as of this date, and 31 days prior
     * to pull back PayCalendarEntries.
     *
     * @return A CalendarGroup Name to PayCalendarEntries mapping.
     */
    public Map<String,CalendarEntry> getPayCalendarEntriesForApprover(String principalId, LocalDate currentDate, String dept);
    
    /*
     * returns all Calendar entries with TimeSheetDocument created and can be approved by given principalId
     */
    public List<CalendarEntry> getAllPayCalendarEntriesForApprover(String principalId, LocalDate currentDate);
    

    
    public boolean doesApproverHavePrincipalsForCalendarGroup(LocalDate asOfDate, String calGroup);
    public Map<String,CalendarEntry> getPayCalendarEntriesForDept(String dept, LocalDate currentDate);

    /**
     * Method to create a map that contains the principal's id and corresponding timesheet document header.
     *
     * @param payBeginDate
     * @param payEndDate
     * @return A PrincipalId to TimesheetDocumentHeader mapping.
     */
    Map<String, TimesheetDocumentHeader> getPrincipalDocumehtHeader(List<TKPerson> person, DateTime payBeginDate, DateTime payEndDate);

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