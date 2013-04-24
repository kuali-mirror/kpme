/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.detail.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.hr.tklm.time.calendar.TkCalendar;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timesummary.TimeSummary;
import org.kuali.hr.tklm.time.util.TKUtils;

public class TimeDetailActionForm extends TimeDetailActionFormBase {

    private static DateTimeFormatter SDF_NO_TZ = DateTimeFormat.forPattern("EEE MMM d HH:mm:ss yyyy");

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
    private String leaveBlockString;
    private TkCalendar calendar;
    private String docEditable;
    private String workingOnItsOwn;	// true if the user is working on its own timesheet
    private List<String> overtimeEarnCodes = new ArrayList<String>();
    private String tkTimeHourDetailId;
    private String isLunchDeleted;

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
		return TKUtils.getSystemTimeZone();
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
        return SDF_NO_TZ.print(new DateTime(this.getBeginPeriodDateTime()));
    }

    public String getEndPeriodDTNoTZ() {
        return SDF_NO_TZ.print(new DateTime(this.getEndPeriodDateTime()));
    }
    

    public boolean isCurrentTimesheet() {
    	return (LocalDate.now().toDate().compareTo(this.getBeginPeriodDateTime()) >= 0 && LocalDate.now().toDate().compareTo(this.getEndPeriodDateTime()) < 0 );
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

    public String getTkTimeHourDetailId() {
        return tkTimeHourDetailId;
    }

    public void setTkTimeHourDetailId(String tkTimeHourDetailId) {
        this.tkTimeHourDetailId = tkTimeHourDetailId;
    }

    public String getLunchDeleted() {
        return isLunchDeleted;
    }

    public void setLunchDeleted(String lunchDeleted) {
        isLunchDeleted = lunchDeleted;
    }

	public String getWorkingOnItsOwn() {
		if(StringUtils.isEmpty(workingOnItsOwn)) {
			workingOnItsOwn="false";
		}
		return workingOnItsOwn;
	}

	public void setWorkingOnItsOwn(String workingOnItsOwn) {
		this.workingOnItsOwn = workingOnItsOwn;
	}

	public String getLeaveBlockString() {
		return leaveBlockString;
	}

	public void setLeaveBlockString(String leaveBlockString) {
		this.leaveBlockString = leaveBlockString;
	}

}
