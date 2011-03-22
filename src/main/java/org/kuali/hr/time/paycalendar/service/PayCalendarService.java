package org.kuali.hr.time.paycalendar.service;

import java.sql.Date;

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
	 * @param job
	 * @param currentDate
	 * @return
	 */
	public PayCalendarEntries getCurrentPayCalendarDates(String principalId, Date currentDate);	
}
