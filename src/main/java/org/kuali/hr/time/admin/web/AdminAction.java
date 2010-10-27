package org.kuali.hr.time.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kew.web.UserLoginFilter;
import org.kuali.rice.kew.web.session.UserSession;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class AdminAction extends TimesheetAction {

	private static final Logger LOG = Logger.getLogger(AdminAction.class);

	public ActionForward backdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AdminActionForm adminForm = (AdminActionForm) form;
		
		if (StringUtils.isNotBlank(adminForm.getBackdoorPrincipalName())) {
			TKUser tkUser = TKContext.getUser();
			Person backdoorPerson = KIMServiceLocator.getPersonService().getPersonByPrincipalName(adminForm.getBackdoorPrincipalName());

			if (backdoorPerson != null && tkUser != null) {
				UserSession userSession = UserLoginFilter.getUserSession(request);
				
				userSession.establishBackdoorWithPrincipalName(backdoorPerson.getPrincipalId());
				GlobalVariables.getUserSession().setBackdoorUser(backdoorPerson.getPrincipalId());
				
				tkUser.setBackdoorPerson(backdoorPerson);
				
				LOG.debug("\n\n" + TKContext.getUser().getActualPerson().getPrincipalName() + " backdoors as : " + backdoorPerson.getPrincipalName() + "\n\n");
			}
			
		}

		return mapping.findForward("basic");
	}
}
