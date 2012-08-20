package org.kuali.hr.time.graceperiod.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class GracePeriodDaoImpl extends PlatformAwareDaoBaseOjb implements GracePeriodDao {
	public GracePeriodRule getGracePeriodRule(Date asOfDate){
		GracePeriodRule gracePeriodRule = null;
		
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		// OJB's awesome sub query setup part 1
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(GracePeriodRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(GracePeriodRule.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(GracePeriodRule.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			gracePeriodRule = (GracePeriodRule) obj;
		}
		
		return gracePeriodRule;
	}

	@Override
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkGracePeriodRuleId", tkGracePeriodId);
		
		Query query = QueryFactory.newQuery(GracePeriodRule.class, crit);
		return (GracePeriodRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
}
