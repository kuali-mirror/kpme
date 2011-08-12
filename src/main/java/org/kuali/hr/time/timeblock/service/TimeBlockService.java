package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TimeBlockService {
	/**
	 * Fetch a TimeBlock by a given ID
	 * @param timeBlockId
	 * @return
	 */
	public TimeBlock getTimeBlock(Long timeBlockId);

	/**
	 * Delete a given TimeBlock
	 * @param timeBlock
	 */
	public void deleteTimeBlock(TimeBlock timeBlock);
	/**
	 * Build a TimeBlock with the given criteria
	 * @param assignment
	 * @param earnCode
	 * @param timesheetDocument
	 * @param beginTimestamp
	 * @param endTimestamp
	 * @param hours
     * @param amount
	 * @param isClockLogCreated
	 * @return
	 */
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
											Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, BigDecimal amount, Boolean isClockLogCreated);
	/**
	 * Save a list of new TimeBlocks
	 * does a comparison for the old versus the new and only saves changed/new/deleted TimeBlocks
	 * @param oldTimeBlocks
	 * @param newTimeBlocks
	 */
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks);

	/**
	 * Save a list of new TimeBlocks
	 * @param tbList
	 */
	public void saveTimeBlocks(List<TimeBlock> tbList);
	/**
	 * Reset the TimeHourDetail object associated with the TimeBlock object on a List of TimeBlocks
	 * @param origTimeBlocks
	 */
	public void resetTimeHourDetail(List<TimeBlock> origTimeBlocks);
	/**
	 * Get the List of TimeBlock of a given document id
	 * @param documentId
	 * @return
	 */
	public List<TimeBlock> getTimeBlocks(Long documentId);
	/**
	 * Build a List of TimeBlocks over a span of multiple days
	 * @param assignment
	 * @param earnCode
	 * @param timesheetDocument
	 * @param beginTimestamp
	 * @param endTimestamp
	 * @param hours
     * @param amount
	 * @param isClockLogCreated
	 * @return
	 */
	public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
												Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, BigDecimal amount, Boolean isClockLogCreated);
	/**
	 * Create a TimeBlock for the given criteria
	 * @param timesheetDocument
	 * @param beginTime
	 * @param endTime
	 * @param assignment
	 * @param earnCode
	 * @param hours
     * @param amount
	 * @param isClockLogCreated
	 * @return
	 */
	public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime,
										Assignment assignment, String earnCode, BigDecimal hours, BigDecimal amount, Boolean isClockLogCreated);

	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);

	public Boolean isTimeBlockEditable(TimeBlock tb);
}
