package org.kuali.hr.time.paycalendar.dao;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paytype.PayType;

import java.util.Date;
import java.util.List;

public interface PayCalendarDao {

	public void saveOrUpdate(PayType payCalendarDates);

	public void saveOrUpdate(List<PayType> payCalendarDatesList);

	public PayCalendar getPayCalendar(Long hrPyCalendarId);

	public PayCalendar getPayCalendarByGroup(String pyCalendarGroup);
	
	public PayCalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar);

}
