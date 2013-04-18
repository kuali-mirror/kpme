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
package org.kuali.hr.lm.leaveadjustment.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;
import org.kuali.hr.lm.leaveadjustment.dao.LeaveAdjustmentDao;

public class LeaveAdjustmentServiceImpl implements LeaveAdjustmentService {
	
	private LeaveAdjustmentDao leaveAdjustmentDao;

	@Override
	public List<LeaveAdjustment> getLeaveAdjustments(String principalId, LocalDate asOfDate) {
		return leaveAdjustmentDao.getLeaveAdjustments(principalId, asOfDate);
	}

	@Override
	public LeaveAdjustment getLeaveAdjustment(String lmLeaveAdjustmentId) {
		return leaveAdjustmentDao.getLeaveAdjustment(lmLeaveAdjustmentId);
	}

	public LeaveAdjustmentDao getLeaveAdjustmentDao() {
		return leaveAdjustmentDao;
	}

	public void setLeaveAdjustmentDao(LeaveAdjustmentDao leaveAdjustmentDao) {
		this.leaveAdjustmentDao = leaveAdjustmentDao;
	}

	@Override
	public List<LeaveAdjustment> getLeaveAdjustments(LocalDate fromEffdt, LocalDate toEffdt, String principalId, String accrualCategory, String earnCode) {
		return leaveAdjustmentDao.getLeaveAdjustments(fromEffdt, toEffdt, principalId, accrualCategory, earnCode);
	}

}
