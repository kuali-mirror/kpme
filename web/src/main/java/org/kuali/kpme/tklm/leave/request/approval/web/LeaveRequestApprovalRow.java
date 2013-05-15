/**
 * Copyright 2004-2013 The Kuali Foundation
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

public class LeaveRequestApprovalRow implements Comparable<LeaveRequestApprovalRow>, Serializable {

	private String leaveRequestDocId;	
	private String requestedDate;
	private String requestedHours;
	private String description;
	private String leaveCode;
	private String submittedTime;

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

}
