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
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.service.AccrualCategoryRuleService;
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
    			lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }

	public static List<String> validateLeaveAccrualRuleMaxUsage(LeaveSummary ls, String selectedEarnCode, String leaveStartDateString,
			String leaveEndDateString, BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();

    	if(ls != null && CollectionUtils.isNotEmpty(ls.getLeaveSummaryRows())) {
    		Date aDate = TKUtils.formatDateString(leaveEndDateString);
	    	EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, aDate);
    	
	    	if(earnCodeObj != null) {
	    		AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), aDate);
	    		if(accrualCategory != null) {
	    			List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
	    			for(LeaveSummaryRow aRow : rows) {
	    				if(aRow.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    					BigDecimal maxUsage = aRow.getUsageLimit();
	    					BigDecimal ytdUsage = aRow.getYtdApprovedUsage();
	    					BigDecimal pendingLeaveBalance = aRow.getPendingLeaveRequests();
	    					BigDecimal desiredUsage = new BigDecimal(0);
	    					if(pendingLeaveBalance!=null) {
	    						desiredUsage = ytdUsage.add(pendingLeaveBalance);
	    					}
	    					for(int i=0; i <= TKUtils.getDaysBetween(TKUtils.formatDateString(leaveStartDateString), TKUtils.formatDateString(leaveEndDateString)); i++) {
		    					desiredUsage = desiredUsage.add(leaveAmount);
	    					}
	    					if(maxUsage!=null) {
		    					if(desiredUsage.compareTo(maxUsage) > 0 ) {
		    						errors.add("This leave request would exceed the usage limit for " + aRow.getAccrualCategory());
		    					}
	    					}
	    					// no usage limit??
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
