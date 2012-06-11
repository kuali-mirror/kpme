package org.kuali.hr.time.timesummary.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
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

import java.math.BigDecimal;
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

		timeSummary.setSummaryHeader(getHeaderForSummary(timesheetDocument.getPayCalendarEntry(), dayArrangements));
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(timesheetDocument.getTimeBlocks(), timesheetDocument.getPayCalendarEntry(), TkServiceLocator.getCalendarSerivce().getCalendar(timesheetDocument.getPayCalendarEntry().getHrCalendarId()), true);
		timeSummary.setWorkedHours(getWorkedHours(tkTimeBlockAggregate));

        List<EarnGroupSection> earnGroupSections = getEarnGroupSections(tkTimeBlockAggregate, timeSummary.getSummaryHeader().size()+1, dayArrangements, timesheetDocument.getAsOfDate());
        timeSummary.setSections(earnGroupSections);

		return timeSummary;
	}
	
    /**
     * Aggregates timeblocks into the appropriate earngroup-> earncode -> assignment rows
     * @param tkTimeBlockAggregate
     * @param numEntries
     * @param dayArrangements
     * @param asOfDate
     * @return
     */
	public List<EarnGroupSection> getEarnGroupSections(TkTimeBlockAggregate tkTimeBlockAggregate, int numEntries, List<Boolean> dayArrangements, Date asOfDate ){
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
								//TODO push this up to the assignment fetch/fully populated instead of like this
								if(assignment != null){
									if(assignment.getJob() == null){
										assignment.setJob(TkServiceLocator.getJobSerivce().getJob(assignment.getPrincipalId(),assignment.getJobNumber(),TKUtils.getTimelessDate(asOfDate)));
									}
									if(assignment.getWorkAreaObj() == null){
										assignment.setWorkAreaObj(TkServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), TKUtils.getTimelessDate(asOfDate)));
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
			EarnGroup earnGroupObj = TkServiceLocator.getEarnGroupService().getEarnGroupSummaryForEarnCode(earnCode, TKUtils.getTimelessDate(asOfDate));
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
        StringBuilder display = new StringBuilder(currentDate.toString(TkConstants.DT_ABBREV_DATE_FORMAT));

        if (virtualDays) {
            LocalDateTime nextDay = currentDate.plusDays(1);
            display.append(" - ");
            display.append(nextDay.toString(TkConstants.DT_ABBREV_DATE_FORMAT));
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
            cal = TkServiceLocator.getCalendarSerivce().getCalendar(calEntry.getHrCalendarId());
        }

        return cal;
    }

}
