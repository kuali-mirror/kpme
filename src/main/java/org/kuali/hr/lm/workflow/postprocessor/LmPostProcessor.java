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
package org.kuali.hr.lm.workflow.postprocessor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;

import edu.emory.mathcs.backport.java.util.Collections;

public class LmPostProcessor extends DefaultPostProcessor {
	
	private static final Logger LOG = Logger.getLogger(LmPostProcessor.class);

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {		
		ProcessDocReport pdr = null;
		Long documentId = new Long(statusChangeEvent.getDocumentId());
		LeaveCalendarDocumentHeader document = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId.toString());
		if (document != null) {
			pdr = super.doRouteStatusChange(statusChangeEvent);
			// Only update the status if it's different.
			if (!document.getDocumentStatus().equals(statusChangeEvent.getNewRouteStatus())) {
                DocumentStatus newDocumentStatus = DocumentStatus.fromCode(statusChangeEvent.getNewRouteStatus());

				updateLeaveCalendarDocumentHeaderStatus(document, newDocumentStatus);
				
				calculateMaxCarryOver(document, newDocumentStatus);
			}
		}
		
		return pdr;
	}
	
	private void updateLeaveCalendarDocumentHeaderStatus(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader, DocumentStatus newDocumentStatus) {
		leaveCalendarDocumentHeader.setDocumentStatus(newDocumentStatus.getCode());
		TkServiceLocator.getLeaveCalendarDocumentHeaderService().saveOrUpdate(leaveCalendarDocumentHeader);
	}
	
	private void calculateMaxCarryOver(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader, DocumentStatus newDocumentStatus) {
		if (DocumentStatus.FINAL.equals(newDocumentStatus)) {
			String documentId = leaveCalendarDocumentHeader.getDocumentId();
			String principalId = leaveCalendarDocumentHeader.getPrincipalId();
			Date endDate = leaveCalendarDocumentHeader.getEndDate();
			PrincipalHRAttributes principalCalendar = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, endDate);
			
			if (StringUtils.isNotBlank(principalCalendar.getLeavePlan())) {
				LeavePlan leavePlan = TkServiceLocator.getLeavePlanService().getLeavePlan(principalCalendar.getLeavePlan(), principalCalendar.getEffectiveDate());
				
				if (isLastLeaveCalendarPeriod(leavePlan, endDate)) {
					List<AccrualCategory> accrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(leavePlan.getLeavePlan(), new java.sql.Date(endDate.getTime()));
				
					for (AccrualCategory accrualCategory : accrualCategories) {
						AccrualCategoryRule accrualCategoryRule = getMaxCarryOverAccrualCategoryRule(accrualCategory, principalCalendar.getServiceDate(), endDate);
						
						if (accrualCategoryRule != null) {
							Long maxCarryOverLimitValue = getMaxCarryOverLimitValue(principalId, leavePlan, accrualCategory, accrualCategoryRule, endDate);
						
							if (maxCarryOverLimitValue != null) {
								BigDecimal accrualCategoryBalance = getAccrualCategoryBalance(principalId, accrualCategory, endDate);

								BigDecimal maxCarryOverLimit = new BigDecimal(maxCarryOverLimitValue);
								BigDecimal fteSum = TkServiceLocator.getJobService().getFteSumForAllActiveLeaveEligibleJobs(principalId, endDate);
								BigDecimal maxCarryOver = maxCarryOverLimit.multiply(fteSum);
							
								if (accrualCategoryBalance.compareTo(maxCarryOver) > 0) {
									DateTime leaveBlockDate = new DateTime(endDate).minusSeconds(1);
									BigDecimal adjustmentAmount = maxCarryOver.subtract(accrualCategoryBalance);

									addAdjustmentLeaveBlock(documentId, principalId, leaveBlockDate, accrualCategory, adjustmentAmount);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isLastLeaveCalendarPeriod(LeavePlan leavePlan, Date endDate) {
		boolean isLastLeaveCalendarPeriod = false;
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd");
		
		try {
			DateTime calendarYearStart = new DateTime(format.parse(leavePlan.getCalendarYearStart()));
			DateTime calendarEntryEndDate = new DateTime(endDate);
			
			int calendarYearStartMonth = calendarYearStart.getMonthOfYear();
			int calendarYearStartDay = calendarYearStart.getDayOfMonth();
			int calendarEntryEndDateMonth = calendarEntryEndDate.getMonthOfYear();
			int calendarEntryEndDateDay = calendarEntryEndDate.getDayOfMonth();
			
			isLastLeaveCalendarPeriod = (calendarYearStartMonth == calendarEntryEndDateMonth) && (calendarYearStartDay == calendarEntryEndDateDay);
		} catch (ParseException e) {
			LOG.error("Could not parse leave plan calendar year start.");
		}
		
		return isLastLeaveCalendarPeriod;
	}
	
	private AccrualCategoryRule getMaxCarryOverAccrualCategoryRule(AccrualCategory accrualCategory, Date serviceDate, Date asOfDate) {
		AccrualCategoryRule maxCarryOverAccrualCategoryRule = null;
		
		AccrualCategoryRule accrualCategoryRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, asOfDate, serviceDate);
		
		if (accrualCategoryRule != null) {
			if (StringUtils.equals(accrualCategoryRule.getMaxBalFlag(), "N") || !StringUtils.equals(accrualCategoryRule.getMaxBalanceActionFrequency(), LMConstants.MAX_BAL_ACTION_FREQ.YEAR_END)) {
				maxCarryOverAccrualCategoryRule = accrualCategoryRule;
			}
		}
		
		return maxCarryOverAccrualCategoryRule;
	}
	
	private Long getMaxCarryOverLimitValue(String principalId, LeavePlan leavePlan, AccrualCategory accrualCategory, AccrualCategoryRule accrualCategoryRule, Date asOfDate) {
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
	
	private Long getEmployeeOverrideMaxCarryOverValue(String principalId, LeavePlan leavePlan, AccrualCategory accrualCategory, Date asOfDate) {
		Long employeeOverrideMaxCarryOverValue = null;
		
		EmployeeOverride employeeOverride = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverride(principalId, leavePlan.getLeavePlan(), accrualCategory.getAccrualCategory(), "MAC", new java.sql.Date(asOfDate.getTime()));
		
		if (employeeOverride != null) {
			employeeOverrideMaxCarryOverValue = employeeOverride.getOverrideValue();
		}
		
		return employeeOverrideMaxCarryOverValue;
	}
	
	private BigDecimal getAccrualCategoryBalance(String principalId, AccrualCategory accrualCategory, Date endDate) {
		BigDecimal accrualCategoryBalance = BigDecimal.ZERO;
		
		Date beginDate = new DateTime(endDate).minusYears(1).toDate();
		List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(principalId, beginDate, endDate);
        
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
	private void addAdjustmentLeaveBlock(String documentId, String principalId, DateTime leaveBlockDate, AccrualCategory accrualCategory, BigDecimal adjustmentAmount) {
		LeaveBlock leaveBlock = new LeaveBlock.Builder(leaveBlockDate, documentId, principalId, accrualCategory.getEarnCode(), adjustmentAmount)
        	.description("Max carry over adjustment")
        	.accrualCategory(accrualCategory.getAccrualCategory())
        	.leaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER)
        	.requestStatus(LMConstants.REQUEST_STATUS.APPROVED)
        	.principalIdModified(principalId)
        	.timestamp(TKUtils.getCurrentTimestamp())
        	.build();
        
        TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(Collections.singletonList(leaveBlock));
	}

}