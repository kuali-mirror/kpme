package org.kuali.hr.time.missedpunch;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.clocklog.TkClockActionValuesFinder;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.struts.action.KualiTransactionalDocumentActionBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
            
            ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(TKContext.getUser().getTargetPrincipalId());
            if(lastClock != null) {
	            MissedPunchDocument lastDoc = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(lastClock.getTkClockLogId());
	            if(lastDoc != null) {
	            	mpDoc.setAssignment(lastDoc.getAssignment());
	            }
            }
        }
        if (StringUtils.equals(request.getParameter("command"), "displayDocSearchView")
        		|| StringUtils.equals(request.getParameter("command"), "displayActionListView") ) {
            Person p = KIMServiceLocator.getPersonService().getPerson(mpDoc.getPrincipalId());
            TKContext.getUser().setTargetPerson(p);
            mpForm.setDocId(mpDoc.getDocumentNumber());
        }
        
        mpForm.setAssignmentReadOnly(false);
        TkClockActionValuesFinder finder = new TkClockActionValuesFinder();
        List<KeyLabelPair> keyLabels = (List<KeyLabelPair>) finder.getKeyValues();
        if(keyLabels.size() == 2 && !mpForm.getDocumentActions().containsKey(KNSConstants.KUALI_ACTION_CAN_EDIT)) {
        	Set<String> actions = TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(TkConstants.CLOCK_IN);
        	boolean flag = true;
        	 for (String entry : actions) {
                 if(!keyLabels.contains(new KeyLabelPair(entry, TkConstants.CLOCK_ACTION_STRINGS.get(entry)))) {
                	 flag = false;
                 }
             }
        	 if(flag) {
        		 mpForm.setAssignmentReadOnly(true); 
        	 }
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
        mpForm.setDocId(mpDoc.getDocumentNumber());
        mpForm.setAssignmentReadOnly(true);
        return fwd;

    }

    @Override
    public ActionForward approve(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MissedPunchForm mpForm = (MissedPunchForm) form;
        MissedPunchDocument mpDoc = (MissedPunchDocument) mpForm.getDocument();
        mpDoc.setDocumentStatus("A");
        mpForm.setAssignmentReadOnly(true);
        request.setAttribute(TkConstants.DOCUMENT_ID_REQUEST_NAME, mpDoc.getDocumentNumber());
        request.setAttribute(TkConstants.TIMESHEET_DOCUMENT_ID_REQUEST_NAME, mpDoc.getTimesheetDocumentId());
        TkServiceLocator.getMissedPunchService().updateClockLogAndTimeBlockIfNecessary(mpDoc);
        ActionForward fwd = super.approve(mapping, form, request, response);
        return fwd;
    }


}
