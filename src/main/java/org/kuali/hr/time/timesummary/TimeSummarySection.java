package org.kuali.hr.time.timesummary;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TimeSummarySection {
	private String earnGroup;
	
	private List<TimeSummaryRow> assignRows = new ArrayList<TimeSummaryRow>();
	private TimeSummaryRow summaryRow = new TimeSummaryRow();
	
	public String getEarnGroup() {
		return earnGroup;
	}

	public void setEarnGroup(String earnGroup) {
		this.earnGroup = earnGroup;
	}

	public void setAssignRows(List<TimeSummaryRow> assignRows) {
		this.assignRows = assignRows;
	}

	public List<TimeSummaryRow> getAssignRows() {
		return assignRows;
	}

	public void setSummaryRow(TimeSummaryRow summaryRow) {
		this.summaryRow = summaryRow;
	}

	public TimeSummaryRow getSummaryRow() {
		return summaryRow;
	}
	
	public TimeSummaryRow getRowForAssignment(String assignment){
		for(TimeSummaryRow tr : assignRows){
			if(StringUtils.equals(tr.getDescr(),assignment)){
				return tr;
			}
		}
		return null;
	}

}
