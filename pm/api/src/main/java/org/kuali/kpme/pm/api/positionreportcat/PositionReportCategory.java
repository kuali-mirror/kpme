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
package org.kuali.kpme.pm.api.positionreportcat;

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
import org.kuali.kpme.pm.api.positionreporttype.PositionReportType;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportCategory.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportCategory.Constants.TYPE_NAME, propOrder = {
    PositionReportCategory.Elements.POSITION_REPORT_CAT,
    PositionReportCategory.Elements.DESCRIPTION,
    PositionReportCategory.Elements.PM_POSITION_REPORT_CAT_ID,
    PositionReportCategory.Elements.PRT_OBJ,
    PositionReportCategory.Elements.POSITION_REPORT_TYPE,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportCategory.Elements.ACTIVE,
    PositionReportCategory.Elements.ID,
    PositionReportCategory.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportCategory.Elements.CREATE_TIME,
    PositionReportCategory.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportCategory
    extends AbstractDataTransferObject
    implements PositionReportCategoryContract
{

    @XmlElement(name = Elements.POSITION_REPORT_CAT, required = false)
    private final String positionReportCat;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.PM_POSITION_REPORT_CAT_ID, required = false)
    private final String pmPositionReportCatId;
    @XmlElement(name = Elements.PRT_OBJ, required = false)
    private final PositionReportType prtObj;
    @XmlElement(name = Elements.POSITION_REPORT_TYPE, required = false)
    private final String positionReportType;
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
    private PositionReportCategory() {
        this.positionReportCat = null;
        this.description = null;
        this.pmPositionReportCatId = null;
        this.prtObj = null;
        this.positionReportType = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        
    }

    private PositionReportCategory(Builder builder) {
        this.positionReportCat = builder.getPositionReportCat();
        this.description = builder.getDescription();
        this.pmPositionReportCatId = builder.getPmPositionReportCatId();
        this.prtObj = builder.getPrtObj() == null ? null : builder.getPrtObj().build();
        this.positionReportType = builder.getPositionReportType();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getPositionReportCat() {
        return this.positionReportCat;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getPmPositionReportCatId() {
        return this.pmPositionReportCatId;
    }

    @Override
    public PositionReportType getPrtObj() {
        return this.prtObj;
    }

    @Override
    public String getPositionReportType() {
        return this.positionReportType;
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
     * A builder which can be used to construct {@link PositionReportCategory} instances.  Enforces the constraints of the {@link PositionReportCategoryContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionReportCategoryContract, ModelBuilder
    {

        private String positionReportCat;
        private String description;
        private String pmPositionReportCatId;
        private PositionReportType.Builder prtObj;
        private String positionReportType;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder( String positionReportCat, String positionReportType) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setPositionReportCat(positionReportCat);
        	setPositionReportType(positionReportType);
        }

        public static Builder create( String positionReportCat, String positionReportType) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(positionReportCat,positionReportType);
        }

        public static Builder create(PositionReportCategoryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getPositionReportCat(),contract.getPositionReportType());
            builder.setDescription(contract.getDescription());
            builder.setPmPositionReportCatId(contract.getPmPositionReportCatId());
            builder.setPrtObj(contract.getPrtObj() == null ? null : PositionReportType.Builder.create(contract.getPrtObj()));
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PositionReportCategory build() {
            return new PositionReportCategory(this);
        }

        @Override
        public String getPositionReportCat() {
            return this.positionReportCat;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getPmPositionReportCatId() {
            return this.pmPositionReportCatId;
        }

        @Override
        public PositionReportType.Builder getPrtObj() {
            return this.prtObj;
        }

        @Override
        public String getPositionReportType() {
            return this.positionReportType;
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

        public void setPositionReportCat(String positionReportCat) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
        	if (StringUtils.isWhitespace(positionReportCat)) {
                throw new IllegalArgumentException("positionReportCat is blank");
            }
            this.positionReportCat = positionReportCat;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setPmPositionReportCatId(String pmPositionReportCatId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionReportCatId = pmPositionReportCatId;
        }

        public void setPrtObj(PositionReportType.Builder prtObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.prtObj = prtObj;
        }

        public void setPositionReportType(String positionReportType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
        	if (StringUtils.isWhitespace(positionReportType)) {
                throw new IllegalArgumentException("positionReportType is blank");
            }
            this.positionReportType = positionReportType;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.active = active;
        }

        public void setId(String id) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "positionReportCategory";
        final static String TYPE_NAME = "PositionReportCategoryType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String POSITION_REPORT_CAT = "positionReportCat";
        final static String DESCRIPTION = "description";
        final static String PM_POSITION_REPORT_CAT_ID = "pmPositionReportCatId";
        final static String PRT_OBJ = "prtObj";
        final static String POSITION_REPORT_TYPE = "positionReportType";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

