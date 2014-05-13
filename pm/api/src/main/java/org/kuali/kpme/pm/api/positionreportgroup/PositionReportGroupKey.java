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
package org.kuali.kpme.pm.api.positionreportgroup;

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

@XmlRootElement(name = PositionReportGroupKey.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportGroupKey.Constants.TYPE_NAME, propOrder = {
    PositionReportGroupKey.Elements.OWNER,
    PositionReportGroupKey.Elements.POSITION_REPORT_GROUP_KEY_ID,
    PositionReportGroupKey.Elements.PM_POSITION_REPORT_GROUP_ID,
    PositionReportGroupKey.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportGroupKey.Elements.GROUP_KEY,
    PositionReportGroupKey.Elements.GROUP_KEY_CODE,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportGroupKey extends AbstractDataTransferObject implements PositionReportGroupKeyContract {

	private static final long serialVersionUID = 4765631151966354214L;
	@XmlElement(name = Elements.OWNER, required = false)
    private final PositionReportGroup owner;
    @XmlElement(name = Elements.POSITION_REPORT_GROUP_KEY_ID, required = false)
    private final String positionReportGroupKeyId;
    @XmlElement(name = Elements.PM_POSITION_REPORT_GROUP_ID, required = false)
    private final String pmPositionReportGroupId;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER, required = false)
    private final LocalDate effectiveLocalDateOfOwner;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKey groupKey;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionReportGroupKey() {
        this.owner = null;
        this.positionReportGroupKeyId = null;
        this.pmPositionReportGroupId = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
        this.groupKey = null;
        this.groupKeyCode = null;
    }

    private PositionReportGroupKey(Builder builder) {
    	// get the immutable owner from the builder (this should have been set by the owner's constructor before)
    	this.owner = builder.getOwner();
    	
        this.positionReportGroupKeyId = builder.getPositionReportGroupKeyId();
        this.pmPositionReportGroupId = builder.getPmPositionReportGroupId();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
        this.groupKeyCode = builder.getGroupKeyCode();
        
    }

    @Override
    public PositionReportGroup getOwner() {
        return this.owner;
    }

    @Override
    public String getPositionReportGroupKeyId() {
        return this.positionReportGroupKeyId;
    }

    @Override
    public String getPmPositionReportGroupId() {
        return this.pmPositionReportGroupId;
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
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }


    /**
     * A builder which can be used to construct {@link PositionReportGroupKey} instances.  Enforces the constraints of the {@link PositionReportGroupKeyContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionReportGroupKeyContract, ModelBuilder {

		private static final long serialVersionUID = 5953020367993221043L;
		
		private PositionReportGroup owner;

        private String positionReportGroupKeyId;
        private String pmPositionReportGroupId;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;
        private HrGroupKey.Builder groupKey;
        private String groupKeyCode;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(PositionReportGroupKeyContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
           
            builder.setPositionReportGroupKeyId(contract.getPositionReportGroupKeyId());
            builder.setPmPositionReportGroupId(contract.getPmPositionReportGroupId());
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            return builder;
        }

        public PositionReportGroupKey build() {
            return new PositionReportGroupKey(this);
        }

        @Override
        public String getPositionReportGroupKeyId() {
            return this.positionReportGroupKeyId;
        }

        @Override
        public String getPmPositionReportGroupId() {
            return this.pmPositionReportGroupId;
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
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }
        
        public PositionReportGroup getOwner() {
			return owner;
		}


        public void setPositionReportGroupKeyId(String positionReportGroupKeyId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.positionReportGroupKeyId = positionReportGroupKeyId;
        }

        public void setPmPositionReportGroupId(String pmPositionReportGroupId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionReportGroupId = pmPositionReportGroupId;
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

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKey = groupKey;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeyCode = groupKeyCode;
        }

		public void setOwner(PositionReportGroup owner) {
			this.owner = owner;
		}

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "positionReportGroupKey";
        final static String TYPE_NAME = "PositionReportGroupKeyType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String OWNER = "owner";
        final static String POSITION_REPORT_GROUP_KEY_ID = "positionReportGroupKeyId";
        final static String PM_POSITION_REPORT_GROUP_ID = "pmPositionReportGroupId";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";
        final static String GROUP_KEY = "groupKey";
        final static String GROUP_KEY_CODE = "groupKeyCode";

    }

}