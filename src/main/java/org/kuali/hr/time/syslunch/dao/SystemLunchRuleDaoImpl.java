package org.kuali.hr.time.syslunch.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class SystemLunchRuleDaoImpl  extends PersistenceBrokerDaoSupport implements SystemLunchRuleDao {

	@Override
	public SystemLunchRule getSystemLunchRule(Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();

		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		root.addEqualTo("effDt", effdtSubQuery);
		root.addEqualTo("active", true);

		Query query = QueryFactory.newQuery(SystemLunchRule.class, root);
		return (SystemLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
