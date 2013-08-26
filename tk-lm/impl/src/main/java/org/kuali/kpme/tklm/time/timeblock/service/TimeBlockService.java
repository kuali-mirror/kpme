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
package org.kuali.kpme.tklm.time.timeblock.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.block.CalendarBlockPermissions;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

public interface TimeBlockService {
	/**
	 * Fetch a TimeBlock by a given ID
	 * @param timeBlockId
	 * @return
	 */
	@Cacheable(value=TimeBlock.CACHE_NAME, key="'{getTimeBlock}' + 'timeBlockId=' + #p0")
	public TimeBlock getTimeBlock(String timeBlockId);

	/**
	 * Delete a given TimeBlock
	 * @param timeBlock
	 */
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true),
            @CacheEvict(value={CalendarBlockPermissions.CACHE_NAME}, key="#p0.tkTimeBlockId")
    })
	@CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
	public void deleteTimeBlock(TimeBlock timeBlock);
	/**
	 * Build a TimeBlock with the given criteria
	 * @param assignment
	 * @param earnCode
	 * @param timesheetDocument
	 * @param beginDateTime
	 * @param endDateTime
	 * @param hours
     * @param amount
	 * @param isClockLogCreated
     * @param isLunchDeleted
	 * @return
	 */
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
											DateTime beginDateTime, DateTime endDateTime, BigDecimal hours, BigDecimal amount,
                                            Boolean getClockLogCreated, Boolean getLunchDeleted, String userPrincipalId);
	/**
	 * Save a list of new TimeBlocks
	 * does a comparison for the old versus the new and only saves changed/new/deleted TimeBlocks
	 * @param oldTimeBlocks
	 * @param newTimeBlocks
	 */
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
    })
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks, String userPrincipalId);

	/**
	 * Save a list of new TimeBlocks
	 * @param tbList
	 */
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
    })
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
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocks}' + 'documentId=' + #p0")
	public List<TimeBlock> getTimeBlocks(String documentId);	
	/**
	 * Get the List of TimeBlock of a given Assignment
	 * @param assign
	 * @return List<TimeBlock>
	 */
	 @Cacheable(value= TimeBlock.CACHE_NAME, key="{getTimeBlocksForAssignment}' + 'assign=' + #p0.tkAssignmentId")
	 public List<TimeBlock> getTimeBlocksForAssignment(Assignment assign);
	/**
	 * Build a List of TimeBlocks over a span of multiple days
	 * @param assignment
	 * @param earnCode
	 * @param timesheetDocument
	 * @param beginDateTime
	 * @param endDateTime
	 * @param hours
     * @param amount
	 * @param isClockLogCreated
     * @param isLunchDeleted
     * @param spanningWeeks
	 * @return
	 */
	public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
												DateTime beginDateTime, DateTime endDateTime, BigDecimal hours, BigDecimal amount,
                                                Boolean getClockLogCreated, Boolean getLunchDeleted, String spanningWeeks, String userPrincipalId);
	/**
	 * Create a TimeBlock for the given criteria
	 * @param timesheetDocument
	 * @param beginDateTime
	 * @param endDateTime
	 * @param assignment
	 * @param earnCode
	 * @param hours
     * @param amount
	 * @param isClockLogCreated
	 * @param isLunchDeleted
	 * @return
	 */
	
	public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, DateTime beginDateTime, DateTime endDateTime,
										Assignment assignment, String earnCode, BigDecimal hours, BigDecimal amount,
                                        Boolean getClockLogCreated, Boolean getLunchDeleted, String userPrincipalId);

    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true),
            @CacheEvict(value={CalendarBlockPermissions.CACHE_NAME}, allEntries = true)
    })
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);

	public Boolean getTimeBlockEditable(TimeBlock tb);
	
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogEndId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocksForClockLogEndId}' + 'tkClockLogId=' + #p0")
	public List<TimeBlock> getTimeBlocksForClockLogEndId(String tkClockLogId);
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogBeginId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocksForClockLogBeginId}' + 'tkClockLogId=' + #p0")
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(String tkClockLogId);
	

	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getLatestEndTimestampForEarnCode}' + 'earnCode=' + #p0")
	public List<TimeBlock> getLatestEndTimestampForEarnCode(String earnCode);

    /**
     * Get overnight timeblocks by the clock log begin id
     * @param clockLogBeginId
     * @return
     */
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getOvernightTimeBlocks}' + 'clockLogEndId=' + #p0")
	public List<TimeBlock> getOvernightTimeBlocks(String clockLogEndId);

    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true),
            @CacheEvict(value={CalendarBlockPermissions.CACHE_NAME}, key="#p0.tkTimeBlockId")
    })
	public void updateTimeBlock(TimeBlock tb);
	
	public List<TimeBlockHistory> createTimeBlockHistories(TimeBlock tb, String actionHistory);

    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
    })
    void deleteLunchDeduction(String tkTimeHourDetailId);
	/*
	 * Get all the active time blocks with the given earn code and effectiveDate
	 * @param earnCode
	 * @param effDate
	 * @return List<TimeBlock>	 * 
	 */
    @Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocksWithEarnCode}' + 'earnCode=' + #p0 + '|' + 'effDate=' + #p1")
    public List<TimeBlock> getTimeBlocksWithEarnCode(String earnCode, DateTime effDate);
}
