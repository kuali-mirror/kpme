package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.rice.core.api.config.property.ConfigContext;

public class TKUtils {

    private static final Logger LOG = Logger.getLogger(TKUtils.class);

    public static java.sql.Date getCurrentDate() {
        return getTimelessDate(null);
    }

    /**
     * @param dateString the format has to be mm/dd/yyyy
     * @return dayOfMonth
     */
    public static String getDayOfMonthFromDateString(String dateString) {
        String[] date = dateString.split("/");
        return date[1];
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
        BigDecimal hrsReminder = new BigDecimal((diff / 3600000.0) % 24);
        // if the hours is exact duplicate of 24 hours
        if (hrsReminder.compareTo(BigDecimal.ZERO) == 0 && diff > 0) {
            return new BigDecimal(diff / 3600000.0).setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING).abs();
        }
        return hrsReminder.setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING).abs();
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
        if (taskLong == null) {
            taskLong = new Long("0");
        }
        return jobNumber + TkConstants.ASSIGNMENT_KEY_DELIMITER + workArea + TkConstants.ASSIGNMENT_KEY_DELIMITER + taskLong;
    }

    public static Map<String, String> formatAssignmentDescription(Assignment assignment) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        Long task = assignment.getTask();
        if (task == null) {
            task = new Long("0");
        }
        String assignmentDescKey = formatAssignmentKey(assignment.getJobNumber(), assignment.getWorkArea(), task);
        String assignmentDescValue = getAssignmentString(assignment);
        assignmentDescriptions.put(assignmentDescKey, assignmentDescValue);

        return assignmentDescriptions;
    }

    public static String getAssignmentString(Assignment assignment) {


        if (assignment.getWorkAreaObj() == null
                || assignment.getJob() == null
                || assignment.getJobNumber() == null) {
            return "";     // getAssignment() of AssignmentService can return an empty assignment
        }
        
       String stringTemp = assignment.getWorkAreaObj().getDescription() + " : $" 
       				+ assignment.getJob().getCompRate().setScale(TkConstants.BIG_DECIMAL_SCALE) 
       				+ " Rcd " + assignment.getJobNumber() + " " + assignment.getJob().getDept();
       if(assignment.getTask()!= null) {
	       	Task aTask = TkServiceLocator.getTaskService().getTask(assignment.getTask(), assignment.getEffectiveDate());
	       	if(aTask != null) {
	       		// do not display task description if the task is the default one
	        	// default task is created in getTask() of TaskService
	        	if(!aTask.getDescription().equals(TkConstants.TASK_DEFAULT_DESP)) {
	        		stringTemp += " " +  aTask.getDescription();
	        	}
	       	} 
       }
       return stringTemp;
    }

    /**
     * Constructs a list of Day Spans for the pay calendar entry provided. You
     * must also provide a DateTimeZone so that we can create relative boundaries.
     *
     * @param calendarEntry
     * @param timeZone
     * @return
     */
    public static List<Interval> getDaySpanForCalendarEntry(CalendarEntries calendarEntry, DateTimeZone timeZone) {
        DateTime beginDateTime = calendarEntry.getBeginLocalDateTime().toDateTime(timeZone);
        DateTime endDateTime = calendarEntry.getEndLocalDateTime().toDateTime(timeZone);

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
     * @param beginDate Starting Date/Time
     * @param endDate   Ending Date/Time
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

    public static BigDecimal convertMillisToDays(long millis) {
        BigDecimal hrs = convertMillisToHours(millis);
        return hrs.divide(TkConstants.BIG_DECIMAL_HRS_IN_DAY, TkConstants.MATH_CONTEXT);
    }

    public static BigDecimal convertMinutesToHours(BigDecimal minutes) {
        return minutes.divide(TkConstants.BIG_DECIMAL_60, TkConstants.MATH_CONTEXT);
    }

    public static int convertMillisToWholeDays(long millis) {
        BigDecimal days = convertMillisToDays(millis);
        return Integer.parseInt(days.setScale(0, BigDecimal.ROUND_UP).toString());
    }

    /*
      * Compares and confirms if the start of the day is at midnight or on a virtual day boundary
      * returns true if at midnight false otherwise(assuming 24 hr days)
      */
    public static boolean isVirtualWorkDay(Calendar payCalendarStartTime) {
        return (payCalendarStartTime.get(Calendar.HOUR_OF_DAY) != 0 || payCalendarStartTime.get(Calendar.MINUTE) != 0
                && payCalendarStartTime.get(Calendar.AM_PM) != Calendar.AM);
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
    public static Timestamp convertDateStringToTimestamp(String dateStr, String timeStr) {
        // the date/time format is defined in tk.calendar.js. For now, the format is 11/17/2010 8:0
        String[] date = dateStr.split("/");
        String[] time = timeStr.split(":");

        DateTimeZone dtz = DateTimeZone.forID(TkServiceLocator.getTimezoneService().getUserTimezone());

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

        return new Timestamp(dateTime.getMillis());
    }
    
    public static Timestamp convertDateStringToTimestampWithoutZone(String dateStr, String timeStr) {
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

        return new Timestamp(dateTime.getMillis());
    }
    
   public static String getIPAddressFromRequest(HttpServletRequest request) {
        // Check for IPv6 addresses - Not sure what to do with them at this point.
        // TODO: IPv6 - I see these on my local machine.
        String ip = request.getRemoteAddr();
        if (ip.indexOf(':') > -1) {
            LOG.warn("ignoring IPv6 address for clock-in: " + ip);
            ip = "";
        }

        return ip;
    }

    public static Date createDate(int month, int day, int year, int hours, int minutes, int seconds) {
        DateTime dt = new DateTime(year, month, day, hours, minutes, seconds, 0);
        return new Date(dt.getMillis());
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

    public static Date subtractOneMillisecondFromDate(java.util.Date date) {
        DateTime dt = new DateTime(date);
        dt = dt.minusMillis(1);
        return new Date(dt.getMillis());
    }

    public static String formatDate(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dt);
    }

    
    public static String formatDateTime(Timestamp timestamp){
    	Date dt = new Date(timestamp.getTime());
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(dt);
    }
    
    public static Date formatDateString(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	try {
			return new Date(sdf.parse(date).getTime());
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
        return StringUtils.isBlank(ConfigContext.getCurrentContextConfig().getProperty(TkConstants.ConfigSettings.SESSION_TIMEOUT))
                ? 2700 :
                Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty(TkConstants.ConfigSettings.SESSION_TIMEOUT));
    }
    
    /**
     * Creates a Timestamp object using Jodatime as an intermediate data structure
     * from the provided date and time string. (From the form POST and javascript
     * formats)
     *
     * @param dateStr (the format is 01/01/2011)
     * @return Timestamp
     */
    public static Timestamp convertDateStringToTimestamp(String dateStr) {
        // the date/time format is defined in tk.calendar.js. the format is 11/17/2010
        String[] date = dateStr.split("/");

        DateTimeZone dtz = DateTimeZone.forID(TkServiceLocator.getTimezoneService().getUserTimezone());

        // this is from the jodattime javadoc:
        // DateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute, int millisOfSecond, DateTimeZone zone)
        // Noted that the month value is the actual month which is different than the java date object where the month value is the current month minus 1.
        // I tried to use the actual month in the code as much as I can to reduce the convertions.
        DateTime dateTime = new DateTime(
                Integer.parseInt(date[2]),
                Integer.parseInt(date[0]),
                Integer.parseInt(date[1]),
                0, 0, 0, 0, dtz);

        return new Timestamp(dateTime.getMillis());
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
    
    public static List<Interval> getDaySpanForCalendarEntry(CalendarEntries calendarEntry) {
        return getDaySpanForCalendarEntry(calendarEntry, TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }

    public static List<Interval> getFullWeekDaySpanForCalendarEntry(CalendarEntries calendarEntry) {
        return getFullWeekDaySpanForCalendarEntry(calendarEntry, TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
    }
    
    public static List<Interval> getFullWeekDaySpanForCalendarEntry(CalendarEntries calendarEntry, DateTimeZone timeZone) {
        DateTime beginDateTime = calendarEntry.getBeginLocalDateTime().toDateTime(timeZone);
        DateTime endDateTime = calendarEntry.getEndLocalDateTime().toDateTime(timeZone);

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
    
    public static java.util.Date removeTime(java.util.Date date) {    
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return cal.getTime(); 
    }
    
    public static int getWorkDays(java.util.Date startDate, java.util.Date endDate) {
    	int dayCounts = 0;
    	if(startDate.after(endDate)) {
    		return 0;
    	}
    	Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		
		while(!cal1.after(cal2)) {
			if(cal1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY 
					&& cal1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				dayCounts ++;		
			}
			cal1.add(Calendar.DATE, 1);
		}
    	return dayCounts;
    }
    
    public static boolean isWeekend(java.util.Date aDate) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
    	if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY 
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;		
		}
    	return false;
    }
}
