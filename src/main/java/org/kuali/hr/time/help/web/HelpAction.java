package org.kuali.hr.time.help.web;

import org.apache.struts.action.ActionForm;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.rice.kns.exception.AuthorizationException;

public class HelpAction extends TkAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        HelpActionForm helpActionForm = (HelpActionForm) form;
    }

//    @Override
//    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return super.execute(mapping, form, request, response);
//    }

}