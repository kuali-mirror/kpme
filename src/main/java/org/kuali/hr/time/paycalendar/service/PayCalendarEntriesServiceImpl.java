package org.kuali.hr.time.paycalendar.service;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paycalendar.dao.PayCalendarEntriesDao;

import java.util.Date;
import java.util.List;

public class PayCalendarEntriesServiceImpl implements PayCalendarEntriesService {

	private PayCalendarEntriesDao payCalendarEntriesDao;

	public void setPayCalendarEntriesDao(PayCalendarEntriesDao payCalendarEntriesDao) {
		this.payCalendarEntriesDao = payCalendarEntriesDao;
	}

	public PayCalendarEntries getPayCalendarEntries(Long hrPyCalendarEntriesId) {

		return payCalendarEntriesDao.getPayCalendarEntries(hrPyCalendarEntriesId);
	}

    @Override
    public PayCalendarEntries getPayCalendarEntriesByIdAndPeriodEndDate(Long hrPyCalendarId, Date endPeriodDate) {
        return payCalendarEntriesDao.getPayCalendarEntriesByIdAndPeriodEndDate(hrPyCalendarId, endPeriodDate);
    }

	@Override
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

    public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		return payCalendarEntriesDao.getCurrentPayCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
	}

}
