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
package org.kuali.kpme.pm.api.positionappointment;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionAppointment.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionAppointment.Constants.TYPE_NAME, propOrder = {
    PositionAppointment.Elements.POSITION_APPOINTMENT,
    PositionAppointment.Elements.DESCRIPTION,
    PositionAppointment.Elements.PM_POSITION_APPOINTMENT_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionAppointment.Elements.ACTIVE,
    PositionAppointment.Elements.ID,
    PositionAppointment.Elements.EFFECTIVE_LOCAL_DATE,
    PositionAppointment.Elements.CREATE_TIME,
    PositionAppointment.Elements.USER_PRINCIPAL_ID,
    PositionAppointment.Elements.GROUP_KEY_CODE,
    PositionAppointment.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionAppointment
    extends AbstractDataTransferObject
    implements PositionAppointmentContract
{

    @XmlElement(name = Elements.POSITION_APPOINTMENT, required = false)
    private final String positionAppointment;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.PM_POSITION_APPOINTMENT_ID, required = false)
    private final String pmPositionAppointmentId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = true)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = true)
    private final HrGroupKey groupKey;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionAppointment() {
        this.positionAppointment = null;
        this.description = null;
        this.pmPositionAppointmentId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }

    private PositionAppointment(Builder builder) {
        this.positionAppointment = builder.getPositionAppointment();
        this.description = builder.getDescription();
        this.pmPositionAppointmentId = builder.getPmPositionAppointmentId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
        this.groupKeyCode = builder.getGroupKeyCode();
    }

    @Override
    public String getPositionAppointment() {
        return this.positionAppointment;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPmPositionAppointmentId() {
        return this.pmPositionAppointmentId;
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
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }


    /**
     * A builder which can be used to construct {@link PositionAppointment} instances.  Enforces the constraints of the {@link PositionAppointmentContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionAppointmentContract, ModelBuilder
    {

        private String positionAppointment;
        private String description;
        private String pmPositionAppointmentId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder(String groupKeyCode, String positionAppointment) {

        	setGroupKeyCode(groupKeyCode);
        	setPositionAppointment(positionAppointment);
        }

        public static Builder create(String groupKeyCode, String positionAppointment) {

            return new Builder(groupKeyCode,positionAppointment);
        }

        public static Builder create(PositionAppointmentContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create(contract.getGroupKeyCode(),contract.getPositionAppointment());
            builder.setPositionAppointment(contract.getPositionAppointment());
            builder.setDescription(contract.getDescription());
            builder.setPmPositionAppointmentId(contract.getPmPositionAppointmentId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public PositionAppointment build() {
            return new PositionAppointment(this);
        }

        @Override
        public String getPositionAppointment() {
            return this.positionAppointment;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getPmPositionAppointmentId() {
            return this.pmPositionAppointmentId;
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
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }
        
        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        public void setPositionAppointment(String positionAppointment) {

        	if (StringUtils.isWhitespace(positionAppointment)) {
                throw new IllegalArgumentException("positionAppointment is blank");
            }
            this.positionAppointment = positionAppointment;
        }

        public void setDescription(String description) {

            this.description = description;
        }

        public void setPmPositionAppointmentId(String pmPositionAppointmentId) {

            this.pmPositionAppointmentId = pmPositionAppointmentId;
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

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {

            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {

            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {

            this.userPrincipalId = userPrincipalId;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            if (StringUtils.isWhitespace(groupKeyCode)) {
                throw new IllegalArgumentException("groupKeyCode is blank");
            }
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            this.groupKey = groupKey;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "positionAppointment";
        final static String TYPE_NAME = "PositionAppointmentType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String POSITION_APPOINTMENT = "positionAppointment";
        final static String DESCRIPTION = "description";
        final static String PM_POSITION_APPOINTMENT_ID = "pmPositionAppointmentId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}