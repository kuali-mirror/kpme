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
package org.kuali.kpme.tklm.leave.payout.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;

public interface LeavePayoutDao {

    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(String principalId);
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(String principalId, LocalDate effectiveDate);
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(LocalDate effectiveDate);
    public List<LeavePayout> getAllLeavePayoutsMarkedPayoutForPrincipalId(String principalId);
    public LeavePayout getLeavePayoutById(String lmLeavePayoutId);
	public List<LeavePayout> getLeavePayouts(String viewPrincipal,
			LocalDate beginPeriodDate, LocalDate endPeriodDate);
	public void saveOrUpdate(LeavePayout payout);
	public List<LeavePayout> getLeavePayouts(String principalId, String fromAccrualCategory, String payoutAmount, String earnCode, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt);

}
