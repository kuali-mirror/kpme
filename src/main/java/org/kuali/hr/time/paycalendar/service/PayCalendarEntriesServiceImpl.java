package org.kuali.hr.time.paycalendar.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paycalendar.dao.PayCalendarEntriesDao;

public class PayCalendarEntriesServiceImpl implements PayCalendarEntriesService {

	private PayCalendarEntriesDao payCalendarEntriesDao;

	public void setPayCalendarEntriesDao(PayCalendarEntriesDao payCalendarEntriesDao) {
		this.payCalendarEntriesDao = payCalendarEntriesDao;
	}

	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId) {

		return payCalendarEntriesDao.getPayCalendarEntries(payCalendarEntriesId);
	}

	@Override
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(
			Long payCalendarId, Date currentDate) {
		return payCalendarEntriesDao.getCurrentPayCalendarEntriesByPayCalendarId(payCalendarId, currentDate);
	}

	public List<PayCalendarEntries> getCurrentPayCalendarEntryNeedsScheduled(int thresholdDays, Date asOfDate){
		return payCalendarEntriesDao.getCurrentPayCalendarEntryNeedsScheduled(thresholdDays, asOfDate);
	}

}
