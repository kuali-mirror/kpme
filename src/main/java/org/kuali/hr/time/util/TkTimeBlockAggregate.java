package org.kuali.hr.time.util;

import java.util.ArrayList;
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
			List<TimeBlock> dayTimeBlocks = new ArrayList<TimeBlock>();
			for(TimeBlock timeBlock : timeBlocks){
				DateTime beginTime = new DateTime(timeBlock.getBeginTimestamp());
				DateTime endTime = new DateTime(timeBlock.getEndTimestamp());
				if(dayInt.contains(beginTime)){
					if(dayInt.contains(endTime)){
						dayTimeBlocks.add(timeBlock);
					} else {
						throw new RuntimeException("Time block is not divided up into days properly");
					}
				}
			}
			dayTimeBlockList.add(dayTimeBlocks);
		}
	}
	
	public List<List<TimeBlock>> getWeekTimeBlocks(int week){
		int startIndex = week*7;
		int endIndex = (week*7)+7;
		endIndex = endIndex > dayTimeBlockList.size() ? dayTimeBlockList.size() : endIndex;
		return dayTimeBlockList.subList(startIndex, endIndex);
	}
	
}
