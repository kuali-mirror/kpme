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
package org.kuali.kpme.tklm.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class ChangeTargetPersonAction extends KPMEAction {
	
	private static final Logger LOG = Logger.getLogger(ChangeTargetPersonAction.class);
	
    public ActionForward changeTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("basic");
    	
    	ChangeTargetPersonForm changeTargetPersonForm = (ChangeTargetPersonForm) form;

        if (StringUtils.isNotBlank(changeTargetPersonForm.getPrincipalName())) {
        	Principal targetPerson = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(changeTargetPersonForm.getPrincipalName());
        	
	        if (targetPerson != null) {
	            if (HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(GlobalVariables.getUserSession().getPrincipalId(), LocalDate.now().toDateTimeAtStartOfDay())
	                	|| HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(GlobalVariables.getUserSession().getPrincipalId(), LocalDate.now().toDateTimeAtStartOfDay())
	                	|| isReviewerForPerson(targetPerson.getPrincipalId())
	                	|| isApproverForPerson(targetPerson.getPrincipalId())
	                	|| isViewOnlyForPerson(targetPerson.getPrincipalId())
                        || isPayrollProcessorForPerson(targetPerson.getPrincipalId())
	                	|| isAdministratorForPerson(targetPerson.getPrincipalId())) {
		                	
	            	HrContext.setTargetPrincipalId(targetPerson.getPrincipalId());
	
		            if (StringUtils.isNotEmpty(changeTargetPersonForm.getReturnUrl())) {
		            	GlobalVariables.getUserSession().addObject(HrConstants.TK_TARGET_USER_RETURN, changeTargetPersonForm.getReturnUrl());
		            }
		            
		            String returnAction = "PersonInfo.do";
		            if (StringUtils.isNotEmpty(changeTargetPersonForm.getTargetUrl())) {
		                returnAction = changeTargetPersonForm.getTargetUrl();
		            }
		            forward = new ActionRedirect(returnAction);
		
		            LOG.debug(GlobalVariables.getUserSession().getActualPerson().getPrincipalName() + " changed target person to " + targetPerson.getPrincipalName());
	            } else {
	                LOG.warn("Non-Admin user attempting to change target person.");
	                return mapping.findForward("unauthorized");
	            }
	        }
        }

        return forward;
    }
    
    private boolean isReviewerForPerson(String principalId) {
        List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

        for (Assignment assignment : assignments) {
            if (HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), assignment.getWorkArea(), LocalDate.now().toDateTimeAtStartOfDay())) {
                return true;
            }
        }
        return false;
    }

    private boolean isApproverForPerson(String principalId) {
        List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

        for (Assignment assignment : assignments) {
        	if (HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), assignment.getWorkArea(), LocalDate.now().toDateTimeAtStartOfDay())
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), assignment.getWorkArea(), LocalDate.now().toDateTimeAtStartOfDay())) {
                return true;
            }
        }

        return false;
    }

    private boolean isPayrollProcessorForPerson(String principalId) {
        List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

        for (Assignment assignment : assignments) {
            if (HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), assignment.getDept(), LocalDate.now().toDateTimeAtStartOfDay())
                    || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), assignment.getDept(), LocalDate.now().toDateTimeAtStartOfDay())) {
                return true;
            }
        }

        return false;
    }

    private boolean isViewOnlyForPerson(String principalId) {
        List<Job> jobs = (List<Job>) HrServiceLocator.getJobService().getJobs(principalId, LocalDate.now());
        
        for (Job job : jobs) {
        	String department = job != null ? job.getDept() : null;
			
			Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getLocation() : null;

            if (HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), department, LocalDate.now().toDateTimeAtStartOfDay())
            		|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), department, LocalDate.now().toDateTimeAtStartOfDay())
            		|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay())
            		|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay())) {
                return true;
            }
        }

        return false;
    }
    
    private boolean isAdministratorForPerson(String principalId) {
        List<Job> jobs = (List<Job>) HrServiceLocator.getJobService().getJobs(principalId, LocalDate.now());
        
        for (Job job : jobs) {
			String department = job != null ? job.getDept() : null;
			
			Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getLocation() : null;
			
        	if (HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, LocalDate.now().toDateTimeAtStartOfDay())
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, LocalDate.now().toDateTimeAtStartOfDay())
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay())
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(GlobalVariables.getUserSession().getPrincipalId(), KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, LocalDate.now().toDateTimeAtStartOfDay())) {
                return true;
            }
        }

        return false;
    }
    
    public ActionForward clearTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HrContext.clearTargetUser();
        
        String returnAction = "PersonInfo.do";
        if (StringUtils.isNotBlank((String) GlobalVariables.getUserSession().retrieveObject(HrConstants.TK_TARGET_USER_RETURN))) {
        	returnAction = (String) GlobalVariables.getUserSession().retrieveObject(HrConstants.TK_TARGET_USER_RETURN);
        }
        
        LOG.debug(GlobalVariables.getUserSession().getActualPerson().getPrincipalName() + " cleared target person");

        return new ActionRedirect(returnAction);
    }

}