package org.kuali.hr.time.mobile.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClockEntryInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7132787403000141501L;

	private Timestamp currentTime;
	
	private Map<String, String> assignKeyToAssignmentDescriptions = new HashMap<String,String>();
	private String lastClockLogDescription;
	private List<String> clockActions = new ArrayList<String>();
	
	public Map<String, String> getAssignKeyToAssignmentDescriptions() {
		return assignKeyToAssignmentDescriptions;
	}
	public void setAssignKeyToAssignmentDescriptions(
			Map<String, String> assignKeyToAssignmentDescriptions) {
		this.assignKeyToAssignmentDescriptions = assignKeyToAssignmentDescriptions;
	}
	public String getLastClockLogDescription() {
		return lastClockLogDescription;
	}
	public void setLastClockLogDescription(String lastClockLogDescription) {
		this.lastClockLogDescription = lastClockLogDescription;
	}
	public List<String> getClockActions() {
		return clockActions;
	}
	public void setClockActions(List<String> clockActions) {
		this.clockActions = clockActions;
	}
	public Timestamp getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(Timestamp currentTime) {
		this.currentTime = currentTime;
	}
}
