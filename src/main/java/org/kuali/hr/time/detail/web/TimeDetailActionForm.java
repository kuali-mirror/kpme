package org.kuali.hr.time.detail.web;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeDetailActionForm extends TimesheetActionForm {

    /**
     *
     */
    private static final long serialVersionUID = 5277197287612035236L;

	private String outputString;
	private String warningJason;
	private Long tkTimeBlockId;
	private String startTime;
	private String endTime;
	private String acrossDays;
	private TimeBlock timeBlock;
	private String clockAction;
	private BigDecimal hours;
	private BigDecimal amount;
	private String startDate;
	private String endDate;
	private String serverTimezone;
	private String userTimezone;
	private TimeSummary timeSummary;
	private Map<String, String> assignStyleClassMap = new HashMap<String, String>();
    private String timeBlockString;

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public String getIsVirtualWorkDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginPeriodDateTime());
		return Boolean.toString(TKUtils.isVirtualWorkDay(cal));
	}

	public TimeSummary getTimeSummary() {
		return timeSummary;
	}

	public void setTimeSummary(TimeSummary timeSummary) {
		this.timeSummary = timeSummary;
	}

	public String getServerTimezone() {
		return TkConstants.SYSTEM_TIME_ZONE;
	}

	public String getUserTimezone() {
		return TkServiceLocator.getTimezoneService().getUserTimeZone();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getWarningJason() {
		return warningJason;
	}

	public void setWarningJason(String warningJason) {
		this.warningJason = warningJason;
	}

	public Map<String, String> getAssignStyleClassMap() {
		return assignStyleClassMap;
	}

	public void setAssignStyleClassMap(Map<String, String> assignStyleClassMap) {
		this.assignStyleClassMap = assignStyleClassMap;
	}

    public String getTimeBlockString() {
        return timeBlockString;
    }

    public void setTimeBlockString(String timeBlockString) {
        this.timeBlockString = timeBlockString;
    }
}
