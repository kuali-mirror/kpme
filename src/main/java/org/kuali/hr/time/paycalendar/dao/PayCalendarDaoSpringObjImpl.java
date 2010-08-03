package org.kuali.hr.time.paycalendar.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.paytype.PayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayCalendarDaoSpringObjImpl extends PersistenceBrokerDaoSupport  implements PayCalendarDao {

	private static final Logger LOG = Logger.getLogger(PayCalendarDaoSpringObjImpl.class);

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
}
