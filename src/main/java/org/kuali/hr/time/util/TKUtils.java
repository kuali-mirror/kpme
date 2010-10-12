package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Weeks;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.rice.core.config.ConfigContext;

public class TKUtils {

	public static String getTimeZone() {
		// TODO : Verify this
		// TODO : Verify this
		// TODO : Verify this
		// TODO : Verify this
		// TODO : Verify this
		// TODO : Verify this
		HttpServletRequest request = TKContext.getHttpServletRequest();
		Locale clientLocale = request.getLocale();
		Calendar calendar = Calendar.getInstance(clientLocale);
		TimeZone clientTimeZone = calendar.getTimeZone();

		return clientTimeZone.getID();
	}

	public static String getEnvironment() {
		return ConfigContext.getCurrentContextConfig().getProperty("environment");
	}
	
	public static java.sql.Date getCurrentDate(){
		return getTimelessDate(null);
	}
	
	/**
	 * Returns a enforced timeless version of the provided date, if the date is null
	 * the current date is returned.
	 * @param date
	 * @return A java.sql.Date version of the provided date, if provided date is null, the current date is returned.
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
	
	public static long getDaysBetween(Date startDate, Date endDate){
		Calendar beginCal = GregorianCalendar.getInstance();
		Calendar endCal = GregorianCalendar.getInstance();
		
		beginCal.setTime(startDate);
		endCal.setTime(endDate);
		
		return getDaysBetween(beginCal, endCal);
	}

	public static BigDecimal getHoursBetween(long start, long end) {
		long diff = end - start;
		return new BigDecimal((diff / 3600000.0) % 24).setScale(TkConstants.MATH_CONTEXT.getPrecision(), TkConstants.MATH_CONTEXT.getRoundingMode()).abs();
	}
	
	public static Map<Timestamp, BigDecimal> getDateToHoursMap(TimeBlock timeBlock, TimeHourDetail timeHourDetail) {
		Map<Timestamp,BigDecimal> dateToHoursMap = new HashMap<Timestamp,BigDecimal>();
		DateTime beginTime = new DateTime(timeBlock.getBeginTimestamp());
		DateTime endTime = new DateTime(timeBlock.getEndTimestamp());
		
		Days d = Days.daysBetween(beginTime, endTime);
		int numberOfDays = d.getDays();
		if(numberOfDays < 1){
			dateToHoursMap.put(timeBlock.getBeginTimestamp(), timeHourDetail.getHours());
			return dateToHoursMap;
		}
		DateTime currentTime = beginTime;
		for(int i = 0 ;i<numberOfDays;i++){
			DateTime nextDayAtMidnight = new DateTime(currentTime.plusDays(1).getMillis());
			nextDayAtMidnight = nextDayAtMidnight.hourOfDay().setCopy(12);
			nextDayAtMidnight = nextDayAtMidnight.minuteOfDay().setCopy(0);
			nextDayAtMidnight = nextDayAtMidnight.secondOfDay().setCopy(0);
			nextDayAtMidnight = nextDayAtMidnight.millisOfSecond().setCopy(0);
			Duration dur = new Duration(currentTime, nextDayAtMidnight);
			long duration = dur.getStandardSeconds();
			BigDecimal hrs = new BigDecimal(duration/3600, TkConstants.MATH_CONTEXT);
			dateToHoursMap.put(new Timestamp(currentTime.getMillis()),hrs);
			currentTime = nextDayAtMidnight;
		}
		Duration dur = new Duration(currentTime, endTime);
		long duration = dur.getStandardSeconds();
		BigDecimal hrs = new BigDecimal(duration/3600, TkConstants.MATH_CONTEXT);
		dateToHoursMap.put(new Timestamp(currentTime.getMillis()),hrs);
		
		return dateToHoursMap;
	}
	
	public static int getNumberOfWeeks(java.util.Date beginDate, java.util.Date endDate){
		
		DateTime beginTime = new DateTime(beginDate);
		DateTime endTime = new DateTime(endDate);
		
		int numOfDays = Days.daysBetween(beginTime, endTime).getDays();
		int numOfWeeks = numOfDays / 7 ;
		if(numOfDays % 7 != 0){
			numOfWeeks++;
		}
		return numOfWeeks;
	}
	
	public static String formatAssignmentKey(Long jobNumber, Long workArea, Long task) {
		return jobNumber + TkConstants.ASSIGNMENT_KEY_DELIMITER + workArea + TkConstants.ASSIGNMENT_KEY_DELIMITER + task; 
	}
}
