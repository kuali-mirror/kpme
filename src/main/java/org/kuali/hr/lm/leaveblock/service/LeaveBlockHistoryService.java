package org.kuali.hr.lm.leaveblock.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;


public interface LeaveBlockHistoryService {
	
	public void saveLeaveBlockHistory(LeaveBlockHistory leaveBlockHistory);
	
	public void saveLeaveBlockHistoryList(List<LeaveBlockHistory> leaveBlockHistories);
	
    public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(String lmLeaveBlockId);
    
    public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId, List<String> requestStatus);

    public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(String principalId, Date beginDate, Date endDate, boolean considerModifiedUser);
    
}
