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
package org.kuali.kpme.tklm.leave.block.dao;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;

public interface LeaveBlockDao {
    public LeaveBlockBo getLeaveBlock(String leaveBlockId);
    public List<LeaveBlockBo> getLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlockBo> getLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlockBo> getLeaveBlocksWithType(String principalId, LocalDate beginDate, LocalDate endDate, String leaveBlockType);
    public List<LeaveBlockBo> getLeaveBlocksWithAccrualCategory(String principalId, LocalDate beginDate, LocalDate endDate, String accrualCategory);
    public List<LeaveBlockBo> getLeaveBlocksSinceCarryOver(String principalId, Map<String, LeaveBlock> carryOverDates, LocalDate endDate, boolean includeAllAccrualCategories);
    public Map<String, LeaveBlockBo> getLastCarryOverBlocks(String principalId, String leaveBlockType, LocalDate asOfDate);

    public List<LeaveBlockBo> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate currentDate);
    public List<LeaveBlockBo> getLeaveBlocksForDate(String principalId, LocalDate leaveDate);
    public List<LeaveBlockBo> getLeaveBlocks(LocalDate leaveDate, String accrualCategory, String principalId);
    public List<LeaveBlockBo> getLeaveBlocks(String principalId, String accrualCategory, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlockBo> getFMLALeaveBlocks(String principalId, String accrualCategory, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlockBo> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, LocalDate leaveDate);
    /**
     * Get the leave blocks created from time or leave calendars for given pricipalId and calendar period
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<LeaveBlockBo> getCalendarLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    public void deleteLeaveBlock(String leaveBlockId);
    public void deleteLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlockBo> getAccrualGeneratedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate);
    public List<LeaveBlockBo> getSSTOLeaveBlocks(String principalId, String sstoId, LocalDate accruledDate);
    public List<LeaveBlockBo> getABELeaveBlocksSinceTime(String principalId, DateTime lastRanDateTime);

    public List<LeaveBlockBo> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate beginDate, LocalDate endDate);
	public List<LeaveBlockBo> getTimeCalendarLeaveBlocksForTimeBlockLookup(
			String documentId, String principalId, String userPrincipalId,
			LocalDate fromDate, LocalDate toDate);
	public List<LeaveBlockBo> getLeaveBlocksForLookup(
			String documentId, String principalId, String userPrincipalId,
			LocalDate fromDate, LocalDate toDate, String leaveBlockType);
	
}

