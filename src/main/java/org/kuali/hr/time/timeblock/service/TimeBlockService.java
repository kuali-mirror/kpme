/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.timeblock.service;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface TimeBlockService {
	/**
	 * Fetch a TimeBlock by a given ID
	 * @param timeBlockId
	 * @return
	 */
	public TimeBlock getTimeBlock(String timeBlockId);

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
     * @param isLunchDeleted
	 * @return
	 */
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
											Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, BigDecimal amount,
                                            Boolean isClockLogCreated, Boolean isLunchDeleted, String userPrincipalId);
	/**
	 * Save a list of new TimeBlocks
	 * does a comparison for the old versus the new and only saves changed/new/deleted TimeBlocks
	 * @param oldTimeBlocks
	 * @param newTimeBlocks
	 */
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks, String userPrincipalId);

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
	public List<TimeBlock> getTimeBlocks(String documentId);	
	/**
	 * Get the List of TimeBlock of a given Assignment
	 * @param assign
	 * @return List<TimeBlock>
	 */
	 public List<TimeBlock> getTimeBlocksForAssignment(Assignment assign);
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
     * @param isLunchDeleted
     * @param spanningWeeks
	 * @return
	 */
	public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
												Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, BigDecimal amount,
                                                Boolean isClockLogCreated, Boolean isLunchDeleted, String spanningWeeks, String userPrincipalId);
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
	 * @param isLunchDeleted
	 * @return
	 */
	public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime,
										Assignment assignment, String earnCode, BigDecimal hours, BigDecimal amount,
                                        Boolean isClockLogCreated, Boolean isLunchDeleted, String userPrincipalId);

	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);

	public Boolean isTimeBlockEditable(TimeBlock tb);
	
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogEndId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlock> getTimeBlocksForClockLogEndId(String tkClockLogId);
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogBeginId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(String tkClockLogId);
	
	public List<TimeBlock> getLatestEndTimestampForEarnCode(String earnCode);

    /**
     * Get overnight timeblocks by the clock log begin id
     * @param clockLogBeginId
     * @return
     */
    public List<TimeBlock> getOvernightTimeBlocks(String clockLogEndId);

	public void updateTimeBlock(TimeBlock tb);
	
	public List<TimeBlockHistory> createTimeBlockHistories(TimeBlock tb, String actionHistory);

    void deleteLunchDeduction(String tkTimeHourDetailId);
	/*
	 * Get all the active time blocks with the given earn code and effectiveDate
	 * @param earnCode
	 * @param effDate
	 * @return List<TimeBlock>	 * 
	 */
    public List<TimeBlock> getTimeBlocksWithEarnCode(String earnCode, Date effDate);
}
