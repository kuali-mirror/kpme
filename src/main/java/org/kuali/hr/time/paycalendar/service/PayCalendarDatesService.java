package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarDatesService {
	public void saveOrUpdate(PayCalendarEntries payCalendarDates);

	public void saveOrUpdate(List<PayCalendarEntries> payCalendarDatesList);

	public PayCalendarEntries getPayCalendarDates(Long payCalendarDatesId);

}
