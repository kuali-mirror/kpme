package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paytype.PayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

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

	public PayCalendar getPayCalendar(Long payCalendarId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("payCalendarId", payCalendarId);

		return (PayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, currentRecordCriteria));
	}

	// Is pay clendar groups alwasy unique?
	public PayCalendar getPayCalendarByGroup(String calendarGroup) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("calendarGroup", calendarGroup);

		return (PayCalendar) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendar.class, currentRecordCriteria));
	}
}
