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
package org.kuali.kpme.pm.positiontype.dao;

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
import org.kuali.kpme.pm.positiontype.PositionType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionTypeDao {

	@Override
	public PositionType getPositionTypeById(
			String pmPositionTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionTypeId", pmPositionTypeId);

        Query query = QueryFactory.newQuery(PositionType.class, crit);
        return (PositionType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionType> getPositionTypeList(String positionType, String institution, String location, LocalDate asOfDate) {
		List<PositionType> prgList = new ArrayList<PositionType>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(positionType)
				&& !ValidationUtils.isWildCard(positionType)) {
			root.addEqualTo("positionType", positionType);  
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !ValidationUtils.isWildCard(institution)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(location) 
				&& !ValidationUtils.isWildCard(location)) {
			root.addEqualTo("location", location); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionType.class, asOfDate, PositionType.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionType.class, PositionType.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionType.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prgList.addAll(c);
		
		return prgList;
	}

	@Override
	public PositionType getPositionType(String positionType, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("positionType", positionType);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionType.class, asOfDate, PositionType.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionType.class, PositionType.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(PositionType.class, root);
        return (PositionType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
