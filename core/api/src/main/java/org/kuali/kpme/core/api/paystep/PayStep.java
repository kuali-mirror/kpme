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
package org.kuali.kpme.core.api.paystep;

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
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PayStep.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PayStep.Constants.TYPE_NAME, propOrder = {
    PayStep.Elements.COMP_RATE,
    PayStep.Elements.STEP_NUMBER,
    PayStep.Elements.PAY_GRADE,
    PayStep.Elements.PM_PAY_STEP_ID,
    PayStep.Elements.SERVICE_UNIT,
    PayStep.Elements.PAY_STEP,
    PayStep.Elements.SALARY_GROUP,
    PayStep.Elements.SERVICE_AMOUNT,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    PayStep.Elements.ACTIVE,
    PayStep.Elements.ID,
    PayStep.Elements.EFFECTIVE_LOCAL_DATE,
    PayStep.Elements.CREATE_TIME,
    PayStep.Elements.USER_PRINCIPAL_ID,
    PayStep.Elements.GROUP_KEY,
    PayStep.Elements.GROUP_KEY_CODE,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PayStep
    extends AbstractDataTransferObject
    implements PayStepContract
{

    @XmlElement(name = Elements.COMP_RATE, required = false)
    private final BigDecimal compRate;
    @XmlElement(name = Elements.STEP_NUMBER, required = false)
    private final int stepNumber;
    @XmlElement(name = Elements.PAY_GRADE, required = false)
    private final String payGrade;
    @XmlElement(name = Elements.PM_PAY_STEP_ID, required = false)
    private final String pmPayStepId;
    @XmlElement(name = Elements.SERVICE_UNIT, required = false)
    private final String serviceUnit;
    @XmlElement(name = Elements.PAY_STEP, required = false)
    private final String payStep;
    @XmlElement(name = Elements.SALARY_GROUP, required = false)
    private final String salaryGroup;
    @XmlElement(name = Elements.SERVICE_AMOUNT, required = false)
    private final int serviceAmount;
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
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKeyContract groupKey;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PayStep() {
        this.compRate = null;
        this.stepNumber = 0;
        this.payGrade = null;
        this.pmPayStepId = null;
        this.serviceUnit = null;
        this.payStep = null;
        this.salaryGroup = null;
        this.serviceAmount = 0;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKey = null;
        this.groupKeyCode = null;
    }

    private PayStep(Builder builder) {
        this.compRate = builder.getCompRate();
        this.stepNumber = builder.getStepNumber();
        this.payGrade = builder.getPayGrade();
        this.pmPayStepId = builder.getPmPayStepId();
        this.serviceUnit = builder.getServiceUnit();
        this.payStep = builder.getPayStep();
        this.salaryGroup = builder.getSalaryGroup();
        this.serviceAmount = builder.getServiceAmount();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKey = builder.getGroupKey();
        this.groupKeyCode = builder.getGroupKeyCode();
    }

    @Override
    public BigDecimal getCompRate() {
        return this.compRate;
    }

    @Override
    public int getStepNumber() {
        return this.stepNumber;
    }

    @Override
    public String getPayGrade() {
        return this.payGrade;
    }

    @Override
    public String getPmPayStepId() {
        return this.pmPayStepId;
    }

    @Override
    public String getServiceUnit() {
        return this.serviceUnit;
    }

    @Override
    public String getPayStep() {
        return this.payStep;
    }

    @Override
    public String getSalaryGroup() {
        return this.salaryGroup;
    }

    @Override
    public int getServiceAmount() {
        return this.serviceAmount;
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

    @Override
    public HrGroupKeyContract getGroupKey() {
        return this.groupKey;
    }

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }


    /**
     * A builder which can be used to construct {@link PayStep} instances.  Enforces the constraints of the {@link PayStepContract}.
     * 
     */
    public final static class Builder
        implements Serializable, PayStepContract, ModelBuilder
    {

        private BigDecimal compRate;
        private int stepNumber;
        private String payGrade;
        private String pmPayStepId;
        private String serviceUnit;
        private String payStep;
        private String salaryGroup;
        private int serviceAmount;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private HrGroupKeyContract groupKey;
        private String groupKeyCode;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(PayStepContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setCompRate(contract.getCompRate());
            builder.setStepNumber(contract.getStepNumber());
            builder.setPayGrade(contract.getPayGrade());
            builder.setPmPayStepId(contract.getPmPayStepId());
            builder.setServiceUnit(contract.getServiceUnit());
            builder.setPayStep(contract.getPayStep());
            builder.setSalaryGroup(contract.getSalaryGroup());
            builder.setServiceAmount(contract.getServiceAmount());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKey(contract.getGroupKey());
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            return builder;
        }

        public PayStep build() {
            return new PayStep(this);
        }

        @Override
        public BigDecimal getCompRate() {
            return this.compRate;
        }

        @Override
        public int getStepNumber() {
            return this.stepNumber;
        }

        @Override
        public String getPayGrade() {
            return this.payGrade;
        }

        @Override
        public String getPmPayStepId() {
            return this.pmPayStepId;
        }

        @Override
        public String getServiceUnit() {
            return this.serviceUnit;
        }

        @Override
        public String getPayStep() {
            return this.payStep;
        }

        @Override
        public String getSalaryGroup() {
            return this.salaryGroup;
        }

        @Override
        public int getServiceAmount() {
            return this.serviceAmount;
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

        @Override
        public HrGroupKeyContract getGroupKey() {
            return this.groupKey;
        }

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        public void setCompRate(BigDecimal compRate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.compRate = compRate;
        }

        public void setStepNumber(int stepNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.stepNumber = stepNumber;
        }

        public void setPayGrade(String payGrade) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.payGrade = payGrade;
        }

        public void setPmPayStepId(String pmPayStepId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPayStepId = pmPayStepId;
        }

        public void setServiceUnit(String serviceUnit) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.serviceUnit = serviceUnit;
        }

        public void setPayStep(String payStep) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.payStep = payStep;
        }

        public void setSalaryGroup(String salaryGroup) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.salaryGroup = salaryGroup;
        }

        public void setServiceAmount(int serviceAmount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.serviceAmount = serviceAmount;
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

        public void setGroupKey(HrGroupKeyContract groupKey) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKey = groupKey;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeyCode = groupKeyCode;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "payStep";
        final static String TYPE_NAME = "PayStepType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String COMP_RATE = "compRate";
        final static String STEP_NUMBER = "stepNumber";
        final static String PAY_GRADE = "payGrade";
        final static String PM_PAY_STEP_ID = "pmPayStepId";
        final static String SERVICE_UNIT = "serviceUnit";
        final static String PAY_STEP = "payStep";
        final static String SALARY_GROUP = "salaryGroup";
        final static String SERVICE_AMOUNT = "serviceAmount";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY = "groupKey";
        final static String GROUP_KEY_CODE = "groupKeyCode";

    }

}

