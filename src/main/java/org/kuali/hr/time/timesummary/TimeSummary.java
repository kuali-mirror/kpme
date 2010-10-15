package org.kuali.hr.time.timesummary;

import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TimeSummary {
	private Long numberOfDays;
	private Date payBeginDate;
	private Date payEndDate;
	private List<String> dateDescr = new LinkedList<String>();
	private List<TimeSummarySection> sections = new ArrayList<TimeSummarySection>();
	
	private TimeSummaryRow workedHours = new TimeSummaryRow();
	
	public Long getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(Long numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public Date getPayBeginDate() {
		return payBeginDate;
	}
	public void setPayBeginDate(Date payBeginDate) {
		this.payBeginDate = payBeginDate;
	}
	public Date getPayEndDate() {
		return payEndDate;
	}
	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}
	
	public void setDateDescr(List<String> dateDescr) {
		this.dateDescr = dateDescr;
	}
	public List<String> getDateDescr() {
		return dateDescr;
	}
	public List<TimeSummarySection> getSections() {
		return sections;
	}
	public void setSections(List<TimeSummarySection> sections) {
		this.sections = sections;
	}
	public TimeSummaryRow getWorkedHours() {
		return workedHours;
	}
	public void setWorkedHours(TimeSummaryRow workedHours) {
		this.workedHours = workedHours;
	}
	
}
