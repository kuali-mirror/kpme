package org.kuali.hr.time.missedpunch;

import java.sql.Time;
import java.sql.Timestamp;

import org.kuali.rice.kns.document.TransactionalDocumentBase;

public class MissedPunchDocument extends TransactionalDocumentBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String principalId;
	private String clockAction;
	private java.util.Date actionDate;
	private Time actionTime;
    private String timesheetDocumentId;
	private String documentStatus;
	private String tkClockLogId;
	private Timestamp timestamp;
	private String ipAddress;
	
    /** Selection key matching what is used on the clock action GUI */
    private String assignment;
	
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getClockAction() {
		return clockAction;
	}
	public void setClockAction(String clockAction) {
		this.clockAction = clockAction;
	}
	public java.util.Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(java.util.Date actionDate) {
		this.actionDate = actionDate;
	}
	public Time getActionTime() {
		return actionTime;
	}
	public void setActionTime(Time actionTime) {
		this.actionTime = actionTime;
	}
	public String getTimesheetDocumentId() {
		return timesheetDocumentId;
	}
	public void setTimesheetDocumentId(String timesheetDocumentId) {
		this.timesheetDocumentId = timesheetDocumentId;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getTkClockLogId() {
		return tkClockLogId;
	}
	public void setTkClockLogId(String tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getAssignment() {
		return assignment;
	}
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

}