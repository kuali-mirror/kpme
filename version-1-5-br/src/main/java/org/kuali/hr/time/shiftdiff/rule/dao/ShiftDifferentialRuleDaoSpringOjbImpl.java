package org.kuali.hr.time.shiftdiff.rule.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class ShiftDifferentialRuleDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements ShiftDifferentialRuleDao {
	
	@Override
	public ShiftDifferentialRule findShiftDifferentialRule(String id) {
		Object o = this.getPersistenceBrokerTemplate().getObjectById(ShiftDifferentialRule.class, id);
		
		return (ShiftDifferentialRule)o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShiftDifferentialRule> findShiftDifferentialRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, Date asOfDate) {
		List<ShiftDifferentialRule> list = new ArrayList<ShiftDifferentialRule>();

		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
		effdt.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
		effdt.addEqualToField("payGrade", Criteria.PARENT_QUERY_PREFIX + "payGrade");
		effdt.addEqualToField("pyCalendarGroup", Criteria.PARENT_QUERY_PREFIX + "pyCalendarGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(ShiftDifferentialRule.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
		timestamp.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
		timestamp.addEqualToField("payGrade", Criteria.PARENT_QUERY_PREFIX + "payGrade");
		timestamp.addEqualToField("pyCalendarGroup", Criteria.PARENT_QUERY_PREFIX + "pyCalendarGroup");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(ShiftDifferentialRule.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("location", location);
		root.addEqualTo("hrSalGroup", hrSalGroup);
		root.addEqualTo("payGrade", payGrade);
		root.addEqualTo("pyCalendarGroup", pyCalendarGroup);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(ShiftDifferentialRule.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			list.addAll(c);
		}
		
		return list;
	}

	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule) {
		this.getPersistenceBrokerTemplate().store(shiftDifferentialRule);
	}

	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules) {
		for (ShiftDifferentialRule sdr : shiftDifferentialRules) {
			saveOrUpdate(sdr);
		}
	}

}
