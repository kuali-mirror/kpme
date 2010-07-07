package org.kuali.hr.time.rule;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.domain.base.TimeBlock;

public class TkRuleContext {
	private String action;
	private ClockLog clockLog = null;
	private TimeBlock timeBlock = null;
	
	public TkRuleContext(String action, ClockLog clockLog){
		this.action = action;
		this.clockLog = clockLog;
	}
	
	public TkRuleContext(String action, TimeBlock timeBlock){
		this.action = action;
		this.timeBlock = timeBlock;
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
	
	
}
