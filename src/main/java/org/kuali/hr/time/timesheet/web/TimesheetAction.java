package org.kuali.hr.time.timesheet.web;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class TimesheetAction extends TkAction {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimesheetAction.class);


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimesheetActionForm taForm = (TimesheetActionForm)form;
		TKUser user = TKContext.getUser();

        // Here - viewPrincipal will be the principal of the user we intend to
        // view, be it target user, backdoor or otherwise.
        String viewPrincipal = user.getTargetPrincipalId();
		PayCalendarEntries payCalendarEntries = null;
		TimesheetDocument td  = null;
		TimesheetDocumentHeader tsdh;

        /**
         * The following logic is for handling the calendar navigation.
         * 1. When the next / prev button is clicked, it will reload (not submitting the form) the page with the param:
         *    calNav=prev/next&documentId=the-current-document-id.
         *    You can search by "calNav=prev" in the fullcalendar*.js and "eventUrl" in the tk.calendar.js to see how they actually work
         *
         * 2. The code right below will grab the next / prev timesheet document headers based on the current document id and
         *    then fetch the needed timesheeet document, and set
         *    - timesheet document: this contains everything we need to load the timesheet
         *    - documentId : this is mainly for the ajax call to grab the timeBlocks
         *    - payCalendarDates : this is used by the calendar widget to render the calendar based on the pay period dates
         *
         * 3. The second time the execute method is called by the ajax call - getTimeBlocks(), it will grab the documentId on the form
         *    and fetch the timeBlocks.
         */
		if(StringUtils.equals(taForm.getCalNav(), TkConstants.PREV_TIMESHEET) || StringUtils.equals(taForm.getCalNav(), TkConstants.NEXT_TIMESHEET)) {
			tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(taForm.getCalNav(), viewPrincipal, taForm.getDocumentId());
            // use the getPayEndDate()-1 as the date to get the current payCalendarDates, since the the payBeginDate is equal to the payEndDate of the previous pay period
			payCalendarEntries = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(viewPrincipal,  TKUtils.getTimelessDate(DateUtils.addDays(tsdh.getPayEndDate(), -1)));
			td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, payCalendarEntries);
		}
		else {
			if(StringUtils.isNotBlank(taForm.getDocumentId())) {
				tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(taForm.getDocumentId());
				payCalendarEntries = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(viewPrincipal,  TKUtils.getTimelessDate(DateUtils.addDays(tsdh.getPayEndDate(), -1)));
			}
			else {
				Date currentDate = TKUtils.getTimelessDate(null);
				payCalendarEntries = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(viewPrincipal,  currentDate);
			}
			td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, payCalendarEntries);
		}

        // Set the TKContext for the current timesheet document id.
        if (td != null) {
            TKContext.setCurrentTimesheetDocumentId(td.getDocumentId());
        }

        if (td != null) {
		    taForm.setTimesheetDocument(td);
		    taForm.setDocumentId(td.getDocumentId());
        } else {
            LOG.error("Null timesheet document in TimesheetAction.");
        }
		taForm.setPayCalendarDates(payCalendarEntries);

		return super.execute(mapping, form, request, response);
	}


}
