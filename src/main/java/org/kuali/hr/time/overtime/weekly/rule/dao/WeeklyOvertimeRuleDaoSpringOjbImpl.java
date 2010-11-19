package org.kuali.hr.time.overtime.weekly.rule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class WeeklyOvertimeRuleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements WeeklyOvertimeRuleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WeeklyOvertimeRule> findWeeklyOvertimeRules(Date asOfDate) {
		List<WeeklyOvertimeRule> list = new ArrayList<WeeklyOvertimeRule>();

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("convertFromEarnGroup", Criteria.PARENT_QUERY_PREFIX + "convertFromEarnGroup");
		effdt.addEqualToField("convertToEarnCode", Criteria.PARENT_QUERY_PREFIX + "convertToEarnCode");
		effdt.addEqualToField("maxHoursEarnGroup", Criteria.PARENT_QUERY_PREFIX + "maxHoursEarnGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(WeeklyOvertimeRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("convertFromEarnGroup", Criteria.PARENT_QUERY_PREFIX + "convertFromEarnGroup");
		timestamp.addEqualToField("convertToEarnCode", Criteria.PARENT_QUERY_PREFIX + "convertToEarnCode");
		timestamp.addEqualToField("maxHoursEarnGroup", Criteria.PARENT_QUERY_PREFIX + "maxHoursEarnGroup");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(WeeklyOvertimeRule.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);

		QueryByCriteria query = new QueryByCriteria(WeeklyOvertimeRule.class, root);
		query.addOrderByAscending("step");

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			list.addAll(c);
		}
				
		return list;
	}

	@Override
	public void saveOrUpdate(WeeklyOvertimeRule weeklyOvertimeRule) {
		this.getPersistenceBrokerTemplate().store(weeklyOvertimeRule);
	}

	@Override
	public void saveOrUpdate(List<WeeklyOvertimeRule> weeklyOvertimeRules) {
		for (WeeklyOvertimeRule wor : weeklyOvertimeRules) {
			saveOrUpdate(wor);
		}
	}

}
