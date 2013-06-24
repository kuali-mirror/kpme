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
package org.kuali.kpme.tklm.leave.calendar.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.tklm.common.CalendarForm;
import org.kuali.kpme.tklm.leave.accrual.bucket.KPMEAccrualCategoryBucket;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendar;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;

@SuppressWarnings("serial")
public class LeaveCalendarForm extends CalendarForm {

    private LeaveCalendar leaveCalendar;
    private LeaveCalendarDocument leaveCalendarDocument;
    
    private String startDate;
    private String endDate;
    private String selectedEarnCode;
    private BigDecimal leaveAmount;
    private String description;
    private String leaveBlockId;

	/** String (concat(job number, work_area, task)) -> Formatted Assignment Descriptions */
	private Map<String,String>  assignmentDescriptions;
	private String selectedAssignment;
	private String spanningWeeks; // KPME-1446
	private String leaveBlockString;  // KPME-1447
	private boolean isDocEditable;
	private LeaveSummary leaveSummary;
    private boolean leavePlanningCalendar;
    private List<BalanceTransfer> forfeitures;
	private String startTime;
	private String endTime;
	private KPMEAccrualCategoryBucket bucket;

	public boolean isDocEditable() {
		return isDocEditable;
	}

	public void setDocEditable(boolean isDocEditable) {
		this.isDocEditable = isDocEditable;
	}

    public boolean isLeavePlanningCalendar() {
        return leavePlanningCalendar;
    }

    public void setLeavePlanningCalendar(boolean leavePlanningCalendar) {
        this.leavePlanningCalendar = leavePlanningCalendar;
    }

    public String getLeaveBlockString() {
		return leaveBlockString;
	}

	public void setLeaveBlockString(String leaveBlockString) {
		this.leaveBlockString = leaveBlockString;
	}

	public String getSpanningWeeks() {
		return spanningWeeks;
	}

	public void setSpanningWeeks(String spanningWeeks) {
		this.spanningWeeks = spanningWeeks;
	}
		
    public String getSelectedAssignment() {
		return selectedAssignment;
	}

	public void setSelectedAssignment(String selectedAssignment) {
		this.selectedAssignment = selectedAssignment;
	}

	public Map<String, String> getAssignmentDescriptions() {
		return assignmentDescriptions;
	}

	public void setAssignmentDescriptions(Map<String, String> assignmentDescriptions) {
		this.assignmentDescriptions = assignmentDescriptions;
	}

    public LeaveCalendar getLeaveCalendar() {
        return leaveCalendar;
    }

    public void setLeaveCalendar(LeaveCalendar leaveCalendar) {
        this.leaveCalendar = leaveCalendar;
    }

    public LeaveCalendarDocument getLeaveCalendarDocument() {
        return leaveCalendarDocument;
    }

    public void setLeaveCalendarDocument(LeaveCalendarDocument leaveCalendarDocument) {
        this.leaveCalendarDocument = leaveCalendarDocument;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

	public BigDecimal getLeaveAmount() {
		return leaveAmount;
	}

	public void setLeaveAmount(BigDecimal leaveAmount) {
		this.leaveAmount = leaveAmount;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeaveBlockId() {
        return leaveBlockId;
    }

    public void setLeaveBlockId(String leaveBlockId) {
        this.leaveBlockId = leaveBlockId;
    }

	public String getSelectedEarnCode() {
		return selectedEarnCode;
	}

	public void setSelectedEarnCode(String selectedEarnCode) {
		this.selectedEarnCode = selectedEarnCode;
	}

	public LeaveSummary getLeaveSummary() {
		return leaveSummary;
	}

	public void setLeaveSummary(LeaveSummary leaveSummary) {
		this.leaveSummary = leaveSummary;
	}

	public List<BalanceTransfer> getForfeitures() {
		return forfeitures;
	}

	public void setForfeitures(List<BalanceTransfer> forfeitures) {
		this.forfeitures = forfeitures;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setBucket(KPMEAccrualCategoryBucket bucket) {
		this.bucket = bucket;
	}
	
	public KPMEAccrualCategoryBucket getKPMEAccrualCategoryBucket() {
		return bucket;
	}
	
}
