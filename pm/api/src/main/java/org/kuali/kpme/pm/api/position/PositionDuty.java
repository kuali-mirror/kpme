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
package org.kuali.kpme.pm.api.position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionDuty.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionDuty.Constants.TYPE_NAME, propOrder = {
    PositionDuty.Elements.NAME,
    PositionDuty.Elements.DESCRIPTION,
    PositionDuty.Elements.PERCENTAGE,
    PositionDuty.Elements.PM_DUTY_ID,
    PositionDuty.Elements.HR_POSITION_ID,
    PositionDuty.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionDuty extends AbstractDataTransferObject implements PositionDutyContract {

	private static final long serialVersionUID = -5750874869253559360L;
	
	@XmlElement(name = Elements.NAME, required = false)
    private final String name;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.PERCENTAGE, required = false)
    private final BigDecimal percentage;
    @XmlElement(name = Elements.PM_DUTY_ID, required = false)
    private final String pmDutyId;
    @XmlElement(name = Elements.HR_POSITION_ID, required = false)
    private final String hrPositionId;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER, required = false)
    private final LocalDate effectiveLocalDateOfOwner;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionDuty() {
        this.name = null;
        this.description = null;
        this.percentage = null;
        this.pmDutyId = null;
        this.hrPositionId = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private PositionDuty(Builder builder) {
        this.name = builder.getName();
        this.description = builder.getDescription();
        this.percentage = builder.getPercentage();
        this.pmDutyId = builder.getPmDutyId();
        this.hrPositionId = builder.getHrPositionId();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public BigDecimal getPercentage() {
        return this.percentage;
    }

    @Override
    public String getPmDutyId() {
        return this.pmDutyId;
    }
    
    @Override
    public String getHrPositionId() {
        return this.hrPositionId;
    }

    @Override
    public LocalDate getEffectiveLocalDateOfOwner() {
        return this.effectiveLocalDateOfOwner;
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
     * A builder which can be used to construct {@link PositionDuty} instances.  Enforces the constraints of the {@link PositionDutyContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionDutyContract, ModelBuilder {

		private static final long serialVersionUID = -529812836523277483L;
		private String name;
        private String description;
        private BigDecimal percentage;
        private String pmDutyId;
        private String hrPositionId;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {

            return new Builder();
        }

        public static Builder create(PositionDutyContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create();
            builder.setName(contract.getName());
            builder.setDescription(contract.getDescription());
            builder.setPercentage(contract.getPercentage());
            builder.setPmDutyId(contract.getPmDutyId());
            builder.setHrPositionId(contract.getHrPositionId());
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public PositionDuty build() {
            return new PositionDuty(this);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public BigDecimal getPercentage() {
            return this.percentage;
        }

        @Override
        public String getPmDutyId() {
            return this.pmDutyId;
        }
        

        @Override
        public String getHrPositionId() {
            return this.hrPositionId;
        }

        @Override
        public LocalDate getEffectiveLocalDateOfOwner() {
            return this.effectiveLocalDateOfOwner;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setName(String name) {

            this.name = name;
        }

        public void setDescription(String description) {

            this.description = description;
        }

        public void setPercentage(BigDecimal percentage) {

            this.percentage = percentage;
        }

        public void setPmDutyId(String pmDutyId) {

            this.pmDutyId = pmDutyId;
        }
        
        public void setHrPositionId(String hrPositionId) {

            this.hrPositionId = hrPositionId;
        }

        public void setEffectiveLocalDateOfOwner(LocalDate effectiveLocalDateOfOwner) {

            this.effectiveLocalDateOfOwner = effectiveLocalDateOfOwner;
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

        final static String ROOT_ELEMENT_NAME = "positionDuty";
        final static String TYPE_NAME = "PositionDutyType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String NAME = "name";
        final static String DESCRIPTION = "description";
        final static String PERCENTAGE = "percentage";
        final static String PM_DUTY_ID = "pmDutyId";
        final static String HR_POSITION_ID = "hrPositionId";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}

