package org.kuali.hr.time.approval.service;

import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.math.BigDecimal;
import java.util.*;


public interface TimeApproveService {

    /**
     * Obtains a Map of Lists of ApprovalTimeSummaryRows. The Key to the map is
     * the Calendar Group name.
     *
     * @param payBeginDate
     * @param payEndDate
     * @param calGroup Specify a calendar group to filter by.
     * @return A Map<String, List<ApprovalTimeSummaryRow>> container.
     */
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, List<String> principalIds);

//	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, List<String> principalIds);

	public List<String> getCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate);

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
    public SortedSet<String> getApproverCalendarGroups(Date payBeginDate, Date payEndDate);

    /**
     * Used to determine if there are notes on a document
     * @param documentNumber
     * @return list of note objects
     */
    @SuppressWarnings("rawtypes")
	public List getNotesForDocument(String documentNumber);

    Map<String, BigDecimal> getHoursToPayDayMap(String principalId, Date payEndDate, List<String> calendarLabels, List<TimeBlock> lstTimeBlocks, Long workArea);

    /**
     * Method to provide a mapping of CalendarGroupNames to CalendarEntries to
     * allow for various starting points in Approval Tab Navigation.
     *
     * @param currentDate The current date. This method will search for active
     * assignments for this approver active as of this date, and 31 days prior
     * to pull back CalendarEntries.
     *
     * @return A CalendarGroup Name to CalendarEntries mapping.
     */
    public Map<String,CalendarEntries> getCalendarEntriesForApprover(String principalId, Date currentDate, String dept);
    public boolean doesApproverHavePrincipalsForCalendarGroup(Date asOfDate, String calGroup);
    public Map<String,CalendarEntries> getCalendarEntriesForDept(String dept, Date currentDate);

    List<String> getUniquePayGroups();

    List<String> getPrincipalIdsByAssignment(Set<Long> workAreas, java.sql.Date payEndDate, String calGroup);

    List<String> getPrincipalIdsByAssignment(Set<Long> workAreas, java.sql.Date payEndDate, String calGroup, Integer start, Integer end);

    Map<String, TimesheetDocumentHeader> getPrincipalDocumehtHeader(List<String> principalIds, Date payBeginDate, Date payEndDate);
}