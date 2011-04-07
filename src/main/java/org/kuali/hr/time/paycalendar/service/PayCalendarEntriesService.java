package org.kuali.hr.time.paycalendar.service;

import java.sql.Date;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarEntriesService {

	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId);
	
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long payCalendarId, Date currentDate);
}
