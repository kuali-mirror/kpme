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
package org.kuali.kpme.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.*;
import org.kuali.kpme.core.api.KPMEConstants;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.util.KpmeUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;

public class TKUtils {

    private static final Logger LOG = Logger.getLogger(TKUtils.class);

    public static boolean singleGroupKeyExists()
    {
        if (getSingleGroupKey() != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public static String getSingleGroupKey(LocalDate asOfDate)
    {
        String singleGroupKey = null;

        List<? extends HrGroupKey> groupKeyList = HrServiceLocator.getHrGroupKeyService().getAllActiveHrGroupKeys(asOfDate);
        if((CollectionUtils.isNotEmpty(groupKeyList)) && (groupKeyList.size() == 1)) {
            singleGroupKey = groupKeyList.get(0).getGroupKeyCode();
        }

        return singleGroupKey;
    }

    public static String getSingleGroupKey()
    {
        return getSingleGroupKey(LocalDate.now());
    }

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


    public static Map<String, String> formatAssignmentDescription(Assignment assignment) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();

        String assignmentDescKey = KpmeUtils.formatAssignmentKey(assignment.getGroupKeyCode(), assignment.getJobNumber(), assignment.getWorkArea(), assignment.getTask());
        String assignmentDescValue = HrServiceLocator.getAssignmentService().getAssignmentDescription(assignment.getPrincipalId(), assignment.getGroupKeyCode(), assignment.getJobNumber(), assignment.getWorkArea(), assignment.getTask(), assignment.getEffectiveLocalDate());
        assignmentDescriptions.put(assignmentDescKey, assignmentDescValue);

        return assignmentDescriptions;
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

        DateTimeZone dtz = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getTargetUserTimezone());

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
    
    public static DateTime convertDateStringToDateTimeWithTimeZone(String dateStr, String timeStr, DateTimeZone dtz) {
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

    public static String getIPAddressFromRequest(HttpServletRequest request) {
        // Check for IPv6 addresses - Not sure what to do with them at this point.
        // TODO: IPv6 - I see these on my local machine.
        String fwdIp = request.getHeader("X-Forwarded-For");
        if (fwdIp != null) {
            LOG.info("Forwarded IP: " + fwdIp);
            return fwdIp;
        }

        String ip = request.getRemoteAddr();
        if (ip.indexOf(':') > -1) {
            LOG.warn("ignoring IPv6 address for clock-in: " + ip);
            ip = "";
        }

        return ip;
    }

    //Used to preserve active row fetching based on max(timestamp)
    public static Timestamp subtractOneSecondFromTimestamp(Timestamp originalTimestamp) {
        DateTime dt = new DateTime(originalTimestamp);
        dt = dt.minusSeconds(1);
        return new Timestamp(dt.getMillis());
    }

    public static String formatDate(LocalDate localDate) {
        if (localDate == null) {
            return StringUtils.EMPTY;
        }
        return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.print(localDate);
    }

    public static String formatDateTimeShort(DateTime dateTime) {
        if (dateTime == null) {
            return StringUtils.EMPTY;
        }
        return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.print(dateTime);
    }
    
    public static String formatDateTimeLong(DateTime dateTime){
        if (dateTime == null) {
            return StringUtils.EMPTY;
        }
        return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT_WITH_SEC.print(dateTime);
    }

    public static LocalDate formatDateString(String date){
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseLocalDate(date);
    }
    
    public static String formatTimeShort(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        DateTime tempDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT_WITH_SEC.parseDateTime(dateString);
    	return HrConstants.DateTimeFormats.BASIC_TIME_FORMAT.print(tempDate);
    }
    
    public static DateTime formatDateTimeString(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(dateTime).withZone(HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback());
    }

    public static DateTime formatDateTimeStringNoTimezone(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        try {
            return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.parseDateTime(dateTime);
        } catch (IllegalArgumentException e) {
            return HrConstants.DateTimeFormats.BASIC_DATE_FORMAT_WITH_SEC.parseDateTime(dateTime);
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
    	return getDaySpanForCalendarEntry(calendarEntry, HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback());
    }

    public static List<Interval> getFullWeekDaySpanForCalendarEntry(CalendarEntry calendarEntry) {
        return getFullWeekDaySpanForCalendarEntry(calendarEntry, HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback());
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
			 
			 if (fromDate != null ? (date.isEqual(fromDate) || date.isAfter(fromDate)) :
                     toDate != null ? (date.isBefore(toDate) || date.isEqual(toDate)) : true) {
				 valid = true;
			 }
		 }
		 
		 return valid;
	 }
	 
	 public static String getRandomColor(Set<String> randomColors) {
			String[] letters = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F".split(",");
			String color = "#";
			for (int i = 0; i < 6; i++) {
				int index = (int) Math.round(Math.random() * 15);
				color += letters[index];
			}
			if (randomColors.contains(color)) {
				color = getRandomColor(randomColors);
			}
			return color;
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
	
	public static String getDocumentDescription(String principalId, LocalDate effectiveDate) {
		StringBuffer docDescription = new StringBuffer();
		EntityNamePrincipalName principalName = null;
        if (principalId != null) {
            principalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
        }
        String personName = (principalName != null  && principalName.getDefaultName() != null) ? principalName.getDefaultName().getCompositeName() : StringUtils.EMPTY;
        String date = TKUtils.formatDate(effectiveDate);
        docDescription.append(personName + " (" + principalId + ")  - " + date);
		return docDescription.toString();
	}
	
	/*
	 * aDateTime is the dateTime we would like to display in the fromTimeZone. 
	 * The results of the method is the time showing in the toTimeZone considering the time difference between these two time zones
	 */
	public static DateTime convertTimeForDifferentTimeZone(DateTime aDateTime, DateTimeZone fromTimeZone, DateTimeZone toTimeZone) {
		if(fromTimeZone == null || toTimeZone == null || fromTimeZone.equals(toTimeZone))
			return aDateTime;	// no conversion is needed
		
		Long millisOfSysTime = fromTimeZone.getMillisKeepLocal(toTimeZone, aDateTime.getMillis());
		DateTime toTime = new DateTime(millisOfSysTime);
		
		return toTime;
	}
	
	public static DateTime convertDateTimeToDifferentTimezone(DateTime aDateTime, DateTimeZone fromTimeZone, DateTimeZone toTimeZone) {
		if(fromTimeZone == null || toTimeZone == null || fromTimeZone.equals(toTimeZone))
			return aDateTime;	// no conversion is needed
		
		Long millisOfSysTime = toTimeZone.getMillisKeepLocal(fromTimeZone, aDateTime.getMillis());
		DateTime toTime = new DateTime(millisOfSysTime);
		
		return toTime;
	}


    public static Assignment getAssignmentWithKey(List<Assignment> assignments, AssignmentDescriptionKey assignmentDescriptionKey) {

        for (Assignment assignment : assignments) {
            if (StringUtils.equals(assignment.getGroupKeyCode(), assignmentDescriptionKey.getGroupKeyCode()) &&
                    assignment.getJobNumber().compareTo(assignmentDescriptionKey.getJobNumber()) == 0 &&
                    assignment.getWorkArea().compareTo(assignmentDescriptionKey.getWorkArea()) == 0 &&
                    assignment.getTask().compareTo(assignmentDescriptionKey.getTask()) == 0) {
                return assignment;
            }
        }

        return null;
    }



}
