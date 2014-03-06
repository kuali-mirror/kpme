package org.kuali.kpme.core.api.leaveplan;

import java.io.Serializable;
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
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.util.jaxb.LocalTimeAdapter;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = LeavePlan.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = LeavePlan.Constants.TYPE_NAME, propOrder = {
        LeavePlan.Elements.BATCH_PRIOR_YEAR_CARRY_OVER_START_LOCAL_TIME,
        LeavePlan.Elements.PLANNING_MONTHS,
        LeavePlan.Elements.LM_LEAVE_PLAN_ID,
        LeavePlan.Elements.LEAVE_PLAN,
        LeavePlan.Elements.DESCR,
        LeavePlan.Elements.CALENDAR_YEAR_START,
        LeavePlan.Elements.BATCH_PRIOR_YEAR_CARRY_OVER_START_DATE,
        LeavePlan.Elements.ACTIVE,
        LeavePlan.Elements.ID,
        LeavePlan.Elements.EFFECTIVE_LOCAL_DATE,
        LeavePlan.Elements.CREATE_TIME,
        LeavePlan.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class LeavePlan
        extends AbstractDataTransferObject
        implements LeavePlanContract
{

    private static final long serialVersionUID = 4561466234557863358L;
    @XmlElement(name = Elements.BATCH_PRIOR_YEAR_CARRY_OVER_START_LOCAL_TIME, required = false)
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private final LocalTime batchPriorYearCarryOverStartLocalTime;
    @XmlElement(name = Elements.PLANNING_MONTHS, required = false)
    private final String planningMonths;
    @XmlElement(name = Elements.LM_LEAVE_PLAN_ID, required = false)
    private final String lmLeavePlanId;
    @XmlElement(name = Elements.LEAVE_PLAN, required = false)
    private final String leavePlan;
    @XmlElement(name = Elements.DESCR, required = false)
    private final String descr;
    @XmlElement(name = Elements.CALENDAR_YEAR_START, required = false)
    private final String calendarYearStart;
    @XmlElement(name = Elements.BATCH_PRIOR_YEAR_CARRY_OVER_START_DATE, required = false)
    private final String batchPriorYearCarryOverStartDate;
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
    private LeavePlan() {
        this.batchPriorYearCarryOverStartLocalTime = null;
        this.planningMonths = null;
        this.lmLeavePlanId = null;
        this.leavePlan = null;
        this.descr = null;
        this.calendarYearStart = null;
        this.batchPriorYearCarryOverStartDate = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private LeavePlan(Builder builder) {
        this.batchPriorYearCarryOverStartLocalTime = builder.getBatchPriorYearCarryOverStartLocalTime();
        this.planningMonths = builder.getPlanningMonths();
        this.lmLeavePlanId = builder.getLmLeavePlanId();
        this.leavePlan = builder.getLeavePlan();
        this.descr = builder.getDescr();
        this.calendarYearStart = builder.getCalendarYearStart();
        this.batchPriorYearCarryOverStartDate = builder.getBatchPriorYearCarryOverStartDate();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public LocalTime getBatchPriorYearCarryOverStartLocalTime() {
        return this.batchPriorYearCarryOverStartLocalTime;
    }

    @Override
    public String getPlanningMonths() {
        return this.planningMonths;
    }

    @Override
    public String getLmLeavePlanId() {
        return this.lmLeavePlanId;
    }

    @Override
    public String getLeavePlan() {
        return this.leavePlan;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public String getCalendarYearStart() {
        return this.calendarYearStart;
    }

    @Override
    public String getBatchPriorYearCarryOverStartDate() {
        return this.batchPriorYearCarryOverStartDate;
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

    public String getCalendarYearStartMonth() {
        if (StringUtils.isEmpty(getCalendarYearStart())) {
            return "01";
        }
        String[] date = getCalendarYearStart().split("/");
        return date[0];
    }

    public String getCalendarYearStartDayOfMonth() {
        if (StringUtils.isEmpty(getCalendarYearStart())) {
            return "01";
        }
        String[] date = getCalendarYearStart().split("/");
        return date[1];
    }


    /**
     * A builder which can be used to construct {@link LeavePlan} instances.  Enforces the constraints of the {@link LeavePlanContract}.
     *
     */
    public final static class Builder
            implements Serializable, LeavePlanContract, ModelBuilder
    {

        private LocalTime batchPriorYearCarryOverStartLocalTime;
        private String planningMonths;
        private String lmLeavePlanId;
        private String leavePlan;
        private String descr;
        private String calendarYearStart;
        private String batchPriorYearCarryOverStartDate;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder(String leavePlan) {
            setLeavePlan(leavePlan);
        }

        public static Builder create(String leavePlan) {
            return new Builder(leavePlan);
        }

        public static Builder create(LeavePlanContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getLeavePlan());
            builder.setBatchPriorYearCarryOverStartLocalTime(contract.getBatchPriorYearCarryOverStartLocalTime());
            builder.setPlanningMonths(contract.getPlanningMonths());
            builder.setLmLeavePlanId(contract.getLmLeavePlanId());
            builder.setDescr(contract.getDescr());
            builder.setCalendarYearStart(contract.getCalendarYearStart());
            builder.setBatchPriorYearCarryOverStartDate(contract.getBatchPriorYearCarryOverStartDate());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public LeavePlan build() {
            return new LeavePlan(this);
        }

        @Override
        public LocalTime getBatchPriorYearCarryOverStartLocalTime() {
            return this.batchPriorYearCarryOverStartLocalTime;
        }

        @Override
        public String getPlanningMonths() {
            return this.planningMonths;
        }

        @Override
        public String getLmLeavePlanId() {
            return this.lmLeavePlanId;
        }

        @Override
        public String getLeavePlan() {
            return this.leavePlan;
        }

        @Override
        public String getDescr() {
            return this.descr;
        }

        @Override
        public String getCalendarYearStart() {
            return this.calendarYearStart;
        }

        @Override
        public String getBatchPriorYearCarryOverStartDate() {
            return this.batchPriorYearCarryOverStartDate;
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

        public void setBatchPriorYearCarryOverStartLocalTime(LocalTime batchPriorYearCarryOverStartLocalTime) {
            this.batchPriorYearCarryOverStartLocalTime = batchPriorYearCarryOverStartLocalTime;
        }

        public void setPlanningMonths(String planningMonths) {
            this.planningMonths = planningMonths;
        }

        public void setLmLeavePlanId(String lmLeavePlanId) {
            this.lmLeavePlanId = lmLeavePlanId;
        }

        public void setLeavePlan(String leavePlan) {
            if (StringUtils.isWhitespace(leavePlan)) {
                throw new IllegalArgumentException("leavePlan is blank");
            }
            this.leavePlan = leavePlan;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public void setCalendarYearStart(String calendarYearStart) {
            this.calendarYearStart = calendarYearStart;
        }

        public void setBatchPriorYearCarryOverStartDate(String batchPriorYearCarryOverStartDate) {
            this.batchPriorYearCarryOverStartDate = batchPriorYearCarryOverStartDate;
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

        final static String ROOT_ELEMENT_NAME = "leavePlan";
        final static String TYPE_NAME = "LeavePlanType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String BATCH_PRIOR_YEAR_CARRY_OVER_START_LOCAL_TIME = "batchPriorYearCarryOverStartLocalTime";
        final static String PLANNING_MONTHS = "planningMonths";
        final static String LM_LEAVE_PLAN_ID = "lmLeavePlanId";
        final static String LEAVE_PLAN = "leavePlan";
        final static String DESCR = "descr";
        final static String CALENDAR_YEAR_START = "calendarYearStart";
        final static String BATCH_PRIOR_YEAR_CARRY_OVER_START_DATE = "batchPriorYearCarryOverStartDate";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}