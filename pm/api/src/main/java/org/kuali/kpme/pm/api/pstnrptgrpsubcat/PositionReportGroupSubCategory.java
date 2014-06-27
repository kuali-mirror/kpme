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
package org.kuali.kpme.pm.api.pstnrptgrpsubcat;

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
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionReportGroupSubCategory.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionReportGroupSubCategory.Constants.TYPE_NAME, propOrder = {
    PositionReportGroupSubCategory.Elements.PSTN_RPT_GRP_SUB_CAT,
    PositionReportGroupSubCategory.Elements.POSITION_REPORT_SUB_CAT,
    PositionReportGroupSubCategory.Elements.PM_PSTN_RPT_GRP_SUB_CAT_ID,
    PositionReportGroupSubCategory.Elements.POSITION_REPORT_GROUP,
    PositionReportGroupSubCategory.Elements.DESCRIPTION,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PositionReportGroupSubCategory.Elements.ACTIVE,
    PositionReportGroupSubCategory.Elements.ID,
    PositionReportGroupSubCategory.Elements.EFFECTIVE_LOCAL_DATE,
    PositionReportGroupSubCategory.Elements.CREATE_TIME,
    PositionReportGroupSubCategory.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionReportGroupSubCategory extends AbstractDataTransferObject implements PositionReportGroupSubCategoryContract {

	private static final long serialVersionUID = 3034895628793394839L;
	
	@XmlElement(name = Elements.PSTN_RPT_GRP_SUB_CAT, required = false)
    private final String pstnRptGrpSubCat;
    @XmlElement(name = Elements.POSITION_REPORT_SUB_CAT, required = false)
    private final String positionReportSubCat;
    @XmlElement(name = Elements.PM_PSTN_RPT_GRP_SUB_CAT_ID, required = false)
    private final String pmPstnRptGrpSubCatId;
    @XmlElement(name = Elements.POSITION_REPORT_GROUP, required = false)
    private final String positionReportGroup;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
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
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionReportGroupSubCategory() {
        this.pstnRptGrpSubCat = null;
        this.positionReportSubCat = null;
        this.pmPstnRptGrpSubCatId = null;
        this.positionReportGroup = null;
        this.description = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private PositionReportGroupSubCategory(Builder builder) {
        this.pstnRptGrpSubCat = builder.getPstnRptGrpSubCat();
        this.positionReportSubCat = builder.getPositionReportSubCat();
        this.pmPstnRptGrpSubCatId = builder.getPmPstnRptGrpSubCatId();
        this.positionReportGroup = builder.getPositionReportGroup();
        this.description = builder.getDescription();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getPstnRptGrpSubCat() {
        return this.pstnRptGrpSubCat;
    }

    @Override
    public String getPositionReportSubCat() {
        return this.positionReportSubCat;
    }

    @Override
    public String getPmPstnRptGrpSubCatId() {
        return this.pmPstnRptGrpSubCatId;
    }

    @Override
    public String getPositionReportGroup() {
        return this.positionReportGroup;
    }

    @Override
    public String getDescription() {
        return this.description;
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
     * A builder which can be used to construct {@link PositionReportGroupSubCategory} instances.  Enforces the constraints of the {@link PositionReportGroupSubCategoryContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionReportGroupSubCategoryContract, ModelBuilder {

		private static final long serialVersionUID = 5114408309591283429L;
		
		private String pstnRptGrpSubCat;
        private String positionReportSubCat;
        private String pmPstnRptGrpSubCatId;
        private String positionReportGroup;
        private String description;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder(String positionReportGroup, String positionReportSubCat) {
            setPositionReportGroup(positionReportGroup);
            setPositionReportSubCat(positionReportSubCat);
        }

        public static Builder create(String positionReportGroup, String positionReportSubCat) {
            return new Builder(positionReportGroup, positionReportSubCat);
        }

        public static Builder create(PositionReportGroupSubCategoryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPositionReportGroup(), contract.getPositionReportSubCat());
            builder.setPstnRptGrpSubCat(contract.getPstnRptGrpSubCat());
            builder.setPmPstnRptGrpSubCatId(contract.getPmPstnRptGrpSubCatId());
            builder.setDescription(contract.getDescription());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PositionReportGroupSubCategory build() {
            return new PositionReportGroupSubCategory(this);
        }

        @Override
        public String getPstnRptGrpSubCat() {
            return this.pstnRptGrpSubCat;
        }

        @Override
        public String getPositionReportSubCat() {
            return this.positionReportSubCat;
        }

        @Override
        public String getPmPstnRptGrpSubCatId() {
            return this.pmPstnRptGrpSubCatId;
        }

        @Override
        public String getPositionReportGroup() {
            return this.positionReportGroup;
        }

        @Override
        public String getDescription() {
            return this.description;
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


        public void setPstnRptGrpSubCat(String pstnRptGrpSubCat) {

            this.pstnRptGrpSubCat = pstnRptGrpSubCat;
        }

        public void setPositionReportSubCat(String positionReportSubCat) {
        	if (StringUtils.isBlank(positionReportSubCat)) {
                throw new IllegalArgumentException("position report sub category is blank");
            }
            this.positionReportSubCat = positionReportSubCat;
        }

        public void setPmPstnRptGrpSubCatId(String pmPstnRptGrpSubCatId) {

            this.pmPstnRptGrpSubCatId = pmPstnRptGrpSubCatId;
        }

        public void setPositionReportGroup(String positionReportGroup) {
        	if (StringUtils.isBlank(positionReportGroup)) {
                throw new IllegalArgumentException("position report group is blank");
            }
        	this.positionReportGroup = positionReportGroup;
        }

        public void setDescription(String description) {

            this.description = description;
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

        final static String ROOT_ELEMENT_NAME = "positionReportGroupSubCategory";
        final static String TYPE_NAME = "PositionReportGroupSubCategoryType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String PSTN_RPT_GRP_SUB_CAT = "pstnRptGrpSubCat";
        final static String POSITION_REPORT_SUB_CAT = "positionReportSubCat";
        final static String PM_PSTN_RPT_GRP_SUB_CAT_ID = "pmPstnRptGrpSubCatId";
        final static String POSITION_REPORT_GROUP = "positionReportGroup";
        final static String DESCRIPTION = "description";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}