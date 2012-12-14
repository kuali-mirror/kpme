package org.kuali.hr.lm.request.approval.web;

public class LeaveRequestApprovalRow implements Comparable<LeaveRequestApprovalRow> {

	//TODO: leave request document Id, LeaveRequest document does not exist yet
	// may only need to define a leave request document attribute, then the following attributes
	// should come from the leave request document
	private String leaveRequestDocId;	
	private String requestedDate;
	private String requestedHours;
	private String leaveCode;
	private String submittedTime;
	private String leaveBlockId;
	// end of leave request document attributes
	
	private String selected = "off";
	private boolean approve = Boolean.FALSE;
	private boolean disapprove = Boolean.FALSE;
	private boolean defer = Boolean.FALSE;
	private String deferDisapproveReason;
	
	@Override
	public int compareTo(LeaveRequestApprovalRow row) {
		  return requestedDate.compareToIgnoreCase(row.getRequestedDate());
	}
	
	public String getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getLeaveCode() {
		return leaveCode;
	}
	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}
	public String getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(String submittedTime) {
		this.submittedTime = submittedTime;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public boolean isApprove() {
		return approve;
	}
	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	public boolean isDisapprove() {
		return disapprove;
	}
	public void setDisapprove(boolean disapprove) {
		this.disapprove = disapprove;
	}
	public boolean isDefer() {
		return defer;
	}
	public void setDefer(boolean defer) {
		this.defer = defer;
	}
	public String getDeferDisapproveReason() {
		return deferDisapproveReason;
	}
	public void setDeferDisapproveReason(String deferDisapproveReason) {
		this.deferDisapproveReason = deferDisapproveReason;
	}

	public String getLeaveRequestDocId() {
		return leaveRequestDocId;
	}

	public void setLeaveRequestDocId(String leaveRequestDocId) {
		this.leaveRequestDocId = leaveRequestDocId;
	}

	public String getLeaveBlockId() {
		return leaveBlockId;
	}

	public void setLeaveBlockId(String leaveBlockId) {
		this.leaveBlockId = leaveBlockId;
	}

	public String getRequestedHours() {
		return requestedHours;
	}

	public void setRequestedHours(String requestedHours) {
		this.requestedHours = requestedHours;
	}

}
