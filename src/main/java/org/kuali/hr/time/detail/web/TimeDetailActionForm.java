package org.kuali.hr.time.detail.web;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public class TimeDetailActionForm extends TimesheetActionForm {

    /**
     *
     */
    private static final long serialVersionUID = 5277197287612035236L;

	private java.util.Date beginPeriodDate;
	private java.util.Date endPeriodDate;
	
	// this is for the ajax call
	private String outputString;
	
	private Long tkTimeBlockId;
	private Long startTime;
	private Long endTime;
	private String acrossDays;
	private TimeBlock timeBlock;
	private String clockAction;
	private BigDecimal hours;
	private String startDate;
	private String endDate;
	
	public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}

	public Long getTkTimeBlockId() {
		return tkTimeBlockId;
	}

	public void setTkTimeBlockId(Long tkTimeBlockId) {
		this.tkTimeBlockId = tkTimeBlockId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getAcrossDays() {
		return acrossDays;
	}

	public void setAcrossDays(String acrossDays) {
		this.acrossDays = acrossDays;
	}

	public List<TimeBlock> getTimeBlockList() {
		return this.getTimesheetDocument().getTimeBlocks();
	}

	public TimeBlock getTimeBlock() {
		return timeBlock;
	}

	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}
	
	public String getClockAction() {
		return clockAction;
	}

	public void setClockAction(String clockAction) {
		this.clockAction = clockAction;
	}

	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
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

	public java.util.Date getBeginPeriodDate() {
		return beginPeriodDate;
	}

	public void setBeginPeriodDate(java.util.Date beginPeriodDate) {
		this.beginPeriodDate = beginPeriodDate;
	}

	public java.util.Date getEndPeriodDate() {
		return endPeriodDate;
	}

	public void setEndPeriodDate(java.util.Date endPeriodDate) {
		this.endPeriodDate = endPeriodDate;
	}

}
