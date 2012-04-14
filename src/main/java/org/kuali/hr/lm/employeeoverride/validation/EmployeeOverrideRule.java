package org.kuali.hr.lm.employeeoverride.validation;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class EmployeeOverrideRule extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof EmployeeOverride) {
			EmployeeOverride eo = (EmployeeOverride) pbo;
			valid &= this.validatePrincipalHRAttributes(eo);
			valid &= this.validateAccrualCategory(eo);
			valid &= this.validateLeavePlan(eo);
		}
		return valid;
	}
	
	boolean validateLeavePlan(EmployeeOverride eo) {
		if(StringUtils.isEmpty(eo.getLeavePlan())) {
			this.putFieldError("leavePlan", "error.employeeOverride.leavePlan.notfound");
			return false;
		} else if(eo.getEffectiveDate() != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().
				getAccrualCategory(eo.getAccrualCategory(), eo.getEffectiveDate());
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().
				getPrincipalCalendar(eo.getPrincipalId(), eo.getEffectiveDate());
			if(ac != null && pha != null && !ac.getLeavePlan().equals(pha.getLeavePlan())) {
				this.putFieldError("leavePlan", "error.employeeOverride.leavePlan.inconsistent", eo.getAccrualCategory() );
				return false;
			}
		}
		return true;
	}
	
	boolean validateAccrualCategory(EmployeeOverride eo) {
		if(eo.getAccrualCategory() != null && eo.getEffectiveDate() != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().
				getAccrualCategory(eo.getAccrualCategory(), eo.getEffectiveDate());
			if(ac == null) {
				this.putFieldError("accrualCategory", "error.employeeOverride.accrualCategory.notfound", eo.getAccrualCategory() );
				return false;
			}
		}
		return true;
	}
	
	boolean validatePrincipalHRAttributes(EmployeeOverride eo) {
		if(eo.getPrincipalId() != null && eo.getEffectiveDate() != null) {
			PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(eo.getPrincipalId(), eo.getEffectiveDate());
			if(pha == null) {
				this.putFieldError("principalId", "error.employeeOverride.principalHRAttributes.notfound", eo.getPrincipalId() );
				return false;
			}
		}
		return true;
	}

}
