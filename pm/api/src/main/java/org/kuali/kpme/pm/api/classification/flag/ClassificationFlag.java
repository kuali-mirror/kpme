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
package org.kuali.kpme.pm.api.classification.flag;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.*;

import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = ClassificationFlag.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ClassificationFlag.Constants.TYPE_NAME, propOrder = {
    ClassificationFlag.Elements.CATEGORY,
    ClassificationFlag.Elements.NAMES,
    ClassificationFlag.Elements.PM_FLAG_ID,
    ClassificationFlag.Elements.PM_POSITION_CLASS_ID,
    ClassificationFlag.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class ClassificationFlag extends AbstractDataTransferObject implements ClassificationFlagContract {

	private static final long serialVersionUID = -8862715227046801613L;
	
	@XmlElement(name = Elements.CATEGORY, required = false)
    private final String category;
    @XmlElementWrapper(name = Elements.NAMES, required = false)
    @XmlElement(name = Elements.NAME, required = false)
    private final List<String> names;
    @XmlElement(name = Elements.PM_FLAG_ID, required = false)
    private final String pmFlagId;
    @XmlElement(name = Elements.PM_POSITION_CLASS_ID, required = false)
    private final String pmPositionClassId;
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
    private ClassificationFlag() {
        this.category = null;
        this.names = null;
        this.pmFlagId = null;
        this.pmPositionClassId = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private ClassificationFlag(Builder builder) {
        this.category = builder.getCategory();
        this.names = builder.getNames() == null ? Collections.<String>emptyList() : Collections.unmodifiableList(builder.getNames());
        this.pmFlagId = builder.getPmFlagId();
        this.pmPositionClassId = builder.getPmPositionClassId();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public List<String> getNames() {
        return this.names;
    }

    @Override
    public String getPmFlagId() {
        return this.pmFlagId;
    }

    @Override
    public String getPmPositionClassId() {
        return this.pmPositionClassId;
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
     * A builder which can be used to construct {@link ClassificationFlag} instances.  Enforces the constraints of the {@link ClassificationFlagContract}.
     * 
     */
    public final static class Builder implements Serializable, ClassificationFlagContract, ModelBuilder {

    	private static final long serialVersionUID = -852663368623881717L;
		
    	private String category;
        private List<String> names;
        private String pmFlagId;
        private String pmPositionClassId;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {

            return new Builder();
        }

        public static Builder create(ClassificationFlagContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create();
            builder.setCategory(contract.getCategory());
            builder.setNames(contract.getNames());
            builder.setPmFlagId(contract.getPmFlagId());
            builder.setPmPositionClassId(contract.getPmPositionClassId());
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public ClassificationFlag build() {
            return new ClassificationFlag(this);
        }

        @Override
        public String getCategory() {
            return this.category;
        }

        @Override
        public List<String> getNames() {
            return this.names;
        }

        @Override
        public String getPmFlagId() {
            return this.pmFlagId;
        }

        @Override
        public String getPmPositionClassId() {
            return this.pmPositionClassId;
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

        public void setCategory(String category) {

            this.category = category;
        }

        public void setNames(List<String> names) {

            this.names = names;
        }

        public void setPmFlagId(String pmFlagId) {

            this.pmFlagId = pmFlagId;
        }

        public void setPmPositionClassId(String pmPositionClassId) {

            this.pmPositionClassId = pmPositionClassId;
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

        final static String ROOT_ELEMENT_NAME = "classificationFlag";
        final static String TYPE_NAME = "ClassificationFlagType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String CATEGORY = "category";
        final static String NAMES = "names";
        final static String NAME = "name";
        final static String PM_FLAG_ID = "pmFlagId";
        final static String PM_POSITION_CLASS_ID = "pmPositionClassId";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}