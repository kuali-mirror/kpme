package org.kuali.hr.time.paycalendar.service;

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
}
