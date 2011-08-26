package org.kuali.hr.time.paycalendar.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.paytype.PayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.Date;
import java.util.List;

public class PayCalendarDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PayCalendarDao {

	private static final Logger LOG = Logger.getLogger(PayCalendarDaoSpringOjbImpl.class);

	public void saveOrUpdate(PayType payCalendar) {
		this.getPersistenceBrokerTemplate().store(payCalendar);
	}

	public void saveOrUpdate(List<PayType> payCalendarList) {
		if (payCalendarList != null) {
			for (PayType payCalendar : payCalendarList) {
				this.getPersistenceBrokerTemplate().store(payCalendar);
			}
		}
	}

	public PayCalendar getPayCalendar(Long hrPyCalendarId) {
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
	
	public PayCalendarEntries getPreviousPayCalendarEntry(Long tkPayCalendarId, Date beginDateCurrentPayCalendar){
        Criteria payEndDateCriteria = new Criteria();
        payEndDateCriteria.addEqualTo("hr_py_calendar_id", tkPayCalendarId);
        payEndDateCriteria.addLessOrEqualThan("end_period_date", beginDateCurrentPayCalendar);
        
        return (PayCalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarEntries.class,payEndDateCriteria));
        
	}
}
