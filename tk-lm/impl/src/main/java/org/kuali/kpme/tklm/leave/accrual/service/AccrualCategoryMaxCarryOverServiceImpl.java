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
package org.kuali.kpme.tklm.leave.accrual.service;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryService;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleService;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.job.service.JobService;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.leaveplan.LeavePlanService;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.api.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.api.leave.accrual.AccrualCategoryMaxCarryOverService;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockService;
import org.kuali.kpme.tklm.api.leave.override.EmployeeOverrideContract;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.api.leave.override.EmployeeOverrideService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class AccrualCategoryMaxCarryOverServiceImpl implements AccrualCategoryMaxCarryOverService {
	
	private AccrualCategoryService accrualCategoryService;
	private AccrualCategoryRuleService accrualCategoryRuleService;
	private EmployeeOverrideService employeeOverrideService;
	private JobService jobService;
	private LeaveBlockService leaveBlockService;
	private LeavePlanService leavePlanService;
	private PrincipalHRAttributesService principalHRAttributesService;
	
	@Override
	public boolean exceedsAccrualCategoryMaxCarryOver(String accrualCategory, String principalId, List<CalendarEntry> calendarEntries, LocalDate asOfDate) {
		boolean exceedsAccrualCategoryMaxCarryOver = false;
		
		PrincipalHRAttributesContract principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {
            CalendarEntry lastCalendarPeriodOfLeavePlan = null;
			
			for (CalendarEntry calendarEntry : calendarEntries) {
				if (getLeavePlanService().isLastCalendarPeriodOfLeavePlan(calendarEntry, principalCalendar.getLeavePlan(), asOfDate)) {
					lastCalendarPeriodOfLeavePlan = calendarEntry;
					break;
				}
			}
			
			if (lastCalendarPeriodOfLeavePlan != null) {
				exceedsAccrualCategoryMaxCarryOver = getAccrualCategoryCarryOverAdjustment(accrualCategory, principalId, lastCalendarPeriodOfLeavePlan, asOfDate).compareTo(BigDecimal.ZERO) > 0;
			}
		}
		
		return exceedsAccrualCategoryMaxCarryOver;
	}
	
	@Override
	public boolean exceedsAccrualCategoryMaxCarryOver(String accrualCategory, String principalId, CalendarEntry calendarEntry, LocalDate asOfDate) {
		boolean exceedsAccrualCategoryMaxCarryOver = false;
		
		PrincipalHRAttributesContract principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {
			if (getLeavePlanService().isLastCalendarPeriodOfLeavePlan(calendarEntry, principalCalendar.getLeavePlan(), asOfDate)) {
				exceedsAccrualCategoryMaxCarryOver = getAccrualCategoryCarryOverAdjustment(accrualCategory, principalId, calendarEntry, asOfDate).compareTo(BigDecimal.ZERO) > 0;
			}
		}
		
		return exceedsAccrualCategoryMaxCarryOver;
	}
	
	@Override
	public void calculateMaxCarryOver(String documentId, String principalId, List<CalendarEntry> calendarEntries, LocalDate asOfDate) {
		PrincipalHRAttributesContract principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {
            CalendarEntry lastCalendarPeriodOfLeavePlan = null;
			
			for (CalendarEntry calendarEntry : calendarEntries) {
				if (getLeavePlanService().isLastCalendarPeriodOfLeavePlan(calendarEntry, principalCalendar.getLeavePlan(), asOfDate)) {
					lastCalendarPeriodOfLeavePlan = calendarEntry;
					break;
				}
			}
			
			if (lastCalendarPeriodOfLeavePlan != null) {
				calculateMaxCarryOverForLeavePlan(documentId, principalId, lastCalendarPeriodOfLeavePlan, principalCalendar.getLeavePlan(), asOfDate);
			}
		}
	}
		
	@Override
	public void calculateMaxCarryOver(String documentId, String principalId, CalendarEntry calendarEntry, LocalDate asOfDate) {
		PrincipalHRAttributesContract principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {			
			if (getLeavePlanService().isLastCalendarPeriodOfLeavePlan(calendarEntry, principalCalendar.getLeavePlan(), asOfDate)) {
				calculateMaxCarryOverForLeavePlan(documentId, principalId, calendarEntry, principalCalendar.getLeavePlan(), asOfDate);
			}
		}
	}
	
	private void calculateMaxCarryOverForLeavePlan(String documentId, String principalId, CalendarEntry calendarEntry, String leavePlan, LocalDate asOfDate) {
		List<? extends AccrualCategoryContract> accrualCategories = getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate);
		
		for (AccrualCategoryContract accrualCategory : accrualCategories) {
			BigDecimal adjustmentAmount = getAccrualCategoryCarryOverAdjustment(accrualCategory.getAccrualCategory(), principalId, calendarEntry, asOfDate);
			
			if (adjustmentAmount.compareTo(BigDecimal.ZERO) > 0) {
				LocalDate LeaveDate = calendarEntry.getEndPeriodFullDateTime().toLocalDate().minusDays(1);
			
				addAdjustmentLeaveBlock(documentId, principalId, LeaveDate, accrualCategory, adjustmentAmount.negate());
			}
		}
	}
	
	private BigDecimal getAccrualCategoryCarryOverAdjustment(String accrualCategory, String principalId, CalendarEntry calendarEntry, LocalDate asOfDate) {
		BigDecimal accrualCategoryCarryOverAdjustment = BigDecimal.ZERO;
		
		PrincipalHRAttributesContract principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {
			LeavePlan leavePlan = getLeavePlanService().getLeavePlan(principalCalendar.getLeavePlan(), principalCalendar.getEffectiveLocalDate());
			
			if (leavePlan != null) {
				AccrualCategory accrualCategoryObj = getAccrualCategoryService().getAccrualCategory(accrualCategory, asOfDate);
				
				if (accrualCategoryObj != null) {
					AccrualCategoryRule accrualCategoryRule = getMaxCarryOverAccrualCategoryRule(accrualCategoryObj, principalCalendar.getServiceLocalDate(), asOfDate);
					
					if (accrualCategoryRule != null) {
						Long maxCarryOverLimitValue = getMaxCarryOverLimitValue(principalId, leavePlan, accrualCategoryObj, accrualCategoryRule, asOfDate);
					
						if (maxCarryOverLimitValue != null) {
							BigDecimal accrualCategoryBalance = getAccrualCategoryBalance(principalId, accrualCategoryObj, asOfDate);
			
							BigDecimal maxCarryOverLimit = new BigDecimal(maxCarryOverLimitValue);
							BigDecimal fteSum = getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, asOfDate);
							BigDecimal maxCarryOver = maxCarryOverLimit.multiply(fteSum);
						
							accrualCategoryCarryOverAdjustment = accrualCategoryBalance.subtract(maxCarryOver);
						}
					}
				}
			}
		}
		
		return accrualCategoryCarryOverAdjustment;
	}
	
	private AccrualCategoryRule getMaxCarryOverAccrualCategoryRule(AccrualCategory accrualCategory, LocalDate serviceDate, LocalDate asOfDate) {
		AccrualCategoryRule maxCarryOverAccrualCategoryRule = null;
		
		AccrualCategoryRule accrualCategoryRule = getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, asOfDate, serviceDate);
		
		if (accrualCategoryRule != null) {
			if (StringUtils.equals(accrualCategoryRule.getMaxBalFlag(), "N") || !StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(), HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				maxCarryOverAccrualCategoryRule = accrualCategoryRule;
			}
		}
		
		return maxCarryOverAccrualCategoryRule;
	}
	
	private Long getMaxCarryOverLimitValue(String principalId, LeavePlan leavePlan, AccrualCategory accrualCategory, AccrualCategoryRule accrualCategoryRule, LocalDate asOfDate) {
		Long maxCarryOverLimitValue = null;
		
		Long employeeOverrideMaxCarryOverValue = getEmployeeOverrideMaxCarryOverValue(principalId, leavePlan, accrualCategory, asOfDate);
		
		if (employeeOverrideMaxCarryOverValue != null) {
			maxCarryOverLimitValue = employeeOverrideMaxCarryOverValue;
		} else {
			Long accrualCategoryRuleMaxCarryOverValue = accrualCategoryRule.getMaxCarryOver();
			
			if (accrualCategoryRuleMaxCarryOverValue != null) {
				maxCarryOverLimitValue = accrualCategoryRuleMaxCarryOverValue;
			}
		}
		
		return maxCarryOverLimitValue;
	}
	
	private Long getEmployeeOverrideMaxCarryOverValue(String principalId, LeavePlan leavePlan, AccrualCategory accrualCategory, LocalDate asOfDate) {
		Long employeeOverrideMaxCarryOverValue = null;

        EmployeeOverrideContract employeeOverride = getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan.getLeavePlan(), accrualCategory.getAccrualCategory(), "MAC", asOfDate);
		
		if (employeeOverride != null) {
			employeeOverrideMaxCarryOverValue = employeeOverride.getOverrideValue();
		}
		
		return employeeOverrideMaxCarryOverValue;
	}
	
	private BigDecimal getAccrualCategoryBalance(String principalId, AccrualCategory accrualCategory, LocalDate endDate) {
		BigDecimal accrualCategoryBalance = BigDecimal.ZERO;
		
		LocalDate beginDate = endDate.minusYears(1);
		List<LeaveBlock> leaveBlocks = getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
        
		for (LeaveBlock leaveBlock : leaveBlocks) {
            if (StringUtils.equals(leaveBlock.getAccrualCategory(), accrualCategory.getAccrualCategory())) {
            	if (StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.APPROVED)) {
                	accrualCategoryBalance = accrualCategoryBalance.add(leaveBlock.getLeaveAmount());
                }
            }
		}
		
		return accrualCategoryBalance;
	}
	
	@SuppressWarnings("unchecked")
	private void addAdjustmentLeaveBlock(String documentId, String principalId, LocalDate leaveDate, AccrualCategoryContract accrualCategory, BigDecimal adjustmentAmount) {
		LeaveBlock.Builder builder = LeaveBlock.Builder.create(principalId, accrualCategory.getEarnCode(), adjustmentAmount, leaveDate, documentId);
        //LeaveBlockBo leaveBlock = new LeaveBlockBo.Builder(leaveDate, documentId, principalId, accrualCategory.getEarnCode(), adjustmentAmount)
        builder.setDescription(LMConstants.MAX_CARRY_OVER_ADJUSTMENT);
        builder.setAccrualCategory(accrualCategory.getAccrualCategory());
        builder.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER_ADJUSTMENT);
        builder.setRequestStatus(HrConstants.REQUEST_STATUS.REQUESTED);
        builder.setUserPrincipalId(principalId);
        builder.setCreateTime(DateTime.now());
        
        getLeaveBlockService().saveLeaveBlocks(Collections.singletonList(builder.build()));
	}

	public AccrualCategoryService getAccrualCategoryService() {
		return accrualCategoryService;
	}

	public void setAccrualCategoryService(AccrualCategoryService accrualCategoryService) {
		this.accrualCategoryService = accrualCategoryService;
	}

	public AccrualCategoryRuleService getAccrualCategoryRuleService() {
		return accrualCategoryRuleService;
	}

	public void setAccrualCategoryRuleService(AccrualCategoryRuleService accrualCategoryRuleService) {
		this.accrualCategoryRuleService = accrualCategoryRuleService;
	}

	public EmployeeOverrideService getEmployeeOverrideService() {
		return employeeOverrideService;
	}

	public void setEmployeeOverrideService(EmployeeOverrideService employeeOverrideService) {
		this.employeeOverrideService = employeeOverrideService;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public LeavePlanService getLeavePlanService() {
		return leavePlanService;
	}

	public void setLeavePlanService(LeavePlanService leavePlanService) {
		this.leavePlanService = leavePlanService;
	}

	public PrincipalHRAttributesService getPrincipalHRAttributesService() {
		return principalHRAttributesService;
	}

	public void setPrincipalHRAttributesService(PrincipalHRAttributesService principalHRAttributesService) {
		this.principalHRAttributesService = principalHRAttributesService;
	}

	public LeaveBlockService getLeaveBlockService() {
		return leaveBlockService;
	}

	public void setLeaveBlockService(LeaveBlockService leaveBlockService) {
		this.leaveBlockService = leaveBlockService;
	}

}
