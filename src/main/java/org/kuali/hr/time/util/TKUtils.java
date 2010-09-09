package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paytype.PayType;
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
	
	/**
	 * For the provided user, returns a currently valid payEndDate based on the PayCalendarDates, that the provided date is valid for.
	 * 
	 * Example:
	 * 
	 * If there is a pay calendar entry range for: 01/01/2010 to 01/15/2010, and 'now' is set 
	 * to 01/03/2010, the return value will be 01/15/2010.
	 * 
	 * @param user
	 * @param now
	 * @return
	 */
	public static java.sql.Date getPayEndDate(TKUser user, java.util.Date now) {
		Date payEndDate = null;
		DateTime currentTime = new DateTime(now); 
		
		if (user != null) {
			List<Job> jobs = user.getJobs();
			if (jobs != null && jobs.size() > 0) {
				Job job = jobs.get(0);
				PayCalendar payCalendar = (job.getPayType() != null) ? job.getPayType().getPayCalendar() : null;
				List<PayCalendarDates> dates = payCalendar.getPayCalendarDates();
				for (PayCalendarDates pcdate : dates) {
					LocalTime beginTime = new LocalTime(pcdate.getBeginPeriodTime()); 
					LocalDate beginDate = new LocalDate(pcdate.getBeginPeriodDate());					
					DateTime begin = beginDate.toDateTime(beginTime); 
					
					LocalTime endTime = new LocalTime(pcdate.getEndPeriodTime());
					LocalDate endDate = new LocalDate(pcdate.getEndPeriodDate());
					DateTime end = endDate.toDateTime(endTime);
					
					Interval range = new Interval(begin, end);
					if (range.contains(currentTime)) {
						// Joda-time is awesome.
						return pcdate.getEndPeriodDate();
					}
				}
			}
		}
		
		return payEndDate;	
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

	public static BigDecimal getHoursBetween(long start, long end) {
		long diff = end - start;
		return new BigDecimal((diff / 3600000.0) % 24).setScale(TkConstants.MATH_CONTEXT.getPrecision(), TkConstants.MATH_CONTEXT.getRoundingMode()).abs();
	}
}
