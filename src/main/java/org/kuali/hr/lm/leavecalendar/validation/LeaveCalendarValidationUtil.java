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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

import com.google.common.base.Predicate;

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
	    					BigDecimal maxUsage = aRow.getUsageLimit();
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

    public static List<String> getAbsentLeaveWarningMessages(List<LeaveBlock> leaveBlocks) {
        List<String> warningMessages = new ArrayList<String>();
        Set<String> aSet = new HashSet<String>();
        if (CollectionUtils.isNotEmpty(leaveBlocks)) {
            for(LeaveBlock lb : leaveBlocks) {
                EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(lb.getEarnCode(), lb.getLeaveDate());
                if(ec != null && ec.getFmla().equals("Y")) {
                    EarnCodeGroup eg = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroupForEarnCode(lb.getEarnCode(), lb.getLeaveDate());
                    if(eg != null && !StringUtils.isEmpty(eg.getWarningText())) {
                        aSet.add(eg.getWarningText());
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
}