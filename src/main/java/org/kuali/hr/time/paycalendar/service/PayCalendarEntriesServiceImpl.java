package org.kuali.hr.time.paycalendar.service;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paycalendar.dao.PayCalendarEntriesDao;
import org.kuali.hr.time.util.TkConstants;

import java.util.Date;
import java.util.List;

public class PayCalendarEntriesServiceImpl implements PayCalendarEntriesService {

	private PayCalendarEntriesDao payCalendarEntriesDao;

	public void setPayCalendarEntriesDao(PayCalendarEntriesDao payCalendarEntriesDao) {
		this.payCalendarEntriesDao = payCalendarEntriesDao;
	}
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PayCalendarEntries getPayCalendarEntries(Long hrPyCalendarEntriesId) {

		return payCalendarEntriesDao.getPayCalendarEntries(hrPyCalendarEntriesId);
	}

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long hrPyCalendarId, Date endPeriodDate) {
        return payCalendarEntriesDao.getPayCalendarEntriesByIdAndPeriodEndDate(hrPyCalendarId, endPeriodDate);
    }

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(
			Long hrPyCalendarId, Date currentDate) {
		return payCalendarEntriesDao.getCurrentPayCalendarEntriesByPayCalendarId(hrPyCalendarId, currentDate);
	}

    @Override
    public PayCalendarEntries getPreviousPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, PayCalendarEntries pce) {
        return payCalendarEntriesDao.getPreviousPayCalendarEntriesByPayCalendarId(hrPyCalendarId, pce);
    }

    @Override
    public PayCalendarEntries getNextPayCalendarEntriesByPayCalendarId(Long hrPyCalendarId, PayCalendarEntries pce) {
        return payCalendarEntriesDao.getNextPayCalendarEntriesByPayCalendarId(hrPyCalendarId, pce);
    }
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		return payCalendarEntriesDao.getCurrentPayCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
	}

}
