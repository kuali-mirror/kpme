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

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.AccruedLeaveBalanceContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.override.EmployeeOverrideContract;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaxCarryoverException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaximumBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.NegativeBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.UsageLimitException;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccruedLeaveBalance extends LeaveBalance implements AccruedLeaveBalanceContract {

	private List<LeaveBlock> leaveBlocks;
	private YearToDateEarnedLeaveBalance ytdEarned;
	private YearToDateUsageLeaveBalance ytdUsage;
	private CarryOverLeaveBalance carryOver;

	public AccruedLeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		//bad, add asOfDate to constructor
		asOfDate = LocalDate.now();
		leaveBlocks = new ArrayList<LeaveBlock>();
		ytdEarned = new YearToDateEarnedLeaveBalance(accrualCategory, principalCalendar);
		ytdUsage = new YearToDateUsageLeaveBalance(accrualCategory, principalCalendar);
		carryOver = new CarryOverLeaveBalance(accrualCategory, principalCalendar);
	}

	private final static String BALANCE_TYPE = "ACCRUED_BALANCE";
	
	@Override
	public void add(LeaveBlock leaveBlock) throws MaximumBalanceException, NegativeBalanceException {
		//TODO: Is the leave block within the previous calendar year ( carryover ), a current calendar year ( could be planned/future usage ), or 
		// a future calendar year ( is planned/future if usage; no balance action if accrual. )
		//DateTime prevRolloverDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		if(leaveBlock.getLeaveLocalDate().compareTo(asOfDate) <= 0) {
			
			try {
				ytdUsage.add(leaveBlock);
			} catch (UsageLimitException e) {
				e.printStackTrace();
			}
			
			ytdEarned.add(leaveBlock);
			
			try {
				carryOver.add(leaveBlock);
			} catch (MaxCarryoverException e) {
				e.printStackTrace();
			}
			
			EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), leaveBlock.getLeaveLocalDate());
			if(earnCode != null) {
				if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
		
					AccrualCategoryRule accrualRule = getAccrualCategoryRuleForDate(leaveBlock.getLeaveLocalDate());
		
					if(leaveBlock.getLeaveAmount().signum() < 0) {
						if(StringUtils.equals(earnCode.getAllowNegativeAccrualBalance(),"N")) {
							if(BigDecimal.ZERO.compareTo(leaveBlock.getLeaveAmount().add(this.balance)) <= 0) {
								add(leaveBlock.getLeaveAmount());
								leaveBlocks.add(leaveBlock);
							}
							else if(StringUtils.equals(earnCode.getAllowNegativeAccrualBalance(),"Y")){
								add(leaveBlock.getLeaveAmount());
								leaveBlocks.add(leaveBlock);
							}
							else {
								add(leaveBlock.getLeaveAmount());
								leaveBlocks.add(leaveBlock);
								//throw new NegativeBalanceException();
							}
						}
						else {
							add(leaveBlock.getLeaveAmount());
							leaveBlocks.add(leaveBlock);
						}
					}
					else {
						if(accrualRule != null
								&& StringUtils.isNotEmpty(accrualRule.getMaxBalFlag())
								&& StringUtils.equals(accrualRule.getMaxBalFlag(),"Y")) {
							
							BigDecimal maxBalance = accrualRule.getMaxBalance();
							BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(leaveBlock.getPrincipalId(), leaveBlock.getLeaveLocalDate());
							maxBalance = maxBalance.multiply(fteSum).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);

                            EmployeeOverrideContract employeeOverride = getEmployeeOverride(leaveBlock, "MB");
							if(employeeOverride != null)
								maxBalance = new BigDecimal(employeeOverride.getOverrideValue());
							
							if(maxBalance.compareTo(leaveBlock.getLeaveAmount().add(this.balance)) <= 0) {
								add(leaveBlock.getLeaveAmount());
								leaveBlocks.add(leaveBlock);
							}
							else {
								add(leaveBlock.getLeaveAmount());
								leaveBlocks.add(leaveBlock);
								//throw new MaximumBalanceException();
							}
						}
						else {
							add(leaveBlock.getLeaveAmount());
							leaveBlocks.add(leaveBlock);
						}
					}
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
			
			}
		}
		//this balance should equal carryOver + ytdEarned.balance - ytdUsage.balance 
	}

	@Override
	public void remove(LeaveBlock leaveBlock) throws MaximumBalanceException, NegativeBalanceException {

		if(leaveBlock.getLeaveLocalDate().compareTo(asOfDate) <= 0) {
		
			try {
				ytdUsage.remove(leaveBlock);
			} catch (NegativeBalanceException e1) {
				e1.printStackTrace();
			}
			
			ytdEarned.remove(leaveBlock);
			
			try {
				carryOver.remove(leaveBlock);
			} catch (MaxCarryoverException e) {
				e.printStackTrace();
			}
			
			AccrualCategoryRule accrualRule = getAccrualCategoryRuleForDate(leaveBlock.getLeaveLocalDate());
			EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), leaveBlock.getLeaveLocalDate());
			if(earnCode != null) {
				if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
					//validate and add to / subtract from balances
					if(leaveBlock.getLeaveAmount().signum() < 0) {
						//removing a negative amount
						if(accrualRule != null
								&& StringUtils.isNotEmpty(accrualRule.getMaxBalFlag())
								&& StringUtils.equals(accrualRule.getMaxBalFlag(),"Y")) {
							
							BigDecimal maxBalance = accrualRule.getMaxBalance();
							BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(leaveBlock.getPrincipalId(), leaveBlock.getLeaveLocalDate());
							maxBalance = maxBalance.multiply(fteSum).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);

                            EmployeeOverrideContract employeeOverride = getEmployeeOverride(leaveBlock, "MB");
							if(employeeOverride != null)
								maxBalance = new BigDecimal(employeeOverride.getOverrideValue());
							
							if(maxBalance.compareTo(this.balance.subtract(leaveBlock.getLeaveAmount())) <= 0) {
								remove(leaveBlock.getLeaveAmount());
								leaveBlocks.remove(leaveBlock);
								//throw new MaximumBalanceException();
							}
							else {
								remove(leaveBlock.getLeaveAmount());
								leaveBlocks.remove(leaveBlock);
							}
						}
						else {
							remove(leaveBlock.getLeaveAmount());
							leaveBlocks.remove(leaveBlock);
						}
					}
					else {
						//removing a positive amount
						if(StringUtils.equals(earnCode.getAllowNegativeAccrualBalance(),"N")) {
							if(BigDecimal.ZERO.compareTo(this.balance.subtract(leaveBlock.getLeaveAmount())) <= 0) {
								remove(leaveBlock.getLeaveAmount());
								leaveBlocks.remove(leaveBlock);
							}
							else {
								remove(leaveBlock.getLeaveAmount());
								leaveBlocks.remove(leaveBlock);
								//throw new NegativeBalanceException();
							}
						}
						else {
							remove(leaveBlock.getLeaveAmount());
							leaveBlocks.remove(leaveBlock);
						}
					}
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
	
			}
			//this balance should equal carryOver + ytdEarned.balance - ytdUsage.balance
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
		super.clear();
		leaveBlocks.clear();
		ytdEarned.clear();
		ytdUsage.clear();
		carryOver.clear();
	}

}
