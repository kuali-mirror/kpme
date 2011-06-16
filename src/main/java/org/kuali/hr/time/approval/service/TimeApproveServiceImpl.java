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
import org.kuali.hr.time.util.*;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.math.BigDecimal;
import java.util.*;

public class TimeApproveServiceImpl implements TimeApproveService {


    public Set<String> getApproverPayCalendarGroups(Date payBeginDate, Date payEndDate) {
        HashSet<String> pcg = new HashSet<String>();

        TKUser tkUser = TKContext.getUser();
        Set<Long> approverWorkAreas = tkUser.getActualPersonRoles().getApproverWorkAreas();
        List<Assignment> assignments = new ArrayList<Assignment>();

        for(Long workArea : approverWorkAreas){
            if(workArea != null){
                assignments.addAll(TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, TKUtils.getCurrentDate()));
            }
        }
        if (!assignments.isEmpty()) {
            for (Assignment assign : assignments) {
                String principalId = assign.getPrincipalId();
                TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
                if (tdh != null) {
                    String calendarGroup = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, payBeginDate).getCalendarGroup();
                    pcg.add(calendarGroup);
                }
            }
        }

        return pcg;
    }

    @Override
    public Map<String, List<ApprovalTimeSummaryRow>> getApprovalSummaryRowsMap(Date payBeginDate, Date payEndDate, Long workArea) {
        Map<String, List<ApprovalTimeSummaryRow>> mappedRows = new HashMap<String, List<ApprovalTimeSummaryRow>>();

        TKUser tkUser = TKContext.getUser();
        Set<Long> approverWorkAreas = tkUser.getActualPersonRoles().getApproverWorkAreas();
        List<Assignment> lstEmployees = new ArrayList<Assignment>();

        for(Long aWorkArea : approverWorkAreas){
            if (aWorkArea != null) {
                if (workArea != null && aWorkArea.equals(workArea)) {
                    lstEmployees.addAll(TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(aWorkArea, TKUtils.getCurrentDate()));
                }
            }
        }
        if (!lstEmployees.isEmpty()) {
            List<String> userIds = new ArrayList<String>();
            List<String> clockUsers = new ArrayList<String>();
            for (Assignment assign : lstEmployees) {
                if (!userIds.contains(assign.getPrincipalId())) {
                    userIds.add(assign.getPrincipalId());

                    // TODO: What are we storing this clockUsers list for?
                    if (assign.getTimeCollectionRule().isClockUserFl()) {
                        clockUsers.add(assign.getPrincipalId());
                    }
                }
            }
            for (String principalId : userIds) {
                TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
                if (tdh != null) {
                    String calendarGroup = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, payBeginDate).getCalendarGroup();
                    List<ApprovalTimeSummaryRow> rows = mappedRows.get(calendarGroup);
                    if (rows == null) {
                        rows = new ArrayList<ApprovalTimeSummaryRow>();
                        mappedRows.put(calendarGroup, rows);
                    }

                    String documentId = tdh.getDocumentId();
                    Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
                    List<TimeBlock> lstTimeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(documentId));
                    Map<String, BigDecimal> hoursToPayLabelMap = getHoursToPayDayMap(principalId, payBeginDate, getPayCalendarLabelsForApprovalTab(payBeginDate,payEndDate), lstTimeBlocks);

                    ApprovalTimeSummaryRow approvalSummaryRow = new ApprovalTimeSummaryRow();
                    approvalSummaryRow.setName(person.getName());
                    approvalSummaryRow.setPayCalendarGroup(calendarGroup);
                    approvalSummaryRow.setDocumentId(documentId);
                    approvalSummaryRow.setLstTimeBlocks(lstTimeBlocks);
                    approvalSummaryRow.setApprovalStatus(tdh.getDocumentStatus());
                    approvalSummaryRow.setHoursToPayLabelMap(hoursToPayLabelMap);
                    approvalSummaryRow.setClockStatusMessage(createLabelForLastClockLog(principalId));
                    rows.add(approvalSummaryRow);
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

        Map<String, List<ApprovalTimeSummaryRow>> mrows = this.getApprovalSummaryRowsMap(payBeginDate, payEndDate, workArea);
        if (mrows.containsKey(calGroup))
            rows = mrows.get(calGroup);

        return rows;
    }

    /*
     * Right now this code is just calling our "Big" data retriever and only returning
     * a subset of that data. It is obvious that some optimization should be done here,
     * for now this is a "future" TODO, to get this going.
     */
    public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup) {
        List<ApprovalTimeSummaryRow> rows = new ArrayList<ApprovalTimeSummaryRow>();

        Map<String, List<ApprovalTimeSummaryRow>> mrows = this.getApprovalSummaryRowsMap(payBeginDate, payEndDate, null);
        if (mrows.containsKey(calGroup))
            rows = mrows.get(calGroup);

        return rows;
    }

	/**
	 * Populate the Summary rows based on the user and begin and end date
	 */
	@Override
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate) {
		List<ApprovalTimeSummaryRow> lstApprovalRows = new ArrayList<ApprovalTimeSummaryRow>();

        Map<String, List<ApprovalTimeSummaryRow>> mrows = this.getApprovalSummaryRowsMap(payBeginDate, payEndDate, null);
        for (List<ApprovalTimeSummaryRow> list : mrows.values()) {
            lstApprovalRows.addAll(list);
        }

		return lstApprovalRows;
	}
	/**
	 * Get pay calendar labels for approval tab
	 */
	@CacheResult
	public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate){
		List<String> lstPayCalendarLabels = new ArrayList<String>();
		DateTime payBegin = new DateTime(payBeginDate.getTime());
		DateTime payEnd = new DateTime(payEndDate.getTime());
		DateTime currTime = payBegin;
		int dayCounter = 1;
		int weekCounter = 1;

		while(currTime.isBefore(payEnd)){
			String labelForDay = createLabelForDay(currTime);
			lstPayCalendarLabels.add(labelForDay);
			currTime = currTime.plusDays(1);
			if((dayCounter % 7)==0){
				lstPayCalendarLabels.add("Week "+weekCounter);
				weekCounter++;
			}
			dayCounter++;
		}
		lstPayCalendarLabels.add("Total Hours");
		return lstPayCalendarLabels;
	}
	/**
	 * Create label for a given pay calendar day
	 * @param fromDate
	 * @return
	 */
	private String createLabelForDay(DateTime fromDate){
		DateMidnight dateMidnight = new DateMidnight(fromDate);
		if(dateMidnight.compareTo(fromDate)==0){
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM/dd");
			return fmt.print(fromDate);
		}
		DateTime toDate = fromDate.plusDays(1);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM/dd k:m:s");
		return fmt.print(fromDate) + "-" + fmt.print(toDate);
	}
	/**
	 * Create label for the last clock log
	 * @param principalId
	 * @return
	 */
	private String createLabelForLastClockLog(String principalId){
		ClockLog cl = TkServiceLocator.getClockLogService().getLastClockLog(principalId);
		if(cl == null){
			return "No previous clock information";
		}
		if(StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_IN)){
			return "Clocked in since: "+cl.getClockTimestamp();
		} else if(StringUtils.equals(cl.getClockAction(), TkConstants.LUNCH_IN)){
			return "At Lunch since: "+cl.getClockTimestamp();
		} else if(StringUtils.equals(cl.getClockAction(), TkConstants.LUNCH_OUT)){
			return "Returned from Lunch : "+cl.getClockTimestamp();
		} else if(StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_OUT)){
			return "Clocked out since: "+cl.getClockTimestamp();
		} else {
			return "No previous clock information";
		}

	}
	/**
	 * Aggregate TimeBlocks to hours per day and sum for week
	 * @param principalId
	 * @param beginDateTime
	 * @param payCalendarLabels
	 * @param lstTimeBlocks
	 * @return
	 */
	public Map<String,BigDecimal> getHoursToPayDayMap(String principalId, Date beginDateTime, List<String> payCalendarLabels, List<TimeBlock> lstTimeBlocks){
		Map<String,BigDecimal> hoursToPayLabelMap = new HashMap<String, BigDecimal>();
		List<BigDecimal> dayTotals = new ArrayList<BigDecimal>();
		PayCalendarEntries payCalendarEntry = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(principalId, beginDateTime);
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(lstTimeBlocks, payCalendarEntry);
		List<List<TimeBlock>> lstOfLstOfTimeBlocksPerDay = tkTimeBlockAggregate.getDayTimeBlockList();
		for(List<TimeBlock> lstTimeBlocksForDay : lstOfLstOfTimeBlocksPerDay){
			BigDecimal total = new BigDecimal(0.00);
			for(TimeBlock tb : lstTimeBlocksForDay){
				if(tb.getHours()!=null){
					total = total.add(tb.getHours(), TkConstants.MATH_CONTEXT);
				}
			}
			dayTotals.add(total);
		}
		int dayCount = 0;
		BigDecimal weekTotal = new BigDecimal(0.00);
		BigDecimal periodTotal = new BigDecimal(0.00);
		for(String payCalendarLabel : payCalendarLabels){
			if(StringUtils.contains(payCalendarLabel, "Week")){
				hoursToPayLabelMap.put(payCalendarLabel, weekTotal);
				weekTotal = new BigDecimal(0.00);
			} else if(StringUtils.contains(payCalendarLabel, "Total Hours")){
					hoursToPayLabelMap.put(payCalendarLabel, periodTotal);
			}
			else {
				hoursToPayLabelMap.put(payCalendarLabel, dayTotals.get(dayCount));
				weekTotal = weekTotal.add(dayTotals.get(dayCount), TkConstants.MATH_CONTEXT);
				periodTotal = periodTotal.add(dayTotals.get(dayCount));
				dayCount++;

			}

		}
		return hoursToPayLabelMap;
	}
	@SuppressWarnings("rawtypes")
	public List hasNotes(String documentNumber){
		List notes = KEWServiceLocator.getNoteService().getNotesByRouteHeaderId(Long.parseLong(documentNumber));
		return notes;
	}
}
