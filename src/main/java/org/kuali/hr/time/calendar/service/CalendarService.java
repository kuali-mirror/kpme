package org.kuali.hr.time.calendar.service;

import java.util.Date;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.springframework.cache.annotation.Cacheable;

public interface CalendarService {
	/**
	 * Fetch a pay calendar with the given id
	 * @param hrCalendarId
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'hrCalendarId=' + #p0")
	public Calendar getCalendar(String hrCalendarId);

	/**
	 * Fetch a pay calendar by group
	 * @param calendarName
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'calendarName=' + #p0")
	public Calendar getCalendarByGroup(String calendarName);

	/**
     * Use this method to get CalendarEntries if you are passing in a "current date"
     * style of date, ie todays date. If you are in a logic situation where you would
     * pass EITHER todays date or a pay calendar date, pass the Pay period BEGIN date,
     * so that the retrieval logic will correctly place the date in the window.
	 *
	 * @param principalId
	 * @param currentDate
	 * @return
	 */
	public CalendarEntries getCurrentCalendarDates(String principalId, Date currentDate);

    /**
     * A method to use specifically when you have a Timesheet Documents Pay Period
     * end date. Do not use if you are passing in a date known not to be the END period date.
     *
     * @param principalId
     * @param payEndDate
     * @return
     */
    @Cacheable(value= Calendar.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'payEndDate=' + #p1 + '|' + 'calendarType=' + #p2")
    public CalendarEntries getCalendarDatesByPayEndDate(String principalId, Date payEndDate, String calendarType);

	/**
	 * Returns the Pay CalendarEntry for previous pay calendar
	 * @param tkCalendarId
	 * @param beginDateCurrentCalendar
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'tkCalendarId=' + #p0 + '|' + 'beginDateCurrentCalendar=' + #p1")
	public CalendarEntries getPreviousCalendarEntry(String tkCalendarId, Date beginDateCurrentCalendar);
	
	/**
	 * Fetch a pay calendar with the given principalId and date, returns null if none found
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public Calendar getCalendarByPrincipalIdAndDate(String principalId, Date asOfDate, boolean findLeaveCal);

	/**
	 * 
	 * @param principalId
	 * @param currentDate
	 * @return
	 */
    @Cacheable(value= Calendar.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'currentDate=' + #p1")
	public CalendarEntries getCurrentCalendarDatesForLeaveCalendar(String principalId, Date currentDate);

}
