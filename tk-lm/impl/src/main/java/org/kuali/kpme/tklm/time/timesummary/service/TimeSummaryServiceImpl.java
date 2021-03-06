/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.timesummary.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.api.time.timesummary.TimeSummaryService;
import org.kuali.kpme.tklm.leave.block.LeaveBlockAggregate;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesummary.AssignmentColumn;
import org.kuali.kpme.tklm.time.timesummary.AssignmentRow;
import org.kuali.kpme.tklm.time.timesummary.EarnCodeSection;
import org.kuali.kpme.tklm.time.timesummary.EarnGroupSection;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TimeSummaryServiceImpl implements TimeSummaryService {
	private static final String OTHER_EARN_GROUP = "Other";
	private static final Logger LOG = Logger.getLogger(TimeSummaryServiceImpl.class);

    @Override
    public TimeSummary getTimeSummaryForDocument(String timesheetDocumentId) {
        TimesheetDocument doc = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
        return getTimeSummary(doc.getPrincipalId(), doc.getTimeBlocks(), doc.getCalendarEntry(), doc.getAssignmentMap());
    }

    @Override
    public TimeSummary getTimeSummary(String principalId, List<TimeBlock> timeBlocks, CalendarEntry calendarEntry, Map<LocalDate, List<Assignment>> assignments) {
		TimeSummary timeSummary = new TimeSummary();

		//if(principalId == null || CollectionUtils.isEmpty(timeBlocks)) { KPME-3340
		if(principalId == null || timeBlocks == null) {
			return timeSummary;
		}

        List<Boolean> dayArrangements = new ArrayList<Boolean>();

		timeSummary.setTimeSummaryHeader(getHeaderForSummary(calendarEntry, timeSummary));
		
		String userTZ = HrServiceLocator.getTimezoneService().getUserTimezone(principalId);
		DateTimeZone userTimeZone = DateTimeZone.forID(userTZ);
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, calendarEntry, HrServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId()), true, userTimeZone);

        if (userTimeZone == null) {
            userTimeZone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        }

        List<String> tAssignmentKeys = new ArrayList<String>();
        Set<Assignment> allAssignments = new HashSet<Assignment>();
        for (List<Assignment> assignmentList: assignments.values()) {
            allAssignments.addAll(assignmentList);
        }
        Set<String> regularEarnCodes = new HashSet<String>();
        for(Assignment assign : allAssignments) {
            tAssignmentKeys.add(assign.getAssignmentKey());
            regularEarnCodes.add(assign.getJob().getPayTypeObj().getRegEarnCode());
        }
        List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
        leaveBlocks.addAll(LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(principalId,
                calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), tAssignmentKeys));
        LeaveBlockAggregate leaveBlockAggregate = new LeaveBlockAggregate(leaveBlocks, calendarEntry);
        tkTimeBlockAggregate = TkTimeBlockAggregate.combineTimeAndLeaveAggregates(tkTimeBlockAggregate, leaveBlockAggregate);

		timeSummary.setWorkedHours(getWorkedHours(principalId, tkTimeBlockAggregate, regularEarnCodes, timeSummary, userTimeZone));
		
		// Set Flsa week total map
		Map<String, BigDecimal> flsaWeekTotal = getHoursToFlsaWeekMap(tkTimeBlockAggregate, principalId, null, regularEarnCodes, userTimeZone);
		timeSummary.setFlsaWeekTotalMap(flsaWeekTotal);

        Map<String,List<EarnGroupSection>> earnGroupSections = getEarnGroupSections(principalId, tkTimeBlockAggregate, timeSummary.getTimeSummaryHeader().size()+1, 
        			dayArrangements, calendarEntry.getEndPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate());
        timeSummary.setWeeklySections(earnGroupSections);
        
        for (String key : timeSummary.getWeeklySections().keySet()) {
        	timeSummary.getWeeklySections().put(key,sortEarnGroupSections(timeSummary.getWeeklySections().get(key), regularEarnCodes));
        }
        
//        timeSummary.setSections(sortEarnGroupSections(earnGroupSections, regularEarnCodes));
        
        
        Map<String, String> aMap = ActionFormUtils.buildAssignmentStyleClassMap(timeBlocks, leaveBlocks);
		// set css classes for each assignment row
        for(String week : timeSummary.getWeeklySections().keySet()) {
			for (EarnGroupSection earnGroupSection : timeSummary.getWeeklySections().get(week)) {
				for (EarnCodeSection section : earnGroupSection.getEarnCodeSections()) {
					for (AssignmentRow assignRow : section.getAssignmentsRows()) {
						String assignmentCssStyle = MapUtils.getString(aMap, assignRow.getAssignmentKey());
						assignRow.setCssClass(assignmentCssStyle);
						for (AssignmentColumn assignmentColumn : assignRow.getAssignmentColumns().values()) {
							assignmentColumn.setCssClass(assignmentCssStyle);
						}
					}
				}
			}
        }
        try {
			List<LeaveSummaryRow> maxedLeaveRows = getMaxedLeaveRows(calendarEntry,principalId);
			timeSummary.setMaxedLeaveRows(maxedLeaveRows);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("error retreiving maxed leave rows", e);
		}

		return timeSummary;
	}
    
    private List<EarnGroupSection> sortEarnGroupSections(List<EarnGroupSection> sections, Set<String> regularEarnCodes) {
    	List<EarnGroupSection> sortedList = new ArrayList<EarnGroupSection>();
    	//first sort by alpha
    	Collections.sort(sections, new Comparator<EarnGroupSection>() {
    		@Override
    		public int compare(EarnGroupSection egs1, EarnGroupSection egs2) {
    			if (egs1 == null ^ egs2 == null) {
    				return egs1 == null ? -1 : 1;
    			}
    			if (egs1 == null && egs2 == null) {
    				return 0;
    			}
    			//'other' earn group needs to be last.
    			boolean isOther1 = StringUtils.equals(egs1.getEarnGroup(), OTHER_EARN_GROUP);
    			boolean isOther2 = StringUtils.equals(egs2.getEarnGroup(), OTHER_EARN_GROUP);
    			if (isOther1 ^ isOther2) {
    				return isOther1 ? 1 : -1;
    			}
    			if (isOther1 && isOther2) {
    				return 0;
    			}
    			return egs1.getEarnGroup().compareTo(egs2.getEarnGroup());
    		}
    	});

    	List<EarnGroupSection> copy = new ArrayList<EarnGroupSection>(sections);
    	//loop through in reverse
    	for (EarnGroupSection egs : copy) {
    		if (!CollectionUtils.intersection(regularEarnCodes, egs.getEarnCodeToEarnCodeSectionMap().keySet()).isEmpty()) {
    			sortedList.add(egs);
    			sections.remove(egs);
    		}
    	}
    	sortedList.addAll(sections);
    	return sortedList;
	}
	
    private List<LeaveSummaryRow> getMaxedLeaveRows(
            CalendarEntry calendarEntry, String principalId) throws Exception {
    	List<LeaveSummaryRow> maxedLeaveRows = new ArrayList<LeaveSummaryRow>();
    	
    	if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT, true)) {
    		
        	Map<String,Set<LeaveBlock>> eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry,principalId);
        	Set<? extends LeaveBlock> onDemandTransfers = eligibilities.get(HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND);

        	Interval calendarEntryInterval = new Interval(calendarEntry.getBeginPeriodFullDateTime(),calendarEntry.getEndPeriodFullDateTime());
        	
        	//use the current date if on the current calendar? yes -> no warning given until accrual is reached. If accrual occurs on last day of period or last day of service interval
        	//change, no warning given to the employee of balance limits being exceeded except on or after that day.

        	if(!onDemandTransfers.isEmpty()) {
            	for(LeaveBlock lb : onDemandTransfers) {
            		LocalDate leaveDate = lb.getLeaveLocalDate();
                	LeaveSummary summary = (LeaveSummary)LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(principalId, leaveDate.plusDays(1));
                	LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCtgy(lb.getAccrualCategory());
            		if(row != null) {
            			//AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(row.getAccrualCategoryId());
                    	//AccrualCategoryRule currentRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, asOfDate, pha.getServiceDate());
                    	if(calendarEntryInterval.contains(leaveDate.toDate().getTime())) {
                    		//do not allow the on-demand max balance action if the rule the action occurs under is no longer in effect,
                    		//or if the infraction did not occur within this interval. ( if it occurred during the previous interval, 
                    		//the employee will have the option to take action in that interval up to & including the end date of that interval. )
	            			row.setInfractingLeaveBlockId(lb.getAccrualCategoryRuleId());
	            			AccrualCategoryRuleContract aRule = lb.getAccrualCategoryRule();
	            			
	            			if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER))
	            				row.setTransferable(true);
	            			else if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT))
	            				row.setPayoutable(true);
	            			
	            			boolean exists = false;
	            			for(LeaveSummaryRow maxedRow : maxedLeaveRows) {
	            				if(StringUtils.equals(maxedRow.getAccrualCategoryId(),row.getAccrualCategoryId()))
	            					exists = true;
	            			}
	            			if(!exists) {
            					maxedLeaveRows.add(row);
                            }
                    	}
            		}
            	}
        	}
    	}
		return maxedLeaveRows;
	}

	/**
     * Aggregates timeblocks into the appropriate earngroup-> earncode -> assignment rows
     * @param tkTimeBlockAggregate
     * @param numEntries
     * @param dayArrangements
     * @param asOfDate
     * @return
     */
	public Map<String, List<EarnGroupSection>> getEarnGroupSections(String principalId, TkTimeBlockAggregate tkTimeBlockAggregate, int numEntries, List<Boolean> dayArrangements, LocalDate asOfDate, LocalDate docEndDate){
		List<EarnGroupSection> earnGroupSections = new ArrayList<EarnGroupSection>();
		Map<String, List<EarnGroupSection>> weeklyEarnGroupSections = new LinkedHashMap<String, List<EarnGroupSection>>();
		Map<String, List<EarnCodeSection>> earnCodeSections = new LinkedHashMap<String, List<EarnCodeSection>>();
		 
		String userTz =	HrServiceLocator.getTimezoneService().getUserTimezone(principalId);
		DateTimeZone userTimeZone = DateTimeZone.forID(userTz);
		List<FlsaWeek> flsaWeeks = tkTimeBlockAggregate.getFlsaWeeks(userTimeZone, DateTimeConstants.SUNDAY, true);
		Map<String, EarnCodeSection> earnCodeToEarnCodeSection = new TreeMap<String, EarnCodeSection>();
		Map<String, EarnGroupSection> earnGroupToEarnGroupSection = new HashMap<String, EarnGroupSection>();
		
		int dayCount = 0;
		
		//TODO remove this and correct the aggregate .. not sure what the down stream changes are
		//so leaving this for initial release
		List<FlsaWeek> trimmedFlsaWeeks = new ArrayList<FlsaWeek>();
		for(FlsaWeek flsaWeek : flsaWeeks){
			if(flsaWeek.getFlsaDays().size() > 0) {
				trimmedFlsaWeeks.add(flsaWeek);
			}
		}
		
		//For every flsa week and day aggegate each time hour detail 
		// buckets it by earn code section first
		int weekCount = 1; 
		for(FlsaWeek flsaWeek : trimmedFlsaWeeks){
			earnCodeToEarnCodeSection = new TreeMap<String, EarnCodeSection>();
			int weekSize = 0;
			List<FlsaDay> flsaDays = flsaWeek.getFlsaDays();
			for(FlsaDay flsaDay : flsaDays){
                LocalDateTime ld = flsaDay.getFlsaDate();
                DateTime td = flsaDay.getFlsaDate().toDateTime(userTimeZone);
                int ldDay = ld.getDayOfWeek();

				Map<String, List<TimeBlock>> earnCodeToTimeBlocks = flsaDay.getEarnCodeToTimeBlocks();
				
				for(List<TimeBlock> timeBlocks : earnCodeToTimeBlocks.values()){
					for(TimeBlock timeBlock : timeBlocks){
						for(TimeHourDetail thd : timeBlock.getTimeHourDetails()){
							if(StringUtils.equals(HrConstants.LUNCH_EARN_CODE, thd.getEarnCode())){
								continue;
							}
							EarnCodeSection earnCodeSection = earnCodeToEarnCodeSection.get(thd.getEarnCode());
							if(earnCodeSection == null){
								earnCodeSection = new EarnCodeSection();
								earnCodeSection.setEarnCode(thd.getEarnCode());
								EarnCodeContract earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(thd.getEarnCode(), asOfDate);
								earnCodeSection.setDescription(earnCodeObj != null ? earnCodeObj.getDescription() : null);
				 	 	 	 	earnCodeSection.setIsAmountEarnCode(earnCodeObj != null ? HrConstants.EARN_CODE_AMOUNT.equalsIgnoreCase(earnCodeObj.getRecordMethod()) : false);
								for(int i = 0;i<(numEntries-1);i++){
									earnCodeSection.getTotals().add(BigDecimal.ZERO);
								}
								earnCodeToEarnCodeSection.put(thd.getEarnCode(), earnCodeSection);
							}
							String assignKey = timeBlock.getAssignmentKey();
							AssignmentRow assignRow = earnCodeSection.getAssignKeyToAssignmentRowMap().get(assignKey);
							if(assignRow == null){
								assignRow = new AssignmentRow();
								assignRow.setAssignmentKey(assignKey);
								AssignmentDescriptionKey assignmentKey = HrServiceLocator.getAssignmentService().getAssignmentDescriptionKey(assignKey);
								Assignment assignment = HrServiceLocator.getAssignmentService().getAssignment(timeBlock.getPrincipalId(), assignmentKey, asOfDate);
								// some assignment may not be effective at the beginning of the pay period, use the end date of the period to find it
								if(assignment == null) {
									assignment = HrServiceLocator.getAssignmentService().getAssignment(timeBlock.getPrincipalId(), assignmentKey, docEndDate);
								}
								if(assignment != null){
									assignRow.setDescr(assignment.getAssignmentDescription());
								}
								assignRow.setEarnCodeSection(earnCodeSection);
								for (int i = 1; i < numEntries; i++) {
									AssignmentColumn assignmentColumn = new AssignmentColumn();
//									assignmentColumn.setTotal(HrConstants.BIG_DECIMAL_SCALED_ZERO);
//									assignmentColumn.setAmount(HrConstants.BIG_DECIMAL_SCALED_ZERO);
									assignRow.addAssignmentColumn(i,assignmentColumn);
								}
								earnCodeSection.addAssignmentRow(assignRow);
							}
							
							assignRow.addToTotal(td.getDayOfWeek(), thd.getHours());
							assignRow.addToAmount(td.getDayOfWeek(), thd.getAmount());

						}
					}
				}
				dayCount++;
				weekSize++;
			}
			
			weekSize = 0;
			dayCount = 0;
			earnCodeSections.put("Week "+weekCount++, new LinkedList<EarnCodeSection>(earnCodeToEarnCodeSection.values()));
		}
		
		dayCount = 0;
		//now create all teh earn group sections and aggregate accordingly
		for (String key : earnCodeSections.keySet()){
			earnGroupToEarnGroupSection = new HashMap<String, EarnGroupSection>();
			earnGroupSections = new ArrayList<EarnGroupSection>();
			List<EarnCodeSection> earnCodeToEarnCodeSections = earnCodeSections.get(key);
			for(EarnCodeSection earnCodeSection : earnCodeToEarnCodeSections){
				String earnCode = earnCodeSection.getEarnCode();
				EarnCodeGroup earnGroupObj = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroupSummaryForEarnCode(earnCode, asOfDate);
				String earnGroup = null;
				if(earnGroupObj == null){
					earnGroup = OTHER_EARN_GROUP;
				} else{
					earnGroup = earnGroupObj.getDescr();
				}
				
				EarnGroupSection earnGroupSection = earnGroupToEarnGroupSection.get(earnGroup);
				if(earnGroupSection == null){
					earnGroupSection = new EarnGroupSection();
					earnGroupSection.setEarnGroup(earnGroup);
                    earnGroupSection.setEarnGroupCode(earnGroupObj == null ? OTHER_EARN_GROUP : earnGroupObj.getEarnCodeGroup());
					for(int i =1;i<(numEntries);i++){
						earnGroupSection.getTotals().put(i,BigDecimal.ZERO);
						earnGroupSection.getTotals().get(i).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
					}
					earnGroupToEarnGroupSection.put(earnGroup, earnGroupSection);
				}
				earnGroupSection.addEarnCodeSection(earnCodeSection, dayArrangements);
				
			}
			for(EarnGroupSection earnGroupSection : earnGroupToEarnGroupSection.values()){
				earnGroupSections.add(earnGroupSection);
			}
		    weeklyEarnGroupSections.put(key, earnGroupSections);
		}
		
		return weeklyEarnGroupSections;
	}
	
	/**
	 * Generate a list of string describing this pay calendar entry for the summary
	 * @param payCalEntry
	 * @return
	 */
	protected List<String> getSummaryHeader(CalendarEntryBo payCalEntry){
		List<String> summaryHeader = new ArrayList<String>();
		int dayCount = 0;
		DateTime beginDateTime = payCalEntry.getBeginPeriodFullDateTime();
		DateTime endDateTime = payCalEntry.getEndPeriodFullDateTime();
		boolean virtualDays = false;
        LocalDateTime endDate = payCalEntry.getEndPeriodLocalDateTime();

        if (endDate.get(DateTimeFieldType.hourOfDay()) != 0 || endDate.get(DateTimeFieldType.minuteOfHour()) != 0 ||
                endDate.get(DateTimeFieldType.secondOfMinute()) != 0){
            virtualDays = true;
        }
		
        DateTime currentDateTime = beginDateTime;
		
		while(currentDateTime.isBefore(endDateTime)){
			LocalDateTime currDate = currentDateTime.toLocalDateTime();
			summaryHeader.add(makeHeaderDiplayString(currDate, virtualDays));
			
			dayCount++;
			if((dayCount % 7) == 0){
				summaryHeader.add("Week "+ ((dayCount / 7)));
			}
			currentDateTime = currentDateTime.plusDays(1);
		}
		
		summaryHeader.add("Period Total");
		return summaryHeader;
	}

    /**
     * Provides the number of hours worked for the pay period indicated in the
     * aggregate.
     *
     * @param aggregate The aggregate we are summing
     *
     * @return A list of BigDecimals containing the number of hours worked.
     * This list will line up with the header.
     */
    private List<BigDecimal> getWorkedHours(String principalId, TkTimeBlockAggregate aggregate, Set<String> regularEarnCodes, TimeSummary timeSummary,DateTimeZone timezone) {
        List<BigDecimal> hours = new ArrayList<BigDecimal>();
        Map<Integer, BigDecimal> weekHours;
        Map<Integer, Boolean> weekClockLogMap; 
        Map<String, BigDecimal> weekTotalMap = new LinkedHashMap<String, BigDecimal>();
        Map<String, Integer> weekDateToCalendarDayInt = new HashMap<String, Integer>();
        Map<String, Map<Integer, Boolean>> clockLogMap = new LinkedHashMap<String, Map<Integer, Boolean>>();
        ClockLog lastClockLog = TkServiceLocator.getClockLogService()
				.getLastClockLog(principalId);
        DateTime userClockLogTime = null;
        if(lastClockLog != null) {
    		String  approverId = HrContext.getPrincipalId();
    		String timeZoneString = HrServiceLocator.getTimezoneService().getApproverTimezone(approverId);
    		DateTimeZone approverTimeZone = StringUtils.isNotBlank(timeZoneString) ? DateTimeZone.forID(timeZoneString) : null;
    		userClockLogTime = lastClockLog.getClockDateTime().withZone(approverTimeZone);
        }
		
        BigDecimal periodTotal = HrConstants.BIG_DECIMAL_SCALED_ZERO;

        int i=0;
        int dayInt=0;
        for (FlsaWeek week : aggregate.getFlsaWeeks(timezone, DateTimeConstants.SUNDAY, true)) {
        	weekHours = new TreeMap<Integer, BigDecimal>();
        	weekClockLogMap = new TreeMap<Integer, Boolean>();
            BigDecimal weeklyTotal = HrConstants.BIG_DECIMAL_SCALED_ZERO;
            for (FlsaDay day : week.getFlsaDays()) {
                BigDecimal totalForDay = HrConstants.BIG_DECIMAL_SCALED_ZERO;
                LocalDateTime ld = day.getFlsaDate();
                int ldDay = ld.getDayOfWeek();

                // check for clocklog exists on the day.
                if(userClockLogTime != null) {
                	if(userClockLogTime.getDayOfMonth() == ld.getDayOfMonth()) {
                		weekClockLogMap.put(ldDay, Boolean.TRUE);
                	}
                }
                
                for (TimeBlock block : day.getAppliedTimeBlocks()) {
                    EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(block.getEarnCode(), block.getEndDateTime().toLocalDate());
                    if (ec != null
                            && (ec.isOvtEarnCode()
                            || regularEarnCodes.contains(ec.getEarnCode()) || ec.getCountsAsRegularPay().equals("Y"))) {
                        totalForDay = totalForDay.add(block.getHours(), HrConstants.MATH_CONTEXT);
                        weeklyTotal = weeklyTotal.add(block.getHours(), HrConstants.MATH_CONTEXT);
                        periodTotal = periodTotal.add(block.getHours(), HrConstants.MATH_CONTEXT);
                    }

                }
                weekHours.put(ldDay, totalForDay);
                weekDateToCalendarDayInt.put(("Week"+(i+1)+"_day"+ldDay), dayInt);
                dayInt++;
                hours.add(totalForDay);
            }
            i++;
            weekTotalMap.put("Week "+i, weeklyTotal);
            timeSummary.getWeeklyWorkedHours().put("Week "+i, weekHours);
            clockLogMap.put("Week "+i, weekClockLogMap);
            hours.add(weeklyTotal);
        }
        hours.add(periodTotal);
        timeSummary.setGrandTotal(periodTotal);
        timeSummary.setWeekTotalMap(weekTotalMap);
        timeSummary.setWeeklyClockLogs(clockLogMap);
        timeSummary.setWeekDateToCalendarDayInt(weekDateToCalendarDayInt);
        return hours;
    }


    /**
     * Handles the generation of the display header for the time summary.
     *
     * @param cal The PayCalendarEntries object we are using to derive information.
     * @param dayArrangements Container passed in to store the position of week / period aggregate sums
     *
     * @return An in-order string of days for this period that properly accounts
     * for FLSA week boundaries in the pay period.
     */
    @Override
    public List<String> getHeaderForSummary(CalendarEntry cal, List<Boolean> dayArrangements) {
 		List<String> header = new ArrayList<String>();
        if (cal == null) {
            return Collections.emptyList();
        }
        // Maps directly to joda time day of week constants.
        int flsaBeginDay = this.getPayCalendarForEntry(cal).getFlsaBeginDayConstant();
        boolean virtualDays = false;
        LocalDateTime startDate = cal.getBeginPeriodLocalDateTime();
        LocalDateTime endDate = cal.getEndPeriodLocalDateTime();

        // Increment end date if we are on a virtual day calendar, so that the
        // for loop can account for having the proper amount of days on the
        // summary calendar.
        if (endDate.get(DateTimeFieldType.hourOfDay()) != 0 || endDate.get(DateTimeFieldType.minuteOfHour()) != 0 ||
                endDate.get(DateTimeFieldType.secondOfMinute()) != 0)
        {
            endDate = endDate.plusDays(1);
            virtualDays = true;
        }

        boolean afterFirstDay = false;
        int week = 1;
        for (LocalDateTime currentDate = startDate; currentDate.compareTo(endDate) < 0; currentDate = currentDate.plusDays(1)) {

            if (currentDate.getDayOfWeek() == flsaBeginDay && afterFirstDay) {
                header.add("Week " + week);
                dayArrangements.add(false);
                week++;
            }

            header.add(makeHeaderDiplayString(currentDate, virtualDays));
            dayArrangements.add(true);


            afterFirstDay = true;
        }

        // We may have a very small final "week" on this pay period. For now
        // we will mark it as a week, and if someone doesn't like it, it can
        // be removed.
        if (!header.isEmpty() && !header.get(header.size() - 1).startsWith("Week")) {
            dayArrangements.add(false);
            header.add("Week " + week);
        }


        header.add("Period Total");
        dayArrangements.add(false);
        return header;
    }

    public Map<Integer, String> getHeaderForSummary(CalendarEntry cal, TimeSummary timeSummary) {
        Map<Integer, String> header = new LinkedHashMap<Integer,String>();
        Map<String, String> weekDates = new LinkedHashMap<String,String>();
        
        header.put(DateTimeConstants.SUNDAY, "Sun");
        header.put(DateTimeConstants.MONDAY, "Mon");
        header.put(DateTimeConstants.TUESDAY, "Tue");
        header.put(DateTimeConstants.WEDNESDAY, "Wed");
        header.put(DateTimeConstants.THURSDAY, "Thu");
        header.put(DateTimeConstants.FRIDAY, "Fri");
        header.put(DateTimeConstants.SATURDAY, "Sat");
        
        LocalDateTime startDate = cal.getBeginPeriodLocalDateTime();
        LocalDateTime endDate = cal.getEndPeriodLocalDateTime();

        LocalDateTime actualStartDate = cal.getBeginPeriodLocalDateTime();
        LocalDateTime actualEndDate = cal.getEndPeriodLocalDateTime();
        
        int daysToMinus = 0;
        if(DateTimeConstants.SUNDAY != startDate.getDayOfWeek()) {
        	daysToMinus = startDate.getDayOfWeek();
        }
        
        actualStartDate = startDate.minusDays(daysToMinus);
        int daysToAdd = 0;
        if(endDate.getDayOfWeek() != DateTimeConstants.SUNDAY) {
        	daysToAdd = DateTimeConstants.SATURDAY - endDate.getDayOfWeek();
        } else {
        	daysToAdd = DateTimeConstants.SATURDAY;
        }
        
        actualEndDate = endDate.plusDays(daysToAdd);
        
        // Increment end date if we are on a virtual day calendar, so that the
        // for loop can account for having the proper amount of days on the
        // summary calendar.
        if (endDate.get(DateTimeFieldType.hourOfDay()) != 0 || endDate.get(DateTimeFieldType.minuteOfHour()) != 0 ||
                endDate.get(DateTimeFieldType.secondOfMinute()) != 0)
        {
            endDate = endDate.plusDays(1);
        }

        boolean afterFirstDay = false;
        int week = 1;
        
        LocalDateTime weekStart = actualStartDate;
        LocalDateTime weekEnd = actualStartDate;
        for (LocalDateTime currentDate = actualStartDate; currentDate.compareTo(actualEndDate) < 0; currentDate = currentDate.plusDays(1)) {
        	
            if (currentDate.getDayOfWeek() == DateTimeConstants.SUNDAY && afterFirstDay) {
                StringBuilder display = new StringBuilder();
                display.append(weekStart.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
                display.append(" - ");
                display.append(weekEnd.minusDays(1).toString(TkConstants.DT_ABBREV_DATE_FORMAT));
                weekDates.put("Week "+week, display.toString());
                weekStart = currentDate;
                week++;
            }

            weekEnd = weekEnd.plusDays(1);
            afterFirstDay = true;
        }

        // We may have a very small final "week" on this pay period. For now
        // we will mark it as a week, and if someone doesn't like it, it can
        // be removed.
        if (!header.isEmpty() && !header.get(header.size() - 1).startsWith("Week")) {
        	StringBuilder display = new StringBuilder();
            display.append(weekStart.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
            display.append(" - ");
            display.append(actualEndDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
            weekDates.put("Week "+week, display.toString());
        }
        
        timeSummary.setWeekDates(weekDates);
        return header;
    }
    /**
     * Helper function to generate display text for the summary header.
     * @param currentDate The date we are generating for.
     * @param virtualDays Whether or not virtual days apply.
     * @return A string appropriate for UI display.
     */
    private String makeHeaderDiplayString(LocalDateTime currentDate, boolean virtualDays) {
        StringBuilder display = new StringBuilder();
        
        display.append(currentDate.toString("E"));
        if (virtualDays) {
        	LocalDateTime nextDate = currentDate.plusDays(1);
        	display.append(" - ");
            display.append(nextDate.toString("E"));
        }
        
//        display.append("<br />");
//        
//        display.append(currentDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
//        if (virtualDays) {
//            LocalDateTime nextDate = currentDate.plusDays(1);
//            display.append(" - ");
//            display.append(nextDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
//        }

        return display.toString();
    }

    /**
     * @param calEntry Calendar entry we are using for lookup.
     * @return The PayCalendar that owns the provided entry.
     */
    private Calendar getPayCalendarForEntry(CalendarEntry calEntry) {
        Calendar cal = null;

        if (calEntry != null) {
            cal = HrServiceLocator.getCalendarService().getCalendar(calEntry.getHrCalendarId());
        }

        return cal;
    }

    
	private Map<String, BigDecimal> getHoursToFlsaWeekMap(TkTimeBlockAggregate tkTimeBlockAggregate, String principalId, Long workArea, Set<String> regularEarnCodes, DateTimeZone timezone) {
		
		Map<String, BigDecimal> hoursToFlsaWeekMap = new LinkedHashMap<String, BigDecimal>();

		List<List<FlsaWeek>> flsaWeeks = tkTimeBlockAggregate.getFlsaWeeks(timezone, principalId);
		
		int weekCount = 1;
		for (List<FlsaWeek> flsaWeekParts : flsaWeeks) {
			BigDecimal weekTotal = new BigDecimal(0.00);
			for (FlsaWeek flsaWeekPart : flsaWeekParts) {
				for (FlsaDay flsaDay : flsaWeekPart.getFlsaDays()) {
					for (TimeBlock timeBlock : flsaDay.getAppliedTimeBlocks()) {
	                    EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), timeBlock.getEndDateTime().toLocalDate());
	                    if (ec != null
	                            && (ec.isOvtEarnCode()
	                            || regularEarnCodes.contains(ec.getEarnCode()) || ec.getCountsAsRegularPay().equals("Y"))) {
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
			}
			hoursToFlsaWeekMap.put("Week " + weekCount++, weekTotal);
		}
		
		return hoursToFlsaWeekMap;
	}
}
