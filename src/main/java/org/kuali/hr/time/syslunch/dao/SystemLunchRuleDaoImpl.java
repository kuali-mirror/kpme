package org.kuali.hr.time.syslunch.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;

public class SystemLunchRuleDaoImpl  extends PersistenceBrokerDaoSupport implements SystemLunchRuleDao {

	@Override
	public SystemLunchRule getSystemLunchRule(Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
        Criteria timestamp = new Criteria();

        //effdt.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

        //timestamp.addEqualToField("tkSystemLunchRuleId", Criteria.PARENT_QUERY_PREFIX + "tkSystemLunchRuleId");
        timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
        ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SystemLunchRule.class, timestamp);
        timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("effDt", effdtSubQuery);
        root.addEqualTo("timeStamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(SystemLunchRule.class, root);
		return (SystemLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
