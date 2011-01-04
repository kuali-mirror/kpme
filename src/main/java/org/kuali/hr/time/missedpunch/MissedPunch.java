package org.kuali.hr.time.missedpunch;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class MissedPunch extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = -7392156233886202883L;
	
	private Long tkMissedPunchId;
	private String principalId;
	private String clockAction;
	private java.util.Date actionDate;
	private Time actionTime;
	private String documentId;
	private String documentStatus;
	private Long tkClockLogId;
	private Timestamp timestamp;
	
	private Person person;

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTkMissedPunchId() {
		return tkMissedPunchId;
	}

	public void setTkMissedPunchId(Long tkMissedPunchId) {
		this.tkMissedPunchId = tkMissedPunchId;
	}

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

	public Time getActionTime() {
		return actionTime;
	}

	public void setActionTime(Time actionTime) {
		this.actionTime = actionTime;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getTkClockLogId() {
		return tkClockLogId;
	}

	public void setTkClockLogId(Long tkClockLogId) {
		this.tkClockLogId = tkClockLogId;
	}

	public java.util.Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(java.util.Date actionDate) {
		this.actionDate = actionDate;
	}

}
