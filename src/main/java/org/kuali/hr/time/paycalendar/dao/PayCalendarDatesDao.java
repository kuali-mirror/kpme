package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarDatesDao {

	public void saveOrUpdate(PayCalendarEntries payCalendarDates);

	public void saveOrUpdate(List<PayCalendarEntries> payCalendarDatesList);

	public PayCalendarEntries getPayCalendarDates(Long payCalendarDatesId);


}
