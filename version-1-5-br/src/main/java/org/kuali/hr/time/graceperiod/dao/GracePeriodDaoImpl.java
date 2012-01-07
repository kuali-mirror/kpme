package org.kuali.hr.time.graceperiod.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class GracePeriodDaoImpl extends PersistenceBrokerDaoSupport implements GracePeriodDao {
	public GracePeriodRule getGracePeriodRule(Date asOfDate){
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();

		effdt.addLessOrEqualThan("effDt", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(GracePeriodRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		root.addEqualTo("effDt", effdtSubQuery);
//		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(GracePeriodRule.class, root);
		return (GracePeriodRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkGracePeriodRuleId", tkGracePeriodId);
		
		Query query = QueryFactory.newQuery(GracePeriodRule.class, crit);
		return (GracePeriodRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
}
