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
package org.kuali.hr.lm.accrual.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.job.service.JobService;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.employeeoverride.service.EmployeeOverrideService;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.service.LeaveBlockService;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.leaveplan.service.LeavePlanService;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.time.util.TKUtils;

import java.util.Collections;

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
		
		PrincipalHRAttributes principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
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
		
		PrincipalHRAttributes principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {
			if (getLeavePlanService().isLastCalendarPeriodOfLeavePlan(calendarEntry, principalCalendar.getLeavePlan(), asOfDate)) {
				exceedsAccrualCategoryMaxCarryOver = getAccrualCategoryCarryOverAdjustment(accrualCategory, principalId, calendarEntry, asOfDate).compareTo(BigDecimal.ZERO) > 0;
			}
		}
		
		return exceedsAccrualCategoryMaxCarryOver;
	}
	
	@Override
	public void calculateMaxCarryOver(String documentId, String principalId, List<CalendarEntry> calendarEntries, LocalDate asOfDate) {
		PrincipalHRAttributes principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
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
		PrincipalHRAttributes principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
		if (principalCalendar != null) {			
			if (getLeavePlanService().isLastCalendarPeriodOfLeavePlan(calendarEntry, principalCalendar.getLeavePlan(), asOfDate)) {
				calculateMaxCarryOverForLeavePlan(documentId, principalId, calendarEntry, principalCalendar.getLeavePlan(), asOfDate);
			}
		}
	}
	
	private void calculateMaxCarryOverForLeavePlan(String documentId, String principalId, CalendarEntry calendarEntry, String leavePlan, LocalDate asOfDate) {
		List<AccrualCategory> accrualCategories = getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(leavePlan, asOfDate);
		
		for (AccrualCategory accrualCategory : accrualCategories) {
			BigDecimal adjustmentAmount = getAccrualCategoryCarryOverAdjustment(accrualCategory.getAccrualCategory(), principalId, calendarEntry, asOfDate);
			
			if (adjustmentAmount.compareTo(BigDecimal.ZERO) > 0) {
				LocalDate LeaveDate = calendarEntry.getEndPeriodFullDateTime().toLocalDate().minusDays(1);
			
				addAdjustmentLeaveBlock(documentId, principalId, LeaveDate, accrualCategory, adjustmentAmount.negate());
			}
		}
	}
	
	private BigDecimal getAccrualCategoryCarryOverAdjustment(String accrualCategory, String principalId, CalendarEntry calendarEntry, LocalDate asOfDate) {
		BigDecimal accrualCategoryCarryOverAdjustment = BigDecimal.ZERO;
		
		PrincipalHRAttributes principalCalendar = getPrincipalHRAttributesService().getPrincipalCalendar(principalId, asOfDate);
		
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
			if (StringUtils.equals(accrualCategoryRule.getMaxBalFlag(), "N") || !StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
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
		
		EmployeeOverride employeeOverride = getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan.getLeavePlan(), accrualCategory.getAccrualCategory(), "MAC", asOfDate);
		
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
            	if (StringUtils.equals(leaveBlock.getRequestStatus(), LMConstants.REQUEST_STATUS.APPROVED)) {
                	accrualCategoryBalance = accrualCategoryBalance.add(leaveBlock.getLeaveAmount());
                }
            }
		}
		
		return accrualCategoryBalance;
	}
	
	@SuppressWarnings("unchecked")
	private void addAdjustmentLeaveBlock(String documentId, String principalId, LocalDate leaveDate, AccrualCategory accrualCategory, BigDecimal adjustmentAmount) {
		LeaveBlock leaveBlock = new LeaveBlock.Builder(leaveDate, documentId, principalId, accrualCategory.getEarnCode(), adjustmentAmount)
        	.description("Max carry over adjustment")
        	.accrualCategory(accrualCategory.getAccrualCategory())
        	.leaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER)
        	.requestStatus(LMConstants.REQUEST_STATUS.REQUESTED)
        	.principalIdModified(principalId)
        	.timestamp(TKUtils.getCurrentTimestamp())
        	.build();
        
        getLeaveBlockService().saveLeaveBlocks(Collections.singletonList(leaveBlock));
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

	public LeaveBlockService getLeaveBlockService() {
		return leaveBlockService;
	}

	public void setLeaveBlockService(LeaveBlockService leaveBlockService) {
		this.leaveBlockService = leaveBlockService;
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

}