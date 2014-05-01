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
package org.kuali.kpme.pm.positionreporttype.dao;

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
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportTypeDao {
	
	 
	@Override
	public PositionReportTypeBo getPositionReportTypeById(String pmPositionReportTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportTypeId", pmPositionReportTypeId);

        Query query = QueryFactory.newQuery(PositionReportTypeBo.class, crit);
        return (PositionReportTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportTypeBo> getPositionReportTypeList(String positionReportType, String groupKeyCode, LocalDate asOfDate) {
		List<PositionReportTypeBo> prtList = new ArrayList<PositionReportTypeBo>();
		Criteria root = new Criteria();
		if(StringUtils.isNotEmpty(positionReportType) 
				&& !ValidationUtils.isWildCard(positionReportType)) {
			root.addEqualTo("positionReportType", positionReportType); 
		}
		if(StringUtils.isNotEmpty(groupKeyCode)) {
			root.addEqualTo("groupKeyCode", groupKeyCode); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportTypeBo.class, asOfDate, PositionReportTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportTypeBo.class, PositionReportTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        Query query = QueryFactory.newQuery(PositionReportTypeBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;
	}
	
	@Override
	public List<PositionReportTypeBo> getPositionReportTypeListByType(String positionReportType) {
		List<PositionReportTypeBo> prtList = new ArrayList<PositionReportTypeBo>();
		Criteria root = new Criteria();
		root.addEqualTo("positionReportType", positionReportType);
		Query query = QueryFactory.newQuery(PositionReportTypeBo.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;
	}
	
	@Override
	public List<PositionReportTypeBo> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate) {
		List<PositionReportTypeBo> prtList = new ArrayList<PositionReportTypeBo>();
		Criteria root = new Criteria();

        root.addEqualTo("institution", institutionCode); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportTypeBo.class, asOfDate, PositionReportTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportTypeBo.class, PositionReportTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportTypeBo.class, root);		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;	
	}
	
	@Override
	public List<PositionReportTypeBo> getPrtListWithLocationAndDate(String location,LocalDate asOfDate) {
		List<PositionReportTypeBo> prtList = new ArrayList<PositionReportTypeBo>();
		Criteria root = new Criteria();

        root.addEqualTo("location", location); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportTypeBo.class, asOfDate, PositionReportTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportTypeBo.class, PositionReportTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportTypeBo.class, root);		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;	
	}

	@Override
	public PositionReportTypeBo getPositionReportType(String positionReportType, LocalDate asOfDate) {
		Criteria root = new Criteria();

		root.addEqualTo("positionReportType", positionReportType);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportTypeBo.class, asOfDate, PositionReportTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportTypeBo.class, PositionReportTypeBo.BUSINESS_KEYS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PositionReportTypeBo.class, root);
		return (PositionReportTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
}
