package org.kuali.hr.time.overtime.daily.rule.validation;

import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class DailyOvertimeRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateWorkArea(DailyOvertimeRule ruleObj) {
		if (!ValidationUtils.validateWorkArea(ruleObj.getWorkArea(), ruleObj.getEffectiveDate())) {
			this.putFieldError("workArea", "error.existence", "workarea '" + ruleObj.getWorkArea() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateDepartment(DailyOvertimeRule ruleObj) {
		if (!ValidationUtils.validateDepartment(ruleObj.getDept(), ruleObj.getEffectiveDate())) {
			this.putFieldError("dept", "error.existence", "department '" + ruleObj.getDept() + "'");
			return false;				
		} else {
			return true;
		}
	}

	
	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for DailyOvertimeRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof DailyOvertimeRule) {
			DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) pbo;
			dailyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
			if (dailyOvertimeRule != null) {
				valid = true;
				valid &= this.validateDepartment(dailyOvertimeRule);
				valid &= this.validateWorkArea(dailyOvertimeRule);			
			}
		}
		
		return valid;
	}

	@Override
	protected boolean processCustomApproveDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomApproveDocumentBusinessRules(document);
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		return super.processCustomRouteDocumentBusinessRules(document);
	}
}