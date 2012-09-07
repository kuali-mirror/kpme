package org.kuali.hr.time.calendar;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.timeblock.TimeBlock;

import java.util.ArrayList;
import java.util.List;

public class TkCalendarDay extends CalendarDay{
	private List<TimeBlock> timeblocks = new ArrayList<TimeBlock>();
    private List<TimeBlockRenderer> blockRenderers = new ArrayList<TimeBlockRenderer>();
    private String dayNumberString;
    private Boolean gray;
    private List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
    private List<LeaveBlockRenderer> leaveBlockRenderers = new ArrayList<LeaveBlockRenderer>();

	public List<TimeBlock> getTimeblocks() {
		return timeblocks;
	}

	public void setTimeblocks(List<TimeBlock> timeblocks) {
		this.timeblocks = timeblocks;
        for (TimeBlock tb : timeblocks) {
            blockRenderers.add(new TimeBlockRenderer(tb));
        }
	}

    public List<TimeBlockRenderer> getBlockRenderers() {
        return blockRenderers;
    }

    public String getDayNumberString() {
        return dayNumberString;
    }

    public void setDayNumberString(String dayNumberString) {
        this.dayNumberString = dayNumberString;
    }

	public Boolean getGray() {
		return gray;
	}

	public void setGray(Boolean gray) {
		this.gray = gray;
	}

	public List<LeaveBlock> getLeaveBlocks() {
		return leaveBlocks;
	}

	public void setLeaveBlocks(List<LeaveBlock> leaveBlocks) {
		this.leaveBlocks = leaveBlocks;
        for (LeaveBlock lb : leaveBlocks) {
            leaveBlockRenderers.add(new LeaveBlockRenderer(lb));
        }
	}

	public List<LeaveBlockRenderer> getLeaveBlockRenderers() {
		return leaveBlockRenderers;
	}

	public void setLeaveBlockRenderers(List<LeaveBlockRenderer> leaveBlockRenderers) {
		this.leaveBlockRenderers = leaveBlockRenderers;
	}

}
