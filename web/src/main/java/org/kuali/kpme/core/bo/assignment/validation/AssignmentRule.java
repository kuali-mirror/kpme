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
package org.kuali.kpme.core.bo.assignment.validation;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.account.AssignmentAccount;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.bo.kfs.coa.businessobject.ObjectCode;
import org.kuali.kpme.core.bo.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;

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
			Task task = HrServiceLocator.getTaskService().getTask(assignment.getTask(), assignment.getEffectiveLocalDate());
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
		Job job = HrServiceLocator.getJobService().getJob(
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

	protected boolean validateEarnCode(AssignmentAccount assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating EarnCode: " + assignmentAccount.getEarnCode());
		EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(
				assignmentAccount.getEarnCode(), LocalDate.now());
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
				Job job = HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getEffectiveLocalDate(), false);
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
			this.putFieldError("assignmentAccounts", "earncode.regular.pay.required");
		}
		return valid;
	}

	protected boolean validateAccount(AssignmentAccount assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating Account: " + assignmentAccount.getAccountNbr());
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("accountNumber", assignmentAccount.getAccountNbr());
		Collection account = KRADServiceLocator.getBusinessObjectService()
				.findMatching(Account.class, fields);
		valid = account.size() > 0;
		if (!valid) {
			this.putGlobalError("error.existence", "Account Number '"
					+ assignmentAccount.getAccountNbr() + "'");
		}
		return valid;
	}

	protected boolean validateObjectCode(AssignmentAccount assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating ObjectCode: "
				+ assignmentAccount.getFinObjectCd());
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("financialObjectCode", assignmentAccount.getFinObjectCd());
		Collection objectCode = KRADServiceLocator.getBusinessObjectService()
				.findMatching(ObjectCode.class, fields);
		valid = objectCode.size() > 0;
		if (!valid) {
			this.putGlobalError("error.existence", "Object Code '"
					+ assignmentAccount.getFinObjectCd() + "'");
		}
		return valid;
	}

	protected boolean validateSubObjectCode(AssignmentAccount assignmentAccount) {
		boolean valid = false;
		LOG.debug("Validating SubObjectCode: "
				+ assignmentAccount.getFinSubObjCd());
		if (assignmentAccount.getFinSubObjCd() != null) {
			Map<String, String> fields = new HashMap<String, String>();
			fields.put("financialSubObjectCode", assignmentAccount
					.getFinSubObjCd());
			Collection subObjectCode = KRADServiceLocator.getBusinessObjectService()
					.findMatching(SubObjectCode.class, fields);
			valid = subObjectCode.size() > 0;
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
	
	protected boolean validateActiveFlag(Assignment assign){
		if(!assign.isActive()) {
			List<TimeBlock> tbList = TkServiceLocator.getTimeBlockService().getTimeBlocksForAssignment(assign);
			if(!tbList.isEmpty()) {
				Date tbEndDate = tbList.get(0).getEndDate();
				for(TimeBlock tb : tbList) {
					if(tb.getEndDate().after(tbEndDate)) {
						tbEndDate = tb.getEndDate();			// get the max end date
					}
				}
				if(tbEndDate.equals(assign.getEffectiveDate()) || tbEndDate.after(assign.getEffectiveDate())) {
					this.putFieldError("active", "error.assignment.timeblock.existence", tbEndDate.toString());
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for DeptLunchRule");
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
				valid &= this.validateActiveFlag(assignment);
				if(!assignment.getAssignmentAccounts().isEmpty()) {
					valid &= this.validateRegPayEarnCode(assignment);	
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
		LOG.debug("entering custom validation for DeptLunchRule");
		PersistableBusinessObject pbo = line;
		if (pbo instanceof AssignmentAccount) {

			AssignmentAccount assignmentAccount = (AssignmentAccount) pbo;
			if (assignmentAccount != null) {
				valid = true;
				valid &= this.validateEarnCode(assignmentAccount);
				valid &= this.validateAccount(assignmentAccount);
				valid &= this.validateObjectCode(assignmentAccount);
				valid &= this.validateSubObjectCode(assignmentAccount);
			}
		}
		return valid;
	}

}
