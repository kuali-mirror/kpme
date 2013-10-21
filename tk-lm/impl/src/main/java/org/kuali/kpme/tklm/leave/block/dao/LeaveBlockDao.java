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
package org.kuali.kpme.tklm.leave.block.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public interface LeaveBlockDao {
    public LeaveBlock getLeaveBlock(String leaveBlockId);
    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlock> getLeaveBlocksWithType(String principalId, LocalDate beginDate, LocalDate endDate, String leaveBlockType);
    public List<LeaveBlock> getLeaveBlocksWithAccrualCategory(String principalId, LocalDate beginDate, LocalDate endDate, String accrualCategory);
    public List<LeaveBlock> getLeaveBlocksSinceCarryOver(String principalId, Map<String, LeaveBlock> carryOverDates, LocalDate endDate, boolean includeAllAccrualCategories);
    public Map<String, LeaveBlock> getLastCarryOverBlocks(String principalId, String leaveBlockType, LocalDate asOfDate);

    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate currentDate);
    public List<LeaveBlock> getLeaveBlocksForDate(String principalId, LocalDate leaveDate);
    public List<LeaveBlock> getLeaveBlocks(LocalDate leaveDate, String accrualCategory, String principalId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, String accrualCategory, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlock> getFMLALeaveBlocks(String principalId, String accrualCategory, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, LocalDate leaveDate);
    /**
     * Get the leave blocks created from time or leave calendars for given pricipalId and calendar period
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<LeaveBlock> getCalendarLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    public void deleteLeaveBlock(String leaveBlockId);
    public void deleteLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlock> getAccrualGeneratedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlock> getSSTOLeaveBlocks(String principalId, String sstoId, LocalDate accruledDate);
    public List<LeaveBlock> getABELeaveBlocksSinceTime(String principalId, DateTime lastRanDateTime);

    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate beginDate, LocalDate endDate);
	public List<LeaveBlock> getTimeCalendarLeaveBlocksForTimeBlockLookup(
			String documentId, String principalId, String userPrincipalId,
			LocalDate fromDate, LocalDate toDate);
	public List<LeaveBlock> getLeaveBlocksForLookup(
			String documentId, String principalId, String userPrincipalId,
			LocalDate fromDate, LocalDate toDate, String leaveBlockType);
	
}

