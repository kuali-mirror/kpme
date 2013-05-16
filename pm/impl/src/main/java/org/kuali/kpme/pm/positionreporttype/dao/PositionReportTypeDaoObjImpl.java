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
package org.kuali.kpme.pm.positionreporttype.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionReportTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportTypeDao {
	
	 
	@Override
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportTypeId", pmPositionReportTypeId);

        Query query = QueryFactory.newQuery(PositionReportType.class, crit);
        return (PositionReportType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String institution, String campus, LocalDate asOfDate) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();
		if(StringUtils.isNotEmpty(positionReportType) 
				&& !PmValidationUtils.isWildCard(positionReportType)) {
			root.addEqualTo("positionReportType", positionReportType); 
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !PmValidationUtils.isWildCard(institution)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !PmValidationUtils.isWildCard(campus)) {
			root.addEqualTo("campus", campus); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PositionReportType.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PositionReportType.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        Query query = QueryFactory.newQuery(PositionReportType.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;
	}
	
	@Override
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();
		root.addEqualTo("positionReportType", positionReportType);
		Query query = QueryFactory.newQuery(PositionReportType.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;
	}
	
	@Override
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();

        root.addEqualTo("institution", institutionCode); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PositionReportType.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PositionReportType.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportType.class, root);		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;	
	}
	
	@Override
	public List<PositionReportType> getPrtListWithCampusAndDate(String campus,LocalDate asOfDate) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();

        root.addEqualTo("campus", campus); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PositionReportType.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PositionReportType.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportType.class, root);		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;	
	}
}
