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
package org.kuali.kpme.pm.pstnqlfrtype.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PstnQlfrTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PstnQlfrTypeDao {

	@Override
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPstnQlfrTypeId", pmPstnQlfrTypeId);

        Query query = QueryFactory.newQuery(PstnQlfrType.class, crit);
        return (PstnQlfrType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public PstnQlfrType getPstnQlfrTypeByType(String pmPstnQlfrType) {
		Criteria crit = new Criteria();
        crit.addEqualTo("type", pmPstnQlfrType);

        Query query = QueryFactory.newQuery(PstnQlfrType.class, crit);
        return (PstnQlfrType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PstnQlfrType> getAllActivePstnQlfrTypes(LocalDate asOfDate) {
		List<PstnQlfrType> aList = new ArrayList<PstnQlfrType>();
		Criteria root = new Criteria();
		if(asOfDate == null) {
			asOfDate = LocalDate.now();
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PstnQlfrType.class, asOfDate, PstnQlfrType.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PstnQlfrType.class, PstnQlfrType.BUSINESS_KEYS, false));
		root.addEqualTo("active", true);
		Query query = QueryFactory.newQuery(PstnQlfrType.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			aList.addAll(c);
		
		return aList;
	}

}
