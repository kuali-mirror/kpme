package org.kuali.hr.time.timeblock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class TimeBlock extends PersistableBusinessObjectBase {

	/**
     *
     */
	private static final long serialVersionUID = -4164042707879641855L;

	private Long tkTimeBlockId;
	private String documentId;
	private Long jobNumber;
	private Long workArea;
	private Long task;
	private Long hrJobId;
	private Long tkWorkAreaId;
	private Long tkTaskId;
	private String earnCode;
	private Timestamp beginTimestamp;
	private Timestamp endTimestamp;
	private Boolean clockLogCreated;
	private BigDecimal hours = new BigDecimal("0.00");
	private BigDecimal amount = new BigDecimal("0.00");
	private String userPrincipalId;
	private Timestamp timestamp;
	private String beginTimestampTimezone;
	private String endTimestampTimezone;

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		return null;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	public String toCSVString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.beginTimestampTimezone+",");
		sb.append(this.earnCode+",");
		sb.append(this.endTimestampTimezone+",");
		sb.append(this.userPrincipalId+",");
		sb.append(this.amount+",");
		sb.append(this.beginTimestamp+",");
		sb.append(this.clockLogCreated+",");
		sb.append(this.endTimestamp+",");
		sb.append(this.hours+",");
		sb.append(this.jobNumber+",");
		sb.append(this.task+",");
		sb.append(this.tkTimeBlockId+",");
		sb.append(this.timestamp+",");
		sb.append(this.workArea+ System.getProperty("line.separator") );
		return sb.toString();
	}

	public Long getTkTimeBlockId() {
		return tkTimeBlockId;
	}

	public void setTkTimeBlockId(Long tkTimeBlockId) {
		this.tkTimeBlockId = tkTimeBlockId;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public Long getTask() {
		return task;
	}

	public void setTask(Long task) {
		this.task = task;
	}

	public Long getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(Long hrJobId) {
		this.hrJobId = hrJobId;
	}

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public Long getTkTaskId() {
		return tkTaskId;
	}

	public void setTkTaskId(Long tkTaskId) {
		this.tkTaskId = tkTaskId;
	}
}
