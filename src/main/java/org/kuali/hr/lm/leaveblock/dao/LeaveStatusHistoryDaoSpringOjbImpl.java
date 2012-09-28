/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leaveblock.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveStatusHistoryDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveStatusHistoryDao{

	@SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(LeaveStatusHistoryDaoSpringOjbImpl.class);
	
	@Override
	public void saveOrUpdate(LeaveStatusHistory leaveStatusHistory) {
		this.getPersistenceBrokerTemplate().store(leaveStatusHistory);
		
	}

	@Override
	public void saveOrUpdate(List<LeaveStatusHistory> leaveStatusHistoryList) {
		if(leaveStatusHistoryList != null){
			for(LeaveStatusHistory leaveStatusHistory : leaveStatusHistoryList) {
				this.getPersistenceBrokerTemplate().store(leaveStatusHistory);
			}
			
		}
		
	}

	@Override
	public List<LeaveStatusHistory> getLeaveStatusHistoryByLmLeaveBlockId(
			String lmLeaveBlockId) {
		Criteria searchCriteria = new Criteria();
		searchCriteria.addEqualTo("lmLeaveBlockId", lmLeaveBlockId);
		Query query = QueryFactory.newQuery(LeaveStatusHistory.class, searchCriteria);
		return (List<LeaveStatusHistory>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	}
	
	public LeaveStatusHistory getLeaveStatusHistoryByLeaveBlockIdAndRequestStatus(String lmLeaveBlockId, List<String> requestStatus){
		Criteria searchCriteria = new Criteria();
		searchCriteria.addEqualTo("lmLeaveBlockId", lmLeaveBlockId);
		if(requestStatus != null) {
			searchCriteria.addIn("requestStatus", requestStatus);
		}
		Query query = QueryFactory.newQuery(LeaveStatusHistory.class, searchCriteria);
		return (LeaveStatusHistory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
