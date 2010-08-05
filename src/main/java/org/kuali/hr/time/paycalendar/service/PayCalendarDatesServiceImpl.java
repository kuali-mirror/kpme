package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paycalendar.dao.PayCalendarDatesDao;

public class PayCalendarDatesServiceImpl implements PayCalendarDatesService {

	PayCalendarDatesDao payCalendarDatesDao;

	@Override
	public void saveOrUpdate(PayCalendarDates payCalendarDates) {
		payCalendarDatesDao.saveOrUpdate(payCalendarDates);
	}

	@Override
	public void saveOrUpdate(List<PayCalendarDates> payCalendarDatesList) {
		payCalendarDatesDao.saveOrUpdate(payCalendarDatesList);
	}

	public void setPayCalendarDatesDao(PayCalendarDatesDao payCalendarDatesDao) {
		this.payCalendarDatesDao = payCalendarDatesDao;
	}

	@Override
	public List<PayCalendarDates> getPayCalendarDates(Long payCalendarDatesId) {
		return payCalendarDatesDao.getPayCalendarDates(payCalendarDatesId);
	}

}
