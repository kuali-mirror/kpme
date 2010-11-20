package org.kuali.hr.time.principal.calendar.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.principal.calendar.PrincipalCalendar;

public interface PrincipalCalendarDao {
	public void saveOrUpdate(PrincipalCalendar principalCalendar);

	public void saveOrUpdate(List<PrincipalCalendar> lstPrincipalCalendar);

	public PrincipalCalendar getPrincipalCalendar(String principalId, Date asOfDate);
}
