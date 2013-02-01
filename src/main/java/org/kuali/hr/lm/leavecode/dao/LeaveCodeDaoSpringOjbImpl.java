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
package org.kuali.hr.lm.leavecode.dao;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveCodeDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveCodeDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("leaveCode")
            .add("leavePlan")
            .add("principalId")
            .build();
	private static final Logger LOG = Logger.getLogger(LeaveCodeDaoSpringOjbImpl.class);

	@Override
	public LeaveCode getLeaveCode(String lmLeaveCodeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeaveCodeId", lmLeaveCodeId);
		Query query = QueryFactory.newQuery(LeaveCode.class, crit);
		return (LeaveCode) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public List<LeaveCode> getLeaveCodes(String leavePlan, Date asOfDate){
		List<LeaveCode> leaveCodes = new ArrayList<LeaveCode>();
		Criteria root = new Criteria();

        java.sql.Date effDate = asOfDate == null ? null : new java.sql.Date(asOfDate.getTime());
		root.addEqualTo("leavePlan", leavePlan);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(LeaveCode.class, effDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeaveCode.class, EQUAL_TO_FIELDS, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(LeaveCode.class, root);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

		if (c != null) {
			leaveCodes.addAll(c);
		}	
		return leaveCodes;
	}

	@Override
    public LeaveCode getLeaveCode(String leaveCode, Date asOfDate) {
		LeaveCode myleaveCode = null;
		Criteria root = new Criteria();
		root.addEqualTo("leaveCode", leaveCode);
		root.addLessOrEqualThan("effectiveDate", asOfDate);
		root.addEqualTo("active",true);
		
		Query query = QueryFactory.newQuery(LeaveCode.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		if (obj != null) {
			myleaveCode = (LeaveCode) obj;
		}

		return myleaveCode;
    }
}
