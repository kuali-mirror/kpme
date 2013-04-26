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
package org.kuali.hr.core.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.accrualcategory.AccrualCategory;
import org.kuali.hr.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.hr.core.bo.assignment.Assignment;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.role.KPMERole;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.util.LMConstants;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PersonInfoAction extends HrAction {



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
		personForm.setJobs(HrServiceLocator.getJobService().getJobs(TKContext.getTargetPrincipalId(), LocalDate.now()));
		
		//KPME-1441
		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(personForm.getPrincipalId(), LocalDate.now());
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
			
			List<AccrualCategory> allAccrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveLeaveAccrualCategoriesForLeavePlan(principalHRAttributes.getLeavePlan(), LocalDate.now());
		    for (AccrualCategory accrualCategory : allAccrualCategories) {
				if (StringUtils.equalsIgnoreCase(accrualCategory.getHasRules(), "Y")) {
					AccrualCategoryRule accrualCategoryRule = HrServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(accrualCategory, LocalDate.now(), principalHRAttributes.getServiceLocalDate());
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

		List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), LocalDate.now());
		
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
		
		List<Long> reviewerWorkAreas = TkServiceLocator.getHRRoleService().getWorkAreasForPrincipalInRole(principalId, KPMERole.REVIEWER.getRoleName(), new DateTime(), true);
		personInfoActionForm.setReviewerWorkAreas(reviewerWorkAreas);
		
		Set<String> allViewOnlyDepartments = new HashSet<String>();
		allViewOnlyDepartments.addAll(TkServiceLocator.getTKRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime(), true));
		allViewOnlyDepartments.addAll(LmServiceLocator.getLMRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), new DateTime(), true));
		allViewOnlyDepartments.addAll(TkServiceLocator.getTKRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), new DateTime(), true));
		allViewOnlyDepartments.addAll(LmServiceLocator.getLMRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), new DateTime(), true));
		personInfoActionForm.setDeptViewOnlyDepts(new ArrayList<String>(allViewOnlyDepartments));
		
		Set<String> allAdministratorDepartments = new HashSet<String>();
		allAdministratorDepartments.addAll(TkServiceLocator.getTKRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime(), true));
		allAdministratorDepartments.addAll(LmServiceLocator.getLMRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), new DateTime(), true));
		allAdministratorDepartments.addAll(TkServiceLocator.getTKRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime(), true));
		allAdministratorDepartments.addAll(LmServiceLocator.getLMRoleService().getDepartmentsForPrincipalInRole(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime(), true));
		personInfoActionForm.setDeptAdminDepts(new ArrayList<String>(allAdministratorDepartments));
		
		Set<String> allAdministratorLocations = new HashSet<String>();
		allAdministratorLocations.addAll(TkServiceLocator.getTKRoleService().getLocationsForPrincipalInRole(principalId, KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime(), true));
		allAdministratorLocations.addAll(LmServiceLocator.getLMRoleService().getLocationsForPrincipalInRole(principalId, KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), new DateTime(), true));
		personInfoActionForm.setLocationAdminDepts(new ArrayList<String>(allAdministratorLocations));
		
		personInfoActionForm.setGlobalViewOnlyRoles(TkServiceLocator.getHRGroupService().isMemberOfSystemViewOnlyGroup(principalId, new DateTime()));
		personInfoActionForm.setSystemAdmin(TkServiceLocator.getHRGroupService().isMemberOfSystemAdministratorGroup(principalId, new DateTime()));
	}

    private List<Person> getDeptartmentAdmins(String dept) {
    	List<Person> departmentAdmins = new ArrayList<Person>();
    	
    	List<RoleMember> roleMembers = new ArrayList<RoleMember>();
    	roleMembers.addAll(TkServiceLocator.getTKRoleService().getRoleMembersInDepartment(KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), dept, new DateTime(), true));
    	roleMembers.addAll(LmServiceLocator.getLMRoleService().getRoleMembersInDepartment(KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), dept, new DateTime(), true));
	        
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