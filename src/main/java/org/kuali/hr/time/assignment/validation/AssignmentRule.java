package org.kuali.hr.time.assignment.validation;



import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class AssignmentRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + assignment.getWorkAreaId());
		WorkArea workArea = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(WorkArea.class, assignment.getWorkAreaId());
		if (workArea != null) {
			valid = true;
			LOG.debug("found workarea.");
		} else {
			this.putFieldError("workAreaId", "error.existence", "workarea '"
					+ assignment.getWorkAreaId()+ "'");
		}
		return valid;
	} 
	
	protected boolean validateTask(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating task: " + assignment.getTaskId());
		Task task = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(Task.class, assignment.getTaskId());
		if (task != null) {
			valid = true;
			LOG.debug("found task.");
		} else {
			this.putFieldError("taskId", "error.existence", "taskId '"
					+ assignment.getTaskId()+ "'");
		}
		return valid;
	}
	
	protected boolean validateJob(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating job: " + assignment.getJobNumber());
		Job job = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(Job.class, assignment.getJobNumber());
		if (job != null) {
			valid = true;
			
			LOG.debug("found job.");
		} else {
			this.putFieldError("jobNumber", "error.existence", "jobNumber '"
					+ assignment.getJobNumber()+ "'");
		}
		return valid;
	} 
	
	protected boolean validateEarnCode(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating EarnCode: " + assignment.getEarnCodeId());
		EarnCode earnCode = KNSServiceLocator.getBusinessObjectService()
				.findBySinglePrimaryKey(EarnCode.class, assignment.getEarnCodeId());
		if (earnCode != null) {
			valid = true;			
			LOG.debug("found earnCode.");
		} else {
			this.putFieldError("earnCodeId", "error.existence", "earnCodeId '"
					+ assignment.getEarnCodeId()+ "'");
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

		LOG.debug("entering custom validation for DeptLunchRule");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof Assignment) {
			Assignment assignment = (Assignment) pbo;			
			if (assignment != null) {
				valid = true;				
				valid &= this.validateWorkArea(assignment);		
				valid &= this.validateJob(assignment);
				valid &= this.validateTask(assignment);				
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
