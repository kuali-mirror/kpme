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
package org.kuali.kpme.core.api.mo;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EffectiveKey.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EffectiveKey.Constants.TYPE_NAME, propOrder = {
    EffectiveKey.Elements.ID,
    EffectiveKey.Elements.OWNER_ID,
    EffectiveKey.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EffectiveKey.Elements.GROUP_KEY_CODE,
    EffectiveKey.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EffectiveKey extends AbstractDataTransferObject implements EffectiveKeyContract {

	private static final long serialVersionUID = -1424727410760335085L;
	
	@XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.OWNER_ID, required = false)
    private final String ownerId;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER, required = false)
    private final LocalDate effectiveLocalDateOfOwner;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKey groupKey;
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EffectiveKey() {
        this.id = null;
        this.ownerId = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }

    private EffectiveKey(Builder builder) {
        this.id = builder.getId();
        this.ownerId = builder.getOwnerId();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getOwnerId() {
        return this.ownerId;
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

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }

    @Override
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }


    /**
     * A builder which can be used to construct {@link EffectiveKey} instances.  Enforces the constraints of the {@link EffectiveKeyContract}.
     * 
     */
    public final static class Builder implements Serializable, EffectiveKeyContract, ModelBuilder {

		private static final long serialVersionUID = -5315864638775959006L;

		private String id;
        private String ownerId;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EffectiveKeyContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setId(contract.getId());
            builder.setOwnerId(contract.getOwnerId());
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));	
            return builder;
        }

        public EffectiveKey build() {
            return new EffectiveKey(this);
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public String getOwnerId() {
            return this.ownerId;
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

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        public void setId(String id) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.id = id;
        }

        public void setOwnerId(String ownerId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.ownerId = ownerId;
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

        public void setGroupKeyCode(String groupKeyCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKey = groupKey;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "effectiveKey";
        final static String TYPE_NAME = "EffectiveKeyType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String ID = "id";
        final static String OWNER_ID = "ownerId";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}

