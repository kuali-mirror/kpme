package org.kuali.hr.time.overtime.daily.rule.dao;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class DailyOvertimeRuleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements DailyOvertimeRuleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DailyOvertimeRule> findDailyOvertimeRules(String dept, Long workArea, Long task, Date asOfDate) {
		List<DailyOvertimeRule> dorList = new LinkedList<DailyOvertimeRule>();

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		effdt.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		effdt.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(DailyOvertimeRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		// OJB's awesome sub query setup part 2
		timestamp.addEqualToField("dept", Criteria.PARENT_QUERY_PREFIX + "dept");
		timestamp.addEqualToField("workArea", Criteria.PARENT_QUERY_PREFIX + "workArea");
		timestamp.addEqualToField("task", Criteria.PARENT_QUERY_PREFIX + "task");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(DailyOvertimeRule.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("dept", dept);
		root.addEqualTo("workArea", workArea);
		root.addEqualTo("task", task);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		root.addEqualTo("active", true);

		Query query = QueryFactory.newQuery(DailyOvertimeRule.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (c != null) {
			dorList.addAll(c);
		}

		return dorList;
	}

	@Override
	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule) {
		this.getPersistenceBrokerTemplate().store(dailyOvertimeRule);
	}

	@Override
	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules) {
		for (DailyOvertimeRule dor : dailyOvertimeRules) {
			saveOrUpdate(dor);
		}
	}

}
