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
package org.kuali.kpme.tklm.leave.approval.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.tklm.common.TKContext;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.note.Note;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class ApprovalLeaveSummaryRow implements Comparable<ApprovalLeaveSummaryRow>, Serializable {
	private String name;
	private String principalId;
	private String documentId;
	private List<String> warnings = new ArrayList<String>();
	private String selected = "off";
	private String approvalStatus;

    private List<Note> notes = new ArrayList<Note>();
	private Boolean moreThanOneCalendar = Boolean.FALSE;
	private String lastApproveMessage;
	private List<LeaveBlock> leaveBlockList = new ArrayList<LeaveBlock>();
	private Map<Date, Map<String, BigDecimal>> earnCodeLeaveHours = new LinkedHashMap<Date, Map<String, BigDecimal>>();
	private Boolean exemptEmployee;
	
    /**
     * Is this record ready to be approved?
     * @return true if a valid TK_APPROVER / TK_PROCESSOR can approve, false otherwise.
     */
    public boolean isApprovable() {
    	boolean isEnroute =  StringUtils.equals(getApprovalStatus(), "ENROUTE") ;

        if(isEnroute){
            LeaveCalendarDocument doc = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(this.documentId);
            //is there a pending bt doc?
            if (!LmServiceLocator.getLeaveCalendarService().isReadyToApprove(doc)) {
                return false;
            }
        	DocumentRouteHeaderValue routeHeader = TkServiceLocator.getTimeApproveService().getRouteHeader(this.getDocumentId());
//            // check if there are any pending calendars are there
//        	LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getMinBeginDatePendingLeaveCalendar(this.principalId);
//            if (lcdh != null){             //if there were any pending document
//                //check to see if it's before the current document. if it is, then this document is not approvable.
//                if (LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(this.documentId).getBeginDate().compareTo(lcdh.getEndDate()) >= 0){
//                    return false;
//                }
//            }
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
	
	 
	public int compareTo(ApprovalLeaveSummaryRow row) {
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

	public Map<Date, Map<String, BigDecimal>> getEarnCodeLeaveHours() {
		return earnCodeLeaveHours;
	}

	public void setEarnCodeLeaveHours(Map<Date, Map<String, BigDecimal>> earnCodeLeaveHours) {
		this.earnCodeLeaveHours = earnCodeLeaveHours;
	}

	public Boolean getMoreThanOneCalendar() {
		return moreThanOneCalendar;
	}


	public void setMoreThanOneCalendar(Boolean moreThanOneCalendar) {
		this.moreThanOneCalendar = moreThanOneCalendar;
	}


	public Boolean getExemptEmployee() {
		if(this.exemptEmployee == null) {
			this.exemptEmployee = LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(this.principalId,TkConstants.FLSA_STATUS_EXEMPT, true);
		}
		return exemptEmployee;
	}


	public void setExemptEmployee(Boolean exemptEmployee) {
		this.exemptEmployee = exemptEmployee;
	}
	
	
}