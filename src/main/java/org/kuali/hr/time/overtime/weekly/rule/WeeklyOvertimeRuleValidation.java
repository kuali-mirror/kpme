package org.kuali.hr.time.overtime.weekly.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class WeeklyOvertimeRuleValidation extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		// TODO Auto-generated method stub
		return super.validateMaintenanceDocument(maintenanceDocument);
	}

	@Override
	public boolean processAddCollectionLineBusinessRules(
			MaintenanceDocument document, String collectionName,
			PersistableBusinessObject bo) {
		// TODO Auto-generated method stub
		return super
				.processAddCollectionLineBusinessRules(document, collectionName, bo);
	}
	
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
		WeeklyOvertimeRuleGroup ruleGroup = (WeeklyOvertimeRuleGroup)this.getNewBo();
		int index = 0;
		for(WeeklyOvertimeRule rule : ruleGroup.getLstWeeklyOvertimeRules()) {
			if(!StringUtils.isEmpty(rule.getMaxHoursEarnGroup())) {
				if(!ValidationUtils.validateEarnGroup(rule.getMaxHoursEarnGroup(), null))
					this.putFieldError("lstWeeklyOvertimeRules["+index+"].maxHoursEarnGroup", "error.existence", "maxHoursEarnGroup '" + rule.getMaxHoursEarnGroup() + "'");
			}
			if(!StringUtils.isEmpty(rule.getConvertFromEarnGroup())) {
				if(!ValidationUtils.validateEarnGroup(rule.getConvertFromEarnGroup(), null))
					this.putFieldError("lstWeeklyOvertimeRules["+index+"].convertFromEarnGroup", "error.existence", "convertFromEarnGroup '" + rule.getConvertFromEarnGroup() + "'");
			}
			if(!StringUtils.isEmpty(rule.getConvertToEarnCode())) {
				if(!ValidationUtils.validateEarnCode(rule.getConvertToEarnCode(), null))
					this.putFieldError("lstWeeklyOvertimeRules["+index+"].convertToEarnCode", "error.existence", "convertToEarnCode '" + rule.getConvertToEarnCode() + "'");
			}
			index++;
		}
		return true;
	}

}
