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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
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

		List<Assignment> lstAssign = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate());
		Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
		
		Map<Long,List<TkRole>> workAreaToApprover = new HashMap<Long,List<TkRole>>();
		Map<Long,List<Person>> workAreaToApproverPerson = new HashMap<Long, List<Person>>();
        Map<String,List<Person>> deptToDeptAdminPerson = new HashMap<String,List<Person>>();
		
		Map<String,Person> principalIdToPerson = new HashMap<String,Person>();
		for(Assignment assign : lstAssign){
			List<Assignment> lstCurrJobAssign = jobNumberToListAssignments.get(assign.getJobNumber());
			if(lstCurrJobAssign == null){
				lstCurrJobAssign = new ArrayList<Assignment>();
			}
			lstCurrJobAssign.add(assign);
			jobNumberToListAssignments.put(assign.getJobNumber(), lstCurrJobAssign);

			this.assignWorkAreaToApprover(assign.getWorkArea(), workAreaToApprover, workAreaToApproverPerson);
            deptToDeptAdminPerson = assignDeptToDeptAdmin(assign.getDept());
		}
		
		personForm.setWorkAreaToApprover(workAreaToApprover);
		personForm.setWorkAreaToApproverPerson(workAreaToApproverPerson);
        personForm.setDeptToDeptAdminPerson(deptToDeptAdminPerson);
		personForm.setJobNumberToListAssignments(jobNumberToListAssignments);
		personForm.setPrincipalIdToPerson(principalIdToPerson);
		return actForw;
	}
	
	private void setupRolesOnForm(PersonInfoActionForm paForm){
		UserRoles roles = TKUser.getCurrentTargetRoles();
		for(Long waApprover : roles.getApproverWorkAreas()){
			paForm.getApproverWorkAreas().add(waApprover);
		}
		
		for(Long waReviewer : roles.getReviewerWorkAreas()){
			paForm.getReviewerWorkAreas().add(waReviewer);
		}
		
		for(String deptAdmin : roles.getOrgAdminDepartments()){
			paForm.getDeptAdminDepts().add(deptAdmin);
		}
		
		for(String deptViewOnly : roles.getDepartmentViewOnlyDepartments()){
			paForm.getDeptViewOnlyDepts().add(deptViewOnly);
		}
		
		for(String location : roles.getOrgAdminCharts()){
			paForm.getLocationAdminDepts().add(location);
		}
		
		paForm.setGlobalViewOnlyRoles(roles.isGlobalViewOnly());
		paForm.setSystemAdmin(roles.isSystemAdmin());
	}

    private Map<String,List<Person>> assignDeptToDeptAdmin(String dept) {
        List<TkRole> deptAdminRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(dept, TkConstants.ROLE_TK_DEPT_ADMIN, TKUtils.getCurrentDate());
        List<Person> deptAdminPeople = new ArrayList<Person>();
        Map<String,List<Person>> deptAdmins = new HashMap<String, List<Person>>();

        for(TkRole role : deptAdminRoles) {
        	if(role.getPositionNumber() != null){
				List<Job> lstJobs = TkServiceLocator.getJobService().getActiveJobsForPosition(role.getPositionNumber(), TKUtils.getCurrentDate());
				for(Job j : lstJobs){
					Person person = KimApiServiceLocator.getPersonService().getPerson(j.getPrincipalId());
					if(person !=null){
						deptAdminPeople.add(person);
					}
				}
			} else{
				Person person = KimApiServiceLocator.getPersonService().getPerson(role.getPrincipalId());
				if(person!=null){
					deptAdminPeople.add(person);
				}
			}
        }
        deptAdmins.put(dept, deptAdminPeople);

        return deptAdmins;
    }
	
	private void assignWorkAreaToApprover(Long workArea, Map<Long,List<TkRole>> workAreaToApprover, Map<Long,List<Person>> workAreaToApproverPerson ){
		List<TkRole> lstApproverRoles = TkServiceLocator.getTkRoleService().getWorkAreaRoles(workArea, TkConstants.ROLE_TK_APPROVER,
				TKUtils.getCurrentDate());
		workAreaToApprover.put(workArea, lstApproverRoles);
		for(TkRole role : lstApproverRoles){
			if(role.getPositionNumber() != null){
				List<Job> lstJobs = TkServiceLocator.getJobService().getActiveJobsForPosition(role.getPositionNumber(), TKUtils.getCurrentDate());
				for(Job j : lstJobs){
					Person approver = KimApiServiceLocator.getPersonService().getPerson(j.getPrincipalId());
					if(approver!=null){
					addApproverPersonForWorkArea(workArea, approver, workAreaToApproverPerson);
					}
				}
			} else{
				Person approver = KimApiServiceLocator.getPersonService().getPerson(role.getPrincipalId());
				if(approver!=null){
					addApproverPersonForWorkArea(workArea, approver, workAreaToApproverPerson);
				}
			}
		}
	}
	
	private void addApproverPersonForWorkArea(Long workArea, Person person, Map<Long,List<Person>> workAreaToApproverPerson){
		List<Person> approvers = workAreaToApproverPerson.get(workArea);
		if(approvers == null){
			approvers = new ArrayList<Person>();
		}
        boolean personExists = false;
		for (Person per : approvers) {
            if (StringUtils.equals(per.getPrincipalId(),person.getPrincipalId())) {
                personExists = true;
            }
        }
        if (!personExists)
            approvers.add(person);
		workAreaToApproverPerson.put(workArea, approvers);
	}
}
