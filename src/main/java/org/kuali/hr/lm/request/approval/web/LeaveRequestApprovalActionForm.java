package org.kuali.hr.lm.request.approval.web;

import java.util.List;

import org.kuali.hr.time.base.web.TkForm;

public class LeaveRequestApprovalActionForm extends TkForm {
	
	private static final long serialVersionUID = 1L;
	private List<LeaveRequestApprovalEmployeeRow> employeeRows;
	
	private String approveList;
	private String disapproveList;
	private String deferList;
	private String reasonList;
	private String principalId;
    private String outputString;		// for displaying error messages
    private String selectedDept;
	
	public List<LeaveRequestApprovalEmployeeRow> getEmployeeRows() {
		return employeeRows;
	}
	public void setEmployeeRows(List<LeaveRequestApprovalEmployeeRow> employeeRows) {
		this.employeeRows = employeeRows;
	}
	public String getApproveList() {
		return approveList;
	}
	public void setApproveList(String approveList) {
		this.approveList = approveList;
	}
	public String getDisapproveList() {
		return disapproveList;
	}
	public void setDisapproveList(String disapproveList) {
		this.disapproveList = disapproveList;
	}
	public String getDeferList() {
		return deferList;
	}
	public void setDeferList(String deferList) {
		this.deferList = deferList;
	}
	public String getReasonList() {
		return reasonList;
	}
	public void setReasonList(String reasonList) {
		this.reasonList = reasonList;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getOutputString() {
		return outputString;
	}
	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}
	public String getSelectedDept() {
		return selectedDept;
	}
	public void setSelectedDept(String selectedDept) {
		this.selectedDept = selectedDept;
	}
	
	
	
}
