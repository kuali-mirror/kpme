/**
 * Copyright 2004-2014 The Kuali Foundation
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

import org.joda.time.Interval;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.tklm.api.leave.accrual.RateRangeContract;
import org.kuali.kpme.tklm.api.leave.timeoff.SystemScheduledTimeOffContract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RateRange implements RateRangeContract {
	private Interval range;
	private List<Job> jobs = new ArrayList<Job>();
	private BigDecimal accrualRatePercentageModifier = new BigDecimal("1.0");
	private BigDecimal standardHours;
	private PrincipalHRAttributes principalHRAttributes;
	private PrincipalHRAttributes endPrincipalHRAttributes;
	private LeavePlan leavePlan;
	private List<AccrualCategory> acList = new ArrayList<AccrualCategory>();
	private List<AccrualCategoryRule> acRuleList = new ArrayList<AccrualCategoryRule>();
	private SystemScheduledTimeOffContract sysScheTimeOff;
	private String leaveCalendarDocumentId;
	private String primaryLeaveAssignmentId;
	
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
	
	public SystemScheduledTimeOffContract getSysScheTimeOff() {
		return sysScheTimeOff;
	}
	public void setSysScheTimeOff(SystemScheduledTimeOffContract sysScheTimeOff) {
		this.sysScheTimeOff = sysScheTimeOff;
	}
	public String getLeaveCalendarDocumentId() {
		return leaveCalendarDocumentId;
	}
	public void setLeaveCalendarDocumentId(String leaveCalendarDocumentId) {
		this.leaveCalendarDocumentId = leaveCalendarDocumentId;
	}
	public String getPrimaryLeaveAssignmentId() {
		return primaryLeaveAssignmentId;
	}
	public void setPrimaryLeaveAssignmentId(String primaryLeaveAssignmentId) {
		this.primaryLeaveAssignmentId = primaryLeaveAssignmentId;
	}
}
