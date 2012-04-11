package org.kuali.hr.lm.workflow.service;

import java.util.Date;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public interface LeaveCalendarDocumentHeaderService {
    LeaveCalendarDocumentHeader getDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getDocumentHeader(String principalId, Date beginDate, Date endDate);
    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
    LeaveCalendarDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId);
}
