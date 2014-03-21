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
package org.kuali.kpme.core.api.calendar;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.util.jaxb.LocalTimeAdapter;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = Calendar.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Calendar.Constants.TYPE_NAME, propOrder = {
        Calendar.Elements.CALENDAR_DESCRIPTIONS,
        Calendar.Elements.CALENDAR_NAME,
        Calendar.Elements.CALENDAR_TYPES,
        Calendar.Elements.FLSA_BEGIN_DAY,
        Calendar.Elements.FLSA_BEGIN_LOCAL_TIME,
        Calendar.Elements.FLSA_BEGIN_DAY_CONSTANT,
        Calendar.Elements.HR_CALENDAR_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Calendar
        extends AbstractDataTransferObject
        implements CalendarContract
{

    private static final long serialVersionUID = 8004629702037414752L;
    @XmlElement(name = Elements.CALENDAR_DESCRIPTIONS, required = false)
    private final String calendarDescriptions;
    @XmlElement(name = Elements.CALENDAR_NAME, required = false)
    private final String calendarName;
    @XmlElement(name = Elements.CALENDAR_TYPES, required = false)
    private final String calendarTypes;
    @XmlElement(name = Elements.FLSA_BEGIN_DAY, required = false)
    private final String flsaBeginDay;
    @XmlElement(name = Elements.FLSA_BEGIN_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime flsaBeginLocalTime;
    @XmlElement(name = Elements.FLSA_BEGIN_DAY_CONSTANT, required = false)
    private final int flsaBeginDayConstant;
    @XmlElement(name = Elements.HR_CALENDAR_ID, required = false)
    private final String hrCalendarId;
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
    private Calendar() {
        this.calendarDescriptions = null;
        this.calendarName = null;
        this.calendarTypes = null;
        this.flsaBeginDay = null;
        this.flsaBeginLocalTime = null;
        this.flsaBeginDayConstant = 0;
        this.hrCalendarId = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private Calendar(Builder builder) {
        this.calendarDescriptions = builder.getCalendarDescriptions();
        this.calendarName = builder.getCalendarName();
        this.calendarTypes = builder.getCalendarTypes();
        this.flsaBeginDay = builder.getFlsaBeginDay();
        this.flsaBeginLocalTime = builder.getFlsaBeginLocalTime();
        this.flsaBeginDayConstant = builder.getFlsaBeginDayConstant();
        this.hrCalendarId = builder.getHrCalendarId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getCalendarDescriptions() {
        return this.calendarDescriptions;
    }

    @Override
    public String getCalendarName() {
        return this.calendarName;
    }

    @Override
    public String getCalendarTypes() {
        return this.calendarTypes;
    }

    @Override
    public String getFlsaBeginDay() {
        return this.flsaBeginDay;
    }

    @Override
    public LocalTime getFlsaBeginLocalTime() {
        return this.flsaBeginLocalTime;
    }

    @Override
    public int getFlsaBeginDayConstant() {
        return this.flsaBeginDayConstant;
    }

    @Override
    public String getHrCalendarId() {
        return this.hrCalendarId;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }


    /**
     * A builder which can be used to construct {@link Calendar} instances.  Enforces the constraints of the {@link CalendarContract}.
     *
     */
    public final static class Builder
            implements Serializable, CalendarContract, ModelBuilder
    {

        private String calendarDescriptions;
        private String calendarName;
        private String calendarTypes;
        private String flsaBeginDay;
        private LocalTime flsaBeginLocalTime;
        private int flsaBeginDayConstant;
        private String hrCalendarId;
        private Long versionNumber;
        private String objectId;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(CalendarContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setCalendarDescriptions(contract.getCalendarDescriptions());
            builder.setCalendarName(contract.getCalendarName());
            builder.setCalendarTypes(contract.getCalendarTypes());
            builder.setFlsaBeginDay(contract.getFlsaBeginDay());
            builder.setFlsaBeginLocalTime(contract.getFlsaBeginLocalTime());
            builder.setFlsaBeginDayConstant(contract.getFlsaBeginDayConstant());
            builder.setHrCalendarId(contract.getHrCalendarId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public Calendar build() {
            return new Calendar(this);
        }

        @Override
        public String getCalendarDescriptions() {
            return this.calendarDescriptions;
        }

        @Override
        public String getCalendarName() {
            return this.calendarName;
        }

        @Override
        public String getCalendarTypes() {
            return this.calendarTypes;
        }

        @Override
        public String getFlsaBeginDay() {
            return this.flsaBeginDay;
        }

        @Override
        public LocalTime getFlsaBeginLocalTime() {
            return this.flsaBeginLocalTime;
        }

        @Override
        public int getFlsaBeginDayConstant() {
            return this.flsaBeginDayConstant;
        }

        @Override
        public String getHrCalendarId() {
            return this.hrCalendarId;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setCalendarDescriptions(String calendarDescriptions) {
            this.calendarDescriptions = calendarDescriptions;
        }

        public void setCalendarName(String calendarName) {
            this.calendarName = calendarName;
        }

        public void setCalendarTypes(String calendarTypes) {
            this.calendarTypes = calendarTypes;
        }

        public void setFlsaBeginDay(String flsaBeginDay) {
            this.flsaBeginDay = flsaBeginDay;
        }

        public void setFlsaBeginLocalTime(LocalTime flsaBeginLocalTime) {
            this.flsaBeginLocalTime = flsaBeginLocalTime;
        }

        public void setFlsaBeginDayConstant(int flsaBeginDayConstant) {
            this.flsaBeginDayConstant = flsaBeginDayConstant;
        }

        public void setHrCalendarId(String hrCalendarId) {
            this.hrCalendarId = hrCalendarId;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "calendar";
        final static String TYPE_NAME = "CalendarType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String CALENDAR_DESCRIPTIONS = "calendarDescriptions";
        final static String CALENDAR_NAME = "calendarName";
        final static String CALENDAR_TYPES = "calendarTypes";
        final static String FLSA_BEGIN_DAY = "flsaBeginDay";
        final static String FLSA_BEGIN_LOCAL_TIME = "flsaBeginLocalTime";
        final static String FLSA_BEGIN_DAY_CONSTANT = "flsaBeginDayConstant";
        final static String HR_CALENDAR_ID = "hrCalendarId";

    }

}