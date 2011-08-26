package org.kuali.hr.time.salgroup.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.salgroup.SalGroup;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.sql.Date;

public class SalGroupDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements SalGroupDao {

	@Override
	public void saveOrUpdate(SalGroup salGroup) {
		this.getPersistenceBrokerTemplate().store(salGroup);		
	}

	@Override
	public SalGroup getSalGroup(String salGroup, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(SalGroup.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("hrSalGroup", Criteria.PARENT_QUERY_PREFIX + "hrSalGroup");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(SalGroup.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("hrSalGroup", salGroup);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(SalGroup.class, root);
		SalGroup s = (SalGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return s;
	}

	@Override
	public SalGroup getSalGroup(Long hrSalGroupId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrSalGroupId", hrSalGroupId);
		
		Query query = QueryFactory.newQuery(SalGroup.class, crit);
		return (SalGroup)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
