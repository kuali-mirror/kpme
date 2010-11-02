package org.kuali.hr.time.timesummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AssignmentRow {
	private String descr;
	private List<BigDecimal> total = new ArrayList<BigDecimal>();
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public List<BigDecimal> getTotal() {
		return total;
	}
	public void setTotal(List<BigDecimal> total) {
		this.total = total;
	}
}
