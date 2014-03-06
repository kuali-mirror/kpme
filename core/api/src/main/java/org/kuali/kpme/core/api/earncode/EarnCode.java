package org.kuali.kpme.core.api.earncode;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@XmlRootElement(name = EarnCode.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EarnCode.Constants.TYPE_NAME, propOrder = {
        EarnCode.Elements.ACCRUAL_BALANCE_ACTION,
        EarnCode.Elements.FRACTIONAL_TIME_ALLOWED,
        EarnCode.Elements.ROUNDING_OPTION,
        EarnCode.Elements.ELIGIBLE_FOR_ACCRUAL,
        EarnCode.Elements.AFFECT_PAY,
        EarnCode.Elements.ALLOW_SCHEDULED_LEAVE,
        EarnCode.Elements.FMLA,
        EarnCode.Elements.WORKMANS_COMP,
        EarnCode.Elements.DEFAULT_AMOUNTOF_TIME,
        EarnCode.Elements.ALLOW_NEGATIVE_ACCRUAL_BALANCE,
        EarnCode.Elements.EARN_CODE,
        EarnCode.Elements.DESCRIPTION,
        EarnCode.Elements.HR_EARN_CODE_ID,
        EarnCode.Elements.ACCRUAL_CATEGORY,
        EarnCode.Elements.ACCRUAL_CATEGORY_OBJ,
        EarnCode.Elements.INFLATE_MIN_HOURS,
        EarnCode.Elements.ROLLUP_TO_EARN_CODE,
        EarnCode.Elements.LEAVE_PLAN,
        EarnCode.Elements.ROLLUP_TO_EARN_CODE_OBJ,
        EarnCode.Elements.COUNTS_AS_REGULAR_PAY,
        EarnCode.Elements.USAGE_LIMIT,
        EarnCode.Elements.RECORD_METHOD,
        EarnCode.Elements.INFLATE_FACTOR,
        EarnCode.Elements.OVT_EARN_CODE,
        EarnCode.Elements.EARN_CODE_TYPE,
        EarnCode.Elements.ACTIVE,
        EarnCode.Elements.ID,
        EarnCode.Elements.EFFECTIVE_LOCAL_DATE,
        EarnCode.Elements.CREATE_TIME,
        EarnCode.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EarnCode
        extends AbstractDataTransferObject
        implements EarnCodeContract
{

    private static final long serialVersionUID = -7863496886844454812L;
    @XmlElement(name = Elements.ACCRUAL_BALANCE_ACTION, required = false)
    private final String accrualBalanceAction;
    @XmlElement(name = Elements.FRACTIONAL_TIME_ALLOWED, required = false)
    private final String fractionalTimeAllowed;
    @XmlElement(name = Elements.ROUNDING_OPTION, required = false)
    private final String roundingOption;
    @XmlElement(name = Elements.ELIGIBLE_FOR_ACCRUAL, required = false)
    private final String eligibleForAccrual;
    @XmlElement(name = Elements.AFFECT_PAY, required = false)
    private final String affectPay;
    @XmlElement(name = Elements.ALLOW_SCHEDULED_LEAVE, required = false)
    private final String allowScheduledLeave;
    @XmlElement(name = Elements.FMLA, required = false)
    private final String fmla;
    @XmlElement(name = Elements.WORKMANS_COMP, required = false)
    private final String workmansComp;
    @XmlElement(name = Elements.DEFAULT_AMOUNTOF_TIME, required = false)
    private final Long defaultAmountofTime;
    @XmlElement(name = Elements.ALLOW_NEGATIVE_ACCRUAL_BALANCE, required = false)
    private final String allowNegativeAccrualBalance;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.HR_EARN_CODE_ID, required = false)
    private final String hrEarnCodeId;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY, required = false)
    private final String accrualCategory;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY_OBJ, required = false)
    private final AccrualCategory accrualCategoryObj;
    @XmlElement(name = Elements.INFLATE_MIN_HOURS, required = false)
    private final BigDecimal inflateMinHours;
    @XmlElement(name = Elements.ROLLUP_TO_EARN_CODE, required = false)
    private final String rollupToEarnCode;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElement(name = Elements.ROLLUP_TO_EARN_CODE_OBJ, required = false)
    private final EarnCode rollupToEarnCodeObj;
    @XmlElement(name = Elements.COUNTS_AS_REGULAR_PAY, required = false)
    private final String countsAsRegularPay;
    @XmlElement(name = Elements.USAGE_LIMIT, required = false)
    private final String usageLimit;
    @XmlElement(name = Elements.RECORD_METHOD, required = false)
    private final String recordMethod;
    @XmlElement(name = Elements.INFLATE_FACTOR, required = false)
    private final BigDecimal inflateFactor;
    @XmlElement(name = Elements.OVT_EARN_CODE, required = false)
    private final Boolean ovtEarnCode;
    @XmlElement(name = Elements.EARN_CODE_TYPE, required = false)
    private final String earnCodeType;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
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
    private EarnCode() {
        this.accrualBalanceAction = null;
        this.fractionalTimeAllowed = null;
        this.roundingOption = null;
        this.eligibleForAccrual = null;
        this.affectPay = null;
        this.allowScheduledLeave = null;
        this.fmla = null;
        this.workmansComp = null;
        this.defaultAmountofTime = null;
        this.allowNegativeAccrualBalance = null;
        this.earnCode = null;
        this.description = null;
        this.hrEarnCodeId = null;
        this.accrualCategory = null;
        this.accrualCategoryObj = null;
        this.inflateMinHours = null;
        this.rollupToEarnCode = null;
        this.leavePlan = null;
        this.rollupToEarnCodeObj = null;
        this.countsAsRegularPay = null;
        this.usageLimit = null;
        this.recordMethod = null;
        this.inflateFactor = null;
        this.ovtEarnCode = null;
        this.earnCodeType = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EarnCode(Builder builder) {
        this.accrualBalanceAction = builder.getAccrualBalanceAction();
        this.fractionalTimeAllowed = builder.getFractionalTimeAllowed();
        this.roundingOption = builder.getRoundingOption();
        this.eligibleForAccrual = builder.getEligibleForAccrual();
        this.affectPay = builder.getAffectPay();
        this.allowScheduledLeave = builder.getAllowScheduledLeave();
        this.fmla = builder.getFmla();
        this.workmansComp = builder.getWorkmansComp();
        this.defaultAmountofTime = builder.getDefaultAmountofTime();
        this.allowNegativeAccrualBalance = builder.getAllowNegativeAccrualBalance();
        this.earnCode = builder.getEarnCode();
        this.description = builder.getDescription();
        this.hrEarnCodeId = builder.getHrEarnCodeId();
        this.accrualCategory = builder.getAccrualCategory();
        this.accrualCategoryObj = builder.getAccrualCategoryObj() == null ? null : builder.getAccrualCategoryObj().build();
        this.inflateMinHours = builder.getInflateMinHours();
        this.rollupToEarnCode = builder.getRollupToEarnCode();
        this.leavePlan = builder.getLeavePlan();
        this.rollupToEarnCodeObj = builder.getRollupToEarnCodeObj() == null ? null : builder.getRollupToEarnCodeObj().build();
        this.countsAsRegularPay = builder.getCountsAsRegularPay();
        this.usageLimit = builder.getUsageLimit();
        this.recordMethod = builder.getRecordMethod();
        this.inflateFactor = builder.getInflateFactor();
        this.ovtEarnCode = builder.isOvtEarnCode();
        this.earnCodeType = builder.getEarnCodeType();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getAccrualBalanceAction() {
        return this.accrualBalanceAction;
    }

    @Override
    public String getFractionalTimeAllowed() {
        return this.fractionalTimeAllowed;
    }

    @Override
    public String getRoundingOption() {
        return this.roundingOption;
    }

    @Override
    public String getEligibleForAccrual() {
        return this.eligibleForAccrual;
    }

    @Override
    public String getAffectPay() {
        return this.affectPay;
    }

    @Override
    public String getAllowScheduledLeave() {
        return this.allowScheduledLeave;
    }

    @Override
    public String getFmla() {
        return this.fmla;
    }

    @Override
    public String getWorkmansComp() {
        return this.workmansComp;
    }

    @Override
    public Long getDefaultAmountofTime() {
        return this.defaultAmountofTime;
    }

    @Override
    public String getAllowNegativeAccrualBalance() {
        return this.allowNegativeAccrualBalance;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getHrEarnCodeId() {
        return this.hrEarnCodeId;
    }

    @Override
    public String getAccrualCategory() {
        return this.accrualCategory;
    }

    @Override
    public AccrualCategory getAccrualCategoryObj() {
        return this.accrualCategoryObj;
    }

    @Override
    public BigDecimal getInflateMinHours() {
        return this.inflateMinHours;
    }

    @Override
    public String getRollupToEarnCode() {
        return this.rollupToEarnCode;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public EarnCode getRollupToEarnCodeObj() {
        return this.rollupToEarnCodeObj;
    }

    @Override
    public String getCountsAsRegularPay() {
        return this.countsAsRegularPay;
    }

    @Override
    public String getUsageLimit() {
        return this.usageLimit;
    }

    @Override
    public String getRecordMethod() {
        return this.recordMethod;
    }

    @Override
    public BigDecimal getInflateFactor() {
        return this.inflateFactor;
    }

    @Override
    public Boolean isOvtEarnCode() {
        return this.ovtEarnCode;
    }

    @Override
    public String getEarnCodeType() {
        return this.earnCodeType;
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
     * A builder which can be used to construct {@link EarnCode} instances.  Enforces the constraints of the {@link EarnCodeContract}.
     *
     */
    public final static class Builder
            implements Serializable, EarnCodeContract, ModelBuilder
    {

        private String accrualBalanceAction;
        private String fractionalTimeAllowed;
        private String roundingOption;
        private String eligibleForAccrual;
        private String affectPay;
        private String allowScheduledLeave;
        private String fmla;
        private String workmansComp;
        private Long defaultAmountofTime;
        private String allowNegativeAccrualBalance;
        private String earnCode;
        private String description;
        private String hrEarnCodeId;
        private String accrualCategory;
        private AccrualCategory.Builder accrualCategoryObj;
        private BigDecimal inflateMinHours;
        private String rollupToEarnCode;
        private String leavePlan;
        private EarnCode.Builder rollupToEarnCodeObj;
        private String countsAsRegularPay;
        private String usageLimit;
        private String recordMethod;
        private BigDecimal inflateFactor;
        private Boolean ovtEarnCode;
        private String earnCodeType;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EarnCodeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setAccrualBalanceAction(contract.getAccrualBalanceAction());
            builder.setFractionalTimeAllowed(contract.getFractionalTimeAllowed());
            builder.setRoundingOption(contract.getRoundingOption());
            builder.setEligibleForAccrual(contract.getEligibleForAccrual());
            builder.setAffectPay(contract.getAffectPay());
            builder.setAllowScheduledLeave(contract.getAllowScheduledLeave());
            builder.setFmla(contract.getFmla());
            builder.setWorkmansComp(contract.getWorkmansComp());
            builder.setDefaultAmountofTime(contract.getDefaultAmountofTime());
            builder.setAllowNegativeAccrualBalance(contract.getAllowNegativeAccrualBalance());
            builder.setEarnCode(contract.getEarnCode());
            builder.setDescription(contract.getDescription());
            builder.setHrEarnCodeId(contract.getHrEarnCodeId());
            builder.setAccrualCategory(contract.getAccrualCategory());
            builder.setAccrualCategoryObj(contract.getAccrualCategoryObj() == null ? null : AccrualCategory.Builder.create(contract.getAccrualCategoryObj()));
            builder.setInflateMinHours(contract.getInflateMinHours());
            builder.setRollupToEarnCode(contract.getRollupToEarnCode());
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setRollupToEarnCodeObj(contract.getRollupToEarnCodeObj() == null ? null : EarnCode.Builder.create(contract.getRollupToEarnCodeObj()));
            builder.setCountsAsRegularPay(contract.getCountsAsRegularPay());
            builder.setUsageLimit(contract.getUsageLimit());
            builder.setRecordMethod(contract.getRecordMethod());
            builder.setInflateFactor(contract.getInflateFactor());
            builder.setOvtEarnCode(contract.isOvtEarnCode());
            builder.setEarnCodeType(contract.getEarnCodeType());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EarnCode build() {
            return new EarnCode(this);
        }

        @Override
        public String getAccrualBalanceAction() {
            return this.accrualBalanceAction;
        }

        @Override
        public String getFractionalTimeAllowed() {
            return this.fractionalTimeAllowed;
        }

        @Override
        public String getRoundingOption() {
            return this.roundingOption;
        }

        @Override
        public String getEligibleForAccrual() {
            return this.eligibleForAccrual;
        }

        @Override
        public String getAffectPay() {
            return this.affectPay;
        }

        @Override
        public String getAllowScheduledLeave() {
            return this.allowScheduledLeave;
        }

        @Override
        public String getFmla() {
            return this.fmla;
        }

        @Override
        public String getWorkmansComp() {
            return this.workmansComp;
        }

        @Override
        public Long getDefaultAmountofTime() {
            return this.defaultAmountofTime;
        }

        @Override
        public String getAllowNegativeAccrualBalance() {
            return this.allowNegativeAccrualBalance;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getHrEarnCodeId() {
            return this.hrEarnCodeId;
        }

        @Override
        public String getAccrualCategory() {
            return this.accrualCategory;
        }

        @Override
        public AccrualCategory.Builder getAccrualCategoryObj() {
            return this.accrualCategoryObj;
        }

        @Override
        public BigDecimal getInflateMinHours() {
            return this.inflateMinHours;
        }

        @Override
        public String getRollupToEarnCode() {
            return this.rollupToEarnCode;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public EarnCode.Builder getRollupToEarnCodeObj() {
            return this.rollupToEarnCodeObj;
        }

        @Override
        public String getCountsAsRegularPay() {
            return this.countsAsRegularPay;
        }

        @Override
        public String getUsageLimit() {
            return this.usageLimit;
        }

        @Override
        public String getRecordMethod() {
            return this.recordMethod;
        }

        @Override
        public BigDecimal getInflateFactor() {
            return this.inflateFactor;
        }

        @Override
        public Boolean isOvtEarnCode() {
            return this.ovtEarnCode;
        }

        @Override
        public String getEarnCodeType() {
            return this.earnCodeType;
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

        public void setAccrualBalanceAction(String accrualBalanceAction) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.accrualBalanceAction = accrualBalanceAction;
        }

        public void setFractionalTimeAllowed(String fractionalTimeAllowed) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.fractionalTimeAllowed = fractionalTimeAllowed;
        }

        public void setRoundingOption(String roundingOption) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.roundingOption = roundingOption;
        }

        public void setEligibleForAccrual(String eligibleForAccrual) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.eligibleForAccrual = eligibleForAccrual;
        }

        public void setAffectPay(String affectPay) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.affectPay = affectPay;
        }

        public void setAllowScheduledLeave(String allowScheduledLeave) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.allowScheduledLeave = allowScheduledLeave;
        }

        public void setFmla(String fmla) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.fmla = fmla;
        }

        public void setWorkmansComp(String workmansComp) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workmansComp = workmansComp;
        }

        public void setDefaultAmountofTime(Long defaultAmountofTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.defaultAmountofTime = defaultAmountofTime;
        }

        public void setAllowNegativeAccrualBalance(String allowNegativeAccrualBalance) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.allowNegativeAccrualBalance = allowNegativeAccrualBalance;
        }

        public void setEarnCode(String earnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCode = earnCode;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setHrEarnCodeId(String hrEarnCodeId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrEarnCodeId = hrEarnCodeId;
        }

        public void setAccrualCategory(String accrualCategory) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.accrualCategory = accrualCategory;
        }

        public void setAccrualCategoryObj(AccrualCategory.Builder accrualCategoryObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.accrualCategoryObj = accrualCategoryObj;
        }

        public void setInflateMinHours(BigDecimal inflateMinHours) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.inflateMinHours = inflateMinHours;
        }

        public void setRollupToEarnCode(String rollupToEarnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.rollupToEarnCode = rollupToEarnCode;
        }

        public void setLeavePlan(String leavePlan) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.leavePlan = leavePlan;
        }

        public void setRollupToEarnCodeObj(EarnCode.Builder rollupToEarnCodeObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.rollupToEarnCodeObj = rollupToEarnCodeObj;
        }

        public void setCountsAsRegularPay(String countsAsRegularPay) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.countsAsRegularPay = countsAsRegularPay;
        }

        public void setUsageLimit(String usageLimit) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.usageLimit = usageLimit;
        }

        public void setRecordMethod(String recordMethod) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.recordMethod = recordMethod;
        }

        public void setInflateFactor(BigDecimal inflateFactor) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.inflateFactor = inflateFactor;
        }

        public void setOvtEarnCode(Boolean ovtEarnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.ovtEarnCode = ovtEarnCode;
        }

        public void setEarnCodeType(String earnCodeType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCodeType = earnCodeType;
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

        final static String ROOT_ELEMENT_NAME = "earnCode";
        final static String TYPE_NAME = "EarnCodeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String ACCRUAL_BALANCE_ACTION = "accrualBalanceAction";
        final static String FRACTIONAL_TIME_ALLOWED = "fractionalTimeAllowed";
        final static String ROUNDING_OPTION = "roundingOption";
        final static String ELIGIBLE_FOR_ACCRUAL = "eligibleForAccrual";
        final static String AFFECT_PAY = "affectPay";
        final static String ALLOW_SCHEDULED_LEAVE = "allowScheduledLeave";
        final static String FMLA = "fmla";
        final static String WORKMANS_COMP = "workmansComp";
        final static String DEFAULT_AMOUNTOF_TIME = "defaultAmountofTime";
        final static String ALLOW_NEGATIVE_ACCRUAL_BALANCE = "allowNegativeAccrualBalance";
        final static String EARN_CODE = "earnCode";
        final static String DESCRIPTION = "description";
        final static String HR_EARN_CODE_ID = "hrEarnCodeId";
        final static String ACCRUAL_CATEGORY = "accrualCategory";
        final static String ACCRUAL_CATEGORY_OBJ = "accrualCategoryObj";
        final static String INFLATE_MIN_HOURS = "inflateMinHours";
        final static String ROLLUP_TO_EARN_CODE = "rollupToEarnCode";
        final static String LEAVE_PLAN = "leavePlan";
        final static String ROLLUP_TO_EARN_CODE_OBJ = "rollupToEarnCodeObj";
        final static String COUNTS_AS_REGULAR_PAY = "countsAsRegularPay";
        final static String USAGE_LIMIT = "usageLimit";
        final static String RECORD_METHOD = "recordMethod";
        final static String INFLATE_FACTOR = "inflateFactor";
        final static String OVT_EARN_CODE = "ovtEarnCode";
        final static String EARN_CODE_TYPE = "earnCodeType";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}