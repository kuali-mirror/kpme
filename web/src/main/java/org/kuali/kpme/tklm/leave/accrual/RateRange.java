/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.tklm.leave.accrual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.leaveplan.LeavePlan;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;

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
	private String leaveCalendarDocumentId;
	
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
	public String getLeaveCalendarDocumentId() {
		return leaveCalendarDocumentId;
	}
	public void setLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
		this.leaveCalendarDocumentId = leaveCalendarDocumentId;
	}
}
