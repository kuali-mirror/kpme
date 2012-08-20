package org.kuali.hr.time.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class DeleteTimesheetAction extends TkAction {
	
	private static final Logger LOG = Logger.getLogger(DeleteTimesheetAction.class);

    public ActionForward deleteTimesheet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DeleteTimesheetForm deleteTimesheetForm = (DeleteTimesheetForm) form;
    	String documentId = deleteTimesheetForm.getDeleteDocumentId();
    	
    	if (StringUtils.isNotBlank(documentId)) {
    		TkServiceLocator.getTimesheetService().deleteTimesheet(documentId);
    		
    		LOG.debug("Deleting timesheet: " + documentId);
    	}
    	
    	return mapping.findForward("basic");
    }

}