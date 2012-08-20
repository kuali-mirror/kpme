package org.kuali.hr.time.holidaycalendar.dao;

import java.util.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class HolidayCalendarDaoImpl extends PlatformAwareDaoBaseOjb implements HolidayCalendarDao {

	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		Criteria root = new Criteria();
		root.addEqualTo("holidayCalendarGroup", holidayCalendarGroup);
		return (HolidayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(HolidayCalendar.class, root));
	}
	
	@SuppressWarnings("unchecked")
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(String hrHolidayCalendarId, Date startDate, Date endDate){
		Criteria root = new Criteria();
		root.addEqualTo("hrHolidayCalendarId", hrHolidayCalendarId);
		root.addBetween("holidayDate", new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
		return (List<HolidayCalendarDateEntry>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(HolidayCalendarDateEntry.class, root));
	}

	@SuppressWarnings("unchecked")
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(String hrHolidayCalendarId, Date startDate){
		Criteria root = new Criteria();
		root.addEqualTo("hrHolidayCalendarId", hrHolidayCalendarId);
		root.addEqualTo("holidayDate", startDate);
		return (HolidayCalendarDateEntry)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(HolidayCalendarDateEntry.class, root));
	}
}
