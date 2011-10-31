package org.kuali.hr.time.calendar.service;

import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;

import java.util.Date;

public interface CalendarService {
	/**
	 * Fetch a pay calendar with the given id
	 * @param hrCalendarId
	 * @return
	 */
	public Calendar getPayCalendar(Long hrCalendarId);

	/**
	 * Fetch a pay calendar by group
	 * @param calendarName
	 * @return
	 */
	public Calendar getPayCalendarByGroup(String calendarName);

	/**
     * Use this method to get PayCalendarEntries if you are passing in a "current date"
     * style of date, ie todays date. If you are in a logic situation where you would
     * pass EITHER todays date or a pay calendar date, pass the Pay period BEGIN date,
     * so that the retrieval logic will correctly place the date in the window.
	 *
	 * @param principalId
	 * @param currentDate
	 * @return
	 */
	public CalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate);

    /**
     * A method to use specifically when you have a Timesheet Documents Pay Period
     * end date. Do not use if you are passing in a date known not to be the END period date.
     *
     * @param principalId
     * @param payEndDate
     * @return
     */
    public CalendarEntries getPayCalendarDatesByPayEndDate(String principalId, Date payEndDate);

	/**
	 * Returns the Pay CalendarEntry for previous pay calendar
	 * @param tkCalendarId
	 * @param beginDateCurrentPayCalendar
	 * @return
	 */
	public CalendarEntries getPreviousPayCalendarEntry(Long tkCalendarId, Date beginDateCurrentPayCalendar);
}
