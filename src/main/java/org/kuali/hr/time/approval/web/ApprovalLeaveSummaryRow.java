package org.kuali.hr.time.approval.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.doctype.SecuritySession;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.api.identity.Person;

public class ApprovalLeaveSummaryRow implements Comparable<ApprovalLeaveSummaryRow> {
	private String name;
	private String principalId;
	private String documentId;
	private List<String> warnings = new ArrayList<String>();
	private String selected = "off";
	private String approvalStatus;
	
	private Boolean moreThanOneCalendar = Boolean.FALSE;
	private String lastApproveMessage;
	private List<LeaveBlock> leaveBlockList = new ArrayList<LeaveBlock>();
	// Key - date, value - Map with key=AccrualCategory, value = amount
	Map<String, Map<String, BigDecimal>> leaveHoursToPayLabelMap = new HashMap<String, Map<String, BigDecimal>>();
	
    /**
     * Is this record ready to be approved?
     * @return true if a valid TK_APPROVER / TK_PROCESSOR can approve, false otherwise.
     */
    public boolean isApprovable() {
    	boolean isEnroute =  StringUtils.equals(getApprovalStatus(), "ENROUTE") ;

        if(isEnroute){
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

        link.append("methodToCall=changeEmployee");
        link.append("&documentId=").append(this.getDocumentId());
        Person person = KimApiServiceLocator.getPersonService().getPerson(this.getPrincipalId());
        link.append("&changeTargetPrincipalName=").append(person.getPrincipalName());

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


	public Map<String, Map<String, BigDecimal>> getLeaveHoursToPayLabelMap() {
		return leaveHoursToPayLabelMap;
	}


	public void setLeaveHoursToPayLabelMap(
			Map<String, Map<String, BigDecimal>> leaveHoursToPayLabelMap) {
		this.leaveHoursToPayLabelMap = leaveHoursToPayLabelMap;
	}


	public Boolean getMoreThanOneCalendar() {
		return moreThanOneCalendar;
	}


	public void setMoreThanOneCalendar(Boolean moreThanOneCalendar) {
		this.moreThanOneCalendar = moreThanOneCalendar;
	}
}
