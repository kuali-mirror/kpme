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
package org.kuali.kpme.tklm.leave.accrual.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.tklm.leave.accrual.PrincipalAccrualRan;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PrincipalAccrualRanDaoOjbImpl extends PlatformAwareDaoBaseOjb implements PrincipalAccrualRanDao{
	
	@Override
	public PrincipalAccrualRan getLastPrincipalAccrualRan(String principalId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);

		Query query = QueryFactory.newQuery(PrincipalAccrualRan.class, crit);
		return (PrincipalAccrualRan)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
