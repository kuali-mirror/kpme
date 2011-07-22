package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.paycalendar.PayCalendarEntries;

public class TkCalendar {
	private List<TkCalendarWeek> weeks = new ArrayList<TkCalendarWeek>();
	private PayCalendarEntries payCalEntry;

	public List<TkCalendarWeek> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<TkCalendarWeek> weeks) {
		this.weeks = weeks;
	}

	public PayCalendarEntries getPayCalEntry() {
		return payCalEntry;
	}

	public void setPayCalEntry(PayCalendarEntries payCalEntry) {
		this.payCalEntry = payCalEntry;
	}
	
	
}
