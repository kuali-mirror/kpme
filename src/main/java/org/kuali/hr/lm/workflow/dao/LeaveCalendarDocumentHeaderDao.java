package org.kuali.hr.lm.workflow.dao;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;

import java.util.Date;

public interface LeaveCalendarDocumentHeaderDao {
    LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String principalId, Date beginDate, Date endDate);

    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
}
