package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarDates;

public interface PayCalendarDatesDao {

	public void saveOrUpdate(PayCalendarDates payCalendarDates);

	public void saveOrUpdate(List<PayCalendarDates> payCalendarDatesList);


}
