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
package org.kuali.kpme.tklm.leave.block.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface LeaveBlockService {
	@Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlock}' + 'leaveBlockId=' + #p0")
    public LeaveBlock getLeaveBlock(String leaveBlockId);
	
	@Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksForDocumentId}' + 'documentId=' + #p0")
	public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId);
    
	@Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocks}' + 'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2")
    public List<LeaveBlock> getLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksWithType}' + 'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'leaveBlockType=' + #p3")
    public List<LeaveBlock> getLeaveBlocksWithType(String principalId, LocalDate beginDate, LocalDate endDate, String leaveBlockType);
    
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksWithAccrualCategory}' + 'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'accrualCategory=' + #p3")
    public List<LeaveBlock> getLeaveBlocksWithAccrualCategory(String principalId, LocalDate beginDate, LocalDate endDate, String accrualCategory);
    
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksSinceCarryOver}' + 'principalId=' + #p0 + '|' + 'carryOverBlocks=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'includeAllAccrualCategories=' + #p3")
    public List<LeaveBlock> getLeaveBlocksSinceCarryOver(String principalId, Map<String, LeaveBlock> carryOverBlocks, LocalDate endDate, boolean includeAllAccrualCategories);
    
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLastCarryOverBlocks}' + 'principalId=' + #p0 + '|' + 'asOfDate=' + #p1")
    public Map<String, LeaveBlock> getLastCarryOverBlocks(String principalId, LocalDate asOfDate);
    
    @CacheEvict(value={LeaveBlock.CACHE_NAME}, allEntries = true)
    public void saveLeaveBlocks(List<LeaveBlock> leaveBlocks);

    @CacheEvict(value={LeaveBlock.CACHE_NAME}, allEntries = true)
    public void saveLeaveBlock(LeaveBlock leaveBlock, String principalId);

    /**
     * The deletion marks the leave block inactive instead of removing the row from the database.
     * @param leaveBlockId
     * @param principalId
     */
    @CacheEvict(value={LeaveBlock.CACHE_NAME}, allEntries = true)
    public void deleteLeaveBlock(String leaveBlockId, String principalId);


    @CacheEvict(value={LeaveBlock.CACHE_NAME}, allEntries = true)
    public void addLeaveBlocks(DateTime beginDate, DateTime endDate, CalendarEntry ce, String selectedEarnCode,
    		BigDecimal hours, String description, Assignment selectedAssignment, String spanningWeeks, String leaveBlockType, String principalId);
    
    @CacheEvict(value={LeaveBlock.CACHE_NAME}, allEntries = true)
    public void updateLeaveBlock(LeaveBlock leaveBlock, String principalId);
    /**
     * 
     * @param principalId
     * @param leaveBlockType
     * @param requestStatus
     * @param currentDate currentDate to get the records for the future date, pass null when not required
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocks}' + 'principalId=' + #p0 + '|' + 'leaveBlocktype=' + #p1 + '|' + 'requestStatus=' + #p2 + '|' + 'currentDate=' + #p3")
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate currentDate);

    /**
     *
     * @param principalId
     * @param leaveBlockType
     * @param requestStatus
     * @param beginDate
     * @param endDate
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocks}' + 'principalId=' + #p0 + '|' + 'leaveBlocktype=' + #p1 + '|' + 'requestStatus=' + #p2 + '|' + 'beginDate=' + #p3 + '|' + 'endDate=' + #p4")
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate beginDate, LocalDate endDate);

    /**
     * Get the list of leave blocks from the given leaveDate for the principalId
     * @param principalId
     * @param leaveDate
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksForDate}' + 'principalId=' + #p0 + '|' + 'leaveDate=' + #p1")
    public List<LeaveBlock> getLeaveBlocksForDate(String principalId, LocalDate leaveDate);
    /**
     * Get the list of not-accrual-generated leave blocks from the given leaveDate for the principalId
     * @param principalId
     * @param leaveDate
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getNotAccrualGeneratedLeaveBlocksForDate}' + 'principalId=' + #p0 + '|' + 'leaveDate=' + #p1")
    public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, LocalDate leaveDate);
    /**
     * Get list of leave blocks to display on time sheet with given dates and principal id
     * Only get leave blocks with type of leave calendar and time calendar
     * the leave blocks should have assignments in the list of assignment keys
     * @param principalId
     * @param beginDate
     * @param endDate
     * @param assignmentKeys
     * @return List of leave blocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksForTimeCalendar}' + 'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'assignmentKeys=' + #p3")
    public List<LeaveBlock> getLeaveBlocksForTimeCalendar(String principalId, LocalDate beginDate, LocalDate endDate, List<String> assignmentKeys); 
    /**
     * Get list of leave blocks to display on leave calendar with given dates and principal id
     * the leave blocks created from time calendar should have assignments in the list of assignment keys
     * @param principalId
     * @param beginDate
     * @param endDate
     * @param assignmentKeys
     * @return List of leave blocks
     */ 
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getLeaveBlocksForLeaveCalendar}' + 'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'assignmentKeys=' + #p3")
    public List<LeaveBlock> getLeaveBlocksForLeaveCalendar(String principalId, LocalDate beginDate, LocalDate endDate, List<String> assignmentKeys); 
   
    /**
     * Filter list of leave blocks with given list of assignmentKeys for Time Calendar
     * @param lbs
     * @param assignmentKeys
     * @return List of leave blocks
     */
    public List<LeaveBlock> filterLeaveBlocksForTimeCalendar(List<LeaveBlock> lbs, List<String> assignmentKeys);
    /**
     * Filter list of leave blocks with given list of assignmentKeys for Leave Calendar
     * @param lbs
     * @param assignmentKeys
     * @return List of leave blocks
     */
    public List<LeaveBlock> filterLeaveBlocksForLeaveCalendar(List<LeaveBlock> lbs, List<String> assignmentKeys);
    /**
     *  Delete time blocks for a given document id
     *  @param documentId
     */
    public void deleteLeaveBlocksForDocumentId(String documentId);
    
    /**
     * Retrieve list of accrual generated leave blocks for given Date range and User
     * @param principalId 
     * @param beginDate
     * @param endDate
     * @return List of leave blocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getAccrualGeneratedLeaveBlocks}' + 'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2")
    public List<LeaveBlock> getAccrualGeneratedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    
    /**
     * Retrieve list of leave blocks generated with given system scheduled time off id, date and user
     * @param principalId
     * @param sstoId
     * @param accruledDate
     * @return
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getSSTOLeaveBlocks}' + 'principalId=' + #p0 + '|' + 'sstoId=' + #p1 + '|' + 'accruleDate=' + #p2")
    public List<LeaveBlock> getSSTOLeaveBlocks(String principalId, String sstoId, LocalDate accruledDate);
    
    /**
     * gets list of leave blocks created for earn codes with eligible-for-accrual=no since the given timestamp
     * @param principalId
     * @param lastRanTime
     * @return
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'{getABELeaveBlocksSinceTime}' + 'principalId=' + #p0 + '|' + 'lastRanTime=' + #p1")
    public List<LeaveBlock> getABELeaveBlocksSinceTime(String principalId, DateTime lastRanDateTime);
}
