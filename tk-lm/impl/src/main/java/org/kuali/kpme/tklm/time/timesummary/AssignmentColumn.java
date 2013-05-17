package org.kuali.kpme.tklm.time.timesummary;

import java.io.Serializable;
import java.math.BigDecimal;

public class AssignmentColumn implements Serializable {

	private static final long serialVersionUID = 879042466570649820L;
	
	private String cssClass;
	private BigDecimal amount = BigDecimal.ZERO;
	private BigDecimal total = BigDecimal.ZERO;
	private boolean weeklyTotal;
	
	public String getCssClass() {
		return cssClass;
	}
	
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isWeeklyTotal() {
		return weeklyTotal;
	}

	public void setWeeklyTotal(boolean weeklyTotal) {
		this.weeklyTotal = weeklyTotal;
	}

}