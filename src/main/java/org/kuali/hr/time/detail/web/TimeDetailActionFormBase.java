package org.kuali.hr.time.detail.web;

import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

import java.math.BigDecimal;
import java.util.List;

/**
 * Base form for both WS and Regular TimeDetail action.
 */
public class TimeDetailActionFormBase extends TimesheetActionForm {

    private Long tkTimeBlockId;
    private String outputString;
    private List<String> warnings;
    private String startTime;
    private String endTime;
    private String acrossDays;
    private String startDate;
    private String endDate;
    private BigDecimal hours;
    private BigDecimal amount;

    public Long getTkTimeBlockId() {
        return tkTimeBlockId;
    }

    public void setTkTimeBlockId(Long tkTimeBlockId) {
        this.tkTimeBlockId = tkTimeBlockId;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAcrossDays() {
        return acrossDays;
    }

    public void setAcrossDays(String acrossDays) {
        this.acrossDays = acrossDays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOutputString() {
        return outputString;
    }

    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
