package org.kuali.hr.time.overtime.daily.rule.validation;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class DailyOvertimeRuleRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(DailyOvertimeRule dailyOvertimeRule ) {
		boolean valid = false;
//		LOG.debug("Validating workarea: " + dailyOvertimeRule.getWorkAreaId());
//		WorkArea workArea = KNSServiceLocator.getBusinessObjectService()
//				.findBySinglePrimaryKey(WorkArea.class, dailyOvertimeRule.getWorkAreaId());
//		if (workArea != null) {
//			valid = true;
//			LOG.debug("found workArea.");
//		} else {
//			this.putFieldError("workAreaId", "error.existence", "Workarea '"
//					+ dailyOvertimeRule.getWorkAreaId()+ "'");
//		}
		return valid;
	}

	protected boolean validateDepartment(DailyOvertimeRule dailyOvertimeRule) {
		boolean valid = false;
//		LOG.debug("Validating dept: " + dailyOvertimeRule.getDeptId());
//		// TODO: We may need a full DAO that handles bo lookups at some point,
//		// but we can use the provided one:
//		Department dept = KNSServiceLocator.getBusinessObjectService()
//				.findBySinglePrimaryKey(Department.class, dailyOvertimeRule.getDeptId());
//		if (dept != null) {
//			valid = true;
//			LOG.debug("found department.");
//		} else {
//			this.putFieldError("deptId", "error.existence", "Department '"
//					+ dailyOvertimeRule.getDeptId() + "'");
//		}
		return valid;
	}
	
	protected boolean validateTask(DailyOvertimeRule dailyOvertimeRule ) {
		boolean valid = false;
//		LOG.debug("Validating task: " + dailyOvertimeRule.getTaskId());
//		Task task = KNSServiceLocator.getBusinessObjectService()
//				.findBySinglePrimaryKey(Task.class, dailyOvertimeRule.getTaskId());
//		if (task != null) {
//			valid = true;
//			LOG.debug("found task");
//		} else {
//			this.putFieldError("taskId", "error.existence", "Task '"
//					+ dailyOvertimeRule.getTaskId()+ "'");
//		}
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

		LOG.debug("entering custom validation for DailyOvertimeRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof DailyOvertimeRule) {
			DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) pbo;
			dailyOvertimeRule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
			if (dailyOvertimeRule != null) {
				valid = true;
				valid &= this.validateDepartment(dailyOvertimeRule);
				valid &= this.validateWorkArea(dailyOvertimeRule);
				valid &= this.validateTask(dailyOvertimeRule);					
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