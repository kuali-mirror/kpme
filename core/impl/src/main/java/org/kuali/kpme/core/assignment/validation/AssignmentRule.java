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
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.api.task.Task;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.account.AssignmentAccountBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("deprecation")
public class AssignmentRule extends MaintenanceDocumentRuleBase {

	protected boolean validateWorkArea(AssignmentBo assignment) {
		boolean valid = true;
		if (assignment.getWorkArea() != null) {
			if (!ValidationUtils.validateWorkArea(assignment.getWorkArea(),
					assignment.getEffectiveLocalDate())) {
				this.putFieldError("dataObject.workArea", "error.existence", "workArea '"
						+ assignment.getWorkArea() + "'");
				valid = false;
			} else {
				int count = HrServiceLocator.getWorkAreaService().getWorkAreaCount(assignment.getDept(), assignment.getWorkArea());
				valid = (count > 0);
				if (!valid) {
					this.putFieldError("dataObject.workArea", "dept.workarea.invalid.sync");
				}
			}
		}
		return valid;
	}
	
	protected boolean validateTask(AssignmentBo assignment) {
		boolean valid = false;
		//task by default is zero so if non zero validate against existing taskss
		if (assignment.getTask() != null && !assignment.getTask().equals(0L)) {
//			TaskContract task = HrServiceLocator.getTaskService().getTask(assignment.getTask(), assignment.getEffectiveLocalDate());
			String workarea = "";
			if(assignment.getWorkArea() != null) {
				workarea = assignment.getWorkArea().toString();
			}
			List<Task> tasks = HrServiceLocator.getTaskService().getTasks(assignment.getTask().toString(), null, workarea, null, assignment.getEffectiveLocalDate());
			for(Task task : tasks) {
				if(workarea.equals(task.getWorkArea().toString())) {
					valid = true;
					return valid;
				}
			}
		} else {
			valid = true;
			return valid;
		}
		this.putFieldError("dataObject.task", "task.workarea.invalid.sync");
		return valid;
	}

	protected boolean validateDepartment(AssignmentBo assignment) {
		boolean valid = true;
		if (assignment.getDept() != null) {
				int count = HrServiceLocator.getJobService().getJobCount(null, assignment.getJobNumber(), assignment.getDept());
				valid = (count > 0);
				if (!valid) {
					this.putFieldError("dataObject.dept", "dept.jobnumber.invalid.sync");
				}
			 
		}
		return valid;
	}

	protected boolean validateJob(AssignmentBo assignment) {
		boolean valid = false;
		LOG.debug("Validating job: " + assignment.getPrincipalId() +" Job number: "+assignment.getJobNumber());
		JobContract job = HrServiceLocator.getJobService().getJob(
				assignment.getPrincipalId(), assignment.getJobNumber(),
				assignment.getEffectiveLocalDate(), false);
		if (job != null) {
			valid = true;

			LOG.debug("found job.");
		} else {
			this.putFieldError("dataObject.jobNumber", "error.existence", "jobNumber '"
					+ assignment.getJobNumber() + "'");
		}
		return valid;
	}

	protected boolean validatePercentagePerEarnCode(AssignmentBo assignment) {
		boolean valid = true;
		LOG.debug("Validating PercentagePerEarnCode: ");
		List<AssignmentAccountBo> assignmentAccounts = assignment
				.getAssignmentAccounts();
		Set<String> invalidEarnCodes = null;
		if (assignmentAccounts != null && assignment.isActive()) {
			Map<String, Integer> earnCodePercent = new HashMap<String, Integer>();
			for (AssignmentAccountBo account : assignmentAccounts) {
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
				for (AssignmentAccountBo account : assignmentAccounts) {
					if (invalidEarnCodes.contains(account.getEarnCode())) {
						this.putFieldError("dataObject.assignmentAccounts[" + index
								+ "].percent", "error.percentage.earncode");
					}
					index++;
				}
			}
		}
		return valid;
	}

	protected boolean validateEarnCode(AssignmentAccountBo assignmentAccount, LocalDate assignmentEffectiveDate) {
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
	
	protected boolean validateRegPayEarnCode(AssignmentBo assignment) {
		boolean valid = false;
		LOG.debug("Validating Regular pay EarnCodes: " + assignment.getAssignmentAccounts().size());
		for(AssignmentAccountBo assignmentAccount : assignment.getAssignmentAccounts()){
			if(assignment.getJobNumber()!=null && assignment.getPrincipalId()!=null){
				JobContract job = HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveLocalDate(), false);
				if(job !=null){
					PayType payType = HrServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), assignment.getEffectiveLocalDate());
					if(StringUtils.equals(assignmentAccount.getEarnCode(), payType.getRegEarnCode())){
						valid = true;
						break;
					}
					
				}
			}
		}
		if(!valid) {
			this.putFieldError("dataObject.assignmentAccounts", "earncode.regular.pay.required");
		}
		return valid;
	}
	
	// KPME-2780 This is to validate accounts in the collection 
	protected boolean validateAccounts(AssignmentBo assignment) {
		boolean valid = false;
		LOG.debug("Validating Accounts: " + assignment.getAssignmentAccounts().size());
		for(AssignmentAccountBo assignmentAccount : assignment.getAssignmentAccounts()){
			valid = ValidationUtils.validateAccount(assignmentAccount.getFinCoaCd(), assignmentAccount.getAccountNbr());
			if(!valid) {
				this.putFieldError("dataObject.assignmentAccounts", "error.existence", "Account Number '" + assignmentAccount.getAccountNbr() + "'");
				break;
			}
		}
		return valid;
	}

	protected boolean validateAccount(AssignmentAccountBo assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating Account: " + assignmentAccount.getAccountNbr());
		valid = ValidationUtils.validateAccount(assignmentAccount.getFinCoaCd(),assignmentAccount.getAccountNbr());
		if (!valid) {
			this.putGlobalError("error.existence", "Account Number '"
					+ assignmentAccount.getAccountNbr() + "'");
		}
		return valid;
	}

	private boolean validateSubAccount(AssignmentAccountBo assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating Sub-Account: " + assignmentAccount.getSubAcctNbr());
		valid = ValidationUtils.validateSubAccount(assignmentAccount.getSubAcctNbr(),assignmentAccount.getAccountNbr(), assignmentAccount.getFinCoaCd());
		if (!valid) {
			this.putGlobalError("error.existence", "Sub-Account Number '"
					+ assignmentAccount.getSubAcctNbr() + "'");
		}
		return valid;
	}

	protected boolean validateObjectCode(AssignmentAccountBo assignmentAccount, LocalDate assignmentEffectiveDate) {
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

	protected boolean validateSubObjectCode(AssignmentAccountBo assignmentAccount, LocalDate assignmentEffectiveDate) {
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
	
	protected boolean validateHasAccounts(AssignmentBo assign){
		if(assign.getAssignmentAccounts().isEmpty()){
			this.putGlobalError("error.assign.must.have.one.or.more.account");
			return false;
		}
		return true;
	}
	
	protected boolean validateOnePrimaryAssignment(AssignmentBo assignment, AssignmentBo oldAssignment) {
		if (assignment.isPrimaryAssign()) {
			//do not block editing of previous primary assignment
			if(oldAssignment!=null && oldAssignment.isPrimaryAssign()){
				return true;
			}
			JobContract job = HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveLocalDate(), false);
			if(job != null && job.isEligibleForLeave()) {
				List<Assignment> assignList = HrServiceLocator.getAssignmentService().getActiveAssignmentsForJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveLocalDate());
				for(Assignment anAssignment : assignList) {
					if(anAssignment != null && anAssignment.isPrimaryAssign()) {
						this.putFieldError("dataObject.primaryAssign", "error.primary.assignment.exists.for.leaveJob", assignment.getJobNumber().toString());
						return false;
					}
				}
			}
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
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		if (pbo instanceof AssignmentBo) {
			AssignmentBo assignment = (AssignmentBo) pbo;
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
//					valid &= this.validateAccounts(assignment); // KPME-2780
					// Validate Assignment Accounts
					for (ListIterator<? extends AssignmentAccountBo> iterator =  assignment.getAssignmentAccounts().listIterator() ; iterator.hasNext();){
						int index = iterator.nextIndex();
						AssignmentAccountBo assignmentAccountBo = iterator.next();
						valid &= this.validateAssignmentAccount(assignmentAccountBo, assignment, index);
					}
				}
				// only allow one primary assignment for the leave eligible job
				if(assignment.isPrimaryAssign()) {
					AssignmentBo oldAssignment = (AssignmentBo) this.getOldDataObject();
					valid &= this.validateOnePrimaryAssignment(assignment, oldAssignment);
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
		PersistableBusinessObject assignmentPbo = (PersistableBusinessObject) this.getNewDataObject();
		PersistableBusinessObject pbo = line;
		if (pbo instanceof AssignmentAccountBo) {
			AssignmentAccountBo assignmentAccount = (AssignmentAccountBo) pbo;
			if (assignmentAccount != null) {
				if(assignmentPbo instanceof AssignmentBo) {
					AssignmentBo assignment = (AssignmentBo) assignmentPbo;
					valid = true;
					valid &= this.validateEarnCode(assignmentAccount, assignment.getEffectiveLocalDate());
					valid &= this.validateAccount(assignmentAccount);
					valid &= this.validateObjectCode(assignmentAccount, assignment.getEffectiveLocalDate());
					valid &= this.validateSubObjectCode(assignmentAccount, assignment.getEffectiveLocalDate());
					if(assignmentAccount.getSubAcctNbr() != null && !assignmentAccount.getSubAcctNbr().isEmpty()) {
						valid &= this.validateSubAccount(assignmentAccount);
					}
				}
			}
		}
		return valid;
	}
	
	
	private boolean validateAssignmentAccount(AssignmentAccountBo assignmentAccount, AssignmentBo assignmentObj, int index) {
		boolean isValid = true;
		String prefix = "assignmentAccounts";
		String propertyNamePrefix = prefix + "[" + index + "].";
		if(StringUtils.isNotEmpty(assignmentAccount.getEarnCode())) {
			boolean valid = ValidationUtils.validateEarnCode(assignmentAccount.getEarnCode(), assignmentObj.getEffectiveLocalDate());
			if(!valid) {
				this.putFieldError(propertyNamePrefix + "earnCode","error.existence", "earn code '"+ assignmentAccount.getEarnCode() + "'");
				isValid = false;
			}
		}
		if(StringUtils.isNotEmpty(assignmentAccount.getAccountNbr())) {
			boolean valid = ValidationUtils.validateAccount(assignmentAccount.getFinCoaCd(),assignmentAccount.getAccountNbr());
			if(!valid) {
				this.putFieldError(propertyNamePrefix + "accountNbr","error.existence", "Account Number '"+ assignmentAccount.getAccountNbr() + "'");
				isValid = false;
			}
		}
		if(StringUtils.isNotEmpty(assignmentAccount.getFinObjectCd())) {
			boolean valid = ValidationUtils.validateObjectCode(assignmentAccount.getFinObjectCd(),assignmentAccount.getFinCoaCd(),null);
			if (!valid) {
				this.putFieldError(propertyNamePrefix + "finObjectCd","error.existence", "Object Code '"+ assignmentAccount.getFinObjectCd() + "'");
				isValid = false;
			}			
		}
		if (StringUtils.isNotEmpty(assignmentAccount.getFinSubObjCd())) {
			boolean valid = ValidationUtils.validateSubObjectCode(String.valueOf(assignmentObj.getEffectiveLocalDate().getYear()),assignmentAccount.getFinCoaCd(),
					assignmentAccount.getAccountNbr(), assignmentAccount.getFinObjectCd(), assignmentAccount.getFinSubObjCd());
			if (!valid) {
				this.putFieldError(propertyNamePrefix + "finSubObjCd","error.existence", "SubObject Code '"+ assignmentAccount.getFinSubObjCd() + "'");
				isValid = false;
			}
		} 
		if(assignmentAccount.getSubAcctNbr() != null && StringUtils.isNotEmpty(assignmentAccount.getSubAcctNbr())) {
			boolean valid = ValidationUtils.validateSubAccount(assignmentAccount.getSubAcctNbr(),assignmentAccount.getAccountNbr(), assignmentAccount.getFinCoaCd());
			if (!valid) {
				this.putFieldError(propertyNamePrefix + "subAcctNbr", "error.existence", "Sub-Account Number '"+ assignmentAccount.getSubAcctNbr() + "'");
				isValid = false;
			}
		}
		
		return isValid;
	}
    

	
}
