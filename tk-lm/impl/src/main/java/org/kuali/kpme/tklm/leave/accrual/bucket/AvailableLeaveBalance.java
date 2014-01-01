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
package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.AvailableLeaveBalanceContract;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaximumBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.NegativeBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.UsageLimitException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;

public class AvailableLeaveBalance extends LeaveBalance implements AvailableLeaveBalanceContract {

	private List<LeaveBlock> leaveBlocks;
	//Future/Planned Usage
	private PendingLeaveBalance pendingBalance;
	private YearToDateUsageLeaveBalance ytdUsage;

	public AvailableLeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		asOfDate = LocalDate.now();
		pendingBalance = new PendingLeaveBalance(accrualCategory,principalCalendar);
		ytdUsage = new YearToDateUsageLeaveBalance(accrualCategory,principalCalendar);
		leaveBlocks = new ArrayList<LeaveBlock>();
	}

	private final static String BALANCE_TYPE = "AVAILABLE_BALANCE";
	
	@Override
	public void add(LeaveBlock leaveBlock) throws NegativeBalanceException {
		
		DateTime rolloverDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		
		if((leaveBlock.getLeaveDate().compareTo(asOfDate.toDate()) <= 0)/*with any signum*/
				|| leaveBlock.getLeaveAmount().signum() < 0 ) {
			try {
				//AVAILABLE BALANCE = USAGE LIMIT - YTDUSAGE - PENDING/FUTURE USAGE
				ytdUsage.add(leaveBlock);
				
				EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
				if(earnCode != null) {
					if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
						//available balance is derived from ytdUsage, usage limit ( if any ), and pendingBalance[usage]
						add(leaveBlock.getLeaveAmount());
						leaveBlocks.add(leaveBlock);
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.ADJUSTMENT)) {
						//does not validate against balances
						add(leaveBlock.getLeaveAmount());
						leaveBlocks.add(leaveBlock);
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.NONE)) {
						//no balance validations, does not affect balances
						leaveBlocks.add(leaveBlock);
					}
					
					try {
						//pendingBalance should never throw BalanceExceptions, so logic below should always execute.
						//If ytdUsage throws UsageLimitException (above), no calculations should be made on this balance.
						pendingBalance.add(leaveBlock);
						
	/*					AccrualCategoryRule accrualRule = getAccrualCategoryRuleForDate(leaveBlock.getLeaveLocalDate());
						
						if(accrualRule != null
								&& ObjectUtils.isNotNull(accrualRule.getMaxUsage())) {
							
							BigDecimal maxUsage = new BigDecimal(accrualRule.getMaxUsage());
							BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(leaveBlock.getPrincipalId(), leaveBlock.getLeaveLocalDate());
							maxUsage = maxUsage.multiply(fteSum).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
							
							EmployeeOverride employeeOverride = getEmployeeOverride(leaveBlock, "MU");
							if(employeeOverride != null
									&& ObjectUtils.isNotNull(employeeOverride.getOverrideValue()))
								maxUsage = new BigDecimal(employeeOverride.getOverrideValue());
							
							if(maxUsage == null) {
								this.balance = new BigDecimal(Long.MAX_VALUE);
								//no usage limit, available balance should be accrued balance!
							}
							else
								this.balance = maxUsage.subtract(ytdUsage.balance.add(pendingBalance.balance));
						}
						else
							this.balance = new BigDecimal(Long.MAX_VALUE);*/
					} catch (MaximumBalanceException e) {
						e.printStackTrace();
					} catch (NegativeBalanceException e) {
						e.printStackTrace();
					}
				}
			} catch (UsageLimitException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void remove(LeaveBlock leaveBlock) {
		
		if((leaveBlock.getLeaveDate().compareTo(asOfDate.toDate()) <= 0)/*any leave amount signum*/
				|| leaveBlock.getLeaveAmount().signum() < 0 ) {
			try {
				//AVAILABLE BALANCE = USAGE LIMIT - YTDUSAGE - PENDING/FUTURE USAGE
				ytdUsage.remove(leaveBlock);
				
				EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
				if(earnCode != null) {
					if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
						//available balance is derived from ytdUsage, usage limit ( if any ), and pendingBalance[usage]
						remove(leaveBlock.getLeaveAmount());
						leaveBlocks.remove(leaveBlock);
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.ADJUSTMENT)) {
						//does not validate against balances
						remove(leaveBlock.getLeaveAmount());
						leaveBlocks.remove(leaveBlock);
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.NONE)) {
						//no balance validations, does not affect balances
						leaveBlocks.remove(leaveBlock);
					}
					
					try {
						//pendingBalance should never throw BalanceExceptions, so logic below should always execute.
						//If ytdUsage throws UsageLimitException (above), no calculations should be made on this balance.
						pendingBalance.remove(leaveBlock);
						
	/*					AccrualCategoryRule accrualRule = getAccrualCategoryRuleForDate(leaveBlock.getLeaveLocalDate());
						
						if(accrualRule != null
								&& ObjectUtils.isNotNull(accrualRule.getMaxUsage())) {
							
							BigDecimal maxUsage = new BigDecimal(accrualRule.getMaxUsage());
							BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(leaveBlock.getPrincipalId(), leaveBlock.getLeaveLocalDate());
							maxUsage = maxUsage.multiply(fteSum).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
							
							EmployeeOverride employeeOverride = getEmployeeOverride(leaveBlock, "MU");
							if(employeeOverride != null
									&& ObjectUtils.isNotNull(employeeOverride.getOverrideValue()))
								maxUsage = new BigDecimal(employeeOverride.getOverrideValue());
							
							if(maxUsage == null) {
								this.balance = new BigDecimal(Long.MAX_VALUE);
								//no usage limit, available balance should be accrued balance!
							}
							else
								this.balance = maxUsage.subtract(ytdUsage.balance.add(pendingBalance.balance));
						}
						else
							this.balance = new BigDecimal(Long.MAX_VALUE);*/
					} catch (MaximumBalanceException e) {
						e.printStackTrace();
					} catch (NegativeBalanceException e) {
						e.printStackTrace();
					}
				}
			} catch (UsageLimitException e) {
				e.printStackTrace();
			} catch (NegativeBalanceException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public String getBalanceType() {
		return BALANCE_TYPE;
	}

	@Override
	public void adjust(LeaveBlock leaveBlock) throws UsageLimitException,
			MaximumBalanceException, NegativeBalanceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		pendingBalance.clear();
		ytdUsage.clear();
		leaveBlocks.clear();
		super.clear();
	}

}
