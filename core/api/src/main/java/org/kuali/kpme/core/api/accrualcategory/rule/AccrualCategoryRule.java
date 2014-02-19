package org.kuali.kpme.core.api.accrualcategory.rule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = AccrualCategoryRule.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = AccrualCategoryRule.Constants.TYPE_NAME, propOrder = {
        AccrualCategoryRule.Elements.SERVICE_UNIT_OF_TIME,
        AccrualCategoryRule.Elements.START,
        AccrualCategoryRule.Elements.END,
        AccrualCategoryRule.Elements.ACCRUAL_RATE,
        AccrualCategoryRule.Elements.MAX_BALANCE,
        AccrualCategoryRule.Elements.LM_ACCRUAL_CATEGORY_RULE_ID,
        AccrualCategoryRule.Elements.MAX_BAL_FLAG,
        AccrualCategoryRule.Elements.MAX_BALANCE_ACTION_FREQUENCY,
        AccrualCategoryRule.Elements.ACTION_AT_MAX_BALANCE,
        AccrualCategoryRule.Elements.MAX_BALANCE_TRANSFER_TO_ACCRUAL_CATEGORY,
        AccrualCategoryRule.Elements.MAX_BALANCE_TRANSFER_CONVERSION_FACTOR,
        AccrualCategoryRule.Elements.MAX_TRANSFER_AMOUNT,
        AccrualCategoryRule.Elements.MAX_PAYOUT_AMOUNT,
        AccrualCategoryRule.Elements.MAX_PAYOUT_EARN_CODE,
        AccrualCategoryRule.Elements.MAX_USAGE,
        AccrualCategoryRule.Elements.MAX_CARRY_OVER,
        AccrualCategoryRule.Elements.LM_ACCRUAL_CATEGORY_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        AccrualCategoryRule.Elements.ACTIVE,
        AccrualCategoryRule.Elements.ID,
        AccrualCategoryRule.Elements.CREATE_TIME,
        AccrualCategoryRule.Elements.EFFECTIVE_LOCAL_DATE,
        AccrualCategoryRule.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class AccrualCategoryRule
        extends AbstractDataTransferObject
        implements AccrualCategoryRuleContract
{

    @XmlElement(name = Elements.SERVICE_UNIT_OF_TIME, required = false)
    private final String serviceUnitOfTime;
    @XmlElement(name = Elements.START, required = false)
    private final Long start;
    @XmlElement(name = Elements.END, required = false)
    private final Long end;
    @XmlElement(name = Elements.ACCRUAL_RATE, required = false)
    private final BigDecimal accrualRate;
    @XmlElement(name = Elements.MAX_BALANCE, required = false)
    private final BigDecimal maxBalance;
    @XmlElement(name = Elements.LM_ACCRUAL_CATEGORY_RULE_ID, required = false)
    private final String lmAccrualCategoryRuleId;
    @XmlElement(name = Elements.MAX_BAL_FLAG, required = false)
    private final String maxBalFlag;
    @XmlElement(name = Elements.MAX_BALANCE_ACTION_FREQUENCY, required = false)
    private final String maxBalanceActionFrequency;
    @XmlElement(name = Elements.ACTION_AT_MAX_BALANCE, required = false)
    private final String actionAtMaxBalance;
    @XmlElement(name = Elements.MAX_BALANCE_TRANSFER_TO_ACCRUAL_CATEGORY, required = false)
    private final String maxBalanceTransferToAccrualCategory;
    @XmlElement(name = Elements.MAX_BALANCE_TRANSFER_CONVERSION_FACTOR, required = false)
    private final BigDecimal maxBalanceTransferConversionFactor;
    @XmlElement(name = Elements.MAX_TRANSFER_AMOUNT, required = false)
    private final Long maxTransferAmount;
    @XmlElement(name = Elements.MAX_PAYOUT_AMOUNT, required = false)
    private final Long maxPayoutAmount;
    @XmlElement(name = Elements.MAX_PAYOUT_EARN_CODE, required = false)
    private final String maxPayoutEarnCode;
    @XmlElement(name = Elements.MAX_USAGE, required = false)
    private final Long maxUsage;
    @XmlElement(name = Elements.MAX_CARRY_OVER, required = false)
    private final Long maxCarryOver;
    @XmlElement(name = Elements.LM_ACCRUAL_CATEGORY_ID, required = false)
    private final String lmAccrualCategoryId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private AccrualCategoryRule() {
        this.serviceUnitOfTime = null;
        this.start = null;
        this.end = null;
        this.accrualRate = null;
        this.maxBalance = null;
        this.lmAccrualCategoryRuleId = null;
        this.maxBalFlag = null;
        this.maxBalanceActionFrequency = null;
        this.actionAtMaxBalance = null;
        this.maxBalanceTransferToAccrualCategory = null;
        this.maxBalanceTransferConversionFactor = null;
        this.maxTransferAmount = null;
        this.maxPayoutAmount = null;
        this.maxPayoutEarnCode = null;
        this.maxUsage = null;
        this.maxCarryOver = null;
        this.lmAccrualCategoryId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
        this.userPrincipalId = null;
    }

    private AccrualCategoryRule(Builder builder) {
        this.serviceUnitOfTime = builder.getServiceUnitOfTime();
        this.start = builder.getStart();
        this.end = builder.getEnd();
        this.accrualRate = builder.getAccrualRate();
        this.maxBalance = builder.getMaxBalance();
        this.lmAccrualCategoryRuleId = builder.getLmAccrualCategoryRuleId();
        this.maxBalFlag = builder.getMaxBalFlag();
        this.maxBalanceActionFrequency = builder.getMaxBalanceActionFrequency();
        this.actionAtMaxBalance = builder.getActionAtMaxBalance();
        this.maxBalanceTransferToAccrualCategory = builder.getMaxBalanceTransferToAccrualCategory();
        this.maxBalanceTransferConversionFactor = builder.getMaxBalanceTransferConversionFactor();
        this.maxTransferAmount = builder.getMaxTransferAmount();
        this.maxPayoutAmount = builder.getMaxPayoutAmount();
        this.maxPayoutEarnCode = builder.getMaxPayoutEarnCode();
        this.maxUsage = builder.getMaxUsage();
        this.maxCarryOver = builder.getMaxCarryOver();
        this.lmAccrualCategoryId = builder.getLmAccrualCategoryId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getServiceUnitOfTime() {
        return this.serviceUnitOfTime;
    }

    @Override
    public Long getStart() {
        return this.start;
    }

    @Override
    public Long getEnd() {
        return this.end;
    }

    @Override
    public BigDecimal getAccrualRate() {
        return this.accrualRate;
    }

    @Override
    public BigDecimal getMaxBalance() {
        return this.maxBalance;
    }

    @Override
    public String getLmAccrualCategoryRuleId() {
        return this.lmAccrualCategoryRuleId;
    }

    @Override
    public String getMaxBalFlag() {
        return this.maxBalFlag;
    }

    @Override
    public String getMaxBalanceActionFrequency() {
        return this.maxBalanceActionFrequency;
    }

    @Override
    public String getActionAtMaxBalance() {
        return this.actionAtMaxBalance;
    }

    @Override
    public String getMaxBalanceTransferToAccrualCategory() {
        return this.maxBalanceTransferToAccrualCategory;
    }

    @Override
    public BigDecimal getMaxBalanceTransferConversionFactor() {
        return this.maxBalanceTransferConversionFactor;
    }

    @Override
    public Long getMaxTransferAmount() {
        return this.maxTransferAmount;
    }

    @Override
    public Long getMaxPayoutAmount() {
        return this.maxPayoutAmount;
    }

    @Override
    public String getMaxPayoutEarnCode() {
        return this.maxPayoutEarnCode;
    }

    @Override
    public Long getMaxUsage() {
        return this.maxUsage;
    }

    @Override
    public Long getMaxCarryOver() {
        return this.maxCarryOver;
    }

    @Override
    public String getLmAccrualCategoryId() {
        return this.lmAccrualCategoryId;
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
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }


    /**
     * A builder which can be used to construct {@link AccrualCategoryRule} instances.  Enforces the constraints of the {@link AccrualCategoryRuleContract}.
     *
     */
    public final static class Builder
            implements Serializable, AccrualCategoryRuleContract, ModelBuilder
    {

        private String serviceUnitOfTime;
        private Long start;
        private Long end;
        private BigDecimal accrualRate;
        private BigDecimal maxBalance;
        private String lmAccrualCategoryRuleId;
        private String maxBalFlag;
        private String maxBalanceActionFrequency;
        private String actionAtMaxBalance;
        private String maxBalanceTransferToAccrualCategory;
        private BigDecimal maxBalanceTransferConversionFactor;
        private Long maxTransferAmount;
        private Long maxPayoutAmount;
        private String maxPayoutEarnCode;
        private Long maxUsage;
        private Long maxCarryOver;
        private String lmAccrualCategoryId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;
        private String userPrincipalId;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public static Builder create(AccrualCategoryRuleContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create();
            builder.setServiceUnitOfTime(contract.getServiceUnitOfTime());
            builder.setStart(contract.getStart());
            builder.setEnd(contract.getEnd());
            builder.setAccrualRate(contract.getAccrualRate());
            builder.setMaxBalance(contract.getMaxBalance());
            builder.setLmAccrualCategoryRuleId(contract.getLmAccrualCategoryRuleId());
            builder.setMaxBalFlag(contract.getMaxBalFlag());
            builder.setMaxBalanceActionFrequency(contract.getMaxBalanceActionFrequency());
            builder.setActionAtMaxBalance(contract.getActionAtMaxBalance());
            builder.setMaxBalanceTransferToAccrualCategory(contract.getMaxBalanceTransferToAccrualCategory());
            builder.setMaxBalanceTransferConversionFactor(contract.getMaxBalanceTransferConversionFactor());
            builder.setMaxTransferAmount(contract.getMaxTransferAmount());
            builder.setMaxPayoutAmount(contract.getMaxPayoutAmount());
            builder.setMaxPayoutEarnCode(contract.getMaxPayoutEarnCode());
            builder.setMaxUsage(contract.getMaxUsage());
            builder.setMaxCarryOver(contract.getMaxCarryOver());
            builder.setLmAccrualCategoryId(contract.getLmAccrualCategoryId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public AccrualCategoryRule build() {
            return new AccrualCategoryRule(this);
        }

        @Override
        public String getServiceUnitOfTime() {
            return this.serviceUnitOfTime;
        }

        @Override
        public Long getStart() {
            return this.start;
        }

        @Override
        public Long getEnd() {
            return this.end;
        }

        @Override
        public BigDecimal getAccrualRate() {
            return this.accrualRate;
        }

        @Override
        public BigDecimal getMaxBalance() {
            return this.maxBalance;
        }

        @Override
        public String getLmAccrualCategoryRuleId() {
            return this.lmAccrualCategoryRuleId;
        }

        @Override
        public String getMaxBalFlag() {
            return this.maxBalFlag;
        }

        @Override
        public String getMaxBalanceActionFrequency() {
            return this.maxBalanceActionFrequency;
        }

        @Override
        public String getActionAtMaxBalance() {
            return this.actionAtMaxBalance;
        }

        @Override
        public String getMaxBalanceTransferToAccrualCategory() {
            return this.maxBalanceTransferToAccrualCategory;
        }

        @Override
        public BigDecimal getMaxBalanceTransferConversionFactor() {
            return this.maxBalanceTransferConversionFactor;
        }

        @Override
        public Long getMaxTransferAmount() {
            return this.maxTransferAmount;
        }

        @Override
        public Long getMaxPayoutAmount() {
            return this.maxPayoutAmount;
        }

        @Override
        public String getMaxPayoutEarnCode() {
            return this.maxPayoutEarnCode;
        }

        @Override
        public Long getMaxUsage() {
            return this.maxUsage;
        }

        @Override
        public Long getMaxCarryOver() {
            return this.maxCarryOver;
        }

        @Override
        public String getLmAccrualCategoryId() {
            return this.lmAccrualCategoryId;
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
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        public void setServiceUnitOfTime(String serviceUnitOfTime) {
            this.serviceUnitOfTime = serviceUnitOfTime;
        }

        public void setStart(Long start) {
            this.start = start;
        }

        public void setEnd(Long end) {
            this.end = end;
        }

        public void setAccrualRate(BigDecimal accrualRate) {
            this.accrualRate = accrualRate;
        }

        public void setMaxBalance(BigDecimal maxBalance) {
            this.maxBalance = maxBalance;
        }

        public void setLmAccrualCategoryRuleId(String lmAccrualCategoryRuleId) {
            this.lmAccrualCategoryRuleId = lmAccrualCategoryRuleId;
        }

        public void setMaxBalFlag(String maxBalFlag) {
            this.maxBalFlag = maxBalFlag;
        }

        public void setMaxBalanceActionFrequency(String maxBalanceActionFrequency) {
            this.maxBalanceActionFrequency = maxBalanceActionFrequency;
        }

        public void setActionAtMaxBalance(String actionAtMaxBalance) {
            this.actionAtMaxBalance = actionAtMaxBalance;
        }

        public void setMaxBalanceTransferToAccrualCategory(String maxBalanceTransferToAccrualCategory) {
            this.maxBalanceTransferToAccrualCategory = maxBalanceTransferToAccrualCategory;
        }

        public void setMaxBalanceTransferConversionFactor(BigDecimal maxBalanceTransferConversionFactor) {
            this.maxBalanceTransferConversionFactor = maxBalanceTransferConversionFactor;
        }

        public void setMaxTransferAmount(Long maxTransferAmount) {
            this.maxTransferAmount = maxTransferAmount;
        }

        public void setMaxPayoutAmount(Long maxPayoutAmount) {
            this.maxPayoutAmount = maxPayoutAmount;
        }

        public void setMaxPayoutEarnCode(String maxPayoutEarnCode) {
            this.maxPayoutEarnCode = maxPayoutEarnCode;
        }

        public void setMaxUsage(Long maxUsage) {
            this.maxUsage = maxUsage;
        }

        public void setMaxCarryOver(Long maxCarryOver) {
            this.maxCarryOver = maxCarryOver;
        }

        public void setLmAccrualCategoryId(String lmAccrualCategoryId) {
            this.lmAccrualCategoryId = lmAccrualCategoryId;
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

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            this.effectiveLocalDate = effectiveLocalDate;
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

        final static String ROOT_ELEMENT_NAME = "accrualCategoryRule";
        final static String TYPE_NAME = "AccrualCategoryRuleType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String SERVICE_UNIT_OF_TIME = "serviceUnitOfTime";
        final static String START = "start";
        final static String END = "end";
        final static String ACCRUAL_RATE = "accrualRate";
        final static String MAX_BALANCE = "maxBalance";
        final static String LM_ACCRUAL_CATEGORY_RULE_ID = "lmAccrualCategoryRuleId";
        final static String MAX_BAL_FLAG = "maxBalFlag";
        final static String MAX_BALANCE_ACTION_FREQUENCY = "maxBalanceActionFrequency";
        final static String ACTION_AT_MAX_BALANCE = "actionAtMaxBalance";
        final static String MAX_BALANCE_TRANSFER_TO_ACCRUAL_CATEGORY = "maxBalanceTransferToAccrualCategory";
        final static String MAX_BALANCE_TRANSFER_CONVERSION_FACTOR = "maxBalanceTransferConversionFactor";
        final static String MAX_TRANSFER_AMOUNT = "maxTransferAmount";
        final static String MAX_PAYOUT_AMOUNT = "maxPayoutAmount";
        final static String MAX_PAYOUT_EARN_CODE = "maxPayoutEarnCode";
        final static String MAX_USAGE = "maxUsage";
        final static String MAX_CARRY_OVER = "maxCarryOver";
        final static String LM_ACCRUAL_CATEGORY_ID = "lmAccrualCategoryId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}
