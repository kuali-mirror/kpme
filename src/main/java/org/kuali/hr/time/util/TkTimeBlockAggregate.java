package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.timeblock.TimeBlock;

public class TkTimeBlockAggregate {
	public List<List<TimeBlock>> dayTimeBlockList = new ArrayList<List<TimeBlock>>();
	
	public TkTimeBlockAggregate(List<TimeBlock> timeBlocks, PayCalendarDates payCalendarEntry){
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
	
}
