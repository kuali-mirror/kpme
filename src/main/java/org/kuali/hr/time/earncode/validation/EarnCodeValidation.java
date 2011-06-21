package org.kuali.hr.time.earncode.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class EarnCodeValidation extends MaintenanceDocumentRuleBase{
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		EarnCode earnCode = (EarnCode)this.getNewBo();
		//if earn code is not designated how to record then throw error

		if (earnCode.getTkEarnCodeId() == null) {
			if (ValidationUtils.validateEarnCode(earnCode.getEarnCode(), null)) {
				// If there IS an earn code, ie, it is valid, we need to report
				// an error as earn codes must be unique.			
				this.putFieldError("earnCode", "earncode.earncode.unique");
				return false;
			}
		}
		
		if(earnCode.getRecordAmount() == false && 
			earnCode.getRecordHours() == false && 
			earnCode.getRecordTime() == false){
			this.putFieldError("recordTime", "earncode.record.not.specified");
			return false;
		}
		//confirm that only one of the check boxes is checked
		//if more than one are true then throw an error
		if(earnCode.getRecordAmount() && earnCode.getRecordHours() && earnCode.getRecordTime()){
			this.putFieldError("recordTime", "earncode.record.unique");
			return false;
		}
		boolean result = earnCode.getRecordAmount() ^ earnCode.getRecordHours() ^ earnCode.getRecordTime();
		if(!result){
			this.putFieldError("recordTime", "earncode.record.unique");
			return false;
		}

		//check if the effective date of the accrual category is prior to effective date of the earn code 
		//accrual category is an optional field
		if(StringUtils.isNotEmpty(earnCode.getAccrualCategory())){
			if (!ValidationUtils.validateAccrualCategory(earnCode.getAccrualCategory(), earnCode.getEffectiveDate())) {
				this.putFieldError("accrualCategory", "earncode.accrualCategory.invalid", earnCode.getAccrualCategory());
				return false;
			}
		}
		
		// check if there's a newer version of the Earn Code
		if(ValidationUtils.newerVersionExists(EarnCode.class, "earnCode", earnCode.getEarnCode(), earnCode.getEffectiveDate())) {
			this.putFieldError("effectiveDate", "earncode.effectiveDate.newer.exists");
			return false;
		}
		return true;
	}

}
