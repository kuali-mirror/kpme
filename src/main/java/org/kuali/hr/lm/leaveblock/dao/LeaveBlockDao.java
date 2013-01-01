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
package org.kuali.hr.lm.leaveblock.dao;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;

public interface LeaveBlockDao {
    public LeaveBlock getLeaveBlock(String leaveBlockId);
    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, Date beginDate, Date endDate);
    public void saveOrUpdate(LeaveBlock leaveBlock);
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, Date currentDate);
    public List<LeaveBlock> getLeaveBlocksForDate(String principalId, Date leaveDate);
    public List<LeaveBlock> getLeaveBlocks(Date leaveDate, String accrualCategory, String principalId);
    public List<LeaveBlock> getLeaveBlocks(String principalId, String accrualCategory, Date beginDate, Date endDate);
    public List<LeaveBlock> getFMLALeaveBlocks(String principalId, String accrualCategory, Date beginDate, Date endDate);
    public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, Date leaveDate);
    /**
     * Get the leave blocks created from time or leave calendars for given pricipalId and calendar period
     * @param principalId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<LeaveBlock> getCalendarLeaveBlocks(String principalId, Date beginDate, Date endDate);
    public void deleteLeaveBlock(String leaveBlockId);
    public void deleteLeaveBlocksForDocumentId(String documentId);
}

