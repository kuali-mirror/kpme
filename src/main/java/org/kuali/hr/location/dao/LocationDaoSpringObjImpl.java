/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.location.dao;

import java.sql.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.location.Location;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LocationDaoSpringObjImpl extends PlatformAwareDaoBaseOjb implements LocationDao {

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
	
	@Override
	public int getLocationCount(String location) {
		Criteria crit = new Criteria();
		crit.addEqualTo("location", location);
		Query query = QueryFactory.newQuery(Location.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

}
