package org.kuali.hr.time.calendar;

import java.math.BigDecimal;

import org.kuali.hr.lm.leaveblock.LeaveBlock;

public class LeaveBlockRenderer {
    private LeaveBlock leaveBlock;
//    private String leaveCode;
    private String earnCode;
    private BigDecimal hours;
    private long leaveBlockId;
    private String documentId;
    private String assignmentTitle;

    public  LeaveBlockRenderer(LeaveBlock leaveBlock) {
        this.leaveBlock = leaveBlock;
    }

    public LeaveBlock getLeaveBlock() {
        return leaveBlock;
    }

    public BigDecimal getHours() {
        return leaveBlock.getLeaveAmount();
    }

    public String getEarnCode() {
        return leaveBlock.getEarnCode();
    }

    public String getLeaveBlockId() {
        return leaveBlock.getLmLeaveBlockId();
    }

    public String getDocumentId() {
        return leaveBlock.getDocumentId();
    }

	public String getAssignmentTitle() {
		return leaveBlock.getAssignmentTitle();
	}

}
