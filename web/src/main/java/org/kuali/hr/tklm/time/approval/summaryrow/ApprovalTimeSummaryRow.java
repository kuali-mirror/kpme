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
package org.kuali.hr.tklm.time.approval.summaryrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.timesummary.TimeSummary;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class ApprovalTimeSummaryRow implements Comparable<ApprovalTimeSummaryRow>, Serializable {
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
    private Map<String,BigDecimal> hoursToFlsaPayLabelMap = new HashMap<String,BigDecimal>();
	private String clockStatusMessage;
    private String payCalendarGroup;
    private List<Note> notes = new ArrayList<Note>();
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
    public void setHoursToFlsaPayLabelMap(Map<String,BigDecimal> hoursToFlsaPayLabelMap) {
        this.hoursToFlsaPayLabelMap = hoursToFlsaPayLabelMap;
    }
    public Map<String,BigDecimal> getHoursToFlsaPayLabelMap() {
        return hoursToFlsaPayLabelMap;
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
        
        	TimesheetDocument doc = TkServiceLocator.getTimesheetService().getTimesheetDocument(this.documentId);
        	//is there a pending bt doc?
        	if (!TkServiceLocator.getTimesheetService().isReadyToApprove(doc)) {
        		return false;
        	}
        	
        	DocumentRouteHeaderValue routeHeader = TkServiceLocator.getTimeApproveService().getRouteHeader(this.getDocumentId());
        	boolean authorized = KEWServiceLocator.getDocumentSecurityService().routeLogAuthorized(TKContext.getPrincipalId(), routeHeader, new SecuritySession(TKContext.getPrincipalId()));
        	if(authorized){
        		List<String> principalsToApprove = KEWServiceLocator.getActionRequestService().getPrincipalIdsWithPendingActionRequestByActionRequestedAndDocId(KewApiConstants.ACTION_REQUEST_APPROVE_REQ, this.getDocumentId());
        		if(!principalsToApprove.isEmpty() && principalsToApprove.contains(TKContext.getPrincipalId())){
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

        link.append("methodToCall=changeTargetPerson");
        link.append("&documentId=").append(this.getDocumentId());
        Principal person = KimApiServiceLocator.getIdentityService().getPrincipal(this.getPrincipalId());
        link.append("&principalName=").append(person.getPrincipalName());

        return link.toString();
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
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
    
    public int compareTo(ApprovalTimeSummaryRow row) {
        return name.compareToIgnoreCase(row.getName());
    }
}
