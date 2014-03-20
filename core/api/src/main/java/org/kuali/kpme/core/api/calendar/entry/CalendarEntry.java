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
package org.kuali.kpme.core.api.calendar.entry;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.util.jaxb.LocalDateTimeAdapter;
import org.kuali.kpme.core.api.util.jaxb.LocalTimeAdapter;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = CalendarEntry.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = CalendarEntry.Constants.TYPE_NAME, propOrder = {
        CalendarEntry.Elements.BEGIN_PERIOD_LOCAL_TIME,
        CalendarEntry.Elements.HR_CALENDAR_ID,
        CalendarEntry.Elements.BEGIN_PERIOD_LOCAL_DATE_TIME,
        CalendarEntry.Elements.END_PERIOD_FULL_DATE_TIME,
        CalendarEntry.Elements.END_PERIOD_LOCAL_DATE_TIME,
        CalendarEntry.Elements.BATCH_INITIATE_LOCAL_TIME,
        CalendarEntry.Elements.HR_CALENDAR_ENTRY_ID,
        CalendarEntry.Elements.CALENDAR_NAME,
        CalendarEntry.Elements.BEGIN_PERIOD_FULL_DATE_TIME,
        CalendarEntry.Elements.END_PERIOD_LOCAL_TIME,
        CalendarEntry.Elements.BATCH_INITIATE_FULL_DATE_TIME,
        CalendarEntry.Elements.BATCH_END_PAY_PERIOD_LOCAL_TIME,
        CalendarEntry.Elements.BATCH_END_PAY_PERIOD_FULL_DATE_TIME,
        CalendarEntry.Elements.BATCH_EMPLOYEE_APPROVAL_LOCAL_TIME,
        CalendarEntry.Elements.BATCH_EMPLOYEE_APPROVAL_FULL_DATE_TIME,
        CalendarEntry.Elements.BATCH_SUPERVISOR_APPROVAL_LOCAL_TIME,
        CalendarEntry.Elements.BATCH_SUPERVISOR_APPROVAL_FULL_DATE_TIME,
        CalendarEntry.Elements.BATCH_PAYROLL_APPROVAL_LOCAL_TIME,
        CalendarEntry.Elements.BATCH_PAYROLL_APPROVAL_FULL_DATE_TIME,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class CalendarEntry
        extends AbstractDataTransferObject
        implements CalendarEntryContract
{

    private static final long serialVersionUID = -5035798908935382456L;
    @XmlElement(name = Elements.BEGIN_PERIOD_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime beginPeriodLocalTime;
    @XmlElement(name = Elements.HR_CALENDAR_ID, required = false)
    private final String hrCalendarId;
    @XmlElement(name = Elements.BEGIN_PERIOD_LOCAL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private final LocalDateTime beginPeriodLocalDateTime;
    @XmlElement(name = Elements.END_PERIOD_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime endPeriodFullDateTime;
    @XmlElement(name = Elements.END_PERIOD_LOCAL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private final LocalDateTime endPeriodLocalDateTime;
    @XmlElement(name = Elements.BATCH_INITIATE_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime batchInitiateLocalTime;
    @XmlElement(name = Elements.HR_CALENDAR_ENTRY_ID, required = false)
    private final String hrCalendarEntryId;
    @XmlElement(name = Elements.CALENDAR_NAME, required = false)
    private final String calendarName;
    @XmlElement(name = Elements.BEGIN_PERIOD_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime beginPeriodFullDateTime;
    @XmlElement(name = Elements.END_PERIOD_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime endPeriodLocalTime;
    @XmlElement(name = Elements.BATCH_INITIATE_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime batchInitiateFullDateTime;
    @XmlElement(name = Elements.BATCH_END_PAY_PERIOD_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime batchEndPayPeriodLocalTime;
    @XmlElement(name = Elements.BATCH_END_PAY_PERIOD_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime batchEndPayPeriodFullDateTime;
    @XmlElement(name = Elements.BATCH_EMPLOYEE_APPROVAL_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime batchEmployeeApprovalLocalTime;
    @XmlElement(name = Elements.BATCH_EMPLOYEE_APPROVAL_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime batchEmployeeApprovalFullDateTime;
    @XmlElement(name = Elements.BATCH_SUPERVISOR_APPROVAL_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime batchSupervisorApprovalLocalTime;
    @XmlElement(name = Elements.BATCH_SUPERVISOR_APPROVAL_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime batchSupervisorApprovalFullDateTime;
    @XmlElement(name = Elements.BATCH_PAYROLL_APPROVAL_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime batchPayrollApprovalLocalTime;
    @XmlElement(name = Elements.BATCH_PAYROLL_APPROVAL_FULL_DATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime batchPayrollApprovalFullDateTime;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private CalendarEntry() {
        this.beginPeriodLocalTime = null;
        this.hrCalendarId = null;
        this.beginPeriodLocalDateTime = null;
        this.endPeriodFullDateTime = null;
        this.endPeriodLocalDateTime = null;
        this.batchInitiateLocalTime = null;
        this.hrCalendarEntryId = null;
        this.calendarName = null;
        this.beginPeriodFullDateTime = null;
        this.endPeriodLocalTime = null;
        this.batchInitiateFullDateTime = null;
        this.batchEndPayPeriodLocalTime = null;
        this.batchEndPayPeriodFullDateTime = null;
        this.batchEmployeeApprovalLocalTime = null;
        this.batchEmployeeApprovalFullDateTime = null;
        this.batchSupervisorApprovalLocalTime = null;
        this.batchSupervisorApprovalFullDateTime = null;
        this.batchPayrollApprovalLocalTime = null;
        this.batchPayrollApprovalFullDateTime = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private CalendarEntry(Builder builder) {
        this.beginPeriodLocalTime = builder.getBeginPeriodLocalTime();
        this.hrCalendarId = builder.getHrCalendarId();
        this.beginPeriodLocalDateTime = builder.getBeginPeriodLocalDateTime();
        this.endPeriodFullDateTime = builder.getEndPeriodFullDateTime();
        this.endPeriodLocalDateTime = builder.getEndPeriodLocalDateTime();
        this.batchInitiateLocalTime = builder.getBatchInitiateLocalTime();
        this.hrCalendarEntryId = builder.getHrCalendarEntryId();
        this.calendarName = builder.getCalendarName();
        this.beginPeriodFullDateTime = builder.getBeginPeriodFullDateTime();
        this.endPeriodLocalTime = builder.getEndPeriodLocalTime();
        this.batchInitiateFullDateTime = builder.getBatchInitiateFullDateTime();
        this.batchEndPayPeriodLocalTime = builder.getBatchEndPayPeriodLocalTime();
        this.batchEndPayPeriodFullDateTime = builder.getBatchEndPayPeriodFullDateTime();
        this.batchEmployeeApprovalLocalTime = builder.getBatchEmployeeApprovalLocalTime();
        this.batchEmployeeApprovalFullDateTime = builder.getBatchEmployeeApprovalFullDateTime();
        this.batchSupervisorApprovalLocalTime = builder.getBatchSupervisorApprovalLocalTime();
        this.batchSupervisorApprovalFullDateTime = builder.getBatchSupervisorApprovalFullDateTime();
        this.batchPayrollApprovalLocalTime = builder.getBatchPayrollApprovalLocalTime();
        this.batchPayrollApprovalFullDateTime = builder.getBatchPayrollApprovalFullDateTime();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public LocalTime getBeginPeriodLocalTime() {
        return this.beginPeriodLocalTime;
    }

    @Override
    public String getHrCalendarId() {
        return this.hrCalendarId;
    }

    @Override
    public LocalDateTime getBeginPeriodLocalDateTime() {
        return this.beginPeriodLocalDateTime;
    }

    @Override
    public DateTime getEndPeriodFullDateTime() {
        return this.endPeriodFullDateTime;
    }

    @Override
    public LocalDateTime getEndPeriodLocalDateTime() {
        return this.endPeriodLocalDateTime;
    }

    @Override
    public LocalTime getBatchInitiateLocalTime() {
        return this.batchInitiateLocalTime;
    }

    @Override
    public String getHrCalendarEntryId() {
        return this.hrCalendarEntryId;
    }

    @Override
    public String getCalendarName() {
        return this.calendarName;
    }

    @Override
    public DateTime getBeginPeriodFullDateTime() {
        return this.beginPeriodFullDateTime;
    }

    @Override
    public LocalTime getEndPeriodLocalTime() {
        return this.endPeriodLocalTime;
    }

    @Override
    public DateTime getBatchInitiateFullDateTime() {
        return this.batchInitiateFullDateTime;
    }

    @Override
    public LocalTime getBatchEndPayPeriodLocalTime() {
        return this.batchEndPayPeriodLocalTime;
    }

    @Override
    public DateTime getBatchEndPayPeriodFullDateTime() {
        return this.batchEndPayPeriodFullDateTime;
    }

    @Override
    public LocalTime getBatchEmployeeApprovalLocalTime() {
        return this.batchEmployeeApprovalLocalTime;
    }

    @Override
    public DateTime getBatchEmployeeApprovalFullDateTime() {
        return this.batchEmployeeApprovalFullDateTime;
    }

    @Override
    public LocalTime getBatchSupervisorApprovalLocalTime() {
        return this.batchSupervisorApprovalLocalTime;
    }

    @Override
    public DateTime getBatchSupervisorApprovalFullDateTime() {
        return this.batchSupervisorApprovalFullDateTime;
    }

    @Override
    public LocalTime getBatchPayrollApprovalLocalTime() {
        return this.batchPayrollApprovalLocalTime;
    }

    @Override
    public DateTime getBatchPayrollApprovalFullDateTime() {
        return this.batchPayrollApprovalFullDateTime;
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
    public int compareTo(CalendarEntryContract pce) {
        return this.getBeginPeriodFullDateTime().compareTo(pce.getBeginPeriodFullDateTime());
    }


    /**
     * A builder which can be used to construct {@link CalendarEntry} instances.  Enforces the constraints of the {@link CalendarEntryContract}.
     *
     */
    public final static class Builder
            implements Serializable, CalendarEntryContract, ModelBuilder
    {

        private static final long serialVersionUID = -8591376112110522172L;
        private String hrCalendarId;
        private DateTime endPeriodFullDateTime;
        private String hrCalendarEntryId;
        private String calendarName;
        private DateTime beginPeriodFullDateTime;
        private DateTime batchInitiateFullDateTime;
        private DateTime batchEndPayPeriodFullDateTime;
        private DateTime batchEmployeeApprovalFullDateTime;
        private DateTime batchSupervisorApprovalFullDateTime;
        private DateTime batchPayrollApprovalFullDateTime;
        private Long versionNumber;
        private String objectId;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(CalendarEntryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setHrCalendarId(contract.getHrCalendarId());
            builder.setEndPeriodFullDateTime(contract.getEndPeriodFullDateTime());
            builder.setHrCalendarEntryId(contract.getHrCalendarEntryId());
            builder.setCalendarName(contract.getCalendarName());
            builder.setBeginPeriodFullDateTime(contract.getBeginPeriodFullDateTime());
            builder.setBatchInitiateFullDateTime(contract.getBatchInitiateFullDateTime());
            builder.setBatchEndPayPeriodFullDateTime(contract.getBatchEndPayPeriodFullDateTime());
            builder.setBatchEmployeeApprovalFullDateTime(contract.getBatchEmployeeApprovalFullDateTime());
            builder.setBatchSupervisorApprovalFullDateTime(contract.getBatchSupervisorApprovalFullDateTime());
            builder.setBatchPayrollApprovalFullDateTime(contract.getBatchPayrollApprovalFullDateTime());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public CalendarEntry build() {
            return new CalendarEntry(this);
        }

        private LocalTime nullSafe(DateTime dateTime) {
            return dateTime == null ? null : dateTime.toLocalTime();
        }
        @Override
        public LocalTime getBeginPeriodLocalTime() {
            return getBeginPeriodFullDateTime().toLocalTime();
        }

        @Override
        public String getHrCalendarId() {
            return this.hrCalendarId;
        }

        @Override
        public LocalDateTime getBeginPeriodLocalDateTime() {
            return getBeginPeriodFullDateTime().toLocalDateTime();
        }

        @Override
        public DateTime getEndPeriodFullDateTime() {
            return this.endPeriodFullDateTime;
        }

        @Override
        public LocalDateTime getEndPeriodLocalDateTime() {
            return getEndPeriodFullDateTime().toLocalDateTime();
        }

        @Override
        public LocalTime getBatchInitiateLocalTime() {
            return nullSafe(getBatchInitiateFullDateTime());
        }

        @Override
        public String getHrCalendarEntryId() {
            return this.hrCalendarEntryId;
        }

        @Override
        public String getCalendarName() {
            return this.calendarName;
        }

        @Override
        public DateTime getBeginPeriodFullDateTime() {
            return this.beginPeriodFullDateTime;
        }

        @Override
        public LocalTime getEndPeriodLocalTime() {
            return getEndPeriodFullDateTime().toLocalTime();
        }

        @Override
        public DateTime getBatchInitiateFullDateTime() {
            return this.batchInitiateFullDateTime;
        }

        @Override
        public LocalTime getBatchEndPayPeriodLocalTime() {
            return nullSafe(getBatchEndPayPeriodFullDateTime());
        }

        @Override
        public DateTime getBatchEndPayPeriodFullDateTime() {
            return this.batchEndPayPeriodFullDateTime;
        }

        @Override
        public LocalTime getBatchEmployeeApprovalLocalTime() {
            return nullSafe(getBatchEmployeeApprovalFullDateTime());
        }

        @Override
        public DateTime getBatchEmployeeApprovalFullDateTime() {
            return this.batchEmployeeApprovalFullDateTime;
        }

        @Override
        public LocalTime getBatchSupervisorApprovalLocalTime() {
            return nullSafe(getBatchSupervisorApprovalFullDateTime());
        }

        @Override
        public DateTime getBatchSupervisorApprovalFullDateTime() {
            return this.batchSupervisorApprovalFullDateTime;
        }

        @Override
        public LocalTime getBatchPayrollApprovalLocalTime() {
            return nullSafe(getBatchPayrollApprovalFullDateTime());
        }

        @Override
        public DateTime getBatchPayrollApprovalFullDateTime() {
            return this.batchPayrollApprovalFullDateTime;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setHrCalendarId(String hrCalendarId) {
            if (StringUtils.isBlank(hrCalendarId)) {
                throw new IllegalArgumentException("hrCalendarId is blank");
            }
            this.hrCalendarId = hrCalendarId;
        }

        public void setEndPeriodFullDateTime(DateTime endPeriodFullDateTime) {
            this.endPeriodFullDateTime = endPeriodFullDateTime;
        }

        public void setHrCalendarEntryId(String hrCalendarEntryId) {
            this.hrCalendarEntryId = hrCalendarEntryId;
        }

        public void setCalendarName(String calendarName) {
            this.calendarName = calendarName;
        }

        public void setBeginPeriodFullDateTime(DateTime beginPeriodFullDateTime) {
            this.beginPeriodFullDateTime = beginPeriodFullDateTime;
        }

        public void setBatchInitiateFullDateTime(DateTime batchInitiateFullDateTime) {
            this.batchInitiateFullDateTime = batchInitiateFullDateTime;
        }

        public void setBatchEndPayPeriodFullDateTime(DateTime batchEndPayPeriodFullDateTime) {
            this.batchEndPayPeriodFullDateTime = batchEndPayPeriodFullDateTime;
        }

        public void setBatchEmployeeApprovalFullDateTime(DateTime batchEmployeeApprovalFullDateTime) {
            this.batchEmployeeApprovalFullDateTime = batchEmployeeApprovalFullDateTime;
        }

        public void setBatchSupervisorApprovalFullDateTime(DateTime batchSupervisorApprovalFullDateTime) {
            this.batchSupervisorApprovalFullDateTime = batchSupervisorApprovalFullDateTime;
        }

        public void setBatchPayrollApprovalFullDateTime(DateTime batchPayrollApprovalFullDateTime) {
            this.batchPayrollApprovalFullDateTime = batchPayrollApprovalFullDateTime;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        @Override
        public int compareTo(CalendarEntryContract pce) {
            return this.getBeginPeriodFullDateTime().compareTo(pce.getBeginPeriodFullDateTime());
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "calendarEntry";
        final static String TYPE_NAME = "CalendarEntryType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String BEGIN_PERIOD_LOCAL_TIME = "beginPeriodLocalTime";
        final static String HR_CALENDAR_ID = "hrCalendarId";
        final static String BEGIN_PERIOD_LOCAL_DATE_TIME = "beginPeriodLocalDateTime";
        final static String END_PERIOD_FULL_DATE_TIME = "endPeriodFullDateTime";
        final static String END_PERIOD_LOCAL_DATE_TIME = "endPeriodLocalDateTime";
        final static String BATCH_INITIATE_LOCAL_TIME = "batchInitiateLocalTime";
        final static String HR_CALENDAR_ENTRY_ID = "hrCalendarEntryId";
        final static String CALENDAR_NAME = "calendarName";
        final static String BEGIN_PERIOD_FULL_DATE_TIME = "beginPeriodFullDateTime";
        final static String END_PERIOD_LOCAL_TIME = "endPeriodLocalTime";
        final static String BATCH_INITIATE_FULL_DATE_TIME = "batchInitiateFullDateTime";
        final static String BATCH_END_PAY_PERIOD_LOCAL_TIME = "batchEndPayPeriodLocalTime";
        final static String BATCH_END_PAY_PERIOD_FULL_DATE_TIME = "batchEndPayPeriodFullDateTime";
        final static String BATCH_EMPLOYEE_APPROVAL_LOCAL_TIME = "batchEmployeeApprovalLocalTime";
        final static String BATCH_EMPLOYEE_APPROVAL_FULL_DATE_TIME = "batchEmployeeApprovalFullDateTime";
        final static String BATCH_SUPERVISOR_APPROVAL_LOCAL_TIME = "batchSupervisorApprovalLocalTime";
        final static String BATCH_SUPERVISOR_APPROVAL_FULL_DATE_TIME = "batchSupervisorApprovalFullDateTime";
        final static String BATCH_PAYROLL_APPROVAL_LOCAL_TIME = "batchPayrollApprovalLocalTime";
        final static String BATCH_PAYROLL_APPROVAL_FULL_DATE_TIME = "batchPayrollApprovalFullDateTime";

    }

}