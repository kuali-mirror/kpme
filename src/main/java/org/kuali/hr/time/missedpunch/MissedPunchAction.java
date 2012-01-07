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
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

public class MissedPunchAction extends KualiTransactionalDocumentActionBase {

    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward act = super.docHandler(mapping, form, request, response);
        MissedPunchForm mpForm = (MissedPunchForm) form;
        MissedPunchDocument mpDoc = (MissedPunchDocument) mpForm.getDocument();
        mpForm.setDocId(mpDoc.getTimesheetDocumentId());

        if (StringUtils.equals(request.getParameter("command"), "initiate")) {
            String tdocId = request.getParameter("tdocid");
            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdocId);
            mpForm.setDocNum(mpDoc.getDocumentNumber());
            mpDoc.setPrincipalId(timesheetDocument.getPrincipalId());
            mpDoc.setTimesheetDocumentId(tdocId);
        }
        if (StringUtils.equals(request.getParameter("command"), "displayDocSearchView")
        		|| StringUtils.equals(request.getParameter("command"), "displayActionListView") ) {
            Person p = KIMServiceLocator.getPersonService().getPerson(mpDoc.getPrincipalId());
            TKContext.getUser().setTargetPerson(p);
            mpForm.setDocId(mpDoc.getDocumentNumber());
        }
        
        return act;
    }

    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MissedPunchForm mpForm = (MissedPunchForm) form;
        mpForm.setEditingMode(new HashMap());
        MissedPunchDocument mpDoc = (MissedPunchDocument) mpForm.getDocument();
        mpDoc.setDocumentStatus("R");
        request.setAttribute(TkConstants.DOCUMENT_ID_REQUEST_NAME, mpDoc.getDocumentNumber());
        request.setAttribute(TkConstants.TIMESHEET_DOCUMENT_ID_REQUEST_NAME, mpDoc.getTimesheetDocumentId());
        ActionForward fwd = super.route(mapping, mpForm, request, response);
        TkServiceLocator.getMissedPunchService().addClockLogForMissedPunch(mpDoc);
        mpForm.setDocId(mpDoc.getDocumentNumber());
        return fwd;

    }

    @Override
    public ActionForward approve(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MissedPunchForm mpForm = (MissedPunchForm) form;
        MissedPunchDocument mpDoc = (MissedPunchDocument) mpForm.getDocument();
        mpDoc.setDocumentStatus("A");
        ActionForward fwd = super.approve(mapping, form, request, response);
        TkServiceLocator.getMissedPunchService().updateClockLogAndTimeBlockIfNecessary(mpDoc);
        return fwd;
    }


}
