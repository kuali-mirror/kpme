package org.kuali.hr.lm.employeeoverride.validation;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.employeeoverride.EmployeeOverride;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class EmployeeOverrideRule extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof EmployeeOverride) {
			EmployeeOverride eo = (EmployeeOverride) pbo;
			valid &= this.validateLeavePlan(eo);
			valid &= this.validateAccrualCategory(eo);
		}
		return valid;
	}
	
	boolean validateLeavePlan(EmployeeOverride eo) {
		if(StringUtils.isEmpty(eo.getLeavePlan())) {
			String[] parameters = new String[2];
			parameters[0] = eo.getPrincipalId();
			parameters[1] = eo.getEffectiveDate().toString();
			this.putFieldError("leavePlan", "error.employeeOverride.leavePlan.notfound", parameters);
			return false;
		}
		return true;
	}
	
	boolean validateAccrualCategory(EmployeeOverride eo) {
		if(eo.getAccrualCategory() != null && eo.getLeavePlan() != null) {
			AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().
				getAccrualCategory(eo.getAccrualCategory(), eo.getEffectiveDate());
			if(ac == null) {
				this.putFieldError("accrualCategory", "error.employeeOverride.accrualCategory.notfound", eo.getAccrualCategory() );
				return false;
			} else {
				if(!ac.getLeavePlan().equals(eo.getLeavePlan())) {
					String[] parameters = new String[2];
					parameters[0] = eo.getAccrualCategory();
					parameters[1] = eo.getLeavePlan();
					this.putFieldError("accrualCategory", "error.employeeOverride.accrualCategory.invalid", parameters);
					return false;
				}
			}
		}
		return true;
	}

}
