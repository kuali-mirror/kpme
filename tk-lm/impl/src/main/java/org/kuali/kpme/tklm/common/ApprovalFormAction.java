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
package org.kuali.kpme.tklm.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.hsqldb.lib.StringUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public abstract class ApprovalFormAction extends KPMEAction {
	
	protected void setSearchFields(ApprovalForm approvalForm) {
        String principalId = GlobalVariables.getUserSession().getPrincipalId();
		LocalDate currentDate = LocalDate.now();
        DateTime currentDateTime = currentDate.toDateTimeAtStartOfDay();

        List<String> roleIds = new ArrayList<String>();
        RoleService roleService = KimApiServiceLocator.getRoleService();
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName()));

        Set<Long> workAreas = new TreeSet<Long>();
        //TODO: create permission for approval, and get all roles with that permission
        workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRoles(principalId, roleIds, currentDateTime, true));
        if (CollectionUtils.isEmpty(approvalForm.getPayCalendarGroups())) {
            List<String> principalIds = HrServiceLocator.getAssignmentService().getPrincipalIdsInActiveAssignmentsForWorkAreas(new ArrayList<Long>(workAreas), currentDate);
	
	        List<String> calendarGroups = new ArrayList<String>();
	        if (CollectionUtils.isNotEmpty(principalIds)) {
	            calendarGroups = getCalendars(principalIds);
	        }
	        if (StringUtils.isEmpty(approvalForm.getSelectedPayCalendarGroup())) {
	        	approvalForm.setSelectedPayCalendarGroup(CollectionUtils.isNotEmpty(calendarGroups) ? calendarGroups.get(0) : null);
	        }
	        approvalForm.setPayCalendarGroups(calendarGroups);
		}

        List<WorkArea> workAreasWithoutRoles = HrServiceLocator.getWorkAreaService().getWorkAreasWithoutRoles(new ArrayList<Long>(workAreas), currentDate);
		if (CollectionUtils.isEmpty(approvalForm.getDepartments())) {
			Set<String> departments = new TreeSet<String>();
			

            for (WorkArea workArea : workAreasWithoutRoles) {
			    departments.add(workArea.getDept());
			}
			approvalForm.setDepartments(new ArrayList<String>(departments));
            if (StringUtils.isEmpty(approvalForm.getSelectedDept())) {
			    approvalForm.setSelectedDept(CollectionUtils.isNotEmpty(approvalForm.getDepartments()) ? approvalForm.getDepartments().get(0) : null);
            }
		}


        //have depts in approvalForm.getDepartments
        //have workareas too...

		approvalForm.getWorkAreaDescr().clear();
    	//List<Long> workAreaIds = HrServiceLocator.getWorkAreaService().getWorkAreasForDepartment(approvalForm.getSelectedDept(), currentDate);
        //List<WorkArea> workAreaObjs = HrServiceLocator.getWorkAreaService().getWorkAreasWithoutRoles(workAreaIds, currentDate);
        for (WorkArea workAreaObj : workAreasWithoutRoles) {
            //only want workareas in selected department
            if (StringUtils.equals(workAreaObj.getDept(), approvalForm.getSelectedDept())) {
                Long workArea = workAreaObj.getWorkArea();
                //String department = workAreaObj.getDept();
                String description = workAreaObj.getDescription();

                //TODO :  this if statement, in this loop, is not a good thing... lots of stuff happening here.
                //if (HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea, currentDateTime)
                //        || HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, currentDateTime)
                //        || HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, currentDateTime)
                //        || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), department, currentDateTime)
                //        || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), department, currentDateTime)) {
                    approvalForm.getWorkAreaDescr().put(workArea, description + "(" + workArea + ")");
                //}
            }
        }
	}
	
	protected abstract List<String> getCalendars(List<String> principalIds);
	
	protected List<String> getWorkAreas(ActionForm form) {
		ApprovalForm approvalForm = (ApprovalForm) form;
		
		List<String> workAreas = new ArrayList<String>();
		
	    if (StringUtil.isEmpty(approvalForm.getSelectedWorkArea())) {
	    	for (Long workAreaKey : approvalForm.getWorkAreaDescr().keySet()) {
	    		workAreas.add(workAreaKey.toString());
	    	}
	    } else {
	    	workAreas.add(approvalForm.getSelectedWorkArea());
	    }
	    
	    return workAreas;
	}

}