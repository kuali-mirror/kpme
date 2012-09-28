/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.timeblock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class TimeBlockTest extends KPMETestCase {
	
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
		
		Assert.assertTrue("Timeblock has been equal", timeBlock.equals(timeBlock2));
	}
	
	@Test
	public void testTimeBlockBuilding() throws Exception {
		CalendarEntries payCalendarEntry = new CalendarEntries();
		java.util.Date beginDateTime = new java.util.Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		java.util.Date endDateTime = new java.util.Date((new DateTime(2010, 1, 15, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		payCalendarEntry.setBeginPeriodDateTime(beginDateTime);
		payCalendarEntry.setEndPeriodDateTime(endDateTime);
		
		List<Interval> dayInterval = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry);
		Timestamp beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		Timestamp endTimeStamp = new Timestamp((new DateTime(2010, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		
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
		Assert.assertTrue("Two timeblocks created", lstTimeBlocks.size() == 2);
		
		lstTimeBlocks.clear();
		
		beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		endTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		
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
		Assert.assertTrue("One time block creation", lstTimeBlocks.size() == 1);
	}
	
	@Test
	public void testTimeBlockAggregate() throws Exception {
		DateTime beginTime = new DateTime(2010, 1, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endTime = new DateTime(2010, 1, 16, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		Calendar payCalendar = new Calendar();
		
		CalendarEntries payCalendarEntry = new CalendarEntries();
		java.util.Date beginDateTime = new java.util.Date(beginTime.getMillis());
		java.util.Date endDateTime = new java.util.Date(endTime.getMillis());
		payCalendarEntry.setBeginPeriodDateTime(beginDateTime);
		payCalendarEntry.setEndPeriodDateTime(endDateTime);
		
		List<TimeBlock> lstTimeBlocks = setupTimeBlocks(beginTime, endTime, payCalendarEntry);
		TkTimeBlockAggregate tkTimeBlockAggregate = new TkTimeBlockAggregate(lstTimeBlocks, payCalendarEntry, payCalendar);
		Assert.assertTrue("Aggregate built correctly ", tkTimeBlockAggregate!= null && tkTimeBlockAggregate.getWeekTimeBlocks(0).size() == 7);
		Assert.assertTrue("Total number of days is correct",tkTimeBlockAggregate.getDayTimeBlockList().size()==15);
	}
	
	@Test
	public void testTimeBlockSorting() throws Exception {
		List<TimeBlock> tbList = new ArrayList<TimeBlock>();
		TimeBlock tb1 = new TimeBlock();
		// time block with 2010 time
		tb1.setBeginTimestamp(new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		tb1.setEndTimestamp(new Timestamp((new DateTime(2010, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		tbList.add(tb1);
		//time block with 2009 time
		TimeBlock tb2 = new TimeBlock();
		tb2.setBeginTimestamp(new Timestamp((new DateTime(2009, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		tb2.setEndTimestamp(new Timestamp((new DateTime(2009, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		tbList.add(tb2);
		
		Assert.assertTrue(tbList.get(0) == tb1);
		Assert.assertTrue(tbList.get(1) == tb2);
		// after sort
		Collections.sort(tbList);
		Assert.assertTrue(tbList.get(0) == tb2);
		Assert.assertTrue(tbList.get(1) == tb1);
	}
	private List<TimeBlock> setupTimeBlocks(DateTime startTime, DateTime endTime, CalendarEntries payCalendarEntry){
		List<Interval> dayInterval = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry);
		Timestamp beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		Timestamp endTimeStamp = new Timestamp((new DateTime(2010, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		
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
		Assert.assertTrue("Two timeblocks created", lstTimeBlocks.size() == 2);
		
		lstTimeBlocks.clear();
		
		beginTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		endTimeStamp = new Timestamp((new DateTime(2010, 1, 1, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		
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
