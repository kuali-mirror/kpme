package org.kuali.hr.lm.leavecalendar.service;


import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.dao.LeaveCalendarDao;
import org.kuali.hr.lm.ledger.Ledger;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;

public class LeaveCalendarServiceImpl implements LeaveCalendarService {

    private LeaveCalendarDao leaveCalendarDao;

    @Override
    public LeaveCalendarDocument getLeaveCalendarDocument(String documentId) {
        LeaveCalendarDocument lcd = null;
        LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentId);
        
        if (lcdh != null) {
            lcd = new LeaveCalendarDocument(lcdh);
            CalendarEntries pce = TkServiceLocator.getCalendarSerivce().getCalendarDatesByPayEndDate(lcdh.getPrincipalId(), lcdh.getEndDate());
            lcd.setCalendarEntry(pce);
        } else {
            throw new RuntimeException("Could not find LeaveCalendarDocumentHeader for DocumentID: " + documentId);
        }
        
        List<Ledger> ledgers = TkServiceLocator.getLedgerService().getLedgersForDocumentId(documentId);
        lcd.setLedgers(ledgers);
        
        return lcd;
    }

    @Override
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntries calEntry) throws WorkflowException{
        LeaveCalendarDocument doc;

        Date begin = calEntry.getBeginPeriodDateTime();
        Date end = calEntry.getEndPeriodDateTime();

        LeaveCalendarDocumentHeader header = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, begin, end);
        if (header == null) {
            doc = initiateWorkflowDocument(principalId, begin, end, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TITLE);
            //this.loadTimesheetDocumentData(timesheetDocument, principalId, calendarDates);
        } else {
            doc = getLeaveCalendarDocument(header.getDocumentId());
        }
        doc.setCalendarEntry(calEntry);
        // TODO: need to set the summary
        return doc;
    }

    protected LeaveCalendarDocument initiateWorkflowDocument(String principalId, Date payBeginDate, Date payEndDate, String documentType, String title) throws WorkflowException {
        LeaveCalendarDocument leaveCalendarDocument = null;
        WorkflowDocument workflowDocument = null;

        workflowDocument = new WorkflowDocument(principalId, documentType);
        workflowDocument.setTitle(title);

        String status = workflowDocument.getRouteHeader().getDocRouteStatus();
        LeaveCalendarDocumentHeader documentHeader = new LeaveCalendarDocumentHeader(workflowDocument.getRouteHeaderId().toString(), principalId, payBeginDate, payEndDate, status);

        documentHeader.setDocumentId(workflowDocument.getRouteHeaderId().toString());
        documentHeader.setDocumentStatus("I");

        TkServiceLocator.getLeaveCalendarDocumentHeaderService().saveOrUpdate(documentHeader);
        leaveCalendarDocument = new LeaveCalendarDocument(documentHeader);

        // TODO:
        //TkServiceLocator.getTkSearchableAttributeService().updateSearchableAttribute(leaveCalendarDocument, payEndDate);

        return leaveCalendarDocument;
    }

    public LeaveCalendarDao getLeaveCalendarDao() {
        return leaveCalendarDao;
    }

    public void setLeaveCalendarDao(LeaveCalendarDao leaveCalendarDao) {
        this.leaveCalendarDao = leaveCalendarDao;
    }
}
