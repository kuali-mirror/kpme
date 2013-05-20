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
package org.kuali.kpme.core.util;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class TKUtils {

    private static final Logger LOG = Logger.getLogger(TKUtils.class);

    /**
     * @param dateString the format has to be mm/dd/yyyy
     * @return dayOfMonth
     */
    public static String getDayOfMonthFromDateString(String dateString) {
        String[] date = dateString.split("/");
        return date[1];
    }

    public static String getSystemTimeZone() {
        String configTimezone = TimeZone.getDefault().getID();
        if (ConfigContext.getCurrentContextConfig() != null
                && StringUtils.isNotBlank(ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.KPME_SYSTEM_TIMEZONE).trim())) {
            String tempTimeZoneId = ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.KPME_SYSTEM_TIMEZONE);

            if (TimeZone.getTimeZone(tempTimeZoneId) != null) {
                configTimezone = ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.KPME_SYSTEM_TIMEZONE);
            } else {
                LOG.error("Timezone set by configuration parameter " + KPMEConstants.ConfigSettings.KPME_SYSTEM_TIMEZONE + " is not a valid time zone id.  Using the systems default time zone instead.");
            }
        }


        return configTimezone;
    }

    public static DateTimeZone getSystemDateTimeZone() {
        return DateTimeZone.forID(TKUtils.getSystemTimeZone());
    }

    public static final LocalDate END_OF_TIME = new LocalDate(9999, 12, 31);

    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
    	long daysBetween = 0;
    	
    	LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            daysBetween++;
            currentDate = currentDate.plusDays(1);
        }
        
        return daysBetween;
    }

    public static BigDecimal getHoursBetween(long start, long end) {
        long diff = end - start;
        BigDecimal hrsReminder = new BigDecimal((diff / 3600000.0) % 24);
        // if the hours is exact duplicate of 24 hours
        if (hrsReminder.compareTo(BigDecimal.ZERO) == 0 && diff > 0) {
            return new BigDecimal(diff / 3600000.0).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING).abs();
        }
        return hrsReminder.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING).abs();
    }

    public static String formatAssignmentKey(Long jobNumber, Long workArea, Long task) {
    	String jobNumberString = ObjectUtils.toString(jobNumber, "0");
    	String workAreaString = ObjectUtils.toString(workArea, "0");
    	String taskString = ObjectUtils.toString(task, "0");
        return jobNumberString + HrConstants.ASSIGNMENT_KEY_DELIMITER + workAreaString + HrConstants.ASSIGNMENT_KEY_DELIMITER + taskString;
    }

    public static Map<String, String> formatAssignmentDescription(Assignment assignment) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        String assignmentDescKey = formatAssignmentKey(assignment.getJobNumber(), assignment.getWorkArea(), assignment.getTask());
        String assignmentDescValue = getAssignmentString(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getWorkArea(), assignment.getTask(), assignment.getEffectiveLocalDate());
        assignmentDescriptions.put(assignmentDescKey, assignmentDescValue);

        return assignmentDescriptions;
    }

    public static String getAssignmentString(String principalId, Long jobNumber, Long workArea, Long task, LocalDate asOfDate) {
    	StringBuilder builder = new StringBuilder();
    	
    	if (jobNumber != null && workArea != null && task != null) {
        	Job jobObj = HrServiceLocator.getJobService().getJob(principalId, jobNumber, asOfDate);
        	WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
        	Task taskObj = HrServiceLocator.getTaskService().getTask(task, asOfDate);
        	
        	String workAreaDescription = workAreaObj != null ? workAreaObj.getDescription() : StringUtils.EMPTY;
        	BigDecimal compensationRate = jobObj != null ? jobObj.getCompRate().setScale(HrConstants.BIG_DECIMAL_SCALE) : BigDecimal.ZERO;
        	String department = jobObj != null ? jobObj.getDept() : StringUtils.EMPTY;
        	String taskDescription = taskObj != null && !HrConstants.TASK_DEFAULT_DESP.equals(taskObj.getDescription()) ? taskObj.getDescription() : StringUtils.EMPTY;
        	
        	builder.append(workAreaDescription).append(" : $").append(compensationRate).append(" Rcd ").append(jobNumber).append(" ").append(department);
        	if (StringUtils.isNotBlank(taskDescription)) {
        		builder.append(" ").append(taskDescription);
        	}
        }
        
        return builder.toString();
    }

    /**
     * Constructs a list of Day Spans for the pay calendar entry provided. You
     * must also provide a DateTimeZone so that we can create relative boundaries.
     *
     * @param calendarEntry
     * @param timeZone
     * @return
     */
    public static List<Interval> getDaySpanForCalendarEntry(CalendarEntry calendarEntry, DateTimeZone timeZone) {
        DateTime beginDateTime = calendarEntry.getBeginPeriodLocalDateTime().toDateTime(timeZone);
        DateTime endDateTime = calendarEntry.getEndPeriodLocalDateTime().toDateTime(timeZone);

        List<Interval> dayIntervals = new ArrayList<Interval>();

        DateTime currDateTime = beginDateTime;
        while (currDateTime.isBefore(endDateTime)) {
            DateTime prevDateTime = currDateTime;
            currDateTime = currDateTime.plusDays(1);
            Interval daySpan = new Interval(prevDateTime, currDateTime);
            dayIntervals.add(daySpan);
        }

        return dayIntervals;
    }

    public static long convertHoursToMillis(BigDecimal hours) {
        return hours.multiply(HrConstants.BIG_DECIMAL_MS_IN_H, HrConstants.MATH_CONTEXT).longValue();
    }

    public static BigDecimal convertMillisToHours(long millis) {
        return (new BigDecimal(millis)).divide(HrConstants.BIG_DECIMAL_MS_IN_H, HrConstants.MATH_CONTEXT);
    }

    public static BigDecimal convertMillisToMinutes(long millis) {
        return (new BigDecimal(millis)).divide(HrConstants.BIG_DECIMAL_MS_IN_M, HrConstants.MATH_CONTEXT);
    }
    
    public static BigDecimal convertMillisToDays(long millis) {
        BigDecimal hrs = convertMillisToHours(millis);
        return hrs.divide(HrConstants.BIG_DECIMAL_HRS_IN_DAY, HrConstants.MATH_CONTEXT);
    }

    public static BigDecimal convertMinutesToHours(BigDecimal minutes) {
        return minutes.divide(HrConstants.BIG_DECIMAL_60, HrConstants.MATH_CONTEXT);
    }

    public static int convertMillisToWholeDays(long millis) {
        BigDecimal days = convertMillisToDays(millis);
        return Integer.parseInt(days.setScale(0, BigDecimal.ROUND_UP).toString());
    }

    /*
      * Compares and confirms if the start of the day is at midnight or on a virtual day boundary
      * returns true if at midnight false otherwise(assuming 24 hr days)
      */
    public static boolean isVirtualWorkDay(DateTime beginPeriodDateTime) {
        return (beginPeriodDateTime.getHourOfDay() != 0 || beginPeriodDateTime.getMinuteOfHour() != 0
                && beginPeriodDateTime.get(DateTimeFieldType.halfdayOfDay()) != DateTimeConstants.AM);
    }

    /**
     * Creates a Timestamp object using Jodatime as an intermediate data structure
     * from the provided date and time string. (From the form POST and javascript
     * formats)
     *
     * @param dateStr (the format is 01/01/2011)
     * @param timeStr (the format is 8:0)
     * @return Timestamp
     */
    public static DateTime convertDateStringToDateTime(String dateStr, String timeStr) {
        // the date/time format is defined in tk.calendar.js. For now, the format is 11/17/2010 8:0
        String[] date = dateStr.split("/");
        String[] time = timeStr.split(":");

        DateTimeZone dtz = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone());

        // this is from the jodattime javadoc:
        // DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond, DateTimeZone zone)
        // Noted that the month value is the actual month which is different than the java date object where the month value is the current month minus 1.
        // I tried to use the actual month in the code as much as I can to reduce the convertions.
        DateTime dateTime = new DateTime(
                Integer.parseInt(date[2]),
                Integer.parseInt(date[0]),
                Integer.parseInt(date[1]),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1]),
                0, 0, dtz);

        return dateTime;
    }
    
    public static DateTime convertDateStringToDateTimeWithoutZone(String dateStr, String timeStr) {
        // the date/time format is defined in tk.calendar.js. For now, the format is 11/17/2010 8:0
        String[] date = dateStr.split("/");
        String[] time = timeStr.split(":");

        // this is from the jodattime javadoc:
        // DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond, DateTimeZone zone)
        // Noted that the month value is the actual month which is different than the java date object where the month value is the current month minus 1.
        // I tried to use the actual month in the code as much as I can to reduce the convertions.
        DateTime dateTime = new DateTime(
                Integer.parseInt(date[2]),
                Integer.parseInt(date[0]),
                Integer.parseInt(date[1]),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1]),
                0, 0);

        return dateTime;
    }
    
   public static String getIPAddressFromRequest(String remoteAddress) {
        // Check for IPv6 addresses - Not sure what to do with them at this point.
        // TODO: IPv6 - I see these on my local machine.
        if (remoteAddress.indexOf(':') > -1) {
            LOG.warn("ignoring IPv6 address for clock-in: " + remoteAddress);
            remoteAddress = "";
        }

        return remoteAddress;
    }

    public static String getIPNumber() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
    //Used to preserve active row fetching based on max(timestamp)
    public static Timestamp subtractOneSecondFromTimestamp(Timestamp originalTimestamp) {
        DateTime dt = new DateTime(originalTimestamp);
        dt = dt.minusSeconds(1);
        return new Timestamp(dt.getMillis());
    }

    public static String formatDate(LocalDate localDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(localDate.toDate());
    }

    public static String formatDateTimeShort(DateTime dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dateTime.toDate());
    }
    
    public static String formatDateTimeLong(DateTime dateTime){
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(dateTime.toDate());
    }
    
    public static LocalDate formatDateString(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
			return LocalDate.fromDateFields(sdf.parse(date));
		} catch (ParseException e) {
			return null;
		}
    }
    
    public static DateTime formatDateTimeString(String dateTime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        DateTimeZone dtz = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone());
    	try {
			return new DateTime(sdf.parse(dateTime)).withZone(dtz);
		} catch (ParseException e) {
			return null;
		}
    }

    public static DateTime formatDateTimeStringNoTimezone(String dateTime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
			return new DateTime(sdf.parse(dateTime));
		} catch (ParseException e) {
			return null;
		}
    }
    
    /**
     * Method to obtain the timezone offset string for the specified date time.
     *
     * Examples:
     *
     * -0500
     * -0800
     *
     * @param date
     * @return
     */
    public static String getTimezoneOffset(DateTime date) {
        StringBuilder o = new StringBuilder();

        int offsetms = date.getZone().getOffset(date);
        boolean negative = offsetms < 0;
        if (negative) offsetms = offsetms * -1;

        Period period = new Period(offsetms);
        if (negative) o.append('-');
        o.append(StringUtils.leftPad(period.getHours() + "", 2, '0'));
        o.append(StringUtils.leftPad(period.getMinutes() + "", 2, '0'));

        return o.toString();
    }

    public static String arrayToString(String[] stringArray) {
        StringBuilder str = new StringBuilder();
        for (String string : stringArray) {
            str.append(string);
        }
        return str.toString();
    }

    /**
     * Get the session timeout time. If it's not defined in the (local) config file, give it a default time.
     */
    public static int getSessionTimeoutTime() {
        return StringUtils.isBlank(ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.SESSION_TIMEOUT))
                ? 2700 :
                Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty(KPMEConstants.ConfigSettings.SESSION_TIMEOUT));
    }
    
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    public static List<Interval> createDaySpan(DateTime beginDateTime, DateTime endDateTime, DateTimeZone zone) {
        beginDateTime = beginDateTime.toDateTime(zone);
        endDateTime = endDateTime.toDateTime(zone);
        List<Interval> dayIntervals = new ArrayList<Interval>();

        DateTime currDateTime = beginDateTime;
        while (currDateTime.isBefore(endDateTime)) {
            DateTime prevDateTime = currDateTime;
            currDateTime = currDateTime.plusDays(1);
            Interval daySpan = new Interval(prevDateTime, currDateTime);
            dayIntervals.add(daySpan);
        }

        return dayIntervals;
    }
    
    public static List<Interval> getDaySpanForCalendarEntry(CalendarEntry calendarEntry) {
        return getDaySpanForCalendarEntry(calendarEntry, HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }

    public static List<Interval> getFullWeekDaySpanForCalendarEntry(CalendarEntry calendarEntry) {
        return getFullWeekDaySpanForCalendarEntry(calendarEntry, HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }
    
    public static List<Interval> getFullWeekDaySpanForCalendarEntry(CalendarEntry calendarEntry, DateTimeZone timeZone) {
        DateTime beginDateTime = calendarEntry.getBeginPeriodLocalDateTime().toDateTime(timeZone);
        DateTime endDateTime = calendarEntry.getEndPeriodLocalDateTime().toDateTime(timeZone);

        List<Interval> dayIntervals = new ArrayList<Interval>();

        DateTime currDateTime = beginDateTime;
        if (beginDateTime.getDayOfWeek() != 7) {
            currDateTime = beginDateTime.plusDays(0 - beginDateTime.getDayOfWeek());
        }

        int afterEndDate = 6 - endDateTime.getDayOfWeek();
        if (endDateTime.getDayOfWeek() == 7 && endDateTime.getHourOfDay() != 0) {
            afterEndDate = 6;
        }
        if (endDateTime.getHourOfDay() == 0) {
            afterEndDate += 1;
        }
        DateTime aDate = endDateTime.plusDays(afterEndDate);
        while (currDateTime.isBefore(aDate)) {
            DateTime prevDateTime = currDateTime;
            currDateTime = currDateTime.plusDays(1);
            Interval daySpan = new Interval(prevDateTime, currDateTime);
            dayIntervals.add(daySpan);
        }

        return dayIntervals;
    }
    
    public static int getWorkDays(DateTime startDate, DateTime endDate) {
    	int workDays = 0;

		DateTime currentDate = startDate;
		while (!currentDate.isAfter(endDate)) {
			if (!isWeekend(currentDate)) {
				workDays++;		
			}
			currentDate = currentDate.plusDays(1);
		}
		
    	return workDays;
    }
    
    public static boolean isWeekend(DateTime date) {
    	return date.getDayOfWeek() == DateTimeConstants.SATURDAY || date.getDayOfWeek() == DateTimeConstants.SUNDAY;
    }
    
    /**
     * Returns effectiveDate "from" date that's passed in through dateString.
     * 
	 * The "from" dateString can either be from the format "fromDate..toDate" or ">=fromDate", otherwise an empty string is returned.
	 * 
     * @param dateString
     * @return
     */
    public static String getFromDateString(String dateString) {
    	String fromDateString = StringUtils.EMPTY;
    	
    	if (StringUtils.startsWith(dateString, ">=")) {
    		fromDateString = StringUtils.substringAfter(dateString, ">=");
    	} else if (StringUtils.contains(dateString, "..")) {
    		fromDateString = StringUtils.substringBefore(dateString, "..");
		}
    	
    	return fromDateString;
    }
    
    /**
     * Returns effectiveDate "to" date that's passed in through dateString.
     * 
     * The "to" dateString can either be from the format "fromDate..toDate" or "<=toDate", otherwise an empty string is returned.
     *
     * @param dateString
     * @return
     */
    public static String getToDateString(String dateString) {
    	String toDateString = StringUtils.EMPTY;
    	
    	if (StringUtils.startsWith(dateString, "<=")) {
    		toDateString = StringUtils.substringAfter(dateString, "<=");
    	} else if (StringUtils.contains(dateString, "..")) {
    		toDateString = StringUtils.substringAfter(dateString, "..");
		}
    	
    	return toDateString;
    }
    
	 
	 public static boolean isDateEqualOrBetween(DateTime date, String searchDateString) {
		 boolean valid = false;
		 
		 String fromDateString = TKUtils.getFromDateString(searchDateString);
		 DateTime fromDate = TKUtils.formatDateTimeString(fromDateString);
		 String toDateString = TKUtils.getToDateString(searchDateString);
		 DateTime toDate = TKUtils.formatDateTimeString(toDateString);
		 
		 if (date != null) {
			 if (fromDate != null ? (date.equals(fromDate) || date.isAfter(fromDate)) : true
					 && toDate != null ? (date.isBefore(toDate) || date.equals(toDate)) : true) {
				 valid = true;
			 }
		 }

		 return valid;
	 }
	 
	/*
	 * Cleans a numerical input so that it can be successfully used with lookups
	 */
	public static BigDecimal cleanNumeric( String value ) {
		String cleanedValue = value.replaceAll( "[^-0-9.]", "" );
		// ensure only one "minus" at the beginning, if any
		if ( cleanedValue.lastIndexOf( '-' ) > 0 ) {
			if ( cleanedValue.charAt( 0 ) == '-' ) {
				cleanedValue = "-" + cleanedValue.replaceAll( "-", "" );
			} else {
				cleanedValue = cleanedValue.replaceAll( "-", "" );
			}
		}
		// ensure only one decimal in the string
		int decimalLoc = cleanedValue.lastIndexOf( '.' );
		if ( cleanedValue.indexOf( '.' ) != decimalLoc ) {
			cleanedValue = cleanedValue.substring( 0, decimalLoc ).replaceAll( "\\.", "" ) + cleanedValue.substring( decimalLoc );
		}
		try {
			return new BigDecimal( cleanedValue );
		} catch ( NumberFormatException ex ) {
			GlobalVariables.getMessageMap().putError(KRADConstants.DOCUMENT_ERRORS, RiceKeyConstants.ERROR_CUSTOM, new String[] { "Invalid Numeric Input: " + value });
			return null;
		}
	}

}
