package org.kuali.hr.time.dept.earncode.validation;

import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;


public class DepartmentEarnCodeRule extends MaintenanceDocumentRuleBase {

	boolean validateSalGroup(DepartmentEarnCode departmentEarnCode ) {
		if (!ValidationUtils.validateSalGroup(departmentEarnCode.getTkSalGroup(), departmentEarnCode.getEffectiveDate())) {
			this.putFieldError("tkSalGroup", "error.existence", "Salgroup '" + departmentEarnCode.getTkSalGroup()+ "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateDept(DepartmentEarnCode clr) {
		if (!ValidationUtils.validateDepartment(clr.getDept(), clr.getEffectiveDate())) {
			this.putFieldError("dept", "error.existence", "department '" + clr.getDept() + "'");
			return false;				
		} else {
			return true;
		}
	}
	
	boolean validateEarnCode(DepartmentEarnCode departmentEarnCode ) {
		if (!ValidationUtils.validateEarnCode(departmentEarnCode.getEarnCode(), departmentEarnCode.getEffectiveDate())) {
			this.putFieldError("earnCode", "error.existence", "Earncode '" + departmentEarnCode.getEarnCode()+ "'");
			return false;
		} else {
			return true;
		}
	}
	
	boolean validateDuplication(DepartmentEarnCode departmentEarnCode) {
		if(ValidationUtils.duplicateDeptEarnCodeExists(departmentEarnCode)) {
			this.putFieldError("effectiveDate", "deptEarncode.duplicate.exists");
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

		LOG.debug("entering custom validation for DepartmentEarnCode");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof DepartmentEarnCode) {
			DepartmentEarnCode departmentEarnCode = (DepartmentEarnCode) pbo;
			
			if (departmentEarnCode != null) {
				valid = true;
				valid &= this.validateSalGroup(departmentEarnCode);
				valid &= this.validateDept(departmentEarnCode);
				valid &= this.validateEarnCode(departmentEarnCode);	
				valid &= this.validateDuplication(departmentEarnCode);
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