package org.kuali.hr.time.base.web;

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
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

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
		//TODO make this applicable for the given timesheet
		List<Assignment> lstAssign = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate());
		Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
		Map<Long,List<TkRole>> workAreaToApprover = new HashMap<Long,List<TkRole>>();
		Map<String,List<TkRole>> deptToOrgAdmin = new HashMap<String,List<TkRole>>();
		
		Map<String,Person> principalIdToPerson = new HashMap<String,Person>();

		Map<Long,List<Person>> workAreaToApproverPerson = new HashMap<Long, List<Person>>();
		
		for(Assignment assign : lstAssign){
			List<TkRole> lstApproverRoles = TkServiceLocator.getTkRoleService().getWorkAreaRoles(assign.getWorkArea(), TkConstants.ROLE_TK_APPROVER,
												TKUtils.getCurrentDate());
			workAreaToApprover.put(assign.getWorkArea(), lstApproverRoles);
			for(TkRole role : lstApproverRoles){
				if(role.getPositionNumber() != null){
					List<Job> lstJobs = TkServiceLocator.getJobSerivce().getActiveJobsForPosition(role.getPositionNumber(), TKUtils.getCurrentDate());
					for(Job j : lstJobs){
						Person approver = KIMServiceLocator.getPersonService().getPerson(j.getPrincipalId());
						if(approver!=null){
							addApproverPersonForWorkArea(assign.getWorkArea(), approver, workAreaToApproverPerson);
						}
					}
				} else{
					Person approver = KIMServiceLocator.getPersonService().getPerson(role.getPrincipalId());
					if(approver!=null){
						addApproverPersonForWorkArea(assign.getWorkArea(), approver, workAreaToApproverPerson);
					}
				}
			}

			List<TkRole> lstOrgAdminRoles = TkServiceLocator.getTkRoleService().getDepartmentRoles(assign.getWorkAreaObj().getDept(),
													TkConstants.ROLE_TK_ORG_ADMIN, TKUtils.getCurrentDate());
			deptToOrgAdmin.put(assign.getWorkAreaObj().getDept(), lstOrgAdminRoles);

			for(TkRole role : lstOrgAdminRoles){
				if(StringUtils.isNotBlank(role.getPrincipalId())){
					Person orgAdmin = KIMServiceLocator.getPersonService().getPerson(role.getPrincipalId());
					principalIdToPerson.put(orgAdmin.getPrincipalId(), orgAdmin);
				}
			}
			List<Assignment> lstCurrJobAssign = jobNumberToListAssignments.get(assign.getJobNumber());
			if(lstCurrJobAssign == null){
				lstCurrJobAssign = new ArrayList<Assignment>();
			}
			lstCurrJobAssign.add(assign);
			jobNumberToListAssignments.put(assign.getJobNumber(), lstCurrJobAssign);
		}
		personForm.setJobNumberToListAssignments(jobNumberToListAssignments);
		personForm.setWorkAreaToApprover(workAreaToApprover);
		personForm.setDeptToOrgAdmin(deptToOrgAdmin);
		personForm.setPrincipalIdToPerson(principalIdToPerson);
		personForm.setWorkAreaToApproverPerson(workAreaToApproverPerson);
		return actForw;
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
