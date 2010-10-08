package org.kuali.hr.time.timeblock.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public interface TimeBlockService {

	public List<TimeBlock> saveTimeBlockList(TimeDetailActionForm form);
	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate);
	public void deleteTimeBlock(TimeDetailActionForm tdaf);
	public TimeBlock getTimeBlock(String timeBlockId);
	public TimeBlock saveTimeBlock(TimesheetActionForm form);
	public List<Map<String,Object>> getTimeBlocksForOurput(TimeDetailActionForm form);
}
