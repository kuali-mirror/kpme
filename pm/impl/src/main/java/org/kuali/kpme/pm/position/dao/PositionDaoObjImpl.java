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
package org.kuali.kpme.pm.position.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.pm.position.Position;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionDao {

	@Override
	public Position getPosition(String id) {
		 Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", id);

        Query query = QueryFactory.newQuery(Position.class, crit);
        return (Position) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<Position> getPositions(String positionNum, String description, String campus,
            String institution, String classificationTitle, String positionType, String poolEligible,
			LocalDate fromEffdt, LocalDate toEffdt, String active,
			String showHistory) {
		List<Position> results = new ArrayList<Position>();
	        
    	Criteria root = new Criteria();

    	// KPME-2695
        if (StringUtils.isNotBlank(positionNum)) {
            root.addLike("UPPER(`position_nbr`)", positionNum.toUpperCase()); // just in case position number is not a number
        }

        if (StringUtils.isNotBlank(description)) {
            root.addLike("UPPER(`description`)", description.toUpperCase());
        }

        if (StringUtils.isNotBlank(campus)) {
            root.addLike("UPPER(`campus`)", campus.toUpperCase());
        }

        if (StringUtils.isNotBlank(institution)) {
            root.addLike("UPPER(`institution`)", institution.toUpperCase());
        }

        if (StringUtils.isNotBlank(classificationTitle)) {
            root.addLike("UPPER(`cl_ttl`)", classificationTitle.toUpperCase());
        }

        if (StringUtils.isNotBlank(positionType)) {
            root.addLike("UPPER(`pstn_typ`)", positionType.toUpperCase());
        }

        if (StringUtils.isNotBlank(poolEligible)) {
            root.addEqualTo("poolEligible", poolEligible);

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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(Position.class, effectiveDateFilter, Position.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Position.class, Position.BUSINESS_KEYS, false));
        }
        
        Query query = QueryFactory.newQuery(Position.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}
	
	

	
}
