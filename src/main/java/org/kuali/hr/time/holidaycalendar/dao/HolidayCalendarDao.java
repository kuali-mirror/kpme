package org.kuali.hr.time.holidaycalendar.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;

public interface HolidayCalendarDao {
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup);
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(Long holidayCalendarId, 
				Date startDate, Date endDate);
}
