package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayCalendarDatesDaoSpringObjImpl extends PersistenceBrokerDaoSupport implements PayCalendarDatesDao {

	private static final Logger LOG = Logger.getLogger(PayCalendarDatesDaoSpringObjImpl.class);

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
}
