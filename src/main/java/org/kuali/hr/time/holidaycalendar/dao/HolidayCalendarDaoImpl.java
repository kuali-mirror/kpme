package org.kuali.hr.time.holidaycalendar.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.exceptions.TkException;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

/**
 * 
 * @author crivera
 * 
 */
public class HolidayCalendarDaoImpl extends PersistenceBrokerDaoSupport
		implements HolidayCalendarDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.kuali.hr.time.holidaycalendar.dao.HolidayCalendarDao#
	 * getHolidayCalendarByGroup(java.lang.String)
	 */
	@Override
	public HolidayCalendar getHolidayCalendarByGroup(String holidayCalendarGroup)
			throws TkException {
		Criteria criteria = new Criteria();
		criteria.addEqualTo("holidayCalendarGroup", holidayCalendarGroup);

		return (HolidayCalendar) getPersistenceBrokerTemplate()
				.getObjectByQuery(
						QueryFactory.newQuery(HolidayCalendar.class, criteria));
	}

}
