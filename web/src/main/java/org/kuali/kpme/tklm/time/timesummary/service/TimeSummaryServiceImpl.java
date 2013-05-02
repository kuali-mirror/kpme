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
package org.kuali.kpme.tklm.time.timesummary.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockAggregate;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.time.flsa.FlsaDay;
import org.kuali.kpme.tklm.time.flsa.FlsaWeek;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeHourDetail;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesummary.AssignmentRow;
import org.kuali.kpme.tklm.time.timesummary.EarnCodeSection;
import org.kuali.kpme.tklm.time.timesummary.EarnGroupSection;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;

public class TimeSummaryServiceImpl implements TimeSummaryService {
	private static final String OTHER_EARN_GROUP = "Other";
	private static final Logger LOG = Logger.getLogger(TimeSummaryServiceImpl.class);
	
    @Override
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument) {
		TimeSummary timeSummary = new TimeSummary();

		if(timesheetDocument.getTimeBlocks() == null) {
			return timeSummary;
		}

        List<Boolean> dayArrangements = new ArrayList<Boolean>();

		timeSummary.setSummaryHeader(getHeaderForSummary(timesheetDocument.getCalendarEntry(), dayArrangements));
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(timesheetDocument.getTimeBlocks(), timesheetDocument.getCalendarEntry(), HrServiceLocator.getCalendarService().getCalendar(timesheetDocument.getCalendarEntry().getHrCalendarId()), true);

        List<Assignment> timeAssignments = timesheetDocument.getAssignments();
        List<String> tAssignmentKeys = new ArrayList<String>();
        for(Assignment assign : timeAssignments) {
            tAssignmentKeys.add(assign.getAssignmentKey());
        }
        List<LeaveBlock> leaveBlocks =  LmServiceLocator.getLeaveBlockService().getLeaveBlocksForTimeCalendar(timesheetDocument.getPrincipalId(),
                timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate(), timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate(), tAssignmentKeys);
        LeaveBlockAggregate leaveBlockAggregate = new LeaveBlockAggregate(leaveBlocks, timesheetDocument.getCalendarEntry());
        tkTimeBlockAggregate = combineTimeAndLeaveAggregates(tkTimeBlockAggregate, leaveBlockAggregate);

		timeSummary.setWorkedHours(getWorkedHours(tkTimeBlockAggregate));

        List<EarnGroupSection> earnGroupSections = getEarnGroupSections(tkTimeBlockAggregate, timeSummary.getSummaryHeader().size()+1, 
        			dayArrangements, timesheetDocument.getAsOfDate(), timesheetDocument.getDocEndDate());
        timeSummary.setSections(earnGroupSections);
        
        try {
			List<LeaveSummaryRow> maxedLeaveRows = getMaxedLeaveRows(timesheetDocument.getCalendarEntry(),timesheetDocument.getPrincipalId());
			timeSummary.setMaxedLeaveRows(maxedLeaveRows);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("error retreiving maxed leave rows", e);
		}

		return timeSummary;
	}
	
    private List<LeaveSummaryRow> getMaxedLeaveRows(
			CalendarEntry calendarEntry, String principalId) throws Exception {
    	List<LeaveSummaryRow> maxedLeaveRows = new ArrayList<LeaveSummaryRow>();
    	
    	if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT, true)) {
    		
        	Map<String,Set<LeaveBlock>> eligibilities = LmServiceLocator.getAccrualCategoryMaxBalanceService().getMaxBalanceViolations(calendarEntry,principalId);
        	Set<LeaveBlock> onDemandTransfers = eligibilities.get(HrConstants.MAX_BAL_ACTION_FREQ.ON_DEMAND);

        	Interval calendarEntryInterval = new Interval(calendarEntry.getBeginPeriodDate().getTime(),calendarEntry.getEndPeriodDate().getTime());
        	
        	//use the current date if on the current calendar? yes -> no warning given until accrual is reached. If accrual occurs on last day of period or last day of service interval
        	//change, no warning given to the employee of balance limits being exceeded except on or after that day.

        	if(!onDemandTransfers.isEmpty()) {
            	for(LeaveBlock lb : onDemandTransfers) {
            		LocalDate leaveDate = lb.getLeaveLocalDate();
                	LeaveSummary summary = LmServiceLocator.getLeaveSummaryService().getLeaveSummaryAsOfDate(principalId, leaveDate.plusDays(1));
                	LeaveSummaryRow row = summary.getLeaveSummaryRowForAccrualCtgy(lb.getAccrualCategory());
            		if(row != null) {
            			//AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(row.getAccrualCategoryId());
                    	//AccrualCategoryRule currentRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, asOfDate, pha.getServiceDate());
                    	if(calendarEntryInterval.contains(leaveDate.toDate().getTime())) {
                    		//do not allow the on-demand max balance action if the rule the action occurs under is no longer in effect,
                    		//or if the infraction did not occur within this interval. ( if it occurred during the previous interval, 
                    		//the employee will have the option to take action in that interval up to & including the end date of that interval. )
	            			row.setInfractingLeaveBlockId(lb.getAccrualCategoryRuleId());
	            			AccrualCategoryRule aRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRule(lb.getAccrualCategoryRuleId());
	            			
	            			if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER))
	            				row.setTransferable(true);
	            			else if(StringUtils.equals(aRule.getActionAtMaxBalance(),HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT))
	            				row.setPayoutable(true);
	            			
	            			boolean exists = false;
	            			for(LeaveSummaryRow maxedRow : maxedLeaveRows) {
	            				if(StringUtils.equals(maxedRow.getAccrualCategoryId(),row.getAccrualCategoryId()))
	            					exists = true;
	            			}
	            			if(!exists)
            					maxedLeaveRows.add(row);
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
	public List<EarnGroupSection> getEarnGroupSections(TkTimeBlockAggregate tkTimeBlockAggregate, int numEntries, List<Boolean> dayArrangements, LocalDate asOfDate, LocalDate docEndDate){
		List<EarnGroupSection> earnGroupSections = new ArrayList<EarnGroupSection>();
		List<FlsaWeek> flsaWeeks = tkTimeBlockAggregate.getFlsaWeeks(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
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
								EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(thd.getEarnCode(), asOfDate);
								earnCodeSection.setDescription(earnCodeObj.getDescription());
								earnCodeSection.setIsAmountEarnCode((earnCodeObj.getRecordMethod()!= null && earnCodeObj.getRecordMethod().equalsIgnoreCase(HrConstants.EARN_CODE_AMOUNT)) ? true : false);
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
									assignment = HrServiceLocator.getAssignmentService().getAssignment(timeBlock.getPrincipalId(), assignmentKey, asOfDate);
								}
								//TODO push this up to the assignment fetch/fully populated instead of like this
								if(assignment != null){
									if(assignment.getJob() == null){
										Job aJob = HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(),assignment.getJobNumber(), assignment.getEffectiveLocalDate());
										assignment.setJob(aJob);
									}
									if(assignment.getWorkAreaObj() == null){
										WorkArea aWorkArea = HrServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), assignment.getEffectiveLocalDate());
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
	protected List<String> getSummaryHeader(CalendarEntry payCalEntry){
		List<String> summaryHeader = new ArrayList<String>();
		int dayCount = 0;
		Date beginDateTime = payCalEntry.getBeginPeriodDateTime();
		Date endDateTime = payCalEntry.getEndPeriodDateTime();
		boolean virtualDays = false;
        LocalDateTime endDate = payCalEntry.getEndPeriodLocalDateTime();

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
    private List<BigDecimal> getWorkedHours(TkTimeBlockAggregate aggregate) {
        List<BigDecimal> hours = new ArrayList<BigDecimal>();
        BigDecimal periodTotal = HrConstants.BIG_DECIMAL_SCALED_ZERO;
        for (FlsaWeek week : aggregate.getFlsaWeeks(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback())) {
            BigDecimal weeklyTotal = HrConstants.BIG_DECIMAL_SCALED_ZERO;
            for (FlsaDay day : week.getFlsaDays()) {
                BigDecimal totalForDay = HrConstants.BIG_DECIMAL_SCALED_ZERO;
                for (TimeBlock block : day.getAppliedTimeBlocks()) {
                    totalForDay = totalForDay.add(block.getHours(), HrConstants.MATH_CONTEXT);
                    weeklyTotal = weeklyTotal.add(block.getHours(), HrConstants.MATH_CONTEXT);
                    periodTotal = periodTotal.add(block.getHours(), HrConstants.MATH_CONTEXT);
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
    public List<String> getHeaderForSummary(CalendarEntry cal, List<Boolean> dayArrangements) {
        List<String> header = new ArrayList<String>();

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
    private Calendar getPayCalendarForEntry(CalendarEntry calEntry) {
        Calendar cal = null;

        if (calEntry != null) {
            cal = HrServiceLocator.getCalendarService().getCalendar(calEntry.getHrCalendarId());
        }

        return cal;
    }

}
