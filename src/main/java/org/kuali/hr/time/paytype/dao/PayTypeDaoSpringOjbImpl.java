package org.kuali.hr.time.paytype.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.paytype.PayType;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.Date;
import java.util.List;

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
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		
		effdt.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
		effdt.addLessOrEqualThan("effectiveDate", effectiveDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayType.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("payType", Criteria.PARENT_QUERY_PREFIX + "payType");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayType.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		currentRecordCriteria.addEqualTo("payType", payType);
		currentRecordCriteria.addEqualTo("effectiveDate", effdtSubQuery);
		currentRecordCriteria.addEqualTo("timestamp", timestampSubQuery);
		
//		Criteria activeFilter = new Criteria(); // Inner Join For Activity
//		activeFilter.addEqualTo("active", true);
//		currentRecordCriteria.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PayType.class, currentRecordCriteria);
		return (PayType)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


	@Override
	public PayType getPayType(String hrPayTypeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayTypeId", hrPayTypeId);
		
		Query query = QueryFactory.newQuery(PayType.class, crit);
		return (PayType)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getPayTypeCount(String payType) {
		Criteria crit = new Criteria();
		crit.addEqualTo("payType", payType);
		Query query = QueryFactory.newQuery(PayType.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}
}
