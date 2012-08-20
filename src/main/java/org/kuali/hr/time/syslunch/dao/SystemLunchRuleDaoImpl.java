package org.kuali.hr.time.syslunch.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SystemLunchRuleDaoImpl  extends PlatformAwareDaoBaseOjb implements SystemLunchRuleDao {

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

		root.addEqualTo("effectiveDate", effdtSubQuery);
        root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(SystemLunchRule.class, root);
		return (SystemLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public SystemLunchRule getSystemLunchRule(String tkSystemLunchRuleId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkSystemLunchRuleId", tkSystemLunchRuleId);
		
		Query query = QueryFactory.newQuery(SystemLunchRule.class, crit);
		
		return (SystemLunchRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
