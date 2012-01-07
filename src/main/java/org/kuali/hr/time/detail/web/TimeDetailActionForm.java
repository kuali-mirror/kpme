package org.kuali.hr.time.detail.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimeDetailActionForm extends TimeDetailActionFormBase {

    private static SimpleDateFormat SDF_NO_TZ = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");

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
    private String docEditable;
    private List<String> overtimeEarnCodes = new ArrayList<String>();

    public TkCalendar getTkCalendar() {
        return calendar;
    }

    public void setTkCalendar(TkCalendar calendar) {
        this.calendar = calendar;
    }

	public List<TimeBlock> getTimeBlockList() {
		return this.getTimesheetDocument().getTimeBlocks();
	}
	
	// for Actual Time Inquiry display only
	public List<TimeBlock> getClockLogTimeBlockList() {
		List<TimeBlock> clockList = new ArrayList<TimeBlock>();
		for(TimeBlock tb : this.getTimeBlockList()){
			if(tb.getClockLogCreated()) {
				clockList.add(tb);
			}
		}
		return clockList;
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
		return TkServiceLocator.getTimezoneService().getUserTimezone();
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

    public String getBeginPeriodDTNoTZ() {
        return SDF_NO_TZ.format(this.getBeginPeriodDateTime());
    }

    public String getEndPeriodDTNoTZ() {
        return SDF_NO_TZ.format(this.getEndPeriodDateTime());
    }

	public String getDocEditable() {
		if(StringUtils.isEmpty(docEditable)) {
			docEditable="false";
		}
		return docEditable;
	}

	public void setDocEditable(String docEditable) {
		this.docEditable = docEditable;
	}

	public List<String> getOvertimeEarnCodes() {
		return overtimeEarnCodes;
	}

	public void setOvertimeEarnCodes(List<String> overtimeEarnCodes) {
		this.overtimeEarnCodes = overtimeEarnCodes;
	}
}
