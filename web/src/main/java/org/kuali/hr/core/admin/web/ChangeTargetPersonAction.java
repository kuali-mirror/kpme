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
package org.kuali.hr.core.admin.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.job.Job;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.tklm.time.base.web.TkAction;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TKContext;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class ChangeTargetPersonAction extends TkAction {
	
	private static final Logger LOG = Logger.getLogger(ChangeTargetPersonAction.class);
	
    public ActionForward changeTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = mapping.findForward("basic");
    	
    	ChangeTargetPersonForm changeTargetPersonForm = (ChangeTargetPersonForm) form;

        if (StringUtils.isNotBlank(changeTargetPersonForm.getPrincipalName())) {
        	Principal targetPerson = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(changeTargetPersonForm.getPrincipalName());
        	
	        if (targetPerson != null) {
	            if (TkServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(GlobalVariables.getUserSession().getPrincipalId(), new DateTime())
	                	|| TkServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(GlobalVariables.getUserSession().getPrincipalId(), new DateTime())
	                	|| isReviewerForPerson(targetPerson.getPrincipalId())
	                	|| isApproverForPerson(targetPerson.getPrincipalId())
	                	|| isViewOnlyForPerson(targetPerson.getPrincipalId())
	                	|| isAdministratorForPerson(targetPerson.getPrincipalId())) {
		                	
	            	TKContext.setTargetPrincipalId(targetPerson.getPrincipalId());
	
		            if (StringUtils.isNotEmpty(changeTargetPersonForm.getReturnUrl())) {
		            	GlobalVariables.getUserSession().addObject(TkConstants.TK_TARGET_USER_RETURN, changeTargetPersonForm.getReturnUrl());
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
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

        for (Assignment assignment : assignments) {
            if (TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), assignment.getWorkArea(), new DateTime())) {
                return true;
            }
        }
        return false;
    }

    private boolean isApproverForPerson(String principalId) {
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, LocalDate.now());

        for (Assignment assignment : assignments) {
        	if (TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), assignment.getWorkArea(), new DateTime())
        			|| TkServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), assignment.getWorkArea(), new DateTime())) {
                return true;
            }
        }

        return false;
    }

    private boolean isViewOnlyForPerson(String principalId) {
        List<Job> jobs = TkServiceLocator.getJobService().getJobs(principalId, LocalDate.now());
        
        for (Job job : jobs) {
        	String department = job != null ? job.getDept() : null;
			
			Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getLocation() : null;

            if (TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), department, new DateTime())
            		|| TkServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), department, new DateTime())
            		|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), location, new DateTime())
            		|| TkServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), location, new DateTime())) {
                return true;
            }
        }

        return false;
    }
    
    private boolean isAdministratorForPerson(String principalId) {
        List<Job> jobs = TkServiceLocator.getJobService().getJobs(principalId, LocalDate.now());
        
        for (Job job : jobs) {
			String department = job != null ? job.getDept() : null;
			
			Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(department, LocalDate.now());
			String location = departmentObj != null ? departmentObj.getLocation() : null;
			
        	if (TkServiceLocator.getTKRoleService().principalHasRoleInDepartment(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
        			|| TkServiceLocator.getLMRoleService().principalHasRoleInDepartment(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), department, new DateTime())
        			|| TkServiceLocator.getTKRoleService().principalHasRoleInLocation(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())
        			|| TkServiceLocator.getLMRoleService().principalHasRoleInLocation(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), location, new DateTime())) {
                return true;
            }
        }

        return false;
    }
    
    public ActionForward clearTargetPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TKContext.clearTargetUser();
        
        String returnAction = "PersonInfo.do";
        if (StringUtils.isNotBlank((String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_RETURN))) {
        	returnAction = (String) GlobalVariables.getUserSession().retrieveObject(TkConstants.TK_TARGET_USER_RETURN);
        }
        
        LOG.debug(GlobalVariables.getUserSession().getActualPerson().getPrincipalName() + " cleared target person");

        return new ActionRedirect(returnAction);
    }

}