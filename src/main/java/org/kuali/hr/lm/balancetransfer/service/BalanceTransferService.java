/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.balancetransfer.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;

public interface BalanceTransferService {

	//Data access
	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(String principalId);
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(String principalId, Date effectiveDate);
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(Date effectiveDate);

	//@Cacheable(value= LeaveDonation.CACHE_NAME, key="'balanceTransferId=' + #p0")
	public BalanceTransfer getBalanceTransferById(String balanceTransferId);
	
	//Use-Case specific service providers. Could be combined if max carry over applies to all use cases.
	/**
	 * For balance transfers triggered by accrual categories that have exceeded a max balance, and
	 * whose max balance action frequency is Leave Approve.
	 * @param principalId
	 * @param accrualCategoryRule
	 * @param leaveSummary
	 * @return A BalanceTransfer object pre-populated according to the supplied parameters if one exists.
	 * 
	 * The transfer amount will be the minimum of:
	 *  
	 *  	1.) the accrual category rule's maximum transfer amount
	 *  	2.) the number of units exceeding the maximum balance
	 *
	 */
	public BalanceTransfer getTransferOnLeaveApprove(String principalId, String accrualCategoryRule, LeaveSummary leaveSummary);
	
	/**
	 * For balance transfers triggered by accrual categories that have exceeded their max balance, and
	 * whose max balance action frequency is Year-End.
	 * @param principalId
	 * @param transferAmount
	 * @param fromAcc
	 * @param toAcc
	 * @return A BalanceTransfer object pre-populated according to the supplied parameters if one exists.
	 * 
	 * The transfer amount will be the minimum of:
	 *  
	 *  	1.) the accrual category rule's maximum transfer amount
	 *  	2.) the number of units exceeding the maximum balance
	 *
	 */
	public BalanceTransfer initiateBalanceTransferOnYearEnd(String principalId, BigDecimal transferAmount, AccrualCategory fromAcc, AccrualCategory toAcc);
	
	/**
	 * For balance transfers triggered by accrual categories that have exceeded their max balance, and
	 * whose max balance action frequency is On-Demand.
	 * @param principalId	The principal who initiated the transfer.
	 * @param leaveSummaryRow	The LeaveSummaryRow the user clicked to initiate the transfer
	 * @param accrualCategoryRule	The accrualCategoryRule governing the transfering accrual category
	 * @return A BalanceTransfer object pre-populated according to the supplied parameters if one exists.
	 * 
	 * The transfer amount will be the minimum of:
	 *  
	 *  	1.) the accrual category rule's maximum transfer amount
	 *  	2.) the number of units exceeding the maximum balance
	 *
	 */
	public BalanceTransfer initiateBalanceTransferOnDemand(String principalId, LeaveSummaryRow leaveSummaryRow, String accrualCategoryRule);
	
	/**
	 * Consumes a BalanceTransfer object, creating up to three leave blocks.
	 * @param balanceTransfer The BalanceTransfer object to use for transfer.
	 */
	public void transfer(BalanceTransfer balanceTransfer);
	
	/**
	 * Helper Services
	 */
	
	/**
	 * 
	 * @param document The LeaveCalendarDocument to use in gathering transfer eligible accrual categories.
	 * @param actionFrequency One of LMConstants.MAX_BAL_ACTION_FREQ
	 * @return A List of accrualCategoryRuleId's for which the associated accrual categories in document.LeaveSummary are eligible for transfer.
	 * @throws Exception
	 */
	public List<String> getAccrualCategoryRuleIdsForEligibleTransfers(LeaveCalendarDocument document, String actionFrequency) throws Exception;
	
	/**
	 * 
	 * @param document The LeaveCalendarDocument to use in determining balance transfer eligibility
	 * @return True if balance transfer qualifications have been met for any accrual category within the Leave calendar, false otherwise.
	 */
	//Unimplemented. Would require an equal number of service calls as "getEligibleTransferForLeaveApprove"
	//public boolean balanceTransferQualificationMet(LeaveCalendarDocument document);
	
}
