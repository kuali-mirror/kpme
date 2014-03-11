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
package org.kuali.kpme.core.api.salarygroup;

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

@XmlRootElement(name = SalaryGroup.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = SalaryGroup.Constants.TYPE_NAME, propOrder = {
        SalaryGroup.Elements.LOCATION,
        SalaryGroup.Elements.HR_SAL_GROUP_ID,
        SalaryGroup.Elements.HR_SAL_GROUP,
        SalaryGroup.Elements.DESCR,
        SalaryGroup.Elements.INSTITUTION,
        SalaryGroup.Elements.PERCENT_TIME,
        SalaryGroup.Elements.BENEFITS_ELIGIBLE,
        SalaryGroup.Elements.LEAVE_ELIGIBLE,
        SalaryGroup.Elements.LEAVE_PLAN,
        SalaryGroup.Elements.ACTIVE,
        SalaryGroup.Elements.ID,
        SalaryGroup.Elements.CREATE_TIME,
        SalaryGroup.Elements.EFFECTIVE_LOCAL_DATE,
        SalaryGroup.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class SalaryGroup
        extends AbstractDataTransferObject
        implements SalaryGroupContract
{

    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.HR_SAL_GROUP_ID, required = false)
    private final String hrSalGroupId;
    @XmlElement(name = Elements.HR_SAL_GROUP, required = false)
    private final String hrSalGroup;
    @XmlElement(name = Elements.DESCR, required = false)
    private final String descr;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = Elements.PERCENT_TIME, required = false)
    private final BigDecimal percentTime;
    @XmlElement(name = Elements.BENEFITS_ELIGIBLE, required = false)
    private final String benefitsEligible;
    @XmlElement(name = Elements.LEAVE_ELIGIBLE, required = false)
    private final String leaveEligible;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
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
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private SalaryGroup() {
        this.location = null;
        this.hrSalGroupId = null;
        this.hrSalGroup = null;
        this.descr = null;
        this.institution = null;
        this.percentTime = null;
        this.benefitsEligible = null;
        this.leaveEligible = null;
        this.leavePlan = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
        this.userPrincipalId = null;
    }

    private SalaryGroup(Builder builder) {
        this.location = builder.getLocation();
        this.hrSalGroupId = builder.getHrSalGroupId();
        this.hrSalGroup = builder.getHrSalGroup();
        this.descr = builder.getDescr();
        this.institution = builder.getInstitution();
        this.percentTime = builder.getPercentTime();
        this.benefitsEligible = builder.getBenefitsEligible();
        this.leaveEligible = builder.getLeaveEligible();
        this.leavePlan = builder.getLeavePlan();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getHrSalGroupId() {
        return this.hrSalGroupId;
    }

    @Override
    public String getHrSalGroup() {
        return this.hrSalGroup;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public String getInstitution() {
        return this.institution;
    }

    @Override
    public BigDecimal getPercentTime() {
        return this.percentTime;
    }

    @Override
    public String getBenefitsEligible() {
        return this.benefitsEligible;
    }

    @Override
    public String getLeaveEligible() {
        return this.leaveEligible;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
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
     * A builder which can be used to construct {@link SalaryGroup} instances.  Enforces the constraints of the {@link SalaryGroupContract}.
     *
     */
    public final static class Builder
            implements Serializable, SalaryGroupContract, ModelBuilder
    {

        private String location;
        private String hrSalGroupId;
        private String hrSalGroup;
        private String descr;
        private String institution;
        private BigDecimal percentTime;
        private String benefitsEligible;
        private String leaveEligible;
        private String leavePlan;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;
        private String userPrincipalId;

        private Builder(String hrSalGroup) {
            setHrSalGroup(hrSalGroup);
        }

        public static Builder create(String hrSalGroup) {
            return new Builder(hrSalGroup);
        }

        public static Builder create(SalaryGroupContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getHrSalGroup());
            builder.setLocation(contract.getLocation());
            builder.setHrSalGroupId(contract.getHrSalGroupId());
            builder.setDescr(contract.getDescr());
            builder.setInstitution(contract.getInstitution());
            builder.setPercentTime(contract.getPercentTime());
            builder.setBenefitsEligible(contract.getBenefitsEligible());
            builder.setLeaveEligible(contract.getLeaveEligible());
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public SalaryGroup build() {
            return new SalaryGroup(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getHrSalGroupId() {
            return this.hrSalGroupId;
        }

        @Override
        public String getHrSalGroup() {
            return this.hrSalGroup;
        }

        @Override
        public String getDescr() {
            return this.descr;
        }

        @Override
        public String getInstitution() {
            return this.institution;
        }

        @Override
        public BigDecimal getPercentTime() {
            return this.percentTime;
        }

        @Override
        public String getBenefitsEligible() {
            return this.benefitsEligible;
        }

        @Override
        public String getLeaveEligible() {
            return this.leaveEligible;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
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

        public void setLocation(String location) {
            this.location = location;
        }

        public void setHrSalGroupId(String hrSalGroupId) {
            this.hrSalGroupId = hrSalGroupId;
        }

        public void setHrSalGroup(String hrSalGroup) {
            if (StringUtils.isEmpty(hrSalGroup)) {
                throw new IllegalArgumentException("hrSalGroup is blank");
            }
            this.hrSalGroup = hrSalGroup;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public void setPercentTime(BigDecimal percentTime) {
            this.percentTime = percentTime;
        }

        public void setBenefitsEligible(String benefitsEligible) {
            this.benefitsEligible = benefitsEligible;
        }

        public void setLeaveEligible(String leaveEligible) {
            this.leaveEligible = leaveEligible;
        }

        public void setLeavePlan(String leavePlan) {
            this.leavePlan = leavePlan;
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

        final static String ROOT_ELEMENT_NAME = "salaryGroup";
        final static String TYPE_NAME = "SalaryGroupType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String LOCATION = "location";
        final static String HR_SAL_GROUP_ID = "hrSalGroupId";
        final static String HR_SAL_GROUP = "hrSalGroup";
        final static String DESCR = "descr";
        final static String INSTITUTION = "institution";
        final static String PERCENT_TIME = "percentTime";
        final static String BENEFITS_ELIGIBLE = "benefitsEligible";
        final static String LEAVE_ELIGIBLE = "leaveEligible";
        final static String LEAVE_PLAN = "leavePlan";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}