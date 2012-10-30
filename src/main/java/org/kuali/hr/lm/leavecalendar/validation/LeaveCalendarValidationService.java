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
package org.kuali.hr.lm.leavecalendar.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.service.AccrualCategoryRuleService;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.lm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

import com.google.common.base.Predicate;

public class LeaveCalendarValidationService {

    /**
     * Convenience method for handling validation directly from the form object.
     * @param tdaf The populated form.
     *
     * @return A list of error strings.
     */
    public static List<String> validateLaveEntryDetails(LeaveCalendarWSForm lcf) {
        return LeaveCalendarValidationService.validateLeaveEntryDetails(
        		lcf.getStartDate(), lcf.getEndDate(), lcf.getSpanningWeeks().equalsIgnoreCase("y")
        );
    }

    public static List<String> validateLeaveEntryDetails(String startDateS, String endDateS, boolean spanningWeeks) {
        List<String> errors = new ArrayList<String>();
        DateTime startTemp = new DateTime(TKUtils.convertDateStringToTimestamp(startDateS, "00:00:00"));
        DateTime endTemp = new DateTime(TKUtils.convertDateStringToTimestamp(endDateS, "00:00:00"));
                
        // KPME-1446 
        // -------------------------------
        // check if there is a weekend day when the include weekends flag is checked
        //--------------------------------
        errors.addAll(validateSpanningWeeks(spanningWeeks, startTemp, endTemp));
        if (errors.size() > 0) return errors;

        return errors;
    }

    public static List<String> validateSpanningWeeks(boolean spanningWeeks, DateTime startTemp, DateTime endTemp) {
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
    
    //begin KPME-1263
    public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
    		updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lcf.getLeaveBlockId());
    	}
    	return validateLeaveAccrualRuleMaxUsage(lcf.getLeaveSummary(), lcf.getSelectedEarnCode(), lcf.getStartDate(),
    			lcf.getEndDate(), lcf.getLeaveAmount(), lcf.getPrincipalId(), updatedLeaveBlock);
    }

	public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveSummary ls, String selectedEarnCode, String leaveStartDateString,
			String leaveEndDateString, BigDecimal leaveAmount, String principalId, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();
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
	    					BigDecimal adjustedPendingLeaveBalance = null;
	    					if(pendingLeaveBalance!=null) {
	    						if(oldLeaveAmount!=null) {
	    							BigDecimal difference = new BigDecimal(0);
	    							// when adding a new leave block, users enter a positive value, when editing (some) existing blocks, value populates negative.
	    							// other accrual categories/earn codes show a positive value for leave used. This may not be needed
	    							// but is added as a security measure.
	    							if(oldLeaveAmount.signum() <= 0)
	    								difference = difference.add(leaveAmount.add(oldLeaveAmount));
	    							else
	    								difference = difference.add(leaveAmount.subtract(oldLeaveAmount));

	    							if(!earnCodeChanged) {
	    								//Adjust the pending leave balance by the difference. This takes into account leaveAmount.
   			    						adjustedPendingLeaveBalance = pendingLeaveBalance.add(difference.multiply(new BigDecimal(daysSpan+1)));
	    							}
	    							else if(updatedLeaveBlock.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    								//Its possible the earn code has changed, but is still related to the same accrual category.
   			    						adjustedPendingLeaveBalance = pendingLeaveBalance.add(difference.multiply(new BigDecimal(daysSpan+1)));
	    							}
	    							//otherwise there shouldn't be an adjustment to pending leave balance.
	    						}
	    						//what if the user changes earn code to one with the same accrual category, but does not
	    						//change/update the leave amount?? This may pop up as a bug. In which case, the leave amount is
	    						//already factored into the pending balance, yet adjustedPendingLeaveBalance remains null...
	    						//so the conditional below would succeed, and leaveAmount would be factored in a second time,
	    						//producing a max usage limit error, when in fact the employee is below that limit.

	    						if(adjustedPendingLeaveBalance!=null)
	    							desiredUsage = desiredUsage.add(adjustedPendingLeaveBalance);
	    						else
	    							desiredUsage = desiredUsage.add(pendingLeaveBalance);
	    					}
	    					if(adjustedPendingLeaveBalance==null) //leaveAmount has not been factored in.
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
    
    public static List<String> validateAvailableLeaveBalance(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
			updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lcf.getLeaveBlockId());
    	}
    	return validateAvailableLeaveBalance(lcf.getLeaveSummary(), lcf.getSelectedEarnCode(), lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }
    
    public static List<String> validateAvailableLeaveBalance(LeaveSummary ls, String earnCode, String leaveEndDateString,
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
    		Date aDate = TKUtils.formatDateString(leaveEndDateString);
	    	EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, aDate);
	    	if(earnCodeObj != null && earnCodeObj.getAllowNegativeAccrualBalance().equals("N")) {
	    		AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), aDate);
	    		if(accrualCategory != null) {
	    			List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
	    			for(LeaveSummaryRow aRow : rows) {
	    				if(aRow.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    					BigDecimal availableBalance = aRow.getLeaveBalance();
	    					if(!earnCodeChanged && oldAmount != null) {
	    						availableBalance = availableBalance.subtract(oldAmount);
	    					}
	    					if(leaveAmount.compareTo(availableBalance) > 0 ) {
	    						errors.add("Requested leave amount is greater than pending available usage.");
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	
    	return errors;
    }
}
