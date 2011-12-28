package org.kuali.hr.time.paycalendar.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarDao {

	public void saveOrUpdate(PayCalendar payCalendarDates);

	public void saveOrUpdate(List<PayCalendar> payCalendarDatesList);

	public PayCalendar getPayCalendar(String hrPyCalendarId);

	public PayCalendar getPayCalendarByGroup(String pyCalendarGroup);
	
	public PayCalendarEntries getPreviousPayCalendarEntry(String tkPayCalendarId, Date beginDateCurrentPayCalendar);

}
