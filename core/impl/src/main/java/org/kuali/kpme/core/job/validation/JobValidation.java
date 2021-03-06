/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.job.validation;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

import java.util.List;

public class JobValidation extends MaintenanceDocumentRuleBase {

	private boolean validatePrincipalId(JobBo job) {
		if (job.getPrincipalId() != null
				&& !ValidationUtils.validatePrincipalId(job.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + job.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateJobNumber(JobBo job) {
		if (job.getJobNumber() != null) {
			JobContract jobObj = HrServiceLocator.getJobService().getJob(
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

	protected boolean validateDepartment(JobBo job) {
		if (job.getDept() != null && 
			!ValidationUtils.validateDepartment(job.getDept(), job.getGroupKeyCode(), job.getEffectiveLocalDate())) {
			this.putFieldError("dept", "error.existence", "department '"
					+ job.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateSalGroup(JobBo job) {
		if (!ValidationUtils.validateSalGroup(job.getHrSalGroup(), job
				.getEffectiveLocalDate())) {
			this.putFieldError("hrSalGroup", "error.existence", "Salgroup '"
					+ job.getHrSalGroup() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateLocation(JobBo job) {
		if (job.getLocation() != null
				&& !ValidationUtils.validateLocation(job.getLocation(), job.getEffectiveLocalDate())) {
			this.putFieldError("location", "error.existence", "location '"
					+ job.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validatePayType(JobBo job) {
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

	boolean validatePayGrade(JobBo job) {
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
	
	boolean validatePrimaryIndicator(JobBo job, JobBo oldJob) {
		boolean valid = true;
		if (job.isPrimaryJob()) {
			//do not block editing of previous primary job
			if(oldJob!=null && oldJob.isPrimaryJob()!=null && oldJob.isPrimaryJob()){
				return valid;
			}
			JobContract existingJob = HrServiceLocator.getJobService().getPrimaryJob(job.getPrincipalId(), LocalDate.now());
			if (existingJob != null && existingJob.isPrimaryJob()) {
				this.putFieldError("primaryIndicator", "error.primary.job.already.exist", job.getPrincipalId());
				valid = false;
			}
		}
		return valid;
	}
	
	// KPME-1129 Kagata
	// This method determines if the job can be inactivated or not
	boolean validateInactivation(JobBo job){
		
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

	private boolean validateConsistentLocation(JobBo job) {
		String department = job.getDept();
		Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, job.getGroupKeyCode(), job.getEffectiveLocalDate());
		Location location = HrServiceLocator.getLocationService().getLocation(job.getLocation(), job.getEffectiveLocalDate());
		if(departmentObj != null && location != null) {
			if(departmentObj.getGroupKey().getLocationId().equals(location.getLocation())) {
				return true;
			}
			else {
				this.putFieldError("location", "job.location.inconsistent", departmentObj.getGroupKey().getLocationId());
			}
		}
		return false;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		if (pbo instanceof JobBo) {
			JobBo job = (JobBo) pbo;
			JobBo oldJob = (JobBo) this.getOldDataObject();
			if (job != null) {
				valid = true;
				valid &= this.validatePrincipalId(job);
				if(!document.isOldDataObjectInDocument()){
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
				valid &= this.validateConsistentLocation(job);
			}
		}
		return valid;
	}
}
