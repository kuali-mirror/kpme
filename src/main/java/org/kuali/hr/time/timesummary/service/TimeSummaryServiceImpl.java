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
package org.kuali.hr.time.timesummary.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.util.LeaveBlockAggregate;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnCodeSection;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workarea.WorkArea;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class TimeSummaryServiceImpl implements TimeSummaryService {
	private static final String OTHER_EARN_GROUP = "Other";

    @Override
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument) {
		TimeSummary timeSummary = new TimeSummary();

		if(timesheetDocument.getTimeBlocks() == null) {
			return timeSummary;
		}

        List<Boolean> dayArrangements = new ArrayList<Boolean>();

		timeSummary.setSummaryHeader(getHeaderForSummary(timesheetDocument.getCalendarEntry(), dayArrangements));
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(timesheetDocument.getTimeBlocks(), timesheetDocument.getCalendarEntry(), TkServiceLocator.getCalendarService().getCalendar(timesheetDocument.getCalendarEntry().getHrCalendarId()), true);

        List<Assignment> timeAssignments = timesheetDocument.getAssignments();
        List<String> tAssignmentKeys = new ArrayList<String>();
        for(Assignment assign : timeAssignments) {
            tAssignmentKeys.add(assign.getAssignmentKey());
        }
        List<LeaveBlock> leaveBlocks =  TkServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(timesheetDocument.getPrincipalId(),
                timesheetDocument.getCalendarEntry().getBeginPeriodDate(), timesheetDocument.getCalendarEntry().getEndPeriodDate(), tAssignmentKeys);
        LeaveBlockAggregate leaveBlockAggregate = new LeaveBlockAggregate(leaveBlocks, timesheetDocument.getCalendarEntry());
        tkTimeBlockAggregate = combineTimeAndLeaveAggregates(tkTimeBlockAggregate, leaveBlockAggregate);

		timeSummary.setWorkedHours(getWorkedHours(tkTimeBlockAggregate, leaveBlockAggregate));

        List<EarnGroupSection> earnGroupSections = getEarnGroupSections(tkTimeBlockAggregate, timeSummary.getSummaryHeader().size()+1, 
        			dayArrangements, timesheetDocument.getAsOfDate(), timesheetDocument.getDocEndDate());
        timeSummary.setSections(earnGroupSections);
        
        try {
			List<LeaveSummaryRow> maxedLeaveRows = getMaxedLeaveRows(timesheetDocument.getCalendarEntry(),timesheetDocument.getPrincipalId());
			timeSummary.setMaxedLeaveRows(maxedLeaveRows);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timeSummary;
	}
	
    private List<LeaveSummaryRow> getMaxedLeaveRows(
			CalendarEntries calendarEntry, String principalId) throws Exception {
    	List<LeaveSummaryRow> maxedLeaveRows = new ArrayList<LeaveSummaryRow>();
    	if (TkServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, TkConstants.FLSA_STATUS_NON_EXEMPT, true)) {
        	Map<String,ArrayList<String>> eligibilities = TkServiceLocator.getBalanceTransferService().getEligibleTransfers(calendarEntry,principalId);
        	Map<String,ArrayList<String>> payouts = TkServiceLocator.getLeavePayoutService().getEligiblePayouts(calendarEntry, principalId);
        	List<String> onDemandTransfers = eligibilities.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND);
        	onDemandTransfers.addAll(payouts.get(LMConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND));
        	if(!onDemandTransfers.isEmpty()) {
            	LeaveSummary summary = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(principalId, calendarEntry);
            	for(LeaveSummaryRow row : summary.getLeaveSummaryRows()) {
            		if(onDemandTransfers.contains(row.getAccrualCategoryRuleId()))
            			maxedLeaveRows.add(row);
            			
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
	public List<EarnGroupSection> getEarnGroupSections(TkTimeBlockAggregate tkTimeBlockAggregate, int numEntries, List<Boolean> dayArrangements, Date asOfDate , Date docEndDate){
		List<EarnGroupSection> earnGroupSections = new ArrayList<EarnGroupSection>();
		List<FlsaWeek> flsaWeeks = tkTimeBlockAggregate.getFlsaWeeks(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
		Map<String, EarnCodeSection> earnCodeToEarnCodeSection = new HashMap<String, EarnCodeSection>();
		Map<String, EarnGroupSection> earnGroupToEarnGroupSection = new HashMap<String, EarnGroupSection>();
		
		int dayCount = 0;
		
		//TODO remove this and correct the aggregate .. not sure what the down stream changes are
		//so leaving this for initial release
		List<FlsaWeek> trimmedFlsaWeeks = new ArrayList<FlsaWeek>();
		for(FlsaWeek flsaWeek : flsaWeeks){
			if(flsaWeek.getFlsaDays().size() > 0){
				trimmedFlsaWeeks.add(flsaWeek);
			}
		}
		
		//For every flsa week and day aggegate each time hour detail 
		// buckets it by earn code section first
		for(FlsaWeek flsaWeek : trimmedFlsaWeeks){
			int weekSize = 0;
			List<FlsaDay> flsaDays = flsaWeek.getFlsaDays();
			for(FlsaDay flsaDay : flsaDays){
				Map<String, List<TimeBlock>> earnCodeToTimeBlocks = flsaDay.getEarnCodeToTimeBlocks();
				
				for(String earnCode : earnCodeToTimeBlocks.keySet()){
					for(TimeBlock timeBlock : earnCodeToTimeBlocks.get(earnCode)){
						for(TimeHourDetail thd : timeBlock.getTimeHourDetails()){
							if(StringUtils.equals(TkConstants.LUNCH_EARN_CODE, thd.getEarnCode())){
								continue;
							}
							EarnCodeSection earnCodeSection = earnCodeToEarnCodeSection.get(thd.getEarnCode());
							if(earnCodeSection == null){
								earnCodeSection = new EarnCodeSection();
								earnCodeSection.setEarnCode(thd.getEarnCode());
								EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(thd.getEarnCode(), TKUtils.getTimelessDate(asOfDate));
								earnCodeSection.setDescription(earnCodeObj.getDescription());
								earnCodeSection.setIsAmountEarnCode((earnCodeObj.getRecordMethod()!= null && earnCodeObj.getRecordMethod().equalsIgnoreCase(TkConstants.EARN_CODE_AMOUNT)) ? true : false);
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
								AssignmentDescriptionKey assignmentKey = TkServiceLocator.getAssignmentService().getAssignmentDescriptionKey(assignKey);
								Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timeBlock.getPrincipalId(), assignmentKey, TKUtils.getTimelessDate(asOfDate));
								// some assignment may not be effective at the beginning of the pay period, use the end date of the period to find it
								if(assignment == null) {
									assignment = TkServiceLocator.getAssignmentService().getAssignment(timeBlock.getPrincipalId(), assignmentKey, TKUtils.getTimelessDate(docEndDate));
								}
								//TODO push this up to the assignment fetch/fully populated instead of like this
								if(assignment != null){
									if(assignment.getJob() == null){
										Job aJob = TkServiceLocator.getJobService().getJob(assignment.getPrincipalId(),assignment.getJobNumber(),TKUtils.getTimelessDate(assignment.getEffectiveDate()));
										assignment.setJob(aJob);
									}
									if(assignment.getWorkAreaObj() == null){
										WorkArea aWorkArea = TkServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), TKUtils.getTimelessDate(assignment.getEffectiveDate()));
										assignment.setWorkAreaObj(aWorkArea);
									}
									assignRow.setDescr(assignment.getAssignmentDescription());
								}
								for(int i = 0;i<(numEntries-1);i++){
									assignRow.getTotal().add(BigDecimal.ZERO);
									assignRow.getAmount().add(BigDecimal.ZERO);
								}
								assignRow.setEarnCodeSection(earnCodeSection);
								earnCodeSection.addAssignmentRow(assignRow);
							}
							assignRow.addToTotal(dayCount, thd.getHours());
							assignRow.addToAmount(dayCount, thd.getAmount());
						}
					}
				}
				dayCount++;
				weekSize++;
			}
			//end of flsa week accumulate weekly totals
			for(EarnCodeSection earnCodeSection : earnCodeToEarnCodeSection.values()){
				earnCodeSection.addWeeklyTotal(dayCount, weekSize);
			}			
			weekSize = 0;

			dayCount++;
		}
		
		dayCount = 0;
		//now create all teh earn group sections and aggregate accordingly
		for(EarnCodeSection earnCodeSection : earnCodeToEarnCodeSection.values()){
			String earnCode = earnCodeSection.getEarnCode();
			EarnCodeGroup earnGroupObj = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroupSummaryForEarnCode(earnCode, TKUtils.getTimelessDate(asOfDate));
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
				for(int i =0;i<(numEntries-1);i++){
					earnGroupSection.getTotals().add(BigDecimal.ZERO);
				}
				earnGroupToEarnGroupSection.put(earnGroup, earnGroupSection);
			}
			earnGroupSection.addEarnCodeSection(earnCodeSection, dayArrangements);
			
		}
		for(EarnGroupSection earnGroupSection : earnGroupToEarnGroupSection.values()){
			earnGroupSections.add(earnGroupSection);
		}
		return earnGroupSections;
	}
	
	/**
	 * Generate a list of string describing this pay calendar entry for the summary
	 * @param payCalEntry
	 * @return
	 */
	protected List<String> getSummaryHeader(CalendarEntries payCalEntry){
		List<String> summaryHeader = new ArrayList<String>();
		int dayCount = 0;
		Date beginDateTime = payCalEntry.getBeginPeriodDateTime();
		Date endDateTime = payCalEntry.getEndPeriodDateTime();
		boolean virtualDays = false;
        LocalDateTime endDate = payCalEntry.getEndLocalDateTime();

        if (endDate.get(DateTimeFieldType.hourOfDay()) != 0 || endDate.get(DateTimeFieldType.minuteOfHour()) != 0 ||
                endDate.get(DateTimeFieldType.secondOfMinute()) != 0){
            virtualDays = true;
        }
		
		Date currDateTime = beginDateTime;
		java.util.Calendar cal = GregorianCalendar.getInstance();
		
		while(currDateTime.before(endDateTime)){
			LocalDateTime currDate = new LocalDateTime(currDateTime);
			summaryHeader.add(makeHeaderDiplayString(currDate, virtualDays));
			
			dayCount++;
			if((dayCount % 7) == 0){
				summaryHeader.add("Week "+ ((dayCount / 7)));
			}
			cal.setTime(currDateTime);
			cal.add(java.util.Calendar.HOUR, 24);
			currDateTime = cal.getTime();
		}
		
		summaryHeader.add("Period Total");
		return summaryHeader;
	}

    //kind of a hack
    private TkTimeBlockAggregate combineTimeAndLeaveAggregates(TkTimeBlockAggregate tbAggregate, LeaveBlockAggregate lbAggregate) {
        if (tbAggregate != null
                && lbAggregate != null
                && tbAggregate.getDayTimeBlockList().size() == lbAggregate.getDayLeaveBlockList().size()) {
            for (int i = 0; i < tbAggregate.getDayTimeBlockList().size(); i++) {
                List<LeaveBlock> leaveBlocks = lbAggregate.getDayLeaveBlockList().get(i);
                if (CollectionUtils.isNotEmpty(leaveBlocks)) {
                    for (LeaveBlock lb : leaveBlocks) {
                        //convert leave block to generic time block and add to list
                        //conveniently, we only really need the hours amount
                        TimeBlock timeBlock = new TimeBlock();
                        timeBlock.setHours(lb.getLeaveAmount().negate());
                        timeBlock.setBeginTimestamp(new Timestamp(lb.getLeaveDate().getTime()));
                        timeBlock.setEndTimestamp(new Timestamp(new DateTime(lb.getLeaveDate()).plusMinutes(timeBlock.getHours().intValue()).getMillis()));
                        timeBlock.setAssignmentKey(lb.getAssignmentKey());
                        timeBlock.setEarnCode(lb.getEarnCode());
                        timeBlock.setPrincipalId(lb.getPrincipalId());
                        timeBlock.setWorkArea(lb.getWorkArea());
                        TimeHourDetail timeHourDetail = new TimeHourDetail();
                        timeHourDetail.setEarnCode(timeBlock.getEarnCode());
                        timeHourDetail.setHours(timeBlock.getHours());
                        timeHourDetail.setAmount(BigDecimal.ZERO);
                        timeBlock.addTimeHourDetail(timeHourDetail);
                        tbAggregate.getDayTimeBlockList().get(i).add(timeBlock);
                    }
                }

            }
        }
        return tbAggregate;
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
    private List<BigDecimal> getWorkedHours(TkTimeBlockAggregate aggregate, LeaveBlockAggregate lbAggregate) {
        List<BigDecimal> hours = new ArrayList<BigDecimal>();
        BigDecimal periodTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
        for (FlsaWeek week : aggregate.getFlsaWeeks(TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback())) {
            BigDecimal weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
            for (FlsaDay day : week.getFlsaDays()) {
                BigDecimal totalForDay = TkConstants.BIG_DECIMAL_SCALED_ZERO;
                for (TimeBlock block : day.getAppliedTimeBlocks()) {
                    totalForDay = totalForDay.add(block.getHours(), TkConstants.MATH_CONTEXT);
                    weeklyTotal = weeklyTotal.add(block.getHours(), TkConstants.MATH_CONTEXT);
                    periodTotal = periodTotal.add(block.getHours(), TkConstants.MATH_CONTEXT);
                }
                hours.add(totalForDay);
            }
            hours.add(weeklyTotal);
        }
        hours.add(periodTotal);

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
    public List<String> getHeaderForSummary(CalendarEntries cal, List<Boolean> dayArrangements) {
        List<String> header = new ArrayList<String>();

        // Maps directly to joda time day of week constants.
        int flsaBeginDay = this.getPayCalendarForEntry(cal).getFlsaBeginDayConstant();
        boolean virtualDays = false;
        LocalDateTime startDate = cal.getBeginLocalDateTime();
        LocalDateTime endDate = cal.getEndLocalDateTime();

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
        if (!header.get(header.size()-1).startsWith("Week")) {
            dayArrangements.add(false);
            header.add("Week " + week);
        }


        header.add("Period Total");
        dayArrangements.add(false);
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
        
        display.append("<br />");
        
        display.append(currentDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
        if (virtualDays) {
            LocalDateTime nextDate = currentDate.plusDays(1);
            display.append(" - ");
            display.append(nextDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
        }

        return display.toString();
    }

    /**
     * @param calEntry Calendar entry we are using for lookup.
     * @return The PayCalendar that owns the provided entry.
     */
    private Calendar getPayCalendarForEntry(CalendarEntries calEntry) {
        Calendar cal = null;

        if (calEntry != null) {
            cal = TkServiceLocator.getCalendarService().getCalendar(calEntry.getHrCalendarId());
        }

        return cal;
    }

}
