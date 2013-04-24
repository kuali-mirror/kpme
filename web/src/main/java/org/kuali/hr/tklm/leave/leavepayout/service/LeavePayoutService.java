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
package org.kuali.hr.tklm.leave.leavepayout.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.tklm.leave.leavepayout.LeavePayout;
import org.kuali.rice.kew.api.exception.WorkflowException;

public interface LeavePayoutService {

    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(String principalId);
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(String principalId, LocalDate effectiveDate);
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(LocalDate effectiveDate);

    //@Cacheable(value= LeaveDonation.CACHE_NAME, key="'lmLeavePayoutId=' + #p0")
    public LeavePayout getLeavePayoutById(String lmLeavePayoutId);

	//Use-Case specific service providers. Could be combined if max carry over applies to all use cases.
	/**
	 * A service that instantiates and returns LeavePayout objects that follow the given accrual category rule.
	 * 
	 * @param principalId	The principal this transfer pertains to.
	 * @param accrualCategoryRule	The accrual category rule that contains the max balance information.
	 * @param leaveSummary	Holds balance information needed for transfer.
	 * @param effectiveDate 
	 * @return A LeavePayout object conforming to @param accrualCategoryRule, if one exists. Null otherwise.
	 * 
	 * The transfer amount will be the minimum of:
	 *  
	 *  	1.) the accrual category rule's maximum transfer amount, adjusted for the employees FTE.
	 *  	2.) the number of time units exceeding the maximum balance 
	 *
	 */
	public LeavePayout initializePayout(String principalId, String accrualCategoryRule, BigDecimal accruedBalance, LocalDate effectiveDate);

	/**
	 * Consumes a LeavePayout object, creating up to three leave blocks.
	 * @param LeavePayout The LeavePayout object to use for transfer.
	 * @return The same LeavePayout object, but with associated leave block ids.
	 */
	public LeavePayout payout(LeavePayout leavePayout);
	
	/**
	 * Helper Services
	 */
	
	public void submitToWorkflow(LeavePayout leavePayout) throws WorkflowException;
	
	public List<LeavePayout> getLeavePayouts(String viewPrincipal, LocalDate beginPeriodDate, LocalDate endPeriodDate);
	
	public void saveOrUpdate(LeavePayout payout);
	public List<LeavePayout> getLeavePayouts(String principalId, String fromAccrualCategory, String payoutAmount, String earnCode, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt);

    
}
