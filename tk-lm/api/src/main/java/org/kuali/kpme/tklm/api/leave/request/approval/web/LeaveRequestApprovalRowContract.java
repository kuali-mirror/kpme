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


/**
 * <p>LeaveRequestApprovalRowContract interface</p>
 *
 */
public interface LeaveRequestApprovalRowContract {
	
	/**
	 * The requestedDate associated with the LeaveRequestApprovalRow
	 * 
	 * <p>
	 * requestedDate of a LeaveRequestApprovalRow
	 * </p>
	 * 
	 * @return requestedDate for LeaveRequestApprovalRow
	 */
	public String getRequestedDate();
	
	/**
	 * The description associated with the LeaveRequestApprovalRow
	 * 
	 * <p>
	 * description of a LeaveRequestApprovalRow
	 * </p>
	 * 
	 * @return description for LeaveRequestApprovalRow
	 */
	public String getDescription();
	
	/**
	 * The leaveCode associated with the LeaveRequestApprovalRow
	 * 
	 * <p>
	 * leaveCode of a LeaveRequestApprovalRow
	 * </p>
	 * 
	 * @return leaveCode for LeaveRequestApprovalRow
	 */
	public String getLeaveCode();
	
	/**
	 * The submittedTime associated with the LeaveRequestApprovalRow
	 * 
	 * <p>
	 * submittedTime of a LeaveRequestApprovalRow
	 * </p>
	 * 
	 * @return submittedTime for LeaveRequestApprovalRow
	 */
	public String getSubmittedTime() ;
	
	/**
	 * The leaveRequestDocId associated with the LeaveRequestApprovalRow
	 * 
	 * <p>
	 * leaveRequestDocId of a LeaveRequestApprovalRow
	 * </p>
	 * 
	 * @return leaveRequestDocId for LeaveRequestApprovalRow
	 */
	public String getLeaveRequestDocId();
	
	/**
	 * The requestedHours associated with the LeaveRequestApprovalRow
	 * 
	 * <p>
	 * requestedHours of a LeaveRequestApprovalRow
	 * </p>
	 * 
	 * @return requestedHours for LeaveRequestApprovalRow
	 */
	public String getRequestedHours();

}
