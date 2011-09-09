package org.kuali.hr.time.timeblock;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class TimeBlock extends PersistableBusinessObjectBase implements Comparable{

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
	private String earnCodeType;
	private Timestamp beginTimestamp;
	private Timestamp endTimestamp;

    @Transient
    private java.sql.Date beginDate;
    @Transient
    private java.sql.Date endDate;
    @Transient
    private Time beginTime;
    @Transient
    private Time endTime;

	private Boolean clockLogCreated;
	private BigDecimal hours = TkConstants.BIG_DECIMAL_SCALED_ZERO;
	private BigDecimal amount = TkConstants.BIG_DECIMAL_SCALED_ZERO;
    private String principalId;
	private String userPrincipalId;
	private Timestamp timestamp;
	private String beginTimestampTimezone;
	private String endTimestampTimezone;
	private DateTime beginTimeDisplay;
	private DateTime endTimeDisplay;

    private Long clockLogBeginId;
    private Long clockLogEndId;

    private String assignmentKey;



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

	public java.sql.Date getBeginDate() {
		if(beginDate == null && this.getBeginTimestamp() != null) {
			 setBeginDate(new java.sql.Date(this.getBeginTimestamp().getTime()));
		}
		return beginDate;
	}

	public void setBeginDate(java.sql.Date beginDate) {
		this.beginDate = beginDate;
	}

	public java.sql.Date getEndDate() {
		if(endDate == null && this.getEndTimestamp() != null) {
			 setEndDate(new java.sql.Date(this.getEndTimestamp().getTime()));
		}
		return endDate;
	}

	public void setEndDate(java.sql.Date endDate) {
		this.endDate = endDate;
	}

	public Time getBeginTime() {
		if(beginTime == null && this.getBeginTimestamp() != null) {
			 setBeginTime(new java.sql.Time(this.getBeginTimestamp().getTime()));
		}
		return beginTime;
	}

	public void setBeginTime(Time beginTime) {
		this.beginTime = beginTime;
	}

	public Time getEndTime() {
		if(endTime == null && this.getEndTimestamp() != null) {
			 setEndTime(new java.sql.Time(this.getEndTimestamp().getTime()));
		}
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
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
		if(hours != null){
			this.hours = hours.setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING);
		} else {
			this.hours = hours;
		}
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		if(amount != null){
			this.amount = amount.setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING);
		} else {
			this.amount = amount;
		}
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
		StringBuilder key = new StringBuilder(getAssignString()+"_"+getEarnCode()+"_"+"_"+getBeginTimestamp()+"_"+getEndTimestamp()+"_"+getHours());
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

    /**
     * Use this call for all GUI/Display related rendering of the BEGIN
     * timestamp of the given time block. Timeblocks require pre-processing
     * before there will be a non-null return value here.
     *
     * @return The Timeblock Begin time to display, with the Users Timezone
     * taken into account and applied to this DateTime object.
     */
	public DateTime getBeginTimeDisplay() {
		return beginTimeDisplay;
	}

    /**
     * Helper to call DateTime.toDate().
     * @return a java.util.Date representing the getBeginTimeDisplay() DateTime.
     */
    public Date getBeginTimeDisplayDate() {
        return getBeginTimeDisplay().toDate();
    }

    /**
     * Set this value with a DateTime that is in the current users Timezone. This
     * should happen as a pre processing step for display purposes. Do not use these
     * values for server-side computation.
     *
     * @param beginTimeDisplay
     */
	public void setBeginTimeDisplay(DateTime beginTimeDisplay) {
		this.beginTimeDisplay = beginTimeDisplay;
	}

    /**
     * Use this call for all GUI/Display related rendering of the END
     * timestamp of the given time block. Timeblocks require pre-processing
     * before there will be a non-null return value here.
     *
     * @return The Timeblock end time to display, with the Users Timezone
     * taken into account and applied to this DateTime object.
     */
	public DateTime getEndTimeDisplay() {
		return endTimeDisplay;
	}

    /**
     * Helper to call DateTime.toDate().
     * @return a java.util.Date representing the getEndTimeDisplay() DateTime.
     */
    public Date getEndTimeDisplayDate() {
        return getEndTimeDisplay().toDate();
    }

    /**
     * Set this value with a DateTime that is in the current users Timezone. This
     * should happen as a pre processing step for display purposes. Do not use these
     * values for server-side computation.
     *
     * @param endTimeDisplay
     */
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

    public Long getClockLogBeginId() {
        return clockLogBeginId;
    }

    public void setClockLogBeginId(Long clockLogBeginId) {
        this.clockLogBeginId = clockLogBeginId;
    }

    public Long getClockLogEndId() {
        return clockLogEndId;
    }

    public void setClockLogEndId(Long clockLogEndId) {
        this.clockLogEndId = clockLogEndId;
    }

	public String getAssignmentKey() {
		if(assignmentKey == null) {
			AssignmentDescriptionKey adk = new AssignmentDescriptionKey(this.getJobNumber().toString(), this.getWorkArea().toString(), this.getTask().toString());
			this.setAssignmentKey(adk.toAssignmentKeyString());
		}
		return assignmentKey;
	}

	public void setAssignmentKey(String assignmentDescription) {
		this.assignmentKey = assignmentDescription;
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
        this.clockLogBeginId = b.clockLogBeginId;
        this.clockLogEndId = b.clockLogEndId;

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

	public String getEarnCodeType() {
		return earnCodeType;
	}

	public void setEarnCodeType(String earnCodeType) {
		this.earnCodeType = earnCodeType;
	}

	/**
     * This is for distribute time block page to sort it by begin date/time
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        return compareTo((TimeBlock) o);
    }

    public int compareTo(TimeBlock tb) {
        return this.getBeginTimestamp().compareTo(tb.getBeginTimestamp());
    }

    public Boolean getEditable() {
    	return TkServiceLocator.getTimeBlockService().isTimeBlockEditable(this);
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
    
    public Date getBeginDistributeDate() {
    	return this.getBeginTimestamp() == null ? new Date() :  new DateTime(this.getBeginTimestamp()).toDate();
    }
    
    public Date getEndDistributeDate() {
    	return this.getEndTimestamp() == null ? new Date() : new DateTime(this.getEndTimestamp()).toDate();
    }
}
