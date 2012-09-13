package org.kuali.hr.time.approval.service;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.notes.Note;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class TimeApproveServiceImpl implements TimeApproveService {

	private static final Logger LOG = Logger
			.getLogger(TimeApproveServiceImpl.class);

	public static final int DAYS_WINDOW_DELTA = 31;

	public Map<String, CalendarEntries> getPayCalendarEntriesForDept(
			String dept, Date currentDate) {
		DateTime minDt = new DateTime(currentDate,
				TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		java.sql.Date windowDate = TKUtils.getTimelessDate(minDt.toDate());

		Map<String, CalendarEntries> pceMap = new HashMap<String, CalendarEntries>();
		Set<String> principals = new HashSet<String>();
		List<WorkArea> workAreasForDept = TkServiceLocator.getWorkAreaService()
				.getWorkAreas(dept, new java.sql.Date(currentDate.getTime()));
		// Get all of the principals within our window of time.
		for (WorkArea workArea : workAreasForDept) {
			Long waNum = workArea.getWorkArea();
			List<Assignment> assignments = TkServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(
							waNum, TKUtils.getTimelessDate(currentDate));

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			} else {
				assignments = TkServiceLocator.getAssignmentService()
						.getActiveAssignmentsForWorkArea(waNum, windowDate);
				if (assignments != null) {
					for (Assignment assignment : assignments) {
						principals.add(assignment.getPrincipalId());
					}
				}
			}
		}

		// Get the pay calendars
		Set<Calendar> payCals = new HashSet<Calendar>();
		for (String pid : principals) {
			PrincipalHRAttributes pc = TkServiceLocator
					.getPrincipalHRAttributeService().getPrincipalCalendar(pid,
							currentDate);
			if (pc == null)
				pc = TkServiceLocator.getPrincipalHRAttributeService()
						.getPrincipalCalendar(pid, windowDate);

			if (pc != null) {
				payCals.add(pc.getCalendar());
			} else {
				LOG.warn("PrincipalCalendar null for principal: '" + pid + "'");
			}
		}

		// Grab the pay calendar entries + groups
		for (Calendar pc : payCals) {
			CalendarEntries pce = TkServiceLocator
					.getCalendarEntriesService()
					.getCurrentCalendarEntriesByCalendarId(
                            pc.getHrCalendarId(), currentDate);
			pceMap.put(pc.getCalendarName(), pce);
		}

		return pceMap;
	}

	@Override
	public Map<String, CalendarEntries> getPayCalendarEntriesForApprover(
			String principalId, Date currentDate, String dept) {
		TKUser tkUser = TKContext.getUser();

		Map<String, CalendarEntries> pceMap = new HashMap<String, CalendarEntries>();
		Set<String> principals = new HashSet<String>();
		DateTime minDt = new DateTime(currentDate,
				TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		java.sql.Date windowDate = TKUtils.getTimelessDate(minDt.toDate());
		Set<Long> approverWorkAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();

		// Get all of the principals within our window of time.
		for (Long waNum : approverWorkAreas) {
			List<Assignment> assignments = TkServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(
							waNum, TKUtils.getTimelessDate(currentDate));

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			}
		}

		// Get the pay calendars
		Set<Calendar> payCals = new HashSet<Calendar>();
		for (String pid : principals) {
			PrincipalHRAttributes pc = TkServiceLocator
					.getPrincipalHRAttributeService().getPrincipalCalendar(pid,
                            currentDate);
			if (pc == null)
				pc = TkServiceLocator.getPrincipalHRAttributeService()
						.getPrincipalCalendar(pid, windowDate);

			if (pc != null) {
				payCals.add(pc.getCalendar());
			} else {
				LOG.warn("PrincipalCalendar null for principal: '" + pid + "'");
			}
		}

		// Grab the pay calendar entries + groups
		for (Calendar pc : payCals) {
			CalendarEntries pce = TkServiceLocator
					.getCalendarEntriesService()
					.getCurrentCalendarEntriesByCalendarId(
                            pc.getHrCalendarId(), currentDate);
			pceMap.put(pc.getCalendarName(), pce);
		}

		return pceMap;
	}

	public SortedSet<String> getApproverPayCalendarGroups(Date payBeginDate,
			Date payEndDate) {
		SortedSet<String> pcg = new TreeSet<String>();

		TKUser tkUser = TKContext.getUser();
		Set<Long> approverWorkAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();
		List<Assignment> assignments = new ArrayList<Assignment>();

		for (Long workArea : approverWorkAreas) {
			if (workArea != null) {
				assignments.addAll(TkServiceLocator.getAssignmentService()
						.getActiveAssignmentsForWorkArea(workArea,
								new java.sql.Date(payBeginDate.getTime())));
			}
		}
		if (!assignments.isEmpty()) {
			for (Assignment assign : assignments) {
				String principalId = assign.getPrincipalId();
				TimesheetDocumentHeader tdh = TkServiceLocator
						.getTimesheetDocumentHeaderService().getDocumentHeader(
								principalId, payBeginDate, payEndDate);
				if (tdh != null) {
					String pyCalendarGroup = TkServiceLocator
							.getPrincipalHRAttributeService()
							.getPrincipalCalendar(principalId, tdh.getBeginDate())
							.getCalendar().getCalendarName();
					pcg.add(pyCalendarGroup);
				}
			}
		}
		return pcg;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(
			Date payBeginDate, Date payEndDate, String calGroup,
			List<TKPerson> persons, List<String> payCalendarLabels,
			CalendarEntries payCalendarEntries) {
		List<ApprovalTimeSummaryRow> rows = new LinkedList<ApprovalTimeSummaryRow>();
		Map<String, TimesheetDocumentHeader> principalDocumentHeader = getPrincipalDocumehtHeader(
				persons, payBeginDate, payEndDate);

		Calendar payCalendar = TkServiceLocator.getCalendarService()
				.getCalendar(payCalendarEntries.getHrCalendarId());
		DateTimeZone dateTimeZone = TkServiceLocator.getTimezoneService()
				.getUserTimezoneWithFallback();
		List<Interval> dayIntervals = TKUtils
				.getDaySpanForCalendarEntry(payCalendarEntries);

		for (TKPerson person : persons) {
			TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
			String documentId = "";
			if (principalDocumentHeader.containsKey(person.getPrincipalId())) {
				tdh = principalDocumentHeader.get(person.getPrincipalId());
				documentId = principalDocumentHeader.get(
						person.getPrincipalId()).getDocumentId();
			}
			List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
			List notes = new ArrayList();
			List<String> warnings = new ArrayList<String>();

			ApprovalTimeSummaryRow approvalSummaryRow = new ApprovalTimeSummaryRow();

			if (principalDocumentHeader.containsKey(person.getPrincipalId())) {
				approvalSummaryRow
						.setApprovalStatus(TkConstants.DOC_ROUTE_STATUS.get(tdh
								.getDocumentStatus()));
			}

			if (StringUtils.isNotBlank(documentId)) {
				timeBlocks = TkServiceLocator.getTimeBlockService()
						.getTimeBlocks(documentId);
				notes = this.getNotesForDocument(documentId);
                TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
				warnings = TkServiceLocator.getWarningService().getWarnings(td);
			}

			Map<String, BigDecimal> hoursToPayLabelMap = getHoursToPayDayMap(
					person.getPrincipalId(), payEndDate, payCalendarLabels,
					timeBlocks, null, payCalendarEntries, payCalendar,
					dateTimeZone, dayIntervals);

			approvalSummaryRow.setName(person.getPrincipalName());
			approvalSummaryRow.setPrincipalId(person.getPrincipalId());
			approvalSummaryRow.setPayCalendarGroup(calGroup);
			approvalSummaryRow.setDocumentId(documentId);
			approvalSummaryRow.setHoursToPayLabelMap(hoursToPayLabelMap);
			approvalSummaryRow.setPeriodTotal(hoursToPayLabelMap
					.get("Period Total"));
			approvalSummaryRow.setLstTimeBlocks(timeBlocks);
			approvalSummaryRow.setNotes(notes);
			approvalSummaryRow.setWarnings(warnings);

			// Compare last clock log versus now and if > threshold
			// highlight entry
			ClockLog lastClockLog = TkServiceLocator.getClockLogService()
					.getLastClockLog(person.getPrincipalId());
			approvalSummaryRow
					.setClockStatusMessage(createLabelForLastClockLog(lastClockLog));
			if (lastClockLog != null
					&& (StringUtils.equals(lastClockLog.getClockAction(),
							TkConstants.CLOCK_IN) || StringUtils
							.equals(lastClockLog.getClockAction(),
									TkConstants.LUNCH_IN))) {
				DateTime startTime = new DateTime(lastClockLog
						.getClockTimestamp().getTime());
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

	public List<TimesheetDocumentHeader> getDocumentHeadersByPrincipalIds(
			Date payBeginDate, Date payEndDate, List<String> principalIds) {
		List<TimesheetDocumentHeader> headers = new LinkedList<TimesheetDocumentHeader>();
		for (String principalId : principalIds) {
			TimesheetDocumentHeader tdh = TkServiceLocator
					.getTimesheetDocumentHeaderService().getDocumentHeader(
							principalId, payBeginDate, payEndDate);
			if (tdh != null) {
				headers.add(tdh);
			}
		}

		return headers;
	}

	/**
	 * Get pay calendar labels for approval tab
	 */
	public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate,
			Date payEndDate) {
		// :)
		// http://stackoverflow.com/questions/111933/why-shouldnt-i-use-hungarian-notation
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
		// return sdf.format(dt);
		if (cl == null) {
			return "No previous clock information";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String dateTime = sdf.format(new java.sql.Date(cl.getClockTimestamp()
				.getTime()));
		if (StringUtils.equals(cl.getClockAction(), TkConstants.CLOCK_IN)) {
			return "Clocked in since: " + dateTime;
		} else if (StringUtils.equals(cl.getClockAction(),
				TkConstants.LUNCH_OUT)) {
			return "At Lunch since: " + dateTime;
		} else if (StringUtils
				.equals(cl.getClockAction(), TkConstants.LUNCH_IN)) {
			return "Returned from Lunch : " + dateTime;
		} else if (StringUtils.equals(cl.getClockAction(),
				TkConstants.CLOCK_OUT)) {
			return "Clocked out since: " + dateTime;
		} else {
			return "No previous clock information";
		}

	}

	public List<Map<String, Map<String, BigDecimal>>> getHoursByDayAssignmentBuckets(
			TkTimeBlockAggregate aggregate,
			List<Assignment> approverAssignments, List<String> payCalendarLabels) {
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
				timeToAdd = timeToAdd.add(block.getHours(),
						TkConstants.MATH_CONTEXT);
				hours.set(day, timeToAdd);
			}
		}

		// Compute Weekly / Period Summary Totals for each Assignment.
		// assignment row, each row has a map of pay calendar label -> big
		// decimal totals.
		Map<String, Map<String, BigDecimal>> approverAssignToPayHourTotals = new HashMap<String, Map<String, BigDecimal>>();
		Map<String, Map<String, BigDecimal>> otherAssignToPayHourTotals = new HashMap<String, Map<String, BigDecimal>>();

		// Pass by Reference
		generateSummaries(approverAssignToPayHourTotals, approverHours,
				payCalendarLabels);
		generateSummaries(otherAssignToPayHourTotals, otherHours,
				payCalendarLabels);

		// Add to our return list, "virtual" tuple.
		List<Map<String, Map<String, BigDecimal>>> returnTuple = new ArrayList<Map<String, Map<String, BigDecimal>>>(
				2);
		returnTuple.add(approverAssignToPayHourTotals);
		returnTuple.add(otherAssignToPayHourTotals);

		return returnTuple;
	}

	// Helper method for above method.
	private void generateSummaries(
			Map<String, Map<String, BigDecimal>> payHourTotals,
			Map<String, List<BigDecimal>> assignmentToHours,
			List<String> payCalendarLabels) {
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
				} else if (StringUtils
						.contains(payCalendarLabel, "Total Hours")) {
					hoursToPayLabelMap.put(payCalendarLabel, periodTotal);
				} else {
					BigDecimal dayTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
					if (dayCount < dayTotals.size())
						dayTotal = dayTotals.get(dayCount);

					hoursToPayLabelMap.put(payCalendarLabel, dayTotal);
					weekTotal = weekTotal.add(dayTotal,
							TkConstants.MATH_CONTEXT);
					periodTotal = periodTotal.add(dayTotal);
					dayCount++;
				}
			}
			payHourTotals.put(key, hoursToPayLabelMap);
		}
	}

	private Map<String, Assignment> mapAssignmentsByAssignmentKey(
			List<Assignment> assignments) {
		Map<String, Assignment> assignmentMap = new HashMap<String, Assignment>();
		for (Assignment assignment : assignments) {
			assignmentMap
					.put(AssignmentDescriptionKey
							.getAssignmentKeyString(assignment), assignment);
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
	public Map<String, BigDecimal> getHoursToPayDayMap(String principalId,
			Date payEndDate, List<String> payCalendarLabels,
			List<TimeBlock> lstTimeBlocks, Long workArea,
			CalendarEntries payCalendarEntries, Calendar payCalendar,
			DateTimeZone dateTimeZone, List<Interval> dayIntervals) {
		Map<String, BigDecimal> hoursToPayLabelMap = new LinkedHashMap<String, BigDecimal>();
		List<BigDecimal> dayTotals = new ArrayList<BigDecimal>();

		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(
				lstTimeBlocks, payCalendarEntries, payCalendar, true,
				dayIntervals);
		List<FlsaWeek> flsaWeeks = tkTimeBlockAggregate
				.getFlsaWeeks(dateTimeZone);
		for (FlsaWeek week : flsaWeeks) {
			for (FlsaDay day : week.getFlsaDays()) {
				BigDecimal total = new BigDecimal(0.00);
				for (TimeBlock tb : day.getAppliedTimeBlocks()) {
					if (workArea != null) {
						if (tb.getWorkArea().compareTo(workArea) == 0) {
							total = total.add(tb.getHours(),
									TkConstants.MATH_CONTEXT);
						} else {
							total = total.add(new BigDecimal("0"),
									TkConstants.MATH_CONTEXT);
						}
					} else {
						total = total.add(tb.getHours(),
								TkConstants.MATH_CONTEXT);
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
				if(dayCount < dayTotals.size()) {
					hoursToPayLabelMap.put(payCalendarLabel,
							dayTotals.get(dayCount));
					weekTotal = weekTotal.add(dayTotals.get(dayCount),
							TkConstants.MATH_CONTEXT);
					periodTotal = periodTotal.add(dayTotals.get(dayCount));
					dayCount++;
				}

			}

		}
		return hoursToPayLabelMap;
	}

	public boolean doesApproverHavePrincipalsForCalendarGroup(Date asOfDate,
			String calGroup) {
		TKUser tkUser = TKContext.getUser();
		Set<Long> approverWorkAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();
		for (Long workArea : approverWorkAreas) {
			List<Assignment> assignments = TkServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(
							workArea, new java.sql.Date(asOfDate.getTime()));
			List<String> principalIds = new ArrayList<String>();
			for (Assignment assign : assignments) {
				if (principalIds.contains(assign.getPrincipalId())) {
					continue;
				}
				principalIds.add(assign.getPrincipalId());
			}

			for (String principalId : principalIds) {
				PrincipalHRAttributes principalCal = TkServiceLocator
						.getPrincipalHRAttributeService().getPrincipalCalendar(
								principalId, asOfDate);
				if (StringUtils.equals(principalCal.getPayCalendar(),
						calGroup)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public List getNotesForDocument(String documentNumber) {
		List notes = KEWServiceLocator.getNoteService()
				.getNotesByDocumentId(documentNumber);
		// add the user name in the note object
		for (Object obj : notes) {
			Note note = (Note) obj;
			note.setNoteAuthorFullName(KimApiServiceLocator.getPersonService()
					.getPerson(note.getNoteAuthorWorkflowId()).getName());
		}
		return notes;
	}

	@Override
	public List<String> getUniquePayGroups() {
		String sql = "SELECT DISTINCT P.pay_calendar FROM hr_principal_attributes_t P WHERE P.active = 'Y'";
		SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(sql);
		List<String> pyGroups = new LinkedList<String>();
		while (rs.next()) {
			pyGroups.add(rs.getString("pay_calendar"));
		}
		return pyGroups;
	}

	@Override
	public List<String> getPrincipalIdsByDeptWorkAreaRolename(String roleName,
			String department, String workArea, java.sql.Date payBeginDate,
			java.sql.Date payEndDate, String calGroup) {
		List<String> principalIds = getPrincipalIdsWithActiveAssignmentsForCalendarGroupByDeptAndWorkArea(
				roleName, department, workArea, calGroup, payEndDate,
				payBeginDate, payEndDate);
		return principalIds;
	}
	
	protected List<String> getPrincipalIdsWithActiveAssignmentsForCalendarGroupByDeptAndWorkArea(
		      String roleName, String department, String workArea,
		      String payCalendarGroup, java.sql.Date effdt,
		      java.sql.Date beginDate, java.sql.Date endDate) {
	    String sql = null;

        List<Job> jobs = TkServiceLocator.getJobService().getJobs(TKUser.getCurrentTargetPerson().getPrincipalId(), effdt);
        String jobPositionNumbersList = "'";
        for (Job job : jobs) {
                        jobPositionNumbersList += job.getPositionNumber() + "','";
        }
        /* the sql statement will enclose this string in single quotes, so we do not want the leading quote, or the trailing quote, comma, and quote. */
        if (jobPositionNumbersList.length() > 3) {
            jobPositionNumbersList = jobPositionNumbersList.substring(1, jobPositionNumbersList.length()-3) ;
        } else {
            jobPositionNumbersList = jobPositionNumbersList.substring(1);
        }

	    if (department == null || department.isEmpty()) {
	      return new ArrayList<String>();
	    } else {
	      List<String> principalIds = new ArrayList<String>();
	      SqlRowSet rs = null;
          sql = "SELECT DISTINCT A0.PRINCIPAL_ID FROM TK_ASSIGNMENT_T A0, HR_ROLES_T R0, TK_WORK_AREA_T W0, HR_PRINCIPAL_ATTRIBUTES_T P0  WHERE "
        		  + "((A0.EFFDT =  (SELECT MAX(EFFDT)  FROM TK_ASSIGNMENT_T  WHERE PRINCIPAL_ID = A0.PRINCIPAL_ID  AND EFFDT <= ? AND WORK_AREA = A0.WORK_AREA  AND TASK = A0.TASK AND JOB_NUMBER = A0.JOB_NUMBER) AND "
                  + "A0.TIMESTAMP =  (SELECT MAX(TIMESTAMP)  FROM TK_ASSIGNMENT_T  WHERE PRINCIPAL_ID = A0.PRINCIPAL_ID  AND EFFDT = A0.EFFDT AND WORK_AREA = A0.WORK_AREA AND TASK = A0.TASK AND JOB_NUMBER = A0.JOB_NUMBER) AND "
                  + "A0.ACTIVE = 'Y') OR (A0.ACTIVE = 'N'  AND A0.EFFDT >= ? AND A0.EFFDT <= ?)) AND "
                  + "R0.WORK_AREA = A0.WORK_AREA AND "
                  + "R0.ROLE_NAME IN ('TK_APPROVER', 'TK_APPROVER_DELEGATE', 'TK_REVIEWER') AND "
                  + "R0.ACTIVE = 'Y' AND "
                  + "( (R0.PRINCIPAL_ID = ? AND "
                  + "R0.EFFDT = (SELECT MAX(EFFDT)  FROM HR_ROLES_T  WHERE ROLE_NAME = R0.ROLE_NAME AND PRINCIPAL_ID = R0.PRINCIPAL_ID AND EFFDT <= ? AND WORK_AREA = R0.WORK_AREA) AND "
                  + "R0.TIMESTAMP = (SELECT MAX(TIMESTAMP)  FROM HR_ROLES_T  WHERE ROLE_NAME = R0.ROLE_NAME AND PRINCIPAL_ID = R0.PRINCIPAL_ID AND EFFDT = R0.EFFDT AND WORK_AREA = R0.WORK_AREA) "
                  + ") or ("
                  + "R0.POSITION_NBR in (?) AND "
                  + "R0.EFFDT = (SELECT MAX(EFFDT)  FROM HR_ROLES_T  WHERE ROLE_NAME = R0.ROLE_NAME AND POSITION_NBR = R0.POSITION_NBR AND EFFDT <= ? AND WORK_AREA = R0.WORK_AREA) AND "
                  + "R0.TIMESTAMP = (SELECT MAX(TIMESTAMP)  FROM HR_ROLES_T  WHERE ROLE_NAME = R0.ROLE_NAME AND POSITION_NBR = R0.POSITION_NBR AND EFFDT = R0.EFFDT AND WORK_AREA = R0.WORK_AREA) "
                  + ") ) AND "
                  + "W0.WORK_AREA = A0.WORK_AREA AND "
                  + "W0.DEPT = ? AND "
                  + "W0.EFFDT = (SELECT MAX(EFFDT) FROM TK_WORK_AREA_T WHERE EFFDT <= ? AND WORK_AREA = W0.WORK_AREA) AND "
                  + "W0.TIMESTAMP =  (SELECT MAX(TIMESTAMP)  FROM TK_WORK_AREA_T  WHERE WORK_AREA = W0.WORK_AREA  AND EFFDT = W0.EFFDT) AND "
                  + "W0.ACTIVE = 'Y' AND "
                  + "P0.PRINCIPAL_ID = A0.PRINCIPAL_ID AND "
                  + "P0.PAY_CALENDAR = ?";


	       int[] params = null;
	       Object[] values = null;
	       if (workArea != null) {
	          sql += " AND A0.WORK_AREA = ? ";
	          params = new int[] {java.sql.Types.DATE,
	              java.sql.Types.DATE,
	              java.sql.Types.DATE,
	              java.sql.Types.VARCHAR, 
	              java.sql.Types.DATE,
                  java.sql.Types.VARCHAR,
                  java.sql.Types.DATE,
	              java.sql.Types.VARCHAR,
	              java.sql.Types.DATE,
	              java.sql.Types.VARCHAR,
	              java.sql.Types.INTEGER };
	          values = new Object[] {effdt, beginDate, endDate, TKUser.getCurrentTargetPerson().getPrincipalId(), effdt, jobPositionNumbersList, effdt, department, effdt, payCalendarGroup, workArea };
	        }else {
	          params = new int[] {java.sql.Types.DATE,
	              java.sql.Types.DATE,
	              java.sql.Types.DATE,
	              java.sql.Types.VARCHAR, 
	              java.sql.Types.DATE,
                  java.sql.Types.VARCHAR,
                  java.sql.Types.DATE,
	              java.sql.Types.VARCHAR,
	              java.sql.Types.DATE,
	              java.sql.Types.VARCHAR};
	          values = new Object[] {effdt, beginDate, endDate, TKUser.getCurrentTargetPerson().getPrincipalId(), effdt, jobPositionNumbersList, effdt, department, effdt, payCalendarGroup};
	        }
	        rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(
	            sql, values, params);
	      while (rs.next()) {
	        principalIds.add(rs.getString("principal_id"));
	      }
	      return principalIds;
	    }
	}

	@Override
	public Map<String, TimesheetDocumentHeader> getPrincipalDocumehtHeader(
			List<TKPerson> persons, Date payBeginDate, Date payEndDate) {
		Map<String, TimesheetDocumentHeader> principalDocumentHeader = new LinkedHashMap<String, TimesheetDocumentHeader>();
		for (TKPerson person : persons) {
			String principalId = person.getPrincipalId();
			
			TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate);
			if(tdh != null) {
				principalDocumentHeader.put(principalId, tdh);	
			}
		}
		return principalDocumentHeader;
	}

	@Override
	public Multimap<String, Long> getDeptWorkAreasByWorkAreas(
			Set<Long> approverWorkAreas) {
		Multimap<String, Long> deptWorkAreas = HashMultimap.create();

		if (approverWorkAreas.size() > 0) {
			// prepare the OR statement for query
			StringBuilder workAreas = new StringBuilder();
			for (Long workarea : approverWorkAreas) {
				if(workarea != null) {
					workAreas.append("work_area = " + workarea + " or ");
				}
			}
			String workAreasForQuery = workAreas.substring(0,
					workAreas.length() - 3);
			String sql = "SELECT DISTINCT work_area, dept FROM tk_work_area_t "
					+ "WHERE " + workAreasForQuery + " AND effdt <= ?";

			/**
			 * Multimap is an interface from Google's java common library -
			 * Guava. HashMultimap allows us to create a map with duplicate keys
			 * which will then generate a data structure, i.e. [key] => [value1,
			 * value2, value3...]
			 * 
			 * It save a good lines of code to do the same thing through the
			 * java map, e.g. Map<String, List<String>> map = new
			 * Hashmap<String, List<String>>();
			 * 
			 * See the java doc for more information:
			 * http://google-collections.googlecode
			 * .com/svn/trunk/javadoc/com/google/common/collect/Multimap.html
			 */
			SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(
					sql, new Object[] { TKUtils.getCurrentDate() },
					new int[] { Types.DATE });
			while (rs.next()) {
				deptWorkAreas
						.put(rs.getString("dept"), rs.getLong("work_area"));
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
			String sql = "SELECT DISTINCT work_area, dept FROM tk_work_area_t "
					+ "WHERE " + deptsForQuery + " AND effdt <= ?";

			SqlRowSet rs = TkServiceLocator.getTkJdbcTemplate().queryForRowSet(
					sql, new Object[] { TKUtils.getCurrentDate() },
					new int[] { Types.DATE });
			while (rs.next()) {
				deptWorkAreas
						.put(rs.getString("dept"), rs.getLong("work_area"));
			}
		}
		return deptWorkAreas;
	}

	public DocumentRouteHeaderValue getRouteHeader(String documentId) {
		return KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentId);
	}
	
	@Override
	public List<CalendarEntries> getAllPayCalendarEntriesForApprover(String principalId, Date currentDate) {
		TKUser tkUser = TKContext.getUser();
		Set<String> principals = new HashSet<String>();
		DateTime minDt = new DateTime(currentDate,
				TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		Set<Long> approverWorkAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();

		// Get all of the principals within our window of time.
		for (Long waNum : approverWorkAreas) {
			List<Assignment> assignments = TkServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, TKUtils.getTimelessDate(currentDate));

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			}
		}
		List<TimesheetDocumentHeader> documentHeaders = new ArrayList<TimesheetDocumentHeader>();
		for(String pid : principals) {
			documentHeaders.addAll(TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeadersForPrincipalId(pid));
		}
		Set<CalendarEntries> payPeriodSet = new HashSet<CalendarEntries>();
		for(TimesheetDocumentHeader tdh : documentHeaders) {
    		CalendarEntries pe = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(tdh.getBeginDate(), tdh.getEndDate());
    		if(pe != null) {
    			payPeriodSet.add(pe);
    		}
        }
		List<CalendarEntries> ppList = new ArrayList<CalendarEntries>(payPeriodSet);
        
		return ppList;
	}
	
	@Override
	public List<CalendarEntries> getAllLeavePayCalendarEntriesForApprover(String principalId, Date currentDate) {
		TKUser tkUser = TKContext.getUser();
		Set<String> principals = new HashSet<String>();
		DateTime minDt = new DateTime(currentDate,
				TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		Set<Long> approverWorkAreas = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).getApproverWorkAreas();

		// Get all of the principals within our window of time.
		for (Long waNum : approverWorkAreas) {
			List<Assignment> assignments = TkServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, TKUtils.getTimelessDate(currentDate));

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			}
		}
		List<LeaveCalendarDocumentHeader> documentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();
		for(String pid : principals) {
			documentHeaders.addAll(TkServiceLocator.getLeaveCalendarDocumentHeaderService().getAllDocumentHeadersForPricipalId(pid));
		}
		Set<CalendarEntries> payPeriodSet = new HashSet<CalendarEntries>();
		for(LeaveCalendarDocumentHeader lcdh : documentHeaders) {
    		CalendarEntries pe = TkServiceLocator.getCalendarEntriesService().getCalendarEntriesByBeginAndEndDate(lcdh.getBeginDate(), lcdh.getEndDate());
    		if(pe != null) {
    			payPeriodSet.add(pe);
    		}
        }
		List<CalendarEntries> ppList = new ArrayList<CalendarEntries>(payPeriodSet);
        
		return ppList;
	}
	
	


	
}
