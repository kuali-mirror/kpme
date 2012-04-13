package org.kuali.hr.lm.accrual.validation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class AccrualCategoryValidation extends MaintenanceDocumentRuleBase {
	private static final String ADD_LINE_LOCATION = "add.accrualCategoryRules.";
	//need to use categoryHasRules flag for any tests that expect rules for the category
	//set in doesCategoryHaveRules() below see KPME JIRA 1352
	private boolean categoryHasRules = true;
	
	boolean doesCategoryHaveRules(AccrualCategory accrualCategory) {
		boolean valid = true;

		List<AccrualCategoryRule> accrualCategoryRules = accrualCategory.getAccrualCategoryRules();
		if (accrualCategoryRules != null) {
			
			if (StringUtils.isNotBlank(accrualCategory.getHasRules())){
			
				if (accrualCategory.getHasRules().equals("N") && accrualCategoryRules.size() == 0) {
					categoryHasRules = false; //user does not want a rule for this category
				}else if (accrualCategory.getHasRules().equals("N") && accrualCategoryRules.size() > 0) {
					Integer count = accrualCategoryRules.size();
					this.putGlobalError("error.accrualCategoryRule.notExpected",count.toString());
					valid = false;
				}
				
				if (accrualCategory.getHasRules().equals("Y") && accrualCategoryRules.size() > 0) {
					//normal case rules expected by other validations on this page
				}else if (accrualCategory.getHasRules().equals("Y")&& accrualCategoryRules.size() == 0) {
					this.putGlobalError("error.accrualCategoryRule.required");
					valid = false;
				}
			}
		}
		return valid;
	}
	
	boolean validateMaxBalAccCat(String maxBalanceTransferAccCat, String fieldPrefix) {
		boolean valid = true;
		if (StringUtils.isEmpty(maxBalanceTransferAccCat)) {
				this.putFieldError(fieldPrefix + "maxBalanceTransferToAccrualCategory", "error.required", "Max Balance Transfer To Accrual Category");
				valid = false;
		}
		return valid;
	}
	
	boolean validateMaxBalConvFact(BigDecimal maxBalanceTransferConvFactor, String fieldPrefix) {
		boolean valid = true;
		if (maxBalanceTransferConvFactor==null) {
			this.putFieldError(fieldPrefix + "maxBalanceTransferConversionFactor", "error.required", "Max Balance Transfer ConversionFactor");
			valid = false;
		}
		return valid;
	}
	
	boolean validateMaxBalTransferAmount(Long maxBalanceTransferAccCat, String fieldPrefix) {
		boolean valid = true;
		if (maxBalanceTransferAccCat==null) {
			this.putFieldError(fieldPrefix + "maxTransferAmount", "error.required", "Max Transfer Amount");
			valid = false;
		}
		return valid;
	}
	
	boolean validateMaxPayoutAmount(Long maxPayoutAmount, String fieldPrefix) {
		boolean valid = true;
		if (maxPayoutAmount==null) {
			this.putFieldError(fieldPrefix + "maxPayoutAmount", "error.required", "Max Payout Amount");
			valid = false;
		}
		return valid;
	}
	
	boolean validateMaxPayoutLeaveCode(String maxPayoutLeaveCode, String fieldPrefix) {
		boolean valid = true;
		if (maxPayoutLeaveCode==null) {
			this.putFieldError(fieldPrefix + "maxPayoutLeaveCode", "error.required", "Max Payout LeaveCode");
			valid = false;
		}
		return valid;
	}

	boolean validateAccrualRulePresent(List<AccrualCategoryRule> accrualCategoryRules) {
		boolean valid = false;

		if (accrualCategoryRules != null && accrualCategoryRules.size() > 0) {
			for (AccrualCategoryRule leaveAccrualCategoryRule : accrualCategoryRules) {
				valid |= leaveAccrualCategoryRule.isActive();
			}
		}

		if (!valid && categoryHasRules) {
			this.putGlobalError("error.accrualCategoryRule.required");
		} else if (!categoryHasRules) { valid = true; } //user specified a category with no rules

		return valid;
	}
	
	boolean validateMinPercentWorked(BigDecimal minPercentWorked, String fieldPrefix) {
		boolean valid = true;
		if (minPercentWorked==null) {
			this.putFieldError(fieldPrefix + "minPercentWorked", "error.required", "Min Percent Worked");
			valid = false;
		} else {
			if ( minPercentWorked.compareTo(new BigDecimal(100.00)) > 0 || 
				 minPercentWorked.compareTo(new BigDecimal(0.00)) < 0	) {
				this.putFieldError("minPercentWorked", "error.percentage.minimumPercentageWorked");
				valid = false;
			}
		}
		return valid;
	}
	
	// KPME-1257. Start and end values for a given unit of time should not allow overlapping/gaping 
	boolean validateStartEndUnits(List<AccrualCategoryRule> accrualCategoryRules) {
		boolean valid = true;
		int index = 0;
		if (accrualCategoryRules != null && accrualCategoryRules.size() > 0) {
			// the rules list has to be sorted by start field
			List<AccrualCategoryRule> sortedAccrualCategoryRules = new ArrayList<AccrualCategoryRule>(accrualCategoryRules);
			Collections.sort(sortedAccrualCategoryRules, SENIORITY_ORDER);
				
			Long previousEndUnit = accrualCategoryRules.get(0).getEnd();
			for (AccrualCategoryRule accrualCategoryRule :  accrualCategoryRules) {							
				if ( index > 0 ) {
					Long nextStartUnit = accrualCategoryRule.getStart();
					if ( previousEndUnit + 1 != nextStartUnit ){
						
						this.putFieldError("accrualCategoryRules["+index+"].start", "error.accrualCategoryRule.startEnd");
						valid = false;
					}
				}
				index++;
			}
		} 
		return valid;
	}
	
	// KPME-1288. Add a flag to the AccrualCategory Rules table that indicates
	// whether or not a Max Balance is required
	boolean validateMaxBalFlag(List<AccrualCategoryRule> accrualCategoryRules) {
		boolean valid = true;

		if (accrualCategoryRules != null && accrualCategoryRules.size() > 0) {
			//System.out.println("Calling validateMaxBalFlag with " + accrualCategoryRules.size() + " rules");
			for (AccrualCategoryRule accrualCategoryRule : accrualCategoryRules) {

				String maxBalFlag = accrualCategoryRule.getMaxBalFlag();
				//System.out.println("The max bal flag is " + maxBalFlag);
				BigDecimal bigDzero = new BigDecimal(0);

				if ((maxBalFlag != null) && (maxBalFlag.equalsIgnoreCase("Y"))) {

					if ((accrualCategoryRule.getMaxBalance() == null) || (accrualCategoryRule.getMaxBalance().equals(bigDzero))) {
						//System.out.println("Triggered Max Balance "+ accrualCategoryRule.getMaxBalance());
						this.putFieldError("Max Balance","error.accrualCategoryRule.maxBalFlag");
						valid = false;
					}

					if (accrualCategoryRule.getMaxBalanceActionFrequency() == null) { 
						//System.out.println("Triggered Max Balance Action Frequency " + accrualCategoryRule.getMaxBalanceActionFrequency());
						this.putFieldError("Max Balance Action Frequency","error.accrualCategoryRule.maxBalFlag");
						valid = false;
					}

					if (accrualCategoryRule.getActionAtMaxBalance() == null) {
						//System.out.println("Triggered Action at Max Balance "+ accrualCategoryRule.getActionAtMaxBalance());
						this.putFieldError("Action at Max Balance","error.accrualCategoryRule.maxBalFlag");
						valid = false;
					}
				}

			}

		}
		return valid;
	}
	
	// JIRA1355
	boolean validateLeavePlan(String leavePlan, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateLeavePlan(leavePlan, asOfDate)) {
			this.putFieldError("leavePlan", "error.existence", "leavePlan '"
					+ leavePlan + "'");
			valid = false;
		}
		return valid;
	}
	
	static final Comparator<AccrualCategoryRule> SENIORITY_ORDER = new Comparator<AccrualCategoryRule>() {

        public int compare(AccrualCategoryRule e1, AccrualCategoryRule e2) {
            return e1.getStart().compareTo(e2.getStart());
        }
    };
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Leave Accrual");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof AccrualCategory) {
			AccrualCategory leaveAccrualCategory = (AccrualCategory) pbo;
			if (leaveAccrualCategory != null) {
				
				valid = true;
				valid &= this.doesCategoryHaveRules(leaveAccrualCategory);
				valid &= this.validateAccrualRulePresent(leaveAccrualCategory.getAccrualCategoryRules());
				valid &= this.validateMinPercentWorked(leaveAccrualCategory.getMinPercentWorked(), ADD_LINE_LOCATION);
				valid &= this.validateStartEndUnits(leaveAccrualCategory.getAccrualCategoryRules());
				valid &= this.validateLeavePlan(leaveAccrualCategory.getLeavePlan(), leaveAccrualCategory.getEffectiveDate());
			}
		}
		return valid;
	}
	
	@Override
	public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName,PersistableBusinessObject line) {

		boolean valid = true;

		LOG.debug("entering custom validation for Leave Accrual Rules");
		PersistableBusinessObject pbo = line;

		if (pbo instanceof AccrualCategoryRule) {
			
			AccrualCategoryRule leaveAccrualCategoryRule = (AccrualCategoryRule) pbo;

			// cannot validate the rule without this flag now
			if (StringUtils.isBlank(leaveAccrualCategoryRule.getMaxBalFlag())) {
				//System.out.println("Caught NPE at max balance FLAG");
				this.putFieldError("add.accrualCategoryRules."+ "maxBalFlag", "error.required");
				valid = false;
							
			} else {

			if (leaveAccrualCategoryRule.getMaxBalFlag().equals("Y")) {

				if (leaveAccrualCategoryRule.getMaxBalance() == null) {
				//System.out.println("Caught NPE at max balance");
				this.putFieldError("add.accrualCategoryRules."+ "maxBalance", "error.required", "Max Balance");
					valid = false;
				}
				if (leaveAccrualCategoryRule.getMaxBalanceActionFrequency() == null) {
				this.putFieldError("add.accrualCategoryRules."+ "maxBalanceActionFrequency", "error.required","Max Balance Action Frequency");
					valid = false;
				}
				if (leaveAccrualCategoryRule.getActionAtMaxBalance() == null) {
				this.putFieldError("add.accrualCategoryRules."+ "actionAtMaxBalance", "error.required","Action at Max Balance");
					valid = false;
				}

				if (StringUtils.equals("T",leaveAccrualCategoryRule.getActionAtMaxBalance())) {
					valid &= validateMaxBalAccCat(leaveAccrualCategoryRule.getMaxBalanceTransferToAccrualCategory(),ADD_LINE_LOCATION);
					valid &= validateMaxBalConvFact(leaveAccrualCategoryRule.getMaxBalanceTransferConversionFactor(),ADD_LINE_LOCATION);
					valid &= validateMaxBalTransferAmount(leaveAccrualCategoryRule.getMaxTransferAmount(),ADD_LINE_LOCATION);
				} else if (StringUtils.equals("P",leaveAccrualCategoryRule.getActionAtMaxBalance())) {
					valid &= validateMaxPayoutAmount(leaveAccrualCategoryRule.getMaxPayoutAmount(),ADD_LINE_LOCATION);
					valid &= validateMaxPayoutLeaveCode(leaveAccrualCategoryRule.getMaxPayoutLeaveCode(),ADD_LINE_LOCATION);
				}
			}

			if (leaveAccrualCategoryRule != null&& leaveAccrualCategoryRule.getMaxBalFlag().equals("N")) {
				BigDecimal noMaxBal = new BigDecimal(0);
				leaveAccrualCategoryRule.setMaxBalance(noMaxBal);
				leaveAccrualCategoryRule.setMaxBalanceActionFrequency("");
				leaveAccrualCategoryRule.setActionAtMaxBalance("");

			}
		  } //else
		}

		return valid;
	}
}
