package org.kuali.hr.time.holidaycalendar.dao;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;

public interface HolidayCalendarDao {
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup);
}
