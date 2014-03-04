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
package org.kuali.kpme.tklm.api.time.timehourdetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = TimeHourDetail.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = TimeHourDetail.Constants.TYPE_NAME, propOrder = {
        TimeHourDetail.Elements.HOURS,
        TimeHourDetail.Elements.TK_TIME_BLOCK_ID,
        TimeHourDetail.Elements.TK_TIME_HOUR_DETAIL_ID,
        TimeHourDetail.Elements.EARN_CODE,
        TimeHourDetail.Elements.AMOUNT,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class TimeHourDetail
        extends AbstractDataTransferObject
        implements TimeHourDetailContract
{

    @XmlElement(name = Elements.HOURS, required = false)
    private final BigDecimal hours;
    @XmlElement(name = Elements.TK_TIME_BLOCK_ID, required = false)
    private final String tkTimeBlockId;
    @XmlElement(name = Elements.TK_TIME_HOUR_DETAIL_ID, required = false)
    private final String tkTimeHourDetailId;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.AMOUNT, required = false)
    private final BigDecimal amount;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private TimeHourDetail() {
        this.hours = null;
        this.tkTimeBlockId = null;
        this.tkTimeHourDetailId = null;
        this.earnCode = null;
        this.amount = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private TimeHourDetail(Builder builder) {
        this.hours = builder.getHours();
        this.tkTimeBlockId = builder.getTkTimeBlockId();
        this.tkTimeHourDetailId = builder.getTkTimeHourDetailId();
        this.earnCode = builder.getEarnCode();
        this.amount = builder.getAmount();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public BigDecimal getHours() {
        return this.hours;
    }

    @Override
    public String getTkTimeBlockId() {
        return this.tkTimeBlockId;
    }

    @Override
    public String getTkTimeHourDetailId() {
        return this.tkTimeHourDetailId;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount;
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
     * A builder which can be used to construct {@link TimeHourDetail} instances.  Enforces the constraints of the {@link TimeHourDetailContract}.
     *
     */
    public final static class Builder
            implements Serializable, TimeHourDetailContract, ModelBuilder
    {

        private BigDecimal hours;
        private String tkTimeBlockId;
        private String tkTimeHourDetailId;
        private String earnCode;
        private BigDecimal amount;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(TimeHourDetailContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setHours(contract.getHours());
            builder.setTkTimeBlockId(contract.getTkTimeBlockId());
            builder.setTkTimeHourDetailId(contract.getTkTimeHourDetailId());
            builder.setEarnCode(contract.getEarnCode());
            builder.setAmount(contract.getAmount());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public TimeHourDetail build() {
            return new TimeHourDetail(this);
        }

        @Override
        public BigDecimal getHours() {
            return this.hours;
        }

        @Override
        public String getTkTimeBlockId() {
            return this.tkTimeBlockId;
        }

        @Override
        public String getTkTimeHourDetailId() {
            return this.tkTimeHourDetailId;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public BigDecimal getAmount() {
            return this.amount;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setHours(BigDecimal hours) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hours = hours;
        }

        public void setTkTimeBlockId(String tkTimeBlockId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.tkTimeBlockId = tkTimeBlockId;
        }

        public void setTkTimeHourDetailId(String tkTimeHourDetailId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.tkTimeHourDetailId = tkTimeHourDetailId;
        }

        public void setEarnCode(String earnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCode = earnCode;
        }

        public void setAmount(BigDecimal amount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.amount = amount;
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

        final static String ROOT_ELEMENT_NAME = "timeHourDetail";
        final static String TYPE_NAME = "TimeHourDetailType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String HOURS = "hours";
        final static String TK_TIME_BLOCK_ID = "tkTimeBlockId";
        final static String TK_TIME_HOUR_DETAIL_ID = "tkTimeHourDetailId";
        final static String EARN_CODE = "earnCode";
        final static String AMOUNT = "amount";

    }

}
