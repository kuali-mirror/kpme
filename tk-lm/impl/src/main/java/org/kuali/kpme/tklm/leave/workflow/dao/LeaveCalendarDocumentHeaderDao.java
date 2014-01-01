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
package org.kuali.kpme.tklm.leave.workflow.dao;

import java.util.List;

import org.joda.time.DateTime;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;

public interface LeaveCalendarDocumentHeaderDao {
    LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String documentId);
    LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String principalId, DateTime beginDate, DateTime endDate);

    void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader);
    public LeaveCalendarDocumentHeader getPreviousDocumentHeader(String principalId, DateTime beginDate);
    public LeaveCalendarDocumentHeader getNextDocumentHeader(String principalId, DateTime endDate);
    
    public List<LeaveCalendarDocumentHeader> getDocumentHeaders(DateTime beginDate, DateTime endDate);
    
    public LeaveCalendarDocumentHeader getMaxEndDateApprovedLeaveCalendar(String principalId);
    
	public LeaveCalendarDocumentHeader getMinBeginDatePendingLeaveCalendar(String principalId);
	
	public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersForPricipalId(String principalId);
	
	public List<LeaveCalendarDocumentHeader> getSubmissionDelinquentDocumentHeaders(String principalId, DateTime beforeDate);
	
	public List<LeaveCalendarDocumentHeader> getApprovalDelinquentDocumentHeaders(String principalId);

    public void deleteLeaveCalendarHeader(String documentId);
    
    public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersInRangeForPricipalId(String principalId, DateTime beginDate, DateTime endDate);
}
