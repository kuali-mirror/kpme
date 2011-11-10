package org.kuali.hr.time.timeblock;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeBlockTest extends TkTestCase {
	
	@Test
	public void testTimeBlockComparison() throws Exception {
		TimeBlock timeBlock = new TimeBlock();
		timeBlock.setJobNumber(2L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setEarnCode("REG");
		Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());
		timeBlock.setBeginTimestamp(beginTimestamp);
		Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
		timeBlock.setEndTimestamp(endTimestamp);
		TimeHourDetail timeHourDetail = new TimeHourDetail();
		timeHourDetail.setEarnCode("REG");
		timeHourDetail.setHours(new BigDecimal(2.0));
		timeBlock.getTimeHourDetails().add(timeHourDetail);
		
		TimeBlock timeBlock2 = new TimeBlock();
		timeBlock2.setJobNumber(2L);
		timeBlock2.setWorkArea(1234L);
		timeBlock2.setTask(1L);
		timeBlock2.setEarnCode("REG");
		timeBlock2.setBeginTimestamp(beginTimestamp);
		timeBlock2.setEndTimestamp(endTimestamp);
		TimeHourDetail timeHourDetail2 = new TimeHourDetail();
		timeHourDetail2.setEarnCode("REG");
		timeHourDetail2.setHours(new BigDecimal(2.0));
		timeBlock2.getTimeHourDetails().add(timeHourDetail);
		
		assertTrue("Timeblock has been equal", timeBlock.equals(timeBlock2));
	}
	
	@Test
	public void testTimeBlockBuilding() throws Exception {
		CalendarEntries calendarEntry = new CalendarEntries();
		java.util.Date beginDateTime = new java.util.Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		java.util.Date endDateTime = new java.util.Date((new DateTime(2010, 1, 15, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		calendarEntry.setBeginPeriodDateTime(beginDateTime);
		calendarEntry.setEndPeriodDateTime(endDateTime);
		
		List<Interval> dayInterval = TKUtils.getDaySpanForCalendarEntry(calendarEntry);
		Timestamp beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Timestamp endTimeStamp = new Timestamp((new DateTime(2010, 1, 2, 14, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		
		Interval firstDay = null;
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginTimestamp(new Timestamp(dayInt.getStartMillis()));
				tb.setEndTimestamp(endTimeStamp);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginTimeStamp.getTime()) ){
				firstDay = dayInt;
				if(dayInt.contains(endTimeStamp.getTime())){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(endTimeStamp);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
					lstTimeBlocks.add(tb);
				}
			}
		}
		assertTrue("Two timeblocks created", lstTimeBlocks.size() == 2);
		
		lstTimeBlocks.clear();
		
		beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		endTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		
		firstDay = null;
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginTimestamp(new Timestamp(dayInt.getStartMillis()));
				tb.setEndTimestamp(endTimeStamp);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginTimeStamp.getTime()) ){
				firstDay = dayInt;
				if(dayInt.contains(endTimeStamp.getTime())){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(endTimeStamp);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
					lstTimeBlocks.add(tb);
				}
			}
		}
		assertTrue("One time block creation", lstTimeBlocks.size() == 1);
	}
	
	@Test
	public void testTimeBlockAggregate() throws Exception {
		DateTime beginTime = new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"));
		DateTime endTime = new DateTime(2010, 1, 16, 12, 0, 0, 0, DateTimeZone.forID("EST"));
		
		Calendar calendar = new Calendar();
		
		CalendarEntries calendarEntry = new CalendarEntries();
		java.util.Date beginDateTime = new java.util.Date(beginTime.getMillis());
		java.util.Date endDateTime = new java.util.Date(endTime.getMillis());
		calendarEntry.setBeginPeriodDateTime(beginDateTime);
		calendarEntry.setEndPeriodDateTime(endDateTime);
		
		List<TimeBlock> lstTimeBlocks = setupTimeBlocks(beginTime, endTime, calendarEntry);
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(lstTimeBlocks, calendarEntry, calendar);
		assertTrue("Aggregate built correctly ", tkTimeBlockAggregate!= null && tkTimeBlockAggregate.getWeekTimeBlocks(0).size() == 7);
		assertTrue("Total number of days is correct",tkTimeBlockAggregate.getDayTimeBlockList().size()==15);
	}
	
	@Test
	public void testTimeBlockSorting() throws Exception {
		List<TimeBlock> tbList = new ArrayList<TimeBlock>();
		TimeBlock tb1 = new TimeBlock();
		// time block with 2010 time
		tb1.setBeginTimestamp(new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		tb1.setEndTimestamp(new Timestamp((new DateTime(2010, 1, 2, 14, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		tbList.add(tb1);
		//time block with 2009 time
		TimeBlock tb2 = new TimeBlock();
		tb2.setBeginTimestamp(new Timestamp((new DateTime(2009, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		tb2.setEndTimestamp(new Timestamp((new DateTime(2009, 1, 2, 14, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		tbList.add(tb2);
		
		assertTrue(tbList.get(0) == tb1);
		assertTrue(tbList.get(1) == tb2);
		// after sort
		Collections.sort(tbList);
		assertTrue(tbList.get(0) == tb2);
		assertTrue(tbList.get(1) == tb1);
	}
	private List<TimeBlock> setupTimeBlocks(DateTime startTime, DateTime endTime, CalendarEntries calendarEntry){
		List<Interval> dayInterval = TKUtils.getDaySpanForCalendarEntry(calendarEntry);
		Timestamp beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Timestamp endTimeStamp = new Timestamp((new DateTime(2010, 1, 2, 14, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		
		Interval firstDay = null;
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginTimestamp(new Timestamp(dayInt.getStartMillis()));
				tb.setEndTimestamp(endTimeStamp);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginTimeStamp.getTime()) ){
				firstDay = dayInt;
				if(dayInt.contains(endTimeStamp.getTime())){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(endTimeStamp);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
					lstTimeBlocks.add(tb);
				}
			}
		}
		assertTrue("Two timeblocks created", lstTimeBlocks.size() == 2);
		
		lstTimeBlocks.clear();
		
		beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		endTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 15, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		
		firstDay = null;
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginTimestamp(new Timestamp(dayInt.getStartMillis()));
				tb.setEndTimestamp(endTimeStamp);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginTimeStamp.getTime()) ){
				firstDay = dayInt;
				if(dayInt.contains(endTimeStamp.getTime())){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(endTimeStamp);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginTimestamp(beginTimeStamp);
					tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
					lstTimeBlocks.add(tb);
				}
			}
		}
		return lstTimeBlocks;
	}
}
