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
package org.kuali.kpme.pm.api.positionflag;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionFlag.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionFlag.Constants.TYPE_NAME, propOrder = {
    PositionFlag.Elements.POSITION_FLAG_NAME,
    PositionFlag.Elements.PM_POSITION_FLAG_ID,
    PositionFlag.Elements.CATEGORY,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionFlag.Elements.ACTIVE,
    PositionFlag.Elements.ID,
    PositionFlag.Elements.EFFECTIVE_LOCAL_DATE,
    PositionFlag.Elements.CREATE_TIME,
    PositionFlag.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionFlag
    extends AbstractDataTransferObject
    implements PositionFlagContract
{

    @XmlElement(name = Elements.POSITION_FLAG_NAME, required = false)
    private final String positionFlagName;
    @XmlElement(name = Elements.PM_POSITION_FLAG_ID, required = false)
    private final String pmPositionFlagId;
    @XmlElement(name = Elements.CATEGORY, required = false)
    private final String category;
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
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionFlag() {
        this.positionFlagName = null;
        this.pmPositionFlagId = null;
        this.category = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private PositionFlag(Builder builder) {
        this.positionFlagName = builder.getPositionFlagName();
        this.pmPositionFlagId = builder.getPmPositionFlagId();
        this.category = builder.getCategory();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getPositionFlagName() {
        return this.positionFlagName;
    }

    @Override
    public String getPmPositionFlagId() {
        return this.pmPositionFlagId;
    }

    @Override
    public String getCategory() {
        return this.category;
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


    /**
     * A builder which can be used to construct {@link PositionFlag} instances.  Enforces the constraints of the {@link PositionFlagContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionFlagContract, ModelBuilder
    {

        private String positionFlagName;
        private String pmPositionFlagId;
        private String category;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {

            return new Builder();
        }

        public static Builder create(PositionFlagContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create();
            builder.setPositionFlagName(contract.getPositionFlagName());
            builder.setPmPositionFlagId(contract.getPmPositionFlagId());
            builder.setCategory(contract.getCategory());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PositionFlag build() {
            return new PositionFlag(this);
        }

        @Override
        public String getPositionFlagName() {
            return this.positionFlagName;
        }

        @Override
        public String getPmPositionFlagId() {
            return this.pmPositionFlagId;
        }

        @Override
        public String getCategory() {
            return this.category;
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

        public void setPositionFlagName(String positionFlagName) {

            this.positionFlagName = positionFlagName;
        }

        public void setPmPositionFlagId(String pmPositionFlagId) {

            this.pmPositionFlagId = pmPositionFlagId;
        }

        public void setCategory(String category) {

            this.category = category;
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

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "positionFlag";
        final static String TYPE_NAME = "PositionFlagType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String POSITION_FLAG_NAME = "positionFlagName";
        final static String PM_POSITION_FLAG_ID = "pmPositionFlagId";
        final static String CATEGORY = "category";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

