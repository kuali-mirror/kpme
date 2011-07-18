package org.kuali.hr.paygrade.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.paygrade.PayGrade;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class PayGradeDaoSpringObjImpl  extends PersistenceBrokerDaoSupport implements PayGradeDao {

	@Override
	public PayGrade getPayGrade(String payGrade, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("payGrade", Criteria.PARENT_QUERY_PREFIX + "payGrade");
//		effdt.addEqualToField("org", Criteria.PARENT_QUERY_PREFIX + "org");
//		effdt.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(PayGrade.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("payGrade", Criteria.PARENT_QUERY_PREFIX + "payGrade");
//		timestamp.addEqualToField("org", Criteria.PARENT_QUERY_PREFIX + "org");
//		timestamp.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(PayGrade.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("payGrade", payGrade);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(PayGrade.class, root);
		
		PayGrade pg = (PayGrade)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return pg;
	}

	@Override
	public PayGrade getPayGrade(Long hrPayGradeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrPayGradeId", hrPayGradeId);
		
		Query query = QueryFactory.newQuery(PayGrade.class, crit);
		
		return (PayGrade)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
