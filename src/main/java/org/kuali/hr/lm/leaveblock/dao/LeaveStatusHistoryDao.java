package org.kuali.hr.lm.leaveblock.dao;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;


public interface LeaveStatusHistoryDao {

	public void saveOrUpdate(LeaveStatusHistory leaveStatusHistory);
	public void saveOrUpdate(List<LeaveStatusHistory> leaveStatusHistoryList);
	public List<LeaveStatusHistory> getLeaveStatusHistoryByLmLeaveBlockId(String lmLeaveBlockId);
	public LeaveStatusHistory getLeaveStatusHistoryByLeaveBlockIdAndRequestStatus(String lmLeaveBlockId, List<String> requestStatus);
	
}
