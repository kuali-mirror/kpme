package org.kuali.hr.time.approval.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;

public class ApprovalTimeSummaryRow {
	private String name;
	private List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
	private String approvalStatus;
	private String documentId;
	private Map<String,BigDecimal> hoursToPayLabelMap = new HashMap<String,BigDecimal>();
	private String clockStatusMessage;

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

}
