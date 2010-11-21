package org.kuali.hr.time.holidaycalendar.service;

import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.dao.HolidayCalendarDao;

public class HolidayCalendarServiceImpl implements HolidayCalendarService {
	private HolidayCalendarDao holidayCalendarDao;
	
	
	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		return holidayCalendarDao.getHolidayCalendarByGroup(holidayCalendarGroup);
	}


	public HolidayCalendarDao getHolidayCalendarDao() {
		return holidayCalendarDao;
	}


	public void setHolidayCalendarDao(HolidayCalendarDao holidayCalendarDao) {
		this.holidayCalendarDao = holidayCalendarDao;
	}

}
