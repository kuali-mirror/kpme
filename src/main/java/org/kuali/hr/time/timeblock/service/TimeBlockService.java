package org.kuali.hr.time.timeblock.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public interface TimeBlockService {

	public List<TimeBlock> saveTimeBlockList(TimeDetailActionForm form);
	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate);
	public TimeBlock getTimeBlock(String timeBlockId);
	public List<Map<String,Object>> getTimeBlocksForOurput(TimeDetailActionForm form);
	public TimeBlock deleteTimeBlock(TimeDetailActionForm tdaf);
	public void deleteTimeBlock(TimeBlock timeBlock);
	public void saveTimeBlock(TimesheetActionForm form);
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument, 
											Timestamp beginTimestamp, Timestamp endTimestamp);
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks);
	public List<TimeBlock> resetTimeHourDetail(List<TimeBlock> origTimeBlocks);
}
