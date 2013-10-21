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

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;

public interface LeaveBlockHistoryDao {
	
	public void saveOrUpdate(LeaveBlockHistory leaveBlockHistory);
	public void saveOrUpdate(List<LeaveBlockHistory> leaveBlockHistoryList);
	public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(String lmLeaveBlockId);
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId, List<String> requestStatus);
	
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(String principalId, LocalDate beginDate, LocalDate endDate, boolean considerModifiedUser);
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,String requestStatus, String action, LocalDate currentDate);
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate);
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate, String leaveBlockType);

}
