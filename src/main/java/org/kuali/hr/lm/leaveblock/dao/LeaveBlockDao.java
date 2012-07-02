package org.kuali.hr.lm.leaveblock.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;

public interface LeaveBlockDao {
    public LeaveBlock getLeaveBlock(Long leaveBlockId);
    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, Date beginDate, Date endDate);
    public void saveOrUpdate(LeaveBlock leaveBlock);
    public List<LeaveBlock> getLeaveBlocks(String principalId, String requestStatus, Date currentDate);
    public List<LeaveBlock> getLeaveBlocksForDate(String principalId, Date leaveDate);
    public List<LeaveBlock> getLeaveBlocks(Date leaveDate, String accrualCategoryId, String principalId);
    public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, Date leaveDate);
}

