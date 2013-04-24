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
package org.kuali.hr.tklm.time.calendar;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.hr.tklm.leave.LMConstants;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.krad.util.ObjectUtils;

public class LeaveBlockRenderer {
    private LeaveBlock leaveBlock;
    private String assignmentClass;
    //private boolean readOnly;

    public  LeaveBlockRenderer(LeaveBlock leaveBlock) {
        this.leaveBlock = leaveBlock;
    }
 
    public LeaveBlock getLeaveBlock() {
        return leaveBlock;
    }

    public BigDecimal getHours() {
        return leaveBlock.getLeaveAmount();
    }

    public String getEarnCode() {
        return leaveBlock.getEarnCode();
    }

    public String getLeaveBlockId() {
        return leaveBlock.getLmLeaveBlockId();
    }

    public String getDocumentId() {
        return leaveBlock.getDocumentId();
    }

	public String getAssignmentTitle() {
		return leaveBlock.getAssignmentTitle();
	}

    public boolean getEditable() {
        return leaveBlock.isEditable();
    }

    public boolean getDeletable() {
        return leaveBlock.isDeletable();
    }

	public String getAssignmentClass() {
		return assignmentClass;
	}

	public void setAssignmentClass(String assignmentClass) {
		this.assignmentClass = assignmentClass;
	}

    public String getRequestStatusClass() {
        return this.leaveBlock.getRequestStatusString().toLowerCase();
    }

    public String getLeaveBlockDetails() {
        if (this.leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE)) {
//            return "accrual";
        	if(ObjectUtils.isNotNull(leaveBlock.getScheduleTimeOffId()))
        		return TkServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOff(this.leaveBlock.getScheduleTimeOffId()).getDescr();
        	else
        		return "accrual";
        }
        else if(this.leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER)) {
        	if(this.leaveBlock.getDescription().contains("Forfeited"))
        		return "transfer forfeiture";
        	else if(this.leaveBlock.getDescription().contains("Amount transferred"))
        		return "amount transferred";
        	else if(this.leaveBlock.getDescription().contains("Transferred amount"))
        		return "transferred amount";
        	else
        		return "balance transfer";
        }
        else if(this.leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT)) {
        	if(this.leaveBlock.getDescription().contains("Forfeited"))
        		return "payout forfeiture";
        	else if(this.leaveBlock.getDescription().contains("Amount paid out"))
        		return "amount paid out";
        	else if(this.leaveBlock.getDescription().contains("Payout amount"))
        		return "payout amount";
        	else
        		return "leave payout";
        }
        else
        	if(!this.leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR) &&
        			!this.leaveBlock.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR))
        		return LMConstants.LEAVE_BLOCK_TYPE_MAP.get(this.leaveBlock.getLeaveBlockType()).toLowerCase();
        	else
        		return getRequestStatusClass();
        
    }
    
    public String getDescription() {
    	return leaveBlock.getDescription();
    }

       
    public String getTimeRange() {
        StringBuilder b = new StringBuilder();

        if(leaveBlock.getBeginTimestamp() != null && leaveBlock.getEndTimestamp() != null) {
        	String earnCodeType = TkServiceLocator.getEarnCodeService().getEarnCodeType(leaveBlock.getEarnCode(), new DateTime(leaveBlock.getBeginTimestamp()).toLocalDate());
        	if(StringUtils.equals(earnCodeType, TkConstants.EARN_CODE_TIME)) {
	        	DateTime start = new DateTime(leaveBlock.getBeginTimestamp().getTime());
	        	DateTime end = new DateTime(leaveBlock.getEndTimestamp().getTime());
	            b.append(start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	            b.append(" - ");
	            b.append(end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
        	}
        }
        return b.toString();
    }
}
