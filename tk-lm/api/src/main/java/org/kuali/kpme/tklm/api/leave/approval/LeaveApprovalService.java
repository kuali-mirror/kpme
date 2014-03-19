/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.api.leave.approval;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.workflow.LeaveCalendarDocumentHeaderContract;
import org.kuali.rice.kew.api.note.Note;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LeaveApprovalService {
	public List<ApprovalLeaveSummaryRowContract> getLeaveApprovalSummaryRows(List<String> principalIds, CalendarEntry payCalendarEntry, List<LocalDateTime> leaveSummaryDates, String docIdSearchTerm);
	
	public Map<LocalDateTime, Map<String, BigDecimal>> getEarnCodeLeaveHours(List<LeaveBlock> leaveBlocks, List<LocalDateTime> leaveSummaryDates);
	
	public List<Map<String, Object>> getLeaveApprovalDetailSections(LeaveCalendarDocumentHeaderContract lcdh);
	
	public Map<LocalDateTime, Map<String, BigDecimal>> getAccrualCategoryLeaveHours(List<LeaveBlock> leaveBlocks, List<LocalDateTime> leaveSummaryDates);

    /**
     * Used to determine if there are notes on a document
     * @param documentNumber
     * @return list of note objects
     */
    public List<Note> getNotesForDocument(String documentNumber);

    /*
     * remove the employees with no jobs that are eligible for leave from the given list of principal ids
     * 
     * @param principalIds
     * 
     */
   public void removeNonLeaveEmployees(List<String> principalIds);

   /**
    * Method to create a map that contains the principal's id and corresponding leave calendar document header.
    *
    *@param principalIds
    * @param payBeginDate
    * @param payEndDate
    * @return A PrincipalId to LeaveCalendarDocumentHeader mapping.
    */
   public Map<String, LeaveCalendarDocumentHeaderContract> getPrincipalDocumentHeader(List<String> principalIds, DateTime payBeginDate, DateTime payEndDate);
   
   /**
    * 
    * @param principalId
    * @param flsaStatus
    * @param chkForLeaveEligible
    * @return
    */
   public boolean isActiveAssignmentFoundOnJobFlsaStatus(String principalId, String flsaStatus, boolean chkForLeaveEligible);
 
   /**
    * Method to get a lit of principal ids based on work area list, calendarGroup and dates
    * @param workAreaList
    * @param calendarGroup
    * @param effdt
    * @param beginDate
    * @param endDate
    * @return
    */		   
   public List<String> getLeavePrincipalIdsWithSearchCriteria(
           List<String> workAreaList, String calendarGroup, LocalDate effdt, LocalDate beginDate, LocalDate endDate);
}
