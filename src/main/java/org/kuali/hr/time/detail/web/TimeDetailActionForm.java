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
	private String timeBlockJson;
	private String timeBlockId;
	private String earnCode;
	private String assignment;
	private Date beginDate;
	private Date endDate;
	private String beginTime;
	private String endTime;
	private String acrossDays;
	private List<TimeBlock> timeBlockList;

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
	public String getTimeBlockJson() {
		return timeBlockJson;
	}

	public void setTimeBlockJson(String timeBlockJson) {
		this.timeBlockJson = timeBlockJson;
	}

	public String getTimeBlockId() {
		return timeBlockId;
	}

	public void setTimeBlockId(String timeBlockId) {
		this.timeBlockId = timeBlockId;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
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

	public List<TimeBlock> getTimeBlocks() {
		return timeBlockList;
	}

	public void setTimeBlocks(List<TimeBlock> timeBlockList) {
		this.timeBlockList = timeBlockList;
	}
}
