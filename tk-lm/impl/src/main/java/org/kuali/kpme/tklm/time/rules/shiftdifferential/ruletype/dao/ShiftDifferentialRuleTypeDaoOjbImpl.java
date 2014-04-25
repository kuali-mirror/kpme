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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.ShiftDifferentialRuleType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ShiftDifferentialRuleTypeDaoOjbImpl extends PlatformAwareDaoBaseOjb implements ShiftDifferentialRuleTypeDao {
	
	@Override
	public ShiftDifferentialRuleType getShiftDifferentialRuleType(String id) {
		Object o = this.getPersistenceBrokerTemplate().getObjectById(ShiftDifferentialRuleType.class, id);
		
		return (ShiftDifferentialRuleType)o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ShiftDifferentialRuleType getActiveShiftDifferentialRuleType(String name, LocalDate asOfDate) {

		Criteria root = new Criteria();

		root.addEqualTo("name", name);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(ShiftDifferentialRuleType.class, asOfDate, ShiftDifferentialRuleType.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(ShiftDifferentialRuleType.class, ShiftDifferentialRuleType.BUSINESS_KEYS, false));


		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(ShiftDifferentialRuleType.class, root);
		ShiftDifferentialRuleType obj = (ShiftDifferentialRuleType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		return obj;
	}




}
