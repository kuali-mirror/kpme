package org.kuali.hr.lm.leave.approval.service;

import java.math.BigDecimal;
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
	
	public List<Map<String, Object>> getLaveApprovalDetailSectins(LeaveCalendarDocumentHeader lcdh);
}
