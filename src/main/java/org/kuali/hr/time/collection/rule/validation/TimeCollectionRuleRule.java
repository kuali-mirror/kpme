package org.kuali.hr.time.collection.rule.validation;

import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class TimeCollectionRuleRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(TimeCollectionRule timeCollectionRule ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + timeCollectionRule.getWorkArea());
		WorkArea workArea = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(WorkArea.class, timeCollectionRule.getWorkArea());
		if (workArea != null) {
			valid = true;
			LOG.debug("found workarea.");			
		} else {
			this.putFieldError("workarea", "error.existence", "workarea '"
					+ timeCollectionRule.getWorkArea()+ "'");			
		}
		return valid;
	}

	protected boolean validateDepartment(TimeCollectionRule timeCollectionRule) {
		boolean valid = false;
		LOG.debug("Validating department: " + timeCollectionRule.getDeptId());
		// TODO: We may need a full DAO that handles bo lookups at some point,
		// but we can use the provided one:
		Department dept = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(Department.class, timeCollectionRule.getDeptId());
		if (dept != null) {
			valid = true;			
			LOG.debug("found department.");
		} else {						
			this.putFieldError("deptId", "error.existence", "department '"
					+ timeCollectionRule.getDeptId() + "'");
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
		LOG.debug("entering custom validation for TimeCollectionRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof TimeCollectionRule) {
			TimeCollectionRule timeCollectionRule = (TimeCollectionRule) pbo;
			timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession().getLoggedInUserPrincipalName());
			if (timeCollectionRule != null) {
				valid = true;
				valid &= this.validateDepartment(timeCollectionRule);
				valid &= this.validateWorkArea(timeCollectionRule);			
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