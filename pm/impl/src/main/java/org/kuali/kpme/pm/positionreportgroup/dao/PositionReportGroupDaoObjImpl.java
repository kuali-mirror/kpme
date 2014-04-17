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
package org.kuali.kpme.pm.positionreportgroup.dao;

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
	public PositionReportGroup getPositionReportGroup(String positionReportGroup, LocalDate asOfDate) {
		Criteria root = new Criteria();
        root.addEqualTo("positionReportGroup", positionReportGroup);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportGroup.class, asOfDate, PositionReportGroup.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportGroup.class, PositionReportGroup.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(PositionReportGroup.class, root);
        return (PositionReportGroup) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	
	@Override
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String groupKeyCode, LocalDate asOfDate) {
		List<PositionReportGroup> prgList = new ArrayList<PositionReportGroup>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(positionReportGroup) 
				&& !ValidationUtils.isWildCard(positionReportGroup)) {
			root.addEqualTo("positionReportGroup", positionReportGroup);  
		}
		
		if (StringUtils.isNotEmpty(groupKeyCode)) {
			root.addLike("UPPER(groupKeyCode)", groupKeyCode.toUpperCase());
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportGroup.class, asOfDate, PositionReportGroup.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportGroup.class, PositionReportGroup.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportGroup.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prgList.addAll(c);
		
		return prgList;
	}
	
	@Override
	public List<PositionReportGroup> getPositionReportGroupList(String positionReportGroup, String groupKeyCode, 
			LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
		LocalDate asOfDate = LocalDate.now();
		List<PositionReportGroup> prgList = new ArrayList<PositionReportGroup>();
		Criteria root = new Criteria();

		if (StringUtils.isNotEmpty(positionReportGroup) 
				&& !ValidationUtils.isWildCard(positionReportGroup)) {
			root.addLike("UPPER(positionReportGroup)", positionReportGroup.toUpperCase());
		}
		
		if (StringUtils.isNotEmpty(groupKeyCode)) {
			root.addLike("UPPER(groupKeyCode)", groupKeyCode.toUpperCase());
		}
		
		Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);
        
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
			 root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PositionReportGroup.class, effectiveDateFilter, PositionReportGroup.BUSINESS_KEYS, false));
		     root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportGroup.class, PositionReportGroup.BUSINESS_KEYS, false));   
        }
		

		Query query = QueryFactory.newQuery(PositionReportGroup.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (!c.isEmpty())
			prgList.addAll(c);

		return prgList;
	}
	

}
