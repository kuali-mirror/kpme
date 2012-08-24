package org.kuali.hr.lm.leaveSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

public class LeaveSummary {
	private List<LeaveSummaryRow> leaveSummaryRows = new ArrayList<LeaveSummaryRow>();
	private String ytdDatesString;
	private String pendingDatesString;
	private List<String> approvalHeaders = new ArrayList<String>();

	public List<LeaveSummaryRow> getLeaveSummaryRows() {
		return leaveSummaryRows;
	}

	public void setLeaveSummaryRows(List<LeaveSummaryRow> leaveSummaryRows) {
		this.leaveSummaryRows = leaveSummaryRows;
	}

	public String getYtdDatesString() {
		return ytdDatesString;
	}

	public void setYtdDatesString(String ytdDatesString) {
		this.ytdDatesString = ytdDatesString;
	}

	public String getPendingDatesString() {
		return pendingDatesString;
	}

	public void setPendingDatesString(String pendingDatesString) {
		this.pendingDatesString = pendingDatesString;
	}

	 public String toJsonString() {
        List<Map<String, Object>> acSections = new ArrayList<Map<String, Object>>();
        for(LeaveSummaryRow lsr : this.leaveSummaryRows) {
        	 Map<String, Object> acs = new HashMap<String, Object>();
        	 
        	 acs.put("accrualCategory", lsr.getAccrualCategory());
        	 acs.put("periodUsage", lsr.getPendingLeaveRequests());
        	 acs.put("available", lsr.getLeaveBalance());
        	 acSections.add(acs);
        }
        return JSONValue.toJSONString(acSections);
	}

	public List<String> getApprovalHeaders() {
		return approvalHeaders;
	}

	public void setApprovalHeaders(List<String> approvalHeaders) {
		this.approvalHeaders = approvalHeaders;
	}
	
	
	
}
