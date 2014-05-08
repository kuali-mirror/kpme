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
package org.kuali.kpme.pm.positionflag.dao;

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
import org.kuali.kpme.pm.positionflag.PositionFlagBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionFlagDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionFlagDao {

	@Override
	public PositionFlagBo getPositionFlagById(String pmPositionFlagId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionFlagId", pmPositionFlagId);

        Query query = QueryFactory.newQuery(PositionFlagBo.class, crit);
        return (PositionFlagBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public List<PositionFlagBo> getAllActivePositionFlags(String category, String name, LocalDate effDate) {
		List<PositionFlagBo> pfList = new ArrayList<PositionFlagBo>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(category) 
				&& !ValidationUtils.isWildCard(category)) {
			root.addEqualTo("category", category);  
		}
		if(StringUtils.isNotEmpty(name) 
				&& !ValidationUtils.isWildCard(name)) {
			root.addEqualTo("positionFlagName", name); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionFlagBo.class, effDate, PositionFlagBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionFlagBo.class, PositionFlagBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionFlagBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pfList.addAll(c);
		
		return pfList;
	}
	
	@Override
	public List<String> getAllActiveFlagCategories() {
		List<String> categoryList = new ArrayList<String>();
		Criteria root = new Criteria();
		root.addEqualTo("active", true); 
		Query query = QueryFactory.newQuery(PositionFlagBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<PositionFlagBo> pfList = new ArrayList<PositionFlagBo>();
        if(!c.isEmpty())
			pfList.addAll(c);
       
        for(PositionFlagBo aFlag : pfList) {
        	if(aFlag != null && StringUtils.isNotEmpty(aFlag.getCategory())
        			&& !categoryList.contains(aFlag.getCategory())) {
        		categoryList.add(aFlag.getCategory());
        	}
        }
        return categoryList;
	}
	
	@Override
	public List<PositionFlagBo> getAllActivePositionFlagsWithCategory(String category, LocalDate effDate) {
		List<PositionFlagBo> pfList = new ArrayList<PositionFlagBo>();
		Criteria root = new Criteria();
		if(StringUtils.isEmpty(category)) {
			return pfList;
		}
		root.addEqualTo("category", category);
		
		if(effDate != null) {
			root.addLessOrEqualThan("effectiveDate", effDate.toDate());
			root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionFlagBo.class, effDate, PositionFlagBo.BUSINESS_KEYS, false));
		}
		Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionFlagBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pfList.addAll(c);
		
		return pfList;
	}

}
