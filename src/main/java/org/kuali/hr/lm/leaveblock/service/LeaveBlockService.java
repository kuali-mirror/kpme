package org.kuali.hr.lm.leaveblock.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;

public interface LeaveBlockService {
    public LeaveBlock getLeaveBlock(Long leaveBlockId);
    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, Date beginDate, Date endDate);

    public void saveLeaveBlocks(List<LeaveBlock> leaveBlocks);

    public void saveLeaveBlock(LeaveBlock leaveBlock);

    /**
     * The deletion marks the leave block inactive instead of removing the row from the database.
     * @param leaveBlockId
     */
    public void deleteLeaveBlock(long leaveBlockId);

    public void addLeaveBlocks(DateTime beginDate, DateTime endDate, CalendarEntries ce, String selectedLeaveCode, BigDecimal hours, String description, Assignment selectedAssignment);
    
    /**
     * 
     * @param principalId
     * @param requestStatus
     * @param currentDate currentDate to get the records for the future date, pass null when not required
     * @return List of LeaveBlocks
     */
    public List<LeaveBlock> getLeaveBlocks(String principalId, String requestStatus, Date currentDate);
    /**
     * Get the list of leave blocks from the given leaveDate for the principalId
     * @param principalId
     * @param leaveDate
     * @return List of LeaveBlocks
     */
    public List<LeaveBlock> getLeaveBlocksForDate(String principalId, Date leaveDate);
    
    /**
     * Calculate the accrual balance (sum of the leave blocks at that time.)
     * @param leaveDate
     * @param accrualCategoryId
     * @param principalId
     * @return sum of leave amount of leave blocks
     */
    public Double calculateAccrualbalance(Date leaveDate, String accrualCategoryId, String principalId);
}
