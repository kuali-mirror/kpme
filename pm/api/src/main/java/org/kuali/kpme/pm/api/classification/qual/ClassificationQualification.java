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
package org.kuali.kpme.pm.api.classification.qual;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.classification.Classification;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = ClassificationQualification.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ClassificationQualification.Constants.TYPE_NAME, propOrder = {
    ClassificationQualification.Elements.QUALIFICATION_VALUE,
    ClassificationQualification.Elements.QUALIFICATION_TYPE,
    ClassificationQualification.Elements.DISPLAY_ORDER,
    ClassificationQualification.Elements.TYPE_VALUE,
    ClassificationQualification.Elements.QUALIFIER,
    ClassificationQualification.Elements.PM_CLASSIFICATION_QUALIFICATION_ID,
    ClassificationQualification.Elements.PM_POSITION_CLASS_ID,
    ClassificationQualification.Elements.OWNER,
    ClassificationQualification.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class ClassificationQualification extends AbstractDataTransferObject implements ClassificationQualificationContract {

	private static final long serialVersionUID = -204740505016136600L;
	
	@XmlElement(name = Elements.QUALIFICATION_VALUE, required = false)
    private final String qualificationValue;
    @XmlElement(name = Elements.QUALIFICATION_TYPE, required = false)
    private final String qualificationType;
    @XmlElement(name = Elements.DISPLAY_ORDER, required = false)
    private final String displayOrder;
    @XmlElement(name = Elements.TYPE_VALUE, required = false)
    private final String typeValue;
    @XmlElement(name = Elements.QUALIFIER, required = false)
    private final String qualifier;
    @XmlElement(name = Elements.PM_CLASSIFICATION_QUALIFICATION_ID, required = false)
    private final String pmClassificationQualificationId;
    @XmlElement(name = Elements.PM_POSITION_CLASS_ID, required = false)
    private final String pmPositionClassId;
    @XmlElement(name = Elements.OWNER, required = false)
    private final Classification owner;
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
    private ClassificationQualification() {
        this.qualificationValue = null;
        this.qualificationType = null;
        this.displayOrder = null;
        this.typeValue = null;
        this.qualifier = null;
        this.pmClassificationQualificationId = null;
        this.pmPositionClassId = null;
        this.owner = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private ClassificationQualification(Builder builder) {
        this.qualificationValue = builder.getQualificationValue();
        this.qualificationType = builder.getQualificationType();
        this.displayOrder = builder.getDisplayOrder();
        this.typeValue = builder.getTypeValue();
        this.qualifier = builder.getQualifier();
        this.pmClassificationQualificationId = builder.getPmClassificationQualificationId();
        this.pmPositionClassId = builder.getPmPositionClassId();
        this.owner = builder.getOwner() == null ? null : builder.getOwner().build();
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
    public String getDisplayOrder() {
        return this.displayOrder;
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
    public String getPmClassificationQualificationId() {
        return this.pmClassificationQualificationId;
    }

    @Override
    public String getPmPositionClassId() {
        return this.pmPositionClassId;
    }

    @Override
    public Classification getOwner() {
        return this.owner;
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
     * A builder which can be used to construct {@link ClassificationQualification} instances.  Enforces the constraints of the {@link ClassificationQualificationContract}.
     * 
     */
    public final static class Builder implements Serializable, ClassificationQualificationContract, ModelBuilder {

    	private static final long serialVersionUID = -5703146256835808803L;
    	
		private String qualificationValue;
        private String qualificationType;
        private String displayOrder;
        private String typeValue;
        private String qualifier;
        private String pmClassificationQualificationId;
        private String pmPositionClassId;
        private Classification.Builder owner;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(ClassificationQualificationContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setQualificationValue(contract.getQualificationValue());
            builder.setQualificationType(contract.getQualificationType());
            builder.setDisplayOrder(contract.getDisplayOrder());
            builder.setTypeValue(contract.getTypeValue());
            builder.setQualifier(contract.getQualifier());
            builder.setPmClassificationQualificationId(contract.getPmClassificationQualificationId());
            builder.setPmPositionClassId(contract.getPmPositionClassId());
            builder.setOwner(contract.getOwner() == null ? null : Classification.Builder.create(contract.getOwner()));
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public ClassificationQualification build() {
            return new ClassificationQualification(this);
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
        public String getDisplayOrder() {
            return this.displayOrder;
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
        public String getPmClassificationQualificationId() {
            return this.pmClassificationQualificationId;
        }

        @Override
        public String getPmPositionClassId() {
            return this.pmPositionClassId;
        }

        @Override
        public Classification.Builder getOwner() {
            return this.owner;
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
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.qualificationValue = qualificationValue;
        }

        public void setQualificationType(String qualificationType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.qualificationType = qualificationType;
        }

        public void setDisplayOrder(String displayOrder) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.displayOrder = displayOrder;
        }

        public void setTypeValue(String typeValue) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.typeValue = typeValue;
        }

        public void setQualifier(String qualifier) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.qualifier = qualifier;
        }

        public void setPmClassificationQualificationId(String pmClassificationQualificationId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmClassificationQualificationId = pmClassificationQualificationId;
        }

        public void setPmPositionClassId(String pmPositionClassId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionClassId = pmPositionClassId;
        }

        public void setOwner(Classification.Builder owner) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.owner = owner;
        }

        public void setEffectiveLocalDateOfOwner(LocalDate effectiveLocalDateOfOwner) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDateOfOwner = effectiveLocalDateOfOwner;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "classificationQualification";
        final static String TYPE_NAME = "ClassificationQualificationType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String QUALIFICATION_VALUE = "qualificationValue";
        final static String QUALIFICATION_TYPE = "qualificationType";
        final static String DISPLAY_ORDER = "displayOrder";
        final static String TYPE_VALUE = "typeValue";
        final static String QUALIFIER = "qualifier";
        final static String PM_CLASSIFICATION_QUALIFICATION_ID = "pmClassificationQualificationId";
        final static String PM_POSITION_CLASS_ID = "pmPositionClassId";
        final static String OWNER = "owner";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}