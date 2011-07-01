package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovalTimeSummaryRow {
	private String name;
	private List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
	private String approvalStatus;
	private String documentId;
	private Map<String,BigDecimal> hoursToPayLabelMap = new HashMap<String,BigDecimal>();
	private String clockStatusMessage;
    private String payCalendarGroup;
    private List notes = new ArrayList();
    private List<String> warnings = new ArrayList<String>();
    private String[] workAreas;
    private String principalId;

	public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}
    public List<TimeBlock> getLstTimeBlocks() {
		return lstTimeBlocks;
	}
	public void setLstTimeBlocks(List<TimeBlock> lstTimeBlocks) {
		this.lstTimeBlocks = lstTimeBlocks;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setHoursToPayLabelMap(Map<String,BigDecimal> hoursToPayLabelMap) {
		this.hoursToPayLabelMap = hoursToPayLabelMap;
	}
	public Map<String,BigDecimal> getHoursToPayLabelMap() {
		return hoursToPayLabelMap;
	}
	public void setClockStatusMessage(String clockStatusMessage) {
		this.clockStatusMessage = clockStatusMessage;
	}
	public String getClockStatusMessage() {
		return clockStatusMessage;
	}

    public String getPayCalendarGroup() {
        return payCalendarGroup;
    }

    public void setPayCalendarGroup(String payCalendarGroup) {
        this.payCalendarGroup = payCalendarGroup;
    }

    /**
     * Is this record initiated?
     * @return true if initiated, false otherwise.
     */
    public boolean isRoutable() {
        return StringUtils.equals(getApprovalStatus(), TkConstants.ROUTE_STATUS.INITIATED);
    }

    /**
     * Is this record ready to be approved?
     * @return true if a valid TK_APPROVER / TK_PROCESSOR can approve, false otherwise.
     */
    public boolean isApprovable() {
        return StringUtils.equals(getApprovalStatus(), TkConstants.ROUTE_STATUS.ENROUTE);
    }

    /**
     * Helper method to grab the URL parameters for setting target mode for a
     * user/documentID timesheet. Returns a portion simlar to:
     *
     * documentId=16352&useTargetUser=true
     *
     * You can use this to append to your URLs, similar to:
     *
     * http://localhost:8080/tk-dev/TimeDetail.do
     *
     * resulting in:
     *
     * http://localhost:8080/tk-dev/TimeDetail.do?documentId=16352&useTargetUser=true
     *
     * @return parameter portion of a URL, usable to initiate target mode.
     */
    public String getTimesheetUserTargetURLParams() {
        StringBuffer link = new StringBuffer();

        link.append("documentId=");
        link.append(this.getDocumentId());
        link.append("&useTargetUser=true");

        return link.toString();
    }

    public List getNotes() {
        return notes;
    }

    public void setNotes(List notes) {
        this.notes = notes;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public String[] getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(String[] workAreas) {
        this.workAreas = workAreas;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
}
