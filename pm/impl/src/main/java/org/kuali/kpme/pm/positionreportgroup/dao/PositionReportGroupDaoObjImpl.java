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
package org.kuali.kpme.pm.positionreportgroup.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportGroupDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportGroupDao {

	@Override
	public PositionReportGroup getPositionReportGroupById(
			String pmPositionReportGroupId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportGroupId", pmPositionReportGroupId);

        Query query = QueryFactory.newQuery(PositionReportGroup.class, crit);
        return (PositionReportGroup) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String institution, String campus, LocalDate asOfDate) {
		List<PositionReportGroup> prgList = new ArrayList<PositionReportGroup>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(positionReportGroup) 
				&& !StringUtils.equals(positionReportGroup, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("positionReportGroup", positionReportGroup);  
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !StringUtils.equals(institution, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !StringUtils.equals(campus, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("campus", campus); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportGroup.class, asOfDate, PositionReportGroup.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportGroup.class, PositionReportGroup.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportGroup.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prgList.addAll(c);
		
		return prgList;
	}

}
