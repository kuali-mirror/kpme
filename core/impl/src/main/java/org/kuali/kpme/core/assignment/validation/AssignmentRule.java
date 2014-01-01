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
package org.kuali.kpme.core.assignment.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.account.AssignmentAccount;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.Task;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

@SuppressWarnings("deprecation")
public class AssignmentRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(Assignment assignment) {
		boolean valid = true;
		if (assignment.getWorkArea() != null) {
			if (!ValidationUtils.validateWorkArea(assignment.getWorkArea(),
					assignment.getEffectiveLocalDate())) {
				this.putFieldError("workArea", "error.existence", "workArea '"
						+ assignment.getWorkArea() + "'");
				valid = false;
			} else {
				int count = HrServiceLocator.getWorkAreaService().getWorkAreaCount(assignment.getDept(), assignment.getWorkArea());
				valid = (count > 0);
				if (!valid) {
					this.putFieldError("workArea", "dept.workarea.invalid.sync");
				}
			}
		}
		return valid;
	}
	
	protected boolean validateTask(Assignment assignment) {
		boolean valid = true;
		//task by default is zero so if non zero validate against existing taskss
		if (assignment.getTask() != null && !assignment.getTask().equals(0L)) {
			TaskContract task = HrServiceLocator.getTaskService().getTask(assignment.getTask(), assignment.getEffectiveLocalDate());
			if(task != null) {
				if(task.getWorkArea() == null || !task.getWorkArea().equals(assignment.getWorkArea())) {
					this.putFieldError("task", "task.workarea.invalid.sync");
					valid = false;
				}
			} 
		}
		return valid;
	}

	protected boolean validateDepartment(Assignment assignment) {
		boolean valid = true;
		if (assignment.getDept() != null) {
				int count = HrServiceLocator.getJobService().getJobCount(null, assignment.getJobNumber(), assignment.getDept());
				valid = (count > 0);
				if (!valid) {
					this.putFieldError("dept", "dept.jobnumber.invalid.sync");
				}
			 
		}
		return valid;
	}

	protected boolean validateJob(Assignment assignment) {
		boolean valid = false;
		LOG.debug("Validating job: " + assignment.getPrincipalId() +" Job number: "+assignment.getJobNumber());
		JobContract job = HrServiceLocator.getJobService().getJob(
				assignment.getPrincipalId(), assignment.getJobNumber(),
				assignment.getEffectiveLocalDate(), false);
		// Job job =
		// KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Job.class,
		// assignment.getJob().getHrJobId());
		if (job != null) {
			valid = true;

			LOG.debug("found job.");
		} else {
			this.putFieldError("jobNumber", "error.existence", "jobNumber '"
					+ assignment.getJobNumber() + "'");
		}
		return valid;
	}

	protected boolean validatePercentagePerEarnCode(Assignment assignment) {
		boolean valid = true;
		LOG.debug("Validating PercentagePerEarnCode: ");
		List<AssignmentAccount> assignmentAccounts = assignment
				.getAssignmentAccounts();
		Set<String> invalidEarnCodes = null;
		if (assignmentAccounts != null && assignment.isActive()) {
			Map<String, Integer> earnCodePercent = new HashMap<String, Integer>();
			for (AssignmentAccount account : assignmentAccounts) {
				if (account.getPercent() != null && account.isActive()) {
					int percent = 0;
					if (earnCodePercent.containsKey(account.getEarnCode())) {
						percent = earnCodePercent.get(account.getEarnCode());
					}
					percent += account.getPercent().toBigInteger().intValue();
					earnCodePercent.put(account.getEarnCode(), percent);
				}
			}
			//Iterator<String> itr = earnCodePercent.keySet().iterator();
            for (Map.Entry<String, Integer> entry : earnCodePercent.entrySet()) {
			//while (itr.hasNext()) {
				String earnCode = entry.getKey();
				if (entry.getValue() != 100) {
					if (invalidEarnCodes == null) {
						invalidEarnCodes = new HashSet<String>();
					}
					invalidEarnCodes.add(earnCode);
					valid = false;
				}
			}
			if (!valid) {
				int index = 0;
				for (AssignmentAccount account : assignmentAccounts) {
					if (invalidEarnCodes.contains(account.getEarnCode())) {
						this.putFieldError("assignmentAccounts[" + index
								+ "].percent", "error.percentage.earncode");
					}
					index++;
				}
			}
		}
		return valid;
	}

	protected boolean validateEarnCode(AssignmentAccount assignmentAccount, LocalDate assignmentEffectiveDate) {
		boolean valid = false;
		LOG.debug("Validating EarnCode: " + assignmentAccount.getEarnCode());
		EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(
				assignmentAccount.getEarnCode(), assignmentEffectiveDate);
		if (earnCode != null) {

			valid = true;
			LOG.debug("found earnCode.");
		} else {
			this.putGlobalError("error.existence", "earn code '"
					+ assignmentAccount.getEarnCode() + "'");
		}
		return valid;
	}
	
	protected boolean validateRegPayEarnCode(Assignment assignment) {
		boolean valid = false;
		LOG.debug("Validating Regular pay EarnCodes: " + assignment.getAssignmentAccounts().size());
		for(AssignmentAccount assignmentAccount : assignment.getAssignmentAccounts()){
			if(assignment.getJobNumber()!=null && assignment.getPrincipalId()!=null){
				JobContract job = HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveLocalDate(), false);
				if(job !=null){
					PayTypeContract payType = HrServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), assignment.getEffectiveLocalDate());
					if(StringUtils.equals(assignmentAccount.getEarnCode(), payType.getRegEarnCode())){
						valid = true;
						break;
					}
					
				}
			}
		}
		if(!valid) {
			this.putFieldError("assignmentAccounts", "earncode.regular.pay.required");
		}
		return valid;
	}
	
	// KPME-2780 This is to validate accounts in the collection 
	protected boolean validateAccounts(Assignment assignment) {
		boolean valid = false;
		LOG.debug("Validating Accounts: " + assignment.getAssignmentAccounts().size());
		for(AssignmentAccount assignmentAccount : assignment.getAssignmentAccounts()){
			valid = ValidationUtils.validateAccount(assignmentAccount.getFinCoaCd(), assignmentAccount.getAccountNbr());
			if(!valid) {
				this.putFieldError("assignmentAccounts", "error.existence", "Account Number '" + assignmentAccount.getAccountNbr() + "'");
				break;
			}
		}
		return valid;
	}

	protected boolean validateAccount(AssignmentAccount assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating Account: " + assignmentAccount.getAccountNbr());
		valid = ValidationUtils.validateAccount(assignmentAccount.getFinCoaCd(),assignmentAccount.getAccountNbr());
		if (!valid) {
			this.putGlobalError("error.existence", "Account Number '"
					+ assignmentAccount.getAccountNbr() + "'");
		}
		return valid;
	}

	private boolean validateSubAccount(AssignmentAccount assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating Sub-Account: " + assignmentAccount.getSubAcctNbr());
		valid = ValidationUtils.validateSubAccount(assignmentAccount.getSubAcctNbr(),assignmentAccount.getAccountNbr(), assignmentAccount.getFinCoaCd());
		if (!valid) {
			this.putGlobalError("error.existence", "Sub-Account Number '"
					+ assignmentAccount.getSubAcctNbr() + "'");
		}
		return valid;
	}

	protected boolean validateObjectCode(AssignmentAccount assignmentAccount, LocalDate assignmentEffectiveDate) {
		boolean valid = false;
		LOG.debug("Validating ObjectCode: "
				+ assignmentAccount.getFinObjectCd());
		valid = ValidationUtils.validateObjectCode(assignmentAccount.getFinObjectCd(),
												assignmentAccount.getFinCoaCd(),
												null);
		if (!valid) {
			this.putGlobalError("error.existence", "Object Code '"
					+ assignmentAccount.getFinObjectCd() + "'");
		}
		return valid;
	}

	protected boolean validateSubObjectCode(AssignmentAccount assignmentAccount, LocalDate assignmentEffectiveDate) {
		boolean valid = false;
		LOG.debug("Validating SubObjectCode: "
				+ assignmentAccount.getFinSubObjCd());
		if (assignmentAccount.getFinSubObjCd() != null) {
			valid = ValidationUtils.validateSubObjectCode(String.valueOf(assignmentEffectiveDate.getYear()),
																		assignmentAccount.getFinCoaCd(),
																		assignmentAccount.getAccountNbr(),
																		assignmentAccount.getFinObjectCd(),
																		assignmentAccount.getFinSubObjCd());
			if (!valid) {
				this.putGlobalError("error.existence", "SubObject Code '"
						+ assignmentAccount.getFinSubObjCd() + "'");
			}
		} else {
			valid = true;
		}
		return valid;
	}
	
	protected boolean validateHasAccounts(Assignment assign){
		if(assign.getAssignmentAccounts().isEmpty()){
			this.putGlobalError("error.assign.must.have.one.or.more.account");
			return false;
		}
		return true;
	}
	
/*	protected boolean validateActiveFlag(Assignment assign){
		if(!assign.isActive()) {
			List<TimeBlock> tbList = TkServiceLocator.getTimeBlockService().getTimeBlocksForAssignment(assign);
			if(!tbList.isEmpty()) {
				DateTime tbEndDate = tbList.get(0).getEndDateTime();
				for(TimeBlock tb : tbList) {
					if(tb.getEndDateTime().isAfter(tbEndDate)) {
						tbEndDate = tb.getEndDateTime();			// get the max end date
					}
				}
				if(tbEndDate.equals(assign.getEffectiveLocalDate().toDateTimeAtStartOfDay()) || tbEndDate.isAfter(assign.getEffectiveLocalDate().toDateTimeAtStartOfDay())) {
					this.putFieldError("active", "error.assignment.timeblock.existence", tbEndDate.toString());
					return false;
				}
			}
		}
		return true;
	}*/

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Assignment");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof Assignment) {
			Assignment assignment = (Assignment) pbo;
			if (assignment != null) {
				valid = true;
				valid &= this.validateWorkArea(assignment);
				valid &= this.validateTask(assignment);
				valid &= this.validateJob(assignment);
				valid &= this.validateDepartment(assignment);
				valid &= this.validatePercentagePerEarnCode(assignment);
				valid &= this.validateHasAccounts(assignment);
				//valid &= this.validateActiveFlag(assignment);
				if(!assignment.getAssignmentAccounts().isEmpty()) {
					valid &= this.validateRegPayEarnCode(assignment);
					valid &= this.validateAccounts(assignment); // KPME-2780
				}
			}
		}

		return valid;
	}

	@Override
	public boolean processCustomAddCollectionLineBusinessRules(
			MaintenanceDocument document, String collectionName,
			PersistableBusinessObject line) {
		boolean valid = false;
		LOG.debug("entering custom add assignment account business rules");
		PersistableBusinessObject assignmentPbo = (PersistableBusinessObject) this.getNewBo();
		PersistableBusinessObject pbo = line;
		if (pbo instanceof AssignmentAccount) {
			AssignmentAccount assignmentAccount = (AssignmentAccount) pbo;
			if (assignmentAccount != null) {
				if(assignmentPbo instanceof Assignment) {
					Assignment assignment = (Assignment) assignmentPbo;
					valid = true;
					valid &= this.validateEarnCode(assignmentAccount, assignment.getEffectiveLocalDate());
					valid &= this.validateAccount(assignmentAccount);
					valid &= this.validateObjectCode(assignmentAccount, assignment.getEffectiveLocalDate());
					valid &= this.validateSubObjectCode(assignmentAccount, assignment.getEffectiveLocalDate());
					if(assignmentAccount.getSubAcctNbr() != null) {
						valid &= this.validateSubAccount(assignmentAccount);
					}
				}
			}
		}
		return valid;
	}

}
