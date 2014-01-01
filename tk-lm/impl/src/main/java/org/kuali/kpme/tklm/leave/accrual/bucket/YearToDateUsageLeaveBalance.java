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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.YearToDateUsageLeaveBalanceContract;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.MaximumBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.NegativeBalanceException;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.UsageLimitException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.rice.krad.util.ObjectUtils;

public class YearToDateUsageLeaveBalance extends LeaveBalance implements YearToDateUsageLeaveBalanceContract {

	private List<LeaveBlock> leaveBlocks;

	public YearToDateUsageLeaveBalance(AccrualCategory accrualCategory, PrincipalHRAttributes principalCalendar) {
		super(accrualCategory, principalCalendar);
		asOfDate = LocalDate.now();
		leaveBlocks = new ArrayList<LeaveBlock>();
	}

	private final static String BALANCE_TYPE = "YTD_USAGE_BALANCE";
	
	@Override
	public void add(LeaveBlock leaveBlock) throws UsageLimitException {

		DateTime rolloverDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		
		if(leaveBlock.getLeaveDate().compareTo(asOfDate.toDate()) <= 0 
				&& leaveBlock.getLeaveDate().compareTo(rolloverDate.toDate()) >= 0) {
		
			if(leaveBlock.getLeaveAmount().signum() < 0) {
				EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
				if(earnCode != null) {
					if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
			
						AccrualCategoryRule accrualRule = getAccrualCategoryRuleForDate(leaveBlock.getLeaveLocalDate());
						
						if(accrualRule != null
								&& ObjectUtils.isNotNull(accrualRule.getMaxUsage())) {
							
							BigDecimal maxUsage = new BigDecimal(accrualRule.getMaxUsage());
							BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(leaveBlock.getPrincipalId(), leaveBlock.getLeaveLocalDate());
							maxUsage = maxUsage.multiply(fteSum).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
							
							EmployeeOverride employeeOverride = getEmployeeOverride(leaveBlock, "MU");
							if(employeeOverride != null)
								maxUsage = new BigDecimal(employeeOverride.getOverrideValue());
							
							if(maxUsage.compareTo(this.balance.add(leaveBlock.getLeaveAmount().negate())) <= 0) {
								add(leaveBlock.getLeaveAmount().negate());
								leaveBlocks.add(leaveBlock);
								//throw new UsageLimitException();
							}
							else {
								add(leaveBlock.getLeaveAmount().negate());
								leaveBlocks.add(leaveBlock);
							}
						}
						else {
							//no usage limit
							add(leaveBlock.getLeaveAmount().negate());
							leaveBlocks.add(leaveBlock);
						}
		
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.ADJUSTMENT)) {
						//does not validate against balances
						add(leaveBlock.getLeaveAmount().negate());
						leaveBlocks.add(leaveBlock);
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.NONE)) {
						//no balance validations, does not affect balances
						leaveBlocks.add(leaveBlock);
					}
				}
			}
		}
	}

	@Override
	public void remove(LeaveBlock leaveBlock) throws NegativeBalanceException {
		
		DateTime rolloverDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		
		if(leaveBlock.getLeaveDate().compareTo(asOfDate.toDate()) <= 0 
				&& leaveBlock.getLeaveDate().compareTo(rolloverDate.toDate()) >= 0) {
			
			if(leaveBlock.getLeaveAmount().signum() < 0) {
				EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(leaveBlock.getEarnCode(), LocalDate.fromDateFields(leaveBlock.getLeaveDate()));
				if(earnCode != null) {
					if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.USAGE)){
	
						AccrualCategoryRule accrualRule = getAccrualCategoryRuleForDate(leaveBlock.getLeaveLocalDate());
						
						if(accrualRule != null
								&& ObjectUtils.isNotNull(accrualRule.getMaxUsage())) {
							
							BigDecimal maxUsage = new BigDecimal(accrualRule.getMaxUsage());
							BigDecimal fteSum = HrServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(leaveBlock.getPrincipalId(), leaveBlock.getLeaveLocalDate());
							maxUsage = maxUsage.multiply(fteSum).setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
							
							EmployeeOverride employeeOverride = getEmployeeOverride(leaveBlock, "MU");
							if(employeeOverride != null)
								maxUsage = new BigDecimal(employeeOverride.getOverrideValue());
							
							if(BigDecimal.ZERO.compareTo(this.balance.subtract(leaveBlock.getLeaveAmount().negate())) > 0) {
								remove(leaveBlock.getLeaveAmount().negate());
								leaveBlocks.remove(leaveBlock);
								//throw new NegativeBalanceException();
							}
							else {
								remove(leaveBlock.getLeaveAmount().negate());
								leaveBlocks.remove(leaveBlock);
							}
						}
						else {
							//no usage limit
							remove(leaveBlock.getLeaveAmount().negate());
							leaveBlocks.remove(leaveBlock);
						}
		
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.ADJUSTMENT)) {
						//does not validate against balances
						remove(leaveBlock.getLeaveAmount().negate());
						leaveBlocks.remove(leaveBlock);
					}
					else if(earnCode.getAccrualBalanceAction().equals(HrConstants.ACCRUAL_BALANCE_ACTION.NONE)) {
						//no balance validations, does not affect balances
						leaveBlocks.remove(leaveBlock);
					}
				}
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
		leaveBlocks.clear();
		super.clear();
	}

}
