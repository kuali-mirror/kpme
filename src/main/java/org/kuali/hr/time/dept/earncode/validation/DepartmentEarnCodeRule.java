package org.kuali.hr.time.dept.earncode.validation;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;


public class DepartmentEarnCodeRule extends MaintenanceDocumentRuleBase {

	protected boolean validateSalGroup(DepartmentEarnCode departmentEarnCode ) {
		boolean valid = false;
		LOG.debug("Validating SalGroup: " + departmentEarnCode.getTkSalGroupId());
		SalGroup salGroup = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(SalGroup.class, departmentEarnCode.getTkSalGroupId());
		if (salGroup != null) {
			valid = true;
			LOG.debug("found SalGroup.");
		} else {
			this.putFieldError("tkSalGroupId", "error.existence", "Salgroup '"
					+ departmentEarnCode.getTkSalGroupId()+ "'");
		}
		return valid;
	}

	protected boolean validateDept(DepartmentEarnCode departmentEarnCode) {
		boolean valid = false;
		LOG.debug("Validating dept: " + departmentEarnCode.getDeptId());
		// TODO: We may need a full DAO that handles bo lookups at some point,
		// but we can use the provided one:
		Department dept = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(Department.class, departmentEarnCode.getDeptId());
		if (dept != null) {
			valid = true;
			LOG.debug("found department.");
		} else {
			this.putFieldError("deptId", "error.existence", "Department '"
					+ departmentEarnCode.getDeptId() + "'");
		}
		return valid;
	}
	
	protected boolean validateEarnCode(DepartmentEarnCode departmentEarnCode ) {
		boolean valid = false;
		LOG.debug("Validating earnCode: " + departmentEarnCode.getEarnCodeId());
		EarnCode earnCode = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(EarnCode.class, departmentEarnCode.getEarnCodeId());
		if (earnCode != null) {
			valid = true;
			LOG.debug("found earnCode.");
		} else {
			this.putFieldError("earnCodeId", "error.existence", "Earncode '"
					+ departmentEarnCode.getEarnCodeId()+ "'");
		}
		return valid;
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