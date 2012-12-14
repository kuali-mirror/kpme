package org.kuali.hr.lm.request.approval.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveRequestApprovalEmployeeRow implements Comparable<LeaveRequestApprovalEmployeeRow> {
	
	private String employeeName;
	private String principalId;	
	private List<LeaveRequestApprovalRow> leaveRequestList = new ArrayList<LeaveRequestApprovalRow>();
	
	public int compareTo(LeaveRequestApprovalEmployeeRow row) {
        return employeeName.compareToIgnoreCase(row.getEmployeeName());
    }

	 public String getUserTargetURLParams() {
        StringBuffer link = new StringBuffer();
        link.append("methodToCall=changeTargetPerson");
        Person person = KimApiServiceLocator.getPersonService().getPerson(this.getPrincipalId());
        link.append("&principalName=").append(person.getPrincipalName());
        
        return link.toString();
    }
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public List<LeaveRequestApprovalRow> getLeaveRequestList() {
		return leaveRequestList;
	}

	public void setLeaveRequestList(List<LeaveRequestApprovalRow> leaveRequestList) {
		this.leaveRequestList = leaveRequestList;
	}

}
