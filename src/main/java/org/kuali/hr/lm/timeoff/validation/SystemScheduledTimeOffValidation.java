package org.kuali.hr.lm.timeoff.validation;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
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
	
	boolean validateExpirationDate(Date expirationDate) {
		boolean valid = true;
		if (expirationDate != null && !ValidationUtils.validateOneYearFutureDate(expirationDate)) {
			this.putFieldError("expirationDate", "error.date.exceed.year", "Expiration Date");
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
	
	boolean validateTransfertoLeaveCode(String transfertoLeaveCode) {
		boolean valid = true;
		if (StringUtils.isEmpty(transfertoLeaveCode)) {
			this.putFieldError("transfertoLeaveCode", "error.required", "Transfer to Leave Code");
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
	
	boolean validateLeaveCode(String leaveCode, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateLeaveCode(leaveCode, asOfDate)) {
			this.putFieldError("leaveCode", "error.existence", "leaveCode '"
					+ leaveCode + "'");
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
				valid &= this.validateEffectiveDate(sysSchTimeOff.getEffectiveDate());
				valid &= this.validateExpirationDate(sysSchTimeOff.getExpirationDate());
				valid &= this.validateAccruedDate(sysSchTimeOff.getAccruedDate());
				valid &= this.validateScheduledTimeOffDate(sysSchTimeOff.getScheduledTimeOffDate());
				if(StringUtils.equals("T", sysSchTimeOff.getUnusedTime())){
					valid &= this.validateTransfertoLeaveCode(sysSchTimeOff.getTransfertoLeaveCode());
					valid &= this.validateTransferConversionFactor(sysSchTimeOff.getTransferConversionFactor());
				}
				valid &= this.validateLocation(sysSchTimeOff.getLocation());
				valid &= this.validateLeaveCode(sysSchTimeOff.getLeaveCode(), sysSchTimeOff.getEffectiveDate());
			}
		}
		
		return valid;
	}
}
