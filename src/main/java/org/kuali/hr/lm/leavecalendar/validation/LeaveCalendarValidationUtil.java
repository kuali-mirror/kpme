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
package org.kuali.hr.lm.leavecalendar.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.accrual.service.AccrualCategoryRuleService;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

import com.google.common.base.Predicate;
import org.kuali.rice.krad.util.ErrorMessage;

public class LeaveCalendarValidationUtil {
    
    //begin KPME-1263
    public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
    		updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lcf.getLeaveBlockId());
    	}
    	return validateLeaveAccrualRuleMaxUsage(lcf.getLeaveSummary(), lcf.getSelectedEarnCode(), lcf.getStartDate(),
    			lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }

	public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveSummary ls, String selectedEarnCode, String leaveStartDateString,
			String leaveEndDateString, BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();
        String principalId = TKContext.getTargetPrincipalId();
    	long daysSpan = TKUtils.getDaysBetween(TKUtils.formatDateString(leaveStartDateString), TKUtils.formatDateString(leaveEndDateString));
    	if(ls != null && CollectionUtils.isNotEmpty(ls.getLeaveSummaryRows())) {
	    	BigDecimal oldLeaveAmount = null;
	    	boolean earnCodeChanged = false;
    		if(updatedLeaveBlock != null) {
    			if(!updatedLeaveBlock.getEarnCode().equals(selectedEarnCode)) {
    				earnCodeChanged = true;
    			}
    			if(!updatedLeaveBlock.getLeaveAmount().equals(leaveAmount)) {
    				oldLeaveAmount = updatedLeaveBlock.getLeaveAmount();
    			}
    		}
    		Date aDate = TKUtils.formatDateString(leaveEndDateString);
	    	EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, aDate);
	    	if(earnCodeObj != null) {
	    		AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), aDate);
	    		if(accrualCategory != null) {
	    			List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
	    			for(LeaveSummaryRow aRow : rows) {
	    				if(aRow.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    					//Does employee have overrides in place?
	    					List<EmployeeOverride> employeeOverrides = TkServiceLocator.getEmployeeOverrideService().getEmployeeOverrides(principalId,TKUtils.formatDateString(leaveEndDateString));
	    					String leavePlan = accrualCategory.getLeavePlan();
	    					BigDecimal maxUsage = aRow.getUsageLimit();
	    					for(EmployeeOverride eo : employeeOverrides) {
	    						if(eo.getLeavePlan().equals(leavePlan) && eo.getAccrualCategory().equals(aRow.getAccrualCategory())) {
	    							if(eo.getOverrideType().equals("MU") && eo.isActive()) {
	    								if(eo.getOverrideValue()!=null && !eo.getOverrideValue().equals(""))
	    									maxUsage = new BigDecimal(eo.getOverrideValue());
	    								else // no limit flag
	    									maxUsage = null;
	    							}
	    						}
	    					}
	    					BigDecimal ytdUsage = aRow.getYtdApprovedUsage();
	    					BigDecimal pendingLeaveBalance = aRow.getPendingLeaveRequests();
	    					BigDecimal desiredUsage = new BigDecimal(0);
	    					if(pendingLeaveBalance!=null) {
	    						if(oldLeaveAmount!=null) {
	    							
	    							if(!earnCodeChanged || 
	    									updatedLeaveBlock.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
   			    						pendingLeaveBalance = pendingLeaveBalance.subtract(oldLeaveAmount.abs());
	    							}
	    						}
 
    							desiredUsage = desiredUsage.add(pendingLeaveBalance);
	    					}
   							
	    					desiredUsage = desiredUsage.add(leaveAmount.multiply(new BigDecimal(daysSpan+1)));

   							if(ytdUsage!=null) {
    							desiredUsage = desiredUsage.add(ytdUsage);
    						}
	    					if(maxUsage!=null) {
		    					if(desiredUsage.compareTo(maxUsage) > 0 ) {
		    						errors.add("This leave request would exceed the usage limit for " + aRow.getAccrualCategory());
		    					}
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	return errors;
    }
	//End KPME-1263

    // get warning messages associated with earn codes of leave blocks
    public static List<String> getWarningMessagesForLeaveBlocks(List<LeaveBlock> leaveBlocks) {
        List<String> warningMessages = new ArrayList<String>();
        Set<String> aSet = new HashSet<String>();
        if (CollectionUtils.isNotEmpty(leaveBlocks)) {
            for(LeaveBlock lb : leaveBlocks) {
                EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(lb.getEarnCode(), lb.getLeaveDate());
                if(ec != null) {
                    EarnCodeGroup eg = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroupForEarnCode(lb.getEarnCode(), lb.getLeaveDate());
                    if(eg != null && !StringUtils.isEmpty(eg.getWarningText())) {
                        aSet.add(eg.getWarningText());
                    }
                }

                if (StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER)) {
                	if(!StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, lb.getRequestStatus())
                			&& !StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, lb.getRequestStatus())) {
                		aSet.add("A pending balance transfer exists on this calendar. It must be finalized before this calendar can be approved.");
                	}
                	else if(StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, lb.getRequestStatus())) {
                		PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(lb.getPrincipalId(), lb.getLeaveDate());
                		AccrualCategory accrualCat = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(lb.getAccrualCategory(), lb.getLeaveDate());
                		AccrualCategoryRule aRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCat, TKUtils.getCurrentDate(), pha.getServiceDate());
                		if(StringUtils.equals(aRule.getActionAtMaxBalance(),LMConstants.ACTION_AT_MAX_BAL.LOSE) &&
                				lb.getLeaveAmount().signum() == -1)
                			aSet.add("A max balance action that forfeited accrued leave occurred on this calendar");
                		else
                			aSet.add("A max balance action for transfer occurred on this calendar.");
                	}
                }
                if (StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT)) {
                	if(!StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED, lb.getRequestStatus())
                			&& !StringUtils.equals(LMConstants.REQUEST_STATUS.DISAPPROVED, lb.getRequestStatus())) {
                		aSet.add("A pending payout exists on this leave calendar. It must be finalized before this calendar can be approved.");
                	}
                	else if(StringUtils.equals(LMConstants.REQUEST_STATUS.APPROVED,lb.getRequestStatus())) {
                		aSet.add("A max balance action for payout occurred on this calendar");
                	}
                }
            }
        }
        warningMessages.addAll(aSet);
        return warningMessages;
    }

    public static List<String> validateAvailableLeaveBalance(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
			updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lcf.getLeaveBlockId());
    	}
    	return validateAvailableLeaveBalance(lcf.getLeaveSummary(), lcf.getSelectedEarnCode(), lcf.getStartDate(), lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }

    public static List<String> validateAvailableLeaveBalance(LeaveSummary ls, String earnCode, String leaveStartDateString, String leaveEndDateString,
    		BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();
    	boolean earnCodeChanged = false;
    	BigDecimal oldAmount = null;
    	if(ls != null && CollectionUtils.isNotEmpty(ls.getLeaveSummaryRows())) {
    		if(updatedLeaveBlock != null) {
    			if(!updatedLeaveBlock.getEarnCode().equals(earnCode)) {
    				earnCodeChanged = true;
    			}
    			if(!updatedLeaveBlock.getLeaveAmount().equals(leaveAmount)) {
    				oldAmount = updatedLeaveBlock.getLeaveAmount();
    			}
    		}
			Date startDate = TKUtils.formatDateString(leaveStartDateString);
			Date endDate = TKUtils.formatDateString(leaveEndDateString);
			long daysSpan = TKUtils.getDaysBetween(startDate,endDate);
	    	EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, endDate);
	    	if(earnCodeObj != null && earnCodeObj.getAllowNegativeAccrualBalance().equals("N")) {
	    		AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), endDate);
	    		if(accrualCategory != null) {
	    			List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
	    			for(LeaveSummaryRow aRow : rows) {
	    				if(aRow.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    					BigDecimal availableBalance = aRow.getLeaveBalance();

							if(oldAmount!=null) {

		    					if(!earnCodeChanged ||
		    							updatedLeaveBlock.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
									availableBalance = availableBalance.add(oldAmount.abs());
		    					}

							}
							//multiply by days in span in case the user has also edited the start/end dates.
	    					BigDecimal desiredUsage = leaveAmount.multiply(new BigDecimal(daysSpan+1));

	    					if(desiredUsage.compareTo(availableBalance) >  0 ) {
	    						errors.add("Requested leave amount is greater than available leave balance.");
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	
    	return errors;
    }
    
    // KPME-2010
    public static List<String> validateSpanningWeeks(LeaveCalendarWSForm lcf) {
    	boolean spanningWeeks = lcf.getSpanningWeeks().equalsIgnoreCase("y");
    	DateTime startTemp = new DateTime(TKUtils.convertDateStringToTimestamp(lcf.getStartDate()).getTime());
        DateTime endTemp = new DateTime(TKUtils.convertDateStringToTimestamp(lcf.getEndDate()).getTime());
    	
        List<String> errors = new ArrayList<String>();
    	boolean valid = true;
    	while ((startTemp.isBefore(endTemp) || startTemp.isEqual(endTemp)) && valid) {
           	if (!spanningWeeks && 
        		(startTemp.getDayOfWeek() == DateTimeConstants.SATURDAY || startTemp.getDayOfWeek() == DateTimeConstants.SUNDAY)) {
        		valid = false;
        	}
        	startTemp = startTemp.plusDays(1);
        }
        if (!valid) {
        	errors.add("Weekend day is selected, but include weekends checkbox is not checked");
        }
    	return errors;
    }
    
}
