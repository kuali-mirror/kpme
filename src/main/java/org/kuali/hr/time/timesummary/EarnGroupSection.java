package org.kuali.hr.time.timesummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;

public class EarnGroupSection {
	private String earnGroup;
	private List<AssignmentRow> assignmentRows = new ArrayList<AssignmentRow>();
	private List<BigDecimal> totals = new ArrayList<BigDecimal>();
	public String getEarnGroup() {
		return earnGroup;
	}
	public void setEarnGroup(String earnGroup) {
		this.earnGroup = earnGroup;
	}
	public List<AssignmentRow> getAssignmentRows() {
		return assignmentRows;
	}
	public void setAssignmentRows(List<AssignmentRow> assignmentRows) {
		this.assignmentRows = assignmentRows;
	}
	public List<BigDecimal> getTotals() {
		return totals;
	}
	
	public void addAssignmentRow(AssignmentRow assignRow) {
		if(totals.size() == 0){
			totals.addAll(assignRow.getTotal());
		} else {
			for(int i = 0;i<assignRow.getTotal().size();i++){
				BigDecimal value = totals.get(i).add(assignRow.getTotal().get(i), TkConstants.MATH_CONTEXT);
				totals.set(i, value);
			}
		}
		assignmentRows.add(assignRow);
	}
}
