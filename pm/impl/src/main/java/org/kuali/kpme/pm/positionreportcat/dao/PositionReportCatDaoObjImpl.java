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
import org.kuali.kpme.pm.positionreportcat.PositionReportCategoryBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportCatDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportCatDao {

	@Override
	public PositionReportCategoryBo getPositionReportCatById(
			String pmPositionReportCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportCatId", pmPositionReportCatId);

        Query query = QueryFactory.newQuery(PositionReportCategoryBo.class, crit);
        return (PositionReportCategoryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportCategoryBo> getPositionReportCatList(String positionReportCat, String positionReportType, LocalDate asOfDate) {
		List<PositionReportCategoryBo> prcList = new ArrayList<PositionReportCategoryBo>();
		Criteria root = new Criteria();
		
		if(StringUtils.isNotEmpty(positionReportCat) 
				&& !ValidationUtils.isWildCard(positionReportCat)) {
			root.addEqualTo("positionReportCat", positionReportCat);  
		}
		if(StringUtils.isNotEmpty(positionReportType) 
				&& !ValidationUtils.isWildCard(positionReportType)) {
			root.addEqualTo("positionReportType", positionReportType);  
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategoryBo.class, asOfDate, PositionReportCategoryBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategoryBo.class, PositionReportCategoryBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportCategoryBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prcList.addAll(c);
		
		return prcList;
	}

	@Override
	public PositionReportCategoryBo getPositionReportCat(String positionReportCat, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("positionReportCat", positionReportCat);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategoryBo.class, asOfDate, PositionReportCategoryBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategoryBo.class, PositionReportCategoryBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PositionReportCategoryBo.class, root);
		return (PositionReportCategoryBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
