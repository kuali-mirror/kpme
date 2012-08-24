package org.kuali.hr.lm.workflow.service;

import java.util.Date;
import java.util.List;

import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;

public interface LeaveCalendarDocumentHeaderService {
    LeaveCalendarDocumentHeader getDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getDocumentHeader(String principalId, Date beginDate, Date endDate);
    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
    LeaveCalendarDocumentHeader getPrevOrNextDocumentHeader(String prevOrNext, String principalId);
    
    /*
     * return the latest approved leave calendar for given principalId
     */
    public LeaveCalendarDocumentHeader getMaxEndDateApprovedLeaveCalendar(String principalId);
    /*
     * return the earliest pending leave calendar for given principalId
     */
    public LeaveCalendarDocumentHeader getMinBeginDatePendingLeaveCalendar(String principalId);
    /*
     * returns List of LeaveCalendarDocumentHeader for the given principalId
     * Used to populate the lists Of calendar year and calendar entries on Approval page
     */
	public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersForPricipalId(String principalId);
	
	public List<LeaveCalendarDocumentHeader> getAllDelinquentDocumentHeadersForPricipalId(String principalId);
}

