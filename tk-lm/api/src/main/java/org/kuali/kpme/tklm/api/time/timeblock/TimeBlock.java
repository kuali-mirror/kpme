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
package org.kuali.kpme.tklm.api.time.timeblock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = TimeBlock.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = TimeBlock.Constants.TYPE_NAME, propOrder = {
        TimeBlock.Elements.HOURS,
        TimeBlock.Elements.END_DATE_TIME,
        TimeBlock.Elements.CLOCK_LOG_CREATED,
        TimeBlock.Elements.BEGIN_DATE_TIME,
        TimeBlock.Elements.END_TIME,
        TimeBlock.Elements.AMOUNT,
        TimeBlock.Elements.USER_PRINCIPAL_ID,
        TimeBlock.Elements.TK_TIME_BLOCK_ID,
        TimeBlock.Elements.TIME_HOUR_DETAILS,
        TimeBlock.Elements.PUSH_BACKWARD,
        TimeBlock.Elements.BEGIN_TIME_DISPLAY,
        TimeBlock.Elements.BEGIN_TIME_DISPLAY_DATE_ONLY_STRING,
        TimeBlock.Elements.BEGIN_TIME_DISPLAY_TIME_ONLY_STRING,
        TimeBlock.Elements.END_TIME_DISPLAY_DATE_ONLY_STRING,
        TimeBlock.Elements.END_TIME_DISPLAY_TIME_ONLY_STRING,
        TimeBlock.Elements.END_TIME_DISPLAY,
        TimeBlock.Elements.CLOCK_LOG_BEGIN_ID,
        TimeBlock.Elements.CLOCK_LOG_END_ID,
        TimeBlock.Elements.ASSIGNMENT_KEY,
        TimeBlock.Elements.ASSIGNMENT_DESCRIPTION,
        TimeBlock.Elements.EARN_CODE_TYPE,
        TimeBlock.Elements.PRINCIPAL_ID,
        TimeBlock.Elements.OVERTIME_PREF,
        TimeBlock.Elements.ACTUAL_BEGIN_TIME_STRING,
        TimeBlock.Elements.ACTUAL_END_TIME_STRING,
        TimeBlock.Elements.LUNCH_DELETED,
        TimeBlock.Elements.HR_CALENDAR_BLOCK_ID,
        TimeBlock.Elements.BEGIN_TIME,
        TimeBlock.Elements.CREATE_TIME,
        TimeBlock.Elements.WORK_AREA,
        TimeBlock.Elements.JOB_NUMBER,
        TimeBlock.Elements.TASK,
        TimeBlock.Elements.EARN_CODE,
        TimeBlock.Elements.CONCRETE_BLOCK_TYPE,
        TimeBlock.Elements.CONCRETE_BLOCK_ID,
        TimeBlock.Elements.DOCUMENT_ID,
        TimeBlock.Elements.LEAVE_DATE_TIME,
        TimeBlock.Elements.ACTION_DATE_TIME,
        TimeBlock.Elements.CLOCK_ACTION,
        TimeBlock.Elements. MISSED_PUNCH_DOC_ID,
        TimeBlock.Elements.MISSED_PUNCH_DOC_STATUS,
        TimeBlock.Elements.ASSIGNMENT_VALUE,
        TimeBlock.Elements.CLOCKED_BY_MISSED_PUNCH,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class TimeBlock
        extends AbstractDataTransferObject
        implements TimeBlockContract
{

    @XmlElement(name = Elements.HOURS, required = false)
    private final BigDecimal hours;
    @XmlElement(name = Elements.END_DATE_TIME, required = false)
    private final DateTime endDateTime;
    @XmlElement(name = Elements.CLOCK_LOG_CREATED, required = false)
    private final Boolean clockLogCreated;
    @XmlElement(name = Elements.BEGIN_DATE_TIME, required = false)
    private final DateTime beginDateTime;
    @XmlElement(name = Elements.END_TIME, required = false)
    private final LocalTime endTime;
    @XmlElement(name = Elements.AMOUNT, required = false)
    private final BigDecimal amount;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.TK_TIME_BLOCK_ID, required = false)
    private final String tkTimeBlockId;
    @XmlElement(name = Elements.TIME_HOUR_DETAILS, required = false)
    private final List<TimeHourDetail> timeHourDetails;
    @XmlElement(name = Elements.PUSH_BACKWARD, required = false)
    private final Boolean pushBackward;
    @XmlElement(name = Elements.BEGIN_TIME_DISPLAY, required = false)
    private final DateTime beginTimeDisplay;
    @XmlElement(name = Elements.BEGIN_TIME_DISPLAY_DATE_ONLY_STRING, required = false)
    private final String beginTimeDisplayDateOnlyString;
    @XmlElement(name = Elements.BEGIN_TIME_DISPLAY_TIME_ONLY_STRING, required = false)
    private final String beginTimeDisplayTimeOnlyString;
    @XmlElement(name = Elements.END_TIME_DISPLAY_DATE_ONLY_STRING, required = false)
    private final String endTimeDisplayDateOnlyString;
    @XmlElement(name = Elements.END_TIME_DISPLAY_TIME_ONLY_STRING, required = false)
    private final String endTimeDisplayTimeOnlyString;
    @XmlElement(name = Elements.END_TIME_DISPLAY, required = false)
    private final DateTime endTimeDisplay;
    @XmlElement(name = Elements.CLOCK_LOG_BEGIN_ID, required = false)
    private final String clockLogBeginId;
    @XmlElement(name = Elements.CLOCK_LOG_END_ID, required = false)
    private final String clockLogEndId;
    @XmlElement(name = Elements.ASSIGNMENT_KEY, required = false)
    private final String assignmentKey;
    @XmlElement(name = Elements.ASSIGNMENT_DESCRIPTION, required = false)
    private final String assignmentDescription;
    @XmlElement(name = Elements.EARN_CODE_TYPE, required = false)
    private final String earnCodeType;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.OVERTIME_PREF, required = false)
    private final String overtimePref;
    @XmlElement(name = Elements.ACTUAL_BEGIN_TIME_STRING, required = false)
    private final String actualBeginTimeString;
    @XmlElement(name = Elements.ACTUAL_END_TIME_STRING, required = false)
    private final String actualEndTimeString;
    @XmlElement(name = Elements.LUNCH_DELETED, required = false)
    private final boolean lunchDeleted;
    @XmlElement(name = Elements.HR_CALENDAR_BLOCK_ID, required = false)
    private final String hrCalendarBlockId;
    @XmlElement(name = Elements.BEGIN_TIME, required = false)
    private final LocalTime beginTime;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = Elements.JOB_NUMBER, required = false)
    private final Long jobNumber;
    @XmlElement(name = Elements.TASK, required = false)
    private final Long task;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.CONCRETE_BLOCK_TYPE, required = false)
    private final String concreteBlockType;
    @XmlElement(name = Elements.CONCRETE_BLOCK_ID, required = false)
    private final String concreteBlockId;
    @XmlElement(name = Elements.DOCUMENT_ID, required = false)
    private final String documentId;
    @XmlElement(name = Elements.LEAVE_DATE_TIME, required = false)
    private final DateTime leaveDateTime;
    @XmlElement(name = Elements.ACTION_DATE_TIME, required = false)
    private final DateTime actionDateTime;
    @XmlElement(name = Elements.CLOCK_ACTION, required = false)
    private final String clockAction;
    @XmlElement(name = Elements.MISSED_PUNCH_DOC_ID, required = false)
    private final String missedPunchDocId;
    @XmlElement(name = Elements.MISSED_PUNCH_DOC_STATUS, required = false)
    private final String missedPunchDocStatus;
    @XmlElement(name = Elements.ASSIGNMENT_VALUE, required = false)
    private final String assignmentValue;
    @XmlElement(name = Elements.CLOCKED_BY_MISSED_PUNCH, required = false)
    private final Boolean clockedByMissedPunch;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private TimeBlock() {
        this.hours = null;
        this.endDateTime = null;
        this.clockLogCreated = null;
        this.beginDateTime = null;
        this.endTime = null;
        this.amount = null;
        this.userPrincipalId = null;
        this.tkTimeBlockId = null;
        this.timeHourDetails = null;
        this.pushBackward = null;
        this.beginTimeDisplay = null;
        this.beginTimeDisplayDateOnlyString = null;
        this.beginTimeDisplayTimeOnlyString = null;
        this.endTimeDisplayDateOnlyString = null;
        this.endTimeDisplayTimeOnlyString = null;
        this.endTimeDisplay = null;
        this.clockLogBeginId = null;
        this.clockLogEndId = null;
        this.assignmentKey = null;
        this.assignmentDescription = null;
        this.earnCodeType = null;
        this.principalId = null;
        this.overtimePref = null;
        this.actualBeginTimeString = null;
        this.actualEndTimeString = null;
        this.lunchDeleted = false;
        this.hrCalendarBlockId = null;
        this.beginTime = null;
        this.createTime = null;
        this.workArea = null;
        this.jobNumber = null;
        this.task = null;
        this.earnCode = null;
        this.concreteBlockType = null;
        this.concreteBlockId = null;
        this.documentId = null;
        this.objectId = null;
        this.versionNumber = null;
        this.leaveDateTime = null;
        this.actionDateTime = null;
        this.clockAction = null;
        this.missedPunchDocId = null;
        this.missedPunchDocStatus = null;
        this.assignmentValue = null;
        this.clockedByMissedPunch = null;
    }

    private TimeBlock(Builder builder) {
        this.hours = builder.getHours();
        this.endDateTime = builder.getEndDateTime();
        this.clockLogCreated = builder.isClockLogCreated();
        this.beginDateTime = builder.getBeginDateTime();
        this.endTime = builder.getEndTime();
        this.amount = builder.getAmount();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.tkTimeBlockId = builder.getTkTimeBlockId();
        List<TimeHourDetail> temp = new ArrayList<TimeHourDetail>();
        if (CollectionUtils.isNotEmpty(builder.getTimeHourDetails())) {
            for (TimeHourDetail.Builder b : builder.getTimeHourDetails()) {
                temp.add(b.build());
            }
        }
        this.timeHourDetails = Collections.unmodifiableList(temp);
        this.pushBackward = builder.isPushBackward();
        this.beginTimeDisplay = builder.getBeginTimeDisplay();
        this.beginTimeDisplayDateOnlyString = builder.getBeginTimeDisplayDateOnlyString();
        this.beginTimeDisplayTimeOnlyString = builder.getBeginTimeDisplayTimeOnlyString();
        this.endTimeDisplayDateOnlyString = builder.getEndTimeDisplayDateOnlyString();
        this.endTimeDisplayTimeOnlyString = builder.getEndTimeDisplayTimeOnlyString();
        this.endTimeDisplay = builder.getEndTimeDisplay();
        this.clockLogBeginId = builder.getClockLogBeginId();
        this.clockLogEndId = builder.getClockLogEndId();
        this.assignmentKey = builder.getAssignmentKey();
        this.assignmentDescription = builder.getAssignmentDescription();
        this.earnCodeType = builder.getEarnCodeType();
        this.principalId = builder.getPrincipalId();
        this.overtimePref = builder.getOvertimePref();
        this.actualBeginTimeString = builder.getActualBeginTimeString();
        this.actualEndTimeString = builder.getActualEndTimeString();
        this.lunchDeleted = builder.isLunchDeleted();
        this.hrCalendarBlockId = builder.getHrCalendarBlockId();
        this.beginTime = builder.getBeginTime();
        this.createTime = builder.getCreateTime();
        this.workArea = builder.getWorkArea();
        this.jobNumber = builder.getJobNumber();
        this.task = builder.getTask();
        this.earnCode = builder.getEarnCode();
        this.concreteBlockType = builder.getConcreteBlockType();
        this.concreteBlockId = builder.getConcreteBlockId();
        this.documentId = builder.getDocumentId();
        this.objectId = builder.getObjectId();
        this.versionNumber = builder.getVersionNumber();
        this.leaveDateTime = builder.getLeaveDateTime();
        this.actionDateTime = builder.getActionDateTime();
        this.clockAction = builder.getClockAction();
        this.missedPunchDocId = builder.getMissedPunchDocId();
        this.missedPunchDocStatus = builder.getMissedPunchDocStatus();
        this.assignmentValue = builder.getAssignmentValue();
        this.clockedByMissedPunch = builder.isClockedByMissedPunch();
    }

    @Override
    public BigDecimal getHours() {
        return this.hours;
    }

    @Override
    public DateTime getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public Boolean isClockLogCreated() {
        return this.clockLogCreated;
    }

    @Override
    public DateTime getBeginDateTime() {
        return this.beginDateTime;
    }

    @Override
    public LocalTime getEndTime() {
        return this.endTime;
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getTkTimeBlockId() {
        return this.tkTimeBlockId;
    }

    @Override
    public List<TimeHourDetail> getTimeHourDetails() {
        return Collections.unmodifiableList(this.timeHourDetails);
    }

    @Override
    public Boolean isPushBackward() {
        return this.pushBackward;
    }

    @Override
    public DateTime getBeginTimeDisplay() {
        return this.beginTimeDisplay;
    }

    @Override
    public String getBeginTimeDisplayDateOnlyString() {
        return this.beginTimeDisplayDateOnlyString;
    }

    @Override
    public String getBeginTimeDisplayTimeOnlyString() {
        return this.beginTimeDisplayTimeOnlyString;
    }

    @Override
    public String getEndTimeDisplayDateOnlyString() {
        return this.endTimeDisplayDateOnlyString;
    }

    @Override
    public String getEndTimeDisplayTimeOnlyString() {
        return this.endTimeDisplayTimeOnlyString;
    }

    @Override
    public DateTime getEndTimeDisplay() {
        return this.endTimeDisplay;
    }

    @Override
    public String getClockLogBeginId() {
        return this.clockLogBeginId;
    }

    @Override
    public String getClockLogEndId() {
        return this.clockLogEndId;
    }

    @Override
    public String getAssignmentKey() {
        return this.assignmentKey;
    }

    @Override
    public String getAssignmentDescription() {
        return this.assignmentDescription;
    }

    @Override
    public String getEarnCodeType() {
        return this.earnCodeType;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public String getOvertimePref() {
        return this.overtimePref;
    }

    @Override
    public String getActualBeginTimeString() {
        return this.actualBeginTimeString;
    }

    @Override
    public String getActualEndTimeString() {
        return this.actualEndTimeString;
    }

    @Override
    public boolean isLunchDeleted() {
        return this.lunchDeleted;
    }

    @Override
    public String getHrCalendarBlockId() {
        return this.hrCalendarBlockId;
    }

    @Override
    public LocalTime getBeginTime() {
        return this.beginTime;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public Long getWorkArea() {
        return this.workArea;
    }

    @Override
    public Long getJobNumber() {
        return this.jobNumber;
    }

    @Override
    public Long getTask() {
        return this.task;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public String getConcreteBlockType() {
        return this.concreteBlockType;
    }

    @Override
    public String getConcreteBlockId() {
        return this.concreteBlockId;
    }

    @Override
    public String getDocumentId() {
        return this.documentId;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public DateTime getLeaveDateTime() {
        return this.leaveDateTime;
    }

    @Override
    public Boolean isClockedByMissedPunch() {
        return clockedByMissedPunch;
    }

    @Override
    public String getAssignmentValue() {
        return assignmentValue;
    }

    @Override
    public String getMissedPunchDocStatus() {
        return missedPunchDocStatus;
    }

    @Override
    public String getMissedPunchDocId() {
        return missedPunchDocId;
    }

    @Override
    public String getClockAction() {
        return clockAction;
    }

    @Override
    public DateTime getActionDateTime() {
        return actionDateTime;
    }

    public static TimeBlock copy(TimeBlock timeBlock) {
        return TimeBlock.Builder.create(timeBlock).build();
    }

    public int compareTo(TimeBlockContract tb) {
        return this.getBeginDateTime().compareTo(tb.getBeginDateTime());
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
                .append(beginDateTime == null ? null : beginDateTime.getMillis(), timeBlock.beginDateTime == null ? null : timeBlock.beginDateTime.getMillis())
                .append(endDateTime == null ? null : endDateTime.getMillis(), timeBlock.endDateTime == null ? null : timeBlock.endDateTime.getMillis())
                .append(hours, timeBlock.hours)
                .append(timeHourDetails, timeBlock.timeHourDetails)
                .append(createTime == null ? null : createTime.getMillis(), timeBlock.createTime == null ? null : timeBlock.createTime.getMillis())
                .isEquals();
    }

    /**
     * A builder which can be used to construct {@link TimeBlock} instances.  Enforces the constraints of the {@link TimeBlockContract}.
     *
     */
    public final static class Builder
            implements Serializable, TimeBlockContract, ModelBuilder
    {

        private BigDecimal hours;
        private DateTime endDateTime;
        private Boolean clockLogCreated;
        private DateTime beginDateTime;
        private LocalTime endTime;
        private BigDecimal amount;
        private String userPrincipalId;
        private String tkTimeBlockId;
        private List<TimeHourDetail.Builder> timeHourDetails;
        private Boolean pushBackward;
        private DateTime beginTimeDisplay;
        private String beginTimeDisplayDateOnlyString;
        private String beginTimeDisplayTimeOnlyString;
        private String endTimeDisplayDateOnlyString;
        private String endTimeDisplayTimeOnlyString;
        private DateTime endTimeDisplay;
        private String clockLogBeginId;
        private String clockLogEndId;
        private String assignmentKey;
        private String assignmentDescription;
        private String earnCodeType;
        private String principalId;
        private String overtimePref;
        private String actualBeginTimeString;
        private String actualEndTimeString;
        private boolean lunchDeleted;
        private String hrCalendarBlockId;
        private LocalTime beginTime;
        private DateTime createTime;
        private Long workArea;
        private Long jobNumber;
        private Long task;
        private String earnCode;
        private String concreteBlockType;
        private String concreteBlockId;
        private String documentId;
        private String objectId;
        private Long versionNumber;
        private DateTime leaveDateTime;
        private DateTime actionDateTime;
        private String clockAction;
        private String missedPunchDocId;
        private String missedPunchDocStatus;
        private String assignmentValue;
        private Boolean clockedByMissedPunch;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(TimeBlockContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setHours(contract.getHours());
            builder.setEndDateTime(contract.getEndDateTime());
            builder.setClockLogCreated(contract.isClockLogCreated());
            builder.setBeginDateTime(contract.getBeginDateTime());
            builder.setEndTime(contract.getEndTime());
            builder.setAmount(contract.getAmount());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setTkTimeBlockId(contract.getTkTimeBlockId());
            if (contract.getTimeHourDetails() != null) {
                List<TimeHourDetail.Builder> tempTimeHourDetails = new ArrayList<TimeHourDetail.Builder>();
                for (TimeHourDetailContract tempTHD : contract.getTimeHourDetails()) {
                    tempTimeHourDetails.add(TimeHourDetail.Builder.create(tempTHD));
                }
                builder.setTimeHourDetails(tempTimeHourDetails);
            }
            builder.setPushBackward(contract.isPushBackward());
            builder.setBeginTimeDisplay(contract.getBeginTimeDisplay());
            builder.setBeginTimeDisplayDateOnlyString(contract.getBeginTimeDisplayDateOnlyString());
            builder.setBeginTimeDisplayTimeOnlyString(contract.getBeginTimeDisplayTimeOnlyString());
            builder.setEndTimeDisplayDateOnlyString(contract.getEndTimeDisplayDateOnlyString());
            builder.setEndTimeDisplayTimeOnlyString(contract.getEndTimeDisplayTimeOnlyString());
            builder.setEndTimeDisplay(contract.getEndTimeDisplay());
            builder.setClockLogBeginId(contract.getClockLogBeginId());
            builder.setClockLogEndId(contract.getClockLogEndId());
            builder.setAssignmentKey(contract.getAssignmentKey());
            builder.setAssignmentDescription(contract.getAssignmentDescription());
            builder.setEarnCodeType(contract.getEarnCodeType());
            builder.setPrincipalId(contract.getPrincipalId());
            builder.setOvertimePref(contract.getOvertimePref());
            builder.setActualBeginTimeString(contract.getActualBeginTimeString());
            builder.setActualEndTimeString(contract.getActualEndTimeString());
            builder.setLunchDeleted(contract.isLunchDeleted());
            builder.setHrCalendarBlockId(contract.getHrCalendarBlockId());
            builder.setBeginTime(contract.getBeginTime());
            builder.setCreateTime(contract.getCreateTime());
            builder.setWorkArea(contract.getWorkArea());
            builder.setJobNumber(contract.getJobNumber());
            builder.setTask(contract.getTask());
            builder.setEarnCode(contract.getEarnCode());
            builder.setConcreteBlockType(contract.getConcreteBlockType());
            builder.setConcreteBlockId(contract.getConcreteBlockId());
            builder.setDocumentId(contract.getDocumentId());
            builder.setObjectId(contract.getObjectId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setLeaveDateTime(contract.getLeaveDateTime());
            builder.setActionDateTime(contract.getActionDateTime());
            builder.setClockAction(contract.getClockAction());
            builder.setMissedPunchDocId(contract.getMissedPunchDocId());
            builder.setMissedPunchDocStatus(contract.getMissedPunchDocStatus());
            builder.setAssignmentValue(contract.getAssignmentValue());
            builder.setClockedByMissedPunch(contract.isClockedByMissedPunch());
            return builder;
        }

        public TimeBlock build() {
            return new TimeBlock(this);
        }

        public int compareTo(TimeBlockContract tb) {
            return this.getBeginDateTime().compareTo(tb.getBeginDateTime());
        }

        @Override
        public BigDecimal getHours() {
            return this.hours;
        }

        @Override
        public DateTime getEndDateTime() {
            return this.endDateTime;
        }

        @Override
        public Boolean isClockLogCreated() {
            return this.clockLogCreated;
        }

        @Override
        public DateTime getBeginDateTime() {
            return this.beginDateTime;
        }

        @Override
        public LocalTime getEndTime() {
            return this.endTime;
        }

        @Override
        public BigDecimal getAmount() {
            return this.amount;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getTkTimeBlockId() {
            return this.tkTimeBlockId;
        }

        @Override
        public List<TimeHourDetail.Builder> getTimeHourDetails() {
            return this.timeHourDetails;
        }

        @Override
        public Boolean isPushBackward() {
            return this.pushBackward;
        }

        @Override
        public DateTime getBeginTimeDisplay() {
            return this.beginTimeDisplay;
        }

        @Override
        public String getBeginTimeDisplayDateOnlyString() {
            return this.beginTimeDisplayDateOnlyString;
        }

        @Override
        public String getBeginTimeDisplayTimeOnlyString() {
            return this.beginTimeDisplayTimeOnlyString;
        }

        @Override
        public String getEndTimeDisplayDateOnlyString() {
            return this.endTimeDisplayDateOnlyString;
        }

        @Override
        public String getEndTimeDisplayTimeOnlyString() {
            return this.endTimeDisplayTimeOnlyString;
        }

        @Override
        public DateTime getEndTimeDisplay() {
            return this.endTimeDisplay;
        }

        @Override
        public String getClockLogBeginId() {
            return this.clockLogBeginId;
        }

        @Override
        public String getClockLogEndId() {
            return this.clockLogEndId;
        }

        @Override
        public String getAssignmentKey() {
            return this.assignmentKey;
        }

        @Override
        public String getAssignmentDescription() {
            return this.assignmentDescription;
        }

        @Override
        public String getEarnCodeType() {
            return this.earnCodeType;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public String getOvertimePref() {
            return this.overtimePref;
        }

        @Override
        public String getActualBeginTimeString() {
            return this.actualBeginTimeString;
        }

        @Override
        public String getActualEndTimeString() {
            return this.actualEndTimeString;
        }

        @Override
        public boolean isLunchDeleted() {
            return this.lunchDeleted;
        }

        @Override
        public String getHrCalendarBlockId() {
            return this.hrCalendarBlockId;
        }

        @Override
        public LocalTime getBeginTime() {
            return this.beginTime;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public Long getWorkArea() {
            return this.workArea;
        }

        @Override
        public Long getJobNumber() {
            return this.jobNumber;
        }

        @Override
        public Long getTask() {
            return this.task;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public String getConcreteBlockType() {
            return this.concreteBlockType;
        }

        @Override
        public String getConcreteBlockId() {
            return this.concreteBlockId;
        }

        @Override
        public String getDocumentId() {
            return this.documentId;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        @Override
        public DateTime getLeaveDateTime() {
            return this.leaveDateTime;
        }

        @Override
        public DateTime getActionDateTime() {
            return actionDateTime;
        }

        @Override
        public Boolean isClockedByMissedPunch() {
            return clockedByMissedPunch;
        }

        @Override
        public String getAssignmentValue() {
            return assignmentValue;
        }

        @Override
        public String getMissedPunchDocStatus() {
            return missedPunchDocStatus;
        }

        @Override
        public String getMissedPunchDocId() {
            return missedPunchDocId;
        }

        @Override
        public String getClockAction() {
            return clockAction;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        public void setHours(BigDecimal hours) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hours = hours;
        }

        public void setEndDateTime(DateTime endDateTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.endDateTime = endDateTime;
        }

        public void setClockLogCreated(Boolean clockLogCreated) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.clockLogCreated = clockLogCreated;
        }

        public void setBeginDateTime(DateTime beginDateTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.beginDateTime = beginDateTime;
        }

        public void setEndTime(LocalTime endTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.endTime = endTime;
        }

        public void setAmount(BigDecimal amount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.amount = amount;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setTkTimeBlockId(String tkTimeBlockId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.tkTimeBlockId = tkTimeBlockId;
        }

        public void setTimeHourDetails(List<TimeHourDetail.Builder> timeHourDetails) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.timeHourDetails = timeHourDetails;
        }

        public void setPushBackward(Boolean pushBackward) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pushBackward = pushBackward;
        }

        public void setBeginTimeDisplay(DateTime beginTimeDisplay) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.beginTimeDisplay = beginTimeDisplay;
        }

        public void setBeginTimeDisplayDateOnlyString(String beginTimeDisplayDateOnlyString) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.beginTimeDisplayDateOnlyString = beginTimeDisplayDateOnlyString;
        }

        public void setBeginTimeDisplayTimeOnlyString(String beginTimeDisplayTimeOnlyString) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.beginTimeDisplayTimeOnlyString = beginTimeDisplayTimeOnlyString;
        }

        public void setEndTimeDisplayDateOnlyString(String endTimeDisplayDateOnlyString) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.endTimeDisplayDateOnlyString = endTimeDisplayDateOnlyString;
        }

        public void setEndTimeDisplayTimeOnlyString(String endTimeDisplayTimeOnlyString) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.endTimeDisplayTimeOnlyString = endTimeDisplayTimeOnlyString;
        }

        public void setEndTimeDisplay(DateTime endTimeDisplay) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.endTimeDisplay = endTimeDisplay;
        }

        public void setClockLogBeginId(String clockLogBeginId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.clockLogBeginId = clockLogBeginId;
        }

        public void setClockLogEndId(String clockLogEndId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.clockLogEndId = clockLogEndId;
        }

        public void setAssignmentKey(String assignmentKey) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.assignmentKey = assignmentKey;
        }

        public void setAssignmentDescription(String assignmentDescription) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.assignmentDescription = assignmentDescription;
        }

        public void setEarnCodeType(String earnCodeType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCodeType = earnCodeType;
        }


        public void setPrincipalId(String principalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.principalId = principalId;
        }

        public void setOvertimePref(String overtimePref) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.overtimePref = overtimePref;
        }

        public void setActualBeginTimeString(String actualBeginTimeString) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.actualBeginTimeString = actualBeginTimeString;
        }

        public void setActualEndTimeString(String actualEndTimeString) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.actualEndTimeString = actualEndTimeString;
        }

        public void setLunchDeleted(boolean lunchDeleted) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.lunchDeleted = lunchDeleted;
        }

        public void setHrCalendarBlockId(String hrCalendarBlockId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrCalendarBlockId = hrCalendarBlockId;
        }

        public void setBeginTime(LocalTime beginTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.beginTime = beginTime;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

        public void setWorkArea(Long workArea) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workArea = workArea;
        }

        public void setJobNumber(Long jobNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.jobNumber = jobNumber;
        }

        public void setTask(Long task) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.task = task;
        }

        public void setEarnCode(String earnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCode = earnCode;
        }

        public void setConcreteBlockType(String concreteBlockType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.concreteBlockType = concreteBlockType;
        }

        public void setConcreteBlockId(String concreteBlockId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.concreteBlockId = concreteBlockId;
        }

        public void setDocumentId(String documentId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.documentId = documentId;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setLeaveDateTime(DateTime leaveDateTime) {
            this.leaveDateTime = leaveDateTime;
        }

        public void setClockedByMissedPunch(Boolean clockedByMissedPunch) {
            this.clockedByMissedPunch = clockedByMissedPunch;
        }

        public void setAssignmentValue(String assignmentValue) {
            this.assignmentValue = assignmentValue;
        }

        public void setMissedPunchDocStatus(String missedPunchDocStatus) {
            this.missedPunchDocStatus = missedPunchDocStatus;
        }

        public void setMissedPunchDocId(String missedPunchDocId) {
            this.missedPunchDocId = missedPunchDocId;
        }

        public void setClockAction(String clockAction) {
            this.clockAction = clockAction;
        }

        public void setActionDateTime(DateTime actionDateTime) {
            this.actionDateTime = actionDateTime;
        }
    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "timeBlock";
        final static String TYPE_NAME = "TimeBlockType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String HOURS = "hours";
        final static String END_DATE_TIME = "endDateTime";
        final static String CLOCK_LOG_CREATED = "clockLogCreated";
        final static String BEGIN_DATE_TIME = "beginDateTime";
        final static String END_TIME = "endTime";
        final static String AMOUNT = "amount";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String TK_TIME_BLOCK_ID = "tkTimeBlockId";
        final static String TIME_HOUR_DETAILS = "timeHourDetails";
        final static String PUSH_BACKWARD = "pushBackward";
        final static String BEGIN_TIME_DISPLAY = "beginTimeDisplay";
        final static String BEGIN_TIME_DISPLAY_DATE_ONLY_STRING = "beginTimeDisplayDateOnlyString";
        final static String BEGIN_TIME_DISPLAY_TIME_ONLY_STRING = "beginTimeDisplayTimeOnlyString";
        final static String END_TIME_DISPLAY_DATE_ONLY_STRING = "endTimeDisplayDateOnlyString";
        final static String END_TIME_DISPLAY_TIME_ONLY_STRING = "endTimeDisplayTimeOnlyString";
        final static String END_TIME_DISPLAY = "endTimeDisplay";
        final static String END_TIME_DISPLAY_DATE = "endTimeDisplayDateTime";
        final static String CLOCK_LOG_BEGIN_ID = "clockLogBeginId";
        final static String CLOCK_LOG_END_ID = "clockLogEndId";
        final static String ASSIGNMENT_KEY = "assignmentKey";
        final static String ASSIGNMENT_DESCRIPTION = "assignmentDescription";
        final static String EARN_CODE_TYPE = "earnCodeType";
        final static String PRINCIPAL_ID = "principalId";
        final static String OVERTIME_PREF = "overtimePref";
        final static String ACTUAL_BEGIN_TIME_STRING = "actualBeginTimeString";
        final static String ACTUAL_END_TIME_STRING = "actualEndTimeString";
        final static String LUNCH_DELETED = "lunchDeleted";
        final static String HR_CALENDAR_BLOCK_ID = "hrCalendarBlockId";
        final static String BEGIN_TIME = "beginTime";
        final static String CREATE_TIME = "createTime";
        final static String WORK_AREA = "workArea";
        final static String JOB_NUMBER = "jobNumber";
        final static String TASK = "task";
        final static String EARN_CODE = "earnCode";
        final static String CONCRETE_BLOCK_TYPE = "concreteBlockType";
        final static String CONCRETE_BLOCK_ID = "concreteBlockId";
        final static String DOCUMENT_ID = "documentId";
        final static String LEAVE_DATE_TIME = "leaveDateTime";

        final static String ACTION_DATE_TIME = "actionDateTime";
        final static String CLOCK_ACTION = "clockAction";
        final static String MISSED_PUNCH_DOC_ID = "missedPunchDocId";
        final static String MISSED_PUNCH_DOC_STATUS = "missedPunchDocStatus";
        final static String ASSIGNMENT_VALUE = "assignmentValue";
        final static String CLOCKED_BY_MISSED_PUNCH = "clockedByMissedPunch";


    }

    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "TimeBlock";
}
