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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.calendar.web.CalendarDay;
import org.kuali.kpme.core.calendar.web.CalendarWeek;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.validation.LeaveCalendarValidationUtil;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.calendar.TkCalendar;
import org.kuali.kpme.tklm.time.calendar.TkCalendarDay;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.web.TimeBlockRenderer;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailRenderer;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class TimeApproveServiceImpl implements TimeApproveService {

	@Override
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(String calGroup, List<String> principalIds, List<String> payCalendarLabels, CalendarEntry payCalendarEntry) {
		DateTime payBeginDate = payCalendarEntry.getBeginPeriodFullDateTime();
		DateTime payEndDate = payCalendarEntry.getEndPeriodFullDateTime();
		
		List<Map<String, Object>> timeBlockJsonMap = new ArrayList<Map<String,Object>>();
		List<ApprovalTimeSummaryRow> rows = new LinkedList<ApprovalTimeSummaryRow>();
		Map<String, TimesheetDocumentHeader> principalDocumentHeader = getPrincipalDocumentHeader(
                principalIds, payBeginDate, payEndDate);

		Calendar payCalendar = HrServiceLocator.getCalendarService()
				.getCalendar(payCalendarEntry.getHrCalendarId());
		DateTimeZone dateTimeZone = HrServiceLocator.getTimezoneService()
				.getUserTimezoneWithFallback();
		List<Interval> dayIntervals = TKUtils
				.getDaySpanForCalendarEntry(payCalendarEntry);

		
		String color = null;
		
		Map<String, String> userColorMap = new HashMap<String, String>();
		Set<String> randomColors = new HashSet<String>();
		
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
                warnings = HrServiceLocator.getEarnCodeGroupService().getWarningTextFromEarnCodeGroups(earnCodeMap);
                
                // Get Timesheet blocks
                
    	       
                List<Interval> intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry);
                TkTimeBlockAggregate tbAggregate = new TkTimeBlockAggregate(timeBlocks, payCalendarEntry, payCalendar, true,intervals);
                // use both time aggregate to populate the calendar
                TkCalendar cal = TkCalendar.getCalendar(tbAggregate);
                
                
                for (CalendarWeek week : cal.getWeeks()) {
                	for(CalendarDay day : week.getDays()) {
                		TkCalendarDay tkDay = (TkCalendarDay) day;
                		for (TimeBlockRenderer renderer : tkDay.getBlockRenderers()) {
                			Map<String, Object> timeBlockMap = new HashMap<String, Object>();
                			
                			// set title..
                			StringBuffer title = new StringBuffer();
                			if(!renderer.getEarnCodeType().equalsIgnoreCase(HrConstants.EARN_CODE_AMOUNT)) {
	                			if(renderer.getDetailRenderers() != null && !renderer.getDetailRenderers().isEmpty()) {
	                				for(TimeHourDetailRenderer thdr : renderer.getDetailRenderers()) {
	                					title.append("\n");
	                					title = new StringBuffer(thdr.getTitle());
	                					title.append(" - "+thdr.getHours());
	                				}
	                			}
                			}
                			
                			timeBlockMap.put("start", tkDay.getDateString());
                			timeBlockMap.put("title", renderer.getTitle() + "\n" +renderer.getTimeRange() + "\n" + title.toString());
                			timeBlockMap.put("id", tkDay.getDayNumberString());
                			if(!userColorMap.containsKey(principalId)) {
		    	        		color = TKUtils.getRandomColor(randomColors);
		    	        		randomColors.add(color);
		    	        		userColorMap.put(principalId, color);
		    	        	}
		    	        	color = userColorMap.get(principalId);
		    	        	timeBlockMap.put("color", userColorMap.get(principalId));
		    	        	timeBlockMap.put("className", "event-approval");
                			timeBlockJsonMap.add(timeBlockMap);
                		}
                	}
                }
                
				warnings = HrServiceLocator.getEarnCodeGroupService().getWarningTextFromEarnCodeGroups(td.getEarnCodeMap());
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
			approvalSummaryRow.setColor(userColorMap.get(principalId));
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
		
		String outputString = JSONValue.toJSONString(timeBlockJsonMap);
		if(rows != null && !rows.isEmpty()) {
			rows.get(0).setOutputString(outputString);
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

	private Map<String, Set<String>> findWarnings(String principalId, CalendarEntry calendarEntry) {
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

	@Override
	public DocumentRouteHeaderValue getRouteHeader(String documentId) {
		return KEWServiceLocator.getRouteHeaderService().getRouteHeader(documentId);
	}
	
}