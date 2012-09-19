package org.kuali.hr.lm.approval.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.time.approval.web.TimeApprovalActionForm;

public class LeaveApprovalActionForm extends TimeApprovalActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private List<ApprovalLeaveSummaryRow> leaveApprovalRows;
	private List<String> leaveCalendarLabels = new ArrayList<String>();
	
	public List<ApprovalLeaveSummaryRow> getLeaveApprovalRows() {
		return leaveApprovalRows;
	}
	public void setLeaveApprovalRows(List<ApprovalLeaveSummaryRow> leaveApprovalRows) {
		this.leaveApprovalRows = leaveApprovalRows;
	}
	public List<String> getLeaveCalendarLabels() {
		return leaveCalendarLabels;
	}
	public void setLeaveCalendarLabels(List<String> leaveCalendarLabels) {
		this.leaveCalendarLabels = leaveCalendarLabels;
	}
}
