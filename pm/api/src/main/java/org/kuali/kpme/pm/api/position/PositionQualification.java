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

@XmlRootElement(name = PositionQualification.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionQualification.Constants.TYPE_NAME, propOrder = {
    PositionQualification.Elements.QUALIFICATION_VALUE,
    PositionQualification.Elements.QUALIFICATION_TYPE,
    PositionQualification.Elements.PM_QUALIFICATION_ID,
    PositionQualification.Elements.TYPE_VALUE,
    PositionQualification.Elements.QUALIFIER,
    PositionQualification.Elements.HR_POSITION_ID,
    PositionQualification.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionQualification extends AbstractDataTransferObject implements PositionQualificationContract {

	private static final long serialVersionUID = -5011017970608326168L;
	
	@XmlElement(name = Elements.QUALIFICATION_VALUE, required = false)
    private final String qualificationValue;
    @XmlElement(name = Elements.QUALIFICATION_TYPE, required = false)
    private final String qualificationType;
    @XmlElement(name = Elements.PM_QUALIFICATION_ID, required = false)
    private final String pmQualificationId;
    @XmlElement(name = Elements.TYPE_VALUE, required = false)
    private final String typeValue;
    @XmlElement(name = Elements.QUALIFIER, required = false)
    private final String qualifier;
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
    private PositionQualification() {
        this.qualificationValue = null;
        this.qualificationType = null;
        this.pmQualificationId = null;
        this.typeValue = null;
        this.qualifier = null;
        this.hrPositionId = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private PositionQualification(Builder builder) {
        this.qualificationValue = builder.getQualificationValue();
        this.qualificationType = builder.getQualificationType();
        this.pmQualificationId = builder.getPmQualificationId();
        this.typeValue = builder.getTypeValue();
        this.qualifier = builder.getQualifier();
        this.hrPositionId = builder.getHrPositionId();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getQualificationValue() {
        return this.qualificationValue;
    }

    @Override
    public String getQualificationType() {
        return this.qualificationType;
    }

    @Override
    public String getPmQualificationId() {
        return this.pmQualificationId;
    }

    @Override
    public String getTypeValue() {
        return this.typeValue;
    }

    @Override
    public String getQualifier() {
        return this.qualifier;
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
     * A builder which can be used to construct {@link PositionQualification} instances.  Enforces the constraints of the {@link PositionQualificationContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionQualificationContract, ModelBuilder {

		private static final long serialVersionUID = 1660521488150811103L;
		
		private String qualificationValue;
        private String qualificationType;
        private String pmQualificationId;
        private String typeValue;
        private String qualifier;
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

        public static Builder create(PositionQualificationContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create();
            builder.setQualificationValue(contract.getQualificationValue());
            builder.setQualificationType(contract.getQualificationType());
            builder.setPmQualificationId(contract.getPmQualificationId());
            builder.setTypeValue(contract.getTypeValue());
            builder.setQualifier(contract.getQualifier());
            builder.setHrPositionId(contract.getHrPositionId());
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public PositionQualification build() {
            return new PositionQualification(this);
        }

        @Override
        public String getQualificationValue() {
            return this.qualificationValue;
        }

        @Override
        public String getQualificationType() {
            return this.qualificationType;
        }

        @Override
        public String getPmQualificationId() {
            return this.pmQualificationId;
        }

        @Override
        public String getTypeValue() {
            return this.typeValue;
        }

        @Override
        public String getQualifier() {
            return this.qualifier;
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

        public void setQualificationValue(String qualificationValue) {

            this.qualificationValue = qualificationValue;
        }

        public void setQualificationType(String qualificationType) {

            this.qualificationType = qualificationType;
        }

        public void setPmQualificationId(String pmQualificationId) {

            this.pmQualificationId = pmQualificationId;
        }

        public void setTypeValue(String typeValue) {

            this.typeValue = typeValue;
        }

        public void setQualifier(String qualifier) {

            this.qualifier = qualifier;
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

        final static String ROOT_ELEMENT_NAME = "positionQualification";
        final static String TYPE_NAME = "PositionQualificationType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String QUALIFICATION_VALUE = "qualificationValue";
        final static String QUALIFICATION_TYPE = "qualificationType";
        final static String PM_QUALIFICATION_ID = "pmQualificationId";
        final static String TYPE_VALUE = "typeValue";
        final static String QUALIFIER = "qualifier";
        final static String HR_POSITION_ID = "hrPositionId";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}

