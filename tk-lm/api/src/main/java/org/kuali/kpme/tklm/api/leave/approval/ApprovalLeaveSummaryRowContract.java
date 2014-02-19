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
package org.kuali.kpme.tklm.api.leave.approval;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.rice.kew.api.note.Note;

/**
 * <p>ApprovalLeaveSummaryRowContract interface</p>
 *
 */
public interface ApprovalLeaveSummaryRowContract extends Comparable<ApprovalLeaveSummaryRowContract>{
   
	/**
	 * Indicates if this record is ready to be approved.
	 * 
	 * <p>
	 * Is this record ready to be approved?
	 * <p>
	 * 
	 * @return true if a valid TK_APPROVER / TK_PROCESSOR can approve, false otherwise.
	 */
    public boolean isApprovable();

	/**
	 * The name associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * name of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return name for ApprovalLeaveSummaryRow
	 */
	public String getName();
	
	/**
	 * The principalId associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * principalId of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return principalId for ApprovalLeaveSummaryRow
	 */
	public String getPrincipalId();
	
	/**
	 * TODO: Make sure this comment is right
   	 * The URL parameters associated with the ApprovalLeaveSummaryRow
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
	 * The list of LeaveBlock objects associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * leaveBlockList of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return leaveBlockList for ApprovalLeaveSummaryRow
	 */
	public List<? extends LeaveBlockContract> getLeaveBlockList();
	
	/**
	 * The documentId associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * documentId of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return documentId for ApprovalLeaveSummaryRow
	 */
	public String getDocumentId();

	/**
	 * The list of warnings associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * warnings of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return warnings for ApprovalLeaveSummaryRow
	 */
	public List<String> getWarnings();

	/**
	 * TODO: Put a better comment
	 * The selected associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * selected of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return selected for ApprovalLeaveSummaryRow
	 */
	public String getSelected();

	/**
	 * The list of notes associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * notes of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return notes for ApprovalLeaveSummaryRow
	 */
    public List<Note> getNotes();

    /**
	 * The lastApproveMessage associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * lastApproveMessage of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return lastApproveMessage for ApprovalLeaveSummaryRow
	 */
	public String getLastApproveMessage();
	
	/**
	 * The approvalStatus associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * approvalStatus of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return approvalStatus for ApprovalLeaveSummaryRow
	 */
	public String getApprovalStatus();

	/**
	 * The map of earn code leave hours associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * earnCodeLeaveHours of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return earnCodeLeaveHours for ApprovalLeaveSummaryRow
	 */
	public Map<LocalDateTime, Map<String, BigDecimal>> getEarnCodeLeaveHours();

	/**
     * TODO: Is this field needed?
	 * The moreThanOneCalendar flag of the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * moreThanOneCalendar flag of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return moreThanOneCalendar flag for an ApprovalTimeSummaryRow
	 */
	public Boolean getMoreThanOneCalendar();

	/**
     * TODO: Is this field needed?
	 * The exemptEmployee flag of the ApprovalTimeSummaryRow
	 * 
	 * <p>
	 * exemptEmployee flag of an ApprovalTimeSummaryRow
	 * <p>
	 * 
	 * @return moreThaexemptEmployeenOneCalendar flag for an ApprovalTimeSummaryRow
	 */
	public Boolean getExemptEmployee();

	/**
	 * The color associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * color of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return color for ApprovalLeaveSummaryRow
	 */
	public String getColor();

    /**
     * Set color associated with the ApprovalLeaveSummaryRow
     *
     * <p>
     * color of an ApprovalLeaveSummaryRow
     * <p>
     *
     * @return color for ApprovalLeaveSummaryRow
     */
    public void setColor(String color);
	
	/**
	 * The map of role names associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * roleNames of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return roleNames for ApprovalLeaveSummaryRow
	 */
    public Map<String, String> getRoleNames();
    
    /**
	 * The role name of the principalId associated with the ApprovalLeaveSummaryRow
	 * 
	 * <p>
	 * role name of the principalId of an ApprovalLeaveSummaryRow
	 * <p>
	 * 
	 * @return getRoleNames().get(HrContext.getPrincipalId()) for ApprovalLeaveSummaryRow
	 */
    public String getRoleName();

}
