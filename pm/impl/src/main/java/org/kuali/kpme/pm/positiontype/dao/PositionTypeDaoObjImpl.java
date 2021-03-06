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
import org.kuali.kpme.pm.positiontype.PositionTypeBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionTypeDao {

	@Override
	public PositionTypeBo getPositionTypeById(
			String pmPositionTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionTypeId", pmPositionTypeId);

        Query query = QueryFactory.newQuery(PositionTypeBo.class, crit);
        return (PositionTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionTypeBo> getPositionTypeList(String positionType, LocalDate asOfDate) {
		List<PositionTypeBo> prgList = new ArrayList<PositionTypeBo>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(positionType)
				&& !ValidationUtils.isWildCard(positionType)) {
			root.addEqualTo("positionType", positionType);  
		}

        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionTypeBo.class, asOfDate, PositionTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionTypeBo.class, PositionTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionTypeBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prgList.addAll(c);
		
		return prgList;
	}

	@Override
	public PositionTypeBo getPositionType(String positionType, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("positionType", positionType);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionTypeBo.class, asOfDate, PositionTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionTypeBo.class, PositionTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(PositionTypeBo.class, root);
        return (PositionTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
