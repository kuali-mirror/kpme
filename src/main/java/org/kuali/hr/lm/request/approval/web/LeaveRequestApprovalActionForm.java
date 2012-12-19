/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.request.approval.web;

import java.util.List;

import org.kuali.hr.time.base.web.ApprovalForm;

public class LeaveRequestApprovalActionForm extends ApprovalForm {
	
	private static final long serialVersionUID = 1L;
	
	private List<LeaveRequestApprovalEmployeeRow> employeeRows;
	private String approveList;
	private String disapproveList;
	private String deferList;
	private String principalId;     
	
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
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	
}
