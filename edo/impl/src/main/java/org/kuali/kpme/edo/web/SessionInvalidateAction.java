package org.kuali.kpme.edo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.kuali.kpme.edo.util.EdoContext;

public class SessionInvalidateAction extends DispatchAction {
	public ActionForward invalidateUserCache(ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		//Turn off invalidating for production issue
		//request.getSession().invalidate();
		EdoContext.clearFormsFromSession();
		EdoContext.clear();
		request.getSession().invalidate();
		return mapping.findForward("basic");
		
	}

}
