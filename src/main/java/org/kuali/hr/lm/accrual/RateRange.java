package org.kuali.hr.lm.accrual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.kuali.hr.job.Job;

public class RateRange {
	private Interval range;
	private List<Job> jobs = new ArrayList<Job>();
	private BigDecimal accrualRatePercentageModifier = new BigDecimal("1.0");
	
	private boolean statusChanged;
	
	public Interval getRange() {
		return range;
	}
	public void setRange(Interval range) {
		this.range = range;
	}
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	public BigDecimal getAccrualRatePercentageModifier() {
		return accrualRatePercentageModifier;
	}
	public void setAccrualRatePercentageModifier(
			BigDecimal accrualRatePercentageModifier) {
		this.accrualRatePercentageModifier = accrualRatePercentageModifier;
	}
	public boolean isStatusChanged() {
		return statusChanged;
	}
	public void setStatusChanged(boolean statusChanged) {
		this.statusChanged = statusChanged;
	}
}
