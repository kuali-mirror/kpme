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
package org.kuali.kpme.pm.pstncontracttype.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.pstncontracttype.PstnContractTypeBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PstnContractTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PstnContractTypeDao {

	@Override
	public PstnContractTypeBo getPstnContractTypeById(
			String pmCntrctTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmCntrctTypeId", pmCntrctTypeId);

        Query query = QueryFactory.newQuery(PstnContractTypeBo.class, crit);
        return (PstnContractTypeBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PstnContractTypeBo> getPstnContractTypeList(String groupKeyCode, LocalDate asOfDate) {
		List<PstnContractTypeBo> pctList = new ArrayList<PstnContractTypeBo>();
		Criteria root = new Criteria();
 		if(StringUtils.isNotEmpty(groupKeyCode)) {
			root.addEqualTo("groupKeyCode", groupKeyCode); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PstnContractTypeBo.class, asOfDate, PstnContractTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PstnContractTypeBo.class, PstnContractTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PstnContractTypeBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pctList.addAll(c);
		
		return pctList;
	}
	
	@Override
	public List<PstnContractTypeBo> getPstnContractTypeList(String name, String groupKeyCode, LocalDate asOfDate) {
		List<PstnContractTypeBo> pctList = new ArrayList<PstnContractTypeBo>();
		Criteria root = new Criteria();
		Set<String> coll = new HashSet<String>();
		
		if(StringUtils.isNotEmpty(name) 
				&& !ValidationUtils.isWildCard(name)) {
			root.addEqualTo("name", name); 
		}
		
 		if(StringUtils.isNotEmpty(groupKeyCode)) {
 			root.addEqualTo("groupKeyCode", groupKeyCode);
		}
 		
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PstnContractTypeBo.class, asOfDate, PstnContractTypeBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PstnContractTypeBo.class, PstnContractTypeBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PstnContractTypeBo.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pctList.addAll(c);
		
		return pctList;
	}

}
