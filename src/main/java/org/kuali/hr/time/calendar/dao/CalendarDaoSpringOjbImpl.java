package org.kuali.hr.time.calendar.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class CalendarDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements CalendarDao {

	private static final Logger LOG = Logger.getLogger(CalendarDaoSpringOjbImpl.class);

	public void saveOrUpdate(Calendar payCalendar) {
		this.getPersistenceBrokerTemplate().store(payCalendar);
	}

	public void saveOrUpdate(List<Calendar> payCalendarList) {
		if (payCalendarList != null) {
			for (Calendar payCalendar : payCalendarList) {
				this.getPersistenceBrokerTemplate().store(payCalendar);
			}
		}
	}

	public Calendar getPayCalendar(Long hrPyCalendarId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("hrCalendarId", hrPyCalendarId);

		return (Calendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(Calendar.class, currentRecordCriteria));
	}

	// Is pay clendar groups alwasy unique?
	public Calendar getPayCalendarByGroup(String pyCalendarGroup) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("calendarName", pyCalendarGroup);

		return (Calendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(Calendar.class, currentRecordCriteria));
	}
	
	public CalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar){
        Criteria payEndDateCriteria = new Criteria();
        payEndDateCriteria.addEqualTo("hr_py_calendar_id", tkPayCalendarId);
        payEndDateCriteria.addLessOrEqualThan("end_period_date", beginDateCurrentPayCalendar);
        
        return (CalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(CalendarEntries.class,payEndDateCriteria));
        
	}
}
