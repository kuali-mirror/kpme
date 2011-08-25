package org.kuali.hr.time.missedpunch;

import java.util.HashMap;

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
			mpForm.setDocNum(mpDoc.getDocumentNumber());
			mpDoc.setPrincipalId(timesheetDocument.getPrincipalId());
			mpDoc.setTimesheetDocumentId(tdocId);
		}
		return act;
	}

	@Override
	public ActionForward route(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MissedPunchForm mpForm = (MissedPunchForm)form;
		mpForm.setEditingMode(new HashMap());
		MissedPunchDocument mpDoc = (MissedPunchDocument)mpForm.getDocument();
		mpDoc.setDocumentStatus("R");
		ActionForward fwd =  super.route(mapping, mpForm, request, response);
		TkServiceLocator.getMissedPunchService().addClockLogForMissedPunch(mpDoc);
		return fwd;
		
	}

	@Override
	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MissedPunchForm mpForm = (MissedPunchForm)form;
		MissedPunchDocument mpDoc = (MissedPunchDocument)mpForm.getDocument();
		mpDoc.setDocumentStatus("A");
		ActionForward fwd = super.approve(mapping, form, request, response); 
		TkServiceLocator.getMissedPunchService().updateClockLogAndTimeBlockIfNecessary(mpDoc);
		return fwd;
	}

	
	
}
