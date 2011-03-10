package org.kuali.hr.time.paycalendar.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayCalendarEntriesDaoSpringOjbImpl extends PersistenceBrokerDaoSupport  implements PayCalendarEntriesDao {
	
	public PayCalendarEntries getPayCalendarEntries(Long payCalendarEntriesId) {
		Criteria currentRecordCriteria = new Criteria();
		currentRecordCriteria.addEqualTo("payCalendarEntriesId", payCalendarEntriesId);

		return (PayCalendarEntries) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayCalendarEntries.class, currentRecordCriteria));
	}

}
