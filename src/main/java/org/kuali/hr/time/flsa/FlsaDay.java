package org.kuali.hr.time.flsa;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public class FlsaDay {
	private List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();

	public List<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}

	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		this.timeBlocks = timeBlocks;
	}
}
