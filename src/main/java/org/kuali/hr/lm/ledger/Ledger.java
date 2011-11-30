package org.kuali.hr.lm.ledger;

import org.joda.time.DateTime;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.HrBusinessObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

public class Ledger extends HrBusinessObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // use java sql date for now
    private Long lmLedgerId;
    private Date ledgerDate;
    private String description;
    private String principalId;
    private String leaveCode;
    private Long leaveCodeId;
    private Long scheduleTimeOffId;
    private Long accrualCategoryId;
    private Boolean active;
    private BigDecimal hours = new BigDecimal("0.0");
    private String applyToYtdUsed;
    private String documentId;
    private String principalActivated;
    private String principalInactivated;
    private Timestamp timestampActivated;
    private Timestamp timestampInactivaed;
    private Boolean accrualGenerated;
    private Long blockId;

    private LeaveCode leaveCodeObj;
    private SystemScheduledTimeOff systemScheduledTimeOffObj;
    private AccrualCategory accrualCategoryObj;

    public static class Builder {

        // required parameters for the constructor
        private final Date ledgerDate;
        private final String principalId;
        private final String documentId;
        private final String leaveCode;
        private final BigDecimal hours;


        private String description = null;
        private Boolean active = Boolean.TRUE;
        private String applyToYtdUsed = null;
        private String principalActivated = null;
        private String principalInactivated = null;
        private Timestamp timestampActivated = null;
        private Timestamp timestampInactivaed = null;
        private Boolean accrualGenerated = Boolean.FALSE;
        private Long blockId = 0L;
        private Long leaveCodeId;
        private Long scheduleTimeOffId;
        private Long accrualCategoryId;


        public Builder(DateTime ledgerDate, String documentId, String principalId, String leaveCode, BigDecimal hours) {
            this.ledgerDate = ledgerDate.toDate();
            this.documentId = documentId;
            this.principalId = principalId;
            this.leaveCode = leaveCode;
            this.hours = hours;
        }

        // validations could be done in the builder methods below

        public Builder description(String val) {
            this.description = val;
            return this;
        }

        public Builder active(Boolean val) {
            this.active = val;
            return this;
        }

        public Builder applyToYtdUsed(String val) {
            this.applyToYtdUsed = val;
            return this;
        }

        public Builder principalActivated(String val) {
            this.principalActivated = val;
            return this;
        }

        public Builder principalInactivated(String val) {
            this.principalInactivated = val;
            return this;
        }

        public Builder timestampActivated(Timestamp val) {
            this.timestampActivated = val;
            return this;
        }

        public Builder timestampInactivaed(Timestamp val) {
            this.timestampInactivaed = val;
            return this;
        }

        public Builder accrualGenerated(Boolean val) {
            this.accrualGenerated = val;
            return this;
        }

        public Builder blockId(Long val) {
            this.blockId = val;
            return this;
        }

        public Builder leaveCodeId(Long val) {
            this.leaveCodeId = val;
            return this;
        }

        //TODO: need to hook up the objcets to get the real ids
        public Builder scheduleTimeOffId(Long val) {
            this.scheduleTimeOffId = val;
            return this;
        }

        public Builder accrualCategoryId(Long val) {
            this.accrualCategoryId = val;
            return this;
        }

        public Ledger build() {
            return new Ledger(this);
        }
    }

    private Ledger(Builder builder) {
        ledgerDate = builder.ledgerDate;
        description = builder.description;
        principalId = builder.principalId;
        leaveCode = builder.leaveCode;
        active = builder.active;
        hours = builder.hours;
        applyToYtdUsed = builder.applyToYtdUsed;
        documentId = builder.documentId;
        principalActivated = builder.principalActivated;
        principalInactivated = builder.principalInactivated;
        timestampActivated = builder.timestampActivated;
        timestampInactivaed = builder.timestampInactivaed;
        accrualGenerated = builder.accrualGenerated;
        blockId = builder.blockId;
        leaveCodeId = builder.leaveCodeId;
        scheduleTimeOffId = builder.scheduleTimeOffId;
        accrualCategoryId = builder.accrualCategoryId;

        // TODO: need to hook up leaveCodeObj, systemScheduledTimeOffObj, accrualCategoryObj, and ids for individual obj
    }


    public Ledger() {

    }

    @Override
    protected String getUniqueKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getId() {
        return lmLedgerId;
    }

    @Override
    public void setId(Long id) {
        this.lmLedgerId = id;
    }

    @Override
    protected LinkedHashMap toStringMapper() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Long getAccrualCategoryId() {
        return accrualCategoryId;
    }

    public void setAccrualCategoryId(Long accrualCategoryId) {
        this.accrualCategoryId = accrualCategoryId;
    }

    public Boolean getAccrualGenerated() {
        return accrualGenerated;
    }

    public void setAccrualGenerated(Boolean accrualGenerated) {
        this.accrualGenerated = accrualGenerated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getApplyToYtdUsed() {
        return applyToYtdUsed;
    }

    public void setApplyToYtdUsed(String applyToYtdUsed) {
        this.applyToYtdUsed = applyToYtdUsed;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public Long getLeaveCodeId() {
        return leaveCodeId;
    }

    public void setLeaveCodeId(Long leaveCodeId) {
        this.leaveCodeId = leaveCodeId;
    }

    public Date getLedgerDate() {
        return ledgerDate;
    }

    public void setLedgerDate(Date ledgerDate) {
        this.ledgerDate = ledgerDate;
    }

    public Long getLmLedgerId() {
        return lmLedgerId;
    }

    public void setLmLedgerId(Long lmLedgerId) {
        this.lmLedgerId = lmLedgerId;
    }

    public String getPrincipalActivated() {
        return principalActivated;
    }

    public void setPrincipalActivated(String principalActivated) {
        this.principalActivated = principalActivated;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalInactivated() {
        return principalInactivated;
    }

    public void setPrincipalInactivated(String principalInactivated) {
        this.principalInactivated = principalInactivated;
    }

    public Long getScheduleTimeOffId() {
        return scheduleTimeOffId;
    }

    public void setScheduleTimeOffId(Long scheduleTimeOffId) {
        this.scheduleTimeOffId = scheduleTimeOffId;
    }

    public Timestamp getTimestampActivated() {
        return timestampActivated;
    }

    public void setTimestampActivated(Timestamp timestampActivated) {
        this.timestampActivated = timestampActivated;
    }

    public Timestamp getTimestampInactivaed() {
        return timestampInactivaed;
    }

    public void setTimestampInactivaed(Timestamp timestampInactivaed) {
        this.timestampInactivaed = timestampInactivaed;
    }

    public AccrualCategory getAccrualCategoryObj() {
        return accrualCategoryObj;
    }

    public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
        this.accrualCategoryObj = accrualCategoryObj;
    }

    public LeaveCode getLeaveCodeObj() {
        return leaveCodeObj;
    }

    public void setLeaveCodeObj(LeaveCode leaveCodeObj) {
        this.leaveCodeObj = leaveCodeObj;
    }

    public SystemScheduledTimeOff getSystemScheduledTimeOffObj() {
        return systemScheduledTimeOffObj;
    }

    public void setSystemScheduledTimeOffObj(SystemScheduledTimeOff systemScheduledTimeOffObj) {
        this.systemScheduledTimeOffObj = systemScheduledTimeOffObj;
    }
}
