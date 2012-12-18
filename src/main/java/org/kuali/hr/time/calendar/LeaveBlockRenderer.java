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
package org.kuali.hr.time.calendar;

import java.math.BigDecimal;

import org.kuali.hr.lm.leaveblock.LeaveBlock;

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

}
