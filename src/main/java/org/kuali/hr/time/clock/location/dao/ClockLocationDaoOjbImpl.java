package org.kuali.hr.time.clock.location.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clocklog.ClockLog;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class ClockLocationDaoOjbImpl extends PersistenceBrokerDaoSupport implements ClockLocationDao{

	@Override
	public ClockLocationRule getClockLocationRule(String principalId,
			Date effectiveDate) {
		Criteria crit = new Criteria();

		crit.addEqualTo("principalId", principalId);
		
		Criteria effectiveDateJoinCriteria = new Criteria();
		effectiveDateJoinCriteria.addEqualToField("principalId",Criteria.PARENT_QUERY_PREFIX +"principalId");
		effectiveDateJoinCriteria.addLessOrEqualThan("effdt", effectiveDate);
		
		ReportQueryByCriteria clockTimeSubQuery = QueryFactory.newReportQuery(ClockLocationRule.class, effectiveDateJoinCriteria);
		clockTimeSubQuery.setAttributes(new String[]{"max(effdt)"});
		crit.addEqualTo("effdt", clockTimeSubQuery);
		
		Criteria timestampJoinCriteria = new Criteria();
		timestampJoinCriteria.addEqualToField("principalId", Criteria.PARENT_QUERY_PREFIX + "principalId");
		timestampJoinCriteria.addEqualToField("effdt", Criteria.PARENT_QUERY_PREFIX + "effdt");
		
		ReportQueryByCriteria timeStampSubQuery = QueryFactory.newReportQuery(ClockLocationRule.class, timestampJoinCriteria);
		timeStampSubQuery.setAttributes(new String[]{"max(timestamp)"});
		timeStampSubQuery.setAttributes(new String[]{"max(timestamp),,"});
		//TODO not complete
		crit.addEqualTo("timestamp", timeStampSubQuery);
		QueryByCriteria query = QueryFactory.newQuery(ClockLocationRule.class,crit);
		return (ClockLocationRule)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
