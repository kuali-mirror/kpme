package org.kuali.hr.lm.leaveSummary;

import java.util.ArrayList;
import java.util.List;

public class LeaveSummary {
	private List<LeaveSummaryRow> leaveSummaryRows = new ArrayList<LeaveSummaryRow>();
	private String ytdDatesString;
	private String pendingDatesString;

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

	
	
	
}
