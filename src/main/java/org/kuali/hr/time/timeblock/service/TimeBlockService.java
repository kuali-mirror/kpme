package org.kuali.hr.time.timeblock.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.clock.web.ClockActionForm;
import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;

public interface TimeBlockService {

	public void saveTimeBlock(TimeBlock timeBlock);
	public void saveTimeBlockList(List<TimeBlock> timeBlockList);
	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate);
	public void deleteTimeBlock(TimeBlock timeBlock);
	public TimeBlock getTimeBlock(String timeBlockId);
	public TimeBlock saveTimeBlock(ClockActionForm form);
	public List<Map<String,Object>> getTimeBlocksForOurput(TimeDetailActionForm form);
}
