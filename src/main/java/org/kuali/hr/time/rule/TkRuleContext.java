package org.kuali.hr.time.rule;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

public class TkRuleContext {
	private String action;
	private ClockLog clockLog = null;
	private TimeBlock timeBlock = null;
	private TimesheetDocument timesheetDocument = null;

	public TkRuleContext(String action, ClockLog clockLog){
		this.action = action;
		this.clockLog = clockLog;
	}
	
	public TkRuleContext(String action, TimeBlock timeBlock){
		this.action = action;
		this.timeBlock = timeBlock;
	}
	
	public TkRuleContext(String action, TimesheetDocument timesheetDocument){
		this.action = action;
		this.timesheetDocument = timesheetDocument;
	}
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public ClockLog getClockLog() {
		return clockLog;
	}
	public void setClockLog(ClockLog clockLog) {
		this.clockLog = clockLog;
	}
	public TimeBlock getTimeBlock() {
		return timeBlock;
	}
	public void setTimeBlock(TimeBlock timeBlock) {
		this.timeBlock = timeBlock;
	}
	
	
	public TimesheetDocument getTimesheetDocument() {
		return timesheetDocument;
	}

	public void setTimesheetDocument(TimesheetDocument timesheetDocument) {
		this.timesheetDocument = timesheetDocument;
	}

}
