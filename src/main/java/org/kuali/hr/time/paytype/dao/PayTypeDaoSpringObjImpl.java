package org.kuali.hr.time.paytype.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.paytype.PayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayTypeDaoSpringObjImpl extends PersistenceBrokerDaoSupport implements PayTypeDao {

	private static final Logger LOG = Logger.getLogger(PayTypeDaoSpringObjImpl.class);

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
}
