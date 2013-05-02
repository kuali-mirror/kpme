/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.time.timeblock;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKContext;
import org.kuali.kpme.core.util.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class TimeBlock extends PersistableBusinessObjectBase implements Comparable {

    /**
     *
     */
    private static final long serialVersionUID = -4164042707879641855L;

    private String tkTimeBlockId;
    private String documentId;
    private Long jobNumber;
    private Long workArea;
    private Long task;
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
    private BigDecimal hours = HrConstants.BIG_DECIMAL_SCALED_ZERO;
    private BigDecimal amount = HrConstants.BIG_DECIMAL_SCALED_ZERO;
    private String principalId;
    private String userPrincipalId;
    private Timestamp timestamp;
    private DateTime beginTimeDisplay;
    private DateTime endTimeDisplay;
    private String clockLogBeginId;
    private String clockLogEndId;
    private String assignmentKey;
    private String overtimePref;
    private boolean lunchDeleted;
    
    @Transient
    private Boolean deleteable;
    
    @Transient
    private Boolean overtimeEditable;
    
    @Transient
    private Boolean regEarnCodeEditable;


    // the two variables below are used to determine if a time block needs to be visually pushed forward / backward
    @Transient
    private Boolean pushBackward = false;

    private TimesheetDocumentHeader timesheetDocumentHeader;
    private transient Person user;
    
    private transient List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
    private transient List<TimeBlockHistory> timeBlockHistories = new ArrayList<TimeBlockHistory>();

    public TimeBlock() {
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
        if (beginDate == null && this.getBeginTimestamp() != null) {
            setBeginDate(new java.sql.Date(this.getBeginTimestamp().getTime()));
        }
        return beginDate;
    }

    public void setBeginDate(java.sql.Date beginDate) {
        this.beginDate = beginDate;
    }

    public java.sql.Date getEndDate() {
        if (endDate == null && this.getEndTimestamp() != null) {
            setEndDate(new java.sql.Date(this.getEndTimestamp().getTime()));
        }
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public Time getBeginTime() {
        if (beginTime == null && this.getBeginTimestamp() != null) {
            setBeginTime(new java.sql.Time(this.getBeginTimestamp().getTime()));
        }
        return beginTime;
    }

    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public Time getEndTime() {
        if (endTime == null && this.getEndTimestamp() != null) {
            setEndTime(new java.sql.Time(this.getEndTimestamp().getTime()));
        }
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public DateTime getBeginDateTime() {
    	return new DateTime(getBeginDate());
    }
    
    public DateTime getEndDateTime() {
    	return new DateTime(getEndDate());
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
        if (hours != null) {
            this.hours = hours.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
        } else {
            this.hours = hours;
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
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

    public String toCSVString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.earnCode + ",");
        sb.append(this.userPrincipalId + ",");
        sb.append(this.amount + ",");
        sb.append(this.beginTimestamp + ",");
        sb.append(this.clockLogCreated + ",");
        sb.append(this.endTimestamp + ",");
        sb.append(this.hours + ",");
        sb.append(this.jobNumber + ",");
        sb.append(this.task + ",");
        sb.append(this.tkTimeBlockId + ",");
        sb.append(this.timestamp + ",");
        sb.append(this.workArea + System.getProperty("line.separator"));
        return sb.toString();
    }

    public String getTkTimeBlockId() {
        return tkTimeBlockId;
    }

    public void setTkTimeBlockId(String tkTimeBlockId) {
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

    public List<TimeHourDetail> getTimeHourDetails() {
        return timeHourDetails;
    }
    
    public void addTimeHourDetail(TimeHourDetail timeHourDetail) {
    	timeHourDetails.add(timeHourDetail);
    }
    
    public void removeTimeHourDetail(TimeHourDetail timeHourDetail) {
    	timeHourDetails.remove(timeHourDetail);
    }

    public void setTimeHourDetails(List<TimeHourDetail> timeHourDetails) {
        this.timeHourDetails = timeHourDetails;
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
     *         taken into account and applied to this DateTime object.
     */
    public DateTime getBeginTimeDisplay() {
        return beginTimeDisplay;
    }

    /**
     * Helper to call DateTime.toDate().
     *
     * @return a date representing the getBeginTimeDisplay() DateTime.
     */
    public Date getBeginTimeDisplayDate() {
        return getBeginTimeDisplay().toDate();
    }

    /*
    *   fix timezone issues caused by JScript, for GUI use only,
    */
    public String getBeginTimeDisplayDateOnlyString() {
        return this.getBeginTimeDisplay().toString(HrConstants.DT_BASIC_DATE_FORMAT);
    }

    public String getBeginTimeDisplayTimeOnlyString() {
        return this.getBeginTimeDisplay().toString(TkConstants.DT_BASIC_TIME_FORMAT);
    }

    public String getEndTimeDisplayDateOnlyString() {
        return this.getEndTimeDisplay().toString(HrConstants.DT_BASIC_DATE_FORMAT);
    }

    public String getEndTimeDisplayTimeOnlyString() {
        return this.getEndTimeDisplay().toString(TkConstants.DT_BASIC_TIME_FORMAT);
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
     *         taken into account and applied to this DateTime object.
     */
    public DateTime getEndTimeDisplay() {
        return endTimeDisplay;
    }

    /**
     * Helper to call DateTime.toDate().
     *
     * @return a date representing the getEndTimeDisplay() DateTime.
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
        if (timesheetDocumentHeader == null && this.getDocumentId() != null) {
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

    public String getClockLogBeginId() {
        return clockLogBeginId;
    }

    public void setClockLogBeginId(String clockLogBeginId) {
        this.clockLogBeginId = clockLogBeginId;
    }

    public String getClockLogEndId() {
        return clockLogEndId;
    }

    public void setClockLogEndId(String clockLogEndId) {
        this.clockLogEndId = clockLogEndId;
    }

    public String getAssignmentKey() {
        if (assignmentKey == null) {
            AssignmentDescriptionKey adk = new AssignmentDescriptionKey(this.getJobNumber().toString(), this.getWorkArea().toString(), this.getTask().toString());
            this.setAssignmentKey(adk.toAssignmentKeyString());
        }
        return assignmentKey;
    }

    public void setAssignmentKey(String assignmentDescription) {
        this.assignmentKey = assignmentDescription;
    }

    public String getAssignmentDescription() {
        AssignmentDescriptionKey adk = new AssignmentDescriptionKey(this.getJobNumber().toString(), this.getWorkArea().toString(), this.getTask().toString());
        Assignment anAssignment = HrServiceLocator.getAssignmentService().getAssignment(adk, this.getBeginDateTime().toLocalDate());
        return anAssignment == null ? this.getAssignmentKey() : anAssignment.getAssignmentDescription();
    }


    /**
     * Word on the street is that Object.clone() is a POS. We only need some
     * basics for comparison, so we'll implement a simple copy constructor
     * instead.
     * <p/>
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
        this.principalId = b.principalId;

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
     *
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

    public String getOvertimePref() {
        return overtimePref;
    }

    public void setOvertimePref(String overtimePref) {
        this.overtimePref = overtimePref;
    }

    /* apply grace period rule to times of time block
     * These strings are for GUI of Actual time inquiry
    */
    public String getActualBeginTimeString() {
        if (this.getClockLogBeginId() != null) {
            if (isOvernightTimeClockLog(clockLogEndId)) {
                return new DateTime(beginTimestamp).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
            } else {
                ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(this.getClockLogBeginId());
                if (cl != null) {
                    return new DateTime(cl.getTimestamp()).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
                }
            }

        }
        return "";
    }

    public String getActualEndTimeString() {
        if (this.getClockLogEndId() != null) {
            if (isOvernightTimeClockLog(clockLogEndId)) {
                return new DateTime(endTimestamp).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
            } else {
                ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(this.getClockLogEndId());
                if (cl != null) {
                    return new DateTime(cl.getTimestamp()).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
                }
            }

        }
        return "";
    }

    private Boolean isOvernightTimeClockLog(String clockLogId) {
        // https://jira.kuali.org/browse/KPME-1179
        Integer overnightTimeBlocks = TkServiceLocator.getTimeBlockService().getOvernightTimeBlocks(clockLogEndId).size();
        if (overnightTimeBlocks >= 2) {
            return true;
        }

        return false;
    }

	public Boolean getDeleteable() {
		return TkServiceLocator.getTKPermissionService().canDeleteTimeBlock(TKContext.getPrincipalId(), this);
	}

	public Boolean getOvertimeEditable() {
		return TkServiceLocator.getTKPermissionService().canEditOvertimeEarnCode(this);
	}
	
	public Boolean getRegEarnCodeEditable() {
		return TkServiceLocator.getTKPermissionService().canEditRegEarnCode(this);
	}

    public Boolean getTimeBlockEditable(){
        return TkServiceLocator.getTKPermissionService().canEditTimeBlock(TKContext.getPrincipalId(), this);
    }

    public boolean isLunchDeleted() {
        return lunchDeleted;
    }

    public void setLunchDeleted(boolean lunchDeleted) {
        this.lunchDeleted = lunchDeleted;
    }

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { 
			return false;
		}
		if (obj == this) { 
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		TimeBlock timeBlock = (TimeBlock) obj;
		return new EqualsBuilder()
			.append(jobNumber, timeBlock.jobNumber)
			.append(workArea, timeBlock.workArea)
			.append(task, timeBlock.task)
			.append(earnCode, timeBlock.earnCode)
			.append(beginTimestamp, timeBlock.beginTimestamp)
			.append(endTimestamp, timeBlock.endTimestamp)
			.append(hours, timeBlock.hours)
			.append(timeHourDetails, timeBlock.timeHourDetails)
			.isEquals();
	}

    @Override
    public int hashCode() {
    	return new HashCodeBuilder(17, 31)
    		.append(jobNumber)
    		.append(workArea)
    		.append(task)
    		.append(earnCode)
    		.append(beginTimestamp)
    		.append(endTimestamp)
    		.append(hours)
    		.append(timeHourDetails)
    		.toHashCode();
    }
    
}