package org.kuali.hr.job.validation;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class JobValidation extends MaintenanceDocumentRuleBase {

	private boolean validatePrincipalId(Job job) {
		if (job.getPrincipalId() != null
				&& !ValidationUtils.validatePrincipalId(job.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + job.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateJobNumber(Job job) {
		if (job.getJobNumber() != null) {
			Job jobObj = TkServiceLocator.getJobSerivce().getJob(
					job.getPrincipalId(), job.getJobNumber(),
					job.getEffectiveDate(), false);
			if (jobObj != null) {
				String[] parameters = new String[2];
				parameters[0] = job.getJobNumber().toString();
				parameters[1] = job.getPrincipalId();
				this.putFieldError("jobNumber",
						"principal.jobnumber.already.exist", parameters);
				return false;
			}
		}
		return true;
	}

	protected boolean validateDepartment(Job job) {
		if (job.getDept() != null
				&& !ValidationUtils.validateDepartment(job.getDept(), job
						.getEffectiveDate())) {
			this.putFieldError("dept", "error.existence", "department '"
					+ job.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateSalGroup(Job job) {
		if (!ValidationUtils.validateSalGroup(job.getHrSalGroup(), job
				.getEffectiveDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '"
					+ job.getHrSalGroup() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateLocation(Job job) {
		if (job.getLocation() != null
				&& !ValidationUtils.validateLocation(job.getLocation(), null)) {
			this.putFieldError("location", "error.existence", "location '"
					+ job.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validatePayType(Job job) {
		if (job.getHrPayType() != null
				&& !ValidationUtils.validatePayType(job.getHrPayType(), job
						.getEffectiveDate())) {
			this.putFieldError("hrPayType", "error.existence", "pay type '"
					+ job.getHrPayType() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validatePayGrade(Job job) {
		if (job.getPayGrade() != null
				&& !ValidationUtils.validatePayGrade(job.getPayGrade(), job
						.getEffectiveDate())) {
			this.putFieldError("payGrade", "error.existence", "pay grade '"
					+ job.getPayGrade() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	boolean validatePrimaryIndicator(Job job, Job oldJob) {
		boolean valid = true;
		if (job.getPrimaryIndicator()) {
			//do not block editing of previous primary job
			if(oldJob!=null && oldJob.getPrimaryIndicator()!=null && oldJob.getPrimaryIndicator()){
				return valid;
			}
			Job existingJob = TkServiceLocator.getJobSerivce().getPrimaryJob(job.getPrincipalId(), TKUtils.getCurrentDate());
			if (existingJob != null && existingJob.getPrimaryIndicator()) {
				this.putFieldError("primaryIndicator", "error.primary.job.already.exist", job.getPrincipalId());
				valid = false;
			}
		}
		return valid;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = this.getNewBo();
		if (pbo instanceof Job) {
			Job job = (Job) pbo;
			Job oldJob = (Job) this.getOldBo();
			if (job != null) {
				valid = true;
				valid &= this.validatePrincipalId(job);
				if(!document.isOldBusinessObjectInDocument()){
					valid &= this.validateJobNumber(job);
				}
				valid &= this.validateDepartment(job);
				valid &= this.validateSalGroup(job);
				valid &= this.validateLocation(job);
				valid &= this.validatePayType(job);
				valid &= this.validatePayGrade(job);
				valid &= this.validatePrimaryIndicator(job, oldJob);
			}
		}
		return valid;
	}
}
