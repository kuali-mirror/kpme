package org.kuali.hr.lm.leavecalendar.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.LeaveCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;

public class LeaveCalendarAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(LeaveCalendarAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarForm lcf = (LeaveCalendarForm) form;

        TKUser user = TKContext.getUser();
        String documentId = lcf.getDocumentId();

        // Here - viewPrincipal will be the principal of the user we intend to
        // view, be it target user, backdoor or otherwise.
        String viewPrincipal = user.getTargetPrincipalId();
        CalendarEntries calendarEntry;

        LeaveCalendarDocument lcd = null;
        LeaveCalendarDocumentHeader lcdh = null;

        // By handling the prev/next in the execute method, we are saving one
        // fetch/construction of a TimesheetDocument. If it were broken out into
        // methods, we would first fetch the current document, and then fetch
        // the next one instead of doing it in the single action.
        if (StringUtils.isNotBlank(documentId)) {
            lcd = TkServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);
            calendarEntry = lcd.getCalendarEntry();
        } else {
            // Default to whatever is active for "today".
            Date currentDate = TKUtils.getTimelessDate(null);
            calendarEntry = TkServiceLocator.getCalendarSerivce().getCurrentCalendarDates(viewPrincipal, currentDate);
            lcd = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(viewPrincipal, calendarEntry);
        }

        if (lcd != null) {
            setupDocumentOnFormContext(lcf, lcd);
        } else {
            LOG.error("Null leave calendar document in LeaveCalendarAction.");
        }

//        PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributesService().getPrincipalCalendar(user.getPrincipalId(), TKUtils.getCurrentDate());

        ActionForward forward = super.execute(mapping, form, request, response);
        if (forward.getRedirect()) {
            return forward;
        }
        LeaveCalendar calendar = new LeaveCalendar(calendarEntry, lcd.getDocumentId());
        lcf.setLeaveCalendar(calendar);

        return forward;
    }

    public ActionForward addLedger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarForm lcf = (LeaveCalendarForm) form;
        LeaveCalendarDocument lcd = lcf.getLeaveCalendarDocument();
        DateTime beginDate = new DateTime(TKUtils.convertDateStringToTimestamp(lcf.getBeginDate()));
        DateTime endDate = new DateTime(TKUtils.convertDateStringToTimestamp(lcf.getEndDate()));
        Long leaveCodeId = Long.parseLong(lcf.getSelectedLeaveCode().split("_")[0]);
        String leaveCode = lcf.getSelectedLeaveCode().split("_")[1];
        BigDecimal hours = lcf.getHours();
        String desc = lcf.getDescription();

        TkServiceLocator.getLedgerService().addLedgers(beginDate, endDate, lcd, leaveCode, leaveCodeId, hours, desc);

        return mapping.findForward("basic");
    }

    public ActionForward deleteLedger(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveCalendarForm lcf = (LeaveCalendarForm) form;
        String ledgerId = lcf.getLedgerId();

        //TODO: need security check
        TkServiceLocator.getLedgerService().deleteLedger(Long.parseLong(ledgerId));

        return mapping.findForward("basic");
    }

    protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm, LeaveCalendarDocument lcd) {
        String viewPrincipal = TKContext.getUser().getTargetPrincipalId();
        TKContext.setCurrentLeaveCalendarDocumentId(lcd.getDocumentId());
        TKContext.setCurrentLeaveCalendarDocument(lcd);
        leaveForm.setLeaveCalendarDocument(lcd);
        leaveForm.setDocumentId(lcd.getDocumentId());
//        TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(TkConstants.PREV_TIMESHEET, viewPrincipal);
//        TimesheetDocumentHeader nextTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPrevOrNextDocumentHeader(TkConstants.NEXT_TIMESHEET, viewPrincipal);
//        if( prevTdh != null ) {
//            taForm.setPrevDocumentId(prevTdh.getDocumentId());
//        }
//        if( nextTdh != null) {
//            taForm.setNextDocumentId(nextTdh.getDocumentId());
//        }
        leaveForm.setCalendarEntry(lcd.getCalendarEntry());
    }
}
