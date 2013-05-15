/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.accrualcategory.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.ObjectUtils;

@SuppressWarnings("deprecation")
public class AccrualCategoryValidation extends MaintenanceDocumentRuleBase {
	private static final String ADD_LINE_LOCATION = "add.accrualCategoryRules.";
    private static final String OLD_LINE_LOCATION = "accrualCategoryRules";
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
	
	boolean validateMaxPayoutEarnCode(String maxPayoutEarnCode, String fieldPrefix) {
		boolean valid = true;
		if (maxPayoutEarnCode==null) {
			this.putFieldError(fieldPrefix + "maxPayoutEarnCode", "error.required", "Max Payout EarnCode");
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
	boolean validateStartEndUnits(List<AccrualCategoryRule> accrualCategoryRules, AccrualCategoryRule newAccrualCategoryRule) {
		List<AccrualCategoryRule> tempAccrualCategoryRules = new ArrayList<AccrualCategoryRule>(accrualCategoryRules);
		//tempAccrualCategoryRules.add(newAccrualCategoryRule);
		boolean valid = true;
		
		if (accrualCategoryRules != null && accrualCategoryRules.size() > 0) {
			// the rules list has to be sorted by start field
			List<AccrualCategoryRule> sortedAccrualCategoryRules = new ArrayList<AccrualCategoryRule>(tempAccrualCategoryRules);
			Collections.sort(sortedAccrualCategoryRules, SENIORITY_ORDER);

			Long previousEndUnit = sortedAccrualCategoryRules.get(sortedAccrualCategoryRules.size()-1).getEnd();

			long decrementedNewRule = newAccrualCategoryRule.getStart();
			if (previousEndUnit.compareTo(decrementedNewRule)!=0) {
					this.putFieldError("add.accrualCategoryRules.start", "error.accrualCategoryRule.startEnd");
					valid = false;
			}
		} 
		
		return valid;
	}

    /**
     * Validates all requirements for a new leave accrual category rule
     * Called after AddCollectionLineBusinessRule
     * @param leaveAccrualCategoryRule
     * @return
     */
    public boolean validateAccrualCategoryRule(AccrualCategoryRule leaveAccrualCategoryRule){
        boolean valid = true;
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

                if (StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER,leaveAccrualCategoryRule.getActionAtMaxBalance())) {
                    valid &= validateMaxBalAccCat(leaveAccrualCategoryRule.getMaxBalanceTransferToAccrualCategory(),ADD_LINE_LOCATION);
                    valid &= validateMaxBalConvFact(leaveAccrualCategoryRule.getMaxBalanceTransferConversionFactor(),ADD_LINE_LOCATION);
                    valid &= validateMaxBalTransferAmount(leaveAccrualCategoryRule.getMaxTransferAmount(),ADD_LINE_LOCATION);
                } else if (StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT,leaveAccrualCategoryRule.getActionAtMaxBalance())) {
                    valid &= validateMaxPayoutAmount(leaveAccrualCategoryRule.getMaxPayoutAmount(),ADD_LINE_LOCATION);
                    valid &= validateMaxPayoutEarnCode(leaveAccrualCategoryRule.getMaxPayoutEarnCode(),ADD_LINE_LOCATION);
                }
            }

            if (leaveAccrualCategoryRule != null&& leaveAccrualCategoryRule.getMaxBalFlag().equals("N")) {
                BigDecimal noMaxBal = new BigDecimal(0);
                leaveAccrualCategoryRule.setMaxBalance(noMaxBal);
                leaveAccrualCategoryRule.setMaxBalanceActionFrequency(HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END);
                leaveAccrualCategoryRule.setActionAtMaxBalance(HrConstants.ACTION_AT_MAX_BALANCE.LOSE);

            }

        } //else
        return valid;
    }

    /**
     * Validates all requirements for all leave accrual category rules already added but might have changed
     * @param accrualCategoryRules
     * @return
     */
    public boolean validateAccrualCategoryRules(List<AccrualCategoryRule> accrualCategoryRules){
        boolean valid = true;

        for(int i = 0; i < accrualCategoryRules.size(); i++) {
            AccrualCategoryRule leaveAccrualCategoryRule = accrualCategoryRules.get(i);

            // cannot validate the rule without this flag now
            if (StringUtils.isBlank(leaveAccrualCategoryRule.getMaxBalFlag())) {
                //System.out.println("Caught NPE at max balance FLAG");
                this.putFieldError("add.accrualCategoryRules[" + i + "].maxBalFlag", "error.required");
                valid = false;
            } else {
                if (leaveAccrualCategoryRule.getMaxBalFlag().equals("Y")) {

                    if (leaveAccrualCategoryRule.getMaxBalance() == null) {
                        //System.out.println("Caught NPE at max balance");
                        this.putFieldError("add.accrualCategoryRules[" + i + "].maxBalance", "error.required", "Max Balance");
                        valid = false;
                    }
                    if (leaveAccrualCategoryRule.getMaxBalanceActionFrequency() == null) {
                        this.putFieldError("add.accrualCategoryRules[" + i + "].maxBalanceActionFrequency", "error.required","Max Balance Action Frequency");
                        valid = false;
                    }
                    if (leaveAccrualCategoryRule.getActionAtMaxBalance() == null) {
                        this.putFieldError("add.accrualCategoryRules[" + i + "].actionAtMaxBalance", "error.required","Action at Max Balance");
                        valid = false;
                    }

                    if (StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.TRANSFER,leaveAccrualCategoryRule.getActionAtMaxBalance())) {
                        valid &= validateMaxBalAccCat(leaveAccrualCategoryRule.getMaxBalanceTransferToAccrualCategory(),OLD_LINE_LOCATION+"["+ i +"].");
                        valid &= validateMaxBalConvFact(leaveAccrualCategoryRule.getMaxBalanceTransferConversionFactor(),OLD_LINE_LOCATION+"["+ i +"].");
                        valid &= validateMaxBalTransferAmount(leaveAccrualCategoryRule.getMaxTransferAmount(),OLD_LINE_LOCATION+"["+ i +"].");
                    } else if (StringUtils.equals(HrConstants.ACTION_AT_MAX_BALANCE.PAYOUT,leaveAccrualCategoryRule.getActionAtMaxBalance())) {
                        valid &= validateMaxPayoutAmount(leaveAccrualCategoryRule.getMaxPayoutAmount(),OLD_LINE_LOCATION+"["+ i +"].");
                        valid &= validateMaxPayoutEarnCode(leaveAccrualCategoryRule.getMaxPayoutEarnCode(),OLD_LINE_LOCATION+"["+ i +"].");
                    }
                }

                if (leaveAccrualCategoryRule != null&& leaveAccrualCategoryRule.getMaxBalFlag().equals("N")) {
                    BigDecimal noMaxBal = new BigDecimal(0);
                    leaveAccrualCategoryRule.setMaxBalance(noMaxBal);
                    leaveAccrualCategoryRule.setMaxBalanceActionFrequency(HrConstants.MAX_BAL_ACTION_FREQ.YEAR_END);
                    leaveAccrualCategoryRule.setActionAtMaxBalance(HrConstants.ACTION_AT_MAX_BALANCE.LOSE);

                }

            } //else
        }
        return valid;
    }

	/**
	 * Validates if there's any gaps or overlaps between rules
	 * @param accrualCategoryRules
	 * @return
	 */
	public boolean validateAccrualRulesGapOverlap(List<AccrualCategoryRule> accrualCategoryRules) {
		if (CollectionUtils.isNotEmpty(accrualCategoryRules)) {
			List<AccrualCategoryRule> tempAccrualCategoryRules = new ArrayList<AccrualCategoryRule>(accrualCategoryRules);
			// rules should be added in order, sort rules list by start field just in case
			List<AccrualCategoryRule> sortedAccrualCategoryRules = new ArrayList<AccrualCategoryRule>(tempAccrualCategoryRules);
			Collections.sort(sortedAccrualCategoryRules, SENIORITY_ORDER);

			for(int i = 0; i < sortedAccrualCategoryRules.size(); i++) {
				AccrualCategoryRule aRule = sortedAccrualCategoryRules.get(i);
				if(aRule != null && i > 0) {
					AccrualCategoryRule previousRule =  sortedAccrualCategoryRules.get(i-1);
					if(ObjectUtils.isNotNull(previousRule.getEnd()) && ObjectUtils.isNotNull(aRule.getStart())) {
						if(previousRule.getEnd().compareTo(aRule.getStart()) != 0) {	// overlap
							String[] errors={previousRule.getEnd().toString(), aRule.getStart().toString()};
							this.putFieldError("accrualCategoryRules[" + i + "].start", "error.accrualCategoryRule.overlapOrGap", errors);
							return false;
						}
					}
					previousRule = aRule;
				}
			}
		}
		return true;
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
	boolean validateLeavePlan(String leavePlan, LocalDate asOfDate) {
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
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof AccrualCategory) {
			AccrualCategory leaveAccrualCategory = (AccrualCategory) pbo;
			if (leaveAccrualCategory != null) {
				valid = true;
				valid &= this.validateEffectiveDate(leaveAccrualCategory.getEffectiveLocalDate());                           //validate effective dates
				valid &= this.doesCategoryHaveRules(leaveAccrualCategory);
				valid &= this.validateAccrualRulePresent(leaveAccrualCategory.getAccrualCategoryRules());               // validate existence of active rules if specified in Acc. Cat.
				if(valid && CollectionUtils.isNotEmpty(leaveAccrualCategory.getAccrualCategoryRules())) {
					valid &= this.validateAccrualRulesGapOverlap(leaveAccrualCategory.getAccrualCategoryRules());         //Validate gaps and overaps
                    valid &= this.validateAccrualCategoryRules(leaveAccrualCategory.getAccrualCategoryRules());           //validate required fields in case of actions at max balance
				}
				valid &= this.validateMinPercentWorked(leaveAccrualCategory.getMinPercentWorked(), ADD_LINE_LOCATION);      //validate minimum work percentage
				valid &= this.validateLeavePlan(leaveAccrualCategory.getLeavePlan(), leaveAccrualCategory.getEffectiveLocalDate());
			}
		}
		return valid;
	}
	
	boolean validateEffectiveDate(LocalDate effectiveDate) {
		boolean valid = true;
		if(effectiveDate != null && !ValidationUtils.validateOneYearFutureEffectiveDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
		} 
		return valid;
	}
	
	@Override
	public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, PersistableBusinessObject line) {

		boolean valid = true;

		LOG.debug("entering custom validation for Leave Accrual Rules");
		PersistableBusinessObject pbo = line;	
		PersistableBusinessObject pboAccrualCategory = document.getDocumentBusinessObject();
		AccrualCategoryRule leaveAccrualCategoryRule = (AccrualCategoryRule) pbo;
		
		if(pboAccrualCategory instanceof AccrualCategory){
//            valid &= this.doesCategoryHaveRules((AccrualCategory) pboAccrualCategory);
			if (StringUtils.isNotBlank(((AccrualCategory) pboAccrualCategory).getHasRules()) && ((AccrualCategory) pboAccrualCategory).getHasRules().equalsIgnoreCase("Y")){
				if ( pbo instanceof AccrualCategoryRule ) {
					AccrualCategory accrualCategory = (AccrualCategory) pboAccrualCategory;
					
					if (leaveAccrualCategoryRule != null && accrualCategory.getAccrualCategoryRules() != null) {
						//KPME 1483 avoid NPEs since page required field validations are not present when add button is clicked
						if (leaveAccrualCategoryRule.getStart() == null || leaveAccrualCategoryRule.getEnd() == null) {
							this.putFieldError("error.accrualCategoryRule.startEndBlank", "error.required");
							return false; //break out before NPE
						}
						valid = this.validateStartEndUnits(accrualCategory.getAccrualCategoryRules(), leaveAccrualCategoryRule);
					}
                    valid = this.validateAccrualCategoryRule(leaveAccrualCategoryRule);
				}
			}
		}
		return valid;
	}

}
