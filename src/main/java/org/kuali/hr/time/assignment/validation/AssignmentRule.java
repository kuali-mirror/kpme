package org.kuali.hr.time.assignment.validation;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class AssignmentRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating workarea: " + assignment.getWorkArea());
		
		WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), assignment.getEffectiveDate());
		//WorkArea workArea = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WorkArea.class, assignment.getWorkAreaObj().getTkWorkAreaId());
		if (workArea != null) {
			valid = true;
			LOG.debug("found workarea.");
		} else {
			this.putFieldError("workAreaId", "error.existence", "workarea '"
					+ assignment.getWorkArea()+ "'");
		}
		return valid;
	} 
	
	@SuppressWarnings("rawtypes")
	protected boolean validateTask(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating task: " + assignment.getTask());
		Map<String,Object> criteria = new HashMap<String,Object>();
		criteria.put("task", assignment.getTask());
		Collection c = KNSServiceLocator.getBusinessObjectService().findMatching(Task.class, criteria);
				//.findBySinglePrimaryKey(Task.class, assignment.getTask());
		if (c.size() > 0) {
			valid = true;
			LOG.debug("found task.");
		} else {
			this.putFieldError("taskId", "error.existence", "taskId '"
					+ assignment.getTask()+ "'");
		}
		return valid;
	}
	
	protected boolean validateJob(Assignment assignment ) {
		boolean valid = false;
		LOG.debug("Validating job: " + assignment.getJob());
		Job job = TkServiceLocator.getJobSerivce().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveDate());
		// Job job = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Job.class, assignment.getJob().getHrJobId());
		if (job != null) {
			valid = true;
			
			LOG.debug("found job.");
		} else {
			this.putFieldError("jobNumber", "error.existence", "jobNumber '"
					+ assignment.getJobNumber()+ "'");
		}
		return valid;
	} 
	
	//TODO fix this class
	protected boolean validateEarnCode(Assignment assignment ) {
		boolean valid = false;
//		LOG.debug("Validating EarnCode: " + assignment.getEarnCode()());
//		EarnCode earnCode = KNSServiceLocator.getBusinessObjectService()
//				.findBySinglePrimaryKey(EarnCode.class, assignment.getEarnCodeId());
//		if (earnCode != null) {
//			valid = true;			
//			LOG.debug("found earnCode.");
//		} else {
//			this.putFieldError("earnCodeId", "error.existence", "earnCodeId '"
//					+ assignment.getEarnCodeId()+ "'");
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
