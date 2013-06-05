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
package org.kuali.kpme.tklm.time.block.history;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMEUnitTestCase;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;

public class TimeBlockTest extends KPMEUnitTestCase {
	
	@Test
	public void testTimeBlockComparison() throws Exception {
		DateTime beginDateTime = new DateTime();
		DateTime endDateTime = new DateTime();
		
		TimeBlock timeBlock = new TimeBlock();
		timeBlock.setJobNumber(2L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setEarnCode("REG");
		timeBlock.setBeginDateTime(beginDateTime);
		timeBlock.setEndDateTime(endDateTime);
		TimeHourDetail timeHourDetail = new TimeHourDetail();
		timeHourDetail.setEarnCode("REG");
		timeHourDetail.setHours(new BigDecimal(2.0));
		timeBlock.getTimeHourDetails().add(timeHourDetail);
		
		TimeBlock timeBlock2 = new TimeBlock();
		timeBlock2.setJobNumber(2L);
		timeBlock2.setWorkArea(1234L);
		timeBlock2.setTask(1L);
		timeBlock2.setEarnCode("REG");
		timeBlock2.setBeginDateTime(beginDateTime);
		timeBlock2.setEndDateTime(endDateTime);
		TimeHourDetail timeHourDetail2 = new TimeHourDetail();
		timeHourDetail2.setEarnCode("REG");
		timeHourDetail2.setHours(new BigDecimal(2.0));
		timeBlock2.getTimeHourDetails().add(timeHourDetail);
		
		Assert.assertTrue("Timeblock has been equal", timeBlock.equals(timeBlock2));
	}
	
	@Test
	public void testTimeBlockBuilding() throws Exception {
		CalendarEntry payCalendarEntry = new CalendarEntry();
		DateTime beginPeriodDateTime = new DateTime(2010, 1, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endPeriodDateTime = new DateTime(2010, 1, 15, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		payCalendarEntry.setBeginPeriodFullDateTime(beginPeriodDateTime);
		payCalendarEntry.setEndPeriodFullDateTime(endPeriodDateTime);
		
		List<Interval> dayInterval = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry);
		DateTime beginDateTime = new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endDateTime = new DateTime(2010, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		Interval firstDay = null;
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginDateTime(dayInt.getStart());
				tb.setEndDateTime(endDateTime);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginDateTime) ){
				firstDay = dayInt;
				if(dayInt.contains(endDateTime)){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(endDateTime);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(firstDay.getEnd());
					lstTimeBlocks.add(tb);
				}
			}
		}
		Assert.assertTrue("Two timeblocks created", lstTimeBlocks.size() == 2);
		
		lstTimeBlocks.clear();
		
		beginDateTime = new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		endDateTime = new DateTime(2010, 1, 1, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		firstDay = null;
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginDateTime(dayInt.getStart());
				tb.setEndDateTime(endDateTime);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginDateTime) ){
				firstDay = dayInt;
				if(dayInt.contains(endDateTime)){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(endDateTime);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(firstDay.getEnd());
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
		
		CalendarEntry payCalendarEntry = new CalendarEntry();
		payCalendarEntry.setBeginPeriodFullDateTime(beginTime);
		payCalendarEntry.setEndPeriodFullDateTime(endTime);
		
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
		tb1.setBeginDateTime(new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		tb1.setEndDateTime(new DateTime(2010, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		tbList.add(tb1);
		//time block with 2009 time
		TimeBlock tb2 = new TimeBlock();
		tb2.setBeginDateTime(new DateTime(2009, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		tb2.setEndDateTime(new DateTime(2009, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		tbList.add(tb2);
		
		Assert.assertTrue(tbList.get(0) == tb1);
		Assert.assertTrue(tbList.get(1) == tb2);
		// after sort
		Collections.sort(tbList);
		Assert.assertTrue(tbList.get(0) == tb2);
		Assert.assertTrue(tbList.get(1) == tb1);
	}
	private List<TimeBlock> setupTimeBlocks(DateTime startTime, DateTime endTime, CalendarEntry payCalendarEntry){
		List<Interval> dayInterval = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry);
		DateTime beginDateTime = new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endDateTime = new DateTime(2010, 1, 2, 14, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		Interval firstDay = null;
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginDateTime(dayInt.getStart());
				tb.setEndDateTime(endDateTime);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginDateTime) ){
				firstDay = dayInt;
				if(dayInt.contains(endDateTime)){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(endDateTime);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(firstDay.getEnd());
					lstTimeBlocks.add(tb);
				}
			}
		}
		Assert.assertTrue("Two timeblocks created", lstTimeBlocks.size() == 2);
		
		lstTimeBlocks.clear();
		
		beginDateTime = new DateTime(2010, 1, 1, 13, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		endDateTime = new DateTime(2010, 1, 1, 15, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		firstDay = null;
		for(Interval dayInt : dayInterval){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = new TimeBlock();
				tb.setBeginDateTime(dayInt.getStart());
				tb.setEndDateTime(endDateTime);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginDateTime) ){
				firstDay = dayInt;
				if(dayInt.contains(endDateTime)){
					//create one timeblock if contained in one day interval
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(endDateTime);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = new TimeBlock();
					tb.setBeginDateTime(beginDateTime);
					tb.setEndDateTime(firstDay.getEnd());
					lstTimeBlocks.add(tb);
				}
			}
		}
		return lstTimeBlocks;
	}
}
