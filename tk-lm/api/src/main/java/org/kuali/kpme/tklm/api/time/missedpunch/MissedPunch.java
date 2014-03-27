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
package org.kuali.kpme.tklm.api.time.missedpunch;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = MissedPunch.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = MissedPunch.Constants.TYPE_NAME, propOrder = {
        MissedPunch.Elements.TASK,
        MissedPunch.Elements.ACTION_FULL_DATE_TIME,
        MissedPunch.Elements.ACTION_LOCAL_DATE,
        MissedPunch.Elements.ACTION_TIME,
        MissedPunch.Elements.CLOCK_ACTION,
        MissedPunch.Elements.TK_CLOCK_LOG_ID,
        MissedPunch.Elements.PRINCIPAL_NAME,
        MissedPunch.Elements.PERSON_NAME,
        MissedPunch.Elements.ASSIGNMENT_READ_ONLY,
        MissedPunch.Elements.TK_MISSED_PUNCH_ID,
        MissedPunch.Elements.JOB_NUMBER,
        MissedPunch.Elements.WORK_AREA,
        MissedPunch.Elements.TIMESHEET_DOCUMENT_ID,
        MissedPunch.Elements.PRINCIPAL_ID,
        MissedPunch.Elements.ASSIGNMENT_KEY,
        MissedPunch.Elements.ASSIGNMENT_VALUE,
        MissedPunch.Elements.MISSED_PUNCH_DOC_ID,
        MissedPunch.Elements.MISSED_PUNCH_DOC_STATUS,
        MissedPunch.Elements.CREATE_TIME,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class MissedPunch
        extends AbstractDataTransferObject
        implements MissedPunchContract
{

    private static final long serialVersionUID = -8100921453474425355L;
    @XmlElement(name = Elements.TASK, required = false)
    private final Long task;
    @XmlElement(name = Elements.ACTION_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime actionFullDateTime;
    @XmlElement(name = Elements.ACTION_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate actionLocalDate;
    @XmlElement(name = Elements.ACTION_TIME, required = false)
    private final String actionTime;
    @XmlElement(name = Elements.CLOCK_ACTION, required = false)
    private final String clockAction;
    @XmlElement(name = Elements.TK_CLOCK_LOG_ID, required = false)
    private final String tkClockLogId;
    @XmlElement(name = Elements.PRINCIPAL_NAME, required = false)
    private final String principalName;
    @XmlElement(name = Elements.PERSON_NAME, required = false)
    private final String personName;
    @XmlElement(name = Elements.ASSIGNMENT_READ_ONLY, required = false)
    private final boolean assignmentReadOnly;
    @XmlElement(name = Elements.TK_MISSED_PUNCH_ID, required = false)
    private final String tkMissedPunchId;
    @XmlElement(name = Elements.JOB_NUMBER, required = false)
    private final Long jobNumber;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = Elements.TIMESHEET_DOCUMENT_ID, required = false)
    private final String timesheetDocumentId;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.ASSIGNMENT_KEY, required = false)
    private final String assignmentKey;
    @XmlElement(name = Elements.ASSIGNMENT_VALUE, required = false)
    private final String assignmentValue;
    @XmlElement(name = Elements.MISSED_PUNCH_DOC_ID, required = false)
    private final String missedPunchDocId;
    @XmlElement(name = Elements.MISSED_PUNCH_DOC_STATUS, required = false)
    private final String missedPunchDocStatus;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private MissedPunch() {
        this.task = null;
        this.actionFullDateTime = null;
        this.actionLocalDate = null;
        this.actionTime = null;
        this.clockAction = null;
        this.tkClockLogId = null;
        this.principalName = null;
        this.personName = null;
        this.assignmentReadOnly = false;
        this.tkMissedPunchId = null;
        this.jobNumber = null;
        this.workArea = null;
        this.timesheetDocumentId = null;
        this.principalId = null;
        this.assignmentKey = null;
        this.assignmentValue = null;
        this.versionNumber = null;
        this.objectId = null;
        this.createTime = null;
        this.missedPunchDocId = null;
        this.missedPunchDocStatus = null;
    }

    private MissedPunch(Builder builder) {
        this.task = builder.getTask();
        this.actionFullDateTime = builder.getActionFullDateTime();
        this.actionLocalDate = builder.getActionLocalDate();
        this.actionTime = builder.getActionTime();
        this.clockAction = builder.getClockAction();
        this.tkClockLogId = builder.getTkClockLogId();
        this.principalName = builder.getPrincipalName();
        this.personName = builder.getPersonName();
        this.assignmentReadOnly = builder.isAssignmentReadOnly();
        this.tkMissedPunchId = builder.getTkMissedPunchId();
        this.jobNumber = builder.getJobNumber();
        this.workArea = builder.getWorkArea();
        this.timesheetDocumentId = builder.getTimesheetDocumentId();
        this.principalId = builder.getPrincipalId();
        this.assignmentKey = builder.getAssignmentKey();
        this.assignmentValue = builder.getAssignmentValue();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.createTime = builder.getCreateTime();
        this.missedPunchDocId = builder.getMissedPunchDocId();
        this.missedPunchDocStatus = builder.getMissedPunchDocStatus();
    }

    @Override
    public Long getTask() {
        return this.task;
    }

    @Override
    public DateTime getActionFullDateTime() {
        return this.actionFullDateTime;
    }

    @Override
    public LocalDate getActionLocalDate() {
        return this.actionLocalDate;
    }

    @Override
    public String getActionTime() {
        return this.actionTime;
    }

    @Override
    public String getClockAction() {
        return this.clockAction;
    }

    @Override
    public String getTkClockLogId() {
        return this.tkClockLogId;
    }

    @Override
    public String getPrincipalName() {
        return this.principalName;
    }

    @Override
    public String getPersonName() {
        return this.personName;
    }

    @Override
    public boolean isAssignmentReadOnly() {
        return this.assignmentReadOnly;
    }

    @Override
    public String getTkMissedPunchId() {
        return this.tkMissedPunchId;
    }

    @Override
    public Long getJobNumber() {
        return this.jobNumber;
    }

    @Override
    public Long getWorkArea() {
        return this.workArea;
    }

    @Override
    public String getTimesheetDocumentId() {
        return this.timesheetDocumentId;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public String getAssignmentKey() {
        return this.assignmentKey;
    }

    @Override
    public String getAssignmentValue() {
        return this.assignmentValue;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public String getMissedPunchDocId() {
        return missedPunchDocId;
    }

    @Override
    public String getMissedPunchDocStatus() {
        return missedPunchDocStatus;
    }

    /**
     * A builder which can be used to construct {@link MissedPunch} instances.  Enforces the constraints of the {@link MissedPunchContract}.
     *
     */
    public final static class Builder
            implements Serializable, MissedPunchContract, ModelBuilder
    {

        private Long task;
        private DateTime actionFullDateTime;
        private String actionTime;
        private String clockAction;
        private String tkClockLogId;
        private String principalName;
        private String personName;
        private boolean assignmentReadOnly;
        private String tkMissedPunchId;
        private Long jobNumber;
        private Long workArea;
        private String timesheetDocumentId;
        private String principalId;
        private String assignmentKey;
        private String assignmentValue;
        private Long versionNumber;
        private String objectId;
        private DateTime createTime;
        private String missedPunchDocId;
        private String missedPunchDocStatus;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(MissedPunchContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setTask(contract.getTask());
            builder.setActionFullDateTime(contract.getActionFullDateTime());
            builder.setActionTime(contract.getActionTime());
            builder.setClockAction(contract.getClockAction());
            builder.setTkClockLogId(contract.getTkClockLogId());
            builder.setPrincipalName(contract.getPrincipalName());
            builder.setPersonName(contract.getPersonName());
            builder.setAssignmentReadOnly(contract.isAssignmentReadOnly());
            builder.setTkMissedPunchId(contract.getTkMissedPunchId());
            builder.setJobNumber(contract.getJobNumber());
            builder.setWorkArea(contract.getWorkArea());
            builder.setTimesheetDocumentId(contract.getTimesheetDocumentId());
            builder.setPrincipalId(contract.getPrincipalId());
            builder.setAssignmentKey(contract.getAssignmentKey());
            builder.setAssignmentValue(contract.getAssignmentValue());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setMissedPunchDocId(contract.getMissedPunchDocId());
            builder.setMissedPunchDocStatus(contract.getMissedPunchDocStatus());
            return builder;
        }

        public MissedPunch build() {
            return new MissedPunch(this);
        }

        @Override
        public Long getTask() {
            return this.task;
        }

        @Override
        public DateTime getActionFullDateTime() {
            return this.actionFullDateTime;
        }

        @Override
        public LocalDate getActionLocalDate() {
            return getActionFullDateTime() == null ? null : getActionFullDateTime().toLocalDate();
        }

        @Override
        public String getActionTime() {
            return this.actionTime;
        }

        @Override
        public String getClockAction() {
            return this.clockAction;
        }

        @Override
        public String getTkClockLogId() {
            return this.tkClockLogId;
        }

        @Override
        public String getPrincipalName() {
            return this.principalName;
        }

        @Override
        public String getPersonName() {
            return this.personName;
        }

        @Override
        public boolean isAssignmentReadOnly() {
            return this.assignmentReadOnly;
        }

        @Override
        public String getTkMissedPunchId() {
            return this.tkMissedPunchId;
        }

        @Override
        public Long getJobNumber() {
            return this.jobNumber;
        }

        @Override
        public Long getWorkArea() {
            return this.workArea;
        }

        @Override
        public String getTimesheetDocumentId() {
            return this.timesheetDocumentId;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public String getAssignmentKey() {
            return this.assignmentKey;
        }

        @Override
        public String getAssignmentValue() {
            return this.assignmentValue;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public String getMissedPunchDocId() {
            return missedPunchDocId;
        }

        @Override
        public String getMissedPunchDocStatus() {
            return missedPunchDocStatus;
        }

        public void setMissedPunchDocId(String missedPunchDocId) {
            this.missedPunchDocId = missedPunchDocId;
        }

        public void setMissedPunchDocStatus(String missedPunchDocStatus) {
            this.missedPunchDocStatus = missedPunchDocStatus;
        }

        public void setTask(Long task) {
            this.task = task;
        }

        public void setActionFullDateTime(DateTime actionFullDateTime) {
            this.actionFullDateTime = actionFullDateTime;
        }

        public void setActionTime(String actionTime) {
            this.actionTime = actionTime;
        }

        public void setClockAction(String clockAction) {
            this.clockAction = clockAction;
        }

        public void setTkClockLogId(String tkClockLogId) {
            this.tkClockLogId = tkClockLogId;
        }

        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public void setAssignmentReadOnly(boolean assignmentReadOnly) {
            this.assignmentReadOnly = assignmentReadOnly;
        }

        public void setTkMissedPunchId(String tkMissedPunchId) {
            this.tkMissedPunchId = tkMissedPunchId;
        }

        public void setJobNumber(Long jobNumber) {
            this.jobNumber = jobNumber;
        }

        public void setWorkArea(Long workArea) {
            this.workArea = workArea;
        }

        public void setTimesheetDocumentId(String timesheetDocumentId) {
            this.timesheetDocumentId = timesheetDocumentId;
        }

        public void setPrincipalId(String principalId) {
            this.principalId = principalId;
        }

        public void setAssignmentKey(String assignmentKey) {
            this.assignmentKey = assignmentKey;
        }

        public void setAssignmentValue(String assignmentValue) {
            this.assignmentValue = assignmentValue;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "missedPunch";
        final static String TYPE_NAME = "MissedPunchType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String TASK = "task";
        final static String ACTION_FULL_DATE_TIME = "actionFullDateTime";
        final static String ACTION_LOCAL_DATE = "actionLocalDate";
        final static String ACTION_TIME = "actionTime";
        final static String CLOCK_ACTION = "clockAction";
        final static String TK_CLOCK_LOG_ID = "tkClockLogId";
        final static String PRINCIPAL_NAME = "principalName";
        final static String PERSON_NAME = "personName";
        final static String ASSIGNMENT_READ_ONLY = "assignmentReadOnly";
        final static String TK_MISSED_PUNCH_ID = "tkMissedPunchId";
        final static String JOB_NUMBER = "jobNumber";
        final static String WORK_AREA = "workArea";
        final static String TIMESHEET_DOCUMENT_ID = "timesheetDocumentId";
        final static String PRINCIPAL_ID = "principalId";
        final static String ASSIGNMENT_KEY = "assignmentKey";
        final static String ASSIGNMENT_VALUE = "assignmentValue";
        final static String CREATE_TIME = "createTime";
        final static String MISSED_PUNCH_DOC_ID = "missedPunchDocId";
        final static String MISSED_PUNCH_DOC_STATUS = "missedPunchDocStatus";

    }

}