package org.kuali.hr.time.calendar.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.dao.CalendarEntriesDao;
import org.kuali.hr.time.util.TkConstants;

import java.util.Date;
import java.util.List;

public class CalendarEntriesServiceImpl implements CalendarEntriesService {

	private CalendarEntriesDao calendarEntriesDao;

	public void setCalendarEntriesDao(CalendarEntriesDao calendarEntriesDao) {
		this.calendarEntriesDao = calendarEntriesDao;
	}
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCalendarEntries(Long hrCalendarEntriesId) {

		return calendarEntriesDao.getCalendarEntries(hrCalendarEntriesId);
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(Long hrCalendarId, Date endPeriodDate) {
        return calendarEntriesDao.getCalendarEntriesByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(
			Long hrCalendarId, Date currentDate) {
		return calendarEntriesDao.getCurrentCalendarEntriesByCalendarId(hrCalendarId, currentDate);
	}

    @Override
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(Long hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getPreviousCalendarEntriesByCalendarId(hrCalendarId, pce);
    }

    @Override
    public CalendarEntries getNextCalendarEntriesByCalendarId(Long hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getNextCalendarEntriesByCalendarId(hrCalendarId, pce);
    }
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		return calendarEntriesDao.getCurrentCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
	}

}
