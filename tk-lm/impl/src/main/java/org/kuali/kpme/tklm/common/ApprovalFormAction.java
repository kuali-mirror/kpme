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
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.rice.krad.util.GlobalVariables;

public abstract class ApprovalFormAction extends KPMEAction {
	
	protected void setSearchFields(ApprovalForm approvalForm) {
        String principalId = GlobalVariables.getUserSession().getPrincipalId();
		LocalDate currentDate = LocalDate.now();
		
		if (CollectionUtils.isEmpty(approvalForm.getPayCalendarGroups())) {
			Set<Long> workAreas = new TreeSet<Long>();
			workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
			workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
			workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
			
			List<String> principalIds = new ArrayList<String>();
	        for (Long workArea : workAreas) {
	            List<Assignment> assignments = HrServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, currentDate);
	            for (Assignment assignment : assignments) {
	                principalIds.add(assignment.getPrincipalId());
	            }
	        }
	
	        List<String> calendarGroups = new ArrayList<String>();
	        if (CollectionUtils.isNotEmpty(principalIds)) {
	            calendarGroups = getCalendars(principalIds);
	        }
	        if (StringUtils.isEmpty(approvalForm.getSelectedPayCalendarGroup())) {
	        	approvalForm.setSelectedPayCalendarGroup(CollectionUtils.isNotEmpty(calendarGroups) ? calendarGroups.get(0) : null);
	        }
	        approvalForm.setPayCalendarGroups(calendarGroups);
		}
		
		if (CollectionUtils.isEmpty(approvalForm.getDepartments())) {
			Set<String> departments = new TreeSet<String>();
			
			Set<Long> workAreas = new TreeSet<Long>();
			workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
			workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
			workAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay(), true));
			
			for (Long workArea : workAreas) {
				WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, LocalDate.now());
				if (workAreaObj != null) {
					departments.add(workAreaObj.getDept());
				}
			}
			approvalForm.setDepartments(new ArrayList<String>(departments));
            if (StringUtils.isEmpty(approvalForm.getSelectedDept())) {
			    approvalForm.setSelectedDept(CollectionUtils.isNotEmpty(approvalForm.getDepartments()) ? approvalForm.getDepartments().get(0) : null);
            }
		}
		
		approvalForm.getWorkAreaDescr().clear();
    	List<WorkArea> workAreaObjs = HrServiceLocator.getWorkAreaService().getWorkAreas(approvalForm.getSelectedDept(), currentDate);
        for (WorkArea workAreaObj : workAreaObjs) {
        	Long workArea = workAreaObj.getWorkArea();
        	String description = workAreaObj.getDescription();
        	
        	if (HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), workArea, LocalDate.now().toDateTimeAtStartOfDay())
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, LocalDate.now().toDateTimeAtStartOfDay())
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, LocalDate.now().toDateTimeAtStartOfDay())) {
        		approvalForm.getWorkAreaDescr().put(workArea, description + "(" + workArea + ")");
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