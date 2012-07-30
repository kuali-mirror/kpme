package org.kuali.hr.lm.leaveplan.validation;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;


public class LeavePlanValidation extends MaintenanceDocumentRuleBase {	 
	
	//KPME-1250 Kagata
	// This method determines if the leave plan can be inactivated
	boolean validateInactivation(LeavePlan leavePlan){
		boolean valid = true;
		// Get a list of active employees based on leave plan and its effective date.
		// If the list is not null, there are active employees and the leave plan can't be inactivated, so return false otherwise true
		if(!leavePlan.isActive()) {
			//this has to use the effective date of the job passed in
			List<PrincipalHRAttributes> pList = TkServiceLocator.getPrincipalHRAttributeService().getActiveEmployeesForLeavePlan(leavePlan.getLeavePlan(), leavePlan.getEffectiveDate());
			
			if (pList != null && pList.size() > 0) {
				// error.leaveplan.inactivate=Can not inactivate leave plan {0}.  There are active employees in the plan.
				this.putFieldError("active", "error.leaveplan.inactivate", leavePlan.getLeavePlan());
				valid = false;
			} 
		}

		return valid;
	}
	
	// KPME-1407 Kagata
	boolean validatePlanningMonths(String planningMonths) {
		boolean valid = true;
		if (planningMonths != null ){
			int iPlanningMonths = Integer.parseInt(planningMonths);
			// error.leaveplan.planningMonths='{0}' should be between 1 and 24.
			if (iPlanningMonths > 24  || iPlanningMonths <= 0) {
				this.putFieldError("planningMonths", "error.leaveplan.planningMonths", "Planning Months");
				valid = false;
			}
		}
		return valid;
	}
	
	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		valid = ValidationUtils.validateOneYearFutureEffectiveDate(effectiveDate);
		if(!valid) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = true;
		LOG.debug("entering custom validation for Leave Plan");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof LeavePlan) {
			LeavePlan leavePlan = (LeavePlan) pbo;
			if (leavePlan != null) {
				valid = true;
				valid &= this.validateInactivation(leavePlan);
				if(StringUtils.isNotEmpty(leavePlan.getPlanningMonths())) {
					valid &= this.validatePlanningMonths(leavePlan.getPlanningMonths());
				}
				if(leavePlan.getEffectiveDate() != null) {
					valid &= this.validateEffectiveDate(leavePlan.getEffectiveDate());
				}
			}
		}
		return valid;
	}
}
