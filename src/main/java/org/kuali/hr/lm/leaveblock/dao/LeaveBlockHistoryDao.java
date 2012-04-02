package org.kuali.hr.lm.leaveblock.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;

public interface LeaveBlockHistoryDao {
	
	public void saveOrUpdate(LeaveBlockHistory leaveBlockHistory);
	public void saveOrUpdate(List<LeaveBlockHistory> leaveBlockHistoryList);
	public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(String lmLeaveBlockId);
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId, List<String> requestStatus);
	
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(String principalId, Date beginDate, Date endDate, boolean considerModifiedUser);

}
