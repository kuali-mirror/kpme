/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.KPMEConstants;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.util.KpmeUtils;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.rice.core.api.config.property.ConfigContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@IntegrationTest
public class TKUtilsTest extends TKLMIntegrationTestCase {

    @Test
    public void testGetDayOfMonthFromDateString() throws Exception {
        Assert.assertEquals("Wrong Day","01",TKUtils.getDayOfMonthFromDateString("01/01/2013"));
        Assert.assertEquals("Wrong Day","10",TKUtils.getDayOfMonthFromDateString("01/10/2013"));
        Assert.assertEquals("Wrong Day","20",TKUtils.getDayOfMonthFromDateString("01/20/2013"));
    }

    @Test
    public void testGetSystemTimeZone() throws Exception {
        Assert.assertEquals("Wrong TimeZone", TimeZone.getDefault().getID(),TKUtils.getSystemTimeZone());
    }

    @Test
    public void testGetSystemDateTimeZone() throws  Exception {
        Assert.assertEquals("Wrong DateTimeZone",DateTimeZone.forID(TimeZone.getDefault().getID()),TKUtils.getSystemDateTimeZone());
    }

    @Test
    public void testGetDaysBetween() throws Exception {
        LocalDate startDate = new LocalDate(2013,1,1);
        LocalDate endDate = new LocalDate(2013,1,15);
        Long days = TKUtils.getDaysBetween(startDate,endDate);
        Assert.assertEquals("Wrong Days",14,days.intValue());

        //add a day
        days =  TKUtils.getDaysBetween(startDate,endDate.plusDays(1));
        Assert.assertEquals("Wrong Days",15,days.intValue());

        //minus a day
        days =  TKUtils.getDaysBetween(startDate,endDate.minusDays(1));
        Assert.assertEquals("Wrong Days",13,days.intValue());
    }

    @Test
    public void testGetHoursBetween() throws Exception {
        DateTime beginDateTime = new DateTime(2010, 10, 16, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime endDateTime = new DateTime(2010, 10, 17, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
        BigDecimal hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
        Assert.assertEquals("Wrong hours", 24, hours.intValue());

        endDateTime = new DateTime(2010, 10, 16, 18, 3, 0, 0, TKUtils.getSystemDateTimeZone());
        hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
        Assert.assertEquals("Wrong hours", 6, hours.intValue());

        endDateTime = new DateTime(2010, 10, 16, 18, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
        Assert.assertEquals("Wrong hours", 5, hours.intValue());

    }

    @Test
    public void testFormatAssignmentKey() throws Exception {
        String assignmentKey = KpmeUtils.formatAssignmentKey(1L, 2L, 3L);
        Assert.assertEquals("Assignment Key should be 1_2_3","1_2_3",assignmentKey);
    }

    @Test
    public void testFormatAssignmentDescription() throws Exception {
        Assignment.Builder testAssignment = Assignment.Builder.create("admin", 30L, 30L, 30L);
        testAssignment.setEffectiveLocalDate(new LocalDate(2010,1,1));

        Map<String,String> formattedAssignmentDescription = TKUtils.formatAssignmentDescription(testAssignment.build());

        assertTrue(formattedAssignmentDescription.containsKey("30_30_30"));
        Assert.assertEquals("Description is not correct","SDR1 Work Area : $20.00 Rcd 30 TEST-DEPT SDR1 task",formattedAssignmentDescription.get("30_30_30"));

        testAssignment.setJobNumber(999L);
        testAssignment.setWorkArea(999L);
        testAssignment.setTask(999L);
        testAssignment.setPrincipalId("principalId");
        testAssignment.setEffectiveLocalDate(new LocalDate(2013,1,1));

        formattedAssignmentDescription = TKUtils.formatAssignmentDescription(testAssignment.build());
        assertTrue(formattedAssignmentDescription.containsKey("999_999_999"));
        Assert.assertEquals("Description is not correct"," : $0.00 Rcd 999 ",formattedAssignmentDescription.get("999_999_999"));
    }

    @Test
    public void testGetAssignmentString() throws Exception {
        String testAssignmentString = HrServiceLocator.getAssignmentService().getAssignmentDescription("admin", 30L, 30L, 30L, new LocalDate(2010, 1, 1));
        Assert.assertEquals("Assignment String is wrong","SDR1 Work Area : $20.00 Rcd 30 TEST-DEPT SDR1 task",testAssignmentString);
    }

    @Test
    public void testGetDaySpanForCalendarEntry() throws Exception {
        CalendarEntry.Builder payCalendarEntry = CalendarEntry.Builder.create();
        payCalendarEntry.setBeginPeriodFullDateTime(new DateTime(2013, 1, 1, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2013, 1, 4, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        List<Interval> daySpanList = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry.build(),TKUtils.getSystemDateTimeZone());
        Assert.assertEquals("List Size should be 3",3,daySpanList.size());
        Assert.assertEquals("Start Date should be 01/01/2013",daySpanList.get(0).getStart(),
                new DateTime(2013,1,1,0,0,0,TKUtils.getSystemDateTimeZone()));
        Assert.assertEquals("End Date should be 01/04/2013",daySpanList.get(daySpanList.size()-1).getEnd(),
                new DateTime(2013,1,4,0,0,0,TKUtils.getSystemDateTimeZone()));

        //add a day
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2013, 1, 5, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        List<Interval> daySpanList1 = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry.build(),TKUtils.getSystemDateTimeZone());
        Assert.assertEquals("List Size should be 4",4,daySpanList1.size());
        Assert.assertEquals("Start Date should be 01/01/2013",daySpanList1.get(0).getStart(),
                new DateTime(2013,1,1,0,0,0,TKUtils.getSystemDateTimeZone()));
        Assert.assertEquals("End Date should be 01/05/2013",daySpanList1.get(daySpanList1.size()-1).getEnd(),
                new DateTime(2013,1,5,0,0,0,TKUtils.getSystemDateTimeZone()));

        //minus a day
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2013, 1, 3, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        List<Interval> daySpanList2 = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry.build(),TKUtils.getSystemDateTimeZone());
        Assert.assertEquals("List Size should be 2",2,daySpanList2.size());
        Assert.assertEquals("Start Date should be 01/01/2013",daySpanList2.get(0).getStart(),
                new DateTime(2013,1,1,0,0,0,TKUtils.getSystemDateTimeZone()));
        Assert.assertEquals("End Date should be 01/03/2013",daySpanList2.get(daySpanList2.size()-1).getEnd(),
                new DateTime(2013,1,3,0,0,0,TKUtils.getSystemDateTimeZone()));

        //test overloaded method
        List<Interval> daySpanList3 = TKUtils.getDaySpanForCalendarEntry(payCalendarEntry.build());
        Assert.assertEquals("List Size should be 2",2,daySpanList3.size());
        Assert.assertEquals("Start Date should be 01/01/2013",daySpanList3.get(0).getStart(),
                new DateTime(2013,1,1,0,0,0,TKUtils.getSystemDateTimeZone()));
        Assert.assertEquals("End Date should be 01/03/2013",daySpanList3.get(daySpanList3.size()-1).getEnd(),
                new DateTime(2013,1,3,0,0,0,TKUtils.getSystemDateTimeZone()));
    }

    @Test
    public void testConvertHoursToMillis() throws Exception {
        BigDecimal hours = new BigDecimal(1);
        Long millis = TKUtils.convertHoursToMillis(hours);
        Assert.assertEquals("Wrong Millis", 3600000, millis.intValue());

        millis = TKUtils.convertHoursToMillis(hours.add(new BigDecimal(.5)));
        Assert.assertEquals("Wrong Millis", 5400000, millis.intValue());


    }

    @Test
    public void testConvertMillisToHours() throws Exception {
        BigDecimal hours = TKUtils.convertMillisToHours(3600000L);
        Assert.assertEquals("Wrong Hours", new BigDecimal(1), hours);

        hours = TKUtils.convertMillisToHours(5400000L);
        Assert.assertEquals("Wrong Hours",new BigDecimal(1.5),hours);


    }

    @Test
    public void testConvertMillisToMinutes() {
        BigDecimal mins = TKUtils.convertMillisToMinutes(380000);
        assertTrue("Minutes should be between 6 and 7", mins.compareTo(new BigDecimal(6)) > 0 && mins.compareTo(new BigDecimal(7)) < 0);
        mins = TKUtils.convertMillisToMinutes(240000);
        assertTrue("Minutes should be 4", mins.compareTo(new BigDecimal(4)) == 0);
    }

    @Test
    public void testConvertMillisToDays() throws Exception {
        Long millis = 86400000L;
        BigDecimal days = TKUtils.convertMillisToDays(millis);
        Assert.assertEquals("Wrong Days",1,days.intValue());
    }

    @Test
    public void testConvertMinutesToHours() throws Exception {
        BigDecimal minutes = new BigDecimal(60);
        BigDecimal hours = TKUtils.convertMinutesToHours(minutes);
        Assert.assertEquals("Wrong Hours",1,hours.intValue());
    }

    @Test
    public void testConvertMillisToWholeDays() throws Exception {
        Long millis = 1L;
        int days = TKUtils.convertMillisToWholeDays(millis);
        Assert.assertEquals("Wrong Whole Days", 1, days);

        millis = 86410000L;
        days = TKUtils.convertMillisToWholeDays(millis);
        Assert.assertEquals("Wrong Whole Days", 2, days);
    }

    @Test
    public void testIsVirtualWorkDay() throws Exception {
        Assert.assertEquals("Expected False",false,TKUtils.isVirtualWorkDay(new DateTime(2013,1,1,0,0,0)));
        Assert.assertEquals("Expected True",true,TKUtils.isVirtualWorkDay(new DateTime(2013,1,1,1,0,0)));
        Assert.assertEquals("Expected False",false,TKUtils.isVirtualWorkDay(new DateTime(2013,1,1,0,30,0)));
        Assert.assertEquals("Expected true",true,TKUtils.isVirtualWorkDay(new DateTime(2013,1,1,13,30,0)));
    }

    @Test
    public void testConvertDateStringToDateTime() throws Exception {
        String dateString = "01/01/2013";
        String timeString = "9:0";
        DateTime dateTime = TKUtils.convertDateStringToDateTime(dateString, timeString);
        DateTime equalsDateTime = new DateTime(2013, 1, 1, 9, 0, 0, TKUtils.getSystemDateTimeZone());
        Assert.assertEquals("Date Time Not Correct", dateTime, equalsDateTime);
    }


    @Test
    public void testConvertDateStringToDateTimeWithoutZone() {
        String dateString = "01/01/2013";
        String timeString = "9:0";
        DateTime dateTime = TKUtils.convertDateStringToDateTimeWithoutZone(dateString, timeString);
        DateTime equalsDateTime = new DateTime(2013, 1, 1, 9, 0, 0);
        Assert.assertEquals("Date Time Not Correct", dateTime, equalsDateTime);
    }

    @Test
    public void testGetIPAddressFromRequest() throws Exception {
        Assert.assertEquals("IP Address is Not Correct","192.168.1.1",TKUtils.getIPAddressFromRequest("192.168.1.1"));
        Assert.assertEquals("IP Address is Not Correct","",TKUtils.getIPAddressFromRequest("FE80:0000:0000:0000:0202:B3FF:FE1E:8329"));
    }

    @Test
    public void testGetIPNumber() throws Exception {
        Assert.assertEquals("Wrong IP Adress",java.net.InetAddress.getLocalHost().getHostAddress(),TKUtils.getIPNumber());
    }
    @Test
    public void testSubtractOneSecondFromTimestamp() throws Exception {
        DateTime testDate1 = new DateTime(2013, 1, 1, 9, 0 , 1);
        DateTime testDate2 = new DateTime(2013, 1, 1, 9, 0 , 0);
        Timestamp originalTimeStamp = new Timestamp(testDate1.getMillis());
        Timestamp compareTimeStamp = new Timestamp(testDate2.getMillis());
        Timestamp newTimeStamp = TKUtils.subtractOneSecondFromTimestamp(originalTimeStamp);
        Assert.assertEquals("TimeStamp is Not Correct",compareTimeStamp,newTimeStamp);
    }

    @Test
    public void testFormatDate() throws Exception {
        LocalDate localDate = new LocalDate(2013,1,1);
        String formattedDate = TKUtils.formatDate(localDate);
        Assert.assertEquals("Date Format is not correct", "01/01/2013", formattedDate);
    }

    @Test
    public void testFormatDateTimeShort() throws Exception {
        DateTime dateTime = new DateTime(2013, 1 , 1, 0 , 0, 0);
        String formattedDate = TKUtils.formatDateTimeShort(dateTime);
        Assert.assertEquals("Date Format is not correct", "01/01/2013", formattedDate);
    }

    @Test
    public void testFormatDateTimeLong() throws Exception {
        DateTime dateTime = new DateTime(2013, 1 , 1, 9 , 30, 15);
        String formattedDate = TKUtils.formatDateTimeLong(dateTime);
        Assert.assertEquals("Date Format is not correct", "01/01/2013 09:30:15", formattedDate);
    }

    @Test
    public void testFormatDateString() throws Exception {
        String date = "01/01/2013";
        LocalDate compareDate = new LocalDate(2013,1,1);
        LocalDate localDate = TKUtils.formatDateString(date);
        Assert.assertEquals("Date is not correct",localDate,compareDate);
    }

    @Test
    public void testFormatTimeShort() throws Exception {
        String dateString = "01/01/2013 09:30:15";
        String timeShortString = TKUtils.formatTimeShort(dateString);
        Assert.assertEquals("Time Format is not correct","09:30",timeShortString);

    }
    @Test
    public void testFormatDateTimeString() throws Exception {
        String dateTimeString = "01/01/2013";
        DateTime compareDateTime = new DateTime(2013,1,1,0,0,0);
        DateTime dateTime = TKUtils.formatDateTimeString(dateTimeString);
        Assert.assertEquals("DateTime is not Correct",compareDateTime,dateTime);
    }

    @Test
    public void testFormatDateTimeStringNoTimeZone() throws Exception {
        String dateTimeString = "01/01/2013";
        DateTime compareDateTime = new DateTime(2013,1,1,0,0,0);
        DateTime dateTime = TKUtils.formatDateTimeStringNoTimezone(dateTimeString);
        Assert.assertEquals("DateTime is not Correct",compareDateTime,dateTime);
    }

    @Test
    public void testGetTimezoneOffset() throws Exception {
        DateTime date = new DateTime(DateTimeZone.forID("Etc/GMT+5"));
        String timeZoneOffset = TKUtils.getTimezoneOffset(date);
        Assert.assertEquals("wrong offset","-0500",timeZoneOffset);
    }

    @Test
    public void testArrayToString() throws Exception {
        String[] stringArray = {"one","two","three"};
        String arrayString = TKUtils.arrayToString(stringArray);
        Assert.assertEquals("String is not correct","onetwothree",arrayString);
    }

    @Test
    public void testGetSessionTimeoutTime() throws Exception {
        int timeOut;
        if(StringUtils.isBlank(ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.SESSION_TIMEOUT))) {
            timeOut = 2700;
        } else {
            timeOut = Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.SESSION_TIMEOUT));
        }
        Assert.assertEquals("Session Time Out is Incorrect",timeOut,TKUtils.getSessionTimeoutTime());
    }

    @Test
    public void testCreateDaySpan() throws Exception {
        DateTime beginDateTime = new DateTime(2013,1,1,0,0,0,TKUtils.getSystemDateTimeZone());
        DateTime endDateTime = new DateTime(2013,1,4,0,0,0,TKUtils.getSystemDateTimeZone());
        List<Interval> daySpanList = TKUtils.createDaySpan(beginDateTime,endDateTime,TKUtils.getSystemDateTimeZone());
        Assert.assertEquals("List Size should be 3",3,daySpanList.size());
        Assert.assertEquals("Start Date should be 01/01/2013",daySpanList.get(0).getStart(),new DateTime(2013,1,1,0,0,0,TKUtils.getSystemDateTimeZone()));
        Assert.assertEquals("End Date should be 01/04/2013",daySpanList.get(daySpanList.size()-1).getEnd(),new DateTime(2013,1,4,0,0,0,TKUtils.getSystemDateTimeZone()));
    }

    @Test
    public void testGetFullWeekDaySpanForPayCalendarEntry() {
        CalendarEntry.Builder payCalendarEntry = CalendarEntry.Builder.create();
        // begin date is a Monday
        payCalendarEntry.setBeginPeriodFullDateTime(new DateTime(2011, 8, 8, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        // end date is a Thursday
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 25, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        List<Interval> intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry.build(), TKUtils.getSystemDateTimeZone() );
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Assert.assertEquals("First Interval should be 08/07/2011", "08/07/2011", format.format(intervals.get(0).getStart().toDate()));
        Assert.assertEquals("Last Interval should be 08/26/2011", "08/26/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

        // begin date is a Sunday
        payCalendarEntry.setBeginPeriodFullDateTime(new DateTime(2011, 8, 14, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry.build(), TKUtils.getSystemDateTimeZone() );
        Assert.assertEquals("First Interval should be 08/14/2011", "08/14/2011", format.format(intervals.get(0).getStart().toDate()));
        Assert.assertEquals("Last Interval should be 08/26/2011", "08/26/2011",  format.format(intervals.get(intervals.size()-1).getStart().toDate()));

        // end date is a Sunday
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 28, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry.build(), TKUtils.getSystemDateTimeZone() );
        Assert.assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
        Assert.assertEquals("Last Interval should be 09/02/2011", "09/02/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

        // end date is a Sunday and end time is 0:00
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 28, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry.build(), TKUtils.getSystemDateTimeZone() );
        Assert.assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
        Assert.assertEquals("Last Interval should be 08/27/2011", "08/27/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

        // end date is a Monday and end time is 0:00
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 29, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry.build(), TKUtils.getSystemDateTimeZone() );
        Assert.assertEquals("First Interval should be 08/14/2011", "08/14/2011",format.format(intervals.get(0).getStart().toDate()));
        Assert.assertEquals("Last Interval should be 09/03/2011", "09/03/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

        //test overload method
        payCalendarEntry.setBeginPeriodFullDateTime(new DateTime(2011, 8, 8, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        payCalendarEntry.setEndPeriodFullDateTime(new DateTime(2011, 8, 25, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone()));
        intervals = TKUtils.getFullWeekDaySpanForCalendarEntry(payCalendarEntry.build());
        Assert.assertEquals("First Interval should be 08/07/2011", "08/07/2011", format.format(intervals.get(0).getStart().toDate()));
        Assert.assertEquals("Last Interval should be 08/26/2011", "08/26/2011", format.format(intervals.get(intervals.size()-1).getStart().toDate()));

    }

    @Test
    public void testisWeekend() throws Exception {
        DateTime saturday = new DateTime(2013,10,26,0,0,0);
        DateTime sunday = new DateTime(2013,10,27,0,0,0);
        DateTime monday = new DateTime(2013,10,28,0,0,0);
        Assert.assertEquals("This should be true",true,TKUtils.isWeekend(saturday));
        Assert.assertEquals("This should be true",true,TKUtils.isWeekend(sunday));
        Assert.assertEquals("This should be false",false,TKUtils.isWeekend(monday));
    }

	@Test
	public void testFromDateString() {
		String dateString = "01/01/2012..12/31/2012";
		String fromDateString = TKUtils.getFromDateString(dateString);
		assertTrue("fromDateString should be 01/01/2012, not " + fromDateString, fromDateString.equals("01/01/2012"));
		Assert.assertNotNull(TKUtils.formatDateString(fromDateString));

		dateString = ">=2/01/2012";
		fromDateString = TKUtils.getFromDateString(dateString);
		assertTrue("fromDateString should be 2/01/2012, not " + fromDateString, fromDateString.equals("2/01/2012"));
		Assert.assertNotNull(TKUtils.formatDateString(fromDateString));
	}

	@Test
	public void testToDateString() {
		String dateString = "01/01/2012..12/31/2012";
		String toDateString = TKUtils.getToDateString(dateString);
		assertTrue("toDateString should be 12/31/2012, not " + toDateString, toDateString.equals("12/31/2012"));
		Assert.assertNotNull(TKUtils.formatDateString(toDateString));

		dateString = "<=2/01/2012";
		toDateString = TKUtils.getToDateString(dateString);
        assertTrue("toDateString should be 2/01/2012, not " + toDateString, toDateString.equals("2/01/2012"));
        Assert.assertNotNull(TKUtils.formatDateString(toDateString));
	}
	
	@Test
	public void testConvertTimeForDifferentTimeZone() {
		// it's hard to test this functionality since it depends on the sytem time the user is running the test from
		// picked Alaska time since we have no user working from Alaska time yet
		DateTimeZone fromTimeZone = TKUtils.getSystemDateTimeZone();
		DateTimeZone toTimeZone = DateTimeZone.forID("America/Anchorage");
		DateTime originalDateTime = new DateTime(2011, 8, 28, 0, 0, 0, 0, fromTimeZone);
		
		DateTime newDateTime = TKUtils.convertTimeForDifferentTimeZone(originalDateTime, fromTimeZone, toTimeZone);	
		assertNotNull(newDateTime);
		assertTrue("newDateTime should be different than originalDateTime", !newDateTime.equals(originalDateTime));
		
		newDateTime = TKUtils.convertTimeForDifferentTimeZone(originalDateTime, null, toTimeZone);
		assertTrue("newDateTime should be the same as originalDateTime", newDateTime.equals(originalDateTime));
		
		newDateTime = TKUtils.convertTimeForDifferentTimeZone(originalDateTime, null, null);
		assertTrue("newDateTime should be the same as originalDateTime", newDateTime.equals(originalDateTime));
		
		newDateTime = TKUtils.convertTimeForDifferentTimeZone(originalDateTime, fromTimeZone, null);
		assertTrue("newDateTime should be the same as originalDateTime", newDateTime.equals(originalDateTime));
	}


    @Test
    public void testisDateEqualOrBetween() throws Exception {
        //equals
        DateTime equalsDate = new DateTime(2013,1,1,0,0,0);
        assertTrue("This should be true", TKUtils.isDateEqualOrBetween(equalsDate, "01/01/2013..01/07/2013"));

        //between
        DateTime betweenDate = new DateTime(2013,1,4,0,0,0);
        assertTrue("This should be true", TKUtils.isDateEqualOrBetween(betweenDate, "01/01/2013..01/07/2013"));

    }
    @Test
    public void testgetRandomColor() throws Exception {
        TreeSet<String> colorSet = new TreeSet<String>();
        colorSet.add("#FFFFFF");
        String color = TKUtils.getRandomColor(colorSet);
        Pattern colorPattern = Pattern.compile("^#(([0-9a-fA-F]{2}){3}|([0-9a-fA-F]){3})$");
        Matcher colorMatcher = colorPattern.matcher(color);
        assertTrue(colorMatcher.find());
    }

    @Test
    public void testcleanNumeric() throws Exception {
        Assert.assertEquals("Expected 1234",new BigDecimal("1234"),TKUtils.cleanNumeric("1234"));
        Assert.assertEquals("Expected 1234",new BigDecimal("1234"),TKUtils.cleanNumeric("12$34A"));
        Assert.assertEquals("Expected 1234",new BigDecimal("1234.45"),TKUtils.cleanNumeric("12$34A..45"));
        Assert.assertEquals("Expected 1234",new BigDecimal("-1234.45"),TKUtils.cleanNumeric("-12$-34A..45"));
    }

    @Test
    public void testgetDocumentDescription() throws Exception {
        String docDescription = TKUtils.getDocumentDescription("admin",new LocalDate(2010,1,1));
        Assert.assertEquals("doc Description is wrong","admin, admin (admin)  - 01/01/2010",docDescription);
    }
}