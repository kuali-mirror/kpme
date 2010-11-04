package org.kuali.hr.time.paytype.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.exceptions.TkException;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.paytype.PrincipalPayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayTypeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport
		implements PayTypeDao {

	// private static final Logger LOG =
	// Logger.getLogger(PayTypeDaoSpringOjbImpl.class);

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
		currentRecordCriteria
				.addLessOrEqualThan("effectiveDate", effectiveDate);
		// TODO timestamp max here

		return (PayType) this.getPersistenceBrokerTemplate().getObjectByQuery(
				QueryFactory.newQuery(PayType.class, currentRecordCriteria));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.hr.time.paytype.dao.PayTypeDao#getPayTypeByPrincipalId(java
	 * .lang.String)
	 */
	@Override
	public PayType getPayTypeByPrincipalId(String principalId)
			throws TkException {
		// lets first query the principal paytype table to find out what paytype the principal is connected to
		Criteria criteria = new Criteria();
		criteria.addEqualTo("principalId", principalId);

		PrincipalPayType ppt = (PrincipalPayType) getPersistenceBrokerTemplate()
				.getObjectByQuery(
						QueryFactory.newQuery(PrincipalPayType.class, criteria));
		if (ppt == null)
			throw new TkException(
					"Could not find payType (PrincipalPayType) for principal:"
							+ principalId);
		// now return the actual paytype object
		return getPayType(ppt.getHrPayType(), new Date(new java.util.Date()
				.getTime()));
	}
}
