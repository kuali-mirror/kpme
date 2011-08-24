package org.kuali.hr.time.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
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
		
		personForm.setPrincipalId(TKContext.getTargetPrincipalId());
		Person person = KIMServiceLocator.getPersonService().getPerson(personForm.getPrincipalId());
		personForm.setPrincipalName(person.getName());
		personForm.setJobs(TkServiceLocator.getJobSerivce().getJobs(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate()));
		
		setupRolesOnForm(personForm);

		List<Assignment> lstAssign = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getTargetPrincipalId(), TKUtils.getCurrentDate());
		Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
		
		Map<String,Person> principalIdToPerson = new HashMap<String,Person>();
		for(Assignment assign : lstAssign){
			List<Assignment> lstCurrJobAssign = jobNumberToListAssignments.get(assign.getJobNumber());
			if(lstCurrJobAssign == null){
				lstCurrJobAssign = new ArrayList<Assignment>();
			}
			lstCurrJobAssign.add(assign);
			jobNumberToListAssignments.put(assign.getJobNumber(), lstCurrJobAssign);
		}
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
}
