package org.kuali.hr.time.timesheet.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class TimesheetAction extends TkAction {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimesheetAction.class);


    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TimesheetActionForm taForm = (TimesheetActionForm)form;
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles(); // either backdoor or actual
        String docid = taForm.getDocumentId();

        if (!roles.isDocumentReadable(docid)) {
            throw new AuthorizationException(user.getPrincipalId(), "TimesheetAction: docid: " + docid, "");
        }
    }

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimesheetActionForm taForm = (TimesheetActionForm)form;
		TKUser user = TKContext.getUser();
		String documentId = taForm.getDocumentId();

        LOG.debug("DOCID: " + documentId);

        // Here - viewPrincipal will be the principal of the user we intend to
        // view, be it target user, backdoor or otherwise.
        String viewPrincipal = user.getTargetPrincipalId();
		PayCalendarEntries payCalendarEntries;
		TimesheetDocument td;
		TimesheetDocumentHeader tsdh;

        // By handling the prev/next in the execute method, we are saving one
        // fetch/construction of a TimesheetDocument. If it were broken out into
        // methods, we would first fetch the current document, and then fetch
        // the next one instead of doing it in the single action.
        if (StringUtils.isNotBlank(documentId)) {
            td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
        } else {
            // Default to whatever is active for "today".
            Date currentDate = TKUtils.getTimelessDate(null);
            payCalendarEntries = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(viewPrincipal,  currentDate);
            td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, payCalendarEntries);
        }

        // Set the TKContext for the current timesheet document id.
        if (td != null) {
           setupDocumentOnFormContext(taForm, td);
        } else {
            LOG.error("Null timesheet document in TimesheetAction.");
        }

        // Do this at the end, so we load the document first,
        // then check security permissions via the superclass execution chain.
		return super.execute(mapping, form, request, response);
	}
    
    protected void setupDocumentOnFormContext(TimesheetActionForm taForm, TimesheetDocument td){
    	String viewPrincipal = TKContext.getUser().getTargetPrincipalId();
    	TKContext.setCurrentTimesheetDocumentId(td.getDocumentId());
	    taForm.setTimesheetDocument(td);
	    taForm.setDocumentId(td.getDocumentId());
        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(TkConstants.PREV_TIMESHEET, viewPrincipal, td.getDocumentId());
        TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(TkConstants.NEXT_TIMESHEET, viewPrincipal, td.getDocumentId());
        if( prevTdh != null ) {
            taForm.setPrevDocumentId(prevTdh.getDocumentId());
        }
        if( nextTdh != null) {
            taForm.setNextDocumentId(nextTdh.getDocumentId());
        }
        taForm.setPayCalendarDates(td.getPayCalendarEntry());
    }

}
