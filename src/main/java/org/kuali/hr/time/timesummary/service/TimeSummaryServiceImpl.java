package org.kuali.hr.time.timesummary.service;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.math.BigDecimal;
import java.util.*;

public class TimeSummaryServiceImpl implements TimeSummaryService {
	private static final String OTHER_EARN_GROUP = "Other";
    private DateTimeZone timeZone;

    DateTimeZone getTimeZone() {
        return (timeZone != null) ? timeZone : TkConstants.SYSTEM_DATE_TIME_ZONE;
    }

    @Override
	public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument) {
		return this.getTimeSummary(timesheetDocument, timesheetDocument.getTimeBlocks(), TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
	}

    @Override
    public TimeSummary getTimeSummary(TimesheetDocument timesheetDocument, List<TimeBlock> timeBlocks) {
        return this.getTimeSummary(timesheetDocument, timeBlocks, TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }

	protected TimeSummary getTimeSummary(TimesheetDocument timesheetDocument, List<TimeBlock> timeBlocks, DateTimeZone timeZone) {
		TimeSummary timeSummary = new TimeSummary();
        this.timeZone = timeZone;

		if(timesheetDocument.getTimeBlocks() == null) {
			return timeSummary;
		}

        // This variable data is generated in getHeaderFOrSummary()
        // We are already touching the calendar and figuring out the boundaries
        // in that method, so to reduce double work we'll get this info there as well.
        //
        // This could be refactored to generate the boolean list first, and then
        // use that list to generate the other values with another method call.
        //
        // For future implementation!
        List<Boolean> dayArrangements = new ArrayList<Boolean>();

		timeSummary.setSummaryHeader(getHeaderForSummary(timesheetDocument.getPayCalendarEntry(), dayArrangements));
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, timesheetDocument.getPayCalendarEntry(), TkServiceLocator.getPayCalendarSerivce().getPayCalendar(timesheetDocument.getPayCalendarEntry().getPayCalendarId()), true);
		timeSummary.setWorkedHours(getWorkedHours(tkTimeBlockAggregate));
        List<EarnGroupSection> sections = buildSummarySections(dayArrangements, tkTimeBlockAggregate, timesheetDocument.getPayCalendarEntry().getEndPeriodDateTime(), timesheetDocument);
        timeSummary.setSections(sections);

		return timeSummary;
	}


    /**
     * Provides the number of hours worked for the pay period indicated in the
     * aggregate. Break down is by FLSA day.
     *
     * @param aggregate The aggregate we are summing
     *
     * @return A list of BigDecimals containing the number of hours worked.
     * This list will line up with the header.
     */
    private List<BigDecimal> getWorkedHours(TkTimeBlockAggregate aggregate) {
        List<BigDecimal> hours = new ArrayList<BigDecimal>();
        BigDecimal periodTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;

        for (FlsaWeek week : aggregate.getFlsaWeeks(getTimeZone())) {
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
    private List<String> getHeaderForSummary(PayCalendarEntries cal, List<Boolean> dayArrangements) {
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
    private PayCalendar getPayCalendarForEntry(PayCalendarEntries calEntry) {
        PayCalendar cal = null;

        if (calEntry != null) {
            cal = TkServiceLocator.getPayCalendarSerivce().getPayCalendar(calEntry.getPayCalendarId());
        }

        return cal;
    }


    /**
     * This method is responsible for grouping the earn codes and assignments
     * into rows that can be displayed on the user interface.
     *
     * @param dayArrangements List of true/false values that let us know whether
     * or not the day at the index position should be a weekly / period summary
     * or just a day sum. This is to allow for FLSA weeks being uneven.
     *
     * @param timeBlockAggregate The time/blocks we are summarizing.
     * @param asOfDate
     * @param timesheetDocument
     *
     * @return A list of EarnGroupSelection objects containing the processed
     * information from the timeBlockAggregate.
     */
	private List<EarnGroupSection> buildSummarySections(List<Boolean> dayArrangements, TkTimeBlockAggregate timeBlockAggregate, Date asOfDate, TimesheetDocument timesheetDocument){
		Map<Integer,Map<String,BigDecimal>> dayToEarnGroupAssignToHoursMap = new HashMap<Integer,Map<String,BigDecimal>>();
		Map<String,Set<String>> earnGroupToAssignmentSets = new HashMap<String,Set<String>>();
		List<EarnGroupSection> lstEarnGroupSections = new ArrayList<EarnGroupSection>();

        // This could stand to be refactored - we don't really care about day
        // counts anymore. We now are interested in FLSA day boundaries and these
        // have already been computed.
        //
		int dayCount = 1;
		for(List<TimeBlock> timeBlocksForDay : timeBlockAggregate.getDayTimeBlockList()){
			Map<String,BigDecimal> earnGroupAssignToHoursMap = null;
			for(TimeBlock tb : timeBlocksForDay){
				for(TimeHourDetail thd : tb.getTimeHourDetails()){
					EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroupSummaryForEarnCode(thd.getEarnCode(), TKUtils.getTimelessDate(asOfDate));
					EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(thd.getEarnCode(), TKUtils.getTimelessDate(asOfDate));
					if(earnGroup == null){
						earnGroup = new EarnGroup();
						earnGroup.setEarnGroup(OTHER_EARN_GROUP);
						earnGroup.setDescr(OTHER_EARN_GROUP);
					}
					buildAssignmentSetForEarnGroup(earnGroupToAssignmentSets, tb.getAssignString(), earnGroup.getDescr());

					String earnGroupAssignDescr = earnGroup.getDescr()+"_"+tb.getAssignString();
					earnGroupAssignToHoursMap = dayToEarnGroupAssignToHoursMap.get(dayCount);
					earnGroupAssignToHoursMap = buildTimeHourDetail(earnGroupAssignToHoursMap, thd.getHours(), earnGroupAssignDescr);
					dayToEarnGroupAssignToHoursMap.put(dayCount, earnGroupAssignToHoursMap);
				}
			}
			dayToEarnGroupAssignToHoursMap.put(dayCount, earnGroupAssignToHoursMap);
			earnGroupAssignToHoursMap = null;
			dayCount++;
		}


        // Here we are building up the columns in the summary section that
        // contain hours and sums of hours for weeks and periods.
        //
		for(String earnGroup : earnGroupToAssignmentSets.keySet()){
			//for each assignment
			EarnGroupSection earnGroupSection = new EarnGroupSection();
			earnGroupSection.setEarnGroup(earnGroup);
			BigDecimal periodTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
			for(String assignmentDescr : earnGroupToAssignmentSets.get(earnGroup)){
				AssignmentRow assignRow = new AssignmentRow();

				Assignment assign = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument,assignmentDescr);
				// set assignmentkey for looking up css classes for assignmentRow
				AssignmentDescriptionKey adk = new AssignmentDescriptionKey(assign.getJobNumber().toString(), assign.getWorkArea().toString(), assign.getTask().toString());
				assignRow.setAssignmentKey(adk.toAssignmentKeyString());

				assignRow.setDescr(assign.getAssignmentDescription());
				BigDecimal weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
				

                // this counter is used to know what position in the dayArrangements
                // boolean list we should use.
                //
                // Note that daydaycount is out of sync with 'i' -> this is something
                // that we can refactor now that we are not under the assumption of
                // always having 7 day weeks.
                int daydaycount = 0;
				for(int i = 1; i <= dayToEarnGroupAssignToHoursMap.size(); i++){ // replace this with std. iterator after refactor above storage code
					Map<String,BigDecimal> earnGroupAssignToHoursMap = dayToEarnGroupAssignToHoursMap.get(i);
                    Boolean dayIsDay = dayArrangements.get(daydaycount); // is this day 'i', a Day? (or a week/period summary)
					BigDecimal hrs = TkConstants.BIG_DECIMAL_SCALED_ZERO;
					if(earnGroupAssignToHoursMap != null && earnGroupAssignToHoursMap.get(earnGroup+"_"+assignmentDescr)!=null){
						hrs = earnGroupAssignToHoursMap.get(earnGroup+"_"+assignmentDescr);
					}

                    // Use our pre-generated list to determine whether this position
                    // is a week/period summary or a day.
                    if (!dayIsDay) {
						assignRow.getTotal().add(weeklyTotal);
						weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
                        daydaycount++; // bump to catch up. The map only contains time block info not period summaries.
					} 

                    assignRow.getTotal().add(hrs);
                    weeklyTotal = weeklyTotal.add(hrs,TkConstants.MATH_CONTEXT);
                    periodTotal = periodTotal.add(hrs, TkConstants.MATH_CONTEXT);

                    daydaycount++; // bump at loop iteration

                    if (i == dayToEarnGroupAssignToHoursMap.size()) {
                        // check for final week sum
                        dayIsDay = dayArrangements.get(daydaycount);
                        if (!dayIsDay) {
                            assignRow.getTotal().add(weeklyTotal);
                            weeklyTotal = TkConstants.BIG_DECIMAL_SCALED_ZERO;
                        }
                    }
				}

				assignRow.getTotal().add(periodTotal);
				earnGroupSection.addAssignmentRow(assignRow);
			}
			lstEarnGroupSections.add(earnGroupSection);
		}

		return lstEarnGroupSections;

	}

	private void buildAssignmentSetForEarnGroup(Map<String,Set<String>> earnGroupToAssignmentSets, String assignDescr,String earnGroup){
		Set<String> assignmentSet = earnGroupToAssignmentSets.get(earnGroup);
		if(assignmentSet == null){
			assignmentSet = new HashSet<String>();
		}
		assignmentSet.add(assignDescr);
		earnGroupToAssignmentSets.put(earnGroup, assignmentSet);
	}


	private Map<String,BigDecimal> buildTimeHourDetail(Map<String,BigDecimal> earnGroupAssignToHoursMap, BigDecimal hours,
															String earnGroupAssignDescr){
		BigDecimal currentDayHrs = TkConstants.BIG_DECIMAL_SCALED_ZERO;
		if(earnGroupAssignToHoursMap == null){
			earnGroupAssignToHoursMap = new HashMap<String,BigDecimal>();
		} else {
			if(earnGroupAssignToHoursMap.get(earnGroupAssignDescr)!=null){
				currentDayHrs = earnGroupAssignToHoursMap.get(earnGroupAssignDescr);
			}
		}
		currentDayHrs = currentDayHrs.add(hours, TkConstants.MATH_CONTEXT);
		earnGroupAssignToHoursMap.put(earnGroupAssignDescr, currentDayHrs);

		return earnGroupAssignToHoursMap;
	}

}
