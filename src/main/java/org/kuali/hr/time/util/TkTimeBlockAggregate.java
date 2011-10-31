package org.kuali.hr.time.util;

import org.joda.time.*;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TkTimeBlockAggregate {
	public List<List<TimeBlock>> dayTimeBlockList = new ArrayList<List<TimeBlock>>();
	private CalendarEntries payCalendarEntry;
	private Calendar payCalendar;

    /**
     * Defaults to using SYSTEM time zone.
     *
     * @param timeBlocks
     * @param payCalendarEntry
     */
	public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, CalendarEntries payCalendarEntry){
		this(timeBlocks, payCalendarEntry, TkServiceLocator.getPayCalendarSerivce().getPayCalendar(payCalendarEntry.getHrCalendarId()));
	}

    /**
     * Defaults to using SYSTEM time zone.
     *
     * @param timeBlocks
     * @param payCalendarEntry
     * @param payCalendar
     */
	public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, CalendarEntries payCalendarEntry, Calendar payCalendar) {
        this(timeBlocks, payCalendarEntry, payCalendar, false);
    }

    /**
     * Provides the option to refer to the time zone adjusted time for the current
     * user.
     * @param timeBlocks
     * @param payCalendarEntry
     * @param payCalendar
     * @param useUserTimeZone
     */
    public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, CalendarEntries payCalendarEntry, Calendar payCalendar, boolean useUserTimeZone) {
		this.payCalendarEntry = payCalendarEntry;
		this.payCalendar = payCalendar;

		List<Interval> dayIntervals = TKUtils.getDaySpanForPayCalendarEntry(payCalendarEntry);
		for(Interval dayInt : dayIntervals){
			List<TimeBlock> dayTimeBlocks = new ArrayList<TimeBlock>();
			for(TimeBlock timeBlock : timeBlocks){

                // Assumption: Timezones can only be switched at pay period end boundaries.
                // If the above assumption becomes false, the logic below will need to
                // accommodate virtual chopping of time blocks to have them fit nicely
                // in the "days" that are displayed to users.

				DateTime beginTime = useUserTimeZone ? timeBlock.getBeginTimeDisplay() : new DateTime(timeBlock.getBeginTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
				DateTime endTime = useUserTimeZone ? timeBlock.getEndTimeDisplay() :  new DateTime(timeBlock.getEndTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
				if(dayInt.contains(beginTime)){
					if(dayInt.contains(endTime) || endTime.compareTo(dayInt.getEnd()) == 0){
						// determine if the time block needs to be pushed forward / backward
						if(beginTime.getHourOfDay() < dayInt.getStart().getHourOfDay()) {
							timeBlock.setPushBackward(true);
						}

						dayTimeBlocks.add(timeBlock);
					}
				}
			}
			dayTimeBlockList.add(dayTimeBlocks);
		}
	}

    public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, CalendarEntries payCalendarEntry, Calendar payCalendar, boolean useUserTimeZone, List<Interval> dayIntervals) {
    	this.payCalendarEntry = payCalendarEntry;
		this.payCalendar = payCalendar;
		
		for(Interval dayInt : dayIntervals){
			List<TimeBlock> dayTimeBlocks = new ArrayList<TimeBlock>();
			for(TimeBlock timeBlock : timeBlocks){

                // Assumption: Timezones can only be switched at pay period end boundaries.
                // If the above assumption becomes false, the logic below will need to
                // accommodate virtual chopping of time blocks to have them fit nicely
                // in the "days" that are displayed to users.

				DateTime beginTime = useUserTimeZone ? timeBlock.getBeginTimeDisplay() : new DateTime(timeBlock.getBeginTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
				DateTime endTime = useUserTimeZone ? timeBlock.getEndTimeDisplay() :  new DateTime(timeBlock.getEndTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
				if(dayInt.contains(beginTime)){
					if(dayInt.contains(endTime) || endTime.compareTo(dayInt.getEnd()) == 0){
						// determine if the time block needs to be pushed forward / backward
						if(beginTime.getHourOfDay() < dayInt.getStart().getHourOfDay()) {
							timeBlock.setPushBackward(true);
						}

						dayTimeBlocks.add(timeBlock);
					}
				}
			}
			dayTimeBlockList.add(dayTimeBlocks);
		}
		
    }
    
    
	public List<TimeBlock> getFlattenedTimeBlockList(){
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(List<TimeBlock> timeBlocks : dayTimeBlockList){
			lstTimeBlocks.addAll(timeBlocks);
		}

		Collections.sort(lstTimeBlocks, new Comparator<TimeBlock>() { // Sort the Time Blocks
			public int compare(TimeBlock tb1, TimeBlock tb2) {
				if (tb1 != null && tb2 != null)
					return tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
				return 0;
			}
		});

		return lstTimeBlocks;
	}

	/**
	 * Provides a way to access all of the time blocks for a given week.
	 *
	 * Outer list is 0 indexed list representing days in a week.
	 * Inner List are all of the time blocks for that day.
	 *
	 * Ex.
	 *
	 * List<List<TimeBlock>> week0 = getWeekTimeBlocks(0);
	 * List<TimeBlock> day0 = week0.get(0);
	 *
	 * @param week
	 * @return
	 */
	public List<List<TimeBlock>> getWeekTimeBlocks(int week){
		int startIndex = week*7;
		int endIndex = (week*7)+7;
		endIndex = endIndex > dayTimeBlockList.size() ? dayTimeBlockList.size() : endIndex;

        // Need to sort each day by clock time.
        List<List<TimeBlock>> wList = dayTimeBlockList.subList(startIndex, endIndex);
        for (List<TimeBlock> dList : wList) {
            Collections.sort(dList, new Comparator<TimeBlock>() { // Sort the Time Blocks
                public int compare(TimeBlock tb1, TimeBlock tb2) {
                    if (tb1 != null && tb2 != null)
                        return tb1.getBeginTimestamp().compareTo(tb2.getBeginTimestamp());
                    return 0;
                }
            });
        }

		return wList;
	}

	/**
	 * When consuming these weeks, you must be aware that you could be on a
	 * virtual day, ie noon to noon schedule and have your FLSA time start
	 * before the virtual day start,
	 * but still have a full 7 days for your week.
     *
     * @param zone The TimeZone to use when constructing this relative sorting.
	 */
	public List<FlsaWeek> getFlsaWeeks(DateTimeZone zone){
		int flsaDayConstant = payCalendar.getFlsaBeginDayConstant();
		Time flsaBeginTime  = payCalendar.getFlsaBeginTime();

		// We can use these to build our interval, we have to make sure we
		// place them on the proper day when we construct it.
		LocalTime flsaBeginLocalTime = LocalTime.fromDateFields(flsaBeginTime);

		// Defines both the start date and the start virtual time.
		// We will add 1 day to this to move over all days.
		//
		// FLSA time is set.  This is an FLSA start date.
        LocalDateTime startLDT = payCalendarEntry.getBeginLocalDateTime();
//		DateTime startDate = new DateTime(payCalendarEntry.getBeginPeriodDateTime());
//		startDate = startDate.toLocalDate().toDateTime(flsaBeginLocalTime,TkConstants.SYSTEM_DATE_TIME_ZONE);

		List<FlsaWeek> flsaWeeks = new ArrayList<FlsaWeek>();
		List<TimeBlock> flatSortedBlockList = getFlattenedTimeBlockList();
		FlsaWeek currentWeek = new FlsaWeek(flsaDayConstant, flsaBeginLocalTime, LocalTime.fromDateFields(payCalendarEntry.getBeginPeriodDateTime()));
//		commented additional week addition, as causing an extra day on UI
		flsaWeeks.add(currentWeek);

		for (int i = 0; i<dayTimeBlockList.size(); i++) {
			LocalDateTime currentDate = startLDT.plusDays(i);
			FlsaDay flsaDay = new FlsaDay(currentDate, flatSortedBlockList, zone);

			if (currentDate.getDayOfWeek() == flsaDayConstant) {
				currentWeek = new FlsaWeek(flsaDayConstant, flsaBeginLocalTime, flsaBeginLocalTime);
				flsaWeeks.add(currentWeek);
				
				currentWeek.addFlsaDay(flsaDay);
			} else {
				// add to existing week.
				currentWeek.addFlsaDay(flsaDay);
			}
		}

		return flsaWeeks;
	}

	/**
	 * @return the total number of weeks that this object represents.
	 */
	public int numberOfAggregatedWeeks() {
		int weeks = 0;

		if (this.dayTimeBlockList.size() > 0) {
			weeks = this.dayTimeBlockList.size() / 7;
			if (this.dayTimeBlockList.size() % 7 > 0)
				weeks++;
		}

		return weeks;
	}

	public List<List<TimeBlock>> getDayTimeBlockList() {
		return dayTimeBlockList;
	}

	public CalendarEntries getPayCalendarEntry() {
		return payCalendarEntry;
	}

	public void setPayCalendarEntry(CalendarEntries payCalendarEntry) {
		this.payCalendarEntry = payCalendarEntry;
	}

	public Calendar getPayCalendar() {
		return payCalendar;
	}

	public void setPayCalendar(Calendar payCalendar) {
		this.payCalendar = payCalendar;
	}

}
