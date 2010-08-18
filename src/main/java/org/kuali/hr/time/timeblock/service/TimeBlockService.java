package org.kuali.hr.time.timeblock.service;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimeBlockService {

	public void saveTimeBlock(TimeBlock timeBlock);
	public void saveTimeBlockList(List<TimeBlock> timeBlockList);
	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, String beginDate, String endDate);
	public void deleteTimeBlock(TimeBlock timeBlock);
	public TimeBlock getTimeBlock(String timeBlockId);
}
