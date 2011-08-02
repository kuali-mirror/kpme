package org.kuali.hr.time.missedpunch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

public class MissedPunchAction extends KualiTransactionalDocumentActionBase {

	@Override
	public ActionForward docHandler(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward act = super.docHandler(mapping, form, request, response);
		MissedPunchForm mpForm = (MissedPunchForm)form;
		if(StringUtils.equals(request.getParameter("command"), "initiate")){
			//initiate the missed punch object and setup
			String tdocId = request.getParameter("tdocid");
			MissedPunchDocument mpDoc = (MissedPunchDocument)mpForm.getDocument();
			TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdocId);
			
			mpDoc.setPrincipalId(timesheetDocument.getPrincipalId());
			mpDoc.setTimesheetDocumentId(tdocId);
		}
		return act;
	}

}
