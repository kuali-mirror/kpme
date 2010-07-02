package org.kuali.hr.time.paycalendar.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase;

public class PayCalendarAction extends KualiDocumentActionBase {

    private static Logger LOG = Logger.getLogger(PayCalendarAction.class);
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	LOG.debug("execute // PayCalendarAction");
	return super.execute(mapping, form, request, response);
    }

}
