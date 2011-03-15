package org.kuali.hr.time.util;

import org.joda.time.*;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class TKUtils {


	public static java.sql.Date getCurrentDate() {
		return getTimelessDate(null);
	}

	/**
	 * Returns a enforced timeless version of the provided date, if the date is
	 * null the current date is returned.
	 * 
	 * @param date
	 * @return A java.sql.Date version of the provided date, if provided date is
	 *         null, the current date is returned.
	 */
	public static java.sql.Date getTimelessDate(java.util.Date date) {
		java.sql.Date jsd = null;
		if (date == null) {
			jsd = new java.sql.Date(System.currentTimeMillis());
		} else {
			jsd = new java.sql.Date(date.getTime());
		}
		return jsd;
	}

	public static long getDaysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static long getDaysBetween(java.util.Date startDate, java.util.Date endDate) {
		Calendar beginCal = GregorianCalendar.getInstance();
		Calendar endCal = GregorianCalendar.getInstance();
		beginCal.setTime(startDate);
		endCal.setTime(endDate);

		return getDaysBetween(beginCal, endCal);
	}

	public static BigDecimal getHoursBetween(long start, long end) {
		long diff = end - start;
		return new BigDecimal((diff / 3600000.0) % 24).setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING).abs();
	}



	public static int getNumberOfWeeks(java.util.Date beginDate, java.util.Date endDate) {

		DateTime beginTime = new DateTime(beginDate);
		DateTime endTime = new DateTime(endDate);

		int numOfDays = Days.daysBetween(beginTime, endTime).getDays();
		int numOfWeeks = numOfDays / 7;
		if (numOfDays % 7 != 0) {
			numOfWeeks++;
		}
		return numOfWeeks;
	}

	public static String formatAssignmentKey(Long jobNumber, Long workArea, Long task) {
		Long taskLong = task;
		if(taskLong == null) {
			taskLong = new Long("0");
		}
		return jobNumber + TkConstants.ASSIGNMENT_KEY_DELIMITER + workArea + TkConstants.ASSIGNMENT_KEY_DELIMITER + taskLong;
	}
	
	public static Map<String,String> formatAssignmentDescription(Assignment assignment) {
		Map<String,String> assignmentDescriptions = new LinkedHashMap<String,String>();
		Long task = assignment.getTask();
		if(task == null) {
			task = new Long("0");
		}
		String assignmentDescKey  = formatAssignmentKey(assignment.getJobNumber(), assignment.getWorkArea(), task);
		String assignmentDescValue = getAssignmentString(assignment);  
		assignmentDescriptions.put(assignmentDescKey, assignmentDescValue);
		
		return assignmentDescriptions;
	}
	
	public static String getAssignmentString(Assignment assignment) {
		return assignment.getWorkAreaObj().getDescription() + " : $" + assignment.getJob().getCompRate() + " Rcd " + assignment.getJobNumber() + " " + assignment.getJob().getDept();
	}

	public static List<Interval> getDaySpanForPayCalendarEntry(PayCalendarEntries payCalendarEntry) {
		DateTime beginDateTime = new DateTime(payCalendarEntry.getBeginPeriodDateTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime endDateTime = new DateTime(payCalendarEntry.getEndPeriodDateTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);

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

	/**
	 * Includes partial weeks if the time range provided does not divide evenly
	 * into 7 day spans.
	 * 
	 * @param beginDate
	 *               Starting Date/Time
	 * @param endDate
	 *            Ending Date/Time
	 * @return A List of Intervals of 7 day spans. The last Interval in the list
	 *         may be less than seven days.
	 */
	public static List<Interval> getWeekIntervals(java.util.Date beginDate, java.util.Date endDate) {
		List<Interval> intervals = new ArrayList<Interval>();
		DateTime beginTime = new DateTime(beginDate);
		DateTime endTime = new DateTime(endDate);

		int dayIncrement = 7;
		DateTime previous = beginTime;
		DateTime nextTime = previous.plusDays(dayIncrement);
		while (nextTime.isBefore(endTime)) {
			Interval interval = new Interval(previous, nextTime);
			intervals.add(interval);
			previous = nextTime;
			nextTime = previous.plusDays(dayIncrement);
		}

		if (previous.isBefore(endTime)) {
			// add a partial week.
			Interval interval = new Interval(previous, endTime);
			intervals.add(interval);
		}

		return intervals;
	}
	
	public static long convertHoursToMillis(BigDecimal hours) {
		return hours.multiply(TkConstants.BIG_DECIMAL_MS_IN_H, TkConstants.MATH_CONTEXT).longValue();
	}
	
	public static BigDecimal convertMillisToHours(long millis) {
		return (new BigDecimal(millis)).divide(TkConstants.BIG_DECIMAL_MS_IN_H, TkConstants.MATH_CONTEXT);
	}
	
	public static BigDecimal convertMillisToDays(long millis){
		BigDecimal hrs = convertMillisToHours(millis);
		return hrs.divide(TkConstants.BIG_DECIMAL_HRS_IN_DAY, TkConstants.MATH_CONTEXT);
	}
	
	public static BigDecimal convertMinutesToHours(BigDecimal minutes) {
		return minutes.divide(TkConstants.BIG_DECIMAL_60, TkConstants.MATH_CONTEXT);
	}
	
	public static int convertMillisToWholeDays(long millis){
		BigDecimal days = convertMillisToDays(millis);
		return Integer.parseInt(days.setScale(0, BigDecimal.ROUND_UP).toString());
	}
	
	/*
	 * Compares and confirms if the start of the day is at midnight or on a virtual day boundary
	 * returns true if at midnight false otherwise(assuming 24 hr days)
	 */
	public static boolean isVirtualWorkDay(Calendar payCalendarStartTime){
		return (payCalendarStartTime.get(Calendar.HOUR_OF_DAY) != 0 || payCalendarStartTime.get(Calendar.MINUTE) != 0 
				&& payCalendarStartTime.get(Calendar.AM_PM) != Calendar.AM);
	}

    /**
     *
     * @param dateTimeString (the format is 11/17/2010 8:0)
     * @return Timestamp
     */
	public static Timestamp convertDateStringToTimestamp(String dateTimeString) {
		// the date/time format is defined in tk.calendar.js. For now, the format is 11/17/2010 8:0
		String[] input = dateTimeString.split(" ");
		String[] dateString = input[0].split("/");
		String[] timeString = input[1].split(":");
		
		DateTimeZone dtz = DateTimeZone.forID(TkServiceLocator.getTimezoneService().getUserTimeZone());
		
		// this is from the jodattime javadoc:
		// DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond, DateTimeZone zone)
        // Noted that the month value is the actual month which is different than the java date object where the month value is the current month minus 1.
        // I tried to use the actual month in the code as much as I can to reduce the convertions.
		DateTime dateTime = new DateTime(
				Integer.parseInt(dateString[2]), 
				Integer.parseInt(dateString[0]), 
				Integer.parseInt(dateString[1]), 
				Integer.parseInt(timeString[0]), 
				Integer.parseInt(timeString[1]), 
				0, 0, dtz); 
		
		return new Timestamp(dateTime.getMillis());
	}
}
