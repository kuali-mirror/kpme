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
package org.kuali.kpme.tklm.leave.timeoff.validation;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class SystemScheduledTimeOffValidation extends MaintenanceDocumentRuleBase {	 
	boolean validateEffectiveDate(LocalDate effectiveDate) {
		boolean valid = true;
		if (effectiveDate != null && !ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}

	boolean validateAccruedDate(LocalDate accruedDate) {
		boolean valid = true;
		if (accruedDate != null && !ValidationUtils.validateFutureDate(accruedDate)) {
			this.putFieldError("accruedDate", "error.future.date", "Accrued Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateLocation(String location, LocalDate asOfDate) {
		boolean valid = true;
		if(!StringUtils.isEmpty(location) && location.equals(HrConstants.WILDCARD_CHARACTER)) {
			return true;
		}
		if (!ValidationUtils.validateLocation(location, asOfDate)) {
			this.putFieldError("location", "error.existence", "location '"
					+ location + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateScheduledTimeOffDate(LocalDate scheduledTimeOffDate) {
		boolean valid = true;
		if (scheduledTimeOffDate!= null && !ValidationUtils.validateFutureDate(scheduledTimeOffDate)) {
			this.putFieldError("scheduledTimeOffDate", "error.future.date", "Scheduled Time Off Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateUnusedTimeForScheduledTimeOffDate(LocalDate scheduledTimeOffDate, String unusedTime) {
		boolean valid = true;
		if (scheduledTimeOffDate == null && (StringUtils.isEmpty(unusedTime) || StringUtils.equals("T", unusedTime) || StringUtils.equals("NUTA", unusedTime))) {
			this.putFieldError("scheduledTimeOffDate", "error.unusedtime.bank.required", LMConstants.UNUSED_TIME_MAP.get(unusedTime));
			valid = false;
		}		
		return valid;
	}
	
	//if no unsued time allowed is selected make sure Accrued date and Schedule Time off date are equal
	//if not throw an error
	boolean validateNoUnusedTimeAllowed(LocalDate scheduledTimeOffDate, LocalDate accruedDate, String unusedTime) {
		boolean valid = true;
		if(scheduledTimeOffDate != null) {
			if (scheduledTimeOffDate.equals(accruedDate) == false) {
				this.putFieldError("unusedTime", "error.nounusedtimeallowed.selected", "Unused Time");			
				valid = false;
			}
		}
		return valid;
		
	}
		
	boolean validateTransfertoEarnCode(String transfertoEarnCode, LocalDate localDate) {
		boolean valid = true;
		if (StringUtils.isEmpty(transfertoEarnCode)) {
			this.putFieldError("transfertoEarnCode", "error.required", "Transfer to Earn Code");
			valid = false;
		}
		else {
			valid &= this.validateEarnCode(transfertoEarnCode, localDate);
		}
		return valid;
	}
	
	boolean validatePremiumEarnCode(String premiumEarnCode, String premiumHoliday, LocalDate localDate) {
		boolean valid = true;
		if (!StringUtils.isEmpty(premiumEarnCode)){
			if(premiumHoliday.equalsIgnoreCase("N")) {
				this.putFieldError("premiumHoliday", "error.SSTO,premiumHoliday", "Premium Holiday");
				valid = false;
			} 
			if(valid) {
				if (!ValidationUtils.validateEarnCode(premiumEarnCode, localDate)) {
					this.putFieldError("premiumEarnCode", "error.existence", "earnCode '"
							+ premiumEarnCode + "'");
					valid = false;
				}
			}
			
		}
		return valid;
	}
	
	boolean validateTransferConversionFactor(BigDecimal transferConversionFactor) {
		boolean valid = true;
		if (transferConversionFactor == null) {
			this.putFieldError("transferConversionFactor", "error.required", "Transfer Conversion Factor");
			valid = false;
		}
		return valid;
	}
	
	boolean validateEarnCode(String earnCode, LocalDate asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateEarnCode(earnCode, asOfDate)) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ earnCode + "'");
			valid = false;
		}
		return valid;
	}

    private boolean validateFraction(String earnCode, BigDecimal amount, LocalDate asOfDate, String fieldName) {
        boolean valid = true;
        if (!ValidationUtils.validateEarnCodeFraction(earnCode, amount, asOfDate)) {
            EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
            if(ec != null && ec.getFractionalTimeAllowed() != null) {
                BigDecimal fracAllowed = new BigDecimal(ec.getFractionalTimeAllowed());
                String[] parameters = new String[2];
                parameters[0] = earnCode;
                parameters[1] = Integer.toString(fracAllowed.scale());
                this.putFieldError(fieldName, "error.amount.fraction", parameters);
                valid = false;
            }
        }
        return valid;
    }
    
	private boolean validateUnusedTime(SystemScheduledTimeOff sysSchTimeOff) {
		boolean valid = true;
		if(StringUtils.isNotBlank(sysSchTimeOff.getUnusedTime())) {
			valid &= this.validateUnusedTimeForScheduledTimeOffDate(sysSchTimeOff.getScheduledTimeOffLocalDate(), sysSchTimeOff.getUnusedTime());
			if(StringUtils.equals(sysSchTimeOff.getUnusedTime(),LMConstants.UNUSED_TIME.TRANSFER)) {
				valid &= this.validateTransfertoEarnCode(sysSchTimeOff.getTransfertoEarnCode(), sysSchTimeOff.getEffectiveLocalDate());
				valid &= this.validateTransferConversionFactor(sysSchTimeOff.getTransferConversionFactor());
				if(valid) {
					// transfer to earn code is not blank, and exists.
					// TODO: xref earn code white[black] list? Should this earn code belong to the same leave plan?
				}
			}
			else if(StringUtils.equals(sysSchTimeOff.getUnusedTime(), LMConstants.UNUSED_TIME.BANK)) {
				//TODO: do "bank" validation
				// can there be a value specified for scheduled time off date, or must this field be left blank?
			}
			else if(StringUtils.equals(sysSchTimeOff.getUnusedTime(), LMConstants.UNUSED_TIME.NO_UNUSED)) {
				valid &= this.validateNoUnusedTimeAllowed(sysSchTimeOff.getScheduledTimeOffLocalDate(), sysSchTimeOff.getAccruedLocalDate(), sysSchTimeOff.getUnusedTime());
			}
		}
		else {
			this.putFieldError("unusedTime", "error.required", "Unused Time");
			valid = false;
		}
		return valid;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for SystemScheduledTimeOff");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof SystemScheduledTimeOff) {
			SystemScheduledTimeOff sysSchTimeOff = (SystemScheduledTimeOff) pbo;
			if (sysSchTimeOff != null) {
				valid = true;
				valid &= this.validateEffectiveDate(sysSchTimeOff.getEffectiveLocalDate());
				valid &= this.validateAccruedDate(sysSchTimeOff.getAccruedLocalDate());
				valid &= this.validateScheduledTimeOffDate(sysSchTimeOff.getScheduledTimeOffLocalDate());
                valid &= this.validateFraction(sysSchTimeOff.getEarnCode(),sysSchTimeOff.getAmountofTime(),sysSchTimeOff.getEffectiveLocalDate(),"amountofTime");
				//valid &= this.validateUnusedTimeForScheduledTimeOffDate(sysSchTimeOff.getScheduledTimeOffLocalDate(), sysSchTimeOff.getUnusedTime());
				valid &= this.validateUnusedTime(sysSchTimeOff);
				
				//valid &= this.validateNoUnusedTimeAllowed(sysSchTimeOff.getScheduledTimeOffLocalDate(),sysSchTimeOff.getAccruedLocalDate(),sysSchTimeOff.getUnusedTime());
				valid &= this.validateLocation(sysSchTimeOff.getLocation(), sysSchTimeOff.getEffectiveLocalDate());
				valid &= this.validateEarnCode(sysSchTimeOff.getEarnCode(), sysSchTimeOff.getEffectiveLocalDate());
				valid &= this.validatePremiumEarnCode(sysSchTimeOff.getPremiumEarnCode(), sysSchTimeOff.getPremiumHoliday(), sysSchTimeOff.getEffectiveLocalDate());
			}
		}
		
		return valid;
	}

}
