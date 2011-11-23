package org.kuali.hr.lm.ledger;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.HrBusinessObject;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Date;
import java.util.LinkedHashMap;

public class Ledger extends HrBusinessObject {

    // use java sql date for now
    private Long lmLedgerId;
    private Date ledger_date;
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

    public Date getLedger_date() {
        return ledger_date;
    }

    public void setLedger_date(Date ledger_date) {
        this.ledger_date = ledger_date;
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
