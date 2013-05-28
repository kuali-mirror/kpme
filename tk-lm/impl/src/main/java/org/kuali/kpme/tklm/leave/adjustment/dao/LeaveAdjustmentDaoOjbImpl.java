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
package org.kuali.kpme.tklm.leave.adjustment.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.tklm.leave.adjustment.LeaveAdjustment;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveAdjustmentDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveAdjustmentDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<LeaveAdjustment> getLeaveAdjustments(String principalId, LocalDate asOfDate) {
        List<LeaveAdjustment> leaveAdjustments = new ArrayList<LeaveAdjustment>();
        Criteria root = new Criteria();
        root.addEqualTo("principalId", principalId);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LeaveAdjustment.class, asOfDate, LeaveAdjustment.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeaveAdjustment.class, LeaveAdjustment.EQUAL_TO_FIELDS, false));

        Criteria activeFilter = new Criteria(); // Inner Join For Activity
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(LeaveAdjustment.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	leaveAdjustments.addAll(c);
        }
        return leaveAdjustments;
    }

	@Override
	public LeaveAdjustment getLeaveAdjustment(String lmLeaveAdjustmentId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("lmLeaveAdjustmentId", lmLeaveAdjustmentId);
        Query query = QueryFactory.newQuery(LeaveAdjustment.class, crit);
        return (LeaveAdjustment) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<LeaveAdjustment> getLeaveAdjustments(LocalDate fromEffdt, LocalDate toEffdt, String principalId, String accrualCategory, String earnCode) {
        List<LeaveAdjustment> results = new ArrayList<LeaveAdjustment>();
    	
    	Criteria root = new Criteria();
    	
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
        
        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("principalId", principalId);
        }

        if (StringUtils.isNotBlank(accrualCategory)) {
            root.addLike("accrualCategory", accrualCategory);
        }

        if (StringUtils.isNotBlank(earnCode)) {
        	root.addLike("earnCode", earnCode);
        }

        Query query = QueryFactory.newQuery(LeaveAdjustment.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}

}
