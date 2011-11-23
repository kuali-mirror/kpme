package org.kuali.hr.lm.workflow.service;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;

import java.util.Date;

public interface LeaveCalendarDocumentHeaderService {
    LeaveCalendarDocumentHeader getDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getDocumentHeader(String principalId, Date beginDate, Date endDate);
    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
}
