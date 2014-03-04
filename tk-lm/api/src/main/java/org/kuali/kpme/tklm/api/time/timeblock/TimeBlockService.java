/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.api.time.timeblock;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.math.BigDecimal;
import java.util.List;

//@WebService(name = "groupService", targetNamespace = TkConstants.Namespace.TKLM_NAMESPACE_2_1)
//@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TimeBlockService {
	/**
	 * Fetch a TimeBlock by a given ID
	 * @param timeBlockId
	 * @return
	 */
    //@WebMethod(operationName = "getTimeBlock")
    //@WebResult(name = "timeBlock")
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlock}' + 'timeBlockId=' + #p0")
	public TimeBlock getTimeBlock(String timeBlockId);

	/**
	 * Delete a given TimeBlock
	 * @param timeBlock
	 */
    //@WebMethod(operationName = "deleteTimeBlock")
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true),
            @CacheEvict(value={CalendarBlockPermissions.CACHE_NAME}, key="'{time}' + #p0.tkTimeBlockId")
    })
	public void deleteTimeBlock(TimeBlock timeBlock);
	/**
	 * Build a TimeBlock with the given criteria
     * @param principalId
     * @param calendarEntry
	 * @param assignment
	 * @param earnCode
	 * @param documentId
	 * @param beginDateTime
	 * @param endDateTime
	 * @param hours
     * @param amount
	 * @param getClockLogCreated
     * @param getLunchDeleted
     * @param clockLogBeginId
     * @param clockLogEndId
	 * @return
	 */
    //@WebMethod(operationName = "buildTimeBlocks")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    @WebResult(name = "timeBlocks")
	public List<TimeBlock> buildTimeBlocks(String principalId, CalendarEntryContract calendarEntry, AssignmentContract assignment, String earnCode, String documentId,
											DateTime beginDateTime, DateTime endDateTime, BigDecimal hours, BigDecimal amount,
                                            Boolean getClockLogCreated, Boolean getLunchDeleted, String userPrincipalId,
                                            String clockLogBeginId, String clockLogEndId);
	/**
	 * Save a list of new TimeBlocks
	 * does a comparison for the old versus the new and only saves changed/new/deleted TimeBlocks
	 * @param oldTimeBlocks
	 * @param newTimeBlocks
	 */
    //@WebMethod(operationName = "saveOrUpdateTimeBlocks")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
    })
	public List<TimeBlock> saveOrUpdateTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks, String userPrincipalId);

	/**
	 * Save a list of new TimeBlocks
	 * @param tbList
	 */
    @WebMethod(operationName = "saveTimeBlocks")
    @XmlElementWrapper(name = "timeBlocks", required = true)
    @XmlElement(name = "timeBlock", required = false)
    @WebResult(name = "timeBlocks")
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
    })
	public List<TimeBlock> saveTimeBlocks(List<TimeBlock> tbList);

    /**
	 * Reset the TimeHourDetail object associated with the TimeBlock object on a List of TimeBlocks
	 * @param origTimeBlocks
	 */
    //@WebMethod(operationName = "resetTimeHourDetail")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	public List<TimeBlock> resetTimeHourDetail(List<TimeBlock> origTimeBlocks);

    /**
	 * Get the List of TimeBlock of a given document id
	 * @param documentId
	 * @return
	 */
    //@WebMethod(operationName = "getTimeBlocks")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocks}' + 'documentId=' + #p0")
	public List<TimeBlock> getTimeBlocks(String documentId);	

    /**
	 * Build a List of TimeBlocks over a span of multiple days
     * @param principalId
     * @param calendarEntry
	 * @param assignment
	 * @param earnCode
	 * @param documentId
	 * @param beginDateTime
	 * @param endDateTime
	 * @param hours
     * @param amount
	 * @param clockLogCreated
     * @param lunchDeleted
     * @param spanningWeeks
	 * @return
	 */
    //@WebMethod(operationName = "buildTimeBlocksSpanDates")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	public List<TimeBlock> buildTimeBlocksSpanDates(String principalId, CalendarEntryContract calendarEntry, AssignmentContract assignment, String earnCode,
                                                    String documentId, DateTime beginDateTime, DateTime endDateTime, BigDecimal hours, BigDecimal amount,
                                                    Boolean clockLogCreated, Boolean lunchDeleted, String spanningWeeks, String userPrincipalId,
                                                    String clockLogBeginId, String clockLogEndId);
	/**
	 * Create a TimeBlock for the given criteria
	 * @param principalId
     * @param documentId
	 * @param beginDateTime
	 * @param endDateTime
	 * @param assignment
	 * @param earnCode
	 * @param hours
     * @param amount
	 * @param clockLogCreated
	 * @param lunchDeleted
	 * @return
	 */
    //@WebMethod(operationName = "createTimeBlock")
    //@WebResult(name = "timeBlock")
	public TimeBlock createTimeBlock(String principalId, String documentId, DateTime beginDateTime, DateTime endDateTime,
										AssignmentContract assignment, String earnCode, BigDecimal hours, BigDecimal amount,
                                        Boolean clockLogCreated, Boolean lunchDeleted, String userPrincipalId);

    //@WebMethod(operationName = "deleteTimeBlocksAssociatedWithDocumentId")
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true),
            @CacheEvict(value={CalendarBlockPermissions.CACHE_NAME}, allEntries = true)
    })
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId);

    //@WebMethod(operationName = "getTimeBlockEditable")
    //@WebResult(name = "editable")
	public Boolean getTimeBlockEditable(TimeBlock tb);
	
	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogEndId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
    //@WebMethod(operationName = "getTimeBlocksForClockLogEndId")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocksForClockLogEndId}' + 'tkClockLogId=' + #p0")
	public List<TimeBlock> getTimeBlocksForClockLogEndId(String tkClockLogId);

	/*
	 * Get all the time blocks with the given Clock Log id as the clockLogBeginId
	 * @param tkClockLogId
	 * @return List<TimeBlock>	 * 
	 */
    //@WebMethod(operationName = "getTimeBlocksForClockLogBeginId")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getTimeBlocksForClockLogBeginId}' + 'tkClockLogId=' + #p0")
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(String tkClockLogId);

    //@WebMethod(operationName = "getLatestEndTimestampForEarnCode")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getLatestEndTimestampForEarnCode}' + 'earnCode=' + #p0")
	public List<TimeBlock> getLatestEndTimestampForEarnCode(String earnCode);

    /**
     * Get overnight timeblocks by the clock log begin id
     * @param clockLogEndId
     * @return
     */
    //@WebMethod(operationName = "getOvernightTimeBlocks")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	@Cacheable(value= TimeBlock.CACHE_NAME, key="'{getOvernightTimeBlocks}' + 'clockLogEndId=' + #p0")
	public List<TimeBlock> getOvernightTimeBlocks(String clockLogEndId);

    //@WebMethod(operationName = "isOvernightTimeBlock")
    //@WebResult(name = "overnightBlock")
    @Cacheable(value= TimeBlock.CACHE_NAME, key="'{isOvernightTimeBlock}' + 'clockLogEndId=' + #p0")
    public boolean isOvernightTimeBlock(String clockLogEndId);

    //@WebMethod(operationName = "updateTimeBlock")
    //@WebResult(name = "timeBlock")
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true),
            @CacheEvict(value={CalendarBlockPermissions.CACHE_NAME}, key="'{time}' + #p0.tkTimeBlockId")
    })
	public TimeBlock updateTimeBlock(TimeBlock tb);
	
    //@WebMethod(operationName = "deleteLunchDeduction")
    @Caching(evict = {
            @CacheEvict(value={TimeBlock.CACHE_NAME}, allEntries = true)
    })
    void deleteLunchDeduction(String tkTimeHourDetailId);

    //@WebMethod(operationName = "applyHolidayPremiumEarnCode")
    //@XmlElementWrapper(name = "timeBlocks", required = true)
    //@XmlElement(name = "timeBlock", required = false)
    //@WebResult(name = "timeBlocks")
	public List<TimeBlock> applyHolidayPremiumEarnCode(String principalId, List<AssignmentContract> timeAssignments, List<TimeBlock> appliedTimeBlocks);
}
