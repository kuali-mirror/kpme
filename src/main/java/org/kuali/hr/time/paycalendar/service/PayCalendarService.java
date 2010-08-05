package org.kuali.hr.time.paycalendar.service;

import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paytype.PayType;

public interface PayCalendarService {

	public void saveOrUpdate(PayType payCalendar);

	public void saveOrUpdate(List<PayType> payCalendarList);

	public PayCalendar getPayCalendar(Long payCalendarId);

	public PayCalendar getPayCalendarByGroup(String calendarGroup);
}
