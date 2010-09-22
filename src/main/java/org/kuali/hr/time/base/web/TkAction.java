package org.kuali.hr.time.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.web.struts.action.KualiAction;

public class TkAction extends KualiAction {


    public ActionForward clearBackdoor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TKContext.clearBackdoorUser();
    	return mapping.findForward("basic");
    }

}
