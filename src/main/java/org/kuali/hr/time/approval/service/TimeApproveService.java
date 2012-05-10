package org.kuali.hr.time.approval.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;

import com.google.common.collect.Multimap;


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
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, List<TKPerson> principalIds, List<String> payCalendarLabels, PayCalendarEntries payCalendarEntries);

//	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, List<String> principalIds);

	public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate);

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
    public SortedSet<String> getApproverPayCalendarGroups(Date payBeginDate, Date payEndDate);

    /**
     * Used to determine if there are notes on a document
     * @param documentNumber
     * @return list of note objects
     */
    @SuppressWarnings("rawtypes")
	public List getNotesForDocument(String documentNumber);

    Map<String, BigDecimal> getHoursToPayDayMap(String principalId, Date payEndDate, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, Long workArea, PayCalendarEntries payCalendarEntries, PayCalendar payCalendar, DateTimeZone dateTimeZone, List<Interval> dayIntervals);

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
    public Map<String,PayCalendarEntries> getPayCalendarEntriesForApprover(String principalId, Date currentDate, String dept);
    
    public List<PayCalendarEntries> getAllPayCalendarEntriesForApprover(String principalId, Date currentDate);
    
    public boolean doesApproverHavePrincipalsForCalendarGroup(Date asOfDate, String calGroup);
    public Map<String,PayCalendarEntries> getPayCalendarEntriesForDept(String dept, Date currentDate);

    /**
     * Get a list of unique pay groups
     * @param approverWorkAreas
     * @return
     */
    List<String> getUniquePayGroups();

    /**
     * Method to get a list of principal ids based on the department work areas.
     *
     * @param department
     * @param workArea
     * @param payEndDate
     * @param calGroup
     * @return A list of the PrincipalIds
     */
    List<String> getPrincipalIdsByDeptWorkAreaRolename(String roleName, String department, String WorkArea, java.sql.Date payBeginDate, java.sql.Date payEndDate, String calGroup);
    
    /**
     * Method to create a map that contains the principal's id and corresponding timesheet document header.
     *
     * @param principalIds
     * @param payBeginDate
     * @param payEndDate
     * @return A PrincipalId to TimesheetDocumentHeader mapping.
     */
    Map<String, TimesheetDocumentHeader> getPrincipalDocumehtHeader(List<TKPerson> person, Date payBeginDate, Date payEndDate);

    /**
     * Method to create a map of the depts and their associated work areas based on the given approver work areas.
     *
     * @param approverWorkAres
     * @return A Dept and Work Areas mapping.
     */
    Multimap<String, Long> getDeptWorkAreasByWorkAreas(Set<Long> approverWorkAres);

    /**
     * Method to create a map of the depts and their associated work areas based on the given depts.
     *
     * @param userDepts
     * @return A Dept and Work Areas mapping.
     */
    Multimap<String, Long> getDeptWorkAreasByDepts(Set<String> userDepts);
    
    public DocumentRouteHeaderValue getRouteHeader(String documentId);
}