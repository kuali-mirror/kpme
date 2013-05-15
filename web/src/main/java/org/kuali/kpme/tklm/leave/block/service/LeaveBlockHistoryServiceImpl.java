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

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.block.dao.LeaveBlockHistoryDao;

public class LeaveBlockHistoryServiceImpl implements LeaveBlockHistoryService {

	private LeaveBlockHistoryDao leaveBlockHistoryDao;
	
	public void setLeaveBlockHistoryDao(LeaveBlockHistoryDao leaveBlockHistoryDao) {
		this.leaveBlockHistoryDao = leaveBlockHistoryDao;
	}

	@Override
	public void saveLeaveBlockHistory(LeaveBlockHistory leaveBlockHistory) {
		 leaveBlockHistoryDao.saveOrUpdate(leaveBlockHistory);
	}

	@Override
	public void saveLeaveBlockHistoryList(List<LeaveBlockHistory> leaveBlockHistoryList) {
		leaveBlockHistoryDao.saveOrUpdate(leaveBlockHistoryList);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoryByLmLeaveBlockId(
			String lmLeaveBlockId) {
		return leaveBlockHistoryDao.getLeaveBlockHistoryByLmLeaveBlockId(lmLeaveBlockId);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,
			List<String> requestStatus) {
		return leaveBlockHistoryDao.getLeaveBlockHistories(principalId, requestStatus);
	}

	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistoriesForLeaveDisplay(
			String principalId, LocalDate beginDate, LocalDate endDate, boolean considerModifiedUser) {
		return leaveBlockHistoryDao.getLeaveBlockHistoriesForLeaveDisplay(principalId, beginDate, endDate, considerModifiedUser);
	}
	
	@Override
	public List<LeaveBlockHistory> getLeaveBlockHistories(String principalId,String requestStatus, String action, LocalDate currentDate) {
		return leaveBlockHistoryDao.getLeaveBlockHistories(principalId, requestStatus, action, currentDate);
	}
	

}
