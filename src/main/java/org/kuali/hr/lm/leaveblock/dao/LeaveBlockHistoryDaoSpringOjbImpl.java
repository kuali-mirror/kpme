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
package org.kuali.hr.lm.leaveblock.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveBlockHistoryDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveBlockHistoryDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
			.getLogger(LeaveBlockHistoryDaoSpringOjbImpl.class);

	@Override
	public void saveOrUpdate(LeaveBlockHistory leaveBlockHistory) {
		this.getPersistenceBrokerTemplate().store(leaveBlockHistory);
	}

	@Override
	public void saveOrUpdate(List<LeaveBlockHistory> leaveBlockHistoryList) {
		if (leaveBlockHistoryList != null) {
			for (LeaveBlockHistory leaveBlockHistory : leaveBlockHistoryList) {
				this.getPersistenceBrokerTemplate().store(leaveBlockHistory);
			}

		}
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(
			String lmLeaveBlockId) {
		Criteria recordCriteria = new Criteria();
		recordCriteria.addEqualTo("lmLeaveBlockId", lmLeaveBlockId);
		Query query = QueryFactory.newQuery(LeaveBlockHistory.class,
				recordCriteria);
		return (List<LeaveBlockHistory>) this.getPersistenceBrokerTemplate()
				.getCollectionByQuery(query);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,
			List<String> requestStatus) {
		Criteria recordCriteria = new Criteria();
		recordCriteria.addEqualTo("principalId", principalId);
		if (requestStatus != null) {
			recordCriteria.addIn("requestStatus", requestStatus);
		}
		Query query = QueryFactory.newQuery(LeaveBlockHistory.class,
				recordCriteria);
		return (List<LeaveBlockHistory>) this.getPersistenceBrokerTemplate()
				.getCollectionByQuery(query);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(String principalId,
			Date beginDate, Date endDate, boolean considerModifiedUser) {
		
		List<LeaveBlockHistory> leaveBlockHistories = new ArrayList<LeaveBlockHistory>();
		
		Criteria root = new Criteria();
		root.addEqualTo("principalId", principalId);
		root.addGreaterOrEqualThan("leaveDate", beginDate);
		root.addLessOrEqualThan("leaveDate", endDate);
		root.addEqualTo("action",LMConstants.ACTION.MODIFIED);
		if(considerModifiedUser) {
			root.addNotEqualTo("principalIdModified", principalId);
		} 
		
		Criteria root1 = new Criteria();
		root1.addEqualTo("principalId", principalId);
		root1.addGreaterOrEqualThan("leaveDate", beginDate);
		root1.addLessOrEqualThan("leaveDate", endDate);
		root1.addEqualTo("action",LMConstants.ACTION.DELETE);
		if(considerModifiedUser) {
			root1.addNotEqualTo("principalIdDeleted", principalId);
		} 
		
		root.addOrCriteria(root1);
		
		Query query = QueryFactory.newQuery(LeaveBlockHistory.class, root);
		Collection c = this.getPersistenceBrokerTemplate()
				.getCollectionByQuery(query);

		if (c != null) {
			leaveBlockHistories.addAll(c);
		}
		return leaveBlockHistories;
	}
}
