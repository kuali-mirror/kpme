package org.kuali.hr.location.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.department.Department;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LocationDaoSpringObjImpl extends PersistenceBrokerDaoSupport implements LocationDao {

	@Override
	public Location getLocation(String location, Date asOfDate) {
		Criteria root = new Criteria();
		Criteria effdt = new Criteria();
		Criteria timestamp = new Criteria();

		effdt.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
//		effdt.addEqualToField("org", Criteria.PARENT_QUERY_PREFIX + "org");
//		effdt.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart");
		effdt.addLessOrEqualThan("effectiveDate", asOfDate);
//		effdt.addEqualTo("active", true);
		ReportQueryByCriteria effdtSubQuery = QueryFactory.newReportQuery(Location.class, effdt);
		effdtSubQuery.setAttributes(new String[] { "max(effdt)" });

		timestamp.addEqualToField("location", Criteria.PARENT_QUERY_PREFIX + "location");
//		timestamp.addEqualToField("org", Criteria.PARENT_QUERY_PREFIX + "org");
//		timestamp.addEqualToField("chart", Criteria.PARENT_QUERY_PREFIX + "chart");
		timestamp.addEqualToField("effectiveDate", Criteria.PARENT_QUERY_PREFIX + "effectiveDate");
//		timestamp.addEqualTo("active", true);
		ReportQueryByCriteria timestampSubQuery = QueryFactory.newReportQuery(Location.class, timestamp);
		timestampSubQuery.setAttributes(new String[] { "max(timestamp)" });

		root.addEqualTo("location", location);
		root.addEqualTo("effectiveDate", effdtSubQuery);
		root.addEqualTo("timestamp", timestampSubQuery);
//		root.addEqualTo("active", true);
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		
		Query query = QueryFactory.newQuery(Location.class, root);
		
		Location l = (Location)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		
		return l;
	}

	@Override
	public Location getLocation(String hrLocationId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrLocationId", hrLocationId);
		
		Query query = QueryFactory.newQuery(Location.class, crit);
		return (Location)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
