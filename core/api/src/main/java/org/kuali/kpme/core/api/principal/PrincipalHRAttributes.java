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
package org.kuali.kpme.core.api.principal;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = PrincipalHRAttributes.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = PrincipalHRAttributes.Constants.TYPE_NAME, propOrder = {
        PrincipalHRAttributes.Elements.NAME,
        PrincipalHRAttributes.Elements.LEAVE_PLAN,
        PrincipalHRAttributes.Elements.SERVICE_LOCAL_DATE,
        PrincipalHRAttributes.Elements.FMLA_ELIGIBLE,
        PrincipalHRAttributes.Elements.WORKERS_COMP_ELIGIBLE,
        PrincipalHRAttributes.Elements.TIMEZONE,
        PrincipalHRAttributes.Elements.CALENDAR,
        PrincipalHRAttributes.Elements.LEAVE_PLAN_OBJ,
        PrincipalHRAttributes.Elements.PRINCIPAL_ID,
        PrincipalHRAttributes.Elements.PAY_CALENDAR,
        PrincipalHRAttributes.Elements.LEAVE_CALENDAR,
        PrincipalHRAttributes.Elements.LEAVE_CAL_OBJ,
        PrincipalHRAttributes.Elements.HR_PRINCIPAL_ATTRIBUTE_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        PrincipalHRAttributes.Elements.ACTIVE,
        PrincipalHRAttributes.Elements.ID,
        PrincipalHRAttributes.Elements.EFFECTIVE_LOCAL_DATE,
        PrincipalHRAttributes.Elements.CREATE_TIME,
        PrincipalHRAttributes.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class PrincipalHRAttributes
        extends AbstractDataTransferObject
        implements PrincipalHRAttributesContract
{

    private static final long serialVersionUID = -6579919450361951325L;
    @XmlElement(name = Elements.NAME, required = false)
    private final String name;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElement(name = Elements.SERVICE_LOCAL_DATE, required = false)
    private final LocalDate serviceLocalDate;
    @XmlElement(name = Elements.FMLA_ELIGIBLE, required = false)
    private final boolean fmlaEligible;
    @XmlElement(name = Elements.WORKERS_COMP_ELIGIBLE, required = false)
    private final boolean workersCompEligible;
    @XmlElement(name = Elements.TIMEZONE, required = false)
    private final String timezone;
    @XmlElement(name = Elements.CALENDAR, required = false)
    private final Calendar calendar;
    @XmlElement(name = Elements.LEAVE_PLAN_OBJ, required = false)
    private final LeavePlan leavePlanObj;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.PAY_CALENDAR, required = false)
    private final String payCalendar;
    @XmlElement(name = Elements.LEAVE_CALENDAR, required = false)
    private final String leaveCalendar;
    @XmlElement(name = Elements.LEAVE_CAL_OBJ, required = false)
    private final Calendar leaveCalObj;
    @XmlElement(name = Elements.HR_PRINCIPAL_ATTRIBUTE_ID, required = false)
    private final String hrPrincipalAttributeId;
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
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private PrincipalHRAttributes() {
        this.name = "";
        this.leavePlan = null;
        this.serviceLocalDate = null;
        this.fmlaEligible = false;
        this.workersCompEligible = false;
        this.timezone = null;
        this.calendar = null;
        this.leavePlanObj = null;
        this.principalId = null;
        this.payCalendar = null;
        this.leaveCalendar = null;
        this.leaveCalObj = null;
        this.hrPrincipalAttributeId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private PrincipalHRAttributes(Builder builder) {
        this.name = builder.getName();
        this.leavePlan = builder.getLeavePlan();
        this.serviceLocalDate = builder.getServiceLocalDate();
        this.fmlaEligible = builder.isFmlaEligible();
        this.workersCompEligible = builder.isWorkersCompEligible();
        this.timezone = builder.getTimezone();
        this.calendar = builder.getCalendar() == null ? null : builder.getCalendar().build();
        this.leavePlanObj = builder.getLeavePlanObj() == null ? null : builder.getLeavePlanObj().build();
        this.principalId = builder.getPrincipalId();
        this.payCalendar = builder.getPayCalendar();
        this.leaveCalendar = builder.getLeaveCalendar();
        this.leaveCalObj = builder.getLeaveCalObj() == null ? null : builder.getLeaveCalObj().build();
        this.hrPrincipalAttributeId = builder.getHrPrincipalAttributeId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public LocalDate getServiceLocalDate() {
        return this.serviceLocalDate;
    }

    @Override
    public boolean isFmlaEligible() {
        return this.fmlaEligible;
    }

    @Override
    public boolean isWorkersCompEligible() {
        return this.workersCompEligible;
    }

    @Override
    public String getTimezone() {
        return this.timezone;
    }

    @Override
    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override
    public LeavePlan getLeavePlanObj() {
        return this.leavePlanObj;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public String getPayCalendar() {
        return this.payCalendar;
    }

    @Override
    public String getLeaveCalendar() {
        return this.leaveCalendar;
    }

    @Override
    public Calendar getLeaveCalObj() {
        return this.leaveCalObj;
    }

    @Override
    public String getHrPrincipalAttributeId() {
        return this.hrPrincipalAttributeId;
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
     * A builder which can be used to construct {@link PrincipalHRAttributes} instances.  Enforces the constraints of the {@link PrincipalHRAttributesContract}.
     *
     */
    public final static class Builder
            implements Serializable, PrincipalHRAttributesContract, ModelBuilder
    {

        private String name;
        private String leavePlan;
        private LocalDate serviceLocalDate;
        private boolean fmlaEligible;
        private boolean workersCompEligible;
        private String timezone;
        private Calendar.Builder calendar;
        private LeavePlan.Builder leavePlanObj;
        private String principalId;
        private String payCalendar;
        private String leaveCalendar;
        private Calendar.Builder leaveCalObj;
        private String hrPrincipalAttributeId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder(String principalId) {
            setPrincipalId(principalId);
        }

        public static Builder create(String principalId) {
            return new Builder(principalId);
        }

        public static Builder create(PrincipalHRAttributesContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPrincipalId());
            builder.setName(contract.getName());
            builder.setLeavePlan(contract.getLeavePlan());
            builder.setServiceLocalDate(contract.getServiceLocalDate());
            builder.setFmlaEligible(contract.isFmlaEligible());
            builder.setWorkersCompEligible(contract.isWorkersCompEligible());
            builder.setTimezone(contract.getTimezone());
            builder.setCalendar(contract.getCalendar() == null ? null : Calendar.Builder.create(contract.getCalendar()));
            builder.setLeavePlanObj(contract.getLeavePlanObj() == null ? null : LeavePlan.Builder.create(contract.getLeavePlanObj()));
            builder.setPayCalendar(contract.getPayCalendar());
            builder.setLeaveCalendar(contract.getLeaveCalendar());
            builder.setLeaveCalObj(contract.getLeaveCalObj() == null ? null : Calendar.Builder.create(contract.getLeaveCalObj()));
            builder.setHrPrincipalAttributeId(contract.getHrPrincipalAttributeId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public PrincipalHRAttributes build() {
            return new PrincipalHRAttributes(this);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public LocalDate getServiceLocalDate() {
            return this.serviceLocalDate;
        }

        @Override
        public boolean isFmlaEligible() {
            return this.fmlaEligible;
        }

        @Override
        public boolean isWorkersCompEligible() {
            return this.workersCompEligible;
        }

        @Override
        public String getTimezone() {
            return this.timezone;
        }

        @Override
        public Calendar.Builder getCalendar() {
            return this.calendar;
        }

        @Override
        public LeavePlan.Builder getLeavePlanObj() {
            return this.leavePlanObj;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public String getPayCalendar() {
            return this.payCalendar;
        }

        @Override
        public String getLeaveCalendar() {
            return this.leaveCalendar;
        }

        @Override
        public Calendar.Builder getLeaveCalObj() {
            return this.leaveCalObj;
        }

        @Override
        public String getHrPrincipalAttributeId() {
            return this.hrPrincipalAttributeId;
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

        public void setName(String name) {
            this.name = name;
        }

        public void setLeavePlan(String leavePlan) {
            this.leavePlan = leavePlan;
        }

        public void setServiceLocalDate(LocalDate serviceLocalDate) {
            this.serviceLocalDate = serviceLocalDate;
        }

        public void setFmlaEligible(boolean fmlaEligible) {
            this.fmlaEligible = fmlaEligible;
        }

        public void setWorkersCompEligible(boolean workersCompEligible) {
            this.workersCompEligible = workersCompEligible;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setCalendar(Calendar.Builder calendar) {
            this.calendar = calendar;
        }

        public void setLeavePlanObj(LeavePlan.Builder leavePlanObj) {
            this.leavePlanObj = leavePlanObj;
        }

        public void setPrincipalId(String principalId) {
            if (StringUtils.isBlank(principalId)) {
                throw new IllegalArgumentException("principalId is blank");
            }
            this.principalId = principalId;
        }

        public void setPayCalendar(String payCalendar) {
            this.payCalendar = payCalendar;
        }

        public void setLeaveCalendar(String leaveCalendar) {
            this.leaveCalendar = leaveCalendar;
        }

        public void setLeaveCalObj(Calendar.Builder leaveCalObj) {
            this.leaveCalObj = leaveCalObj;
        }

        public void setHrPrincipalAttributeId(String hrPrincipalAttributeId) {
            this.hrPrincipalAttributeId = hrPrincipalAttributeId;
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

        final static String ROOT_ELEMENT_NAME = "principalHRAttributes";
        final static String TYPE_NAME = "PrincipalHRAttributesType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String NAME = "name";
        final static String LEAVE_PLAN = "leavePlan";
        final static String SERVICE_LOCAL_DATE = "serviceLocalDate";
        final static String FMLA_ELIGIBLE = "fmlaEligible";
        final static String WORKERS_COMP_ELIGIBLE = "workersCompEligible";
        final static String TIMEZONE = "timezone";
        final static String CALENDAR = "calendar";
        final static String LEAVE_PLAN_OBJ = "leavePlanObj";
        final static String PRINCIPAL_ID = "principalId";
        final static String PAY_CALENDAR = "payCalendar";
        final static String LEAVE_CALENDAR = "leaveCalendar";
        final static String LEAVE_CAL_OBJ = "leaveCalObj";
        final static String HR_PRINCIPAL_ATTRIBUTE_ID = "hrPrincipalAttributeId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}