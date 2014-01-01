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
package org.kuali.kpme.tklm.api.leave.block;

import java.math.BigDecimal;


/**
 * <p>LeaveBlockRendererContract interface</p>
 *
 */
public interface LeaveBlockRendererContract {

	/**
	 * The LeaveBlock object associated with the LeaveBlockRenderer
	 * 
	 * <p>
	 * leaveBlock of a LeaveBlockRenderer
	 * <p>
	 * 
	 * @return leaveBlock for LeaveBlockRenderer
	 */	
    public LeaveBlockContract getLeaveBlock();

	/**
	 * The leave amount of the LeaveBlock object associated with the LeaveBlockRenderer
	 * 
	 * <p>
	 * leaveBlock.getLeaveAmount() of a LeaveBlockRenderer
	 * <p>
	 * 
	 * @return leaveBlock.getLeaveAmount() for LeaveBlockRenderer
	 */
    public BigDecimal getHours();

    /**
	 * The EarnCode name of the LeaveBlock object associated with the LeaveBlockRenderer
	 * 
	 * <p>
	 * leaveBlock.getEarnCode() of a LeaveBlockRenderer
	 * <p>
	 * 
	 * @return leaveBlock.getEarnCode() for LeaveBlockRenderer
	 */
    public String getEarnCode();

    /**
   	 * The leave block id of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.getLmLeaveBlockId() of a LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.getLmLeaveBlockId() for LeaveBlockRenderer
   	 */
    public String getLeaveBlockId();

    /**
   	 * The document id of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.getDocumentId() of a LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.getDocumentId() for LeaveBlockRenderer
   	 */
    public String getDocumentId();

    /**
   	 * The assignment title of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.getAssignmentTitle() of a LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.getAssignmentTitle() for LeaveBlockRenderer
   	 */
	public String getAssignmentTitle();

	/**
   	 * The editable flag of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.isEditable() of a LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.isEditable() for LeaveBlockRenderer
   	 */
    public boolean isEditable(); 

    /**
   	 * The deletable flag of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.isDeletable() of a LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.isDeletable() for LeaveBlockRenderer
   	 */
    public boolean isDeletable();

    /**
	 * The assignment class associated with the LeaveBlockRenderer
	 * 
	 * <p>
	 * assignmentClass of a LeaveBlockRenderer
	 * <p>
	 * 
	 * @return assignmentClass for LeaveBlockRenderer
	 */	
	public String getAssignmentClass();

	/**
   	 * The request status string of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.getRequestStatusString().toLowerCase() of an LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.getRequestStatusString().toLowerCase() for LeaveBlockRenderer
   	 */
    public String getRequestStatusClass();

    /**
  	 * The details of the LeaveBlock object associated with the LeaveBlockRenderer
  	 * 
  	 * <p>
  	 * If the leave block type is Accrual Service
  	 *     if the ScheduleTimeOffId of the leave block is not null, return the description of the ScheduleTimeOff, else "accrual"
  	 * If the leave block type is Carry Over Adjustment
  	 *     if the description of the leave block is Max carry over adjustment, return "carryover adjustment", else "adjustment"
  	 * If the leave block type is Balance Transfer
  	 *     if the description of the leave block contains Forfeited, return "transfer forfeiture"
  	 *     else if it contains Amount transferred, return "amount transferred"
  	 *     else if it contains Transferred amount, return "transferred amount"
  	 *     else "balance transfer"
  	 * If the leave block type is Leave Payout
  	 *     if the description of the leave block contains Forfeited, return "payout forfeiture"
  	 *     else if it contains Amount paid out, return "amount paid out"
  	 *     else if it contains Payout amount, return "payout amount"
  	 *     else "leave payout"
  	 * Else
  	 *    if the leave block type is not Leave Calendar nor Time Calendar, return corresponding string in LEAVE_BLOCK_TYPE_MAP for the leave block type 
  	 *    else return leaveBlock.getRequestStatusString()
  	 * <p>
  	 * 
  	 * @return see above
  	 */
    public String getLeaveBlockDetails();
    
    /**
   	 * The description of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * leaveBlock.getDescription() of a LeaveBlockRenderer
   	 * <p>
   	 * 
   	 * @return leaveBlock.getDescription() for LeaveBlockRenderer
   	 */
    public String getDescription();
     
    /**
   	 * The time range of the LeaveBlock object associated with the LeaveBlockRenderer
   	 * 
   	 * <p>
   	 * If the begin timestamp and end timestamp of the LeaveBlock are not null, and If the earn code type of the LeaveBlock is "T",
   	 * it constructs and returns a string that contains begin timestamp and end timestamp.
   	 * <p>
   	 * 
   	 * @return "leaveBlock.getBeginDateTime() in (hh:mm aa) - leaveBlock.getEndDateTime() in (hh:mm aa)" for LeaveBlockRenderer
   	 */
    public String getTimeRange();

}
