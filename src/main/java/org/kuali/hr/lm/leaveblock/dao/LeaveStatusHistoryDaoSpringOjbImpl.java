package org.kuali.hr.lm.leaveblock.dao;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeaveStatusHistoryDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements LeaveStatusHistoryDao{

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

}
