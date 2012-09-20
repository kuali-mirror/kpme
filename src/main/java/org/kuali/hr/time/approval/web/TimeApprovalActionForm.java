package org.kuali.hr.time.approval.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.base.web.TkApprovalForm;

public class TimeApprovalActionForm extends TkApprovalForm {

	private static final long serialVersionUID = 339670908525224389L;
	
	private List<String> payCalendarLabels = new ArrayList<String>();
	private List<ApprovalTimeSummaryRow> approvalRows;
	
	public List<String> getPayCalendarLabels() {
		return payCalendarLabels;
	}
	public void setPayCalendarLabels(List<String> payCalendarLabels) {
		this.payCalendarLabels = payCalendarLabels;
	}
	public List<ApprovalTimeSummaryRow> getApprovalRows() {
		return approvalRows;
	}
	public void setApprovalRows(List<ApprovalTimeSummaryRow> approvalRows) {
		this.approvalRows = approvalRows;
	}
	
	

}
