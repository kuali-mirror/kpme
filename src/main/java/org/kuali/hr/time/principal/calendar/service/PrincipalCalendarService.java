package org.kuali.hr.time.principal.calendar.service;

import java.sql.Date;

import org.kuali.hr.time.principal.calendar.PrincipalCalendar;

public interface PrincipalCalendarService {
	/**
	 * Fetch PrincipalCalendar object at a particular date
	 * @param principalId
	 * @param asOfDate
	 * @return
	 */
	public PrincipalCalendar getPrincipalCalendar(String principalId, Date asOfDate);
}
