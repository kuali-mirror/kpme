package org.kuali.hr.time.calendar;

import java.util.ArrayList;
import java.util.List;

public class TkCalendarWeek {
	private List<TkCalendarDay> days = new ArrayList<TkCalendarDay>();

	public List<TkCalendarDay> getDays() {
		return days;
	}

	public void setDays(List<TkCalendarDay> days) {
		this.days = days;
	}
}
