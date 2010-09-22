package org.kuali.hr.time.timesummary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TimeSummaryRow {
	private String descr;
	private Map<String,BigDecimal> dayToHours = new HashMap<String,BigDecimal>();

	public Map<String, BigDecimal> getDayToHours() {
		return dayToHours;
	}
	public void setDayToHours(Map<String, BigDecimal> dayToHours) {
		this.dayToHours = dayToHours;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
