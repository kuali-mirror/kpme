package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.util.KEWConstants;

import java.math.BigDecimal;
import java.util.*;

public class ApprovalTimeSummaryRow {
	private String name;
	private List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
    /** A Map (Assignment key) of Mapped totals (pay label mapping) */
    private Map<String, Map<String, BigDecimal>> approverHoursByAssignment;
    /** A Map (Assignment key) of Mapped totals (pay label mapping) */
    private Map<String, Map<String, BigDecimal>> otherHoursByAssignment;
    /** A String (AssignmentDescriptionKey) to Description mapping for all assignments on this summary row */
    private Map<String,String> assignmentDescriptions; // could refactor out to action level call

	private String approvalStatus;
	private String approvalStatusMessage;
	private String documentId;
	private Map<String,BigDecimal> hoursToPayLabelMap = new HashMap<String,BigDecimal>();
	private String clockStatusMessage;
    private String payCalendarGroup;
    private List notes = new ArrayList();
    private List<String> warnings = new ArrayList<String>();
    private Set<String> workAreas;
    private String principalId;
    private Boolean clockedInOverThreshold = Boolean.FALSE;    
    private String selected = "off";
    private TimeSummary timeSummary;
    private BigDecimal periodTotal = BigDecimal.ZERO;
    
    public String getApprovalStatusMessage() {
		return approvalStatusMessage;
	}

	public void setApprovalStatusMessage(String approvalStatusMessage) {
		this.approvalStatusMessage = approvalStatusMessage;
	}

	public Map<String, String> getAssignmentDescriptions() {
        return assignmentDescriptions;
    }

    public void setAssignmentDescriptions(Map<String, String> assignmentDescriptions) {
        this.assignmentDescriptions = assignmentDescriptions;
    }

    public Map<String, Map<String, BigDecimal>> getApproverHoursByAssignment() {
        return approverHoursByAssignment;
    }

    public void setApproverHoursByAssignment(Map<String, Map<String, BigDecimal>> approverHoursByAssignment) {
        this.approverHoursByAssignment = approverHoursByAssignment;
    }

    public Map<String, Map<String, BigDecimal>> getOtherHoursByAssignment() {
        return otherHoursByAssignment;
    }

    public void setOtherHoursByAssignment(Map<String, Map<String, BigDecimal>> otherHoursByAssignment) {
        this.otherHoursByAssignment = otherHoursByAssignment;
    }

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
    	boolean isEnroute =  StringUtils.equals(getApprovalStatus(), "ENROUTE") ;
        if(isEnroute){
        	DocumentRouteHeaderValue routeHeader = KEWServiceLocator.getRouteHeaderService().getRouteHeader(Long.parseLong(this.getDocumentId()));
        	boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(TKContext.getUserSession(), routeHeader, new SecuritySession(TKContext.getUserSession()));
        	if(authorized){
        		List<String> principalsToApprove = KEWServiceLocator.getActionRequestService().getPrincipalIdsWithPendingActionRequestByActionRequestedAndDocId(KEWConstants.ACTION_REQUEST_APPROVE_REQ, routeHeader.getRouteHeaderId());
        		if(principalsToApprove != null && principalsToApprove.contains(TKContext.getPrincipalId())){
            		return true;
            	}
        	}
        }
        return false;
    }

    /**
     * Helper method to grab the URL parameters for setting target mode for a
     * user/documentID timesheet. Returns a portion simlar to:
     *
     * @return parameter portion of a URL, usable to initiate target mode.
     */
    public String getTimesheetUserTargetURLParams() {
        StringBuffer link = new StringBuffer();

        link.append("methodToCall=changeEmployee");
        link.append("&documentId=").append(this.getDocumentId());
        link.append("&changeTargetPrincipalId=").append(this.getPrincipalId());

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

    public Set<String> getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(Set<String> workAreas) {
        this.workAreas = workAreas;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
	public Boolean getClockedInOverThreshold() {
		return clockedInOverThreshold;
	}
	public void setClockedInOverThreshold(Boolean clockedInOverThreshold) {
		this.clockedInOverThreshold = clockedInOverThreshold;
	}
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

    public TimeSummary getTimeSummary() {
        return timeSummary;
    }

    public void setTimeSummary(TimeSummary timeSummary) {
        this.timeSummary = timeSummary;
    }

    public BigDecimal getPeriodTotal() {
        return periodTotal;
    }

    public void setPeriodTotal(BigDecimal periodTotal) {
        this.periodTotal = periodTotal;
    }
}
