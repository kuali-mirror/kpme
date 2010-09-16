package org.kuali.hr.time.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

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
	
	public static Date getCurrentDate(){
		return new Date(System.currentTimeMillis());
	}
}
