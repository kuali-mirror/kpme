package org.kuali.hr.time.base.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
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
		//TODO make this applicable for the given timesheet
		List<Assignment> lstAssign = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getPrincipalId(), TKUtils.getCurrentDate());
		Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
		Map<Long,List<TkRole>> workAreaToApprover = new HashMap<Long,List<TkRole>>();
		Map<String,List<TkRole>> deptToOrgAdmin = new HashMap<String,List<TkRole>>();
		Map<String,Person> principalIdToPerson = new HashMap<String,Person>();

		for(Assignment assign : lstAssign){
			List<TkRole> lstApproverRoles = TkServiceLocator.getTkRoleService().getWorkAreaRoles(assign.getWorkArea(), TkConstants.ROLE_TK_APPROVER,
												TKUtils.getCurrentDate());
			workAreaToApprover.put(assign.getWorkArea(), lstApproverRoles);
			for(TkRole role : lstApproverRoles){
				Person approver = KIMServiceLocator.getPersonService().getPerson(role.getPrincipalId());
				principalIdToPerson.put(approver.getPrincipalId(), approver);
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
		return actForw;
	}
}
