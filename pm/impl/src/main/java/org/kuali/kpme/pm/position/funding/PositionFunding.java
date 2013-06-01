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
package org.kuali.kpme.pm.position.funding;

import java.math.BigDecimal;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PositionFunding extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String pmPositionFunctionId;
	private String hrPositionId;
	
	private String chart;
	private String org;
	private String account;
	private String subAccount;
	private String objectCode;
	private String subObjectCode;
	private String orgRefCode;
	private BigDecimal percent;
	private BigDecimal amount;
	private boolean priorityFlag;
	
	// indicates where the funding information comes from, it could be from maint document, Institution batch imports, etc..
	// we use source on the maint document to determine which funding information is readonly
	private String source;		
	

	@Override
	public String getId() {
		return getPmPositionFunctionId();
	}

	@Override
	public void setId(String id) {
		setPmPositionFunctionId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPmPositionFunctionId() + "_" + getHrPositionId();
	}
	
	public String getPmPositionFunctionId() {
		return pmPositionFunctionId;
	}

	public void setPmPositionFunctionId(String pmPositionFunctionId) {
		this.pmPositionFunctionId = pmPositionFunctionId;
	}
	
	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	public String getSubObjectCode() {
		return subObjectCode;
	}

	public void setSubObjectCode(String subObjectCode) {
		this.subObjectCode = subObjectCode;
	}

	public String getOrgRefCode() {
		return orgRefCode;
	}

	public void setOrgRefCode(String orgRefCode) {
		this.orgRefCode = orgRefCode;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(boolean priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	
}
