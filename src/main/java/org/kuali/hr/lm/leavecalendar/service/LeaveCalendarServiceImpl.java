package org.kuali.hr.lm.leavecalendar.service;


import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.dao.LeaveCalendarDao;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
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

        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(documentId);
        lcd.setLeaveBlocks(leaveBlocks);

        // Fetching assignments
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(lcdh.getPrincipalId(), lcd.getCalendarEntry());
        lcd.setAssignments(assignments);
        
        return lcd;
    }

    @Override
    public LeaveCalendarDocument openLeaveCalendarDocument(String principalId, CalendarEntries calEntry) throws WorkflowException {
        LeaveCalendarDocument doc;

        Date begin = calEntry.getBeginPeriodDateTime();
        Date end = calEntry.getEndPeriodDateTime();

        LeaveCalendarDocumentHeader header = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, begin, end);
        if (header == null) {
            doc = initiateWorkflowDocument(principalId, begin, end, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TYPE, LeaveCalendarDocument.LEAVE_CALENDAR_DOCUMENT_TITLE);
            // This will preload the document data.
            loadLeaveCalendarDocumentData(doc, principalId, calEntry);
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

    /**
     * Preload the document data. It preloads:
     * - LeaveBlocks on the document.
     * @param ldoc
     * @param principalId
     * @param calEntry
     */
    protected void loadLeaveCalendarDocumentData(LeaveCalendarDocument ldoc, String principalId, CalendarEntries calEntry) {
        List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksForDocumentId(ldoc.getDocumentId());
        ldoc.setLeaveBlocks(leaveBlocks);
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, calEntry);
        ldoc.setAssignments(assignments);
    }

    public LeaveCalendarDao getLeaveCalendarDao() {
        return leaveCalendarDao;
    }

    public void setLeaveCalendarDao(LeaveCalendarDao leaveCalendarDao) {
        this.leaveCalendarDao = leaveCalendarDao;
    }

	@Override
	public LeaveCalendarDocument getLeaveCalendarDocument(
			String principalId, CalendarEntries calendarEntry) {
		LeaveCalendarDocument leaveCalendarDocument = new LeaveCalendarDocument(calendarEntry);
		LeaveCalendarDocumentHeader lcdh = new LeaveCalendarDocumentHeader();
		lcdh.setBeginDate(calendarEntry.getBeginPeriodDateTime());
		lcdh.setEndDate(calendarEntry.getEndPeriodDateTime());
		leaveCalendarDocument.setLeaveCalendarDocumentHeader(lcdh);
		// Fetching assignments
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, calendarEntry);
        leaveCalendarDocument.setAssignments(assignments);
		return leaveCalendarDocument;
	}
}
