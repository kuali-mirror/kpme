package org.kuali.hr.lm.leavedonation.validation;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class LeaveDonationValidation extends MaintenanceDocumentRuleBase {
	public static final String DONOR = "donor";
	public static final String RECEPIENT = "recepient";

	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		if (effectiveDate != null
				&& !ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}

	boolean validatePrincipal(String principalId, String forPerson) {
		boolean valid = true;
		if (!ValidationUtils.validatePrincipalId(principalId)) {
			this.putFieldError(
					forPerson.equals(LeaveDonationValidation.DONOR) ? "donorsPrincipalID"
							: "recipientsPrincipalID", "error.existence",
					"principal Id '" + principalId + "'");
			valid = false;
		}
		return valid;
	}

	boolean validateAccrualCategory(String accrualCategory, Date asOfDate,
			String forPerson) {
		boolean valid = true;
		if (!ValidationUtils.validateAccCategory(accrualCategory, asOfDate)) {
			this.putFieldError(
					forPerson.equals(LeaveDonationValidation.DONOR) ? "donatedAccrualCategory"
							: "recipientsAccrualCategory", "error.leavePlan.mismatch",
					"accrualCategory '" + accrualCategory + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateAccrualCategory(String accrualCategory, Date asOfDate,
			String forPerson, String principalId) {
		boolean valid = true;
		if (!ValidationUtils.validateAccCategory(accrualCategory, principalId, asOfDate)) {
			String[] myErrorsArgs={"accrualCategory '" + accrualCategory + "'", forPerson};
			this.putFieldError(
					forPerson.equals(LeaveDonationValidation.DONOR) ? "donatedAccrualCategory"
							: "recipientsAccrualCategory", "error.leavePlan.mismatch",
							myErrorsArgs);
			valid = false;
			
		}
		return valid;
	}

	boolean validateLeaveCode(String principalAC, String formLeaveCode, String forPerson, Date asOfDate) {
		boolean valid = true;

		LeaveCode testLeaveCode = TkServiceLocator.getLeaveCodeService().getLeaveCode(formLeaveCode, asOfDate);
		String formLeaveCodeAC = "NullAccrualCategoryPlaceholder";
		if (testLeaveCode != null && testLeaveCode.getAccrualCategory() != null) {
			formLeaveCodeAC = testLeaveCode.getAccrualCategory();
		}

		if (!StringUtils.equalsIgnoreCase(principalAC, formLeaveCodeAC)) {
			this.putFieldError(forPerson.equals(LeaveDonationValidation.DONOR) ? "donatedLeaveCode"
					: "recipientsLeaveCode", "error.codeCategory.mismatch", forPerson);
			valid = false;
		}
		return valid;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Leave Donation");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof LeaveDonation) {
			LeaveDonation leaveDonation = (LeaveDonation) pbo;
			if (leaveDonation != null) {
				valid = true;
				//valid &= this.validateEffectiveDate(leaveDonation.getEffectiveDate()); // KPME-1207, effectiveDate can be past, current or future
				if(leaveDonation.getDonatedAccrualCategory() != null) {
						valid &= this.validateAccrualCategory(
						leaveDonation.getDonatedAccrualCategory(),
						leaveDonation.getEffectiveDate(),
						LeaveDonationValidation.DONOR, leaveDonation.getDonorsPrincipalID());
				}
				if(leaveDonation.getRecipientsAccrualCategory() != null) {
						valid &= this.validateAccrualCategory(
						leaveDonation.getRecipientsAccrualCategory(),
						leaveDonation.getEffectiveDate(),
						LeaveDonationValidation.RECEPIENT, leaveDonation.getRecipientsPrincipalID());
				}
				if(leaveDonation.getDonorsPrincipalID() != null) {
						valid &= this.validatePrincipal(
						leaveDonation.getDonorsPrincipalID(),
						LeaveDonationValidation.DONOR);
				}
				if(leaveDonation.getRecipientsPrincipalID() != null){
						valid &= this.validatePrincipal(
						leaveDonation.getRecipientsPrincipalID(),
						LeaveDonationValidation.RECEPIENT);
				}
				if(leaveDonation.getDonatedAccrualCategory() != null) {
						valid &= this.validateLeaveCode(
						leaveDonation.getDonatedAccrualCategory(),
						leaveDonation.getDonatedLeaveCode(),
						LeaveDonationValidation.DONOR, leaveDonation.getEffectiveDate());
				}
				if(leaveDonation.getRecipientsAccrualCategory() != null) {
						valid &= this.validateLeaveCode(
						leaveDonation.getRecipientsAccrualCategory(),
						leaveDonation.getRecipientsLeaveCode(),
						LeaveDonationValidation.RECEPIENT, leaveDonation.getEffectiveDate());
				}
			}
		}
		return valid;
	}
}
