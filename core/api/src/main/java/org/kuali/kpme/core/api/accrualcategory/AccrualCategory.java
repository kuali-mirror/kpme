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
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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
        AccrualCategory.Elements.EARN_CODE,
        AccrualCategory.Elements.EARN_CODE_OBJ,
        AccrualCategory.Elements.ID,
        AccrualCategory.Elements.EFFECTIVE_DATE,
        AccrualCategory.Elements.EFFECTIVE_LOCAL_DATE,
        AccrualCategory.Elements.RELATIVE_EFFECTIVE_DATE,
        AccrualCategory.Elements.USER_PRINCIPAL_ID,
        AccrualCategory.Elements.CREATE_TIME,
        AccrualCategory.Elements.ACCRUAL_CATEGORY_RULES,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        AccrualCategory.Elements.ACTIVE,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class AccrualCategory
        extends AbstractDataTransferObject
        implements AccrualCategoryContract
{

    private static final long serialVersionUID = 5215514350209105035L;
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
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElementWrapper(name = Elements.ACCRUAL_CATEGORY_RULES, required = false)
    @XmlElement(name = Elements.ACCRUAL_CATEGORY_RULE, required = false)
    private final List<AccrualCategoryRule> accrualCategoryRules;
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
        this.accrualCategoryRules = null;
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
        this.accrualCategoryRules = ModelObjectUtils.<AccrualCategoryRule>buildImmutableCopy(builder.getAccrualCategoryRules());
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

    @Override
    public List<AccrualCategoryRule> getAccrualCategoryRules() {
        return accrualCategoryRules;
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
        private String earnCode;
        private EarnCodeContract earnCodeObj;
        private String id;
        private LocalDate effectiveLocalDate;
        private String userPrincipalId;
        private DateTime createTime;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private List<AccrualCategoryRule.Builder> accrualCategoryRules;

        private static final ModelObjectUtils.Transformer<AccrualCategoryRuleContract, AccrualCategoryRule.Builder> toAccrualCategoryRuleBuilder = new ModelObjectUtils.Transformer<AccrualCategoryRuleContract, AccrualCategoryRule.Builder>() {
            public AccrualCategoryRule.Builder transform(AccrualCategoryRuleContract input) {
                return AccrualCategoryRule.Builder.create(input);
            }
        };

        private Builder() {

        }

        public static Builder create() {

            return new Builder();
        }

        public static Builder create(AccrualCategoryContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }

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
            builder.setAccrualCategoryRules(ModelObjectUtils.transform(contract.getAccrualCategoryRules(), toAccrualCategoryRuleBuilder));
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

        @Override
        public List<AccrualCategoryRule.Builder> getAccrualCategoryRules() {
            return accrualCategoryRules;
        }

        public void setAccrualCategoryRules(List<AccrualCategoryRule.Builder> accrualCategoryRules) {
            this.accrualCategoryRules = accrualCategoryRules;
        }

        public void setHasRules(String hasRules) {

            this.hasRules = hasRules;
        }

        public void setMinPercentWorked(BigDecimal minPercentWorked) {

            this.minPercentWorked = minPercentWorked;
        }

        public void setLmAccrualCategoryId(String lmAccrualCategoryId) {

            this.lmAccrualCategoryId = lmAccrualCategoryId;
        }

        public void setLeavePlan(String leavePlan) {

            this.leavePlan = leavePlan;
        }

        public void setAccrualCategory(String accrualCategory) {

            this.accrualCategory = accrualCategory;
        }

        public void setDescr(String descr) {

            this.descr = descr;
        }

        public void setAccrualEarnInterval(String accrualEarnInterval) {

            this.accrualEarnInterval = accrualEarnInterval;
        }

        public void setProration(String proration) {

            this.proration = proration;
        }

        public void setDonation(String donation) {

            this.donation = donation;
        }

        public void setShowOnGrid(String showOnGrid) {

            this.showOnGrid = showOnGrid;
        }

        public void setUnitOfTime(String unitOfTime) {

            this.unitOfTime = unitOfTime;
        }

        public void setEarnCode(String earnCode) {

            this.earnCode = earnCode;
        }

        public void setEarnCodeObj(EarnCodeContract earnCodeObj) {

            this.earnCodeObj = earnCodeObj;
        }

        public void setId(String id) {

            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {

            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setUserPrincipalId(String userPrincipalId) {

            this.userPrincipalId = userPrincipalId;
        }

        public void setCreateTime(DateTime createTime) {

            this.createTime = createTime;
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
        final static String EARN_CODE = "earnCode";
        final static String EARN_CODE_OBJ = "earnCodeObj";
        final static String ID = "id";
        final static String EFFECTIVE_DATE = "effectiveDate";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String RELATIVE_EFFECTIVE_DATE = "relativeEffectiveDate";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String CREATE_TIME = "createTime";
        final static String ACTIVE = "active";
        final static String ACCRUAL_CATEGORY_RULES = "accrualCategoryRules";
        final static String ACCRUAL_CATEGORY_RULE = "accrualCategoryRule";
    }

    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(Elements.ACCRUAL_CATEGORY)
            .build();

}
