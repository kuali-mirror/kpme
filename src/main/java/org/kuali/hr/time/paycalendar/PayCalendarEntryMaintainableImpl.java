package org.kuali.hr.time.paycalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class PayCalendarEntryMaintainableImpl extends KualiMaintainableImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PersistableBusinessObject getBusinessObject() {
		//used to setup the divided time/date fields
		PayCalendarEntries payEntry = (PayCalendarEntries)super.getBusinessObject();
		if(payEntry.getBeginPeriodTime()==null){
			payEntry.setBeginPeriodDateTime(payEntry.getBeginPeriodDateTime());
			payEntry.setEndPeriodDateTime(payEntry.getEndPeriodDateTime());
		}
		return payEntry;
	}

	@Override
	public void saveBusinessObject() {
		PayCalendarEntries payEntry = (PayCalendarEntries)super.getBusinessObject();
		java.sql.Date beginDate = payEntry.getBeginPeriodDate();
		java.sql.Time beginTime = payEntry.getBeginPeriodTime();

		LocalTime beginTimeLocal = new LocalTime(beginTime.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime beginDateTime = new DateTime(beginDate.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		beginDateTime = beginDateTime.plus(beginTimeLocal.getMillisOfDay());
		payEntry.setBeginPeriodDateTime(new java.util.Date(beginDateTime.getMillis()));

		java.sql.Date endDate = payEntry.getEndPeriodDate();
		java.sql.Time endTime = payEntry.getEndPeriodTime();

		LocalTime endTimeLocal = new LocalTime(endTime.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		DateTime endDateTime = new DateTime(endDate.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);
		endDateTime = endDateTime.plus(endTimeLocal.getMillisOfDay());

		payEntry.setEndPeriodDateTime(new java.util.Date(endDateTime.getMillis()));
		super.saveBusinessObject();
	}






}
