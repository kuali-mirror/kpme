/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.location.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LocationDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LocationDao {
   
	@Override
	public LocationBo getLocation(String location, LocalDate asOfDate) {
		Criteria root = new Criteria();
		root.addEqualTo("location", location);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LocationBo.class, asOfDate, LocationBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LocationBo.class, LocationBo.BUSINESS_KEYS, false));
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(LocationBo.class, root);
		
		return (LocationBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public LocationBo getLocation(String hrLocationId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("hrLocationId", hrLocationId);
		
		Query query = QueryFactory.newQuery(LocationBo.class, crit);
		return (LocationBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public int getLocationCount(String location,  LocalDate asOfDate) {
		Criteria crit = new Criteria();
		// allow wild card
		if(StringUtils.isNotEmpty(location) && !ValidationUtils.isWildCard(location)) {
			crit.addEqualTo("location", location);
		}
		crit.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LocationBo.class, asOfDate, LocationBo.BUSINESS_KEYS, false));
		crit.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LocationBo.class, LocationBo.BUSINESS_KEYS, false));
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		crit.addAndCriteria(activeFilter);
		Query query = QueryFactory.newQuery(LocationBo.class, crit);
		return this.getPersistenceBrokerTemplate().getCount(query);
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<LocationBo> searchLocations(String location, String locationDescr, String active, String showHistory) {
        List<LocationBo> results = new ArrayList<LocationBo>();
    	
        Criteria root = new Criteria();
        // ignore cases for location
        if (StringUtils.isNotBlank(location)) {
            root.addLike("UPPER(location)", location.toUpperCase());
        }
        
        if (StringUtils.isNotBlank(locationDescr)) {
            root.addLike("UPPER(description)", locationDescr.toUpperCase()); // KPME-2695
        }
        
        if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }

        if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(LocationBo.class, LocationBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LocationBo.class, LocationBo.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(LocationBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

    @Override
    public List<LocationBo> getNewerVersionLocation(String location, LocalDate asOfDate) {
        Criteria root = new Criteria();
        root.addEqualTo("location", location);
        root.addGreaterThan("effectiveDate", asOfDate.toDate());

        Criteria activeFilter = new Criteria(); //Inner join for Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(LocationBo.class, root);
        List<LocationBo> locations = (List<LocationBo>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if(locations == null) {
            locations = new ArrayList<LocationBo>();
        }

        return locations;

    }

    @Override
    public List<LocationBo> getLocations(String location) {
        List<LocationBo> results = new ArrayList<LocationBo>();
        Criteria crit = new Criteria();
        crit.addEqualTo("location", location);
        crit.addEqualTo("active", true);
        Query query = QueryFactory.newQuery(LocationBo.class, crit);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        return results;
    }
}
