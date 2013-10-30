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
package org.kuali.kpme.core.bo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.bo.dao.HrBusinessObjectDao;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class HrBusinessObjectDaoOjbImpl extends PlatformAwareDaoBaseOjb implements HrBusinessObjectDao {

	private static final String YES = "Y";
	private static final String ACTIVE = "active";
	private static final String EFFECTIVE_DATE = "effectiveDate";


	@SuppressWarnings("unchecked")
	@Override
	public <T extends HrBusinessObjectContract> List<T> getNewerVersionsOf(T bo) {
		List<T> retVal = new ArrayList<T>();
		
		// use bo's effective local date and the business key values to query T's table
		Date effectiveDate = this.extractEffectiveDate(bo);
		if(effectiveDate != null) {
			Criteria criteria = new Criteria();
			this.addBusinessKeyValuesToCriteria(criteria, bo);
			criteria.addEqualTo(ACTIVE, YES);
			criteria.addGreaterThan(EFFECTIVE_DATE, effectiveDate);
			Query query = QueryFactory.newQuery(bo.getClass(), criteria);
			retVal.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
		}
		
       	return retVal;
	}
	
	
	protected void addBusinessKeyValuesToCriteria(Criteria criteria, HrBusinessObjectContract bo) {
		// populate the criteria with the bo's business key value map
		for(Entry<String, Object> entry: bo.getBusinessKeyValuesMap().entrySet() ) {
			criteria.addEqualTo(entry.getKey(), entry.getValue());
		}
	}
	

	protected Date extractEffectiveDate(HrBusinessObjectContract bo) {
		Date retVal = null;
		if(bo.getEffectiveLocalDate() != null) {
			retVal = bo.getEffectiveLocalDate().toDate();
		}
		return retVal;				
	}

}
