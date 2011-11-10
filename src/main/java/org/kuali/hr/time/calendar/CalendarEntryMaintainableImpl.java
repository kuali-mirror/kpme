package org.kuali.hr.time.calendar;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class CalendarEntryMaintainableImpl extends KualiMaintainableImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PersistableBusinessObject getBusinessObject() {
		//used to setup the divided time/date fields
		CalendarEntries payEntry = (CalendarEntries)super.getBusinessObject();
		if(payEntry.getBeginPeriodTime()==null){
			payEntry.setBeginPeriodDateTime(payEntry.getBeginPeriodDateTime());
			payEntry.setEndPeriodDateTime(payEntry.getEndPeriodDateTime());
		}
		return payEntry;
	}

	@Override
	public void saveBusinessObject() {
		CalendarEntries payEntry = (CalendarEntries)super.getBusinessObject();
		Calendar calendar = TkServiceLocator.getCalendarSerivce().getCalendarByGroup(payEntry.getCalendarName());
		payEntry.setHrCalendarId(calendar.getHrCalendarId());
		
		java.sql.Date beginDate = payEntry.getBeginPeriodDate();
		java.sql.Time beginTime = payEntry.getBeginPeriodTime();
		LocalTime beginTimeLocal = new LocalTime(beginTime.getTime());
		DateTime beginDateTime = new DateTime(beginDate.getTime());
		beginDateTime = beginDateTime.plus(beginTimeLocal.getMillisOfDay());
		payEntry.setBeginPeriodDateTime(new java.util.Date(beginDateTime.getMillis()));

		java.sql.Date endDate = payEntry.getEndPeriodDate();
		java.sql.Time endTime = payEntry.getEndPeriodTime();
		LocalTime endTimeLocal = new LocalTime(endTime.getTime());
		DateTime endDateTime = new DateTime(endDate.getTime());
		endDateTime = endDateTime.plus(endTimeLocal.getMillisOfDay());
		payEntry.setEndPeriodDateTime(new java.util.Date(endDateTime.getMillis()));
		
		super.saveBusinessObject();
	}






}
