package org.kuali.hr.time.calendar.service;

import java.util.Date;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface CalendarService {
	/**
	 * Fetch a pay calendar with the given id
	 * @param hrCalendarId
	 * @return
	 */
	public Calendar getCalendar(String hrCalendarId);

	/**
	 * Fetch a pay calendar by group
	 * @param calendarName
	 * @return
	 */
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
    public CalendarEntries getCalendarDatesByPayEndDate(String principalId, Date payEndDate);

	/**
	 * Returns the Pay CalendarEntry for previous pay calendar
	 * @param tkCalendarId
	 * @param beginDateCurrentCalendar
	 * @return
	 */
	public CalendarEntries getPreviousCalendarEntry(String tkCalendarId, Date beginDateCurrentCalendar);
}
