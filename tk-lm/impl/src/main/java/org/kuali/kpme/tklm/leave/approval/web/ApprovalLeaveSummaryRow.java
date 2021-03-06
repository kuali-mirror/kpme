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
package org.kuali.kpme.tklm.leave.approval.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.api.leave.approval.ApprovalLeaveSummaryRowContract;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.summary.LeaveSummaryContract;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class ApprovalLeaveSummaryRow implements Serializable, ApprovalLeaveSummaryRowContract {

	private static final long serialVersionUID = -1573234630744940098L;
	private String name;
	private String principalId;
	private String documentId;
    private Map<String, String> roleNames;
	private List<String> warnings = new ArrayList<String>();
	private String selected = "off";
	private String approvalStatus;

    private List<Note> notes = new ArrayList<Note>();
	private Boolean moreThanOneCalendar = Boolean.FALSE;
	private Map<String, Boolean> enableWeekDetails = new LinkedHashMap<String, Boolean>();	
	private String lastApproveMessage;
	private List<LeaveBlock> leaveBlockList = new ArrayList<LeaveBlock>();
	private Map<Integer, String> weeklyDistribution = new LinkedHashMap<Integer, String>();
	private Map<String, String> weekDates = new LinkedHashMap<String, String>();
	private Map<LocalDateTime, Map<String, BigDecimal>> earnCodeLeaveHours = new LinkedHashMap<LocalDateTime, Map<String, BigDecimal>>();
	private Map<String, Set<LocalDateTime>> weekDateList = new LinkedHashMap<String, Set<LocalDateTime>>();
	private Boolean exemptEmployee;
	private String color;
	private Map<String,List<Map<String, Object>>> detailMap = new LinkedHashMap<String, List<Map<String,Object>>>();
	private LeaveSummaryContract leaveSummary;
	
    public LeaveSummaryContract getLeaveSummary() {
		return leaveSummary;
	}

	public void setLeaveSummary(LeaveSummaryContract leaveSummary) {
		this.leaveSummary = leaveSummary;
	}


	/**
     * Is this record ready to be approved?
     * @return true if a valid TK_APPROVER / TK_PROCESSOR can approve, false otherwise.
     */
    public boolean isApprovable() {
    	boolean isApprovable = false;

    	if (DocumentStatus.ENROUTE.getLabel().equals(getApprovalStatus())) {
    		LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(getDocumentId());
    		if (leaveCalendarDocument != null) {
    			String leaveCalendarPrincipalId = leaveCalendarDocument.getPrincipalId();
    			String approverPrincipalId = HrContext.getPrincipalId();

    			if (!StringUtils.equals(leaveCalendarPrincipalId, approverPrincipalId) && LmServiceLocator.getLeaveCalendarService().isReadyToApprove(leaveCalendarDocument)) {
    				boolean authorized = KewApiServiceLocator.getWorkflowDocumentActionsService().isUserInRouteLog(getDocumentId(), approverPrincipalId, false);
    				if (authorized) {
                        List<String> approverPrincipalIds = KewApiServiceLocator.getWorkflowDocumentService().getPrincipalIdsWithPendingActionRequestByActionRequestedAndDocId(KewApiConstants.ACTION_REQUEST_APPROVE_REQ, getDocumentId());
    					if (approverPrincipalIds.contains(approverPrincipalId)) {
    						isApprovable = true;
    					}
    				}
    			}
    		}
    	}
	 	 	 	
        return isApprovable;
    }
	
	 
	public int compareTo(ApprovalLeaveSummaryRowContract row) {
        return name.compareToIgnoreCase(row.getName());
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	
    public String getUserTargetURLParams() {
        StringBuffer link = new StringBuffer();

        link.append("methodToCall=changeTargetPerson");
        link.append("&documentId=").append(this.getDocumentId());
        Principal person = KimApiServiceLocator.getIdentityService().getPrincipal(this.getPrincipalId());
        link.append("&principalName=").append(person.getPrincipalName());
        
        return link.toString();
    }

	public List<LeaveBlock> getLeaveBlockList() {
		return leaveBlockList;
	}

	public void setLeaveBlockList(List<LeaveBlock> leaveBlockList) {
		this.leaveBlockList = leaveBlockList;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

	public String getLastApproveMessage() {
		return lastApproveMessage;
	}

	public void setLastApproveMessage(String lastApproveMessage) {
		this.lastApproveMessage = lastApproveMessage;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Map<LocalDateTime, Map<String, BigDecimal>> getEarnCodeLeaveHours() {
		return earnCodeLeaveHours;
	}

	public void setEarnCodeLeaveHours(Map<LocalDateTime, Map<String, BigDecimal>> earnCodeLeaveHours) {
		this.earnCodeLeaveHours = earnCodeLeaveHours;
	}

	
	public Map<Integer, String> getWeeklyDistribution() {
		return weeklyDistribution;
	}


	public void setWeeklyDistribution(Map<Integer, String> weeklyDistribution) {
		this.weeklyDistribution = weeklyDistribution;
	}
	
	public Map<String, String> getWeekDates() {
		return weekDates;
	}


	public void setWeekDates(Map<String, String> weekDates) {
		this.weekDates = weekDates;
	}


	public Boolean getMoreThanOneCalendar() {
		return moreThanOneCalendar;
	}


	public void setMoreThanOneCalendar(Boolean moreThanOneCalendar) {
		this.moreThanOneCalendar = moreThanOneCalendar;
	}


	public Boolean getExemptEmployee() {
		if(this.exemptEmployee == null) {
			this.exemptEmployee = LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(this.principalId,HrConstants.FLSA_STATUS_EXEMPT, true);
		}
		return exemptEmployee;
	}


	public void setExemptEmployee(Boolean exemptEmployee) {
		this.exemptEmployee = exemptEmployee;
	}


	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}


	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

    public Map<String, String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Map<String, String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleName() {
        return getRoleNames().get(HrContext.getPrincipalId());
    }


	public Map<String, Set<LocalDateTime>> getWeekDateList() {
		return weekDateList;
	}


	public void setWeekDateList(Map<String, Set<LocalDateTime>> weekDateList) {
		this.weekDateList = weekDateList;
	}


	public Map<String, List<Map<String, Object>>> getDetailMap() {
		return detailMap;
	}


	public void setDetailMap(Map<String, List<Map<String, Object>>> detailMap) {
		this.detailMap = detailMap;
	}


	public Map<String, Boolean> getEnableWeekDetails() {
		return enableWeekDetails;
	}


	public void setEnableWeekDetails(Map<String, Boolean> enableWeekDetails) {
		this.enableWeekDetails = enableWeekDetails;
	}	
    
}