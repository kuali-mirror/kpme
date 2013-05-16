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
package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.util.TKUtils;

public class TKUtilsTest extends Assert {

	@Test
	public void testgetHoursBetween() throws Exception {
		DateTime beginDateTime = new DateTime(2010, 10, 16, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endDateTime = new DateTime(2010, 10, 17, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
		BigDecimal hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
		assertEquals("Wrong hours", 24, hours.intValue());

		endDateTime = new DateTime(2010, 10, 16, 18, 3, 0, 0, TKUtils.getSystemDateTimeZone());
		hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
		assertEquals("Wrong hours", 6, hours.intValue());

		endDateTime = new DateTime(2010, 10, 16, 18, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
		assertEquals("Wrong hours", 5, hours.intValue());

	}

	@Test
	public void testGetFullWeekDaySpanForPayCalendarEntry() {
		CalendarEntry payCalendarEntry = new CalendarEntry();
		// begin date is a Monday
		payCalendarEntry.setBeginPeriodFullDateTime(new DateTime(2011, 8, 8, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		// end date is a Thursday
		payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 25, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		List<Interval> intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		assertEquals("First Interval should be 08/07/2011", "08/07/2011", format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/26/2011", "08/26/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// begin date is a Sunday
		payCalendarEntry.setBeginPeriodFullDateTime(new DateTime(2011, 8, 14, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011", format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/26/2011", "08/26/2011",  format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// end date is a Sunday
		payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 28, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 09/02/2011", "09/02/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// end date is a Sunday and end time is 0:00
		payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 28, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
		intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry, TKUtils.getSystemDateTimeZone() );
		assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
		assertEquals("Last Interval should be 08/27/2011", "08/27/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

		// end date is a Monday and end time is 0:00
		payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
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
	public void testFromDateString() {
		String dateString = "01/01/2012..12/31/2012";
		String fromDateString = TKUtils.getFromDateString(dateString);
		assertTrue("fromDateString should be 01/01/2012, not " + fromDateString, fromDateString.equals("01/01/2012"));
		assertNotNull(TKUtils.formatDateString(fromDateString));
		
		dateString = ">=2/01/2012";
		fromDateString = TKUtils.getFromDateString(dateString);
		assertTrue("fromDateString should be 2/01/2012, not " + fromDateString, fromDateString.equals("2/01/2012"));
		assertNotNull(TKUtils.formatDateString(fromDateString));
	}
	
	@Test
	public void testToDateString() {
		String dateString = "01/01/2012..12/31/2012";
		String toDateString = TKUtils.getToDateString(dateString);
		assertTrue("toDateString should be 12/31/2012, not " + toDateString, toDateString.equals("12/31/2012"));
		assertNotNull(TKUtils.formatDateString(toDateString));
		
		dateString = "<=2/01/2012";
		toDateString = TKUtils.getToDateString(dateString);
		assertTrue("toDateString should be 2/01/2012, not " + toDateString, toDateString.equals("2/01/2012"));
		assertNotNull(TKUtils.formatDateString(toDateString));
	}

}