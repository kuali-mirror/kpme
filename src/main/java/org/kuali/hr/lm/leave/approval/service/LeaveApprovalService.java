package org.kuali.hr.lm.leave.approval.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.person.TKPerson;

public interface LeaveApprovalService {
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalSummaryRows(List<TKPerson> persons, CalendarEntries payCalendarEntries, List<String> headers);
	
	public Map<String, Map<String, BigDecimal>> getLeaveHoursToPayDayMap(List<LeaveBlock> leaveBlocks,List<String> headers);
	
	public List<Map<String, Object>> getLaveApprovalDetailSections(LeaveCalendarDocumentHeader lcdh);
	
	public List<String> getUniqueLeavePayGroups();
	
    /*
     * returns all calendar entries with LeaveCalendarDocument created and can be approved by given principalId
     */
    public List<CalendarEntries> getAllLeavePayCalendarEntriesForApprover(String principalId, Date currentDate);

    /*
     * remove the employees with no jobs that are eligible for leave fromt the given list of principal ids
     * 
     * @param principalIds
     * 
     */
   public void removeNonLeaveEmployees(List<String> principalIds);
   
   /**
    * Method to get a list of principal ids based on the department work areas.
    *
    * @param roleName
    * @param department
    * @param workArea
    * @param payEndDate
    * @param calGroup
    * @return A list of the PrincipalIds
    */
   List<String> getPrincipalIdsByDeptWorkAreaRolename(String roleName, String department, String workArea, java.sql.Date payBeginDate, java.sql.Date payEndDate, String calGroup);
   
   /**
    * Method to create a map that contains the principal's id and corresponding leave calendar document header.
    *
    *@param persons
    * @param payBeginDate
    * @param payEndDate
    * @return A PrincipalId to LeaveCalendarDocumentHeader mapping.
    */
   public Map<String, LeaveCalendarDocumentHeader> getPrincipalDocumehtHeader(List<TKPerson> persons, Date payBeginDate, Date payEndDate);
}
