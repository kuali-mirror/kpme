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
package org.kuali.kpme.tklm.leave.block.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveBlockHistoryDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveBlockHistoryDao {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger
			.getLogger(LeaveBlockHistoryDaoOjbImpl.class);

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
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,String requestStatus, String action, LocalDate currentDate) {
		Criteria recordCriteria = new Criteria();
		recordCriteria.addEqualTo("principalId", principalId);
		if (requestStatus != null) {
			recordCriteria.addEqualTo("requestStatus", requestStatus);
		}
		if(currentDate != null) {
			recordCriteria.addGreaterThan("leaveDate", currentDate.toDate());
		}
		if(action != null) {
			recordCriteria.addEqualTo("action", action);
		}
		Query query = QueryFactory.newQuery(LeaveBlockHistory.class,
				recordCriteria);
		return (List<LeaveBlockHistory>) this.getPersistenceBrokerTemplate()
				.getCollectionByQuery(query);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(String principalId,
			LocalDate beginDate, LocalDate endDate, boolean considerModifiedUser) {
		
		List<LeaveBlockHistory> leaveBlockHistories = new ArrayList<LeaveBlockHistory>();
		
		Criteria root = new Criteria();
		root.addEqualTo("principalId", principalId);
		root.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
		root.addLessOrEqualThan("leaveDate", endDate.toDate());
		root.addEqualTo("action",HrConstants.ACTION.MODIFIED);
		if(considerModifiedUser) {
			root.addNotEqualTo("principalIdModified", principalId);
		} 
		
		Criteria root1 = new Criteria();
		root1.addEqualTo("principalId", principalId);
		root1.addGreaterOrEqualThan("leaveDate", beginDate.toDate());
		root1.addLessOrEqualThan("leaveDate", endDate.toDate());
		root1.addEqualTo("action",HrConstants.ACTION.DELETE);
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

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate) {
	   	List<LeaveBlockHistory> leaveBlocks = new ArrayList<LeaveBlockHistory>();
        Criteria criteria = new Criteria();

        //document id....
        //get document, and cal entry, fill in query data
        if (StringUtils.isNotBlank(documentId)) {
            TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
            if (tsdh == null) {
                return Collections.emptyList();
            }
            criteria.addGreaterOrEqualThan("beginTimestamp", tsdh.getBeginDate());
            criteria.addLessOrEqualThan("endTimestamp",tsdh.getEndDate());
            criteria.addEqualTo("principalId", tsdh.getPrincipalId());
        }


        if(fromDate != null) {
        	criteria.addGreaterOrEqualThan("beginTimestamp", fromDate.toDate());
        }
        if(toDate != null) {
        	criteria.addLessOrEqualThan("endTimestamp",toDate.toDate());
        }
        if(StringUtils.isNotBlank(principalId)) {
        	criteria.addEqualTo("principalId", principalId);
        }
        if(StringUtils.isNotBlank(userPrincipalId)) {
        	criteria.addEqualTo("principalIdModified", userPrincipalId);
        }
        criteria.addEqualTo("leaveBlockType",LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR);
        Query query = QueryFactory.newQuery(LeaveBlockHistory.class, criteria);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	leaveBlocks.addAll(c);
        }
        return leaveBlocks;
	}
	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate, String leaveBlockType) {
	   	List<LeaveBlockHistory> leaveBlocks = new ArrayList<LeaveBlockHistory>();
        Criteria criteria = new Criteria();

        //document id....
        //get document, and cal entry, fill in query data
        if (StringUtils.isNotBlank(documentId)) {
            TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
            if (tsdh == null) {
                return Collections.emptyList();
            }
            criteria.addGreaterOrEqualThan("beginTimestamp", tsdh.getBeginDate());
            criteria.addLessOrEqualThan("endTimestamp",tsdh.getEndDate());
            criteria.addEqualTo("principalId", tsdh.getPrincipalId());
        }


        if(fromDate != null) {
        	criteria.addGreaterOrEqualThan("beginTimestamp", fromDate.toDate());
        }
        if(toDate != null) {
        	criteria.addLessOrEqualThan("endTimestamp",toDate.toDate());
        }
        if(StringUtils.isNotBlank(principalId)) {
        	criteria.addEqualTo("principalId", principalId);
        }
        if(StringUtils.isNotBlank(userPrincipalId)) {
        	criteria.addEqualTo("principalIdModified", userPrincipalId);
        }
        criteria.addEqualTo("leaveBlockType",leaveBlockType);
        Query query = QueryFactory.newQuery(LeaveBlockHistory.class, criteria);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
        	leaveBlocks.addAll(c);
        }
        return leaveBlocks;
	}
}
