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
package org.kuali.kpme.core.position.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionBaseDaoOjbImpl extends PlatformAwareDaoBaseOjb implements PositionBaseDao {
    
    @Override
    public PositionBase getPosition(String hrPositionId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", hrPositionId);

        Query query = QueryFactory.newQuery(PositionBase.class, crit);
        return (PositionBase) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }


    @Override
    public PositionBase getPosition(String positionNumber, LocalDate effectiveDate) {
        Criteria root = new Criteria();

        root.addEqualTo("positionNumber", positionNumber);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionBase.class, effectiveDate, PositionBase.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionBase.class, PositionBase.EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionBase.class, root);
        return (PositionBase) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

	@Override
    @SuppressWarnings("unchecked")
    public List<PositionBase> getPositions(String positionNum, String description, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        List<PositionBase> results = new ArrayList<PositionBase>();
        
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(positionNum)) {
            root.addLike("positionNumber", positionNum);
        }

        if (StringUtils.isNotBlank(description)) {
            root.addLike("description", description);
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
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PositionBase.class, effectiveDateFilter, PositionBase.EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionBase.class, PositionBase.EQUAL_TO_FIELDS, false));
        }
        
        Query query = QueryFactory.newQuery(PositionBase.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    /*@Override
    public PositionNumber getNextUniquePositionNumber() {
        Criteria crit = new Criteria();
        ReportQueryByCriteria query = QueryFactory.newReportQuery(PositionNumber.class, crit);
        query.setAttributes(new String[]{"max(id)"});
        return (PositionNumber) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public void saveOrUpdate(PositionNumber positionNumber) {
        this.getPersistenceBrokerTemplate().store(positionNumber);
    }*/

}
