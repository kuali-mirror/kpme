package org.kuali.hr.lm.leaveblock.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leaveblock.dao.LeaveBlockHistoryDao;

public class LeaveBlockHistoryServiceImpl implements LeaveBlockHistoryService {

	private LeaveBlockHistoryDao leaveBlockHistoryDao;
	
	public void setLeaveBlockHistoryDao(LeaveBlockHistoryDao leaveBlockHistoryDao) {
		this.leaveBlockHistoryDao = leaveBlockHistoryDao;
	}

	@Override
	public void saveLeaveBlockHistory(LeaveBlockHistory leaveBlockHistory) {
		 leaveBlockHistoryDao.saveOrUpdate(leaveBlockHistory);
	}

	@Override
	public void saveLeaveBlockHistoryList(List<LeaveBlockHistory> leaveBlockHistoryList) {
		leaveBlockHistoryDao.saveOrUpdate(leaveBlockHistoryList);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(
			String lmLeaveBlockId) {
		return leaveBlockHistoryDao.getLeaveBlockHistoryByLmLeaveBlockId(lmLeaveBlockId);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,
			List<String> requestStatus) {
		return leaveBlockHistoryDao.getLeaveBlockHistories(principalId, requestStatus);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(
			String principalId, Date beginDate, Date endDate, boolean considerModifiedUser) {
		return leaveBlockHistoryDao.getLeaveBlockHistoriesForLeaveDisplay(principalId, beginDate, endDate, considerModifiedUser);
	}

	
	

}
