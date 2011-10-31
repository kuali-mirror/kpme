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
	public CalendarEntries getPayCalendarEntries(Long hrCalendarEntriesId) {

		return calendarEntriesDao.getPayCalendarEntries(hrCalendarEntriesId);
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long hrCalendarId, Date endPeriodDate) {
        return calendarEntriesDao.getPayCalendarEntriesByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(
			Long hrCalendarId, Date currentDate) {
		return calendarEntriesDao.getCurrentPayCalendarEntriesByPayCalendarId(hrCalendarId, currentDate);
	}

    @Override
    public CalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getPreviousPayCalendarEntriesByPayCalendarId(hrCalendarId, pce);
    }

    @Override
    public CalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getNextPayCalendarEntriesByPayCalendarId(hrCalendarId, pce);
    }
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public List<CalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		return calendarEntriesDao.getCurrentPayCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
	}

}
