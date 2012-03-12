package org.kuali.hr.lm.leaveblock.service;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;


public interface LeaveBlockHistoryService {
	
	public void saveLeaveBlockHistory(LeaveBlockHistory leaveBlockHistory);
	
	public void saveLeaveBlockHistoryList(List<LeaveBlockHistory> leaveBlockHistories);
	
    public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(String lmLeaveBlockId);
}
