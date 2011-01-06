package org.kuali.hr.time.dept.lunch.validation;

import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class DeptLunchRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateWorkArea(DeptLunchRule ruleObj) {
		if (!ValidationUtils.validateWorkArea(ruleObj.getWorkArea(), ruleObj.getEffectiveDate())) {
			this.putFieldError("workArea", "error.existence", "workarea '" + ruleObj.getWorkArea() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateDepartment(DeptLunchRule ruleObj) {
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

		LOG.debug("entering custom validation for DeptLunchRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof DeptLunchRule) {
			DeptLunchRule deptLunchRule = (DeptLunchRule) pbo;
			deptLunchRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
			if (deptLunchRule != null) {
				valid = true;
				valid &= this.validateDepartment(deptLunchRule);
				valid &= this.validateWorkArea(deptLunchRule);					
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