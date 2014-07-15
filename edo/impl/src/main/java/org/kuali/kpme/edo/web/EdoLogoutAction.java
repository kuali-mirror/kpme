package org.kuali.kpme.edo.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/6/13
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class EdoLogoutAction extends EdoAction {


    public final ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        return super.execute(mapping, form, request, response);
    }

    public final ActionForward sessionExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        return mapping.findForward("sessionExpired");
    }

}
