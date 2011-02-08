package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TimeBlockService {

	public TimeBlock getTimeBlock(Long timeBlockId);
	public List<Map<String,Object>> getTimeBlocksForOurput(TimesheetDocument tsd);
	public void deleteTimeBlock(TimeBlock timeBlock);
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
											Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, Boolean isClockLogCreated);
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks);
	public void resetTimeHourDetail(List<TimeBlock> origTimeBlocks);
	public List<TimeBlock> getTimeBlocks(Long documentId);
	public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
												Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, Boolean isClockLogCreated);
	public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime,
										Assignment assignment, String earnCode, BigDecimal hours, Boolean isClockLogCreated);
}
