package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paytype.PayType;

public interface PayCalendarDao {

	public void saveOrUpdate(PayType payCalendarDates);

	public void saveOrUpdate(List<PayType> payCalendarDatesList);

	public PayCalendar getPayCalendar(Long payCalendarId);

	public PayCalendar getPayCalendarByGroup(String calendarGroup);

}
