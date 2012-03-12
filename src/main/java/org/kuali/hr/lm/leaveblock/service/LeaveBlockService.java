package org.kuali.hr.lm.leaveblock.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;

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

    public void addLeaveBlocks(DateTime beginDate, DateTime endDate, LeaveCalendarDocument lcd, String selectedLeaveCode, BigDecimal hours, String description);
    
    public List<LeaveBlock> getLeaveBlocksByRequestStatus(String requestStatus);
}
