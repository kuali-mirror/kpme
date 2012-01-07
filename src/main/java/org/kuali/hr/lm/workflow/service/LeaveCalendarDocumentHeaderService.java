package org.kuali.hr.lm.workflow.service;

import java.util.Date;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;

public interface LeaveCalendarDocumentHeaderService {
    LeaveCalendarDocumentHeader getDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getDocumentHeader(String principalId, Date beginDate, Date endDate);
    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
}
