package org.kuali.hr.lm.accrual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.time.principal.PrincipalHRAttributes;

public class RateRange {
	private Interval range;
	private List<Job> jobs = new ArrayList<Job>();
	private BigDecimal accrualRatePercentageModifier = new BigDecimal("1.0");
	private BigDecimal standardHours;
	private PrincipalHRAttributes principalHRAttributes;
	private PrincipalHRAttributes endPrincipalHRAttributes;
	private LeavePlan leavePlan;
	private List<AccrualCategory> acList = new ArrayList<AccrualCategory>();
	private List<AccrualCategoryRule> acRuleList = new ArrayList<AccrualCategoryRule>();
	private SystemScheduledTimeOff sysScheTimeOff;
	
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
	public BigDecimal getStandardHours() {
		return standardHours;
	}
	public void setStandardHours(BigDecimal standardHours) {
		this.standardHours = standardHours;
	}
	public PrincipalHRAttributes getPrincipalHRAttributes() {
		return principalHRAttributes;
	}
	public void setPrincipalHRAttributes(PrincipalHRAttributes principalHRAttributes) {
		this.principalHRAttributes = principalHRAttributes;
	}
	public PrincipalHRAttributes getEndPrincipalHRAttributes() {
		return endPrincipalHRAttributes;
	}
	public void setEndPrincipalHRAttributes(
			PrincipalHRAttributes endPrincipalHRAttributes) {
		this.endPrincipalHRAttributes = endPrincipalHRAttributes;
	}
	public LeavePlan getLeavePlan() {
		return leavePlan;
	}
	public void setLeavePlan(LeavePlan leavePlan) {
		this.leavePlan = leavePlan;
	}
	public List<AccrualCategory> getAcList() {
		return acList;
	}
	public void setAcList(List<AccrualCategory> acList) {
		this.acList = acList;
	}
	public List<AccrualCategoryRule> getAcRuleList() {
		return acRuleList;
	}
	public void setAcRuleList(List<AccrualCategoryRule> acRuleList) {
		this.acRuleList = acRuleList;
	}
	
	public SystemScheduledTimeOff getSysScheTimeOff() {
		return sysScheTimeOff;
	}
	public void setSysScheTimeOff(SystemScheduledTimeOff sysScheTimeOff) {
		this.sysScheTimeOff = sysScheTimeOff;
	}
}
