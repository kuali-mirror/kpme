package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;

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
	
}
