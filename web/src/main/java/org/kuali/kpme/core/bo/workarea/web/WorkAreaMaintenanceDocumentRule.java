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
package org.kuali.kpme.core.bo.workarea.web;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberBo;
import org.kuali.kpme.core.role.PositionRoleMemberBo;
import org.kuali.kpme.core.role.PrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.kpme.tklm.time.authorization.AuthorizationValidationUtils;
import org.kuali.kpme.tklm.time.authorization.DepartmentalRule;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("deprecation")
public class WorkAreaMaintenanceDocumentRule extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		
		if (pbo instanceof WorkArea) {
			WorkArea workArea = (WorkArea) pbo;
			
			valid &= validateDefaultOvertimeEarnCode(workArea.getDefaultOvertimeEarnCode(), workArea.getEffectiveLocalDate());
			
			valid &= validateDepartment(workArea.getDept(), workArea.getEffectiveLocalDate());
			
			if (!AuthorizationValidationUtils.hasAccessToWrite((DepartmentalRule)pbo)) {
				String[] params = new String[] {GlobalVariables.getUserSession().getPrincipalName(), workArea.getDept()};
				this.putFieldError("dept", "dept.user.unauthorized", params);
				valid &= false;
			}
			
			valid &= validateRoleMembers(workArea.getPrincipalRoleMembers(), workArea.getPositionRoleMembers(), workArea.getEffectiveLocalDate(), "principalRoleMembers", "positionRoleMembers");
			
			valid &= validateActive(workArea);
		}
		
		return valid;
	}

	@Override
	public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, PersistableBusinessObject line) {
		boolean valid = true;
		
		PersistableBusinessObject pboWorkArea = document.getDocumentBusinessObject();
		PersistableBusinessObject pboTask = line;
		
		if (pboWorkArea instanceof WorkArea && pboTask instanceof Task) {
			WorkArea workArea = (WorkArea) pboWorkArea;
			Task task = (Task) pboTask;
			
			valid &= validateTask(task, workArea);
			
			if (valid) {
				if (task.getTask() == null) {
					Long maxTaskNumberInTable = this.getMaxTaskNumber(workArea);
					Long maxTaskNumberOnPage = 0L;
					if (!workArea.getTasks().isEmpty()) {
						maxTaskNumberOnPage = workArea.getTasks().get(workArea.getTasks().size() - 1).getTask();
					}
					
					if (maxTaskNumberOnPage.compareTo(maxTaskNumberInTable) >= 0) {
						task.setTask(maxTaskNumberOnPage + 1);
					} else {
						task.setTask(maxTaskNumberInTable);
					}
					
					task.setWorkArea(workArea.getWorkArea());
				}
			}
		}
		
		return valid;
	}
	
	protected boolean validateDefaultOvertimeEarnCode(String defaultOvertimeEarnCode, LocalDate asOfDate) {
		boolean valid = true;
		
		if (defaultOvertimeEarnCode != null) {
			if (!ValidationUtils.validateEarnCode(defaultOvertimeEarnCode, asOfDate)) {
				this.putFieldError("defaultOvertimeEarnCode", "error.existence", "earnCode '" + defaultOvertimeEarnCode + "'");
				valid = false;
			} else {
				if (!ValidationUtils.validateEarnCode(defaultOvertimeEarnCode, true, asOfDate)) {
					this.putFieldError("defaultOvertimeEarnCode", "earncode.ovt.required", defaultOvertimeEarnCode);
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	protected boolean validateDepartment(String dept, LocalDate asOfDate) {
		boolean valid = ValidationUtils.validateDepartment(dept, asOfDate);
		
		if (!valid) {
			this.putFieldError("dept", "dept.notfound");
		}
		
		return valid;
	}

	boolean validateRoleMembers(List<? extends PrincipalRoleMemberBo> principalRoleMembers, List<? extends PositionRoleMemberBo> positionRoleMembers, LocalDate effectiveDate, String principalPrefix, String positionPrefix) {
		boolean valid = true;
		
		boolean activeRoleMember = false;
		for (ListIterator<? extends KPMERoleMemberBo> iterator = principalRoleMembers.listIterator(); iterator.hasNext(); ) {
			int index = iterator.nextIndex();
			KPMERoleMemberBo roleMember = iterator.next();
			
			activeRoleMember |= roleMember.isActive();

			valid &= validateRoleMember(roleMember, effectiveDate, principalPrefix, index);
		}
		for (ListIterator<? extends KPMERoleMemberBo> iterator = positionRoleMembers.listIterator(); iterator.hasNext(); ) {
			int index = iterator.nextIndex();
			KPMERoleMemberBo roleMember = iterator.next();
			
			activeRoleMember |= roleMember.isActive();

			valid &= validateRoleMember(roleMember, effectiveDate, positionPrefix, index);
		}

		if (!activeRoleMember) {
			this.putGlobalError("role.required");
			valid = false;
		}

		return valid;
	}
	
	boolean validateRoleMember(KPMERoleMemberBo roleMember, LocalDate effectiveDate, String prefix, int index) {
		boolean valid = true;
		
		Role role = KimApiServiceLocator.getRoleService().getRole(roleMember.getRoleId());
		
		if (StringUtils.equals(role.getName(), KPMERole.APPROVER_DELEGATE.getRoleName())) {
			String propertyNamePrefix = prefix + "[" + index + "].";

			if (roleMember.getActiveToDateValue() == null) {
				this.putFieldError(propertyNamePrefix + "expirationDate", "error.role.expiration.required");
				valid = false;
			} else if (effectiveDate.toDate().compareTo(roleMember.getActiveToDateValue()) >= 0
					|| roleMember.getActiveFromDateValue().compareTo(roleMember.getActiveToDateValue()) >= 0) {
				this.putFieldError(propertyNamePrefix + "expirationDate", "error.role.expiration");
				valid = false;
			} else if (TKUtils.getDaysBetween(roleMember.getActiveFromDate().toLocalDate(), roleMember.getActiveToDate().toLocalDate()) > 180) {
				this.putFieldError(propertyNamePrefix + "expirationDate", "error.role.expiration.duration");
				valid = false;
        	}
		}
		
		return valid;
	}
	
	boolean validateActive(WorkArea workArea) {
		boolean valid = true;
		
		if(!workArea.isActive()){
			List<Assignment> assignments = HrServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea.getWorkArea(), workArea.getEffectiveLocalDate());
			for(Assignment assignment: assignments){
				if(assignment.getWorkArea().equals(workArea.getWorkArea())){
					this.putGlobalError("workarea.active.required");
					valid = false;
					break;
				}
			}
		} else{
			List<Long> inactiveTasks = new ArrayList<Long>();
			for (Task task : workArea.getTasks()) {
				if(!task.isActive()){
					inactiveTasks.add(task.getTask());
				}
			}
			
			if(!inactiveTasks.isEmpty()){
				List<Assignment> assignments = HrServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea.getWorkArea(), workArea.getEffectiveLocalDate());
				for(Assignment assignment : assignments){
					for(Long inactiveTask : inactiveTasks){
						if(inactiveTask.equals(assignment.getTask())){
							this.putGlobalError("task.active.required", inactiveTask.toString());
							valid = false;
						}
					}
				}
			}
			
		}
		
		return valid;
	}
	
	boolean validateTask(Task task, WorkArea workArea) {
		boolean valid = true;

		if (task.getEffectiveDate().compareTo(workArea.getEffectiveDate()) < 0) {
            this.putGlobalError("task.workarea.invalid.effdt", "effective date '" + task.getEffectiveDate().toString() + "'");
            valid = false;
        }
		
		return valid;
	}

	private Long getMaxTaskNumber(WorkArea workArea) {
		Long task = new Long("100");
		
		Task maxTask = HrServiceLocator.getTaskService().getMaxTask();
		
		if (maxTask != null) {
			task = maxTask.getTask() + 1;
		}
		
		return task;
	}
}
