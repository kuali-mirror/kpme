package org.kuali.hr.time.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TkCommonCalendarForm extends TkForm {
	private static final long serialVersionUID = 1L;
	
	private List<String> calendarYears = new ArrayList<String>();
    private Map<String,String> payPeriodsMap = new HashMap<String,String>();
    
    private String selectedCalendarYear;
    private String selectedPayPeriod;
    private boolean onCurrentPeriod;
    
	public List<String> getCalendarYears() {
		return calendarYears;
	}
	public void setCalendarYears(List<String> calendarYears) {
		this.calendarYears = calendarYears;
	}
	
	public String getSelectedCalendarYear() {
		return selectedCalendarYear;
	}
	public void setSelectedCalendarYear(String selectedCalendarYear) {
		this.selectedCalendarYear = selectedCalendarYear;
	}
	public String getSelectedPayPeriod() {
		return selectedPayPeriod;
	}
	public void setSelectedPayPeriod(String selectedPayPeriod) {
		this.selectedPayPeriod = selectedPayPeriod;
	}
	public Map<String, String> getPayPeriodsMap() {
		return payPeriodsMap;
	}
	public void setPayPeriodsMap(Map<String, String> payPeriodsMap) {
		this.payPeriodsMap = payPeriodsMap;
	}
	public boolean isOnCurrentPeriod() {
		return onCurrentPeriod;
	}
	public void setOnCurrentPeriod(boolean onCurrentPeriod) {
		this.onCurrentPeriod = onCurrentPeriod;
	}
}
