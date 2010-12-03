package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayCalendarDatesDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements PayCalendarDatesDao {

	private static final Logger LOG = Logger.getLogger(PayCalendarDatesDaoSpringOjbImpl.class);

	public void saveOrUpdate(PayCalendarDates payCalendarDates) {
		this.getPersistenceBrokerTemplate().store(payCalendarDates);
	}

	public void saveOrUpdate(List<PayCalendarDates> payCalendarDatesList) {
		if (payCalendarDatesList != null) {
			for (PayCalendarDates payCalendarDates : payCalendarDatesList) {
				this.getPersistenceBrokerTemplate().store(payCalendarDates);
			}
		}
	}

	/**
	 * The name PayCalendarDates is a bit confusing, and will eventually 
	 * be changed to PayCalendarEntry.
	 */
	public PayCalendarDates getPayCalendarDates(Long payCalendarDatesId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("payCalendarDatesId", payCalendarDatesId);

		return (PayCalendarDates) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarDates.class, currentRecordCriteria));
	}
}
