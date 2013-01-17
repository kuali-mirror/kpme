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
package org.kuali.hr.lm.leavepayout.service;

import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.hr.lm.leavepayout.dao.LeavePayoutDao;

import java.sql.Date;
import java.util.List;

public class LeavePayoutServiceImpl implements LeavePayoutService {

    private LeavePayoutDao leavePayoutDao;

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(
            String principalId) {
        // TODO Auto-generated method stub
        return leavePayoutDao.getAllLeavePayoutsForPrincipalId(principalId);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(
            String principalId, Date effectiveDate) {
        return leavePayoutDao.getAllLeavePayoutsForPrincipalIdAsOfDate(principalId,effectiveDate);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(
            Date effectiveDate) {
        // TODO Auto-generated method stub
        return leavePayoutDao.getAllLeavePayoutsByEffectiveDate(effectiveDate);
    }

    @Override
    public LeavePayout getLeavePayoutById(String lmLeavePayoutId) {
        // TODO Auto-generated method stub
        return leavePayoutDao.getLeavePayoutById(lmLeavePayoutId);
    }

    public LeavePayoutDao getLeavePayoutDao() {
        return leavePayoutDao;
    }

    public void setLeavePayoutDao(LeavePayoutDao leavePayoutDao) {
        this.leavePayoutDao = leavePayoutDao;
    }

}
