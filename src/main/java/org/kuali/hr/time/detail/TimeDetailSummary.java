package org.kuali.hr.time.detail;

import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public class TimeDetailSummary {
	private List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
	private Integer numberOfDays;

	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		this.timeBlocks = timeBlocks;
	}

	public List<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public Integer getNumberOfDays() {
		return numberOfDays;
	}
	
}
