package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paycalendar.dao.PayCalendarDatesDao;

public class PayCalendarDatesServiceImpl implements PayCalendarDatesService {

	PayCalendarDatesDao payCalendarDatesDao;

	@Override
	public void saveOrUpdate(PayCalendarEntries payCalendarDates) {
		payCalendarDatesDao.saveOrUpdate(payCalendarDates);
	}

	@Override
	public void saveOrUpdate(List<PayCalendarEntries> payCalendarDatesList) {
		payCalendarDatesDao.saveOrUpdate(payCalendarDatesList);
	}

	public void setPayCalendarDatesDao(PayCalendarDatesDao payCalendarDatesDao) {
		this.payCalendarDatesDao = payCalendarDatesDao;
	}

	@Override
	public PayCalendarEntries getPayCalendarDates(Long payCalendarDatesId) {
		return payCalendarDatesDao.getPayCalendarDates(payCalendarDatesId);
	}

}
