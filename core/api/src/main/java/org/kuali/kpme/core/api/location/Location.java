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
package org.kuali.kpme.core.api.location;

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
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = Location.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Location.Constants.TYPE_NAME, propOrder = {
        Location.Elements.LOCATION,
        Location.Elements.HR_LOCATION_ID,
        Location.Elements.TIMEZONE,
        Location.Elements.DESCRIPTION,
        Location.Elements.USER_PRINCIPAL_ID,
        Location.Elements.ACTIVE,
        Location.Elements.ID,
        Location.Elements.CREATE_TIME,
        Location.Elements.EFFECTIVE_LOCAL_DATE,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Location
        extends AbstractDataTransferObject
        implements LocationContract
{

    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.HR_LOCATION_ID, required = false)
    private final String hrLocationId;
    @XmlElement(name = Elements.TIMEZONE, required = false)
    private final String timezone;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private Location() {
        this.location = null;
        this.hrLocationId = null;
        this.timezone = null;
        this.description = null;
        this.userPrincipalId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
    }

    private Location(Builder builder) {
        this.location = builder.getLocation();
        this.hrLocationId = builder.getHrLocationId();
        this.timezone = builder.getTimezone();
        this.description = builder.getDescription();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getHrLocationId() {
        return this.hrLocationId;
    }

    @Override
    public String getTimezone() {
        return this.timezone;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
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
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }


    /**
     * A builder which can be used to construct {@link Location} instances.  Enforces the constraints of the {@link LocationContract}.
     *
     */
    public final static class Builder
            implements Serializable, LocationContract, ModelBuilder
    {

        private String location;
        private String hrLocationId;
        private String timezone;
        private String description;
        private String userPrincipalId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;

        private Builder(String location) {
            setLocation(location);
        }

        public static Builder create(String location) {
            return new Builder(location);
        }

        public static Builder create(LocationContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getLocation());
            builder.setHrLocationId(contract.getHrLocationId());
            builder.setTimezone(contract.getTimezone());
            builder.setDescription(contract.getDescription());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            return builder;
        }

        public Location build() {
            return new Location(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getHrLocationId() {
            return this.hrLocationId;
        }

        @Override
        public String getTimezone() {
            return this.timezone;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
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
        public boolean isActive() {
            return this.active;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        public void setLocation(String location) {
            if (StringUtils.isEmpty(location)) {
                throw new IllegalArgumentException("location is blank");
            }
            this.location = location;
        }

        public void setHrLocationId(String hrLocationId) {
            this.hrLocationId = hrLocationId;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            this.effectiveLocalDate = effectiveLocalDate;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "location";
        final static String TYPE_NAME = "LocationType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String LOCATION = "location";
        final static String HR_LOCATION_ID = "hrLocationId";
        final static String TIMEZONE = "timezone";
        final static String DESCRIPTION = "description";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";

    }

}
