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
import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveRequestApprovalEmployeeRow implements Comparable<LeaveRequestApprovalEmployeeRow>, Serializable {
	
	private String employeeName;
	private String principalId;	
	private List<LeaveRequestApprovalRow> leaveRequestList = new ArrayList<LeaveRequestApprovalRow>();
	
	public int compareTo(LeaveRequestApprovalEmployeeRow row) {
        return employeeName.compareToIgnoreCase(row.getEmployeeName());
    }

	 public String getUserTargetURLParams() {
        StringBuffer link = new StringBuffer();
        link.append("methodToCall=changeTargetPerson");
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(this.getPrincipalId());
        link.append("&principalName=").append(principal.getPrincipalName());
        
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
