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
package org.kuali.kpme.core.workarea.validation;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.task.Task;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberBo;
import org.kuali.kpme.core.role.PositionRoleMemberBo;
import org.kuali.kpme.core.role.PrincipalRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("deprecation")
public class WorkAreaMaintenanceDocumentRule extends MaintenanceDocumentRuleBase {

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = true;

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		
		if (pbo instanceof WorkAreaBo) {
			WorkAreaBo workArea = (WorkAreaBo) pbo;
			
			valid &= validateDefaultOvertimeEarnCode(workArea.getDefaultOvertimeEarnCode(), workArea.getEffectiveLocalDate());
			valid &= validateDepartment(workArea.getDept(), workArea.getGroupKeyCode(), workArea.getEffectiveLocalDate());
			valid &= validateRoleMembers(workArea.getPrincipalRoleMembers(), workArea.getPositionRoleMembers(), workArea.getEffectiveLocalDate(), "principalRoleMembers", "positionRoleMembers");
			valid &= validateActive(workArea);
		}

		return valid;
	}

	@Override
	protected boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, Object line) {
		boolean valid = true;

		PersistableBusinessObject pboWorkArea = (WorkAreaBo)document.getDocumentDataObject();
		Object pboTask = line;

		if (pboWorkArea instanceof WorkAreaBo && pboTask instanceof TaskBo) {
			WorkAreaBo workArea = (WorkAreaBo) pboWorkArea;
            for(TaskBo task : workArea.getTasks()){
                valid &= validateTask(task,workArea);
            }
	    }
		
		//TODO: Do we really need to use member type, id, role id? If there are duplicate role names listed in the drop downs, this is just going to cause confusion...
		if(line instanceof WorkAreaPrincipalRoleMemberBo) {
			WorkAreaPrincipalRoleMemberBo roleMember = (WorkAreaPrincipalRoleMemberBo) line;
			WorkAreaBo location = (WorkAreaBo) document.getDocumentDataObject();
			List<WorkAreaPrincipalRoleMemberBo> existingRoleMembers = location.getPrincipalRoleMembers();
			for(ListIterator<WorkAreaPrincipalRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "roleMembers[" + index + "].";
				WorkAreaPrincipalRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPrincipalId(),roleMember.getPrincipalId())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
						}
					}
				}
			}
			existingRoleMembers = location.getInactivePrincipalRoleMembers();
			for(ListIterator<WorkAreaPrincipalRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "inactiveRoleMembers[" + index + "].";
				WorkAreaPrincipalRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPrincipalId(),roleMember.getPrincipalId())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
						}
					}
				}
			}
		}
		
		//TODO: Do we really need to use member type, id, role id? If there are duplicate role names listed in the drop downs, this is just going to cause confusion...
		if(line instanceof WorkAreaPositionRoleMemberBo) {
			WorkAreaPositionRoleMemberBo roleMember = (WorkAreaPositionRoleMemberBo) line;
			WorkAreaBo location = (WorkAreaBo) document.getDocumentDataObject();
			List<WorkAreaPositionRoleMemberBo> existingRoleMembers = location.getPositionRoleMembers();
			for(ListIterator<WorkAreaPositionRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "roleMembers[" + index + "].";
				WorkAreaPositionRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPositionNumber(),roleMember.getPositionNumber())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.active.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.active.duplicate");
						}
					}
				}
			}
			existingRoleMembers = location.getInactivePositionRoleMembers();
			for(ListIterator<WorkAreaPositionRoleMemberBo> iter = existingRoleMembers.listIterator(); iter.hasNext(); ) {
				int index = iter.nextIndex();
	            String prefix = "inactiveRoleMembers[" + index + "].";
				WorkAreaPositionRoleMemberBo existingRoleMember = iter.next();
				if(StringUtils.equals(existingRoleMember.getPositionNumber(),roleMember.getPositionNumber())) {
					if(StringUtils.equals(existingRoleMember.getRoleName(),roleMember.getRoleName())) {
						if(existingRoleMember.getActiveToDate() != null) {
							if(roleMember.getActiveFromDate().compareTo(existingRoleMember.getActiveToDate()) < 0) {
								valid &= false;
								this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
								this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
							}
						}
						else {
							valid &= false;
							this.putFieldError(prefix + "effectiveDate", "error.role.inactive.existence");
							this.putFieldError("add.roleMembers.effectiveDate", "error.role.inactive.duplicate");
						}
					}
				}
			}
		}
		
		return valid;
	}
	
	private boolean isBetweenEffectiveDateRange(Object existingRoleMember, Object roleMember) {
		
		boolean isBetweenEffectiveDateRange = true;
		boolean isFromDateBetween = true;
		boolean isToDateBetween = true;
		if(existingRoleMember instanceof WorkAreaPrincipalRoleMemberBo && roleMember instanceof WorkAreaPrincipalRoleMemberBo){
			WorkAreaPrincipalRoleMemberBo existingPrincipalRoleMember = (WorkAreaPrincipalRoleMemberBo) existingRoleMember;
			WorkAreaPrincipalRoleMemberBo currentPrincipalRoleMember = (WorkAreaPrincipalRoleMemberBo) roleMember;
			isFromDateBetween = isDateBetweenRange(existingPrincipalRoleMember.getActiveFromDate() , existingPrincipalRoleMember.getActiveToDate(),currentPrincipalRoleMember.getActiveFromDate());
			isToDateBetween = isDateBetweenRange(existingPrincipalRoleMember.getActiveFromDate() , existingPrincipalRoleMember.getActiveToDate(),currentPrincipalRoleMember.getActiveToDate());
			isBetweenEffectiveDateRange = (isFromDateBetween || isToDateBetween);
		}else if(existingRoleMember instanceof WorkAreaPositionRoleMemberBo && roleMember instanceof WorkAreaPositionRoleMemberBo){
			WorkAreaPositionRoleMemberBo existingPositionRoleMember = (WorkAreaPositionRoleMemberBo) existingRoleMember;
			WorkAreaPositionRoleMemberBo currentPositionRoleMember = (WorkAreaPositionRoleMemberBo) roleMember;
			isFromDateBetween = isDateBetweenRange(existingPositionRoleMember.getActiveFromDate() , existingPositionRoleMember.getActiveToDate(), currentPositionRoleMember.getActiveFromDate());
			isToDateBetween = isDateBetweenRange(existingPositionRoleMember.getActiveFromDate() , existingPositionRoleMember.getActiveToDate(), currentPositionRoleMember.getActiveToDate());
			isBetweenEffectiveDateRange = (isFromDateBetween || isToDateBetween);
		}
		return isBetweenEffectiveDateRange;
	}

	private boolean isDateBetweenRange(DateTime activeFromDate, DateTime activeToDate, DateTime checkDate) {
		
		Interval interval = new Interval( activeFromDate , activeToDate);
		return interval.contains(checkDate);
	}
	
	
	
	protected boolean validateDefaultOvertimeEarnCode(String defaultOvertimeEarnCode, LocalDate asOfDate) {
		boolean valid = true;
		
		if (defaultOvertimeEarnCode != null && !StringUtils.isEmpty(defaultOvertimeEarnCode)) {
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
	
	protected boolean validateDepartment(String dept, String groupKeyCode, LocalDate asOfDate) {
		boolean valid = ValidationUtils.validateDepartment(dept, groupKeyCode, asOfDate);
		
		if (!valid) {
			this.putFieldError("dept", "dept.notfound");
		}
		
		return valid;
	}

	protected boolean validateRoleMembers(List<? extends PrincipalRoleMemberBo> principalRoleMembers, List<? extends PositionRoleMemberBo> positionRoleMembers, LocalDate effectiveDate, String principalPrefix, String positionPrefix) {
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

    protected boolean validateRoleMember(KPMERoleMemberBo roleMember, LocalDate effectiveDate, String prefix, int index) {
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
			} else {
				LocalDate dateLimit = roleMember.getActiveFromDate().toLocalDate().plusMonths(6).minusDays(1);
				if(roleMember.getActiveToDate().toLocalDate().isAfter(dateLimit)) {			
					this.putFieldError(propertyNamePrefix + "expirationDate", "error.role.expiration.duration");
					valid = false;
				}
        	}
		}
		
		return valid;
	}
	
	protected boolean validateActive(WorkAreaBo workArea) {
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
			for (TaskBo task : workArea.getTasks()) {
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
	
	protected boolean validateTask(TaskBo task, WorkAreaBo workArea) {
        

		boolean valid = true;
        if (task.getEffectiveDate() == null) {
            return false;
        }
       	if(workArea.getEffectiveDate()!=null){
       		if (task.getEffectiveDate().compareTo(workArea.getEffectiveDate()) < 0) {
       			this.putGlobalError("task.workarea.invalid.effdt", "effective date '" + task.getEffectiveDate().toString() + "'");
       			valid = false;
       		}
       	}else{
       		this.putGlobalError("workarea.invalid.effdt");
       		valid = false;
       	}

         //before commit check against tasks common to different work areas and assignments
         List<Long> inactiveTasks = new ArrayList<Long>();
            for (TaskBo inactiveTask : workArea.getTasks()) {
                if(!inactiveTask.isActive()){
                    inactiveTasks.add(inactiveTask.getTask());
                }
            }

            if(!inactiveTasks.isEmpty()){
                List<Assignment> assignments = HrServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea.getWorkArea(), workArea.getEffectiveLocalDate());
                for(Assignment assignment : assignments){
                    for(Long inactiveTask : inactiveTasks){
                        if(inactiveTask.equals(assignment.getTask())){
                            this.putGlobalError("task.active.inactivate", inactiveTask.toString());
                            valid = false;
                        }
                    }
                }
            }

		return valid;
	}

	protected Long getMaxTaskNumber(WorkAreaBo workArea) {
		Long task = new Long("100");
		
		Task maxTask = HrServiceLocator.getTaskService().getMaxTask(workArea.getWorkArea());
		
		if (maxTask != null) {
			task = maxTask.getTask() + 1;
		}
		
		return task;
	}
}
