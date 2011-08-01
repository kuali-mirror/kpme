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
	 * Returns the PayCalendarDates object that the provided parameters
	 * fit into.
	 *
	 * @param principalId
	 * @param currentDate
	 * @return
	 */
	public PayCalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate);
    public PayCalendarEntries getPayCalendarDatesByPayEndDate(String principalId, Date payEndDate);

	/**
	 * Returns the Pay CalendarEntry for previous pay calendar
	 * @param tkPayCalendarId
	 * @param beginDateCurrentPayCalendar
	 * @return
	 */
	public PayCalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar);
}
