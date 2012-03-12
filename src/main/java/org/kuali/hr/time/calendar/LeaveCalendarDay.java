package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

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
            leaveBlockRenderers.add(new LeaveBlockRenderer(leaveBlock));
        }
    }
}
