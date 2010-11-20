package org.kuali.hr.time.principal.calendar.service;

import java.sql.Date;

import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.principal.calendar.dao.PrincipalCalendarDao;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class PrincipalCalendarServiceImpl implements PrincipalCalendarService {
	private PrincipalCalendarDao principalCalendarDao;

	public void setPrincipalCalendarDao(PrincipalCalendarDao principalCalendarDao) {
		this.principalCalendarDao = principalCalendarDao;
	}
	
	public PrincipalCalendar getPrincipalCalendar(String principalId, Date asOfDate){
		PrincipalCalendar pc =  this.principalCalendarDao.getPrincipalCalendar(principalId, asOfDate);
		pc.setPayCalendar(TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(pc.getCalendarGroup()));
		return pc;
	}

}
