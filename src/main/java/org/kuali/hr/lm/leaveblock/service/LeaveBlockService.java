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
package org.kuali.hr.lm.leaveblock.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.springframework.cache.annotation.Cacheable;

public interface LeaveBlockService {
    public LeaveBlock getLeaveBlock(String leaveBlockId);
    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, Date beginDate, Date endDate);
    public List<LeaveBlock> getLeaveBlocksWithType(String principalId, Date beginDate, Date endDate, String leaveBlockType);
    public List<LeaveBlock> getLeaveBlocksWithAccrualCategory(String principalId, Date beginDate, Date endDate, String accrualCategory);
    public List<LeaveBlock> getLeaveBlocksSinceCarryOver(String principalId, Map<String, LeaveBlock> carryOverBlocks, DateTime endDate, boolean includeAllAccrualCategories);
    public Map<String, LeaveBlock> getLastCarryOverBlocks(String principalId, Date asOfDate);
    public void saveLeaveBlocks(List<LeaveBlock> leaveBlocks);

    public void saveLeaveBlock(LeaveBlock leaveBlock, String principalId);

    /**
     * The deletion marks the leave block inactive instead of removing the row from the database.
     * @param leaveBlockId
     * @param principalId
     */
    public void deleteLeaveBlock(String leaveBlockId, String principalId);

    public void addLeaveBlocks(DateTime beginDate, DateTime endDate, CalendarEntry ce, String selectedEarnCode,
    		BigDecimal hours, String description, Assignment selectedAssignment, String spanningWeeks, String leaveBlockType, String principalId);
    
    public void updateLeaveBlock(LeaveBlock leaveBlock, String principalId);
    /**
     * 
     * @param principalId
     * @param leaveBlockType
     * @param requestStatus
     * @param currentDate currentDate to get the records for the future date, pass null when not required
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'leaveBlocktype=' + #p1 + '|' + 'requestStatus=' + #p2 + '|' + 'currentDate=' + #p3")
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, Date currentDate);

    /**
     *
     * @param principalId
     * @param leaveBlockType
     * @param requestStatus
     * @param beginDate
     * @param endDate
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'leaveBlocktype=' + #p1 + '|' + 'requestStatus=' + #p2 + '|' + 'endDate=' + #p3")
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, Date beginDate, Date endDate);

    /**
     * Get the list of leave blocks from the given leaveDate for the principalId
     * @param principalId
     * @param leaveDate
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'leaveDate=' + #p1")
    public List<LeaveBlock> getLeaveBlocksForDate(String principalId, Date leaveDate);
    /**
     * Get the list of not-accrual-generated leave blocks from the given leaveDate for the principalId
     * @param principalId
     * @param leaveDate
     * @return List of LeaveBlocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'leaveDate=' + #p1")
    public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, Date leaveDate);
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
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'assignmentKeys=' + #p3")
    public List<LeaveBlock> getLeaveBlocksForTimeCalendar(String principalId, Date beginDate, Date endDate, List<String> assignmentKeys);
    /**
     * Get list of leave blocks to display on leave calendar with given dates and principal id
     * the leave blocks created from time calendar should have assignments in the list of assignment keys
     * @param principalId
     * @param beginDate
     * @param endDate
     * @param assignmentKeys
     * @return List of leave blocks
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2 + '|' + 'assignmentKeys=' + #p3")
    public List<LeaveBlock> getLeaveBlocksForLeaveCalendar(String principalId, Date beginDate, Date endDate, List<String> assignmentKeys);
   
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
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'beginDate=' + #p1 + '|' + 'endDate=' + #p2")
    public List<LeaveBlock> getAccrualGeneratedLeaveBlocks(String principalId, Date beginDate, Date endDate);
    
    /**
     * Retrieve list of leave blocks generated with given system scheduled time off id, date and user
     * @param principalId
     * @param sstoId
     * @param accruledDate
     * @return
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'sstoId=' + #p1 + '|' + 'accruleDate=' + #p2")
    public List<LeaveBlock> getSSTOLeaveBlocks(String principalId, String sstoId, Date accruledDate);
    
    /**
     * gets list of leave blocks created for earn codes with eligible-for-accrual=no since the given timestamp
     * @param principalId
     * @param lastRanTime
     * @return
     */
    @Cacheable(value= LeaveBlock.CACHE_NAME, key="'principalId=' + #p0 + '|' + 'lastRanTime=' + #p1")
    public List<LeaveBlock> getABELeaveBlocksSinceTime(String principalId, Timestamp lastRanTime);
}
