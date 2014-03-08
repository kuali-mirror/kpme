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
package org.kuali.kpme.core.api.paytype;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = PayType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PayType.Constants.TYPE_NAME, propOrder = {
        PayType.Elements.LOCATION,
        PayType.Elements.REG_EARN_CODE_OBJ,
        PayType.Elements.PAY_TYPE,
        PayType.Elements.DESCR,
        PayType.Elements.REG_EARN_CODE,
        PayType.Elements.HR_PAY_TYPE_ID,
        PayType.Elements.HR_EARN_CODE_ID,
        PayType.Elements.OVT_EARN_CODE,
        PayType.Elements.INSTITUTION,
        PayType.Elements.FLSA_STATUS,
        PayType.Elements.PAY_FREQUENCY,
        PayType.Elements.ACTIVE,
        PayType.Elements.ID,
        PayType.Elements.EFFECTIVE_LOCAL_DATE,
        PayType.Elements.CREATE_TIME,
        PayType.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PayType
        extends AbstractDataTransferObject
        implements PayTypeContract
{

    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.REG_EARN_CODE_OBJ, required = false)
    private final EarnCode regEarnCodeObj;
    @XmlElement(name = Elements.PAY_TYPE, required = false)
    private final String payType;
    @XmlElement(name = Elements.DESCR, required = false)
    private final String descr;
    @XmlElement(name = Elements.REG_EARN_CODE, required = false)
    private final String regEarnCode;
    @XmlElement(name = Elements.HR_PAY_TYPE_ID, required = false)
    private final String hrPayTypeId;
    @XmlElement(name = Elements.HR_EARN_CODE_ID, required = false)
    private final String hrEarnCodeId;
    @XmlElement(name = Elements.OVT_EARN_CODE, required = false)
    private final Boolean ovtEarnCode;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = Elements.FLSA_STATUS, required = false)
    private final String flsaStatus;
    @XmlElement(name = Elements.PAY_FREQUENCY, required = false)
    private final String payFrequency;
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
    private PayType() {
        this.location = null;
        this.regEarnCodeObj = null;
        this.payType = null;
        this.descr = null;
        this.regEarnCode = null;
        this.hrPayTypeId = null;
        this.hrEarnCodeId = null;
        this.ovtEarnCode = null;
        this.institution = null;
        this.flsaStatus = null;
        this.payFrequency = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private PayType(Builder builder) {
        this.location = builder.getLocation();
        this.regEarnCodeObj = builder.getRegEarnCodeObj() == null ? null : builder.getRegEarnCodeObj().build();
        this.payType = builder.getPayType();
        this.descr = builder.getDescr();
        this.regEarnCode = builder.getRegEarnCode();
        this.hrPayTypeId = builder.getHrPayTypeId();
        this.hrEarnCodeId = builder.getHrEarnCodeId();
        this.ovtEarnCode = builder.isOvtEarnCode();
        this.institution = builder.getInstitution();
        this.flsaStatus = builder.getFlsaStatus();
        this.payFrequency = builder.getPayFrequency();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public EarnCode getRegEarnCodeObj() {
        return this.regEarnCodeObj;
    }

    @Override
    public String getPayType() {
        return this.payType;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public String getRegEarnCode() {
        return this.regEarnCode;
    }

    @Override
    public String getHrPayTypeId() {
        return this.hrPayTypeId;
    }

    @Override
    public String getHrEarnCodeId() {
        return this.hrEarnCodeId;
    }

    @Override
    public Boolean isOvtEarnCode() {
        return this.ovtEarnCode;
    }

    @Override
    public String getInstitution() {
        return this.institution;
    }

    @Override
    public String getFlsaStatus() {
        return this.flsaStatus;
    }

    @Override
    public String getPayFrequency() {
        return this.payFrequency;
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
     * A builder which can be used to construct {@link PayType} instances.  Enforces the constraints of the {@link PayTypeContract}.
     *
     */
    public final static class Builder
            implements Serializable, PayTypeContract, ModelBuilder
    {

        private String location;
        private EarnCode.Builder regEarnCodeObj;
        private String payType;
        private String descr;
        private String regEarnCode;
        private String hrPayTypeId;
        private String hrEarnCodeId;
        private Boolean ovtEarnCode;
        private String institution;
        private String flsaStatus;
        private String payFrequency;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder(String payType) {
            setPayType(payType);
        }

        public static Builder create(String payType) {
            return new Builder(payType);
        }

        public static Builder create(PayTypeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPayType());
            builder.setLocation(contract.getLocation());
            builder.setRegEarnCodeObj(contract.getRegEarnCodeObj() == null ? null : EarnCode.Builder.create(contract.getRegEarnCodeObj()));
            builder.setDescr(contract.getDescr());
            builder.setRegEarnCode(contract.getRegEarnCode());
            builder.setHrPayTypeId(contract.getHrPayTypeId());
            builder.setHrEarnCodeId(contract.getHrEarnCodeId());
            builder.setOvtEarnCode(contract.isOvtEarnCode());
            builder.setInstitution(contract.getInstitution());
            builder.setFlsaStatus(contract.getFlsaStatus());
            builder.setPayFrequency(contract.getPayFrequency());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PayType build() {
            return new PayType(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public EarnCode.Builder getRegEarnCodeObj() {
            return this.regEarnCodeObj;
        }

        @Override
        public String getPayType() {
            return this.payType;
        }

        @Override
        public String getDescr() {
            return this.descr;
        }

        @Override
        public String getRegEarnCode() {
            return this.regEarnCode;
        }

        @Override
        public String getHrPayTypeId() {
            return this.hrPayTypeId;
        }

        @Override
        public String getHrEarnCodeId() {
            return this.hrEarnCodeId;
        }

        @Override
        public Boolean isOvtEarnCode() {
            return this.ovtEarnCode;
        }

        @Override
        public String getInstitution() {
            return this.institution;
        }

        @Override
        public String getFlsaStatus() {
            return this.flsaStatus;
        }

        @Override
        public String getPayFrequency() {
            return this.payFrequency;
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

        public void setLocation(String location) {
            this.location = location;
        }

        public void setRegEarnCodeObj(EarnCode.Builder regEarnCodeObj) {
            this.regEarnCodeObj = regEarnCodeObj;
        }

        public void setPayType(String payType) {
            if (StringUtils.isEmpty(payType)) {
                throw new IllegalArgumentException("payType is blank");
            }
            this.payType = payType;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public void setRegEarnCode(String regEarnCode) {
            this.regEarnCode = regEarnCode;
        }

        public void setHrPayTypeId(String hrPayTypeId) {
            this.hrPayTypeId = hrPayTypeId;
        }

        public void setHrEarnCodeId(String hrEarnCodeId) {
            this.hrEarnCodeId = hrEarnCodeId;
        }

        public void setOvtEarnCode(Boolean ovtEarnCode) {
            this.ovtEarnCode = ovtEarnCode;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public void setFlsaStatus(String flsaStatus) {
            this.flsaStatus = flsaStatus;
        }

        public void setPayFrequency(String payFrequency) {
            this.payFrequency = payFrequency;
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

        final static String ROOT_ELEMENT_NAME = "payType";
        final static String TYPE_NAME = "PayTypeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String LOCATION = "location";
        final static String REG_EARN_CODE_OBJ = "regEarnCodeObj";
        final static String PAY_TYPE = "payType";
        final static String DESCR = "descr";
        final static String REG_EARN_CODE = "regEarnCode";
        final static String HR_PAY_TYPE_ID = "hrPayTypeId";
        final static String HR_EARN_CODE_ID = "hrEarnCodeId";
        final static String OVT_EARN_CODE = "ovtEarnCode";
        final static String INSTITUTION = "institution";
        final static String FLSA_STATUS = "flsaStatus";
        final static String PAY_FREQUENCY = "payFrequency";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}
