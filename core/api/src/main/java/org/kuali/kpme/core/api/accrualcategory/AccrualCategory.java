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
package org.kuali.kpme.core.api.accrualcategory;

import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.krad.bo.PersistableBusinessObjectExtension;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@XmlRootElement(name = AccrualCategory.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = AccrualCategory.Constants.TYPE_NAME, propOrder = {
        AccrualCategory.Elements.HAS_RULES,
        AccrualCategory.Elements.MIN_PERCENT_WORKED,
        AccrualCategory.Elements.LM_ACCRUAL_CATEGORY_ID,
        AccrualCategory.Elements.LEAVE_PLAN,
        AccrualCategory.Elements.ACCRUAL_CATEGORY,
        AccrualCategory.Elements.DESCR,
        AccrualCategory.Elements.ACCRUAL_EARN_INTERVAL,
        AccrualCategory.Elements.PRORATION,
        AccrualCategory.Elements.DONATION,
        AccrualCategory.Elements.SHOW_ON_GRID,
        AccrualCategory.Elements.UNIT_OF_TIME,
        AccrualCategory.Elements.HISTORY,
        AccrualCategory.Elements.EARN_CODE,
        AccrualCategory.Elements.EARN_CODE_OBJ,
        AccrualCategory.Elements.ID,
        AccrualCategory.Elements.EFFECTIVE_DATE,
        AccrualCategory.Elements.EFFECTIVE_LOCAL_DATE,
        AccrualCategory.Elements.RELATIVE_EFFECTIVE_DATE,
        AccrualCategory.Elements.BUSINESS_KEY_VALUES_MAP,
        AccrualCategory.Elements.USER_PRINCIPAL_ID,
        AccrualCategory.Elements.CREATE_TIME,
        AccrualCategory.Elements.EXTENSION,
        AccrualCategory.Elements.NEW_COLLECTION_RECORD,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        AccrualCategory.Elements.ACTIVE,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class AccrualCategory
        extends AbstractDataTransferObject
        implements AccrualCategoryContract
{

    @XmlElement(name = Elements.HAS_RULES, required = false)
    private final String hasRules;
    @XmlElement(name = Elements.MIN_PERCENT_WORKED, required = false)
    private final BigDecimal minPercentWorked;
    @XmlElement(name = Elements.LM_ACCRUAL_CATEGORY_ID, required = false)
    private final String lmAccrualCategoryId;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY, required = false)
    private final String accrualCategory;
    @XmlElement(name = Elements.DESCR, required = false)
    private final String descr;
    @XmlElement(name = Elements.ACCRUAL_EARN_INTERVAL, required = false)
    private final String accrualEarnInterval;
    @XmlElement(name = Elements.PRORATION, required = false)
    private final String proration;
    @XmlElement(name = Elements.DONATION, required = false)
    private final String donation;
    @XmlElement(name = Elements.SHOW_ON_GRID, required = false)
    private final String showOnGrid;
    @XmlElement(name = Elements.UNIT_OF_TIME, required = false)
    private final String unitOfTime;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.EARN_CODE_OBJ, required = false)
    private final EarnCodeContract earnCodeObj;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private AccrualCategory() {
        this.hasRules = null;
        this.minPercentWorked = null;
        this.lmAccrualCategoryId = null;
        this.leavePlan = null;
        this.accrualCategory = null;
        this.descr = null;
        this.accrualEarnInterval = null;
        this.proration = null;
        this.donation = null;
        this.showOnGrid = null;
        this.unitOfTime = null;
        this.earnCode = null;
        this.earnCodeObj = null;
        this.id = null;
        this.effectiveLocalDate = null;
        this.userPrincipalId = null;
        this.createTime = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
    }

    private AccrualCategory(Builder builder) {
        this.hasRules = builder.getHasRules();
        this.minPercentWorked = builder.getMinPercentWorked();
        this.lmAccrualCategoryId = builder.getLmAccrualCategoryId();
        this.leavePlan = builder.getLeavePlan();
        this.accrualCategory = builder.getAccrualCategory();
        this.descr = builder.getDescr();
        this.accrualEarnInterval = builder.getAccrualEarnInterval();
        this.proration = builder.getProration();
        this.donation = builder.getDonation();
        this.showOnGrid = builder.getShowOnGrid();
        this.unitOfTime = builder.getUnitOfTime();
        this.earnCode = builder.getEarnCode();
        this.earnCodeObj = builder.getEarnCodeObj();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.createTime = builder.getCreateTime();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
    }

    @Override
    public String getHasRules() {
        return this.hasRules;
    }

    @Override
    public BigDecimal getMinPercentWorked() {
        return this.minPercentWorked;
    }

    @Override
    public String getLmAccrualCategoryId() {
        return this.lmAccrualCategoryId;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public String getAccrualCategory() {
        return this.accrualCategory;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public String getAccrualEarnInterval() {
        return this.accrualEarnInterval;
    }

    @Override
    public String getProration() {
        return this.proration;
    }

    @Override
    public String getDonation() {
        return this.donation;
    }

    @Override
    public String getShowOnGrid() {
        return this.showOnGrid;
    }

    @Override
    public String getUnitOfTime() {
        return this.unitOfTime;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public EarnCodeContract getEarnCodeObj() {
        return this.earnCodeObj;
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
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
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


    /**
     * A builder which can be used to construct {@link AccrualCategory} instances.  Enforces the constraints of the {@link AccrualCategoryContract}.
     *
     */
    public final static class Builder
            implements Serializable, AccrualCategoryContract, ModelBuilder
    {

        private String hasRules;
        private BigDecimal minPercentWorked;
        private String lmAccrualCategoryId;
        private String leavePlan;
        private String accrualCategory;
        private String descr;
        private String accrualEarnInterval;
        private String proration;
        private String donation;
        private String showOnGrid;
        private String unitOfTime;
        private Boolean history;
        private String earnCode;
        private EarnCodeContract earnCodeObj;
        private String id;
        private Date effectiveDate;
        private LocalDate effectiveLocalDate;
        private Date relativeEffectiveDate;
        private Map businessKeyValuesMap;
        private String userPrincipalId;
        private DateTime createTime;
        private PersistableBusinessObjectExtension extension;
        private boolean newCollectionRecord;
        private Long versionNumber;
        private String objectId;
        private boolean active;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(AccrualCategoryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setHasRules(contract.getHasRules());
            builder.setMinPercentWorked(contract.getMinPercentWorked());
            builder.setLmAccrualCategoryId(contract.getLmAccrualCategoryId());
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setAccrualCategory(contract.getAccrualCategory());
            builder.setDescr(contract.getDescr());
            builder.setAccrualEarnInterval(contract.getAccrualEarnInterval());
            builder.setProration(contract.getProration());
            builder.setDonation(contract.getDonation());
            builder.setShowOnGrid(contract.getShowOnGrid());
            builder.setUnitOfTime(contract.getUnitOfTime());
            builder.setEarnCode(contract.getEarnCode());
            builder.setEarnCodeObj(contract.getEarnCodeObj());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            return builder;
        }

        public AccrualCategory build() {
            return new AccrualCategory(this);
        }

        @Override
        public String getHasRules() {
            return this.hasRules;
        }

        @Override
        public BigDecimal getMinPercentWorked() {
            return this.minPercentWorked;
        }

        @Override
        public String getLmAccrualCategoryId() {
            return this.lmAccrualCategoryId;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public String getAccrualCategory() {
            return this.accrualCategory;
        }

        @Override
        public String getDescr() {
            return this.descr;
        }

        @Override
        public String getAccrualEarnInterval() {
            return this.accrualEarnInterval;
        }

        @Override
        public String getProration() {
            return this.proration;
        }

        @Override
        public String getDonation() {
            return this.donation;
        }

        @Override
        public String getShowOnGrid() {
            return this.showOnGrid;
        }

        @Override
        public String getUnitOfTime() {
            return this.unitOfTime;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public EarnCodeContract getEarnCodeObj() {
            return this.earnCodeObj;
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
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
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

        public void setHasRules(String hasRules) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hasRules = hasRules;
        }

        public void setMinPercentWorked(BigDecimal minPercentWorked) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.minPercentWorked = minPercentWorked;
        }

        public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.lmAccrualCategoryId = lmAccrualCategoryId;
        }

        public void setLeavePlan(String leavePlan) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.leavePlan = leavePlan;
        }

        public void setAccrualCategory(String accrualCategory) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.accrualCategory = accrualCategory;
        }

        public void setDescr(String descr) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.descr = descr;
        }

        public void setAccrualEarnInterval(String accrualEarnInterval) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.accrualEarnInterval = accrualEarnInterval;
        }

        public void setProration(String proration) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.proration = proration;
        }

        public void setDonation(String donation) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.donation = donation;
        }

        public void setShowOnGrid(String showOnGrid) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.showOnGrid = showOnGrid;
        }

        public void setUnitOfTime(String unitOfTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.unitOfTime = unitOfTime;
        }

        public void setHistory(Boolean history) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.history = history;
        }

        public void setEarnCode(String earnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCode = earnCode;
        }

        public void setEarnCodeObj(EarnCodeContract earnCodeObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCodeObj = earnCodeObj;
        }

        public void setId(String id) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.id = id;
        }

        public void setEffectiveDate(Date effectiveDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveDate = effectiveDate;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setRelativeEffectiveDate(Date relativeEffectiveDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.relativeEffectiveDate = relativeEffectiveDate;
        }

        public void setBusinessKeyValuesMap(Map businessKeyValuesMap) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.businessKeyValuesMap = businessKeyValuesMap;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

        public void setExtension(PersistableBusinessObjectExtension extension) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.extension = extension;
        }

        public void setNewCollectionRecord(boolean newCollectionRecord) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.newCollectionRecord = newCollectionRecord;
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

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "accrualCategory";
        final static String TYPE_NAME = "AccrualCategoryType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String HAS_RULES = "hasRules";
        final static String MIN_PERCENT_WORKED = "minPercentWorked";
        final static String LM_ACCRUAL_CATEGORY_ID = "lmAccrualCategoryId";
        final static String LEAVE_PLAN = "leavePlan";
        final static String ACCRUAL_CATEGORY = "accrualCategory";
        final static String DESCR = "descr";
        final static String ACCRUAL_EARN_INTERVAL = "accrualEarnInterval";
        final static String PRORATION = "proration";
        final static String DONATION = "donation";
        final static String SHOW_ON_GRID = "showOnGrid";
        final static String UNIT_OF_TIME = "unitOfTime";
        final static String HISTORY = "history";
        final static String EARN_CODE = "earnCode";
        final static String EARN_CODE_OBJ = "earnCodeObj";
        final static String ID = "id";
        final static String EFFECTIVE_DATE = "effectiveDate";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String RELATIVE_EFFECTIVE_DATE = "relativeEffectiveDate";
        final static String BUSINESS_KEY_VALUES_MAP = "businessKeyValuesMap";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String CREATE_TIME = "createTime";
        final static String EXTENSION = "extension";
        final static String NEW_COLLECTION_RECORD = "newCollectionRecord";
        final static String ACTIVE = "active";
    }

    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(Elements.ACCRUAL_CATEGORY)
            .build();

}
