package org.kuali.hr.time.timesummary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class TimeSummaryRow {
	private String descr;
	private Map<String,BigDecimal> dayToHours = new HashMap<String,BigDecimal>();
	private List<BigDecimal> weeklyTotals = new LinkedList<BigDecimal>();
	private BigDecimal periodTotal = new BigDecimal(0);

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
	public List<BigDecimal> getWeeklyTotals() {
		return weeklyTotals;
	}
	public void setWeeklyTotals(List<BigDecimal> weeklyTotals) {
		this.weeklyTotals = weeklyTotals;
	}
	public BigDecimal getPeriodTotal() {
		return periodTotal;
	}
	public void setPeriodTotal(BigDecimal periodTotal) {
		this.periodTotal = periodTotal;
	}
	
	public BigDecimal getWeeklyTotal(String weekStr){
		if(StringUtils.contains(weekStr, "Week")){
			String res = StringUtils.stripStart(weekStr, "Week ");
			int result = Integer.parseInt(res);
			return weeklyTotals.get(result-1);
		}
		return BigDecimal.ZERO;
	}
	
}
