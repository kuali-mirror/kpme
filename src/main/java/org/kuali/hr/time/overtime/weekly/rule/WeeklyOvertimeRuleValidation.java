package org.kuali.hr.time.overtime.weekly.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class WeeklyOvertimeRuleValidation extends MaintenanceDocumentRuleBase {

    private static final String ADD_LINE_LOCATION = "add.lstWeeklyOvertimeRules.";

    @Override
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, PersistableBusinessObject line) {
        boolean valid = true;

        if (line instanceof WeeklyOvertimeRule) {
            WeeklyOvertimeRule rule = (WeeklyOvertimeRule)line;
            valid = validateWeeklyOvertimeRule(rule, ADD_LINE_LOCATION);
        }

        return valid;
    }

	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;
		WeeklyOvertimeRuleGroup ruleGroup = (WeeklyOvertimeRuleGroup)this.getNewBo();

		int index = 0;
		for(WeeklyOvertimeRule rule : ruleGroup.getLstWeeklyOvertimeRules()) {
            valid &= validateWeeklyOvertimeRule(rule, "lstWeeklyOvertimeRules["+index+"].");
			index++;
		}

		return valid;
	}

    boolean validateWeeklyOvertimeRule(WeeklyOvertimeRule rule, String errorFieldPrefix) {
        boolean valid = true;

        if (errorFieldPrefix == null)
            errorFieldPrefix = "";

        if(!StringUtils.isEmpty(rule.getMaxHoursEarnGroup())) {
            if(!ValidationUtils.validateEarnGroup(rule.getMaxHoursEarnGroup(), rule.getEffectiveDate())) {
                this.putFieldError(errorFieldPrefix + "maxHoursEarnGroup", "error.existence", "maxHoursEarnGroup '" + rule.getMaxHoursEarnGroup() + "'");
                valid = false;
            }
        }
        if(!StringUtils.isEmpty(rule.getConvertFromEarnGroup())) {
            if(!ValidationUtils.validateEarnGroup(rule.getConvertFromEarnGroup(), rule.getEffectiveDate())) {
                this.putFieldError(errorFieldPrefix + "convertFromEarnGroup", "error.existence", "convertFromEarnGroup '" + rule.getConvertFromEarnGroup() + "'");
                valid = false;
            }
        }

        if(!StringUtils.isEmpty(rule.getConvertToEarnCode())) {
            if(!ValidationUtils.validateEarnCode(rule.getConvertToEarnCode(), rule.getEffectiveDate())) {
                this.putFieldError(errorFieldPrefix + "convertToEarnCode", "error.existence", "convertToEarnCode '" + rule.getConvertToEarnCode() + "'");
                valid = false;
            } else if (!ValidationUtils.validateEarnCode(rule.getConvertToEarnCode(), true, rule.getEffectiveDate())) {
                this.putFieldError(errorFieldPrefix + "convertToEarnGroup", "earncode.ovt.required", rule.getConvertToEarnCode());
                valid = false;
            }
        }

        return valid;
    }

}
