package org.kuali.hr.lm.leaveblock.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeaveBlockHistoryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements LeaveBlockHistoryDao {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(LeaveBlockHistoryDaoSpringOjbImpl.class);
	
	@Override
	public void saveOrUpdate(LeaveBlockHistory leaveBlockHistory) {
		 this.getPersistenceBrokerTemplate().store(leaveBlockHistory);
	}

	@Override
	public void saveOrUpdate(List<LeaveBlockHistory> leaveBlockHistoryList) {
		if(leaveBlockHistoryList != null) {
			for(LeaveBlockHistory leaveBlockHistory : leaveBlockHistoryList) {
				this.getPersistenceBrokerTemplate().store(leaveBlockHistory);
			}
			
		}
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(
			String lmLeaveBlockId) {
		Criteria recordCriteria = new Criteria();
		recordCriteria.addEqualTo("lmLeaveBlockId", lmLeaveBlockId);
		Query query = QueryFactory.newQuery(LeaveBlockHistory.class, recordCriteria);
		return (List<LeaveBlockHistory>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,
			List<String> requestStatus) {
		Criteria recordCriteria = new Criteria();
		recordCriteria.addEqualTo("principalId", principalId);
		if(requestStatus != null) {
			recordCriteria.addIn("requestStatus", requestStatus);
		}
		Query query = QueryFactory.newQuery(LeaveBlockHistory.class, recordCriteria);
		return (List<LeaveBlockHistory>)this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	}

}
