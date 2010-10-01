package org.kuali.hr.time.timeblock;

import java.sql.Timestamp;

public class TimeBlockHistory extends TimeBlock {

	/**
	 *
	 */
	private static final long serialVersionUID = 3943771766084238699L;

	private Long tkTimeBlockHistoryId = null;
	private String actionHistory;
	private String modifiedByPrincipalId;
	private Timestamp timestampModified;

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
		this.setTimestamp(tb.getTimestamp());
		this.setBeginTimestampTimezone(tb.getBeginTimestampTimezone());
		this.setEndTimestampTimezone(tb.getEndTimestampTimezone());
	}

	public Long getTkTimeBlockHistoryId() {
		return tkTimeBlockHistoryId;
	}
	public void setTkTimeBlockHistoryId(Long tkTimeBlockHistoryId) {
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
	public Timestamp getTimestampModified() {
		return timestampModified;
	}
	public void setTimestampModified(Timestamp timestampModified) {
		this.timestampModified = timestampModified;
	}
}
