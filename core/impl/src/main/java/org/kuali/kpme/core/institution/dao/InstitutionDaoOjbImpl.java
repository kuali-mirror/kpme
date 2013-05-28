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
package org.kuali.kpme.core.institution.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.institution.Institution;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class InstitutionDaoOjbImpl extends PlatformAwareDaoBaseOjb implements InstitutionDao {
	
    
    @Override
    public Institution getInstitution(String institution, LocalDate asOfDate) {
		Institution inst = null;

		Criteria root = new Criteria();
		root.addEqualTo("institutionCode", institution);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Institution.class, asOfDate, Institution.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Institution.class, Institution.EQUAL_TO_FIELDS, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(Institution.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			inst = (Institution) obj;
		}

		return inst;
    }
    
	@Override
	public List<Institution> getActiveInstitutions(LocalDate asOfDate) {
		List<Institution> institutions = new ArrayList<Institution>();
		
		Criteria root = new Criteria();

		root.addEqualTo("effectiveDate",  OjbSubQueryUtil.getEffectiveDateSubQuery(Institution.class, asOfDate, Institution.EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(Institution.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(!c.isEmpty())
			institutions.addAll(c);
		
		return institutions;
	}

	@Override
	public List<Institution> getInstitutionByCode(String code) {
		List<Institution> institutions = new ArrayList<Institution>();
		
		Criteria root = new Criteria();

		root.addEqualTo("institutionCode", code);

		Query query = QueryFactory.newQuery(Institution.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(!c.isEmpty())
			institutions.addAll(c);
		
		return institutions;

	}

	@Override
	public Institution getInstitutionById(String institutionId) {
		
		Criteria root = new Criteria();

		root.addEqualTo("institutionId", institutionId);

		Query query = QueryFactory.newQuery(Institution.class, root);
		
		return (Institution) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

	}

}
