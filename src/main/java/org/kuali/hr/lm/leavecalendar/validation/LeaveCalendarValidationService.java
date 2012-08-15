package org.kuali.hr.lm.leavecalendar.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

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
    
    public static List<String> validateAvailableLeaveBalance(LeaveCalendarWSForm lcf) {
    	LeaveBlock updatedLeaveBlock = null;
    	if(lcf.getLeaveBlockId() != null) {
			updatedLeaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(new Long(lcf.getLeaveBlockId()));
    	}
    	return validateAvailableLeaveBalance(lcf.getLeaveSummary(), lcf.getSelectedEarnCode(), lcf.getEndDate(), lcf.getLeaveAmount(), updatedLeaveBlock);
    }
    
    public static List<String> validateAvailableLeaveBalance(LeaveSummary ls, String earnCodeId, String leaveEndDateString,
    		BigDecimal leaveAmount, LeaveBlock updatedLeaveBlock) {
    	List<String> errors = new ArrayList<String>();
    	String oldEarnCodeId = null;
    	BigDecimal oldAmount = null;
    	if(ls != null && CollectionUtils.isNotEmpty(ls.getLeaveSummaryRows())) {
    		if(updatedLeaveBlock != null) {
    			if(!updatedLeaveBlock.getEarnCodeId().equals(earnCodeId)) {
    				oldEarnCodeId = updatedLeaveBlock.getEarnCodeId();
    			}
    			if(!updatedLeaveBlock.getLeaveAmount().equals(leaveAmount)) {
    				oldAmount = updatedLeaveBlock.getLeaveAmount();
    			}
    		}
	    	EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCodeById(earnCodeId);
	    	if(earnCodeObj != null && earnCodeObj.getAllowNegativeAccrualBalance().equals("N")) {
	    		Date aDate = TKUtils.formatDateString(leaveEndDateString);
	    		AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCodeObj.getAccrualCategory(), aDate);
	    		if(accrualCategory != null) {
	    			List<LeaveSummaryRow> rows = ls.getLeaveSummaryRows();
	    			for(LeaveSummaryRow aRow : rows) {
	    				if(aRow.getAccrualCategory().equals(accrualCategory.getAccrualCategory())) {
	    					BigDecimal availableUsage = aRow.getPendingAvailableUsage();
	    					if(oldEarnCodeId == null && oldAmount != null) {
	    						availableUsage = availableUsage.subtract(oldAmount);
	    					}
	    					if(leaveAmount.compareTo(availableUsage) > 0 ) {
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
