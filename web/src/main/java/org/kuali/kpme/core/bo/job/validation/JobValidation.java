/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.bo.job.validation;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.ValidationUtils;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

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
			Job jobObj = HrServiceLocator.getJobService().getJob(
					job.getPrincipalId(), job.getJobNumber(),
					job.getEffectiveLocalDate(), false);
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
						.getEffectiveLocalDate())) {
			this.putFieldError("dept", "error.existence", "department '"
					+ job.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateSalGroup(Job job) {
		if (!ValidationUtils.validateSalGroup(job.getHrSalGroup(), job
				.getEffectiveLocalDate())) {
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
						.getEffectiveLocalDate())) {
			this.putFieldError("hrPayType", "error.existence", "pay type '"
					+ job.getHrPayType() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validatePayGrade(Job job) {
		if (job.getPayGrade() != null
				&& !ValidationUtils.validatePayGrade(job.getPayGrade(), job.getHrSalGroup(), job
						.getEffectiveLocalDate())) {
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
			Job existingJob = HrServiceLocator.getJobService().getPrimaryJob(job.getPrincipalId(), LocalDate.now());
			if (existingJob != null && existingJob.getPrimaryIndicator()) {
				this.putFieldError("primaryIndicator", "error.primary.job.already.exist", job.getPrincipalId());
				valid = false;
			}
		}
		return valid;
	}
	
	// KPME-1129 Kagata
	// This method determines if the job can be inactivated or not
	boolean validateInactivation(Job job){
		
		// Get a list of active assignments based on principalId, jobNumber and current date.
		// If the list is not null, there are active assignments and the job can't be inactivated, so return false, otherwise true
		if(!job.isActive()) {
			//this has to use the effective date of the job passed in
			List<Assignment> aList = HrServiceLocator.getAssignmentService().getActiveAssignmentsForJob(job.getPrincipalId(), job.getJobNumber(), job.getEffectiveLocalDate());
			if (aList != null && aList.size() > 0) {
				// error.job.inactivate=Can not inactivate job number {0}.  It is used in active assignments.
				this.putFieldError("active", "error.job.inactivate", job.getJobNumber().toString());
				return false;
			} 
		}

		return true;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
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
				// KPME-1129 Kagata
				valid &= this.validateInactivation(job);
			}
		}
		return valid;
	}
}
