/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.block.CalendarBlock;
import org.kuali.kpme.core.block.CalendarBlockBase;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.api.identity.Person;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeBlockBo extends CalendarBlock implements TimeBlockContract {

    private static final long serialVersionUID = -4164042707879641855L;
    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "TimeBlock";

    private String tkTimeBlockId;

    @Transient
    private Date beginDate;
    @Transient
    private Date endDate;
    @Transient
    private Time beginTime;
    @Transient
    private Time endTime;

    private String earnCodeType;

    private Boolean clockLogCreated;
    private BigDecimal hours = HrConstants.BIG_DECIMAL_SCALED_ZERO;
    private BigDecimal amount = HrConstants.BIG_DECIMAL_SCALED_ZERO;
    private DateTime beginTimeDisplay;
    private DateTime endTimeDisplay;
    private String clockLogBeginId;
    private String clockLogEndId;
    private String assignmentKey;
    private String overtimePref;
    //userPrincipalId == super.principalIdModified
    private String userPrincipalId;
    @Transient
    private Boolean deleteable;
    
    @Transient
    private Boolean overtimeEditable;
    
    @Transient
	private Boolean clockedByMissedPunch;
    @Transient
    private DateTime actionDateTime;
    @Transient
    private String clockAction;
    @Transient
    private String assignmentValue;
    
    private transient String missedPunchDocId;
    private transient String missedPunchDocStatus;

    // the two variables below are used to determine if a time block needs to be visually pushed forward / backward
    @Transient
    private Boolean pushBackward = false;

    private TimesheetDocumentHeader timesheetDocumentHeader;
    private transient Person user;
    private transient Person employeeObj;

    private transient List<TimeHourDetailBo> timeHourDetails = new ArrayList<TimeHourDetailBo>();
    private transient List<TimeBlockHistory> timeBlockHistories = new ArrayList<TimeBlockHistory>();
	protected BigDecimal leaveAmount = new BigDecimal("0.0");
	
	@Transient
	private Date leaveDate;
	
    public Date getLeaveDate() {
		return leaveDate;
	}

    public DateTime getLeaveDateTime() {
        return leaveDate == null ? null : new DateTime(leaveDate.getTime());
    }

	public void setLeaveDate(Date leaveLocalDate) {
		this.leaveDate = leaveLocalDate;
	}

	public TimeBlockBo() {
    	super();
    }
    
	public DateTime getActionDateTime() {
		return actionDateTime;
	}

	public void setActionDateTime(DateTime actionDateTime) {
		this.actionDateTime = actionDateTime;
	}

	public String getClockAction() {
		return clockAction;
	}

	public void setClockAction(String clockAction) {
		this.clockAction = clockAction;
	}

	public String getAssignmentValue() {
		return assignmentValue;
	}

	public void setAssignmentValue(String assignmentValue) {
		this.assignmentValue = assignmentValue;
	}

	public Boolean isClockedByMissedPunch() {
    	if(clockedByMissedPunch == null) {
    		this.assignClockedByMissedPunch();
    	}
		return clockedByMissedPunch;
	}
    
    public void assignClockedByMissedPunch() {
    	if(this.isClockLogCreated() != null && this.isClockLogCreated()){
  			MissedPunch missedPunchClockIn = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(this.getClockLogBeginId());

  			if(missedPunchClockIn!=null){
  				generateMissedPunchDetails(missedPunchClockIn);
  				return;
            } else {
                MissedPunch missedPunchClockOut = TkServiceLocator.getMissedPunchService().getMissedPunchByClockLogId(this.getClockLogEndId());
  			    if(missedPunchClockOut!=null){
  				    generateMissedPunchDetails(missedPunchClockOut);
  				    return;
                }
  			}  			
    	}
    	this.setClockedByMissedPunch(Boolean.FALSE);
    }

    private void generateMissedPunchDetails( MissedPunch missedPunch){
    		actionDateTime = missedPunch.getActionFullDateTime();
			clockAction = missedPunch.getClockAction();
			missedPunchDocId = missedPunch.getMissedPunchDocId();
			missedPunchDocStatus = missedPunch.getMissedPunchDocStatus();
			assignmentValue = missedPunch.getAssignmentValue();
			this.setClockedByMissedPunch(Boolean.TRUE);
    }
    
	public void setClockedByMissedPunch(Boolean clockedByMissedPunch) {
		this.clockedByMissedPunch = clockedByMissedPunch;
	}

	public Date getBeginDate() {
    	Date beginDate = null;
    	
    	if (beginTimestamp != null) {
    		beginDate = new Date(beginTimestamp.getTime());
    	}
        return beginDate;
    }

    public Timestamp getBeginTimestampVal() {
        return new Timestamp(beginTimestamp.getTime());
    }

    public Timestamp getEndTimestampVal() {
        return new Timestamp(endTimestamp.getTime());
    }

    public void setBeginDate(Date beginDate) {
    	DateTime dateTime = new DateTime(beginTimestamp);
    	LocalDate localDate = new LocalDate(beginDate);
    	LocalTime localTime = new LocalTime(beginTimestamp);
    	beginTimestamp = new Timestamp(localDate.toDateTime(localTime, dateTime.getZone()).getMillis());
    }
    
    public LocalTime getBeginTime() {
    	LocalTime beginTime = null;
    	
    	if (beginTimestamp != null) {
    		beginTime = new LocalTime(beginTimestamp.getTime());
    	}
    	
    	return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
    	DateTime dateTime = new DateTime(beginTimestamp);
    	LocalDate localDate = new LocalDate(beginTimestamp);
    	//LocalTime localTime = new LocalTime(beginTime);
    	beginTimestamp = new Timestamp(localDate.toDateTime(beginTime, dateTime.getZone()).getMillis());
    }
    
    public DateTime getBeginDateTime() {
    	return beginTimestamp != null ? new DateTime(beginTimestamp) : null;
    }
    
    public void setBeginDateTime(DateTime beginDateTime) {
    	beginTimestamp = beginDateTime != null ? new Timestamp(beginDateTime.getMillis()) : null;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Date getEndDate() {
    	Date endDate = null;
    	
    	if (endTimestamp != null) {
    		endDate = new Date(endTimestamp.getTime());
    	}

        return endDate;
    }

    public void setEndDate(Date endDate) {
    	DateTime dateTime = new DateTime(endTimestamp);
    	LocalDate localDate = new LocalDate(endDate);
    	LocalTime localTime = new LocalTime(endTimestamp);
    	endTimestamp = new Timestamp(localDate.toDateTime(localTime, dateTime.getZone()).getMillis());
    }

    public LocalTime getEndTime() {
    	LocalTime endTime = null;
    	
    	if (endTimestamp != null) {
    		endTime = new LocalTime(endTimestamp.getTime());
    	}
    	
    	return endTime;
    }

    public void setEndTime(LocalTime endTime) {
    	DateTime dateTime = new DateTime(endTimestamp);
    	LocalDate localDate = new LocalDate(endTimestamp);
        endTimestamp = new Timestamp(localDate.toDateTime(endTime, dateTime.getZone()).getMillis());
    }
    
    public DateTime getEndDateTime() {
    	return endTimestamp != null ? new DateTime(endTimestamp) : null;
    }
    
    public void setEndDateTime(DateTime endDateTime) {
    	endTimestamp = endDateTime != null ? new Timestamp(endDateTime.getMillis()) : null;
    }
    
    public Boolean isClockLogCreated() {
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

    public DateTime getCreateTime() {
        return timestamp == null ? null : new DateTime(timestamp.getTime());
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String toCSVString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.earnCode + ",");
        sb.append(this.getUserPrincipalId() + ",");
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
        super.concreteBlockId = tkTimeBlockId;
    }

    public List<TimeHourDetailBo> getTimeHourDetails() {
        return timeHourDetails;
    }
    
    public void addTimeHourDetail(TimeHourDetailBo timeHourDetail) {
    	timeHourDetails.add(timeHourDetail);
    }
    
    public void removeTimeHourDetail(TimeHourDetailBo timeHourDetail) {
    	timeHourDetails.remove(timeHourDetail);
    }

    public void setTimeHourDetails(List<TimeHourDetailBo> timeHourDetails) {
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
    	if(beginTimeDisplay == null && this.getBeginDateTime() != null) {
    		DateTimeZone timezone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
    		if(ObjectUtils.equals(timezone, TKUtils.getSystemDateTimeZone()))
				this.setBeginTimeDisplay(this.getBeginDateTime());
    		else
    			this.setBeginTimeDisplay(this.getBeginDateTime().withZone(timezone));
    	}
        return beginTimeDisplay;
    }

    /**
     * Helper to call DateTime.toDate().
     *
     * @return a date representing the getBeginTimeDisplay() DateTime.
     */
    public Date getBeginTimeDisplayDate() {
        return getBeginTimeDisplay() != null ? getBeginTimeDisplay().toDate() : null;
    }

    /*
    *   fix timezone issues caused by JScript, for GUI use only,
    */
    public String getBeginTimeDisplayDateOnlyString() {
    	DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        return getBeginDateTime() != null ? getBeginDateTime().withZone(zone).toString(HrConstants.DT_BASIC_DATE_FORMAT) : null;
    }

    public String getBeginTimeDisplayTimeOnlyString() {
    	DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
    	return getBeginDateTime() != null ? getBeginDateTime().withZone(zone).toString(TkConstants.DT_BASIC_TIME_FORMAT) : null;
    }

    public String getEndTimeDisplayDateOnlyString() {
    	DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        return getEndDateTime() != null ? getEndDateTime().withZone(zone).toString(HrConstants.DT_BASIC_DATE_FORMAT) : null;
    }

    public String getEndTimeDisplayTimeOnlyString() {
    	DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        return getEndDateTime() != null ? getEndDateTime().withZone(zone).toString(TkConstants.DT_BASIC_TIME_FORMAT) : null;
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
    	if(endTimeDisplay == null && this.getEndDateTime() != null) {
    		DateTimeZone timezone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
     		if(ObjectUtils.equals(timezone, TKUtils.getSystemDateTimeZone()))
 				this.setEndTimeDisplay(this.getBeginDateTime());
     		else
     			this.setEndTimeDisplay(this.getEndDateTime().withZone(timezone));
    	}
        return endTimeDisplay;
    }

    /**
     * Helper to call DateTime.toDate().
     *
     * @return a date representing the getEndTimeDisplay() DateTime.
     */
    //public Date getEndTimeDisplayDate() {
    //    return getEndTimeDisplay() != null ? getEndTimeDisplay().toDate() : null;
    //}

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
        AssignmentDescriptionKey adk = new AssignmentDescriptionKey(this.getJobNumber(), this.getWorkArea(), this.getTask());
        this.setAssignmentKey(adk.toAssignmentKeyString());
        return assignmentKey;
    }

    public void setAssignmentKey(String assignmentDescription) {
        this.assignmentKey = assignmentDescription;
    }

    public String getAssignmentDescription() {
        if (getJobNumber() != null && getWorkArea() != null && getTask() != null && getBeginDateTime() != null) {
            AssignmentDescriptionKey adk = new AssignmentDescriptionKey(this.getJobNumber(), this.getWorkArea(), this.getTask());
            AssignmentContract anAssignment = HrServiceLocator.getAssignmentService().getAssignment(principalId, adk, this.getBeginDateTime().toLocalDate());
            return anAssignment == null ? this.getAssignmentKey() : anAssignment.getAssignmentDescription();
        } else {
            return null;
        }
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
    protected TimeBlockBo(TimeBlockBo b) {
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
        this.setUserPrincipalId(b.getUserPrincipalId());
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
        for (TimeHourDetailBo thd : b.timeHourDetails) {
            this.timeHourDetails.add(thd.copy());
        }

        // TODO: For now, not copying TimeBlockHistory - The Object extends this one, which seems odd.
        //private List<TimeBlockHistory> timeBlockHistories = new ArrayList<TimeBlockHistory>();
    }

    /**
     * @return A new copy of this TimeBlock.
     */
    public TimeBlockBo copy() {
        return new TimeBlockBo(this);
    }
    
    public void copy(TimeBlockBo b) {
    	 this.tkTimeBlockId = b.tkTimeBlockId;
         this.documentId = b.documentId;
         this.jobNumber = b.jobNumber;
         this.workArea = b.workArea;
         this.task = b.task;
         this.earnCode = b.earnCode;
         this.earnCodeType = b.earnCodeType;
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
         for (TimeHourDetailBo thd : b.timeHourDetails) {
             this.timeHourDetails.add(thd.copy());
         }
         
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
    public int compareTo(TimeBlockContract o) {
        return compareTo((CalendarBlockBase) o);
    }

    public int compareTo(CalendarBlockBase tb) {
        return this.getBeginDateTime().compareTo(tb.getBeginDateTime());
    }

    public Boolean getEditable() {
        return TkServiceLocator.getTimeBlockService().getTimeBlockEditable(this);
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
        	 DateTimeZone dtz = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone());
        	 if (getOvernightTimeClockLog(clockLogBeginId)) {
                 return getBeginDateTime().withZone(dtz).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
             } else {
                 ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(this.getClockLogBeginId());
                 if (cl != null) {
                     return new DateTime(cl.getTimestamp()).withZone(dtz).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
                 }
             }
        }
        return "";
    }

    public String getActualEndTimeString() {
        if (this.getClockLogEndId() != null) {
        	DateTimeZone dtz = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone());
            if (getOvernightTimeClockLog(clockLogEndId)) {
                return getEndDateTime().withZone(dtz).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
            } else {
                ClockLog cl = TkServiceLocator.getClockLogService().getClockLog(this.getClockLogEndId());
                if (cl != null) {
                    return new DateTime(cl.getTimestamp()).withZone(dtz).toString(TkConstants.DT_FULL_DATE_TIME_FORMAT);
                }
            }

        }
        return "";
    }

    private Boolean getOvernightTimeClockLog(String clockLogId) {
        return TkServiceLocator.getTimeBlockService().isOvernightTimeBlock(clockLogEndId);
    }

	public Boolean getDeleteable() {
		return TkServiceLocator.getTKPermissionService().canDeleteTimeBlock(HrContext.getPrincipalId(), this);
	}

	public Boolean getOvertimeEditable() {
		return TkServiceLocator.getTKPermissionService().canEditOvertimeEarnCode(HrContext.getPrincipalId(), this);
	}
	
    public Boolean getTimeBlockEditable(){
        return TkServiceLocator.getTKPermissionService().canEditTimeBlock(HrContext.getPrincipalId(), this);
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

    public Person getEmployeeObj() {
        return employeeObj;
    }

    public void setEmployeeObj(Person employeeObj) {
        this.employeeObj = employeeObj;
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
		TimeBlockBo timeBlock = (TimeBlockBo) obj;
		return new EqualsBuilder()
			.append(jobNumber, timeBlock.jobNumber)
			.append(workArea, timeBlock.workArea)
			.append(task, timeBlock.task)
			.append(earnCode, timeBlock.earnCode)
			.append(beginTimestamp, timeBlock.beginTimestamp)
			.append(endTimestamp, timeBlock.endTimestamp)
			.append(hours, timeBlock.hours)
			.append(timeHourDetails, timeBlock.timeHourDetails)
			.append(timestamp, timeBlock.timestamp)
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

	public String getPrincipalIdModified() {
		return getUserPrincipalId();
	}

	public void setPrincipalIdModified(String principalIdModified) {
		setUserPrincipalId(principalIdModified);
	}

	public String getHrCalendarBlockId() {
		return super.hrCalendarBlockId;
	}
	
	@Override
	public void setHrCalendarBlockId(String hrCalendarBlockId) {
		this.hrCalendarBlockId = hrCalendarBlockId;
	}

	@Override
	public String getConcreteBlockId() {
		return tkTimeBlockId;
	}
	
	@Override
	public void setConcreteBlockId(String concreteBlockId) {
		this.concreteBlockId = concreteBlockId;
		tkTimeBlockId = concreteBlockId;
	}

	@Override
	public String getConcreteBlockType() {
		return super.concreteBlockType == null ? this.getClass().getName() : super.concreteBlockType;
	}

	@Override
	public void setConcreteBlockType(String ojbConcreteClass) {
		super.concreteBlockType = ojbConcreteClass;
	}

	public String getMissedPunchDocId() {
		return missedPunchDocId;
	}

	public void setMissedPunchDocId(String missedPunchDocId) {
		this.missedPunchDocId = missedPunchDocId;
	}

	public String getMissedPunchDocStatus() {
		return missedPunchDocStatus;
	}

	public void setMissedPunchDocStatus(String missedPunchDocStatus) {
		this.missedPunchDocStatus = missedPunchDocStatus;
	}

    public static TimeBlockBo from(TimeBlock im) {
        TimeBlockBo tb = new TimeBlockBo();

        tb.setTkTimeBlockId(im.getTkTimeBlockId());
        tb.setBeginDate(im.getBeginDateTime() == null ? null : im.getBeginDateTime().toDate());
        tb.setEndDate(im.getEndDateTime() == null ? null : im.getEndDateTime().toDate());
        tb.setBeginTime(im.getBeginTime() == null ? null : im.getBeginTime());
        tb.setEndTime(im.getEndTime() == null ? null : im.getEndTime());
        tb.setEarnCodeType(im.getEarnCodeType());

        tb.setClockLogCreated(im.isClockLogCreated());
        tb.setHours(im.getHours());
        tb.setAmount(im.getAmount());
        tb.setBeginTimeDisplay(im.getBeginTimeDisplay());
        tb.setEndTimeDisplay(im.getEndTimeDisplay());
        tb.setClockLogBeginId(im.getClockLogBeginId());
        tb.setClockLogEndId(im.getClockLogEndId());
        tb.setAssignmentKey(im.getAssignmentKey());
        tb.setOvertimePref(im.getOvertimePref());


        tb.setClockedByMissedPunch(im.isClockedByMissedPunch());

        tb.setActionDateTime(im.getActionDateTime() == null ? null : im.getActionDateTime());
        tb.setClockAction(im.getClockAction());
        tb.setAssignmentValue(im.getAssignmentValue());
        tb.setMissedPunchDocId(im.getMissedPunchDocId());
        tb.setMissedPunchDocStatus(im.getMissedPunchDocStatus());

        // the two variables below are used to determine if a time block needs to be visually pushed forward / backward
        tb.setPushBackward(im.isPushBackward());

        List<TimeHourDetailBo> tempTHDs = new ArrayList<TimeHourDetailBo>();
        if (CollectionUtils.isNotEmpty(im.getTimeHourDetails())) {
            for (TimeHourDetail thd : im.getTimeHourDetails()) {
                tempTHDs.add(TimeHourDetailBo.from(thd));
            }
        }
        tb.setTimeHourDetails(tempTHDs);

        //tb.setLeaveAmount(im.getL);

        tb.setLeaveDate(im.getLeaveDateTime() == null ? null : im.getLeaveDateTime().toDate());

        tb.setHrCalendarBlockId(im.getHrCalendarBlockId());
        tb.setPrincipalId(im.getPrincipalId());
        tb.setUserPrincipalId(im.getUserPrincipalId());
        tb.setDocumentId(im.getDocumentId());
        tb.setBeginTimestamp(im.getBeginDateTime() == null ? null : new Timestamp(im.getBeginDateTime().getMillis()));
        tb.setEndTimestamp(im.getEndDateTime() == null ? null : new Timestamp((im.getEndDateTime().getMillis())));
        tb.setTimestamp(im.getCreateTime() == null ? null : new Timestamp(im.getCreateTime().getMillis()));
        tb.setLunchDeleted(im.isLunchDeleted());
        tb.setHours(im.getHours());
        tb.setAmount(im.getAmount());
        tb.setOvertimePref(im.getOvertimePref());
        tb.setEarnCode(im.getEarnCode());
        tb.setWorkArea(im.getWorkArea());
        tb.setJobNumber(im.getJobNumber());
        tb.setTask(im.getTask());
        tb.setConcreteBlockType(im.getConcreteBlockType());
        tb.setConcreteBlockId(im.getConcreteBlockId());
        tb.setPrincipalIdModified(im.getUserPrincipalId());
        tb.setBeginDate(im.getBeginDateTime() == null ? null : im.getBeginDateTime().toDate());

        tb.setAssignmentKey(im.getAssignmentKey());


        //need lots more!!!!
        tb.setLeaveDate(im.getLeaveDateTime() == null ? null : im.getLeaveDateTime().toDate());

        tb.setObjectId(im.getObjectId());
        tb.setVersionNumber(im.getVersionNumber());

        return tb;
    }

    public static TimeBlock to(TimeBlockBo bo) {
        if (bo == null) {
            return null;
        }

        return TimeBlock.Builder.create(bo).build();
    }
}
