package org.kuali.hr.time.paycalendar.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.paytype.PayType;

public interface PayCalendarService {

	public void saveOrUpdate(PayType payCalendar);

	public void saveOrUpdate(List<PayType> payCalendarList);

	public PayCalendar getPayCalendar(Long payCalendarId);

	public PayCalendar getPayCalendarByGroup(String calendarGroup);
	
	/**
	 * Returns the PayCalendarDates object that the provided parameters
	 * fit into.
	 * 
	 * @param principalId
	 * @param job
	 * @param currentDate
	 * @return
	 */
	public PayCalendarDates getCurrentPayCalendarDates(String principalId, Job job, Date currentDate);	
}
