package org.kuali.hr.time.holidaycalendar.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.Date;
import java.util.List;

public class HolidayCalendarDaoImpl extends PersistenceBrokerDaoSupport implements HolidayCalendarDao {

	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		Criteria root = new Criteria();
		root.addEqualTo("holidayCalendarGroup", holidayCalendarGroup);
		return (HolidayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(HolidayCalendar.class, root));
	}
	
	@SuppressWarnings("unchecked")
	public List<HolidayCalendarDateEntry> getHolidayCalendarDateEntriesForPayPeriod(Long hrHolidayCalendarId, Date startDate, Date endDate){
		Criteria root = new Criteria();
		root.addEqualTo("hrHolidayCalendarId", hrHolidayCalendarId);
		root.addBetween("holidayDate", startDate, endDate);
		return (List<HolidayCalendarDateEntry>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(HolidayCalendarDateEntry.class, root));
	}

	@SuppressWarnings("unchecked")
	public HolidayCalendarDateEntry getHolidayCalendarDateEntryByDate(Long hrHolidayCalendarId, Date startDate){
		Criteria root = new Criteria();
		root.addEqualTo("hrHolidayCalendarId", hrHolidayCalendarId);
		root.addEqualTo("holidayDate", startDate);
		return (HolidayCalendarDateEntry)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(HolidayCalendarDateEntry.class, root));
	}
}
