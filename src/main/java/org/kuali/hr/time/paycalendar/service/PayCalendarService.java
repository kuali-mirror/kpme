package org.kuali.hr.time.paycalendar.service;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;

import java.util.Date;
import java.util.List;

public interface PayCalendarService {
	/**
	 * Fetch a pay calendar with the given id
	 * @param hrPyCalendarId
	 * @return
	 */
	public PayCalendar getPayCalendar(String hrPyCalendarId);

	/**
	 * Fetch a pay calendar by group
	 * @param pyCalendarGroup
	 * @return
	 */
	public PayCalendar getPayCalendarByGroup(String pyCalendarGroup);

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
	public PayCalendarEntries getPreviousPayCalendarEntry(String tkPayCalendarId, Date beginDateCurrentPayCalendar);

    List<PayCalendar> getPayCalendars(String pyCalendarGroup, String flsaBeginDay, String flsaBeginTime, String active);
    
    /**
     * get the count of pay calendars by given pyCalendarGroup
     * @param earnGroup
     * @return int
     */
    public int getPyCalendarGroupCount(String pyCalendarGroup);
}
