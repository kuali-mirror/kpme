package org.kuali.hr.time.workarea.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

public class WorkAreaAction extends KualiTransactionalDocumentActionBase {

    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	return super.save(mapping, form, request, response);
    }

}
