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
    
    private String documentId;
    private Integer jobNumber;
    private Long workAreaId;
    private Long taskId;
    private String earnCode;
    private Timestamp beginTimestamp;
    private Timestamp endTimestamp;
    private Boolean clockCreatedFlag;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Integer getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Integer jobNumber) {
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

    public Boolean getClockCreatedFlag() {
        return clockCreatedFlag;
    }

    public void setClockCreatedFlag(Boolean clockCreatedFlag) {
        this.clockCreatedFlag = clockCreatedFlag;
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
