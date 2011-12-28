package org.kuali.hr.time.earngroup.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;

public class EarnGroupDaoServiceImpl extends PersistenceBrokerDaoSupport implements EarnGroupDaoService {

	@Override
	public EarnGroup getEarnGroup(String earnGroup, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("earnGroup", Criteria.PARENT_QUERY_PREFIX + "earnGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EarnGroup.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("earnGroup", Criteria.PARENT_QUERY_PREFIX + "earnGroup");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EarnGroup.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("earnGroup", earnGroup);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);
		//do not include the summary setup earn groups

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
				
		Query query = QueryFactory.newQuery(EarnGroup.class, root);
		EarnGroup earnGroupObj  = (EarnGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnGroup getEarnGroupSummaryForEarnCode(String earnCode, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		Criteria earnCodeJoin = new Criteria();
		
		effdt.addEqualToField("earnGroup", Criteria.PARENT_QUERY_PREFIX + "earnGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EarnGroup.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("earnGroup", Criteria.PARENT_QUERY_PREFIX + "earnGroup");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EarnGroup.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });
		
		earnCodeJoin.addEqualToField("hrEarnGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnGroupId");
		earnCodeJoin.addEqualTo("earnCode", earnCode);
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnGroupDefinition.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_group_id"});
		
		root.addEqualTo("hrEarnGroupId",earnCodeSubQuery);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);
		root.addEqualTo("showSummary", true);
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(EarnGroup.class, root);
		EarnGroup earnGroupObj  = (EarnGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnGroup getEarnGroupForEarnCode(String earnCode, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();
		Criteria earnCodeJoin = new Criteria();
		
		effdt.addEqualToField("earnGroup", Criteria.PARENT_QUERY_PREFIX + "earnGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(EarnGroup.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("earnGroup", Criteria.PARENT_QUERY_PREFIX + "earnGroup");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(EarnGroup.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });
		
		earnCodeJoin.addEqualToField("hrEarnGroupId", Criteria.PARENT_QUERY_PREFIX + "hrEarnGroupId");
		earnCodeJoin.addEqualTo("earnCode", earnCode);
		ReportQueryByCriteria earnCodeSubQuery = QueryFactory.newReportQuery(EarnGroupDefinition.class, earnCodeJoin);
		earnCodeSubQuery.setAttributes(new String[]{"hr_earn_group_id"});
		
		root.addEqualTo("hrEarnGroupId",earnCodeSubQuery);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(EarnGroup.class, root);
		EarnGroup earnGroupObj  = (EarnGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return earnGroupObj;
	}

	@Override
	public EarnGroup getEarnGroup(String hrEarnGroupId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrEarnGroupId", hrEarnGroupId);
		
		Query query = QueryFactory.newQuery(EarnGroup.class, crit);
		return (EarnGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
