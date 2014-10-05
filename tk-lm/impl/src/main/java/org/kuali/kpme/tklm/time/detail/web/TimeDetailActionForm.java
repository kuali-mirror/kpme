/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.detail.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.time.calendar.TkCalendar;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timesummary.TimeSummary;
import org.kuali.rice.core.api.config.property.ConfigContext;

public class TimeDetailActionForm extends TimeDetailActionFormBase {

    private static final long serialVersionUID = 5277197287612035236L;


	private TimeBlockBo timeBlock;
	private String clockAction;
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
    private Boolean notesEditable = Boolean.TRUE;
   

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
			if(tb.isClockLogCreated()) {
				clockList.add(tb);
			}
		}
		return clockList;
	}

	public TimeBlockBo getTimeBlock() {
		return timeBlock;
	}

	public void setTimeBlock(TimeBlockBo timeBlock) {
		this.timeBlock = timeBlock;
	}

	public String getClockAction() {
		return clockAction;
	}

	public void setClockAction(String clockAction) {
		this.clockAction = clockAction;
	}

	public String getIsVirtualWorkDay() {
		String isVirtualWorkDay = "false";
		
		if (getCalendarEntry() != null) {
			isVirtualWorkDay = Boolean.toString(TKUtils.isVirtualWorkDay(getCalendarEntry().getBeginPeriodFullDateTime()));
		}
		
		return isVirtualWorkDay;
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
		return HrServiceLocator.getTimezoneService().getUserTimezone();
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
    
    public boolean isCurrentTimesheet() {
    	boolean isCurrentTimesheet = false;
    	
    	if (getCalendarEntry() != null) {
    		Interval interval = new Interval(getCalendarEntry().getBeginPeriodFullDateTime(), getCalendarEntry().getEndPeriodFullDateTime());
    		isCurrentTimesheet = interval.containsNow();
    	}
    	
    	return isCurrentTimesheet;
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

	public Boolean getNotesEditable() {
		return notesEditable;
	}

	public void setNotesEditable(Boolean notesEditable) {
		this.notesEditable = notesEditable;
	}

    public Boolean isTimeBlockEditable(TimeBlockContract timeBlock) {
        return TkServiceLocator.getTKPermissionService().canEditTimeBlock(HrContext.getPrincipalId(), timeBlock);
    }

    public Boolean isDeletable(TimeBlockContract timeBlock) {
        return TkServiceLocator.getTKPermissionService().canDeleteTimeBlock(HrContext.getPrincipalId(), timeBlock);
    }

    public int getPageSize() {
        String parameterValue = ConfigContext.getCurrentContextConfig().getProperty("kpme.tklm.actualtime.page.size");
        Integer limit = 25;
        if(StringUtils.isNotBlank(parameterValue)) {
            limit = Integer.parseInt(ConfigContext.getCurrentContextConfig().getProperty("kpme.tklm.actualtime.page.size"));
        }
        return limit;
    }
}
