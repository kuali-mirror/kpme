package org.kuali.hr.time.timesummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TimeSummary {
	private List<String> summaryHeader = new ArrayList<String>();
	private List<EarnGroupSection> sections = new ArrayList<EarnGroupSection>();
	
	private List<BigDecimal> workedHours = new ArrayList<BigDecimal>();

	public List<String> getSummaryHeader() {
		return summaryHeader;
	}
	public void setSummaryHeader(List<String> summaryHeader) {
		this.summaryHeader = summaryHeader;
	}
	public List<EarnGroupSection> getSections() {
		return sections;
	}
	public void setSections(List<EarnGroupSection> sections) {
		this.sections = sections;
	}
	public List<BigDecimal> getWorkedHours() {
		return workedHours;
	}
	public void setWorkedHours(List<BigDecimal> workedHours) {
		this.workedHours = workedHours;
	}
	
}
