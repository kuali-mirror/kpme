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
package org.kuali.kpme.core.earncode.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class EarnCodeValidation extends MaintenanceDocumentRuleBase{
	
	boolean validateRollupToEarnCode(String earnCode, LocalDate asOfDate) {
		boolean valid = true;
		if (!StringUtils.isEmpty(earnCode) && !ValidationUtils.validateEarnCode(earnCode, asOfDate)) {
			this.putFieldError("rollupToEarnCode", "earncode.rollupToEarnCode.notfound", "Roll up to Earn code "
					+ earnCode + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateDefaultAmountOfTime(Long defaultAmountofTime) {
		boolean valid = true;
		if ( defaultAmountofTime != null ){
			if (defaultAmountofTime.compareTo(24L) > 0  || defaultAmountofTime.compareTo(0L) < 0) {
				this.putFieldError("defaultAmountofTime", "error.leaveCode.hours", "Default Amount of Time '"
						+ defaultAmountofTime + "'");
				valid = false;
			}
		}
		return valid;
	}
	
	boolean validateRecordMethod(String recordMethod, String accrualCategory, LocalDate asOfDate){
		boolean valid = true;
		if(recordMethod != null) {
			if(StringUtils.isNotEmpty(accrualCategory)) {
				valid = ValidationUtils.validateRecordMethod(recordMethod, accrualCategory, asOfDate);
				if(!valid) {
					this.putFieldError("recordMethod", "earncode.recordMethod.invalid", "Record Method");
				}
			}
		}
		return valid;
	}
	
	boolean validateLeavePlan(EarnCode earnCode) {
		
		boolean valid = true;
		
		if (StringUtils.isNotBlank(earnCode.getLeavePlan())) {

			if (!ValidationUtils.validateLeavePlan(earnCode.getLeavePlan(), earnCode.getEffectiveLocalDate())) {
				this.putFieldError("leavePlan", "error.existence", "leavePlan '"
						+ earnCode.getLeavePlan() + "'");
				valid = false;
				return valid;
			}
			
			if (earnCode.getEffectiveDate() != null && StringUtils.isNotBlank(earnCode.getAccrualCategory())) {
				AccrualCategory myTestAccrualCategoryObj =  HrServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCode.getAccrualCategory(), earnCode.getEffectiveLocalDate());
				if(myTestAccrualCategoryObj != null) {
					if (!myTestAccrualCategoryObj.getLeavePlan().equals(earnCode.getLeavePlan())) {
						this.putFieldError("leavePlan", "error.leaveCode.leavePlanMismatch", myTestAccrualCategoryObj.getLeavePlan());
						valid = false;
						return valid;
					}
					earnCode.setLeavePlan(myTestAccrualCategoryObj.getLeavePlan());
				} 
			}
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		EarnCode earnCode = (EarnCode)this.getNewBo();
		EarnCode oldEarnCode = (EarnCode)this.getOldBo();
		if ((StringUtils.equals(oldEarnCode.getEarnCode(), HrConstants.LUNCH_EARN_CODE) 
				|| StringUtils.equals(oldEarnCode.getEarnCode(), HrConstants.HOLIDAY_EARN_CODE))
					&& !earnCode.isActive()) {
			this.putFieldError("active", "earncode.inactivate.locked", earnCode
					.getEarnCode());
		}
		//if earn code is not designated how to record then throw error
		if (earnCode.getHrEarnCodeId() == null) {
			if (ValidationUtils.validateEarnCode(earnCode.getEarnCode(), null)) {
				// If there IS an earn code, ie, it is valid, we need to report
				// an error as earn codes must be unique.			
				this.putFieldError("earnCode", "earncode.earncode.unique");
				return false;
			}
		}
		
		 //check if the effective date of the leave plan is prior to effective date of the earn code 
		 //accrual category is an optional field
		if(StringUtils.isNotEmpty(earnCode.getLeavePlan())){
			boolean valid = validateLeavePlan(earnCode);
			if(!valid) {
				return false;
			}
		}
		//check if the effective date of the accrual category is prior to effective date of the earn code 
		//accrual category is an optional field
		if(StringUtils.isNotEmpty(earnCode.getAccrualCategory())){
			if (!ValidationUtils.validateAccrualCategory(earnCode.getAccrualCategory(), earnCode.getEffectiveLocalDate())) {
				this.putFieldError("accrualCategory", "earncode.accrualCategory.invalid", new String[]{earnCode.getAccrualCategory(),earnCode.getLeavePlan()});
				return false;
			}
		}
		
		// check if there's a newer version of the Earn Code
		int count = HrServiceLocator.getEarnCodeService().getNewerEarnCodeCount(earnCode.getEarnCode(), earnCode.getEffectiveLocalDate());
		if(count > 0) {
			this.putFieldError("effectiveDate", "earncode.effectiveDate.newer.exists");
			return false;
		}
		
/*		// kpme-937 can not deactivate an earn code if it used in active timeblocks
		List<TimeBlock> latestEndTimestampTimeBlocks =  TkServiceLocator.getTimeBlockService().getLatestEndTimestampForEarnCode(earnCode.getEarnCode());
		if ( !earnCode.isActive() && !latestEndTimestampTimeBlocks.isEmpty() && earnCode.getEffectiveDate().before(latestEndTimestampTimeBlocks.get(0).getEndDate()) ){
			this.putFieldError("earnCode", "earncode.earncode.inactivate", earnCode.getEarnCode());
			return false;
		}*/
		
		if(!(this.validateDefaultAmountOfTime(earnCode.getDefaultAmountofTime()))) {
			return false;
		}
		
		if(earnCode.getRollupToEarnCode() != null && !StringUtils.isEmpty(earnCode.getRollupToEarnCode())) {
			if(!(this.validateRollupToEarnCode(earnCode.getRollupToEarnCode(), earnCode.getEffectiveLocalDate()))) {
				return false;
			}
		}
		
		if(!validateRecordMethod(earnCode.getRecordMethod(), earnCode.getAccrualCategory(), earnCode.getEffectiveLocalDate())) {
			return false;
		}
		
		return true;
	}

}
