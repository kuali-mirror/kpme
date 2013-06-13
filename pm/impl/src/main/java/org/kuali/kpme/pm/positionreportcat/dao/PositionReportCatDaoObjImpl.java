/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.pm.positionreportcat.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportCatDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportCatDao {

	@Override
	public PositionReportCategory getPositionReportCatById(
			String pmPositionReportCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportCatId", pmPositionReportCatId);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, crit);
        return (PositionReportCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String location, LocalDate asOfDate) {
		List<PositionReportCategory> prcList = new ArrayList<PositionReportCategory>();
		Criteria root = new Criteria();
		
		if(StringUtils.isNotEmpty(positionReportCat) 
				&& !ValidationUtils.isWildCard(positionReportCat)) {
			root.addEqualTo("positionReportCat", positionReportCat);  
		}
		if(StringUtils.isNotEmpty(positionReportType) 
				&& !ValidationUtils.isWildCard(positionReportType)) {
			root.addEqualTo("positionReportType", positionReportType);  
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !ValidationUtils.isWildCard(institution)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(location) 
				&& !ValidationUtils.isWildCard(location)) {
			root.addEqualTo("location", location); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategory.class, asOfDate, PositionReportCategory.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategory.class, PositionReportCategory.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prcList.addAll(c);
		
		return prcList;
	}

	@Override
	public PositionReportCategory getPositionReportCat(String positionReportCat, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("positionReportCat", positionReportCat);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategory.class, asOfDate, PositionReportCategory.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategory.class, PositionReportCategory.EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PositionReportCategory.class, root);
		return (PositionReportCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
