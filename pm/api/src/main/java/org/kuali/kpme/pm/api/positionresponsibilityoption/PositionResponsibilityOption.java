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
package org.kuali.kpme.pm.api.positionresponsibilityoption;

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

@XmlRootElement(name = PositionResponsibilityOption.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionResponsibilityOption.Constants.TYPE_NAME, propOrder = {
    PositionResponsibilityOption.Elements.PR_DESCRIPTION,
    PositionResponsibilityOption.Elements.PR_OPTION_ID,
    PositionResponsibilityOption.Elements.PR_OPTION_NAME,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionResponsibilityOption.Elements.ACTIVE,
    PositionResponsibilityOption.Elements.ID,
    PositionResponsibilityOption.Elements.EFFECTIVE_LOCAL_DATE,
    PositionResponsibilityOption.Elements.CREATE_TIME,
    PositionResponsibilityOption.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionResponsibilityOption
    extends AbstractDataTransferObject
    implements PositionResponsibilityOptionContract
{

    @XmlElement(name = Elements.PR_DESCRIPTION, required = false)
    private final String prDescription;
    @XmlElement(name = Elements.PR_OPTION_ID, required = false)
    private final String prOptionId;
    @XmlElement(name = Elements.PR_OPTION_NAME, required = false)
    private final String prOptionName;
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
    private PositionResponsibilityOption() {
        this.prDescription = null;
        this.prOptionId = null;
        this.prOptionName = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private PositionResponsibilityOption(Builder builder) {
        this.prDescription = builder.getPrDescription();
        this.prOptionId = builder.getPrOptionId();
        this.prOptionName = builder.getPrOptionName();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getPrDescription() {
        return this.prDescription;
    }

    @Override
    public String getPrOptionId() {
        return this.prOptionId;
    }

    @Override
    public String getPrOptionName() {
        return this.prOptionName;
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
     * A builder which can be used to construct {@link PositionResponsibilityOption} instances.  Enforces the constraints of the {@link PositionResponsibilityOptionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionResponsibilityOptionContract, ModelBuilder
    {

        private String prDescription;
        private String prOptionId;
        private String prOptionName;
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

        public static Builder create(PositionResponsibilityOptionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create();
            builder.setPrDescription(contract.getPrDescription());
            builder.setPrOptionId(contract.getPrOptionId());
            builder.setPrOptionName(contract.getPrOptionName());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PositionResponsibilityOption build() {
            return new PositionResponsibilityOption(this);
        }

        @Override
        public String getPrDescription() {
            return this.prDescription;
        }

        @Override
        public String getPrOptionId() {
            return this.prOptionId;
        }

        @Override
        public String getPrOptionName() {
            return this.prOptionName;
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

        public void setPrDescription(String prDescription) {

            this.prDescription = prDescription;
        }

        public void setPrOptionId(String prOptionId) {

            this.prOptionId = prOptionId;
        }

        public void setPrOptionName(String prOptionName) {

            this.prOptionName = prOptionName;
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

        final static String ROOT_ELEMENT_NAME = "positionResponsibilityOption";
        final static String TYPE_NAME = "PositionResponsibilityOptionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String PR_DESCRIPTION = "prDescription";
        final static String PR_OPTION_ID = "prOptionId";
        final static String PR_OPTION_NAME = "prOptionName";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

