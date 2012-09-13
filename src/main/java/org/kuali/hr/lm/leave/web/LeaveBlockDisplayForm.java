package org.kuali.hr.lm.leave.web;

import java.util.List;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.time.base.web.TkForm;

public class LeaveBlockDisplayForm extends TkForm {

	private int year;
	private String navString;
	private List<LeaveBlock> leaveEntries;
	private List<LeaveBlockHistory> correctedLeaveEntries;
	private List<LeaveBlockHistory> inActiveLeaveEntries;
	private List<String> accrualCategoires;
	private String targetName;
	
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public List<LeaveBlockHistory> getInActiveLeaveEntries() {
		return inActiveLeaveEntries;
	}

	public void setInActiveLeaveEntries(List<LeaveBlockHistory> inActiveLeaveEntries) {
		this.inActiveLeaveEntries = inActiveLeaveEntries;
	}

	public List<LeaveBlockHistory> getCorrectedLeaveEntries() {
		return correctedLeaveEntries;
	}

	public void setCorrectedLeaveEntries(
			List<LeaveBlockHistory> correctedLeaveEntries) {
		this.correctedLeaveEntries = correctedLeaveEntries;
	}

	public List<LeaveBlock> getLeaveEntries() {
		return leaveEntries;
	}

	public void setLeaveEntries(List<LeaveBlock> leaveEntries) {
		this.leaveEntries = leaveEntries;
	}

	public String getNavString() {
		return navString;
	}

	public void setNavString(String navString) {
		this.navString = navString;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<String> getAccrualCategoires() {
		return accrualCategoires;
	}

	public void setAccrualCategoires(List<String> accrualCategoires) {
		this.accrualCategoires = accrualCategoires;
	}
	

}
