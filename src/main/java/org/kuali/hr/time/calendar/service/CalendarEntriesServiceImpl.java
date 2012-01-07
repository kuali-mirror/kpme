package org.kuali.hr.time.calendar.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.dao.CalendarEntriesDao;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.util.DateUtils;

public class CalendarEntriesServiceImpl implements CalendarEntriesService {

	private CalendarEntriesDao calendarEntriesDao;

	public void setCalendarEntriesDao(CalendarEntriesDao calendarEntriesDao) {
		this.calendarEntriesDao = calendarEntriesDao;
	}
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCalendarEntries(String hrCalendarEntriesId) {

		return calendarEntriesDao.getCalendarEntries(hrCalendarEntriesId);
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public CalendarEntries getCalendarEntriesByIdAndPeriodEndDate(String hrCalendarId, Date endPeriodDate) {
        return calendarEntriesDao.getCalendarEntriesByIdAndPeriodEndDate(hrCalendarId, endPeriodDate);
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public CalendarEntries getCurrentCalendarEntriesByCalendarId(
			String hrCalendarId, Date currentDate) {
		return calendarEntriesDao.getCurrentCalendarEntriesByCalendarId(hrCalendarId, currentDate);
	}

    @Override
    public CalendarEntries getPreviousCalendarEntriesByCalendarId(String hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getPreviousCalendarEntriesByCalendarId(hrCalendarId, pce);
    }

    @Override
    public CalendarEntries getNextCalendarEntriesByCalendarId(String hrCalendarId, CalendarEntries pce) {
        return calendarEntriesDao.getNextCalendarEntriesByCalendarId(hrCalendarId, pce);
    }
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public List<CalendarEntries> getCurrentCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		return calendarEntriesDao.getCurrentCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
	}
    
    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public void createNextCalendarEntry(CalendarEntries calendarEntries){
		calendarEntries.setHrCalendarEntriesId(null);
		calendarEntries.setBeginPeriodDateTime(DateUtils.addDays(calendarEntries.getBeginPeriodDateTime(), 14));
		calendarEntries.setEndPeriodDateTime(DateUtils.addDays(calendarEntries.getEndPeriodDateTime(), 14));
		calendarEntries.setBatchInitiateDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchInitiateDate(), 14).getTime()));
		calendarEntries.setBatchEndPayPeriodDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchEndPayPeriodDate(), 14).getTime()));
		calendarEntries.setBatchEmployeeApprovalDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchEmployeeApprovalDate(), 14).getTime()));
		calendarEntries.setBatchSupervisorApprovalDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchSupervisorApprovalDate(), 14).getTime()));
		calendarEntriesDao.saveOrUpdate(calendarEntries);
    }

}
