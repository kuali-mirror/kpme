package org.kuali.hr.time.base.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Person person = KIMServiceLocator.getPersonService().getPerson(personForm.getPrincipalId());
		personForm.setPrincipalName(person.getPrincipalName());
		// set name
		personForm.setName(person.getName());
		personForm.setJobs(TkServiceLocator.getJobSerivce().getJobs(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate()));
		
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
		UserRoles roles = TKContext.getUser().getCurrentRoles();
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
				List<Job> lstJobs = TkServiceLocator.getJobSerivce().getActiveJobsForPosition(role.getPositionNumber(), TKUtils.getCurrentDate());
				for(Job j : lstJobs){
					Person person = KIMServiceLocator.getPersonService().getPerson(j.getPrincipalId());
					if(person !=null){
						deptAdminPeople.add(person);
					}
				}
			} else{
				Person person = KIMServiceLocator.getPersonService().getPerson(role.getPrincipalId());
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
				List<Job> lstJobs = TkServiceLocator.getJobSerivce().getActiveJobsForPosition(role.getPositionNumber(), TKUtils.getCurrentDate());
				for(Job j : lstJobs){
					Person approver = KIMServiceLocator.getPersonService().getPerson(j.getPrincipalId());
					if(approver!=null){
					addApproverPersonForWorkArea(workArea, approver, workAreaToApproverPerson);
					}
				}
			} else{
				Person approver = KIMServiceLocator.getPersonService().getPerson(role.getPrincipalId());
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
		approvers.add(person);
		workAreaToApproverPerson.put(workArea, approvers);
	}
}
