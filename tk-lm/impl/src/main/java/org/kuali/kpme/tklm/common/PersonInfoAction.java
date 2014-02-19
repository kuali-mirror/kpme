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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRuleBo;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.accrualcategory.AccrualEarnInterval;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PersonInfoAction extends KPMEAction {
    private IdentityService identityService;

    public ActionForward showInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return mapping.findForward("basic");
    }

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward actForw =  super.execute(mapping, form, request, response);
		PersonInfoActionForm personForm = (PersonInfoActionForm)form;
		
		personForm.setPrincipalId(HrContext.getTargetPrincipalId());
        EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(personForm.getPrincipalId());
		//Person person = KimApiServiceLocator.getPersonService().getPerson(personForm.getPrincipalId());
		if (name != null) {
            personForm.setPrincipalName(name.getPrincipalName());
            // set name
            personForm.setName(name.getDefaultName() != null ? name.getDefaultName().getCompositeName() : StringUtils.EMPTY);
        }
		personForm.setJobs((List<Job>) HrServiceLocator.getJobService().getJobs(HrContext.getTargetPrincipalId(), LocalDate.now()));
		
		//KPME-1441
		PrincipalHRAttributesContract principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(personForm.getPrincipalId(), LocalDate.now());
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

						for (AccrualEarnInterval entry : AccrualEarnInterval.values()) {
				            if (accrualCategory.getAccrualEarnInterval().equals(entry.getCode())) {
				            	accrualEarnIntervals.put(accrualCategory.getAccrualCategory(), entry.getDescription());
				            	break;
				            }
				        } 
						
						for (Map.Entry<String, String> entry : HrConstants.UNIT_OF_TIME.entrySet()) {					            
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

		List<Assignment> assignments = (List<Assignment>) HrServiceLocator.getAssignmentService().getAssignments(HrContext.getTargetPrincipalId(), LocalDate.now());
		
		Map<Long, Set<Assignment>> jobNumberToListAssignments = new HashMap<Long,Set<Assignment>>();
		Map<Long, Set<Person>> workAreaToApproverPerson = new HashMap<Long, Set<Person>>();
        Map<String, Set<Person>> deptToDeptAdminPerson = new HashMap<String, Set<Person>>();
        Map<String, Set<Person>> deptToPayrollPerson = new HashMap<String, Set<Person>>();
		
		for (Assignment assignment : assignments) {
			Set<Assignment> jobAssignments = jobNumberToListAssignments.get(assignment.getJobNumber());
			if (jobAssignments == null) {
				jobAssignments = new HashSet<Assignment>();
			}
			jobAssignments.add(assignment);
			jobNumberToListAssignments.put(assignment.getJobNumber(), jobAssignments);
			
			Set<Person> approvers = workAreaToApproverPerson.get(assignment.getWorkArea());
			if (approvers == null) {
				approvers = new TreeSet<Person>(new Comparator<Person>() {
					@Override
					public int compare(Person person1, Person person2) {
						return ObjectUtils.compare(person1.getPrincipalId(), person2.getPrincipalId());
					}
				});
			}
			approvers.addAll(getApprovers(assignment.getWorkArea()));
			workAreaToApproverPerson.put(assignment.getWorkArea(), approvers);
			
			Set<Person> departmentAdmins = deptToDeptAdminPerson.get(assignment.getDept());
			if (departmentAdmins == null) {
				departmentAdmins = new TreeSet<Person>(new Comparator<Person>() {
					@Override
					public int compare(Person person1, Person person2) {
						return ObjectUtils.compare(person1.getPrincipalId(), person2.getPrincipalId());
					}
				});
			}
			departmentAdmins.addAll(getDepartmentAdmins(assignment.getDept()));
            deptToDeptAdminPerson.put(assignment.getDept(), departmentAdmins);

            Set<Person> payrollProcessors = deptToPayrollPerson.get(assignment.getDept());
            if (payrollProcessors == null) {
                payrollProcessors = new TreeSet<Person>(new Comparator<Person>() {
                    @Override
                    public int compare(Person person1, Person person2) {
                        return ObjectUtils.compare(person1.getPrincipalId(), person2.getPrincipalId());
                    }
                });
            }
            payrollProcessors.addAll(getPayrollProcessors(assignment.getDept()));
            deptToPayrollPerson.put(assignment.getDept(), payrollProcessors);
		}
		
		personForm.setJobNumberToListAssignments(jobNumberToListAssignments);
		personForm.setWorkAreaToApproverPerson(workAreaToApproverPerson);
        personForm.setDeptToDeptAdminPerson(deptToDeptAdminPerson);
        personForm.setDeptToPayrollPerson(deptToPayrollPerson);
		
		return actForw;
	}
	
	private void setupRolesOnForm(PersonInfoActionForm personInfoActionForm) {
		String principalId = HrContext.getTargetPrincipalId();
		DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
		Set<Long> allApproverWorkAreas = new HashSet<Long>();
		allApproverWorkAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), date, true));
		allApproverWorkAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), date, true));
		personInfoActionForm.setApproverWorkAreas(new ArrayList<Long>(allApproverWorkAreas));
		
		List<Long> reviewerWorkAreas = HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), date, true);
		personInfoActionForm.setReviewerWorkAreas(reviewerWorkAreas);
		
		Set<String> allViewOnlyDepartments = new HashSet<String>();
		allViewOnlyDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_VIEW_ONLY.getRoleName(), date, true));
		allViewOnlyDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_VIEW_ONLY.getRoleName(), date, true));
		allViewOnlyDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), date, true));
		allViewOnlyDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), date, true));
		personInfoActionForm.setDeptViewOnlyDepts(new ArrayList<String>(allViewOnlyDepartments));
		
		Set<String> allAdministratorDepartments = new HashSet<String>();
		allAdministratorDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), date, true));
		allAdministratorDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), date, true));
		allAdministratorDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), date, true));
		allAdministratorDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), date, true));
		personInfoActionForm.setDeptAdminDepts(new ArrayList<String>(allAdministratorDepartments));

        Set<String> allPayrollProcessorDepartments = new HashSet<String>();
        allPayrollProcessorDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), date, true));
        allPayrollProcessorDepartments.addAll(HrServiceLocator.getKPMERoleService().getDepartmentsForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName(), date, true));
        personInfoActionForm.setPayrollProcessorDepts(new ArrayList<String>(allPayrollProcessorDepartments));

		Set<String> allAdministratorLocations = new HashSet<String>();
		allAdministratorLocations.addAll(HrServiceLocator.getKPMERoleService().getLocationsForPrincipalInRole(principalId, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), date, true));
		allAdministratorLocations.addAll(HrServiceLocator.getKPMERoleService().getLocationsForPrincipalInRole(principalId, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), date, true));
		personInfoActionForm.setLocationAdminDepts(new ArrayList<String>(allAdministratorLocations));
		
		personInfoActionForm.setGlobalViewOnlyRoles(HrServiceLocator.getKPMEGroupService().isMemberOfSystemViewOnlyGroup(principalId, date));
		personInfoActionForm.setSystemAdmin(HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(principalId, date));
	}

    private Set<Person> getDepartmentAdmins(String dept) {
    	Set<Person> departmentAdmins = new HashSet<Person>();
        DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
    	List<RoleMember> roleMembers = new ArrayList<RoleMember>();
    	roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInDepartment(KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), dept, date, true));
    	roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInDepartment(KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), dept, date, true));
	        
    	for (RoleMember roleMember : roleMembers) {
    		Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
			if (person != null) {
				departmentAdmins.add(person);
			}
        }
    	
        return departmentAdmins;
    }

    private Set<Person> getPayrollProcessors(String dept) {
        Set<Person> payrollProcs = new HashSet<Person>();
        DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
        List<RoleMember> roleMembers = new ArrayList<RoleMember>();
        roleMembers.addAll(HrServiceLocator.getKPMERoleService().getRoleMembersInDepartment(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName(), dept, date, true));

        for (RoleMember roleMember : roleMembers) {
            Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
            if (person != null) {
                payrollProcs.add(person);
            }
        }

        return payrollProcs;
    }
	
	private Set<Person> getApprovers(Long workArea) {
		Set<Person> approvers = new HashSet<Person>();
        DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
		List<RoleMember> roleMembers = HrServiceLocator.getKPMERoleService().getRoleMembersInWorkArea(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), workArea, date, true);
		
		for (RoleMember roleMember : roleMembers) {
			Person person = KimApiServiceLocator.getPersonService().getPerson(roleMember.getMemberId());
			if (person != null) {
				approvers.add(person);
			}
        }
		
		return approvers;
	}


}