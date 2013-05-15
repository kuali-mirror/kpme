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
package org.kuali.kpme.tklm.leave.summary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONValue;

public class LeaveSummary implements Serializable {
	private List<LeaveSummaryRow> leaveSummaryRows = new ArrayList<LeaveSummaryRow>();
	private String ytdDatesString;
	private String pendingDatesString;
    private String note;
	private List<String> approvalHeaders = new ArrayList<String>();

	public LeaveSummary(LeaveSummary leaveSummary) {
		leaveSummaryRows = leaveSummary.leaveSummaryRows;
		ytdDatesString = leaveSummary.ytdDatesString;
		pendingDatesString = leaveSummary.pendingDatesString;
		note = leaveSummary.note;
		approvalHeaders = leaveSummary.approvalHeaders;
	}

	public LeaveSummary() {
		// TODO Auto-generated constructor stub
	}

	public List<LeaveSummaryRow> getLeaveSummaryRows() {
		return leaveSummaryRows;
	}

    public LeaveSummaryRow getLeaveSummaryRowForAccrualCtgy(String accrualCategory) {
        for (LeaveSummaryRow row : getLeaveSummaryRows()) {
            if (StringUtils.equals(row.getAccrualCategory(), accrualCategory)) {
                return row;
            }
        }
        return null;
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

	public LeaveSummaryRow getLeaveSummaryRowForAccrualCategory(
			String accrualCategoryId) {
		for(LeaveSummaryRow lsr : leaveSummaryRows) {
			if(StringUtils.equals(lsr.getAccrualCategoryId(),accrualCategoryId)) {
				return lsr;
			}
		}
		return null;
	}

    public String getNote() {
        return note == null ? "" : note;
    }

    public void setNote(String note) {
        this.note = note;
    }
	
	
	
}
