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
package org.kuali.kpme.pm.positionResponsibilityOption.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.positionResponsibilityOption.PositionResponsibilityOption;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;


public class PositionResponsibilityOptionDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionResponsibilityOptionDao {

	@Override
	public PositionResponsibilityOption getPositionResponsibilityOptionById(
			String prOptionId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("prOptionId", prOptionId);

        Query query = QueryFactory.newQuery(PositionResponsibilityOption.class, crit);
        return (PositionResponsibilityOption) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public List<PositionResponsibilityOption> getAllActivePstnRspOptions() {
		List<PositionResponsibilityOption> aList = new ArrayList<PositionResponsibilityOption>();
		Criteria root = new Criteria();
		root.addEqualTo("active", true);
		Query query = QueryFactory.newQuery(PositionResponsibilityOption.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			aList.addAll(c);
		
		return aList;
	}
	
	
}
