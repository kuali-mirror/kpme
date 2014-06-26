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
package org.kuali.kpme.pm.api.positionreportsubcat;

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
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategory;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportSubCategory.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportSubCategory.Constants.TYPE_NAME, propOrder = {
    PositionReportSubCategory.Elements.POSITION_REPORT_TYPE,
    PositionReportSubCategory.Elements.DESCRIPTION,
    PositionReportSubCategory.Elements.POSITION_REPORT_SUB_CAT,
    PositionReportSubCategory.Elements.POSITION_REPORT_CAT,
    PositionReportSubCategory.Elements.PRC_OBJ,
    PositionReportSubCategory.Elements.PM_POSITION_REPORT_SUB_CAT_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportSubCategory.Elements.ACTIVE,
    PositionReportSubCategory.Elements.ID,
    PositionReportSubCategory.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportSubCategory.Elements.CREATE_TIME,
    PositionReportSubCategory.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportSubCategory
    extends AbstractDataTransferObject
    implements PositionReportSubCategoryContract
{

    @XmlElement(name = Elements.POSITION_REPORT_TYPE, required = false)
    private final String positionReportType;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.POSITION_REPORT_SUB_CAT, required = false)
    private final String positionReportSubCat;
    @XmlElement(name = Elements.POSITION_REPORT_CAT, required = false)
    private final String positionReportCat;
    @XmlElement(name = Elements.PRC_OBJ, required = false)
    private final PositionReportCategory prcObj;
    @XmlElement(name = Elements.PM_POSITION_REPORT_SUB_CAT_ID, required = false)
    private final String pmPositionReportSubCatId;
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
    private PositionReportSubCategory() {
        this.positionReportType = null;
        this.description = null;
        this.positionReportSubCat = null;
        this.positionReportCat = null;
        this.prcObj = null;
        this.pmPositionReportSubCatId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private PositionReportSubCategory(Builder builder) {
        this.positionReportType = builder.getPositionReportType();
        this.description = builder.getDescription();
        this.positionReportSubCat = builder.getPositionReportSubCat();
        this.positionReportCat = builder.getPositionReportCat();
        this.prcObj = builder.getPrcObj() == null ? null : builder.getPrcObj().build();
        this.pmPositionReportSubCatId = builder.getPmPositionReportSubCatId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getPositionReportType() {
        return this.positionReportType;
    }

    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public String getPositionReportSubCat() {
        return this.positionReportSubCat;
    }

    @Override
    public String getPositionReportCat() {
        return this.positionReportCat;
    }

    @Override
    public PositionReportCategory getPrcObj() {
        return this.prcObj;
    }

    @Override
    public String getPmPositionReportSubCatId() {
        return this.pmPositionReportSubCatId;
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
     * A builder which can be used to construct {@link PositionReportSubCategory} instances.  Enforces the constraints of the {@link PositionReportSubCategoryContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PositionReportSubCategoryContract, ModelBuilder
    {

        private String positionReportType;
        private String description;
        private String positionReportSubCat;
        private String positionReportCat;
        private PositionReportCategory.Builder prcObj;
        private String pmPositionReportSubCatId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder( String positionReportSubCat, String positionReportCat) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setPositionReportSubCat(positionReportSubCat);
        	setPositionReportCat(positionReportCat);
        }

        public static Builder create( String positionReportSubCat, String positionReportCat) {

            return new Builder( positionReportSubCat, positionReportCat);
        }

        public static Builder create(PositionReportSubCategoryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

            Builder builder = create( contract.getPositionReportSubCat(), contract.getPositionReportCat());
            builder.setPositionReportType(contract.getPositionReportType());
            builder.setDescription(contract.getDescription());
            builder.setPrcObj(contract.getPrcObj() == null ? null : PositionReportCategory.Builder.create(contract.getPrcObj()));
            builder.setPmPositionReportSubCatId(contract.getPmPositionReportSubCatId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PositionReportSubCategory build() {
            return new PositionReportSubCategory(this);
        }

        @Override
        public String getPositionReportType() {
            return this.positionReportType;
        }

        @Override
        public String getDescription() {
            return this.description;
        }


        @Override
        public String getPositionReportSubCat() {
            return this.positionReportSubCat;
        }

        @Override
        public String getPositionReportCat() {
            return this.positionReportCat;
        }

        @Override
        public PositionReportCategory.Builder getPrcObj() {
            return this.prcObj;
        }

        @Override
        public String getPmPositionReportSubCatId() {
            return this.pmPositionReportSubCatId;
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

        public void setPositionReportType(String positionReportType) {

            this.positionReportType = positionReportType;
        }

        public void setDescription(String description) {

            this.description = description;
        }


        public void setPositionReportSubCat(String positionReportSubCat) {

        	if (StringUtils.isWhitespace(positionReportSubCat)) {
                throw new IllegalArgumentException("positionReportSubCat is blank");
            }
            this.positionReportSubCat = positionReportSubCat;
        }

        public void setPositionReportCat(String positionReportCat) {

        	if (StringUtils.isWhitespace(positionReportCat)) {
                throw new IllegalArgumentException("positionReportCat is blank");
            }
            this.positionReportCat = positionReportCat;
        }

        public void setPrcObj(PositionReportCategory.Builder prcObj) {

            this.prcObj = prcObj;
        }

        public void setPmPositionReportSubCatId(String pmPositionReportSubCatId) {

            this.pmPositionReportSubCatId = pmPositionReportSubCatId;
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

        final static String ROOT_ELEMENT_NAME = "positionReportSubCategory";
        final static String TYPE_NAME = "PositionReportSubCategoryType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String POSITION_REPORT_TYPE = "positionReportType";
        final static String DESCRIPTION = "description";
        final static String POSITION_REPORT_SUB_CAT = "positionReportSubCat";
        final static String POSITION_REPORT_CAT = "positionReportCat";
        final static String PRC_OBJ = "prcObj";
        final static String PM_POSITION_REPORT_SUB_CAT_ID = "pmPositionReportSubCatId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

