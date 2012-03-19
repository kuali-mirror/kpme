package org.kuali.hr.lm.leaveblock.service;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;
import org.kuali.hr.lm.leaveblock.dao.LeaveStatusHistoryDao;

public class LeaveStatusHistoryServiceImpl implements LeaveStatusHistoryService {

	private LeaveStatusHistoryDao leaveStatusHistoryDao;
	
	public void setLeaveStatusHistoryDao(LeaveStatusHistoryDao leaveStatusHistoryDao) {
		this.leaveStatusHistoryDao = leaveStatusHistoryDao;
	}

	@Override
	public void saveLeaveStatusHistory(LeaveStatusHistory leaveStatusHistory) {
		this.leaveStatusHistoryDao.saveOrUpdate(leaveStatusHistory);

	}

	@Override
	public void saveLeaveStatusHistoryList(
			List<LeaveStatusHistory> leaveStatusHistories) {
		this.leaveStatusHistoryDao.saveOrUpdate(leaveStatusHistories);

	}

	@Override
	public List<LeaveStatusHistory> getLeaveStatusHistoryByLmLeaveBlockId(
			String lmLeaveBlockId) {
		return leaveStatusHistoryDao.getLeaveStatusHistoryByLmLeaveBlockId(lmLeaveBlockId);
	}

	@Override
	public LeaveStatusHistory getLeaveStatusHistoryByLmLeaveBlockIdAndRequestStatus(
			String lmLeaveBlockId, List<String> requestStatus) {
		return leaveStatusHistoryDao.getLeaveStatusHistoryByLeaveBlockIdAndRequestStatus(lmLeaveBlockId, requestStatus);
	}

}
