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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeApproveServiceImpl implements TimeApproveService {

	private static final Logger LOG = Logger
			.getLogger(TimeApproveServiceImpl.class);

	public static final int DAYS_WINDOW_DELTA = 31;

	public Map<String, CalendarEntry> getPayCalendarEntriesForDept(
			String dept, LocalDate currentDate) {
		DateTime minDt = currentDate.toDateTimeAtStartOfDay(TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		LocalDate windowDate = minDt.toLocalDate();

		Map<String, CalendarEntry> pceMap = new HashMap<String, CalendarEntry>();
		Set<String> principals = new HashSet<String>();
		List<WorkArea> workAreasForDept = HrServiceLocator.getWorkAreaService()
				.getWorkAreas(dept, currentDate);
		// Get all of the principals within our window of time.
		for (WorkArea workArea : workAreasForDept) {
			Long waNum = workArea.getWorkArea();
			List<Assignment> assignments = HrServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(
							waNum, currentDate);

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			} else {
				assignments = HrServiceLocator.getAssignmentService()
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
			PrincipalHRAttributes pc = HrServiceLocator
					.getPrincipalHRAttributeService().getPrincipalCalendar(pid,
							currentDate);
			if (pc == null)
				pc = HrServiceLocator.getPrincipalHRAttributeService()
						.getPrincipalCalendar(pid, windowDate);

			if (pc != null) {
				payCals.add(pc.getCalendar());
			} else {
				LOG.warn("PrincipalCalendar null for principal: '" + pid + "'");
			}
		}

		// Grab the pay calendar entries + groups
		for (Calendar pc : payCals) {
			CalendarEntry pce = HrServiceLocator
					.getCalendarEntryService()
					.getCurrentCalendarEntryByCalendarId(
                            pc.getHrCalendarId(), currentDate.toDateTimeAtStartOfDay());
			pceMap.put(pc.getCalendarName(), pce);
		}

		return pceMap;
	}

	@Override
	public Map<String, CalendarEntry> getPayCalendarEntriesForApprover(
			String principalId, LocalDate currentDate, String dept) {

		Map<String, CalendarEntry> pceMap = new HashMap<String, CalendarEntry>();
		Set<String> principals = new HashSet<String>();
		DateTime minDt = currentDate.toDateTimeAtStartOfDay(TKUtils.getSystemDateTimeZone());
		minDt = minDt.minusDays(DAYS_WINDOW_DELTA);
		LocalDate windowDate = minDt.toLocalDate();
		
    	Set<Long> workAreas = new HashSet<Long>();
    	workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
        workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

		// Get all of the principals within our window of time.
		for (Long waNum : workAreas) {
			List<Assignment> assignments = HrServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(
							waNum, currentDate);

			if (assignments != null) {
				for (Assignment assignment : assignments) {
					principals.add(assignment.getPrincipalId());
				}
			}
		}

		// Get the pay calendars
		Set<Calendar> payCals = new HashSet<Calendar>();
		for (String pid : principals) {
			PrincipalHRAttributes pc = HrServiceLocator
					.getPrincipalHRAttributeService().getPrincipalCalendar(pid,
                            currentDate);
			if (pc == null)
				pc = HrServiceLocator.getPrincipalHRAttributeService()
						.getPrincipalCalendar(pid, windowDate);

			if (pc != null) {
				payCals.add(pc.getCalendar());
			} else {
				LOG.warn("PrincipalCalendar null for principal: '" + pid + "'");
			}
		}

		// Grab the pay calendar entries + groups
		for (Calendar pc : payCals) {
			CalendarEntry pce = HrServiceLocator
					.getCalendarEntryService()
					.getCurrentCalendarEntryByCalendarId(
                            pc.getHrCalendarId(), currentDate.toDateTimeAtStartOfDay());
			pceMap.put(pc.getCalendarName(), pce);
		}

		return pceMap;
	}

	public SortedSet<String> getApproverPayCalendarGroups(DateTime payBeginDate,
			DateTime payEndDate) {
		SortedSet<String> pcg = new TreeSet<String>();

    	Set<Long> workAreas = new HashSet<Long>();
    	workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(GlobalVariables.getUserSession().getPrincipalId(), KPMERole.APPROVER.getRoleName(), new DateTime(), true));
        workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(GlobalVariables.getUserSession().getPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

		List<Assignment> assignments = new ArrayList<Assignment>();

		for (Long workArea : workAreas) {
			if (workArea != null) {
				assignments.addAll(HrServiceLocator.getAssignmentService()
						.getActiveAssignmentsForWorkArea(workArea,
								payBeginDate.toLocalDate()));
			}
		}
		if (!assignments.isEmpty()) {
			for (Assignment assign : assignments) {
				String principalId = assign.getPrincipalId();
				TimesheetDocumentHeader tdh = TkServiceLocator
						.getTimesheetDocumentHeaderService().getDocumentHeader(
								principalId, payBeginDate, payEndDate);
				if (tdh != null) {
					String pyCalendarGroup = HrServiceLocator
							.getPrincipalHRAttributeService()
							.getPrincipalCalendar(principalId, tdh.getBeginDateTime().toLocalDate())
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
			DateTime payBeginDate, DateTime payEndDate, String calGroup,
			List<String> principalIds, List<String> payCalendarLabels,
			CalendarEntry payCalendarEntry) {

		List<ApprovalTimeSummaryRow> rows = new LinkedList<ApprovalTimeSummaryRow>();
		Map<String, TimesheetDocumentHeader> principalDocumentHeader = getPrincipalDocumentHeader(
				principalIds, payBeginDate, payEndDate);

		Calendar payCalendar = HrServiceLocator.getCalendarService()
				.getCalendar(payCalendarEntry.getHrCalendarId());
		DateTimeZone dateTimeZone = HrServiceLocator.getTimezoneService()
				.getUserTimezoneWithFallback();
		List<Interval> dayIntervals = TKUtils
				.getDaySpanForCalendarEntry(payCalendarEntry);

		for (String principalId : principalIds) {
			TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
			String documentId = "";
			if (principalDocumentHeader.containsKey(principalId)) {
				tdh = principalDocumentHeader.get(principalId);
				Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(principalId);
				documentId = principalDocumentHeader.get(
						principal.getPrincipalId()).getDocumentId();
			}
			List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
			List<Note> notes = new ArrayList<Note>();
			List<String> warnings = new ArrayList<String>();

			ApprovalTimeSummaryRow approvalSummaryRow = new ApprovalTimeSummaryRow();

			if (principalDocumentHeader.containsKey(principalId)) {
				approvalSummaryRow
						.setApprovalStatus(HrConstants.DOC_ROUTE_STATUS.get(tdh
								.getDocumentStatus()));
			}

			if (StringUtils.isNotBlank(documentId)) {
				timeBlocks = TkServiceLocator.getTimeBlockService()
						.getTimeBlocks(documentId);
                notes = getNotesForDocument(documentId);
                TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
                Map<String, List<LocalDate>> earnCodeMap = new HashMap<String, List<LocalDate>>();
                for(TimeBlock tb : td.getTimeBlocks()) {
                	if(!earnCodeMap.containsKey(tb.getEarnCode())) {
                		List<LocalDate> lst = new ArrayList<LocalDate>();
                		lst.add(tb.getBeginDateTime().toLocalDate());
                		earnCodeMap.put(tb.getEarnCode(), lst);
                	}
                	else
                		earnCodeMap.get(tb.getEarnCode()).add(tb.getBeginDateTime().toLocalDate());
                }
                warnings = HrServiceLocator.getEarnCodeGroupService().warningTextFromEarnCodeGroupsOfDocument(earnCodeMap);

			}
			Map<String, Set<String>> transactionalWarnings = LeaveCalendarValidationUtil.validatePendingTransactions(principalId, payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate(), payCalendarEntry.getEndPeriodFullDateTime().toLocalDate());
			
			warnings.addAll(transactionalWarnings.get("infoMessages"));
			warnings.addAll(transactionalWarnings.get("warningMessages"));
			warnings.addAll(transactionalWarnings.get("actionMessages"));
			
			Map<String, Set<String>> eligibleTransfers = findWarnings(principalId, payCalendarEntry);
			warnings.addAll(eligibleTransfers.get("warningMessages"));
			
			Map<String, BigDecimal> hoursToPayLabelMap = getHoursToPayDayMap(
					principalId, payEndDate, payCalendarLabels,
					timeBlocks, null, payCalendarEntry, payCalendar,
					dateTimeZone, dayIntervals);
			
			Map<String, BigDecimal> hoursToFlsaPayLabelMap = getHoursToFlsaWeekMap(
					principalId, payEndDate, payCalendarLabels,
					timeBlocks, null, payCalendarEntry, payCalendar,
					dateTimeZone, dayIntervals);

			approvalSummaryRow.setName(principalId);
			approvalSummaryRow.setPrincipalId(principalId);
			approvalSummaryRow.setPayCalendarGroup(calGroup);
			approvalSummaryRow.setDocumentId(documentId);
			approvalSummaryRow.setHoursToPayLabelMap(hoursToPayLabelMap);
			approvalSummaryRow.setHoursToFlsaPayLabelMap(hoursToFlsaPayLabelMap);
			approvalSummaryRow.setPeriodTotal(hoursToPayLabelMap
					.get("Period Total"));
			approvalSummaryRow.setLstTimeBlocks(timeBlocks);
			approvalSummaryRow.setNotes(notes);
			approvalSummaryRow.setWarnings(warnings);

			// Compare last clock log versus now and if > threshold
			// highlight entry
			ClockLog lastClockLog = TkServiceLocator.getClockLogService()
					.getLastClockLog(principalId);
			if (isSynchronousUser(principalId)) {
                approvalSummaryRow.setClockStatusMessage(createLabelForLastClockLog(lastClockLog));
            }
			if (lastClockLog != null
					&& (StringUtils.equals(lastClockLog.getClockAction(),
							TkConstants.CLOCK_IN) || StringUtils
							.equals(lastClockLog.getClockAction(),
									TkConstants.LUNCH_IN))) {
				DateTime startTime = lastClockLog.getClockDateTime();
				DateTime endTime = new DateTime();

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

    private boolean isSynchronousUser(String principalId) {
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());
        boolean isSynchronousUser = false;
        if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment assignment : assignments) {
                TimeCollectionRule tcr = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getDept(), assignment.getWorkArea(), LocalDate.now());
                isSynchronousUser |= (tcr == null || tcr.isClockUserFl());
            }
        }
        return isSynchronousUser;
    }

	public List<TimesheetDocumentHeader> getDocumentHeadersByPrincipalIds(
			DateTime payBeginDate, DateTime payEndDate, List<String> principalIds) {
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
	public List<String> getPayCalendarLabelsForApprovalTab(DateTime payBeginDate,
			DateTime payEndDate) {
		// :)
		// http://stackoverflow.com/questions/111933/why-shouldnt-i-use-hungarian-notation
		List<String> lstPayCalendarLabels = new ArrayList<String>();
		DateTime currTime = payBeginDate;
		int dayCounter = 1;
		int weekCounter = 1;

		while (currTime.isBefore(payEndDate)) {
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

	private Map<String, Set<String>> findWarnings(String principalId, CalendarEntry calendarEntry) {
//      List<String> warnings = LeaveCalendarValidationUtil.getWarningMessagesForLeaveBlocks(leaveBlocks);
      Map<String, Set<String>> allMessages = new HashMap<String,Set<String>>();
      allMessages.put("warningMessages", new HashSet<String>());

      Map<String, Set<LeaveBlock>> eligibilities;

  	 eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry, principalId);

      if (eligibilities != null) {
          for (Entry<String,Set<LeaveBlock>> entry : eligibilities.entrySet()) {
        	  for(LeaveBlock lb : entry.getValue()) {
        		  AccrualCategoryRule rule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
        		  if (rule != null) {
        			  AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(rule.getLmAccrualCategoryId());
        			  if (rule.getActionAtMaxBalance().equals(HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER)) {
        				  //Todo: add link to balance transfer
        				  allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");   //warningMessages
        			  } else if (rule.getActionAtMaxBalance().equals(HrConstants.ACTION_AT_MAX_BALANCE.LOSE)) {
        				  //Todo: compute and display amount of time lost.
        				  allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");      //warningMessages
        			  } else if (rule.getActionAtMaxBalance().equals(HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT)) {
        				  //Todo: display payout details.
        				  allMessages.get("warningMessages").add("Accrual Category '" + accrualCategory.getAccrualCategory() + "' is over max balance.");      //warningMessages            				  
        			  }
        		  }
        	  }
          }
      }
      
      return allMessages;
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
		String dateTime = sdf.format(cl.getClockTimestamp());
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
					hours.add(HrConstants.BIG_DECIMAL_SCALED_ZERO);
				}

				// Add time from time block to existing time.
				BigDecimal timeToAdd = hours.get(day);
				timeToAdd = timeToAdd.add(block.getHours(),
						HrConstants.MATH_CONTEXT);
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
		for (Entry<String, List<BigDecimal>> entry : assignmentToHours.entrySet()) {
			// for every Assignment
			Map<String, BigDecimal> hoursToPayLabelMap = new LinkedHashMap<String, BigDecimal>();
			List<BigDecimal> dayTotals = entry.getValue();
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
					BigDecimal dayTotal = HrConstants.BIG_DECIMAL_SCALED_ZERO;
					if (dayCount < dayTotals.size())
						dayTotal = dayTotals.get(dayCount);

					hoursToPayLabelMap.put(payCalendarLabel, dayTotal);
					weekTotal = weekTotal.add(dayTotal,
							HrConstants.MATH_CONTEXT);
					periodTotal = periodTotal.add(dayTotal);
					dayCount++;
				}
			}
			payHourTotals.put(entry.getKey(), hoursToPayLabelMap);
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
	 */
	@Override
	public Map<String, BigDecimal> getHoursToPayDayMap(String principalId,
			DateTime payEndDate, List<String> payCalendarLabels,
			List<TimeBlock> lstTimeBlocks, Long workArea,
			CalendarEntry payCalendarEntry, Calendar payCalendar,
			DateTimeZone dateTimeZone, List<Interval> dayIntervals) {
		Map<String, BigDecimal> hoursToPayLabelMap = new LinkedHashMap<String, BigDecimal>();
		List<BigDecimal> dayTotals = new ArrayList<BigDecimal>();

		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(
				lstTimeBlocks, payCalendarEntry, payCalendar, true,
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
									HrConstants.MATH_CONTEXT);
						} else {
							total = total.add(new BigDecimal("0"),
									HrConstants.MATH_CONTEXT);
						}
					} else {
						total = total.add(tb.getHours(),
								HrConstants.MATH_CONTEXT);
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
							HrConstants.MATH_CONTEXT);
					periodTotal = periodTotal.add(dayTotals.get(dayCount));
					dayCount++;
				}

			}

		}
		return hoursToPayLabelMap;
	}
	
	/**
	 * Aggregate TimeBlocks to hours per day and sum for flsa week (including previous/next weeks)
	 */
	@Override
	public Map<String, BigDecimal> getHoursToFlsaWeekMap(String principalId, 
			DateTime payEndDate, List<String> payCalendarLabels, 
			List<TimeBlock> lstTimeBlocks, Long workArea, 
			CalendarEntry payCalendarEntry, Calendar payCalendar,
			DateTimeZone dateTimeZone, List<Interval> dayIntervals) {
		
		Map<String, BigDecimal> hoursToFlsaWeekMap = new LinkedHashMap<String, BigDecimal>();

		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(lstTimeBlocks, payCalendarEntry, payCalendar, true, dayIntervals);
		List<List<FlsaWeek>> flsaWeeks = tkTimeBlockAggregate.getFlsaWeeks(dateTimeZone, principalId);
		
		int weekCount = 1;
		for (List<FlsaWeek> flsaWeekParts : flsaWeeks) {
			BigDecimal weekTotal = new BigDecimal(0.00);
			for (FlsaWeek flsaWeekPart : flsaWeekParts) {
				for (FlsaDay flsaDay : flsaWeekPart.getFlsaDays()) {
					for (TimeBlock timeBlock : flsaDay.getAppliedTimeBlocks()) {
						if (workArea != null) {
							if (timeBlock.getWorkArea().compareTo(workArea) == 0) {
								weekTotal = weekTotal.add(timeBlock.getHours(), HrConstants.MATH_CONTEXT);
							} else {
								weekTotal = weekTotal.add(new BigDecimal("0"), HrConstants.MATH_CONTEXT);
							}
						} else {
							weekTotal = weekTotal.add(timeBlock.getHours(),HrConstants.MATH_CONTEXT);
						}
					}
				}
			}
			hoursToFlsaWeekMap.put("Week " + weekCount++, weekTotal);
		}
		
		return hoursToFlsaWeekMap;
	}

	public boolean doesApproverHavePrincipalsForCalendarGroup(LocalDate asOfDate, String calGroup) {
    	Set<Long> workAreas = new HashSet<Long>();
    	workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(GlobalVariables.getUserSession().getPrincipalId(), KPMERole.APPROVER.getRoleName(), new DateTime(), true));
        workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(GlobalVariables.getUserSession().getPrincipalId(), KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

		for (Long workArea : workAreas) {
			List<Assignment> assignments = HrServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(
							workArea, asOfDate);
			List<String> principalIds = new ArrayList<String>();
			for (Assignment assign : assignments) {
				if (principalIds.contains(assign.getPrincipalId())) {
					continue;
				}
				principalIds.add(assign.getPrincipalId());
			}

			for (String principalId : principalIds) {
				PrincipalHRAttributes principalCal = HrServiceLocator
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

    @Override
	public List<Note> getNotesForDocument(String documentNumber) {
        return KewApiServiceLocator.getNoteService().getNotes(documentNumber);
	}

    @Override
    public List<String> getTimePrincipalIdsWithSearchCriteria(List<String> workAreaList, String calendarGroup, LocalDate effdt, LocalDate beginDate, LocalDate endDate) {
    	if (CollectionUtils.isEmpty(workAreaList)) {
    		return new ArrayList<String>();
  	    }
  		List<Assignment> assignmentList = HrServiceLocator.getAssignmentService().getAssignments(workAreaList, effdt, beginDate, endDate);
  		List<Assignment> tempList = this.removeNoTimeAssignment(assignmentList);
  		Set<String> pids = new HashSet<String>();
        for(Assignment anAssignment : tempList) {
        	if(anAssignment != null) {
        		pids.add(anAssignment.getPrincipalId());
         	}
        }
        List<String> ids = new ArrayList<String>();
        ids.addAll(pids);
  		
  		if(CollectionUtils.isEmpty(ids)) {
  			return new ArrayList<String>();
  		}
  		// use unique principalIds and selected calendarGroup to get unique ids from principalHRAttributes table
  		List<String> idList = HrServiceLocator.getPrincipalHRAttributeService()
  				.getActiveEmployeesIdForTimeCalendarAndIdList(calendarGroup, ids, endDate); 
  		if(CollectionUtils.isEmpty(idList)) {
  			return new ArrayList<String>();
  		}
  		return idList;
    }
    
    private List<Assignment> removeNoTimeAssignment(List<Assignment> assignmentList) {
    	List<Assignment> results = new ArrayList<Assignment>();
		if(CollectionUtils.isNotEmpty(assignmentList)) {
	     	for(Assignment anAssignment: assignmentList) {
     			if(anAssignment != null 
		    			&& anAssignment.getJob() != null 
		    			&& anAssignment.getJob().getFlsaStatus() != null 
		    			&& anAssignment.getJob().getFlsaStatus().equalsIgnoreCase(HrConstants.FLSA_STATUS_NON_EXEMPT)) {
     				results.add(anAssignment);	
     			}
	        }
	    }
		return results;
	}
    
	@Override
	public Map<String, TimesheetDocumentHeader> getPrincipalDocumentHeader(
			List<String> principalIds, DateTime payBeginDate, DateTime payEndDate) {
		Map<String, TimesheetDocumentHeader> principalDocumentHeader = new LinkedHashMap<String, TimesheetDocumentHeader>();
		for (String principalId : principalIds) {
			
			TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payBeginDate, payEndDate.plusMillis(1));
			if(tdh != null) {
				principalDocumentHeader.put(principalId, tdh);	
			}
		}
		return principalDocumentHeader;
	}

	public DocumentRouteHeaderValue getRouteHeader(String documentId) {
		return KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentId);
	}
	
	@Override
	public List<CalendarEntry> getAllPayCalendarEntriesForApprover(String principalId, LocalDate currentDate) {
		Set<String> principals = new HashSet<String>();
		
    	Set<Long> workAreas = new HashSet<Long>();
    	workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));
        workAreas.addAll(HrServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));

		// Get all of the principals within our window of time.
		for (Long waNum : workAreas) {
			List<Assignment> assignments = HrServiceLocator
					.getAssignmentService().getActiveAssignmentsForWorkArea(waNum, currentDate);

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
		Set<CalendarEntry> payPeriodSet = new HashSet<CalendarEntry>();
		for(TimesheetDocumentHeader tdh : documentHeaders) {
            CalendarEntry pe = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdh.getDocumentId()).getCalendarEntry();
    		if(pe != null) {
    			payPeriodSet.add(pe);
    		}
        }
		List<CalendarEntry> ppList = new ArrayList<CalendarEntry>(payPeriodSet);
        
		return ppList;
	}
	
}
