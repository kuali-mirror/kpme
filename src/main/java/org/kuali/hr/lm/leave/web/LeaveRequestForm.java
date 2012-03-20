package org.kuali.hr.lm.leave.web;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.time.base.web.TkForm;

public class LeaveRequestForm extends TkForm {

	private List<LeaveBlock> plannedLeaves;
	private List<LeaveBlock> pendingLeaves;
	private List<LeaveBlock> approvedLeaves;
	private List<LeaveBlockHistory> disapprovedLeaves;

	public List<LeaveBlock> getPlannedLeaves() {
		return plannedLeaves;
	}

	public void setPlannedLeaves(List<LeaveBlock> plannedLeaves) {
		this.plannedLeaves = plannedLeaves;
	}

	public List<LeaveBlock> getPendingLeaves() {
		return pendingLeaves;
	}

	public void setPendingLeaves(List<LeaveBlock> pendingLeaves) {
		this.pendingLeaves = pendingLeaves;
	}

	public List<LeaveBlock> getApprovedLeaves() {
		return approvedLeaves;
	}

	public void setApprovedLeaves(List<LeaveBlock> approvedLeaves) {
		this.approvedLeaves = approvedLeaves;
	}

	public List<LeaveBlockHistory> getDisapprovedLeaves() {
		return disapprovedLeaves;
	}

	public void setDisapprovedLeaves(List<LeaveBlockHistory> disapprovedLeaves) {
		this.disapprovedLeaves = disapprovedLeaves;
	}

	

}
