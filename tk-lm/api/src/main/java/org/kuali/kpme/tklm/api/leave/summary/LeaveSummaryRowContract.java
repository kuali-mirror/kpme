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
package org.kuali.kpme.tklm.api.leave.summary;

import java.math.BigDecimal;
import java.util.SortedMap;



/**
 * <p>LeaveSummaryRowContract interface</p>
 *
 */
public interface LeaveSummaryRowContract {
	
	/**
	 * The AccrualCategory name associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * accrualCategory of a LeaveSummaryRow
	 * <p>
	 * 
	 * @return accrualCategory for LeaveSummaryRow
	 */
	public String getAccrualCategory();
	
	/**
	 * The AccrualCategory id associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * accrualCategoryId of a LeaveSummaryRow - used as a very simple means of getting the AccrualCategory object
	 * <p>
	 * 
	 * @return accrualCategoryId for LeaveSummaryRow
	 */
    public String getAccrualCategoryId();
   
    /**
	 * The AccrualCategoryRule id associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * accrualCategoryRuleId of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return accrualCategoryRuleId for LeaveSummaryRow
	 */
    public String getAccrualCategoryRuleId();

    /**
     * TODO: Make sure this comment is right
   	 * The amount user can carry over to next year associated with the LeaveSummaryRow
   	 * 
   	 * <p>
   	 * carryOver of a LeaveSummaryRow 
   	 * <p>
   	 * 
   	 * @return carryOver for LeaveSummaryRow
   	 */
	public BigDecimal getCarryOver();
	
	/**
	 * The year to date accrued balance associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * ytdAccruedBalance of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return ytdAccruedBalance for LeaveSummaryRow
	 */
	public BigDecimal getYtdAccruedBalance();
	
	/**
	 * The year to date approved usage associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * ytdApprovedUsage of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return ytdApprovedUsage for LeaveSummaryRow
	 */
	public BigDecimal getYtdApprovedUsage();
	
	/**
	 * The leave balance associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * leaveBalance of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return leaveBalance for LeaveSummaryRow
	 */
	public BigDecimal getLeaveBalance();
	
	/**
	 * The pending leave accrual associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * pendingLeaveAccrual of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return pendingLeaveAccrual for LeaveSummaryRow
	 */
	public BigDecimal getPendingLeaveAccrual();
	
	/**
	 * The pending leave requests associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * pendingLeaveRequests of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return pendingLeaveRequests for LeaveSummaryRow
	 */
	public BigDecimal getPendingLeaveRequests();
	
	/**
	 * The pending leave balance associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * pendingLeaveBalance of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return pendingLeaveBalance for LeaveSummaryRow
	 */
	public BigDecimal getPendingLeaveBalance();
	
	/**
	 * The pending available usage associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * pendingAvailableUsage of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return pendingAvailableUsage for LeaveSummaryRow
	 */
	public BigDecimal getPendingAvailableUsage();
	
	/**
	 * The usage limit associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * usageLimit of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return usageLimit for LeaveSummaryRow
	 */
	public BigDecimal getUsageLimit();
	
	/**
	 * The fmla usage associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * fmlaUsage of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return fmlaUsage for LeaveSummaryRow
	 */
	public BigDecimal getFmlaUsage();
	
	/**
	 * The accrued balance associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * carryOver + ytdAccruedBalance - ytdApprovedUsage
	 * <p>
	 * 
	 * @return accrued balance for LeaveSummaryRow
	 */
    public BigDecimal getAccruedBalance();
    
    /**
	 * The flag that indicates if accrued balance is eligible for transfer
	 * 
	 * <p>
	 * transferable flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if accrued balance is eligible for transfer, N if not
	 */
	public boolean isTransferable();
	
	/**
	 * The flag that indicates if accrued balance is eligible for payout
	 * 
	 * <p>
	 * payoutable flag of a ShiftDifferentialRule
	 * </p>
	 * 
	 * @return Y if accrued balance is eligible for payout, N if not
	 */
	public boolean isPayoutable();
	
	/**
	 * The map of prior year usage associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * priorYearsUsage of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return priorYearsUsage for LeaveSummaryRow
	 */
    public SortedMap<String, BigDecimal> getPriorYearsUsage();
 
    /**
	 * The map of prior years accrued total associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * priorYearsTotalAccrued of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return priorYearsTotalAccrued for LeaveSummaryRow
	 */
    public SortedMap<String, BigDecimal> getPriorYearsTotalAccrued();

    /**
	 * The max carry over associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * maxCarryOver of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return maxCarryOver for LeaveSummaryRow
	 */
	public BigDecimal getMaxCarryOver();
	
	/**
	 * TODO: Put a better comment
	 * The infractingLeaveBlockId associated with the LeaveSummaryRow
	 * 
	 * <p>
	 * infractingLeaveBlockId of a LeaveSummaryRow 
	 * <p>
	 * 
	 * @return infractingLeaveBlockId for LeaveSummaryRow
	 */
	public String getInfractingLeaveBlockId();

}
