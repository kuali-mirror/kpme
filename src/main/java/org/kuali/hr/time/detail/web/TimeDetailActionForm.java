package org.kuali.hr.time.detail.web;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;

public class TimeDetailActionForm extends TimesheetActionForm {

    /**
     *
     */
    private static final long serialVersionUID = 5277197287612035236L;

	private Date beginPeriodDate;
	private Date endPeriodDate;
	// this is for the ajax call
	private String outputString;
	private String timeBlockId;
	private Long startTime;
	private Long endTime;
	private String acrossDays;
	private List<TimeBlock> timeBlockList;
	private TimeBlock timeBlock;
	private String clockAction;
	
	public Date getBeginPeriodDate() {
		return beginPeriodDate;
	}

	public void setBeginPeriodDate(Date beginPeriodDate) {
		this.beginPeriodDate = beginPeriodDate;
	}

	public Date getEndPeriodDate() {
		return endPeriodDate;
	}

	public void setEndPeriodDate(Date endPeriodDate) {
		this.endPeriodDate = endPeriodDate;
	}
	public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}

	public String getTimeBlockId() {
		return timeBlockId;
	}

	public void setTimeBlockId(String timeBlockId) {
		this.timeBlockId = timeBlockId;
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
		return timeBlockList;
	}

	public void setTimeBlockList(List<TimeBlock> timeBlockList) {
		this.timeBlockList = timeBlockList;
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

}
