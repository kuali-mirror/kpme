package org.kuali.hr.time.paycalendar.dao;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface PayCalendarDao {

	public void saveOrUpdate(PayCalendar payCalendarDates);

	public void saveOrUpdate(List<PayCalendar> payCalendarDatesList);

	public PayCalendar getPayCalendar(String hrPyCalendarId);

	public PayCalendar getPayCalendarByGroup(String pyCalendarGroup);
	
	public PayCalendarEntries getPreviousPayCalendarEntry(String tkPayCalendarId, Date beginDateCurrentPayCalendar);

    List<PayCalendar> getPayCalendars(String pyCalendarGroup, String flsaBeginDay, Time flsaBeginTime, String active);
    
    public int getPyCalendarGroupCount(String pyCalendarGroup);
}
