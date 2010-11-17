package org.kuali.hr.time.earncode.validation;

import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class EarnCodeValidation extends MaintenanceDocumentRuleBase{
	
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
		EarnCode earnCode = (EarnCode)this.getNewBo();
		//if earn code is not designated how to record then throw error
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
		return true;
	}

}
