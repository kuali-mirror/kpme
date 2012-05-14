package org.kuali.hr.paygrade.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.paygrade.PayGrade;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;

public class PayGradeDaoSpringObjImpl  extends PersistenceBrokerDaoSupport implements PayGradeDao {

	@Override
	public PayGrade getPayGrade(String payGrade, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("payGrade", Criteria.PARENT_QUERY_PREFIX + "payGrade");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayGrade.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("payGrade", Criteria.PARENT_QUERY_PREFIX + "payGrade");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");

		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayGrade.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("payGrade", payGrade);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(PayGrade.class, root);
		
		PayGrade pg = (PayGrade)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return pg;
	}

	@Override
	public PayGrade getPayGrade(String hrPayGradeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayGradeId", hrPayGradeId);
		
		Query query = QueryFactory.newQuery(PayGrade.class, crit);
		
		return (PayGrade)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public int getPayGradeCount(String payGrade) {
		Criteria crit = new Criteria();
		crit.addEqualTo("payGrade", payGrade);
		Query query = QueryFactory.newQuery(PayGrade.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

}
