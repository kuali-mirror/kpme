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
package org.kuali.hr.time.base.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PersonInfoAction extends TkAction {



    public ActionForward showInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return mapping.findForward("basic");
    }

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward actForw =  super.execute(mapping, form, request, response);
		PersonInfoActionForm personForm = (PersonInfoActionForm)form;
		
		personForm.setPrincipalId(TKContext.getTargetPrincipalId());
        EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(personForm.getPrincipalId());
		//Person person = KimApiServiceLocator.getPersonService().getPerson(personForm.getPrincipalId());
		if (name != null) {
            personForm.setPrincipalName(name.getPrincipalName());
            // set name
            personForm.setName(name.getDefaultName() != null ? name.getDefaultName().getCompositeName() : StringUtils.EMPTY);
        }
		personForm.setJobs(TkServiceLocator.getJobService().getJobs(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate()));
		
		//KPME-1441
		PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(personForm.getPrincipalId(), TKUtils.getCurrentDate());
		if ( principalHRAttributes != null && principalHRAttributes.getServiceDate() != null ){
			personForm.setServiceDate(principalHRAttributes.getServiceDate().toString());
		} else {
			personForm.setServiceDate("");
		}
		// KPME-1441
		
		if (principalHRAttributes != null && principalHRAttributes.getLeavePlan() != null) {
			List<AccrualCategory> accrualCategories = new ArrayList<AccrualCategory>();
			Map<String, BigDecimal> accrualCategoryRates = new HashMap<String, BigDecimal>();
		    Map<String, String> accrualEarnIntervals = new HashMap<String, String>();
		    Map<String, String> unitOfTime = new HashMap<String, String>();
			
			List<AccrualCategory> allAccrualCategories = TkServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalHRAttributes.getLeavePlan(), TKUtils.getCurrentDate());
		    for (AccrualCategory accrualCategory : allAccrualCategories) {
				if (StringUtils.equalsIgnoreCase(accrualCategory.getHasRules(), "Y")) {
					AccrualCategoryRule accrualCategoryRule = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, TKUtils.getCurrentDate(), principalHRAttributes.getServiceDate());
					if (accrualCategoryRule != null) {
						accrualCategories.add(accrualCategory);
						
						accrualCategoryRates.put(accrualCategory.getAccrualCategory(), accrualCategoryRule.getAccrualRate());

						for (Map.Entry<String, String> entry : LMConstants.ACCRUAL_EARN_INTERVAL_MAP.entrySet()) {					            
				            if (accrualCategory.getAccrualEarnInterval().equals(entry.getKey())) {
				            	accrualEarnIntervals.put(accrualCategory.getAccrualCategory(), entry.getValue());
				            	break;
				            }
				        } 
						
						for (Map.Entry<String, String> entry : TkConstants.UNIT_OF_TIME.entrySet()) {					            
				            if (accrualCategory.getUnitOfTime().equals(entry.getKey()) ){
				            	unitOfTime.put(accrualCategory.getAccrualCategory(), entry.getValue());
				            	break;
				            }
				        } 
					}
				}
			}
			personForm.setAccrualCategories(accrualCategories);
			personForm.setAccrualCategoryRates(accrualCategoryRates);
			personForm.setAccrualEarnIntervals(accrualEarnIntervals);
			personForm.setUnitOfTime(unitOfTime);
		}
		
		setupRolesOnForm(personForm);

		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate());
		
		Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
		Map<Long,List<Person>> workAreaToApproverPerson = new HashMap<Long, List<Person>>();
        Map<String,List<Person>> deptToDeptAdminPerson = new HashMap<String,List<Person>>();
		
		for (Assignment assignment : assignments) {
			List<Assignment> jobAssignments = jobNumberToListAssignments.get(assignment.getJobNumber());
			if (jobAssignments == null) {
				jobAssignments = new ArrayList<Assignment>();
			}
			jobAssignments.add(assignment);
			jobNumberToListAssignments.put(assignment.getJobNumber(), jobAssignments);
			
			List<Person> approvers = workAreaToApproverPerson.get(assignment.getWorkArea());
			if (approvers == null) {
				approvers = new ArrayList<Person>();
			}
			approvers.addAll(getApprovers(assignment.getWorkArea()));
			workAreaToApproverPerson.put(assignment.getWorkArea(), approvers);
			
			List<Person> departmentAdmins = deptToDeptAdminPerson.get(assignment.getDept());
			if (departmentAdmins == null) {
				departmentAdmins = new ArrayList<Person>();
			}
			departmentAdmins.addAll(getDeptartmentAdmins(assignment.getDept()));
            deptToDeptAdminPerson.put(assignment.getDept(), departmentAdmins);
		}
		
		personForm.setJobNumberToListAssignments(jobNumberToListAssignments);
		personForm.setWorkAreaToApproverPerson(workAreaToApproverPerson);
        personForm.setDeptToDeptAdminPerson(deptToDeptAdminPerson);
		
		return actForw;
	}
	
	private void setupRolesOnForm(PersonInfoActionForm personInfoActionForm) {
		String principalId = TKContext.getTargetPrincipalId();
		
		Set<Long> allApproverWorkAreas = new HashSet<Long>();
		allApproverWorkAreas.addAll(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), new DateTime(), true));
		allApproverWorkAreas.addAll(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.APPROVER.getRoleName(), new DateTime(), true));

		personInfoActionForm.setApproverWorkAreas(new ArrayList<Long>(allApproverWorkAreas));
		personInfoActionForm.setReviewerWorkAreas(TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.REVIEWER.getRoleName(), new DateTime(), true));
		personInfoActionForm.setDeptViewOnlyDepts(TkServiceLocator.getDepartmentService().getViewOnlyDepartments(principalId));
		personInfoActionForm.setDeptAdminDepts(TkServiceLocator.getDepartmentService().getAdministratorDepartments(principalId));
		personInfoActionForm.setLocationAdminDepts(TkServiceLocator.getLocationService().getAdministratorLocations(principalId));
		personInfoActionForm.setGlobalViewOnlyRoles(TkServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(principalId, new DateTime()));
		personInfoActionForm.setSystemAdmin(TkServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(principalId, new DateTime()));
	}

    private List<Person> getDeptartmentAdmins(String dept) {
    	List<Person> departmentAdmins = new ArrayList<Person>();
    	
    	List<RoleMember> roleMembers = new ArrayList<RoleMember>();
    	roleMembers.addAll(TkServiceLocator.getTKRoleService().getRoleMembersInDepartment(KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), dept, new DateTime(), true));
    	roleMembers.addAll(TkServiceLocator.getLMRoleService().getRoleMembersInDepartment(KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), dept, new DateTime(), true));
	        
    	for (RoleMember roleMember : roleMembers) {
    		Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
			if (person != null) {
				departmentAdmins.add(person);
			}
        }
    	
        return departmentAdmins;
    }
	
	private List<Person> getApprovers(Long workArea) {
		List<Person> approvers = new ArrayList<Person>();
		
		List<RoleMember> roleMembers = TkServiceLocator.getHRRoleService().getRoleMembersInWorkArea(KPMERole.APPROVER.getRoleName(), workArea, new DateTime(), true);
		
		for (RoleMember roleMember : roleMembers) {
			Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
			if (person != null) {
				approvers.add(person);
			}
        }
		
		return approvers;
	}

}