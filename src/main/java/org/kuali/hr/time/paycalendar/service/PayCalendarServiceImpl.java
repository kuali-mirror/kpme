package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.dao.PayCalendarDao;
import org.kuali.hr.time.paytype.PayType;

public class PayCalendarServiceImpl implements PayCalendarService {

	private PayCalendarDao payCalendarDao;

	@Override
	public void saveOrUpdate(PayType payCalendar) {
		payCalendarDao.saveOrUpdate(payCalendar);
	}

	@Override
	public void saveOrUpdate(List<PayType> payCalendarList) {
		payCalendarDao.saveOrUpdate(payCalendarList);
	}

	public void setPayCalendarDao(PayCalendarDao payCalendarDao) {
		this.payCalendarDao = payCalendarDao;
	}

	@Override
	public PayCalendar getPayCalendar(Long payCalendarId) {
		return payCalendarDao.getPayCalendar(payCalendarId);
	}

	@Override
	public PayCalendar getPayCalendarByGroup(String calendarGroup) {
		return payCalendarDao.getPayCalendarByGroup(calendarGroup);
	}

}
