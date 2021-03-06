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
package org.kuali.kpme.pm.position.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionDao {

	@Override
	public PositionBo getPosition(String id) {
		 Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", id);

        Query query = QueryFactory.newQuery(PositionBo.class, crit);
        return (PositionBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PositionBo> getPositions(String positionNum, String description, String grpKeyCode, String classificationTitle, String positionType, String poolEligible, String positionStatus,
			LocalDate fromEffdt, LocalDate toEffdt, String active,
			String showHistory) {
		List<PositionBo> results = new ArrayList<PositionBo>();
	        
    	Criteria root = new Criteria();

    	// KPME-2695
        if (StringUtils.isNotBlank(positionNum)) {
            root.addLike("UPPER(positionNumber)", positionNum.toUpperCase()); // just in case position number is not a number
        }

        if (StringUtils.isNotBlank(description)) {
            root.addLike("UPPER(description)", description.toUpperCase());
        }

        if (StringUtils.isNotBlank(grpKeyCode)) {
            root.addLike("UPPER(`GRP_KEY_CD`)", grpKeyCode.toUpperCase());
        }

        if (StringUtils.isNotBlank(classificationTitle)) {
            root.addLike("UPPER(classificationTitle)", classificationTitle.toUpperCase());
        }

        if (StringUtils.isNotBlank(positionType)) {
            root.addLike("UPPER(positionType)", positionType.toUpperCase());
        }

        if (StringUtils.isNotBlank(poolEligible)) {
            root.addEqualTo("poolEligible", poolEligible);
        }

        if (StringUtils.isNotBlank(positionStatus)) {
            root.addLike("UPPER(`pstn_status`)", positionStatus.toUpperCase());
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PositionBo.class, effectiveDateFilter, PositionBo.BUSINESS_KEYS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionBo.class, PositionBo.BUSINESS_KEYS, false));
        }


        Query query = QueryFactory.newQuery(PositionBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}
	
	

	
}
