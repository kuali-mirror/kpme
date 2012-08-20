package org.kuali.hr.lm.leavedonation.validation;

import java.math.BigDecimal;
import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

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

	boolean validateEarnCode(String principalAC, String formEarnCode, String forPerson, Date asOfDate) {
		boolean valid = true;

		EarnCode testEarnCode = TkServiceLocator.getEarnCodeService().getEarnCode(formEarnCode, asOfDate);
//		LeaveCode testLeaveCode = TkServiceLocator.getLeaveCodeService().getLeaveCode(formEarnCode, asOfDate);
		String formEarnCodeAC = "NullAccrualCategoryPlaceholder";
		if (testEarnCode != null && testEarnCode.getAccrualCategory() != null) {
			formEarnCodeAC = testEarnCode.getAccrualCategory();
		}

		if (!StringUtils.equalsIgnoreCase(principalAC, formEarnCodeAC)) {
			this.putFieldError(forPerson.equals(LeaveDonationValidation.DONOR) ? "donatedEarnCode"
					: "recipientsEarnCode", "error.codeCategory.mismatch", forPerson);
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
		LOG.debug("entering custom validation for Leave Donation");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
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
						valid &= this.validateEarnCode(
						leaveDonation.getDonatedAccrualCategory(),
						leaveDonation.getDonatedEarnCode(),
						LeaveDonationValidation.DONOR, leaveDonation.getEffectiveDate());
				}
				if(leaveDonation.getRecipientsAccrualCategory() != null) {
						valid &= this.validateEarnCode(
						leaveDonation.getRecipientsAccrualCategory(),
						leaveDonation.getRecipientsEarnCode(),
						LeaveDonationValidation.RECEPIENT, leaveDonation.getEffectiveDate());
				}
				if(leaveDonation.getAmountDonated() != null && leaveDonation.getDonatedEarnCode() != null) {
					valid &= this.validateFraction(
					leaveDonation.getDonatedEarnCode(), 
					leaveDonation.getAmountDonated(), 
					leaveDonation.getEffectiveDate(),
					"amountDonated");
				}
				if(leaveDonation.getAmountReceived() != null && leaveDonation.getRecipientsEarnCode() != null) {
					valid &= this.validateFraction(
					leaveDonation.getRecipientsEarnCode(), 
					leaveDonation.getAmountReceived(), 
					leaveDonation.getEffectiveDate(),
					"amountReceived");
				}
			}
		}
		return valid;
	}
}
