package org.kuali.hr.time.approval.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.math.BigDecimal;
import java.util.*;

public class TimeApproveServiceImpl implements TimeApproveService {


    public SortedSet<String> getApproverPayCalendarGroups(Date payBeginDate, Date payEndDate) {
        SortedSet<String> pcg = new TreeSet<String>();

        TKUser tkUser = TKContext.getUser();
        Set<Long> approverWorkAreas = tkUser.getCurrentRoles().getApproverWorkAreas();
        List<Assignment> assignments = new ArrayList<Assignment>();

        for (Long workArea : approverWorkAreas) {
            if (workArea != null) {
                assignments.addAll(TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, new java.sql.Date(payBeginDate.getTime())));
            }
        }
        if (!assignments.isEmpty()) {
            for (Assignment assign : assignments) {
                String principalId = assign.getPrincipalId();
                TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
                if (tdh == null) {
                    throw new RuntimeException("Timesheet document header shouldn't be null");
                }
                String calendarGroup = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, payBeginDate).getCalendarGroup();
                pcg.add(calendarGroup);
            }
        }

        // Handle the situation where pay calendar is null. This shouldn't happen but just in case.
        if (pcg.size() == 0) {
            throw new RuntimeException("Pay calendar group is null");
        }

        return pcg;
    }

    @Override
    public Map<String, List<ApprovalTimeSummaryRow>> getApprovalSummaryRowsMap(Date payBeginDate, Date payEndDate, String calGroup, Long workarea) {
        Map<String, List<ApprovalTimeSummaryRow>> mappedRows = new HashMap<String, List<ApprovalTimeSummaryRow>>();

        TKUser tkUser = TKContext.getUser();
        Set<Long> approverWorkAreas = tkUser.getCurrentRoles().getApproverWorkAreas();
        List<Assignment> lstEmployees = new ArrayList<Assignment>();

        for (Long aWorkArea : approverWorkAreas) {
            // if the workarea is not null, it means we only want the active assignments under the specified workarea
            if (workarea != null && aWorkArea.compareTo(workarea) == 0) {
                lstEmployees.addAll(TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(aWorkArea, new java.sql.Date(payBeginDate.getTime())));
                break;
            }
            // otherwise we will get all the active assigments that the person has an approval role associated with
            else {
                lstEmployees.addAll(TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(aWorkArea, new java.sql.Date(payBeginDate.getTime())));
            }
        }
        if (!lstEmployees.isEmpty()) {
            List<String> userIds = new ArrayList<String>();
            List<String> clockUsers = new ArrayList<String>();
            Map<String, Set<String>> employeeWorkAreas = new LinkedHashMap<String, Set<String>>();
            List<ApprovalTimeSummaryRow> rows = new LinkedList<ApprovalTimeSummaryRow>();

            for (Assignment assign : lstEmployees) {

                if (!userIds.contains(assign.getPrincipalId())) {
                    userIds.add(assign.getPrincipalId());
                    Set<String> workAreas = new HashSet<String>();
                    workAreas.add(assign.getWorkArea().toString());
                    employeeWorkAreas.put(assign.getPrincipalId(), workAreas);

                    // TODO: What are we storing this clockUsers list for?
                    if (assign.getTimeCollectionRule().isClockUserFl()) {
                        clockUsers.add(assign.getPrincipalId());
                    }
                }
                else {
                    employeeWorkAreas.get(assign.getPrincipalId()).add(assign.getWorkArea().toString());
                }
            }
            for (String principalId : userIds) {
                TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
                if (tdh != null) {
                    String documentId = tdh.getDocumentId();
                    Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
                    List<TimeBlock> lstTimeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(documentId));
                    Map<String, BigDecimal> hoursToPayLabelMap = getHoursToPayDayMap(principalId, payBeginDate, getPayCalendarLabelsForApprovalTab(payBeginDate, payEndDate), lstTimeBlocks, workarea);
                    List notes = this.getNotesForDocument(documentId);
                    List<String> warnings = TkServiceLocator.getWarningService().getWarnings(documentId);
                    String[] workAreas = employeeWorkAreas.get(principalId).toArray(new String[employeeWorkAreas.get(principalId).size()]);

                    ApprovalTimeSummaryRow approvalSummaryRow = new ApprovalTimeSummaryRow();
                    approvalSummaryRow.setName(person.getName());
                    approvalSummaryRow.setPayCalendarGroup(calGroup);
                    approvalSummaryRow.setDocumentId(documentId);
                    approvalSummaryRow.setLstTimeBlocks(lstTimeBlocks);
                    approvalSummaryRow.setApprovalStatus(tdh.getDocumentStatus());
                    approvalSummaryRow.setHoursToPayLabelMap(hoursToPayLabelMap);
                    approvalSummaryRow.setClockStatusMessage(createLabelForLastClockLog(principalId));
                    approvalSummaryRow.setNotes(notes);
                    approvalSummaryRow.setWarnings(warnings);
                    approvalSummaryRow.setWorkAreas(workAreas);
                    rows.add(approvalSummaryRow);

                    mappedRows.put(calGroup, rows);
                }
            }
        }

        return mappedRows;
    }


    /*
     * Right now this code is just calling our "Big" data retriever and only returning
     * a subset of that data. It is obvious that some optimization should be done here,
     * for now this is a "future" TODO, to get this going.
     */
    public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, Long workArea) {
        List<ApprovalTimeSummaryRow> rows = new ArrayList<ApprovalTimeSummaryRow>();

        Map<String, List<ApprovalTimeSummaryRow>> mrows = this.getApprovalSummaryRowsMap(payBeginDate, payEndDate, calGroup, workArea);
        rows = mrows.get(calGroup);

        return rows;
    }

    /**
     * Get pay calendar labels for approval tab
     */
    @CacheResult
    public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate) {
        List<String> lstPayCalendarLabels = new ArrayList<String>();
        DateTime payBegin = new DateTime(payBeginDate.getTime());
        DateTime payEnd = new DateTime(payEndDate.getTime());
        DateTime currTime = payBegin;
        int dayCounter = 1;
        int weekCounter = 1;

        while (currTime.isBefore(payEnd)) {
            String labelForDay = createLabelForDay(currTime);
            lstPayCalendarLabels.add(labelForDay);
            currTime = currTime.plusDays(1);
            if ((dayCounter % 7) == 0) {
                lstPayCalendarLabels.add("Week " + weekCounter);
                weekCounter++;
            }
            dayCounter++;
        }
        lstPayCalendarLabels.add("Total Hours");
        return lstPayCalendarLabels;
    }

    /**
     * Create label for a given pay calendar day
     *
     * @param fromDate
     * @return
     */
    private String createLabelForDay(DateTime fromDate) {
        DateMidnight dateMidnight = new DateMidnight(fromDate);
        if (dateMidnight.compareTo(fromDate) == 0) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM/dd");
            return fmt.print(fromDate);
        }
        DateTime toDate = fromDate.plusDays(1);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM/dd k:m:s");
        return fmt.print(fromDate) + "-" + fmt.print(toDate);
    }

    /**
     * Create label for the last clock log
     *
     * @param principalId
     * @return
     */
    private String createLabelForLastClockLog(String principalId) {
        ClockLog cl = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
        if (cl == null) {
            return "No previous clock information";
        }
        if (StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_IN)) {
            return "Clocked in since: " + cl.getClockTimestamp();
        } else if (StringUtils.equals(cl.getClockAction(), TkConstants.LUNCH_IN)) {
            return "At Lunch since: " + cl.getClockTimestamp();
        } else if (StringUtils.equals(cl.getClockAction(), TkConstants.LUNCH_OUT)) {
            return "Returned from Lunch : " + cl.getClockTimestamp();
        } else if (StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_OUT)) {
            return "Clocked out since: " + cl.getClockTimestamp();
        } else {
            return "No previous clock information";
        }

    }

    /**
     * Aggregate TimeBlocks to hours per day and sum for week
     *
     * @param principalId
     * @param beginDateTime
     * @param payCalendarLabels
     * @param lstTimeBlocks
     * @param workArea
     * @return
     */
    public Map<String, BigDecimal> getHoursToPayDayMap(String principalId, Date beginDateTime, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, Long workArea) {
        Map<String, BigDecimal> hoursToPayLabelMap = new HashMap<String, BigDecimal>();
        List<BigDecimal> dayTotals = new ArrayList<BigDecimal>();
        PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(principalId, beginDateTime);
        TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(lstTimeBlocks, payCalendarEntry);
        List<List<TimeBlock>> lstOfLstOfTimeBlocksPerDay = tkTimeBlockAggregate.getDayTimeBlockList();
        for (List<TimeBlock> lstTimeBlocksForDay : lstOfLstOfTimeBlocksPerDay) {
            BigDecimal total = new BigDecimal(0.00);
            for (TimeBlock tb : lstTimeBlocksForDay) {
                if (workArea != null) {
                    if (tb.getHours() != null && tb.getWorkArea().compareTo(workArea) == 0) {
                        total = total.add(tb.getHours(), TkConstants.MATH_CONTEXT);
                    }
                } else {
                    total = total.add(tb.getHours(), TkConstants.MATH_CONTEXT);
                }
            }
            dayTotals.add(total);
        }
        int dayCount = 0;
        BigDecimal weekTotal = new BigDecimal(0.00);
        BigDecimal periodTotal = new BigDecimal(0.00);
        for (String payCalendarLabel : payCalendarLabels) {
            if (StringUtils.contains(payCalendarLabel, "Week")) {
                hoursToPayLabelMap.put(payCalendarLabel, weekTotal);
                weekTotal = new BigDecimal(0.00);
            } else if (StringUtils.contains(payCalendarLabel, "Total Hours")) {
                hoursToPayLabelMap.put(payCalendarLabel, periodTotal);
            } else {
                hoursToPayLabelMap.put(payCalendarLabel, dayTotals.get(dayCount));
                weekTotal = weekTotal.add(dayTotals.get(dayCount), TkConstants.MATH_CONTEXT);
                periodTotal = periodTotal.add(dayTotals.get(dayCount));
                dayCount++;

            }

        }
        return hoursToPayLabelMap;
    }

    @SuppressWarnings("rawtypes")
    public List getNotesForDocument(String documentNumber) {
        List notes = KEWServiceLocator.getNoteService().getNotesByRouteHeaderId(Long.parseLong(documentNumber));
        return notes;
    }
}
