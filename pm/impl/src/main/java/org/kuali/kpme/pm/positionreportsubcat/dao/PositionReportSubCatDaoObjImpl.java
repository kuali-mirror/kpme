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
package org.kuali.kpme.pm.positionreportsubcat.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportSubCatDaoObjImpl extends PlatformAwareDaoBaseOjb  implements PositionReportSubCatDao {
	 public PositionReportSubCategory getPositionReportSubCatById(
			String pmPositionReportSubCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportSubCatId", pmPositionReportSubCatId);

        Query query = QueryFactory.newQuery(PositionReportSubCategory.class, crit);
        return (PositionReportSubCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String location, LocalDate asOfDate) {
		List<PositionReportSubCategory> prscList = new ArrayList<PositionReportSubCategory>();
		Criteria root = new Criteria();
		if(StringUtils.isNotEmpty(pstnRptSubCat) 
				&& !ValidationUtils.isWildCard(pstnRptSubCat)) {
			root.addEqualTo("positionReportSubCat", pstnRptSubCat); 
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !ValidationUtils.isWildCard(institution)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(location) 
				&& !ValidationUtils.isWildCard(location)) {
			root.addEqualTo("location", location); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportSubCategory.class, asOfDate, PositionReportSubCategory.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportSubCategory.class, PositionReportSubCategory.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        Query query = QueryFactory.newQuery(PositionReportSubCategory.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prscList.addAll(c);
		
		return prscList;
	}

	@Override
	public PositionReportSubCategory getPositionReportSubCat(String pstnRptSubCat, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("positionReportSubCat", pstnRptSubCat);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportSubCategory.class, asOfDate, PositionReportSubCategory.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportSubCategory.class, PositionReportSubCategory.BUSINESS_KEYS, false));
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(PositionReportSubCategory.class, root);
        return (PositionReportSubCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
