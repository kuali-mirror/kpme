package org.kuali.hr.time.timeblock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.bo.Person;

public class TimeBlockHistory extends TimeBlock {

	/**
	 *
	 */
	private static final long serialVersionUID = 3943771766084238699L;

	private String tkTimeBlockHistoryId = null;
	private String actionHistory;
	private String modifiedByPrincipalId;
	private Timestamp timestampModified;
	private Person principal;
	private Person userPrincipal;
	private List<TimeBlockHistoryDetail> timeBlockHistoryDetails = new ArrayList<TimeBlockHistoryDetail>();

	public TimeBlockHistory() {
	}

	public TimeBlockHistory(TimeBlock tb) {
		this.setTkTimeBlockId(tb.getTkTimeBlockId());
		this.setDocumentId(tb.getDocumentId());
		this.setJobNumber(tb.getJobNumber());
		this.setWorkArea(tb.getWorkArea());
		this.setTask(tb.getTask());
		this.setHrJobId(tb.getHrJobId());
		this.setTkWorkAreaId(tb.getTkWorkAreaId());
		this.setTkTaskId(tb.getTkTaskId());
		this.setEarnCode(tb.getEarnCode());
		this.setBeginTimestamp(tb.getBeginTimestamp());
		this.setEndTimestamp(tb.getEndTimestamp());
		this.setClockLogCreated(tb.getClockLogCreated());
		this.setHours(tb.getHours());
		this.setUserPrincipalId(tb.getUserPrincipalId());
		this.setPrincipalId(tb.getPrincipalId());
		this.setTimestamp(tb.getTimestamp());
		this.setBeginTimestampTimezone(tb.getBeginTimestampTimezone());
		this.setEndTimestampTimezone(tb.getEndTimestampTimezone());
		// add time block history details for this time block history
		TkServiceLocator.getTimeBlockHistoryService().addTimeBlockHistoryDetails(this, tb);
	}
	

	public String getTkTimeBlockHistoryId() {
		return tkTimeBlockHistoryId;
	}
	public void setTkTimeBlockHistoryId(String tkTimeBlockHistoryId) {
		this.tkTimeBlockHistoryId = tkTimeBlockHistoryId;
	}
	public String getActionHistory() {
		return actionHistory;
	}
	public void setActionHistory(String actionHistory) {
		this.actionHistory = actionHistory;
	}
	public String getModifiedByPrincipalId() {
		return modifiedByPrincipalId;
	}
	public void setModifiedByPrincipalId(String modifiedByPrincipalId) {
		this.modifiedByPrincipalId = modifiedByPrincipalId;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public Person getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(Person userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	public List<TimeBlockHistoryDetail> getTimeBlockHistoryDetails() {
		return timeBlockHistoryDetails;
	}

	public void setTimeBlockHistoryDetails(List<TimeBlockHistoryDetail> timeBlockHistoryDetails) {
		this.timeBlockHistoryDetails = timeBlockHistoryDetails;
	}
}
