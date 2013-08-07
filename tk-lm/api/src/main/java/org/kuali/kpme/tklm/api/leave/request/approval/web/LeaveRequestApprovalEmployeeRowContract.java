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
package org.kuali.kpme.tklm.api.leave.request.approval.web;

import java.util.List;

/**
 * <p>LeaveRequestApprovalEmployeeRowContract interface</p>
 *
 */
public interface LeaveRequestApprovalEmployeeRowContract {
	
	/**
	 * TODO: Make sure this comment is right
   	 * The URL parameters associated with the LeaveRequestApprovalEmployeeRow
   	 * 
   	 * <p>
   	 * Helper method to grab the URL parameters for setting target mode for a
     * user/documentID leave summary.
   	 * <p>
   	 * 
   	 * @return parameter portion of a URL, usable to initiate target mode.
   	 */
	public String getUserTargetURLParams();
		
	/**
	 * The employeeName associated with the LeaveRequestApprovalEmployeeRow
	 * 
	 * <p>
	 * employeeName of a LeaveRequestApprovalEmployeeRow
	 * </p>
	 * 
	 * @return employeeName for LeaveRequestApprovalEmployeeRow
	 */
	public String getEmployeeName();
	
	/**
	 * The principalId associated with the LeaveRequestApprovalEmployeeRow
	 * 
	 * <p>
	 * principalId of a LeaveRequestApprovalEmployeeRow
	 * </p>
	 * 
	 * @return principalId for LeaveRequestApprovalEmployeeRow
	 */
	public String getPrincipalId();
	
	/**
	 * The list of LeaveRequestApprovalRow objects associated with the LeaveRequestApprovalEmployeeRow
	 * 
	 * <p>
	 * leaveRequestList of a LeaveRequestApprovalEmployeeRow
	 * </p>
	 * 
	 * @return leaveRequestList for LeaveRequestApprovalEmployeeRow
	 */
	public List<? extends LeaveRequestApprovalRowContract> getLeaveRequestList();

}
