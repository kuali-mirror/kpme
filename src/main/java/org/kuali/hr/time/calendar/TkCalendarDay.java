package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public class TkCalendarDay {
	private List<TimeBlock> timeblocks = new ArrayList<TimeBlock>();

	public List<TimeBlock> getTimeblocks() {
		return timeblocks;
	}

	public void setTimeblocks(List<TimeBlock> timeblocks) {
		this.timeblocks = timeblocks;
	}
}
