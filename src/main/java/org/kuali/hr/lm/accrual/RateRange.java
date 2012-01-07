package org.kuali.hr.lm.accrual;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.kuali.hr.job.Job;

public class RateRange {
	private Interval range;
	private List<Job> jobs = new ArrayList<Job>();
	private double accrualRatePercentageModifier = 1.0;
	
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
	public double getAccrualRatePercentageModifier() {
		return accrualRatePercentageModifier;
	}
	public void setAccrualRatePercentageModifier(
			double accrualRatePercentageModifier) {
		this.accrualRatePercentageModifier = accrualRatePercentageModifier;
	}
}
