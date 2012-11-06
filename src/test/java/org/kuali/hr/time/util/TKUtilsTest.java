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
import org.kuali.hr.time.calendar.CalendarEntries;

public class TKUtilsTest extends Assert {

	@Test
	public void testGetWeekIntervals() throws Exception {
		Date beginDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		Date endDate   = new Date((new DateTime(2010, 3, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		List<Interval> intervals = TKUtils.getWeekIntervals(beginDate, endDate);
		assertEquals("Missing partial week", 9, intervals.size());
		for (Interval interval : intervals) {
			System.out.println(interval);
		}

		beginDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		endDate   = new Date((new DateTime(2010, 1, 3, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		intervals = TKUtils.getWeekIntervals(beginDate, endDate);
		assertEquals("Missing partial week", 1, intervals.size());
		for (Interval interval : intervals) {
			System.out.println(interval);
		}

	}

	@Test
	public void testgetHoursBetween() throws Exception {
		Timestamp beginTime = new Timestamp((new DateTime(2010, 10, 16, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		Timestamp endTime = new Timestamp((new DateTime(2010, 10, 17, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		BigDecimal hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
		assertEquals("Wrong hours", 24, hours.intValue());

		endTime = new Timestamp((new DateTime(2010, 10, 16, 18, 3, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
		assertEquals("Wrong hours", 6, hours.intValue());

		endTime = new Timestamp((new DateTime(2010, 10, 16, 18, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
		assertEquals("Wrong hours", 5, hours.intValue());

	}

	@Test
	public void testGetFullWeekDaySpanForPayCalendarEntry() {
		CalendarEntries payCalendarEntry = new CalendarEntries();
		// begin date is a Monday
		payCalendarEntry.setBeginPeriodDateTime(new Date((new DateTime(2011, 8, 8, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		// end date is a Thursday
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 25, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		List<Interval> intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals("First Interval should be 08/07/2011", "08/07/2011", format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/26/2011", "08/26/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// begin date is a Sunday
		payCalendarEntry.setBeginPeriodDateTime(new Date((new DateTime(2011, 8, 14, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011", format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/26/2011", "08/26/2011",  format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// end date is a Sunday
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 28, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 09/02/2011", "09/02/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// end date is a Sunday and end time is 0:00
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 28, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/27/2011", "08/27/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// end date is a Monday and end time is 0:00
		payCalendarEntry.setEndPeriodDateTime(new Date((new DateTime(2011, 8, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 09/03/2011", "09/03/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

	}
	
	@Test
	public void testConvertMillisToMinutes() {
		BigDecimal mins = TKUtils.convertMillisToMinutes(new Long(380000));
		assertTrue("Minutes should be between 6 and 7",  mins.compareTo(new BigDecimal(6)) > 0  && mins.compareTo(new BigDecimal(7)) < 0);
		mins = TKUtils.convertMillisToMinutes(new Long(240000));
		assertTrue("Minutes should be 4",  mins.compareTo(new BigDecimal(4)) == 0);
	}
	
	@Test
	public void testFromAndToDateString() {
		String dateString = "01/01/2012..12/20/2012";
		String fromDateString = TKUtils.getFromDateString(dateString);
		assertTrue("FromDateString should be 01/01/2012, not " + fromDateString, fromDateString.equals("01/01/2012"));
		assertNotNull(TKUtils.formatDateString(fromDateString));
		String toDateString = TKUtils.getToDateString(dateString);
		assertTrue("toDateString should be 12/20/2012, not " + toDateString, toDateString.equals("12/20/2012"));
		assertNotNull(TKUtils.formatDateString(toDateString));
		
		dateString = "<=2/1/2012";
		toDateString = TKUtils.getToDateString(dateString);
		assertTrue("toDateString should be 2/1/2012, not " + toDateString, toDateString.equals("2/1/2012"));
		assertNotNull(TKUtils.formatDateString(toDateString));
		
		dateString = ">=3/01/2012";
		toDateString = TKUtils.getToDateString(dateString);
		assertTrue("toDateString should be 3/01/2012, not " + toDateString, toDateString.equals("3/01/2012"));
		assertNotNull(TKUtils.formatDateString(toDateString));
	}

}
