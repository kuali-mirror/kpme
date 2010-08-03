package org.kuali.hr.time.timeblock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeBlock extends PersistableBusinessObjectBase {

	/**
     *
     */
	private static final long serialVersionUID = -4164042707879641855L;

	private Long timeBlockId = null;
	private String documentId;
	private Long jobNumber;
	private Long workAreaId;
	private Long taskId;
	private String earnCode;
	private Timestamp beginTimestamp;
	private Timestamp endTimestamp;
	private Boolean clockLogCreated;
	private BigDecimal hours;
	private String userPrincipalId;
	private Timestamp timestamp;
	private String beginTimestampTimezone;
	private String endTimestampTimezone;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
	}

	public Long getTimeBlockId() {
		return timeBlockId;
	}

	public void setTimeBlockId(Long timeBlockId) {
		this.timeBlockId = timeBlockId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Long getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public Timestamp getBeginTimestamp() {
		return beginTimestamp;
	}

	public void setBeginTimestamp(Timestamp beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	public Timestamp getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Timestamp endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public Boolean getClockLogCreated() {
		return clockLogCreated;
	}

	public void setClockLogCreated(Boolean clockLogCreated) {
		this.clockLogCreated = clockLogCreated;
	}

	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getBeginTimestampTimezone() {
		return beginTimestampTimezone;
	}

	public void setBeginTimestampTimezone(String beginTimestampTimezone) {
		this.beginTimestampTimezone = beginTimestampTimezone;
	}

	public String getEndTimestampTimezone() {
		return endTimestampTimezone;
	}

	public void setEndTimestampTimezone(String endTimestampTimezone) {
		this.endTimestampTimezone = endTimestampTimezone;
	}
}
