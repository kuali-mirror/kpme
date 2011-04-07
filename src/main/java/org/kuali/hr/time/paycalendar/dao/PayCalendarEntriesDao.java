package org.kuali.hr.time.paycalendar.dao;

import java.sql.Date;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public interface PayCalendarEntriesDao {
	
	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId);
	
	public PayCalendarEntries getCurrentPayCalendarEntriesByPayCalendarId(Long payCalendarId, Date currentDate);

}
