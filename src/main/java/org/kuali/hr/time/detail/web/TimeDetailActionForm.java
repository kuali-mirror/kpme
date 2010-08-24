package org.kuali.hr.time.detail.web;

import java.util.List;

import org.kuali.hr.time.base.web.TkForm;
import org.kuali.hr.time.timeblock.TimeBlock;

public class TimeDetailActionForm extends TkForm {

    /**
     *
     */
    private static final long serialVersionUID = 5277197287612035236L;

	private String beginPeriodDate;
	private String endPeriodDate;
	private String timeBlockJson;
	private String timeBlockId;
	private String earnCode;
	private String assignment;
	private String beginDate;
	private String endDate;
	private String beginTime;
	private String endTime;
	private String acrossDays;
	private List<TimeBlock> timeBlockList;

	public String getBeginPeriodDate() {
		return beginPeriodDate;
	}

	public void setBeginPeriodDate(String beginPeriodDate) {
		this.beginPeriodDate = beginPeriodDate;
	}

	public String getEndPeriodDate() {
		return endPeriodDate;
	}

	public void setEndPeriodDate(String endPeriodDate) {
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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
