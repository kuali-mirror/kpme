package org.kuali.hr.time.util;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.kuali.hr.time.flsa.FlsaDay;
import org.kuali.hr.time.flsa.FlsaWeek;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;

public class TkTimeBlockAggregate {
	public List<List<TimeBlock>> dayTimeBlockList = new ArrayList<List<TimeBlock>>();
	private PayCalendarDates payCalendarEntry;
	private PayCalendar payCalendar;
	
	public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, PayCalendarDates payCalendarEntry){
		this(timeBlocks, payCalendarEntry, TkServiceLocator.getPayCalendarSerivce().getPayCalendar(payCalendarEntry.getPayCalendarId()));
	}
	
	public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, PayCalendarDates payCalendarEntry, PayCalendar payCalendar){
		this.payCalendarEntry = payCalendarEntry;
		this.payCalendar = payCalendar;
		List<Interval> dayIntervals = TKUtils.getDaySpanForPayCalendarEntry(payCalendarEntry);
		for(Interval dayInt : dayIntervals){
			Calendar dayIntBeginCal = Calendar.getInstance();
			dayIntBeginCal.setTimeInMillis(dayInt.getStartMillis());
			Calendar dayIntEndCal = Calendar.getInstance();
			dayIntEndCal.setTimeInMillis(dayInt.getEndMillis());
			List<TimeBlock> dayTimeBlocks = new ArrayList<TimeBlock>();
			for(TimeBlock timeBlock : timeBlocks){
				DateTime beginTime = new DateTime(timeBlock.getBeginTimestamp());
				DateTime endTime = new DateTime(timeBlock.getEndTimestamp());
				if(dayInt.contains(beginTime)){
					if(dayInt.contains(endTime) || endTime.compareTo(dayInt.getEnd()) == 0){
						// determine if the time block needs to be pushed forward / backward
						if(beginTime.getHourOfDay() < dayIntBeginCal.get(Calendar.HOUR_OF_DAY)) {
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
		return dayTimeBlockList.subList(startIndex, endIndex);
	}
		
	/**
	 * When consuming these weeks, you must be aware that you could be on a 
	 * virtual day, ie noon to noon schedule and have your FLSA time start 
	 * before the virtual day start, 
	 * but still have a full 7 days for your week.
	 */
	public List<FlsaWeek> getFlsaWeeks(){
		int flsaDayConstant = payCalendar.getFlsaBeginDayConstant();
		Time flsaBeginTime  = payCalendar.getFlsaBeginTime();

		// We can use these to build our interval, we have to make sure we 
		// place them on the proper day when we construct it.
		LocalTime flsaBeginLocalTime = LocalTime.fromDateFields(flsaBeginTime);
		
		// Defines both the start date and the start virtual time.
		// We will add 1 day to this to move over all days.
		//
		// FLSA time is set.  This is an FLSA start date.
		DateTime startDate = new DateTime(payCalendarEntry.getBeginPeriodDateTime());
		startDate = startDate.toLocalDate().toDateTime(flsaBeginLocalTime,TkConstants.SYSTEM_DATE_TIME_ZONE);
		
		List<FlsaWeek> flsaWeeks = new ArrayList<FlsaWeek>();
		List<TimeBlock> flatSortedBlockList = getFlattenedTimeBlockList();
		FlsaWeek currentWeek = new FlsaWeek(flsaDayConstant, flsaBeginLocalTime, LocalTime.fromDateFields(payCalendarEntry.getBeginPeriodDateTime()));
		flsaWeeks.add(currentWeek);
		
		for (int i = 0; i<dayTimeBlockList.size(); i++) {
			DateTime currentDate = startDate.plusDays(i);
			FlsaDay flsaDay = new FlsaDay(currentDate, flatSortedBlockList);
			
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

	public PayCalendarDates getPayCalendarEntry() {
		return payCalendarEntry;
	}

	public void setPayCalendarEntry(PayCalendarDates payCalendarEntry) {
		this.payCalendarEntry = payCalendarEntry;
	}

	public PayCalendar getPayCalendar() {
		return payCalendar;
	}

	public void setPayCalendar(PayCalendar payCalendar) {
		this.payCalendar = payCalendar;
	}
	
}
