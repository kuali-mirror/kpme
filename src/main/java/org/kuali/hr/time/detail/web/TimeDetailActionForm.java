package org.kuali.hr.time.detail.web;

import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeDetailActionForm extends TimeDetailActionFormBase {

    /**
     *
     */
    private static final long serialVersionUID = 5277197287612035236L;


	private TimeBlock timeBlock;
	private String clockAction;
	private String serverTimezone;
	private String userTimezone;
	private TimeSummary timeSummary;
	private Map<String, String> assignStyleClassMap = new HashMap<String, String>();
    private String timeBlockString;
    private TkCalendar calendar;

    public TkCalendar getTkCalendar() {
        return calendar;
    }

    public void setTkCalendar(TkCalendar calendar) {
        this.calendar = calendar;
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

    // Stopgap Measure - remove this
    public String getBeginPeriodDTNoTZ() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
        return sdf.format(this.getBeginPeriodDateTime());
    }

    public String getEndPeriodDTNoTZ() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
        return sdf.format(this.getEndPeriodDateTime());
    }
}
