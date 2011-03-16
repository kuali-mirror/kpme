package org.kuali.hr.time.timeblock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
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
	private BigDecimal hours = TkConstants.BIG_DECIMAL_SCALED_ZERO;
	private BigDecimal amount = TkConstants.BIG_DECIMAL_SCALED_ZERO;
	private String userPrincipalId;
	private Timestamp timestamp;
	private String beginTimestampTimezone;
	private String endTimestampTimezone;
	private DateTime beginTimeDisplay;
	private DateTime endTimeDisplay;
	// the two variables below are used to determine if a time block needs to be visually pushed forward / backward
	@Transient
	private Boolean pushBackward = false;

	private TimesheetDocumentHeader timesheetDocumentHeader;

	private List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
	private List<TimeBlockHistory> timeBlockHistories = new ArrayList<TimeBlockHistory>();

    public TimeBlock() {
    }

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("tkTimeBlockId", tkTimeBlockId);
		toStringMap.put("earnCode", earnCode);
		toStringMap.put("hours", hours);
        toStringMap.put("beginTimestamp", beginTimestamp);
        toStringMap.put("endTimestamp", endTimestamp);
		for (TimeHourDetail thd : timeHourDetails) {
			toStringMap.put("thd:earnCode:"+thd.getEarnCode(), thd.getHours());
			toStringMap.put("thd:earnCode:"+thd.getEarnCode(), thd.getHours());
		}
		return toStringMap;
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

	public List<TimeHourDetail> getTimeHourDetails() {
		return timeHourDetails;
	}

	public void setTimeHourDetails(List<TimeHourDetail> timeHourDetails) {
		this.timeHourDetails = timeHourDetails;
	}

	public String getAssignString(){
		return this.jobNumber + "_" + this.workArea + "_" + this.task;
	}

	@Override
	public boolean equals(Object obj) {
		if(hashCode() == obj.hashCode()){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		StringBuilder key = new StringBuilder(getAssignString()+"_"+getEarnCode()+"_"+"_"+getBeginTimestamp()+"_"+getEndTimestamp());
		for(TimeHourDetail timeHourDetail : getTimeHourDetails()){
			key.append(timeHourDetail.getEarnCode()+"_"+timeHourDetail.getAmount()+"_"+timeHourDetail.getHours());
		}
		return HashCodeBuilder.reflectionHashCode(key);
	}

	public Boolean isPushBackward() {
		return pushBackward;
	}

	public void setPushBackward(Boolean pushBackward) {
		this.pushBackward = pushBackward;
	}

	public DateTime getBeginTimeDisplay() {
		return beginTimeDisplay;
	}

	public void setBeginTimeDisplay(DateTime beginTimeDisplay) {
		this.beginTimeDisplay = beginTimeDisplay;
	}

	public DateTime getEndTimeDisplay() {
		return endTimeDisplay;
	}

	public void setEndTimeDisplay(DateTime endTimeDisplay) {
		this.endTimeDisplay = endTimeDisplay;
	}

	public TimesheetDocumentHeader getTimesheetDocumentHeader() {
		if(timesheetDocumentHeader == null && this.getDocumentId() != null) {
			setTimesheetDocumentHeader(TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(this.getDocumentId()));
		}
		return timesheetDocumentHeader;
	}

	public void setTimesheetDocumentHeader(
			TimesheetDocumentHeader timesheetDocumentHeader) {
		this.timesheetDocumentHeader = timesheetDocumentHeader;
	}

	public List<TimeBlockHistory> getTimeBlockHistories() {
		return timeBlockHistories;
	}

	public void setTimeBlockHistories(List<TimeBlockHistory> timeBlockHistories) {
		this.timeBlockHistories = timeBlockHistories;
	}

    /**
     * Word on the street is that Object.clone() is a POS. We only need some
     * basics for comparison, so we'll implement a simple copy constructor
     * instead.
     *
     * TODO: Check whether or not it matters if the History is copied, this
     * operation needs to be as inexpensive as possible.
     *
     * @param b The TimeBlock to copy values from when creating this instance.
     */
    protected TimeBlock(TimeBlock b) {
        // TODO : Implement "copy" constructor.
        this.tkTimeBlockId = b.tkTimeBlockId;
        this.documentId = b.documentId;
        this.jobNumber = b.jobNumber;
        this.workArea = b.workArea;
        this.task = b.task;
        this.hrJobId = b.hrJobId;
        this.tkWorkAreaId = b.tkWorkAreaId;
        this.tkTaskId = b.tkTaskId;
        this.earnCode = b.earnCode;
        this.beginTimestamp = new Timestamp(b.beginTimestamp.getTime());
        this.endTimestamp = new Timestamp(b.endTimestamp.getTime());
        this.clockLogCreated = b.clockLogCreated;
        this.hours = b.hours;
        this.amount = b.amount;
        this.userPrincipalId = b.userPrincipalId;
        this.timestamp = new Timestamp(b.timestamp.getTime());
        this.beginTimeDisplay = b.beginTimeDisplay;
        this.endTimeDisplay = b.endTimeDisplay;
        this.pushBackward = b.pushBackward;

        // We just set the reference for this object, since splitting the
        // TimeBlock would be abnormal behavior.
        this.timesheetDocumentHeader = b.timesheetDocumentHeader;

        //private List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
        for (TimeHourDetail thd : b.timeHourDetails) {
            this.timeHourDetails.add(thd.copy());
        }

        // TODO: For now, not copying TimeBlockHistory - The Object extends this one, which seems odd.
        //private List<TimeBlockHistory> timeBlockHistories = new ArrayList<TimeBlockHistory>();
    }

    /**
     * @return A new copy of this TimeBlock.
     */
    public TimeBlock copy() {
        return new TimeBlock(this);
    }

}
