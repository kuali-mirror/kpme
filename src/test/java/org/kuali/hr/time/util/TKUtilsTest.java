package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public class TKUtilsTest extends Assert {
	
	@Test
	public void testGetWeekIntervals() throws Exception {
		Date beginDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Date endDate   = new Date((new DateTime(2010, 3, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		List<Interval> intervals = TKUtils.getWeekIntervals(beginDate, endDate);
		assertEquals("Missing partial week", 9, intervals.size());
		for (Interval interval : intervals) {
			System.out.println(interval);
		}
		
		beginDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		endDate   = new Date((new DateTime(2010, 1, 3, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		intervals = TKUtils.getWeekIntervals(beginDate, endDate);
		assertEquals("Missing partial week", 1, intervals.size());
		for (Interval interval : intervals) {
			System.out.println(interval);
		}

	}

	@Test
	public void testgetHoursBetween() throws Exception {
		Timestamp beginTime = new Timestamp((new DateTime(2010, 10, 16, 12, 3, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Timestamp endTime = new Timestamp((new DateTime(2010, 10, 17, 12, 3, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		BigDecimal hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
		assertEquals("Wrong hours", 24, hours.intValue());
		
		endTime = new Timestamp((new DateTime(2010, 10, 16, 18, 3, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
		assertEquals("Wrong hours", 6, hours.intValue());
		
		endTime = new Timestamp((new DateTime(2010, 10, 16, 18, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
		assertEquals("Wrong hours", 5, hours.intValue());

	}
	
	@Test
	public void testGetFullWeekDaySpanForPayCalendarEntry() {
		PayCalendarEntries payCalendarEntry = new PayCalendarEntries();
		// begin date is a Monday
		payCalendarEntry.setBeginPeriodDateTime(new Date((new DateTime(2011, 8, 8, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		// end date is a Thursday
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 25, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		List<Interval> intervals = TKUtils.getFullWeekDaySpanForPayCalendarEntry(payCalendarEntry, DateTimeZone.forID("EST") );
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals("First Interval should be 08/07/2011", "08/07/2011", format.format(intervals.get(0).getStart().toDate())); 
		assertEquals("Last Interval should be 08/26/2011", "08/26/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));
		
		// begin date is a Sunday
		payCalendarEntry.setBeginPeriodDateTime(new Date((new DateTime(2011, 8, 14, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForPayCalendarEntry(payCalendarEntry, DateTimeZone.forID("EST") );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011", format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/26/2011", "08/26/2011",  format.format(intervals.get(intervals.size()-1).getStart().toDate()));
		
		// end date is a Sunday
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 28, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForPayCalendarEntry(payCalendarEntry, DateTimeZone.forID("EST") );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 09/02/2011", "09/02/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));
		
		// end date is a Sunday and end time is 0:00
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 28, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForPayCalendarEntry(payCalendarEntry, DateTimeZone.forID("EST") );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/27/2011", "08/27/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));
		
		// end date is a Monday and end time is 0:00
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 29, 0, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForPayCalendarEntry(payCalendarEntry, DateTimeZone.forID("EST") );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 09/03/2011", "09/03/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));
		
	}
	
	
}
