package org.kuali.hr.time.paycalendar.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayCalendarDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PayCalendarDao {

	private static final Logger LOG = Logger.getLogger(PayCalendarDaoSpringOjbImpl.class);

	public void saveOrUpdate(PayCalendar payCalendar) {
		this.getPersistenceBrokerTemplate().store(payCalendar);
	}

	public void saveOrUpdate(List<PayCalendar> payCalendarList) {
		if (payCalendarList != null) {
			for (PayCalendar payCalendar : payCalendarList) {
				this.getPersistenceBrokerTemplate().store(payCalendar);
			}
		}
	}

	public PayCalendar getPayCalendar(String hrPyCalendarId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("hrPyCalendarId", hrPyCalendarId);

		return (PayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, currentRecordCriteria));
	}

	// Is pay clendar groups alwasy unique?
	public PayCalendar getPayCalendarByGroup(String pyCalendarGroup) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("pyCalendarGroup", pyCalendarGroup);

		return (PayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, currentRecordCriteria));
	}
	
	public PayCalendarEntries getPreviousPayCalendarEntry(String tkPayCalendarId, Date beginDateCurrentPayCalendar){
        Criteria payEndDateCriteria = new Criteria();
        payEndDateCriteria.addEqualTo("hr_py_calendar_id", tkPayCalendarId);
        payEndDateCriteria.addLessOrEqualThan("end_period_date", beginDateCurrentPayCalendar);
        
        return (PayCalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarEntries.class,payEndDateCriteria));
        
	}
}
