package org.kuali.hr.lm.leaveblock.service;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leaveblock.LeaveStatusHistory;

public interface LeaveStatusHistoryService {

	public void saveLeaveStatusHistory(LeaveStatusHistory leaveStatusHistory);
	
	public void saveLeaveStatusHistoryList(List<LeaveStatusHistory> leaveStatusHistories);
	
    public List<LeaveStatusHistory> getLeaveStatusHistoryByLmLeaveBlockId(String lmLeaveBlockId);
    
    public LeaveStatusHistory getLeaveStatusHistoryByLmLeaveBlockIdAndRequestStatus(String lmLeaveBlockId, List<String> requestStatus);

}
