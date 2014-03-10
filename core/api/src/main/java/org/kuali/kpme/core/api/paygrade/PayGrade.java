package org.kuali.kpme.core.api.paygrade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = PayGrade.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PayGrade.Constants.TYPE_NAME, propOrder = {
        PayGrade.Elements.LOCATION,
        PayGrade.Elements.RATE_TYPE,
        PayGrade.Elements.MIN_RATE,
        PayGrade.Elements.MAX_RATE,
        PayGrade.Elements.HR_PAY_GRADE_ID,
        PayGrade.Elements.PAY_GRADE,
        PayGrade.Elements.DESCRIPTION,
        PayGrade.Elements.MID_POINT_RATE,
        PayGrade.Elements.MAX_HIRING_RATE,
        PayGrade.Elements.USER_PRINCIPAL_ID,
        PayGrade.Elements.SAL_GROUP,
        PayGrade.Elements.INSTITUTION,
        PayGrade.Elements.ACTIVE,
        PayGrade.Elements.ID,
        PayGrade.Elements.CREATE_TIME,
        PayGrade.Elements.EFFECTIVE_LOCAL_DATE,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PayGrade
        extends AbstractDataTransferObject
        implements PayGradeContract
{

    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.RATE_TYPE, required = false)
    private final String rateType;
    @XmlElement(name = Elements.MIN_RATE, required = false)
    private final BigDecimal minRate;
    @XmlElement(name = Elements.MAX_RATE, required = false)
    private final BigDecimal maxRate;
    @XmlElement(name = Elements.HR_PAY_GRADE_ID, required = false)
    private final String hrPayGradeId;
    @XmlElement(name = Elements.PAY_GRADE, required = false)
    private final String payGrade;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.MID_POINT_RATE, required = false)
    private final BigDecimal midPointRate;
    @XmlElement(name = Elements.MAX_HIRING_RATE, required = false)
    private final BigDecimal maxHiringRate;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.SAL_GROUP, required = false)
    private final String salGroup;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private PayGrade() {
        this.location = null;
        this.rateType = null;
        this.minRate = null;
        this.maxRate = null;
        this.hrPayGradeId = null;
        this.payGrade = null;
        this.description = null;
        this.midPointRate = null;
        this.maxHiringRate = null;
        this.userPrincipalId = null;
        this.salGroup = null;
        this.institution = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
    }

    private PayGrade(Builder builder) {
        this.location = builder.getLocation();
        this.rateType = builder.getRateType();
        this.minRate = builder.getMinRate();
        this.maxRate = builder.getMaxRate();
        this.hrPayGradeId = builder.getHrPayGradeId();
        this.payGrade = builder.getPayGrade();
        this.description = builder.getDescription();
        this.midPointRate = builder.getMidPointRate();
        this.maxHiringRate = builder.getMaxHiringRate();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.salGroup = builder.getSalGroup();
        this.institution = builder.getInstitution();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getRateType() {
        return this.rateType;
    }

    @Override
    public BigDecimal getMinRate() {
        return this.minRate;
    }

    @Override
    public BigDecimal getMaxRate() {
        return this.maxRate;
    }

    @Override
    public String getHrPayGradeId() {
        return this.hrPayGradeId;
    }

    @Override
    public String getPayGrade() {
        return this.payGrade;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public BigDecimal getMidPointRate() {
        return this.midPointRate;
    }

    @Override
    public BigDecimal getMaxHiringRate() {
        return this.maxHiringRate;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getSalGroup() {
        return this.salGroup;
    }

    @Override
    public String getInstitution() {
        return this.institution;
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


    /**
     * A builder which can be used to construct {@link PayGrade} instances.  Enforces the constraints of the {@link PayGradeContract}.
     *
     */
    public final static class Builder
            implements Serializable, PayGradeContract, ModelBuilder
    {

        private String location;
        private String rateType;
        private BigDecimal minRate;
        private BigDecimal maxRate;
        private String hrPayGradeId;
        private String payGrade;
        private String description;
        private BigDecimal midPointRate;
        private BigDecimal maxHiringRate;
        private String userPrincipalId;
        private String salGroup;
        private String institution;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;

        private Builder(String payGrade, String salGroup) {
            setPayGrade(payGrade);
            setSalGroup(salGroup);
        }

        public static Builder create(String payGrade, String salGroup) {
            return new Builder(payGrade, salGroup);
        }

        public static Builder create(PayGradeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPayGrade(), contract.getSalGroup());
            builder.setLocation(contract.getLocation());
            builder.setRateType(contract.getRateType());
            builder.setMinRate(contract.getMinRate());
            builder.setMaxRate(contract.getMaxRate());
            builder.setHrPayGradeId(contract.getHrPayGradeId());
            builder.setPayGrade(contract.getPayGrade());
            builder.setDescription(contract.getDescription());
            builder.setMidPointRate(contract.getMidPointRate());
            builder.setMaxHiringRate(contract.getMaxHiringRate());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setSalGroup(contract.getSalGroup());
            builder.setInstitution(contract.getInstitution());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            return builder;
        }

        public PayGrade build() {
            return new PayGrade(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getRateType() {
            return this.rateType;
        }

        @Override
        public BigDecimal getMinRate() {
            return this.minRate;
        }

        @Override
        public BigDecimal getMaxRate() {
            return this.maxRate;
        }

        @Override
        public String getHrPayGradeId() {
            return this.hrPayGradeId;
        }

        @Override
        public String getPayGrade() {
            return this.payGrade;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public BigDecimal getMidPointRate() {
            return this.midPointRate;
        }

        @Override
        public BigDecimal getMaxHiringRate() {
            return this.maxHiringRate;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getSalGroup() {
            return this.salGroup;
        }

        @Override
        public String getInstitution() {
            return this.institution;
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

        public void setLocation(String location) {
            this.location = location;
        }

        public void setRateType(String rateType) {
            this.rateType = rateType;
        }

        public void setMinRate(BigDecimal minRate) {
            this.minRate = minRate;
        }

        public void setMaxRate(BigDecimal maxRate) {
            this.maxRate = maxRate;
        }

        public void setHrPayGradeId(String hrPayGradeId) {
            this.hrPayGradeId = hrPayGradeId;
        }

        public void setPayGrade(String payGrade) {
            if (StringUtils.isEmpty(payGrade)) {
                throw new IllegalArgumentException("payGrade is blank");
            }
            this.payGrade = payGrade;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setMidPointRate(BigDecimal midPointRate) {
            this.midPointRate = midPointRate;
        }

        public void setMaxHiringRate(BigDecimal maxHiringRate) {
            this.maxHiringRate = maxHiringRate;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

        public void setSalGroup(String salGroup) {
            if (StringUtils.isEmpty(salGroup)) {
                throw new IllegalArgumentException("salGroup is blank");
            }
            this.salGroup = salGroup;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
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

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "payGrade";
        final static String TYPE_NAME = "PayGradeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String LOCATION = "location";
        final static String RATE_TYPE = "rateType";
        final static String MIN_RATE = "minRate";
        final static String MAX_RATE = "maxRate";
        final static String HR_PAY_GRADE_ID = "hrPayGradeId";
        final static String PAY_GRADE = "payGrade";
        final static String DESCRIPTION = "description";
        final static String MID_POINT_RATE = "midPointRate";
        final static String MAX_HIRING_RATE = "maxHiringRate";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String SAL_GROUP = "salGroup";
        final static String INSTITUTION = "institution";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";

    }

}