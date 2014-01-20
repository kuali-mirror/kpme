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
package org.kuali.kpme.tklm.leave.request.approval.web;

import java.io.Serializable;

import org.kuali.kpme.tklm.api.leave.request.approval.web.LeaveRequestApprovalRowContract;

public class LeaveRequestApprovalRow implements Comparable<LeaveRequestApprovalRow>, Serializable, LeaveRequestApprovalRowContract {

	private static final long serialVersionUID = -5107503528620209360L;
	
	private String employeeName;
	private String principalId;	
	private String leaveRequestDocId;	
	private String requestedDate;
	private String requestedHours;
	private String description;
	private String leaveCode;
	private String submittedTime;
	private String assignmentTitle;
	private String requestStatus;
	private boolean selected;

	@Override
	public int compareTo(LeaveRequestApprovalRow row) {
		  return requestedDate.compareToIgnoreCase(row.getRequestedDate());
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

	public String getRequestedDate() {
		return requestedDate;
	}
	
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	public String getLeaveRequestDocId() {
		return leaveRequestDocId;
	}

	public void setLeaveRequestDocId(String leaveRequestDocId) {
		this.leaveRequestDocId = leaveRequestDocId;
	}
	
	public String getRequestedHours() {
		return requestedHours;
	}

	public void setRequestedHours(String requestedHours) {
		this.requestedHours = requestedHours;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getAssignmentTitle() {
		return assignmentTitle;
	}

	public void setAssignmentTitle(String assignmentTitle) {
		this.assignmentTitle = assignmentTitle;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
}
