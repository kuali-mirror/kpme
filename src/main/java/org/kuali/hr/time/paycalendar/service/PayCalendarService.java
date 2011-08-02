package org.kuali.hr.time.paycalendar.service;

import java.util.Date;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarService {
	/**
	 * Fetch a pay calendar with the given id
	 * @param payCalendarId
	 * @return
	 */
	public PayCalendar getPayCalendar(Long payCalendarId);

	/**
	 * Fetch a pay calendar by group
	 * @param calendarGroup
	 * @return
	 */
	public PayCalendar getPayCalendarByGroup(String calendarGroup);

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
	public PayCalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate);

    /**
     * A method to use specifically when you have a Timesheet Documents Pay Period
     * end date. Do not use if you are passing in a date known not to be the END period date.
     *
     * @param principalId
     * @param payEndDate
     * @return
     */
    public PayCalendarEntries getPayCalendarDatesByPayEndDate(String principalId, Date payEndDate);

	/**
	 * Returns the Pay CalendarEntry for previous pay calendar
	 * @param tkPayCalendarId
	 * @param beginDateCurrentPayCalendar
	 * @return
	 */
	public PayCalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar);
}
