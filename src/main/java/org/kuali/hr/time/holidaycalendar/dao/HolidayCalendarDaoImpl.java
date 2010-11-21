package org.kuali.hr.time.holidaycalendar.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class HolidayCalendarDaoImpl extends PersistenceBrokerDaoSupport implements HolidayCalendarDao {

	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup) {
		Criteria root = new Criteria();
		root.addEqualTo("holidayCalendarGroup", holidayCalendarGroup);
		return (HolidayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, root));
	}

}
