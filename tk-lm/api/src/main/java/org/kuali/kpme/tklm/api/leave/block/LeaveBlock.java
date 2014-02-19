package org.kuali.kpme.tklm.api.leave.block;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = LeaveBlock.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = LeaveBlock.Constants.TYPE_NAME, propOrder = {
        LeaveBlock.Elements.TASK,
        LeaveBlock.Elements.EARN_CODE,
        LeaveBlock.Elements.ACCRUAL_CATEGORY_RULE,
        LeaveBlock.Elements.ACCRUAL_CATEGORY_OBJ,
        LeaveBlock.Elements.ACCRUAL_CATEGORY_RULE_ID,
        LeaveBlock.Elements.ACCRUAL_CATEGORY,
        LeaveBlock.Elements.SUBMIT,
        LeaveBlock.Elements.BLOCK_ID,
        LeaveBlock.Elements.DESCRIPTION,
        LeaveBlock.Elements.ASSIGNMENT_TITLE,
        LeaveBlock.Elements.CALENDAR_ID,
        LeaveBlock.Elements.EARN_CODE_DESCRIPTION,
        LeaveBlock.Elements.LEAVE_BLOCK_TYPE,
        //LeaveBlock.Elements.EDITABLE,
        //LeaveBlock.Elements.DELETABLE,
        LeaveBlock.Elements.ASSIGNMENT_KEY,
        LeaveBlock.Elements.DOCUMENT_STATUS,
        LeaveBlock.Elements.LEAVE_REQUEST_DOCUMENT_ID,
        LeaveBlock.Elements.PLANNING_DESCRIPTION,
        LeaveBlock.Elements.TRANSACTIONAL_DOC_ID,
        LeaveBlock.Elements.BEGIN_DATE_TIME,
        LeaveBlock.Elements.END_DATE_TIME,
        LeaveBlock.Elements.LM_LEAVE_BLOCK_ID,
        LeaveBlock.Elements.LEAVE_AMOUNT,
        LeaveBlock.Elements.WORK_AREA,
        LeaveBlock.Elements.JOB_NUMBER,
        LeaveBlock.Elements.ACCRUAL_GENERATED,
        LeaveBlock.Elements.LEAVE_LOCAL_DATE,
        LeaveBlock.Elements.LEAVE_DATE_TIME,
        LeaveBlock.Elements.SCHEDULE_TIME_OFF_ID,
        LeaveBlock.Elements.REQUEST_STATUS,
        LeaveBlock.Elements.REQUEST_STATUS_STRING,
        LeaveBlock.Elements.REASON,
        LeaveBlock.Elements.HOURS,
        LeaveBlock.Elements.CREATE_TIME,
        LeaveBlock.Elements.HR_CALENDAR_BLOCK_ID,
        LeaveBlock.Elements.CONCRETE_BLOCK_TYPE,
        LeaveBlock.Elements.CONCRETE_BLOCK_ID,
        LeaveBlock.Elements.DOCUMENT_ID,
        LeaveBlock.Elements.PRINCIPAL_ID,
        LeaveBlock.Elements.AMOUNT,
        LeaveBlock.Elements.OVERTIME_PREF,
        LeaveBlock.Elements.LUNCH_DELETED,
        LeaveBlock.Elements.USER_PRINCIPAL_ID,
        LeaveBlock.Elements.AFFECTS_PAY,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class LeaveBlock
        extends AbstractDataTransferObject
        implements LeaveBlockContract
{

    @XmlElement(name = Elements.TASK, required = false)
    private final Long task;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY_RULE, required = false)
    private final AccrualCategoryRule accrualCategoryRule;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY_OBJ, required = false)
    private final AccrualCategory accrualCategoryObj;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY_RULE_ID, required = false)
    private final String accrualCategoryRuleId;
    @XmlElement(name = Elements.ACCRUAL_CATEGORY, required = false)
    private final String accrualCategory;
    @XmlElement(name = Elements.SUBMIT, required = false)
    private final boolean submit;
    @XmlElement(name = Elements.BLOCK_ID, required = false)
    private final Long blockId;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    //@XmlElement(name = Elements.LEAVE_DATE, required = false)
    //private final DateTime leaveDate;
    @XmlElement(name = Elements.ASSIGNMENT_TITLE, required = false)
    private final String assignmentTitle;
    @XmlElement(name = Elements.CALENDAR_ID, required = false)
    private final String calendarId;
    @XmlElement(name = Elements.EARN_CODE_DESCRIPTION, required = false)
    private final String earnCodeDescription;
    @XmlElement(name = Elements.LEAVE_BLOCK_TYPE, required = false)
    private final String leaveBlockType;
    //@XmlElement(name = Elements.EDITABLE, required = false)
    //private final boolean editable;
    //@XmlElement(name = Elements.DELETABLE, required = false)
    //private final boolean deletable;
    @XmlElement(name = Elements.ASSIGNMENT_KEY, required = false)
    private final String assignmentKey;
    @XmlElement(name = Elements.DOCUMENT_STATUS, required = false)
    private final String documentStatus;
    @XmlElement(name = Elements.LEAVE_REQUEST_DOCUMENT_ID, required = false)
    private final String leaveRequestDocumentId;
    @XmlElement(name = Elements.PLANNING_DESCRIPTION, required = false)
    private final String planningDescription;
    @XmlElement(name = Elements.TRANSACTIONAL_DOC_ID, required = false)
    private final String transactionalDocId;
    @XmlElement(name = Elements.BEGIN_DATE_TIME, required = false)
    private final DateTime beginDateTime;
    @XmlElement(name = Elements.END_DATE_TIME, required = false)
    private final DateTime endDateTime;
    @XmlElement(name = Elements.LM_LEAVE_BLOCK_ID, required = false)
    private final String lmLeaveBlockId;
    @XmlElement(name = Elements.LEAVE_AMOUNT, required = false)
    private final BigDecimal leaveAmount;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = Elements.JOB_NUMBER, required = false)
    private final Long jobNumber;
    @XmlElement(name = Elements.ACCRUAL_GENERATED, required = false)
    private final Boolean accrualGenerated;
    @XmlElement(name = Elements.LEAVE_LOCAL_DATE, required = false)
    private final LocalDate leaveLocalDate;
    @XmlElement(name = Elements.LEAVE_DATE_TIME, required = false)
    private final DateTime leaveDateTime;
    @XmlElement(name = Elements.SCHEDULE_TIME_OFF_ID, required = false)
    private final String scheduleTimeOffId;
    @XmlElement(name = Elements.REQUEST_STATUS, required = false)
    private final String requestStatus;
    @XmlElement(name = Elements.REQUEST_STATUS_STRING, required = false)
    private final String requestStatusString;
    @XmlElement(name = Elements.REASON, required = false)
    private final String reason;
    @XmlElement(name = Elements.HOURS, required = false)
    private final BigDecimal hours;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.HR_CALENDAR_BLOCK_ID, required = false)
    private final String hrCalendarBlockId;
    @XmlElement(name = Elements.CONCRETE_BLOCK_TYPE, required = false)
    private final String concreteBlockType;
    @XmlElement(name = Elements.CONCRETE_BLOCK_ID, required = false)
    private final String concreteBlockId;
    @XmlElement(name = Elements.DOCUMENT_ID, required = false)
    private final String documentId;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.AMOUNT, required = false)
    private final BigDecimal amount;
    @XmlElement(name = Elements.OVERTIME_PREF, required = false)
    private final String overtimePref;
    @XmlElement(name = Elements.LUNCH_DELETED, required = false)
    private final boolean lunchDeleted;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.AFFECTS_PAY, required = false)
    private final String affectsPay;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private LeaveBlock() {
        this.task = null;
        this.earnCode = null;
        this.accrualCategoryRule = null;
        this.accrualCategoryObj = null;
        this.accrualCategoryRuleId = null;
        this.accrualCategory = null;
        this.submit = false;
        this.blockId = null;
        this.description = null;
        this.leaveDateTime = null;
        this.assignmentTitle = null;
        this.calendarId = null;
        this.earnCodeDescription = null;
        this.leaveBlockType = null;
        //this.editable = false;
        //this.deletable = false;
        this.assignmentKey = null;
        this.documentStatus = null;
        this.leaveRequestDocumentId = null;
        this.planningDescription = null;
        this.transactionalDocId = null;
        this.beginDateTime = null;
        this.endDateTime = null;
        this.lmLeaveBlockId = null;
        this.leaveAmount = null;
        this.workArea = null;
        this.jobNumber = null;
        this.accrualGenerated = null;
        this.leaveLocalDate = null;
        this.scheduleTimeOffId = null;
        this.requestStatus = null;
        this.requestStatusString = null;
        this.reason = null;
        this.hours = null;
        this.createTime = null;
        this.hrCalendarBlockId = null;
        this.concreteBlockType = null;
        this.concreteBlockId = null;
        this.documentId = null;
        this.principalId = null;
        this.amount = null;
        this.overtimePref = null;
        this.lunchDeleted = false;
        this.userPrincipalId = null;
        this.affectsPay = null;
        this.objectId = null;
        this.versionNumber = null;
    }

    private LeaveBlock(Builder builder) {
        this.task = builder.getTask();
        this.earnCode = builder.getEarnCode();
        this.accrualCategoryRule = builder.getAccrualCategoryRule() == null ? null : builder.getAccrualCategoryRule().build();
        this.accrualCategoryObj = builder.getAccrualCategoryObj() == null ? null : builder.getAccrualCategoryObj().build();
        this.accrualCategoryRuleId = builder.getAccrualCategoryRuleId();
        this.accrualCategory = builder.getAccrualCategory();
        this.submit = builder.isSubmit();
        this.blockId = builder.getBlockId();
        this.description = builder.getDescription();
        this.leaveDateTime = builder.getLeaveDateTime() == null ? null : builder.getLeaveDateTime();
        this.assignmentTitle = builder.getAssignmentTitle();
        this.calendarId = builder.getCalendarId();
        this.earnCodeDescription = builder.getEarnCodeDescription();
        this.leaveBlockType = builder.getLeaveBlockType();
        //this.editable = builder.isEditable();
        //this.deletable = builder.isDeletable();
        this.assignmentKey = builder.getAssignmentKey();
        this.documentStatus = builder.getDocumentStatus();
        this.leaveRequestDocumentId = builder.getLeaveRequestDocumentId();
        this.planningDescription = builder.getPlanningDescription();
        this.transactionalDocId = builder.getTransactionalDocId();
        this.beginDateTime = builder.getBeginDateTime();
        this.endDateTime = builder.getEndDateTime();
        this.lmLeaveBlockId = builder.getLmLeaveBlockId();
        this.leaveAmount = builder.getLeaveAmount();
        this.workArea = builder.getWorkArea();
        this.jobNumber = builder.getJobNumber();
        this.accrualGenerated = builder.isAccrualGenerated();
        this.leaveLocalDate = builder.getLeaveLocalDate();
        this.scheduleTimeOffId = builder.getScheduleTimeOffId();
        this.requestStatus = builder.getRequestStatus();
        this.requestStatusString = builder.getRequestStatusString();
        this.reason = builder.getReason();
        this.hours = builder.getHours();
        this.createTime = builder.getCreateTime();
        this.hrCalendarBlockId = builder.getHrCalendarBlockId();
        this.concreteBlockType = builder.getConcreteBlockType();
        this.concreteBlockId = builder.getConcreteBlockId();
        this.documentId = builder.getDocumentId();
        this.principalId = builder.getPrincipalId();
        this.amount = builder.getAmount();
        this.overtimePref = builder.getOvertimePref();
        this.lunchDeleted = builder.isLunchDeleted();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.affectsPay = builder.getAffectPay();
        this.objectId = builder.getObjectId();
        this.versionNumber = builder.getVersionNumber();
    }

    @Override
    public Long getTask() {
        return this.task;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public AccrualCategoryRule getAccrualCategoryRule() {
        return this.accrualCategoryRule;
    }

    @Override
    public AccrualCategory getAccrualCategoryObj() {
        return this.accrualCategoryObj;
    }

    @Override
    public String getAccrualCategoryRuleId() {
        return this.accrualCategoryRuleId;
    }

    @Override
    public String getAccrualCategory() {
        return this.accrualCategory;
    }

    @Override
    public boolean isSubmit() {
        return this.submit;
    }

    @Override
    public Long getBlockId() {
        return this.blockId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public DateTime getLeaveDateTime() {
        return this.leaveDateTime;
    }

    @Override
    public String getAssignmentTitle() {
        return this.assignmentTitle;
    }

    @Override
    public String getCalendarId() {
        return this.calendarId;
    }

    @Override
    public String getEarnCodeDescription() {
        return this.earnCodeDescription;
    }

    @Override
    public String getLeaveBlockType() {
        return this.leaveBlockType;
    }

    //@Override
    //public boolean isEditable() {
    //    return this.editable;
    //}

    //@Override
    //public boolean isDeletable() {
    //    return this.deletable;
    //}

    @Override
    public String getAssignmentKey() {
        return this.assignmentKey;
    }

    @Override
    public String getDocumentStatus() {
        return this.documentStatus;
    }

    @Override
    public String getLeaveRequestDocumentId() {
        return this.leaveRequestDocumentId;
    }

    @Override
    public String getPlanningDescription() {
        return this.planningDescription;
    }

    @Override
    public String getTransactionalDocId() {
        return this.transactionalDocId;
    }

    @Override
    public DateTime getBeginDateTime() {
        return this.beginDateTime;
    }

    @Override
    public DateTime getEndDateTime() {
        return this.endDateTime;
    }

    @Override
    public String getLmLeaveBlockId() {
        return this.lmLeaveBlockId;
    }

    @Override
    public BigDecimal getLeaveAmount() {
        return this.leaveAmount;
    }

    @Override
    public Long getWorkArea() {
        return this.workArea;
    }

    @Override
    public Long getJobNumber() {
        return this.jobNumber;
    }

    @Override
    public Boolean isAccrualGenerated() {
        return this.accrualGenerated;
    }

    @Override
    public LocalDate getLeaveLocalDate() {
        return this.leaveLocalDate;
    }

    @Override
    public String getScheduleTimeOffId() {
        return this.scheduleTimeOffId;
    }

    @Override
    public String getRequestStatus() {
        return this.requestStatus;
    }

    @Override
    public String getRequestStatusString() {
        return this.requestStatusString;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public BigDecimal getHours() {
        return this.hours;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public String getHrCalendarBlockId() {
        return this.hrCalendarBlockId;
    }

    @Override
    public String getConcreteBlockType() {
        return this.concreteBlockType;
    }

    @Override
    public String getConcreteBlockId() {
        return this.concreteBlockId;
    }

    @Override
    public String getDocumentId() {
        return this.documentId;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public String getOvertimePref() {
        return this.overtimePref;
    }

    @Override
    public boolean isLunchDeleted() {
        return this.lunchDeleted;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getAffectPay() {
        return this.affectsPay;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        LeaveBlock leaveBlock = (LeaveBlock) obj;
        return new EqualsBuilder()
                .append(principalId, leaveBlock.principalId)
                .append(jobNumber, leaveBlock.jobNumber)
                .append(workArea, leaveBlock.workArea)
                .append(task, leaveBlock.task)
                .append(earnCode, leaveBlock.earnCode)
                .append(leaveDateTime, leaveBlock.leaveDateTime)
                .append(leaveAmount, leaveBlock.leaveAmount)
                .append(accrualCategory, leaveBlock.accrualCategory)
                .append(earnCode, leaveBlock.earnCode)
                .append(description, leaveBlock.description)
                .append(leaveBlockType, leaveBlock.leaveBlockType)
                .isEquals();
    }
    /**
     * A builder which can be used to construct {@link LeaveBlock} instances.  Enforces the constraints of the {@link LeaveBlockContract}.
     *
     */
    public final static class Builder
            implements Serializable, LeaveBlockContract, ModelBuilder
    {

        private Long task;
        private String earnCode;
        private AccrualCategoryRule.Builder accrualCategoryRule;
        private AccrualCategory.Builder accrualCategoryObj;
        private String accrualCategoryRuleId;
        private String accrualCategory;
        private boolean submit;
        private Long blockId;
        private String description;
        private DateTime leaveDateTime;
        private String assignmentTitle;
        private String calendarId;
        private String earnCodeDescription;
        private String leaveBlockType;
        //private boolean editable;
        //private boolean deletable;
        private String assignmentKey;
        private String documentStatus;
        private String leaveRequestDocumentId;
        private String planningDescription;
        private String transactionalDocId;
        private DateTime beginDateTime;
        private DateTime endDateTime;
        private String lmLeaveBlockId;
        private BigDecimal leaveAmount;
        private Long workArea;
        private Long jobNumber;
        private Boolean accrualGenerated;
        private LocalDate leaveLocalDate;
        private String scheduleTimeOffId;
        private String requestStatus;
        private String requestStatusString;
        private String reason;
        private BigDecimal hours;
        private DateTime createTime;
        private String hrCalendarBlockId;
        private String concreteBlockType;
        private String concreteBlockId;
        private String documentId;
        private String principalId;
        private BigDecimal amount;
        private String overtimePref;
        private boolean lunchDeleted;
        private String userPrincipalId;
        private String affectsPay;
        private String objectId;
        private Long versionNumber;

        private Builder(String principalId, String earnCode, BigDecimal leaveAmount) {
            setPrincipalId(principalId);
            setEarnCode(earnCode);
            setLeaveAmount(leaveAmount);
        }

        public static Builder create(String principalId, String earnCode, BigDecimal leaveAmount) {
            return new Builder(principalId, earnCode, leaveAmount);
        }

        public static Builder create(String principalId, String earnCode, BigDecimal leaveAmount, LocalDate leaveLocalDate, String documentId) {
            Builder builder = create(principalId, earnCode, leaveAmount);
            builder.setLeaveLocalDate(leaveLocalDate);
            builder.setDocumentId(documentId);
            return builder;
        }

        public static Builder create(LeaveBlockContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getPrincipalId(), contract.getEarnCode(), contract.getLeaveAmount());
            builder.setTask(contract.getTask());
            builder.setAccrualCategoryRule(contract.getAccrualCategoryRule() == null ? null : AccrualCategoryRule.Builder.create(contract.getAccrualCategoryRule()));
            builder.setAccrualCategoryObj(contract.getAccrualCategoryObj() == null ? null : AccrualCategory.Builder.create(contract.getAccrualCategoryObj()));
            builder.setAccrualCategoryRuleId(contract.getAccrualCategoryRuleId());
            builder.setAccrualCategory(contract.getAccrualCategory());
            builder.setSubmit(contract.isSubmit());
            builder.setBlockId(contract.getBlockId());
            builder.setDescription(contract.getDescription());
            builder.setLeaveDateTime(contract.getLeaveDateTime());
            builder.setAssignmentTitle(contract.getAssignmentTitle());
            builder.setCalendarId(contract.getCalendarId());
            builder.setEarnCodeDescription(contract.getEarnCodeDescription());
            builder.setLeaveBlockType(contract.getLeaveBlockType());
            //builder.setEditable(contract.isEditable());
            //builder.setDeletable(contract.isDeletable());
            builder.setAssignmentKey(contract.getAssignmentKey());
            builder.setDocumentStatus(contract.getDocumentStatus());
            builder.setLeaveRequestDocumentId(contract.getLeaveRequestDocumentId());
            builder.setPlanningDescription(contract.getPlanningDescription());
            builder.setTransactionalDocId(contract.getTransactionalDocId());
            builder.setBeginDateTime(contract.getBeginDateTime());
            builder.setEndDateTime(contract.getEndDateTime());
            builder.setLmLeaveBlockId(contract.getLmLeaveBlockId());
            builder.setWorkArea(contract.getWorkArea());
            builder.setJobNumber(contract.getJobNumber());
            builder.setAccrualGenerated(contract.isAccrualGenerated());
            builder.setLeaveLocalDate(contract.getLeaveLocalDate());
            builder.setScheduleTimeOffId(contract.getScheduleTimeOffId());
            builder.setRequestStatus(contract.getRequestStatus());
            builder.setRequestStatusString(contract.getRequestStatusString());
            builder.setReason(contract.getReason());
            builder.setHours(contract.getHours());
            builder.setCreateTime(contract.getCreateTime());
            builder.setHrCalendarBlockId(contract.getHrCalendarBlockId());
            builder.setConcreteBlockType(contract.getConcreteBlockType());
            builder.setConcreteBlockId(contract.getConcreteBlockId());
            builder.setDocumentId(contract.getDocumentId());
            builder.setAmount(contract.getAmount());
            builder.setOvertimePref(contract.getOvertimePref());
            builder.setLunchDeleted(contract.isLunchDeleted());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setAffectsPay(contract.getAffectPay());
            builder.setObjectId(contract.getObjectId());
            builder.setVersionNumber(contract.getVersionNumber());
            return builder;
        }

        public LeaveBlock build() {
            return new LeaveBlock(this);
        }

        @Override
        public Long getTask() {
            return this.task;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public AccrualCategoryRule.Builder getAccrualCategoryRule() {
            return this.accrualCategoryRule;
        }

        @Override
        public AccrualCategory.Builder getAccrualCategoryObj() {
            return this.accrualCategoryObj;
        }

        @Override
        public String getAccrualCategoryRuleId() {
            return this.accrualCategoryRuleId;
        }

        @Override
        public String getAccrualCategory() {
            return this.accrualCategory;
        }

        @Override
        public boolean isSubmit() {
            return this.submit;
        }

        @Override
        public Long getBlockId() {
            return this.blockId;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public DateTime getLeaveDateTime() {
            return this.leaveDateTime;
        }

        @Override
        public String getAssignmentTitle() {
            return this.assignmentTitle;
        }

        @Override
        public String getCalendarId() {
            return this.calendarId;
        }

        @Override
        public String getEarnCodeDescription() {
            return this.earnCodeDescription;
        }

        @Override
        public String getLeaveBlockType() {
            return this.leaveBlockType;
        }

        //@Override
        //public boolean isEditable() {
        //    return this.editable;
        //}

        //@Override
        //public boolean isDeletable() {
        //    return this.deletable;
        //}

        @Override
        public String getAssignmentKey() {
            return this.assignmentKey;
        }

        @Override
        public String getDocumentStatus() {
            return this.documentStatus;
        }

        @Override
        public String getLeaveRequestDocumentId() {
            return this.leaveRequestDocumentId;
        }

        @Override
        public String getPlanningDescription() {
            return this.planningDescription;
        }

        @Override
        public String getTransactionalDocId() {
            return this.transactionalDocId;
        }

        @Override
        public DateTime getBeginDateTime() {
            return this.beginDateTime;
        }

        @Override
        public DateTime getEndDateTime() {
            return this.endDateTime;
        }

        @Override
        public String getLmLeaveBlockId() {
            return this.lmLeaveBlockId;
        }

        @Override
        public BigDecimal getLeaveAmount() {
            return this.leaveAmount;
        }

        @Override
        public Long getWorkArea() {
            return this.workArea;
        }

        @Override
        public Long getJobNumber() {
            return this.jobNumber;
        }

        @Override
        public Boolean isAccrualGenerated() {
            return this.accrualGenerated;
        }

        @Override
        public LocalDate getLeaveLocalDate() {
            return this.leaveLocalDate;
        }

        @Override
        public String getScheduleTimeOffId() {
            return this.scheduleTimeOffId;
        }

        @Override
        public String getRequestStatus() {
            return this.requestStatus;
        }

        @Override
        public String getRequestStatusString() {
            return this.requestStatusString;
        }

        @Override
        public String getReason() {
            return this.reason;
        }

        @Override
        public BigDecimal getHours() {
            return this.hours;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public String getHrCalendarBlockId() {
            return this.hrCalendarBlockId;
        }

        @Override
        public String getConcreteBlockType() {
            return this.concreteBlockType;
        }

        @Override
        public String getConcreteBlockId() {
            return this.concreteBlockId;
        }

        @Override
        public String getDocumentId() {
            return this.documentId;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public BigDecimal getAmount() {
            return this.amount;
        }

        @Override
        public String getOvertimePref() {
            return this.overtimePref;
        }

        @Override
        public boolean isLunchDeleted() {
            return this.lunchDeleted;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getAffectPay() {
            return this.affectsPay;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        public void setTask(Long task) {
            
            this.task = task;
        }

        public void setEarnCode(String earnCode) {
            
            this.earnCode = earnCode;
        }

        public void setAccrualCategoryRule(AccrualCategoryRule.Builder accrualCategoryRule) {
            
            this.accrualCategoryRule = accrualCategoryRule;
        }

        public void setAccrualCategoryObj(AccrualCategory.Builder accrualCategoryObj) {
            
            this.accrualCategoryObj = accrualCategoryObj;
        }

        public void setAccrualCategoryRuleId(String accrualCategoryRuleId) {
            
            this.accrualCategoryRuleId = accrualCategoryRuleId;
        }

        public void setAccrualCategory(String accrualCategory) {
            
            this.accrualCategory = accrualCategory;
        }

        public void setSubmit(boolean submit) {
            
            this.submit = submit;
        }

        public void setBlockId(Long blockId) {
            
            this.blockId = blockId;
        }

        public void setDescription(String description) {
            
            this.description = description;
        }

        //public void setLeaveDate(Date leaveDate) {
            
        //   this.leaveDate = leaveDate;
        //}

        public void setAssignmentTitle(String assignmentTitle) {
            
            this.assignmentTitle = assignmentTitle;
        }

        public void setCalendarId(String calendarId) {
            
            this.calendarId = calendarId;
        }

        public void setEarnCodeDescription(String earnCodeDescription) {
            
            this.earnCodeDescription = earnCodeDescription;
        }

        public void setLeaveBlockType(String leaveBlockType) {
            
            this.leaveBlockType = leaveBlockType;
        }

        //public void setEditable(boolean editable) {
            
        //    this.editable = editable;
        //}

        //public void setDeletable(boolean deletable) {
            
        //    this.deletable = deletable;
        //}

        public void setAssignmentKey(String assignmentKey) {
            
            this.assignmentKey = assignmentKey;
        }

        public void setDocumentStatus(String documentStatus) {
            
            this.documentStatus = documentStatus;
        }

        public void setLeaveRequestDocumentId(String leaveRequestDocumentId) {
            
            this.leaveRequestDocumentId = leaveRequestDocumentId;
        }

        public void setPlanningDescription(String planningDescription) {
            
            this.planningDescription = planningDescription;
        }

        public void setTransactionalDocId(String transactionalDocId) {
            
            this.transactionalDocId = transactionalDocId;
        }

        public void setBeginDateTime(DateTime beginDateTime) {
            
            this.beginDateTime = beginDateTime;
        }

        public void setEndDateTime(DateTime endDateTime) {
            
            this.endDateTime = endDateTime;
        }

        public void setLmLeaveBlockId(String lmLeaveBlockId) {
            
            this.lmLeaveBlockId = lmLeaveBlockId;
        }

        public void setLeaveAmount(BigDecimal leaveAmount) {
            
            this.leaveAmount = leaveAmount;
        }

        public void setWorkArea(Long workArea) {
            
            this.workArea = workArea;
        }

        public void setJobNumber(Long jobNumber) {
            
            this.jobNumber = jobNumber;
        }

        public void setAccrualGenerated(Boolean accrualGenerated) {
            
            this.accrualGenerated = accrualGenerated;
        }

        public void setLeaveLocalDate(LocalDate leaveLocalDate) {
            
            this.leaveLocalDate = leaveLocalDate;
        }

        public void setScheduleTimeOffId(String scheduleTimeOffId) {
            
            this.scheduleTimeOffId = scheduleTimeOffId;
        }

        public void setRequestStatus(String requestStatus) {
            
            this.requestStatus = requestStatus;
        }

        public void setRequestStatusString(String requestStatusString) {
            
            this.requestStatusString = requestStatusString;
        }

        public void setReason(String reason) {
            
            this.reason = reason;
        }

        public void setHours(BigDecimal hours) {
            
            this.hours = hours;
        }

        public void setCreateTime(DateTime createTime) {
            
            this.createTime = createTime;
        }

        public void setHrCalendarBlockId(String hrCalendarBlockId) {
            
            this.hrCalendarBlockId = hrCalendarBlockId;
        }

        public void setConcreteBlockType(String concreteBlockType) {
            
            this.concreteBlockType = concreteBlockType;
        }

        public void setConcreteBlockId(String concreteBlockId) {
            
            this.concreteBlockId = concreteBlockId;
        }

        public void setDocumentId(String documentId) {
            
            this.documentId = documentId;
        }

        public void setPrincipalId(String principalId) {
            
            this.principalId = principalId;
        }

        public void setAmount(BigDecimal amount) {
            
            this.amount = amount;
        }

        public void setOvertimePref(String overtimePref) {
            
            this.overtimePref = overtimePref;
        }

        public void setLunchDeleted(boolean lunchDeleted) {
            
            this.lunchDeleted = lunchDeleted;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

        public void setAffectsPay(String affectsPay) {
            this.affectsPay = affectsPay;
        }

        public void setLeaveDateTime(DateTime leaveDateTime) {
            this.leaveDateTime = leaveDateTime;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }
    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "leaveBlock";
        final static String TYPE_NAME = "LeaveBlockType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String TASK = "task";
        final static String EARN_CODE = "earnCode";
        final static String ACCRUAL_CATEGORY_RULE = "accrualCategoryRule";
        final static String ACCRUAL_CATEGORY_OBJ = "accrualCategoryObj";
        final static String ACCRUAL_CATEGORY_RULE_ID = "accrualCategoryRuleId";
        final static String ACCRUAL_CATEGORY = "accrualCategory";
        final static String SUBMIT = "submit";
        final static String BLOCK_ID = "blockId";
        final static String DESCRIPTION = "description";
        final static String ASSIGNMENT_TITLE = "assignmentTitle";
        final static String CALENDAR_ID = "calendarId";
        final static String EARN_CODE_DESCRIPTION = "earnCodeDescription";
        final static String LEAVE_BLOCK_TYPE = "leaveBlockType";
        final static String EDITABLE = "editable";
        final static String DELETABLE = "deletable";
        final static String ASSIGNMENT_KEY = "assignmentKey";
        final static String DOCUMENT_STATUS = "documentStatus";
        final static String LEAVE_REQUEST_DOCUMENT_ID = "leaveRequestDocumentId";
        final static String PLANNING_DESCRIPTION = "planningDescription";
        final static String TRANSACTIONAL_DOC_ID = "transactionalDocId";
        final static String BEGIN_DATE_TIME = "beginDateTime";
        final static String END_DATE_TIME = "endDateTime";
        final static String LM_LEAVE_BLOCK_ID = "lmLeaveBlockId";
        final static String LEAVE_AMOUNT = "leaveAmount";
        final static String WORK_AREA = "workArea";
        final static String JOB_NUMBER = "jobNumber";
        final static String ACCRUAL_GENERATED = "accrualGenerated";
        final static String LEAVE_LOCAL_DATE = "leaveLocalDate";
        final static String SCHEDULE_TIME_OFF_ID = "scheduleTimeOffId";
        final static String REQUEST_STATUS = "requestStatus";
        final static String REQUEST_STATUS_STRING = "requestStatusString";
        final static String REASON = "reason";
        final static String HOURS = "hours";
        final static String CREATE_TIME = "createTime";
        final static String HR_CALENDAR_BLOCK_ID = "hrCalendarBlockId";
        final static String CONCRETE_BLOCK_TYPE = "concreteBlockType";
        final static String CONCRETE_BLOCK_ID = "concreteBlockId";
        final static String DOCUMENT_ID = "documentId";
        final static String PRINCIPAL_ID = "principalId";
        final static String AMOUNT = "amount";
        final static String OVERTIME_PREF = "overtimePref";
        final static String LUNCH_DELETED = "lunchDeleted";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String AFFECTS_PAY = "affectsPay";
        final static String LEAVE_DATE_TIME = "leaveDateTime";
    }

    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "LeaveBlock";


}