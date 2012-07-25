package org.kuali.hr.time.calendar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;

public class LeaveCalendarDay extends CalendarDay {

    private List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
    private List<LeaveBlockRenderer> leaveBlockRenderers = new ArrayList<LeaveBlockRenderer>();

    public List<LeaveBlockRenderer> getLeaveBlockRenderers() {
        return leaveBlockRenderers;
    }

    public List<LeaveBlock> getLeaveBlocks() {
        return leaveBlocks;
    }

    public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
        this.leaveBlocks = leaveBlocks;
        for (LeaveBlock leaveBlock : leaveBlocks) {
        	// KPME-1690 : set if usage then amount should be appeared as negative.
        	if(leaveBlock.getRequestStatus() == null || StringUtils.equalsIgnoreCase(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.USAGE)) {
        		leaveBlock.setLeaveAmount(leaveBlock.getLeaveAmount().multiply(new BigDecimal(-1)));
        	}
            leaveBlockRenderers.add(new LeaveBlockRenderer(leaveBlock));
        }
    }
}
