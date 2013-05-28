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
package org.kuali.kpme.tklm.leave.timeoff.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class SystemScheduledTimeOffDaoOjbImpl extends PlatformAwareDaoBaseOjb implements SystemScheduledTimeOffDao {

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmSystemScheduledTimeOffId", lmSystemScheduledTimeOffId);
		Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, crit);
		return (SystemScheduledTimeOff) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, LocalDate startDate, LocalDate endDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addBetween("accruedDate", startDate.toDate(), endDate.toDate());
		return (List<SystemScheduledTimeOff>)this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(SystemScheduledTimeOff.class, root));
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(String leavePlan, LocalDate startDate) {
		Criteria root = new Criteria();
		root.addEqualTo("leavePlan", leavePlan);
		root.addEqualTo("accruedDate", startDate.toDate());
		return (SystemScheduledTimeOff)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(SystemScheduledTimeOff.class, root));
	}

	@Override
    @SuppressWarnings("unchecked")
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(LocalDate fromEffdt, LocalDate toEffdt, String earnCode, LocalDate fromAccruedDate,LocalDate toAccruedDate, 
    		LocalDate fromSchTimeOffDate, LocalDate toSchTimeOffDate, String active, String showHistory) {
        
    	List<SystemScheduledTimeOff> results = new ArrayList<SystemScheduledTimeOff>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(earnCode)) {
            root.addLike("earnCode", earnCode);
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

        if (fromAccruedDate != null) {
            root.addGreaterOrEqualThan("accruedDate", fromAccruedDate.toDate());
        }
        if (toAccruedDate != null) {
            root.addLessOrEqualThan("accruedDate", toAccruedDate.toDate());
        }

        if (fromSchTimeOffDate != null) {
            root.addGreaterOrEqualThan("scheduledTimeOffDate", fromSchTimeOffDate.toDate());
        }
        if (toSchTimeOffDate != null) {
            root.addLessOrEqualThan("scheduledTimeOffDate", toSchTimeOffDate.toDate());
        }
        
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
//			      ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                    .add("earnCode")
//                    .add("accruedDate") //Not part of Primary Business Key
//                    .build();
//        	KPME-2273/1965 Primary Business Keys List. Using list from now on
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(SystemScheduledTimeOff.class, effectiveDateFilter, SystemScheduledTimeOff.fields, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SystemScheduledTimeOff.class, SystemScheduledTimeOff.fields, false));
        }
        
        Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }
	
	@Override
    @SuppressWarnings("unchecked")
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffsForLeavePlan(LocalDate fromAccruedDate,LocalDate toAccruedDate, String leavePlan) {
    	List<SystemScheduledTimeOff> results = new ArrayList<SystemScheduledTimeOff>();
    	Criteria root = new Criteria();

        if (fromAccruedDate != null) {
            root.addGreaterOrEqualThan("accruedDate", fromAccruedDate.toDate());
        }
        if (toAccruedDate != null) {
            root.addLessOrEqualThan("accruedDate", toAccruedDate.toDate());
        }
        
        if(StringUtils.isNotEmpty(leavePlan)) {
        	root.addEqualTo("leavePlan", leavePlan);
        }
    	Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

//        ImmutableList<String> fields = new ImmutableList.Builder<String>()
//                .add("leavePlan")
//                .add("accruedDate") //Not part of Primary Business Key
//                .build();
//    	KPME-2273/1965 Primary Business Keys List. Using list from now on
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithoutFilter(SystemScheduledTimeOff.class, SystemScheduledTimeOff.fields, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(SystemScheduledTimeOff.class, SystemScheduledTimeOff.fields, false));
        
        Query query = QueryFactory.newQuery(SystemScheduledTimeOff.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        
        return results;
    }

}
