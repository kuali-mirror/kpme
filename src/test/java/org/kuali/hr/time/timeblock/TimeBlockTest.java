package org.kuali.hr.time.timeblock;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class TimeBlockTest extends TkTestCase {
	@Test
	public void testTimeBlockPersistence() throws Exception {
		
	}
	
	@Test
	public void testTimeBlockComparison() throws Exception {
		TimesheetDocument timesheetDocument = TkTestUtils.populateTimesheetDocument(new Date(System.currentTimeMillis()));
		
		TimeBlock timeBlock = TkTestUtils.createTimeBlock(timesheetDocument, 1, 6);
		TimeBlock timeBlock2 = TkTestUtils.createTimeBlock(timesheetDocument, 1, 6);
		
		assertTrue("Timeblock has been equal", timeBlock.equals(timeBlock2));
		
	}
	
	@Test
	public void testTimeBlockBuilding() throws Exception {
		PayCalendarDates payCalendarEntry = new PayCalendarDates();
		java.util.Date beginDateTime = new java.util.Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		java.util.Date endDateTime = new java.util.Date((new DateTime(2010, 1, 15, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		payCalendarEntry.setBeginPeriodDateTime(beginDateTime);
		payCalendarEntry.setEndPeriodDateTime(endDateTime);
		
		List<Interval> dayInterval = TKUtils.getDaySpanForPayCalendarEntry(payCalendarEntry);
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
	
}
