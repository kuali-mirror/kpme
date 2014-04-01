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
package org.kuali.kpme.pm.api.position.funding;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.position.Position;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PositionFunding.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PositionFunding.Constants.TYPE_NAME, propOrder = {
    PositionFunding.Elements.SUB_OBJECT_CODE,
    PositionFunding.Elements.PM_POSITION_FUNCTION_ID,
    PositionFunding.Elements.PRIORITY_FLAG,
    PositionFunding.Elements.OBJECT_CODE,
    PositionFunding.Elements.ORG_REF_CODE,
    PositionFunding.Elements.ACCOUNT,
    PositionFunding.Elements.SUB_ACCOUNT,
    PositionFunding.Elements.ORG,
    PositionFunding.Elements.AMOUNT,
    PositionFunding.Elements.CHART,
    PositionFunding.Elements.SOURCE,
    PositionFunding.Elements.PERCENT,
    PositionFunding.Elements.HR_POSITION_ID,
    PositionFunding.Elements.OWNER,
    PositionFunding.Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PositionFunding extends AbstractDataTransferObject implements PositionFundingContract {

	private static final long serialVersionUID = 4405475619470263919L;
	
	@XmlElement(name = Elements.SUB_OBJECT_CODE, required = false)
    private final String subObjectCode;
    @XmlElement(name = Elements.PM_POSITION_FUNCTION_ID, required = false)
    private final String pmPositionFunctionId;
    @XmlElement(name = Elements.PRIORITY_FLAG, required = false)
    private final boolean priorityFlag;
    @XmlElement(name = Elements.OBJECT_CODE, required = false)
    private final String objectCode;
    @XmlElement(name = Elements.ORG_REF_CODE, required = false)
    private final String orgRefCode;
    @XmlElement(name = Elements.ACCOUNT, required = false)
    private final String account;
    @XmlElement(name = Elements.SUB_ACCOUNT, required = false)
    private final String subAccount;
    @XmlElement(name = Elements.ORG, required = false)
    private final String org;
    @XmlElement(name = Elements.AMOUNT, required = false)
    private final BigDecimal amount;
    @XmlElement(name = Elements.CHART, required = false)
    private final String chart;
    @XmlElement(name = Elements.SOURCE, required = false)
    private final String source;
    @XmlElement(name = Elements.PERCENT, required = false)
    private final BigDecimal percent;
    @XmlElement(name = Elements.HR_POSITION_ID, required = false)
    private final String hrPositionId;
    @XmlElement(name = Elements.OWNER, required = false)
    private final Position owner;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE_OF_OWNER, required = false)
    private final LocalDate effectiveLocalDateOfOwner;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private PositionFunding() {
        this.subObjectCode = null;
        this.pmPositionFunctionId = null;
        this.priorityFlag = false;
        this.objectCode = null;
        this.orgRefCode = null;
        this.account = null;
        this.subAccount = null;
        this.org = null;
        this.amount = null;
        this.chart = null;
        this.source = null;
        this.percent = null;
        this.hrPositionId = null;
        this.owner = null;
        this.effectiveLocalDateOfOwner = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private PositionFunding(Builder builder) {
        this.subObjectCode = builder.getSubObjectCode();
        this.pmPositionFunctionId = builder.getPmPositionFunctionId();
        this.priorityFlag = builder.isPriorityFlag();
        this.objectCode = builder.getObjectCode();
        this.orgRefCode = builder.getOrgRefCode();
        this.account = builder.getAccount();
        this.subAccount = builder.getSubAccount();
        this.org = builder.getOrg();
        this.amount = builder.getAmount();
        this.chart = builder.getChart();
        this.source = builder.getSource();
        this.percent = builder.getPercent();
        this.hrPositionId = builder.getHrPositionId();
        this.owner =  builder.getOwner() == null ? null : builder.getOwner().build();
        this.effectiveLocalDateOfOwner = builder.getEffectiveLocalDateOfOwner();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getSubObjectCode() {
        return this.subObjectCode;
    }

    @Override
    public String getPmPositionFunctionId() {
        return this.pmPositionFunctionId;
    }

    @Override
    public boolean isPriorityFlag() {
        return this.priorityFlag;
    }

    @Override
    public String getObjectCode() {
        return this.objectCode;
    }

    @Override
    public String getOrgRefCode() {
        return this.orgRefCode;
    }

    @Override
    public String getAccount() {
        return this.account;
    }

    @Override
    public String getSubAccount() {
        return this.subAccount;
    }

    @Override
    public String getOrg() {
        return this.org;
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public String getChart() {
        return this.chart;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public BigDecimal getPercent() {
        return this.percent;
    }
    
    @Override
    public String getHrPositionId() {
        return this.hrPositionId;
    }

    @Override
    public Position getOwner() {
        return this.owner;
    }

    @Override
    public LocalDate getEffectiveLocalDateOfOwner() {
        return this.effectiveLocalDateOfOwner;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }


    /**
     * A builder which can be used to construct {@link PositionFunding} instances.  Enforces the constraints of the {@link PositionFundingContract}.
     * 
     */
    public final static class Builder implements Serializable, PositionFundingContract, ModelBuilder {

		private static final long serialVersionUID = -6059757160893115256L;
		
		private String subObjectCode;
        private String pmPositionFunctionId;
        private boolean priorityFlag;
        private String objectCode;
        private String orgRefCode;
        private String account;
        private String subAccount;
        private String org;
        private BigDecimal amount;
        private String chart;
        private String source;
        private BigDecimal percent;
        private String hrPositionId;
        private Position.Builder owner;
        private LocalDate effectiveLocalDateOfOwner;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(PositionFundingContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setSubObjectCode(contract.getSubObjectCode());
            builder.setPmPositionFunctionId(contract.getPmPositionFunctionId());
            builder.setPriorityFlag(contract.isPriorityFlag());
            builder.setObjectCode(contract.getObjectCode());
            builder.setOrgRefCode(contract.getOrgRefCode());
            builder.setAccount(contract.getAccount());
            builder.setSubAccount(contract.getSubAccount());
            builder.setOrg(contract.getOrg());
            builder.setAmount(contract.getAmount());
            builder.setChart(contract.getChart());
            builder.setSource(contract.getSource());
            builder.setPercent(contract.getPercent());
            builder.setHrPositionId(contract.getHrPositionId());
            builder.setOwner(contract.getOwner() == null ? null : Position.Builder.create(contract.getOwner()));
            builder.setEffectiveLocalDateOfOwner(contract.getEffectiveLocalDateOfOwner());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public PositionFunding build() {
            return new PositionFunding(this);
        }

        @Override
        public String getSubObjectCode() {
            return this.subObjectCode;
        }

        @Override
        public String getPmPositionFunctionId() {
            return this.pmPositionFunctionId;
        }

        @Override
        public boolean isPriorityFlag() {
            return this.priorityFlag;
        }

        @Override
        public String getObjectCode() {
            return this.objectCode;
        }

        @Override
        public String getOrgRefCode() {
            return this.orgRefCode;
        }

        @Override
        public String getAccount() {
            return this.account;
        }

        @Override
        public String getSubAccount() {
            return this.subAccount;
        }

        @Override
        public String getOrg() {
            return this.org;
        }

        @Override
        public BigDecimal getAmount() {
            return this.amount;
        }

        @Override
        public String getChart() {
            return this.chart;
        }

        @Override
        public String getSource() {
            return this.source;
        }

        @Override
        public BigDecimal getPercent() {
            return this.percent;
        }
        
        @Override
        public String getHrPositionId() {
            return this.hrPositionId;
        }

        @Override
        public Position.Builder getOwner() {
            return this.owner;
        }

        @Override
        public LocalDate getEffectiveLocalDateOfOwner() {
            return this.effectiveLocalDateOfOwner;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setSubObjectCode(String subObjectCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.subObjectCode = subObjectCode;
        }

        public void setPmPositionFunctionId(String pmPositionFunctionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.pmPositionFunctionId = pmPositionFunctionId;
        }

        public void setPriorityFlag(boolean priorityFlag) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.priorityFlag = priorityFlag;
        }

        public void setObjectCode(String objectCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectCode = objectCode;
        }

        public void setOrgRefCode(String orgRefCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.orgRefCode = orgRefCode;
        }

        public void setAccount(String account) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.account = account;
        }

        public void setSubAccount(String subAccount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.subAccount = subAccount;
        }

        public void setOrg(String org) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.org = org;
        }

        public void setAmount(BigDecimal amount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.amount = amount;
        }

        public void setChart(String chart) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.chart = chart;
        }

        public void setSource(String source) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.source = source;
        }

        public void setPercent(BigDecimal percent) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.percent = percent;
        }
        
        public void setHrPositionId(String hrPositionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrPositionId = hrPositionId;
        }

        public void setOwner(Position.Builder owner) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.owner = owner;
        }

        public void setEffectiveLocalDateOfOwner(LocalDate effectiveLocalDateOfOwner) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDateOfOwner = effectiveLocalDateOfOwner;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "positionFunding";
        final static String TYPE_NAME = "PositionFundingType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String SUB_OBJECT_CODE = "subObjectCode";
        final static String PM_POSITION_FUNCTION_ID = "pmPositionFunctionId";
        final static String PRIORITY_FLAG = "priorityFlag";
        final static String OBJECT_CODE = "objectCode";
        final static String ORG_REF_CODE = "orgRefCode";
        final static String ACCOUNT = "account";
        final static String SUB_ACCOUNT = "subAccount";
        final static String ORG = "org";
        final static String AMOUNT = "amount";
        final static String CHART = "chart";
        final static String SOURCE = "source";
        final static String PERCENT = "percent";
        final static String HR_POSITION_ID = "hrPositionId";
        final static String OWNER = "owner";
        final static String EFFECTIVE_LOCAL_DATE_OF_OWNER = "effectiveLocalDateOfOwner";

    }

}

