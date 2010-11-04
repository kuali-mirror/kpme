package org.kuali.hr.time.holidaycalendar.dao;

import org.kuali.hr.time.exceptions.TkException;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;

/**
 * 
 * @author crivera
 * 
 */
public interface HolidayCalendarDao {

	/**
	 * 
	 * @param holidayCalendarGroup
	 * @return
	 * @throws TkException
	 */
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup)
			throws TkException;

}
