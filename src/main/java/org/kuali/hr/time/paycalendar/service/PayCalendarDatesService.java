package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarDates;

public interface PayCalendarDatesService {
	public void saveOrUpdate(PayCalendarDates payCalendarDates);

	public void saveOrUpdate(List<PayCalendarDates> payCalendarDatesList);

	public List<PayCalendarDates> getPayCalendarDates(Long payCalendarDatesId);
}
