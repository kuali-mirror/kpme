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
package org.kuali.hr.lm.timeoff.validation;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class SystemScheduledTimeOffValidation extends MaintenanceDocumentRuleBase {	 
	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		if (effectiveDate != null && !ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateExpirationDate(Date expirationDate, Date accruedDate) {
		boolean valid = true;
		if (accruedDate != null && expirationDate != null && !ValidationUtils.validateOneYearFutureDateFromAccrualDate(expirationDate, accruedDate)) {
			this.putFieldError("expirationDate", "error.date.exceed.year.accruedDate", "Expiration Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateAccruedDate(Date accruedDate) {
		boolean valid = true;
		if (accruedDate != null && !ValidationUtils.validateFutureDate(accruedDate)) {
			this.putFieldError("accruedDate", "error.future.date", "Accrued Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateLocation(String location) {
		boolean valid = true;
		if(!StringUtils.isEmpty(location) && location.equals(TkConstants.WILDCARD_CHARACTER)) {
			return true;
		}
		if (!ValidationUtils.validateLocation(location, null)) {
			this.putFieldError("location", "error.existence", "location '"
					+ location + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateScheduledTimeOffDate(Date scheduledTimeOffDate) {
		boolean valid = true;
		if (scheduledTimeOffDate!= null && !ValidationUtils.validateFutureDate(scheduledTimeOffDate)) {
			this.putFieldError("scheduledTimeOffDate", "error.future.date", "Scheduled Time Off Date");
			valid = false;
		}
		return valid;
	}
	
	boolean validateUnusedTimeForScheduledTimeOffDate(Date scheduledTimeOffDate, String unusedTime) {
		boolean valid = true;
		if (scheduledTimeOffDate == null && (StringUtils.isEmpty(unusedTime) || StringUtils.equals("T", unusedTime) || StringUtils.equals("NUTA", unusedTime))) {
			this.putFieldError("unusedTime", "error.unusedtime.bank.required", "Unused Time");			
			valid = false;
		}		
		return valid;
	}
		
	boolean validateTransfertoEarnCode(String transfertoEarnCode) {
		boolean valid = true;
		if (StringUtils.isEmpty(transfertoEarnCode)) {
			this.putFieldError("transfertoEarnCode", "error.required", "Transfer to Earn Code");
			valid = false;
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
	
	boolean validateEarnCode(String earnCode, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateEarnCode(earnCode, asOfDate)) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ earnCode + "'");
			valid = false;
		}
		return valid;
	}

    private boolean validateFraction(String earnCode, BigDecimal amount, Date asOfDate, String fieldName) {
        boolean valid = true;
        if (!ValidationUtils.validateEarnCodeFraction(earnCode, amount, asOfDate)) {
            EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
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
				valid &= this.validateEffectiveDate(sysSchTimeOff.getEffectiveDate());
				valid &= this.validateAccruedDate(sysSchTimeOff.getAccruedDate());
				valid &= this.validateExpirationDate(sysSchTimeOff.getExpirationDate(), sysSchTimeOff.getAccruedDate());
				valid &= this.validateScheduledTimeOffDate(sysSchTimeOff.getScheduledTimeOffDate());
                valid &= this.validateFraction(sysSchTimeOff.getEarnCode(),sysSchTimeOff.getAmountofTime(),sysSchTimeOff.getEffectiveDate(),"amountofTime");
				valid &= this.validateUnusedTimeForScheduledTimeOffDate(sysSchTimeOff.getScheduledTimeOffDate(), sysSchTimeOff.getUnusedTime());
				valid &= this.validateLocation(sysSchTimeOff.getLocation());
				valid &= this.validateEarnCode(sysSchTimeOff.getEarnCode(), sysSchTimeOff.getEffectiveDate());
			}
		}
		
		return valid;
	}
}
