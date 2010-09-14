package org.kuali.hr.time.paytype.dao;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.paytype.PayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayTypeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements PayTypeDao {

	private static final Logger LOG = Logger.getLogger(PayTypeDaoSpringOjbImpl.class);

	public void saveOrUpdate(PayType payType) {
		this.getPersistenceBrokerTemplate().store(payType);
	}

	public void saveOrUpdate(List<PayType> payTypeList) {
		if (payTypeList != null) {
			for (PayType payType : payTypeList) {
				this.getPersistenceBrokerTemplate().store(payType);
			}
		}
	}

	public PayType getPayType(String payType, Date effectiveDate) {
		Criteria currentRecordCriteria = new Criteria();
		
		currentRecordCriteria.addEqualTo("payType", payType);
		currentRecordCriteria.addLessOrEqualThan("effectiveDate", effectiveDate);
		//TODO timestamp max here

		return (PayType) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(PayType.class, currentRecordCriteria));
	}
}
