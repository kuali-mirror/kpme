/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leaveSummary;

import java.math.BigDecimal;

public class LeaveSummaryRow {
	private String accrualCategory;
	private BigDecimal carryOver;
	private BigDecimal ytdAccruedBalance;
	private BigDecimal ytdApprovedUsage;
	private BigDecimal leaveBalance;
	private BigDecimal pendingLeaveAccrual;
	private BigDecimal pendingLeaveRequests;
	private BigDecimal pendingLeaveBalance;
	private BigDecimal pendingAvailableUsage;
	private BigDecimal usageLimit;
	private BigDecimal fmlaUsage;
	
	public String getAccrualCategory() {
		return accrualCategory;
	}
	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}
	public BigDecimal getCarryOver() {
		return carryOver;
	}
	public void setCarryOver(BigDecimal carryOver) {
		this.carryOver = carryOver;
	}
	public BigDecimal getYtdAccruedBalance() {
		return ytdAccruedBalance;
	}
	public void setYtdAccruedBalance(BigDecimal ytdAccruedBalance) {
		this.ytdAccruedBalance = ytdAccruedBalance;
	}
	public BigDecimal getYtdApprovedUsage() {
		return ytdApprovedUsage;
	}
	public void setYtdApprovedUsage(BigDecimal ytdApprovedUsage) {
		this.ytdApprovedUsage = ytdApprovedUsage;
	}
	public BigDecimal getLeaveBalance() {
		return leaveBalance;
	}
	public void setLeaveBalance(BigDecimal leaveBalance) {
		this.leaveBalance = leaveBalance;
	}
	public BigDecimal getPendingLeaveAccrual() {
		return pendingLeaveAccrual;
	}
	public void setPendingLeaveAccrual(BigDecimal pendingLeaveAccrual) {
		this.pendingLeaveAccrual = pendingLeaveAccrual;
	}
	public BigDecimal getPendingLeaveRequests() {
		return pendingLeaveRequests;
	}
	public void setPendingLeaveRequests(BigDecimal pendingLeaveRequests) {
		this.pendingLeaveRequests = pendingLeaveRequests;
	}
	public BigDecimal getPendingLeaveBalance() {
		return pendingLeaveBalance;
	}
	public void setPendingLeaveBalance(BigDecimal pendingLeaveBalance) {
		this.pendingLeaveBalance = pendingLeaveBalance;
	}
	public BigDecimal getPendingAvailableUsage() {
		return pendingAvailableUsage;
	}
	public void setPendingAvailableUsage(BigDecimal pendingAvailableUsage) {
		this.pendingAvailableUsage = pendingAvailableUsage;
	}
	public BigDecimal getUsageLimit() {
		return usageLimit;
	}
	public void setUsageLimit(BigDecimal usageLimit) {
		this.usageLimit = usageLimit;
	}
	public BigDecimal getFmlaUsage() {
		return fmlaUsage;
	}
	public void setFmlaUsage(BigDecimal fmlaUsage) {
		this.fmlaUsage = fmlaUsage;
	}
    public BigDecimal getAccruedBalance() {
        BigDecimal carryOver = getCarryOver() == null ? BigDecimal.ZERO : getCarryOver();
        BigDecimal ytdAccruedBalance = getYtdAccruedBalance() == null ? BigDecimal.ZERO : getYtdAccruedBalance();
        BigDecimal ytdApproved = getYtdApprovedUsage() == null ? BigDecimal.ZERO : getYtdApprovedUsage();
        return carryOver.add(ytdAccruedBalance).subtract(ytdApproved);
    }

}
