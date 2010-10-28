package org.kuali.hr.time.timeblock.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public interface TimeBlockService {

	public TimeBlock getTimeBlock(String timeBlockId);
	public List<Map<String,Object>> getTimeBlocksForOurput(TimesheetDocument tsd);
	public void deleteTimeBlock(TimeBlock timeBlock);
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument, 
											Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours);
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks);
	public List<TimeBlock> resetTimeHourDetail(List<TimeBlock> origTimeBlocks);
	public List<TimeBlock> getTimeBlocks(Long documentId);
	public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument, 
												Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours);
	public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime, 
										Assignment assignment, String earnCode, BigDecimal hours);
}
