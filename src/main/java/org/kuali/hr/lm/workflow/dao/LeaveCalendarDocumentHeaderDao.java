package org.kuali.hr.lm.workflow.dao;

import java.util.Date;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;

public interface LeaveCalendarDocumentHeaderDao {
    LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String principalId, Date beginDate, Date endDate);

    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
    public LeaveCalendarDocumentHeader getPreviousDocumentHeader(String principalId, Date beginDate);
    public LeaveCalendarDocumentHeader getNextDocumentHeader(String principalId, Date endDate);
    
    public LeaveCalendarDocumentHeader getMaxEndDateApprovedLeaveCalendar(String principalId);
    
	public LeaveCalendarDocumentHeader getMinBeginDatePendingLeaveCalendar(String principalId);
}
