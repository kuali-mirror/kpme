package org.kuali.hr.time.approval.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.*;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeApproveServiceImpl implements TimeApproveService {

    private static final Logger LOG = Logger.getLogger(TimeApproveServiceImpl.class);

    public static final int DAYS_WINDOW_DELTA = 31;


    public Map<String, PayCalendarEntries> getPayCalendarEntriesForDept(String dept, Date currentDate) {
        DateTime minDt = new DateTime(currentDate, TkConstants.SYSTEM_DATE_TIME_ZONE);
        minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
        java.sql.Date windowDate = TKUtils.getTimelessDate(minDt.toDate());

        Map<String, PayCalendarEntries> pceMap = new HashMap<String, PayCalendarEntries>();
        Set<String> principals = new HashSet<String>();
        List<WorkArea> workAreasForDept = TkServiceLocator.getWorkAreaService().getWorkAreas(dept, new java.sql.Date(currentDate.getTime()));
        // Get all of the principals within our window of time.
        for (WorkArea workArea : workAreasForDept) {
            Long waNum = workArea.getWorkArea();
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, TKUtils.getTimelessDate(currentDate));

            if (assignments != null) {
                for (Assignment assignment : assignments) {
                    principals.add(assignment.getPrincipalId());
                }
            } else {
                assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, windowDate);
                if (assignments != null) {
                    for (Assignment assignment : assignments) {
                        principals.add(assignment.getPrincipalId());
                    }
                }
            }
        }

        // Get the pay calendars
        Set<PayCalendar> payCals = new HashSet<PayCalendar>();
        for (String pid : principals) {
            PrincipalCalendar pc = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(pid, currentDate);
            if (pc == null)
                pc = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(pid, windowDate);

            if (pc != null) {
                payCals.add(pc.getPayCalendar());
            } else {
                LOG.warn("PrincipalCalendar null for principal: '" + pid + "'");
            }
        }

        // Grab the pay calendar entries + groups
        for (PayCalendar pc : payCals) {
            PayCalendarEntries pce = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(pc.getHrPyCalendarId(), currentDate);
            pceMap.put(pc.getPyCalendarGroup(), pce);
        }

        return pceMap;
    }

    @Override
    public Map<String, PayCalendarEntries> getPayCalendarEntriesForApprover(String principalId, Date currentDate, String dept) {
        TKUser tkUser = TKContext.getUser();

        Map<String, PayCalendarEntries> pceMap = new HashMap<String, PayCalendarEntries>();
        Set<String> principals = new HashSet<String>();
        DateTime minDt = new DateTime(currentDate, TkConstants.SYSTEM_DATE_TIME_ZONE);
        minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
        java.sql.Date windowDate = TKUtils.getTimelessDate(minDt.toDate());
        Set<Long> approverWorkAreas = tkUser.getCurrentRoles().getApproverWorkAreas();

        // Get all of the principals within our window of time.
        for (Long waNum : approverWorkAreas) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, TKUtils.getTimelessDate(currentDate));

            if (assignments != null) {
                for (Assignment assignment : assignments) {
                    principals.add(assignment.getPrincipalId());
                }
            }
//            else {
//                assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, windowDate);
//                if (assignments != null) {
//                    for (Assignment assignment : assignments) {
//                        principals.add(assignment.getPrincipalId());
//                    }
//                }
//            }
        }

        // Get the pay calendars
        Set<PayCalendar> payCals = new HashSet<PayCalendar>();
        for (String pid : principals) {
            PrincipalCalendar pc = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(pid, currentDate);
            if (pc == null)
                pc = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(pid, windowDate);

            if (pc != null) {
                payCals.add(pc.getPayCalendar());
            } else {
                LOG.warn("PrincipalCalendar null for principal: '" + pid + "'");
            }
        }

        // Grab the pay calendar entries + groups
        for (PayCalendar pc : payCals) {
            PayCalendarEntries pce = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(pc.getHrPyCalendarId(), currentDate);
            pceMap.put(pc.getPyCalendarGroup(), pce);
        }

        return pceMap;
    }

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
                if (tdh != null) {
                    String pyCalendarGroup = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, payBeginDate).getPyCalendarGroup();
                    pcg.add(pyCalendarGroup);
                }
            }
        }

        // Handle the situation where pay calendar is null. This shouldn't happen but just in case.
//        if (pcg.size() == 0) {
//            throw new RuntimeException("Pay calendar group is null");
//        }

        return pcg;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, List<String> principalIds) {
        List<ApprovalTimeSummaryRow> rows = new LinkedList<ApprovalTimeSummaryRow>();
        Map<String, TimesheetDocumentHeader> principalDocumentHeader = getPrincipalDocumehtHeader(principalIds, payBeginDate, payEndDate);

        for (String principalId : principalIds) {
            TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
            String documentId = "";
            if (principalDocumentHeader.containsKey(principalId)) {
                tdh = principalDocumentHeader.get(principalId);
                documentId = principalDocumentHeader.get(principalId).getDocumentId();
            }
            List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
            List notes = new ArrayList();
            List<String> warnings = new ArrayList<String>();

//            TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
            if (StringUtils.isNotBlank(documentId)) {
                timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(documentId));
                notes = this.getNotesForDocument(documentId);
                warnings = TkServiceLocator.getWarningService().getWarnings(documentId);
            }

            Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
            PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getPayCalendarDatesByPayEndDate(principalId, TKUtils.getTimelessDate(payEndDate));

            List<String> pyCalendarLabels = TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntry, new ArrayList<Boolean>());

            Map<String, BigDecimal> hoursToPayLabelMap = getHoursToPayDayMap(principalId, payEndDate, pyCalendarLabels, timeBlocks, null);

            ApprovalTimeSummaryRow approvalSummaryRow = new ApprovalTimeSummaryRow();
            approvalSummaryRow.setName(person.getName());
            approvalSummaryRow.setPrincipalId(person.getPrincipalId());
            approvalSummaryRow.setPayCalendarGroup(calGroup);
            approvalSummaryRow.setDocumentId(documentId);
            approvalSummaryRow.setLstTimeBlocks(timeBlocks);
            if (principalDocumentHeader.containsKey(principalId)) {
                approvalSummaryRow.setApprovalStatus(TkConstants.DOC_ROUTE_STATUS.get(tdh.getDocumentStatus()));
                TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdh.getDocumentId());
                TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(td);
                approvalSummaryRow.setTimeSummary(ts);
            }
            approvalSummaryRow.setHoursToPayLabelMap(hoursToPayLabelMap);
            approvalSummaryRow.setPeriodTotal(hoursToPayLabelMap.get("Period Total"));
            approvalSummaryRow.setNotes(notes);
            approvalSummaryRow.setWarnings(warnings);

            // Compare last clock log versus now and if > threshold
            // highlight entry
            ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
            approvalSummaryRow.setClockStatusMessage(createLabelForLastClockLog(lastClockLog));
            if (lastClockLog != null &&
                    (StringUtils.equals(lastClockLog.getClockAction(), TkConstants.CLOCK_IN) || StringUtils.equals(lastClockLog.getClockAction(), TkConstants.LUNCH_IN))) {
                DateTime startTime = new DateTime(lastClockLog.getClockTimestamp().getTime());
                DateTime endTime = new DateTime(System.currentTimeMillis());

                Hours hour = Hours.hoursBetween(startTime, endTime);
                if (hour != null) {
                    int elapsedHours = hour.getHours();
                    if (elapsedHours >= TkConstants.NUMBER_OF_HOURS_CLOCKED_IN_APPROVE_TAB_HIGHLIGHT) {
                        approvalSummaryRow.setClockedInOverThreshold(true);
                    }
                }

            }
            rows.add(approvalSummaryRow);
        }

        return rows;
    }

    public List<TimesheetDocumentHeader> getDocumentHeadersByPrincipalIds(Date payBeginDate, Date payEndDate, List<String> principalIds) {
        List<TimesheetDocumentHeader> headers = new LinkedList<TimesheetDocumentHeader>();
        for (String principalId : principalIds) {
            TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
            if (tdh != null) {
                headers.add(tdh);
            }
        }

        return headers;
    }

    /**
     * Get pay calendar labels for approval tab
     */
    @CacheResult
    public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate) {
        // :) http://stackoverflow.com/questions/111933/why-shouldnt-i-use-hungarian-notation
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
     * @param cl
     * @return
     */
    private String createLabelForLastClockLog(ClockLog cl) {
//    	return sdf.format(dt);
        if (cl == null) {
            return "No previous clock information";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String dateTime = sdf.format(new java.sql.Date(cl.getClockTimestamp().getTime()));
        if (StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_IN)) {
            return "Clocked in since: " + dateTime;
        } else if (StringUtils.equals(cl.getClockAction(), TkConstants.LUNCH_IN)) {
            return "At Lunch since: " + dateTime;
        } else if (StringUtils.equals(cl.getClockAction(), TkConstants.LUNCH_OUT)) {
            return "Returned from Lunch : " + dateTime;
        } else if (StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_OUT)) {
            return "Clocked out since: " + dateTime;
        } else {
            return "No previous clock information";
        }

    }

    public List<Map<String, Map<String, BigDecimal>>> getHoursByDayAssignmentBuckets(TkTimeBlockAggregate aggregate, List<Assignment> approverAssignments, List<String> payCalendarLabels) {
        Map<String, Assignment> mappedAssignments = mapAssignmentsByAssignmentKey(approverAssignments);
        List<List<TimeBlock>> blocksByDay = aggregate.getDayTimeBlockList();

        // (assignment_key, <List of Hours Summed by Day>)
        Map<String, List<BigDecimal>> approverHours = new HashMap<String, List<BigDecimal>>();
        Map<String, List<BigDecimal>> otherHours = new HashMap<String, List<BigDecimal>>();
        for (int day = 0; day < blocksByDay.size(); day++) {
            List<TimeBlock> dayBlocks = blocksByDay.get(day);
            for (TimeBlock block : dayBlocks) {
                List<BigDecimal> hours;
                // Approver vs. Other :: Set our day-hour-list object
                if (mappedAssignments.containsKey(block.getAssignmentKey())) {
                    hours = approverHours.get(block.getAssignmentKey());
                    if (hours == null) {
                        hours = new ArrayList<BigDecimal>();
                        approverHours.put(block.getAssignmentKey(), hours);
                    }
                } else {
                    hours = otherHours.get(block.getAssignmentKey());
                    if (hours == null) {
                        hours = new ArrayList<BigDecimal>();
                        otherHours.put(block.getAssignmentKey(), hours);
                    }
                }

                // Fill in zeroes for days with 0 hours / no time blocks
                for (int fill = hours.size(); fill <= day; fill++) {
                    hours.add(TkConstants.BIG_DECIMAL_SCALED_ZERO);
                }

                // Add time from time block to existing time.
                BigDecimal timeToAdd = hours.get(day);
                timeToAdd = timeToAdd.add(block.getHours(), TkConstants.MATH_CONTEXT);
                hours.set(day, timeToAdd);
            }
        }

        // Compute Weekly / Period Summary Totals for each Assignment.
        // assignment row, each row has a map of pay calendar label -> big decimal totals.
        Map<String, Map<String, BigDecimal>> approverAssignToPayHourTotals = new HashMap<String, Map<String, BigDecimal>>();
        Map<String, Map<String, BigDecimal>> otherAssignToPayHourTotals = new HashMap<String, Map<String, BigDecimal>>();

        // Pass by Reference
        generateSummaries(approverAssignToPayHourTotals, approverHours, payCalendarLabels);
        generateSummaries(otherAssignToPayHourTotals, otherHours, payCalendarLabels);

        // Add to our return list, "virtual" tuple.
        List<Map<String, Map<String, BigDecimal>>> returnTuple = new ArrayList<Map<String, Map<String, BigDecimal>>>(2);
        returnTuple.add(approverAssignToPayHourTotals);
        returnTuple.add(otherAssignToPayHourTotals);

        return returnTuple;
    }

    // Helper method for above method.
    private void generateSummaries(Map<String, Map<String, BigDecimal>> payHourTotals, Map<String, List<BigDecimal>> assignmentToHours, List<String> payCalendarLabels) {
        for (String key : assignmentToHours.keySet()) {
            // for every Assignment
            Map<String, BigDecimal> hoursToPayLabelMap = new LinkedHashMap<String, BigDecimal>();
            List<BigDecimal> dayTotals = assignmentToHours.get(key);
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
                    BigDecimal dayTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
                    if (dayCount < dayTotals.size())
                        dayTotal = dayTotals.get(dayCount);

                    hoursToPayLabelMap.put(payCalendarLabel, dayTotal);
                    weekTotal = weekTotal.add(dayTotal, TkConstants.MATH_CONTEXT);
                    periodTotal = periodTotal.add(dayTotal);
                    dayCount++;
                }
            }
            payHourTotals.put(key, hoursToPayLabelMap);
        }
    }

    private Map<String, Assignment> mapAssignmentsByAssignmentKey(List<Assignment> assignments) {
        Map<String, Assignment> assignmentMap = new HashMap<String, Assignment>();
        for (Assignment assignment : assignments) {
            assignmentMap.put(AssignmentDescriptionKey.getAssignmentKeyString(assignment), assignment);
        }
        return assignmentMap;
    }

    /**
     * Aggregate TimeBlocks to hours per day and sum for week
     *
     * @param principalId
     * @param payEndDate
     * @param payCalendarLabels
     * @param lstTimeBlocks
     * @param workArea
     * @return
     */
    @Override
    public Map<String, BigDecimal> getHoursToPayDayMap(String principalId, Date payEndDate, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks, Long workArea) {
        Map<String, BigDecimal> hoursToPayLabelMap = new LinkedHashMap<String, BigDecimal>();
        List<BigDecimal> dayTotals = new ArrayList<BigDecimal>();
        PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getPayCalendarDatesByPayEndDate(principalId, payEndDate);
        // TODO: we should just pass in the timeblocks instead of calling TkTimeBlockAggregate twice..
        TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(lstTimeBlocks, payCalendarEntry, TkServiceLocator.getPayCalendarSerivce().getPayCalendar(payCalendarEntry.getHrPyCalendarId()), true);
        for (FlsaWeek week : tkTimeBlockAggregate.getFlsaWeeks(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback())) {
            for (FlsaDay day : week.getFlsaDays()) {
                BigDecimal total = new BigDecimal(0.00);
                for (TimeBlock tb : day.getAppliedTimeBlocks()) {
                    if (workArea != null) {
                        if (tb.getWorkArea().compareTo(workArea) == 0) {
                            total = total.add(tb.getHours(), TkConstants.MATH_CONTEXT);
                        } else {
                            total = total.add(new BigDecimal("0"), TkConstants.MATH_CONTEXT);
                        }
                    } else {
                        total = total.add(tb.getHours(), TkConstants.MATH_CONTEXT);
                    }
                }
                dayTotals.add(total);
            }
        }

        int dayCount = 0;
        BigDecimal weekTotal = new BigDecimal(0.00);
        BigDecimal periodTotal = new BigDecimal(0.00);
        for (String payCalendarLabel : payCalendarLabels) {
            if (StringUtils.contains(payCalendarLabel, "Week")) {
                hoursToPayLabelMap.put(payCalendarLabel, weekTotal);
                weekTotal = new BigDecimal(0.00);
            } else if (StringUtils.contains(payCalendarLabel, "Period Total")) {
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

    public boolean doesApproverHavePrincipalsForCalendarGroup(Date asOfDate, String calGroup) {
        TKUser tkUser = TKContext.getUser();
        Set<Long> approverWorkAreas = tkUser.getCurrentRoles().getApproverWorkAreas();
        for (Long workArea : approverWorkAreas) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, new java.sql.Date(asOfDate.getTime()));
            List<String> principalIds = new ArrayList<String>();
            for (Assignment assign : assignments) {
                if (principalIds.contains(assign.getPrincipalId())) {
                    continue;
                }
                principalIds.add(assign.getPrincipalId());
            }

            for (String principalId : principalIds) {
                PrincipalCalendar principalCal = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, asOfDate);
                if (StringUtils.equals(principalCal.getPyCalendarGroup(), calGroup)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public List getNotesForDocument(String documentNumber) {
        List notes = KEWServiceLocator.getNoteService().getNotesByRouteHeaderId(Long.parseLong(documentNumber));
        return notes;
    }

//    private static final String UNIQUE_PY_GROUP_SQL = "select distinct py_calendar_group from hr_py_calendar_t where active = 'Y'";
    private static final String UNIQUE_PY_GROUP_SQL = "SELECT DISTINCT P.py_calendar_group FROM tk_work_area_t W, hr_principal_calendar_t P WHERE P.active = 'Y' AND ";

    @Override
    public List<String> getUniquePayGroups(Set<Long> approverWorkAreas) {
        StringBuilder workAreas = new StringBuilder();
        for (long workarea : approverWorkAreas) {
            workAreas.append("W.work_area = " + workarea + " or ");
        }
        String workAreasForQuery = workAreas.substring(0, workAreas.length() - 3);
        String sql = UNIQUE_PY_GROUP_SQL + workAreasForQuery;
        SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(sql);
        List<String> pyGroups = new LinkedList<String>();
        while (rs.next()) {
            pyGroups.add(rs.getString("py_calendar_group"));
        }

        return pyGroups;
    }

//    public List<String> getPrincipalIdsByAssignment(Set<Long> workAreas, java.sql.Date payEndDate, String calGroup, Integer start, Integer end) {
//        Set<String> list = getPrincipalIdsByWorkAreas(workAreas, payEndDate, calGroup);
//        return list.subList(start, end);
//    }

    @Override
    public Set<String> getPrincipalIdsByWorkAreas(Set<Long> workAreas, java.sql.Date payBeginDate, java.sql.Date payEndDate, String calGroup) {
        //        List<Assignment> activeAssignments = new ArrayList<Assignment>(); //getActiveAssignmentsAndPrincipalCalendars(workAreas, payEndDate);
//        List<Assignment> activeAssignments = getActiveAssignmentsAndPrincipalCalendars(workAreas, payEndDate);

        Set<String> principalIds = getPrincipalIdsWithActiveAssignmentsForCalendarGroup(workAreas, calGroup, payEndDate, payBeginDate, payEndDate);
//        getActiveAssignmentsForCalendarGroup(workAreas, payEndDate, payBeginDate, payEndDate);
//        for (Long aWorkArea : workAreas) {
//            activeAssignments.addAll(TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(aWorkArea, new java.sql.Date(payEndDate.getTime())));
//        }
//        if (!activeAssignments.isEmpty()) {
//            for (Assignment assign : activeAssignments) {
//                PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(assign.getPrincipalId(), payEndDate);
//                //TODO remove this comparision sometiem
//                if (StringUtils.equals(principalCalendar.getPyCalendarGroup(), calGroup)) {
//                    principalIds.add(assign.getPrincipalId());
//                }
//            }
//        }

        return principalIds;
    }

    private static final String GET_PRINCIPAL_ID_SQL_PRE = "SELECT DISTINCT P.principal_id FROM hr_principal_calendar_t P, tk_assignment_t T WHERE P.py_calendar_group = ? AND P.principal_id in (" +
            "SELECT DISTINCT `A0`.`principal_id` " +
            "FROM `tk_assignment_t` `A0` " +
            "WHERE (((`A0`.`effdt` = " +
            "(SELECT max(`effdt`) " +
            "FROM `tk_assignment_t` `B0` " +
            "WHERE ((((`B0`.`job_number` = `A0`.`job_number`) AND `B0`.`work_area` = `A0`.`work_area`) AND `B0`.`task` = `A0`.`task`)) AND `B0`.`principal_id` = `A0`.`principal_id`)) AND `A0`.`timestamp` = " +
            "(SELECT max(`B0`.`timestamp`) " +
            "FROM `tk_assignment_t` `B0` " +
            "WHERE " +
            "((((`B0`.`job_number` = `A0`.`job_number`) AND `B0`.`work_area` = `A0`.`work_area`) AND `B0`.`task` = `A0`.`task`) AND `B0`.`effdt` = `A0`.`effdt`) AND `B0`.`principal_id` = `A0`.`principal_id`))) AND ( ";

    private static final String GET_PRINCIPAL_ID_SQL_POST =
            " ) AND `A0`.`effdt` <= ? AND `A0`.`active` = 'Y' OR " +
            "(`A0`.`active` = 'N' AND (`A0`.`effdt` >= ? AND `A0`.`effdt` <= ?)"+
             " AND (select exists(select C0.principal_id from tk_assignment_t C0 where C0.principal_id = A0.principal_id and C0.job_number = A0.job_number "+
             " AND C0.work_area = A0.work_area and C0.task = A0.task and C0.effdt <= ? and C0.active = 'Y'))))";

    private Set<String> getPrincipalIdsWithActiveAssignmentsForCalendarGroup(Set<Long> approverWorkAreas, String payCalendarGroup, java.sql.Date effdt, java.sql.Date beginDate, java.sql.Date endDate) {
        if (approverWorkAreas.size() == 0) {
            return new LinkedHashSet<String>();
        }

        Set<String> principalIds = new LinkedHashSet<String>();
        StringBuilder workAreas = new StringBuilder();
        for (long workarea : approverWorkAreas) {
            workAreas.append("A0.work_area = " + workarea + " or ");
        }
        String workAreasForQuery = workAreas.substring(0, workAreas.length() - 3);
        String sql = GET_PRINCIPAL_ID_SQL_PRE + workAreasForQuery + GET_PRINCIPAL_ID_SQL_POST;

        SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(sql, new Object[]{payCalendarGroup, effdt, beginDate, endDate, endDate});
        while(rs.next()) {
            principalIds.add(rs.getString("principal_id"));
        }

        return principalIds;
    }

    @Override
    public Map<String, TimesheetDocumentHeader> getPrincipalDocumehtHeader(List<String> principalIds, Date payBeginDate, Date payEndDate) {

        if (principalIds.size() == 0) {
            return new LinkedHashMap<String, TimesheetDocumentHeader>();
        }


        StringBuilder ids = new StringBuilder();
        for (String principalId : principalIds) {
            ids.append("principal_id = '" + principalId + "' or ");
        }
        String idsForQuery = ids.substring(0, ids.length() - 4);
        String sql = "SELECT document_id, principal_id, document_status " +
                "FROM tk_document_header_t " +
                "WHERE " + idsForQuery + " AND pay_begin_dt >= ? AND pay_end_dt <= ?";
        Map<String, TimesheetDocumentHeader> principalDocumentHeader = new LinkedHashMap<String, TimesheetDocumentHeader>();
        SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(sql,
                new Object[]{payBeginDate, payEndDate}, new int[]{Types.DATE, Types.DATE});
        while (rs.next()) {
            TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
            tdh.setPrincipalId(rs.getString("principal_id"));
            String docId = StringUtils.isBlank(rs.getString("document_id")) ? "" : rs.getString("document_id");
            tdh.setDocumentId(docId);
            tdh.setDocumentStatus(rs.getString("document_status"));

            principalDocumentHeader.put(rs.getString("principal_id"), tdh);
        }

        return principalDocumentHeader;
    }

    @Override
    public Multimap<String, Long> getDeptWorkAreasByWorkAreas(Set<Long> approverWorkAreas) {
        Multimap<String, Long> deptWorkAreas = HashMultimap.create();

        if (approverWorkAreas.size() > 0) {
            // prepare the OR statement for query
            StringBuilder workAreas = new StringBuilder();
            for (long workarea : approverWorkAreas) {
                workAreas.append("work_area = " + workarea + " or ");
            }
            String workAreasForQuery = workAreas.substring(0, workAreas.length() - 3);
            String sql = "SELECT DISTINCT work_area, dept FROM tk_work_area_t " +
                    "WHERE " + workAreasForQuery + " AND effdt <= ?";

            /**
             * Multimap is an interface from Google's java common library - Guava.
             * HashMultimap allows us to create a map with duplicate keys which will then generate a data structure, i.e.
             * [key] => [value1, value2, value3...]
             *
             * It save a good lines of code to do the same thing through the java map, e.g.
             * Map<String, List<String>> map = new Hashmap<String, List<String>>();
             *
             * See the java doc for more information: http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/Multimap.html
             */
            SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(sql, new Object[]{TKUtils.getCurrentDate()}, new int[]{Types.DATE});
            while (rs.next()) {
                deptWorkAreas.put(rs.getString("dept"), rs.getLong("work_area"));
            }
        }
        return deptWorkAreas;
    }

    @Override
    public Multimap<String, Long> getDeptWorkAreasByDepts(Set<String> userDepts) {
        Multimap<String, Long> deptWorkAreas = HashMultimap.create();

        if (userDepts.size() > 0) {
            // prepare the OR statement for query
            StringBuilder depts = new StringBuilder();
            for (String dept : userDepts) {
                depts.append("dept = '" + dept + "' or ");
            }
            String deptsForQuery = depts.substring(0, depts.length() - 4);
            String sql = "SELECT DISTINCT work_area, dept FROM tk_work_area_t " +
                    "WHERE " + deptsForQuery + " AND effdt <= ?";

            SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(sql, new Object[]{TKUtils.getCurrentDate()}, new int[]{Types.DATE});
            while (rs.next()) {
                deptWorkAreas.put(rs.getString("dept"), rs.getLong("work_area"));
            }
        }
        return deptWorkAreas;
    }
}

