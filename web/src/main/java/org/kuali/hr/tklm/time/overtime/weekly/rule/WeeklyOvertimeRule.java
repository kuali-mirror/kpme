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
package org.kuali.hr.tklm.time.overtime.weekly.rule;

import java.math.BigDecimal;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.core.earncode.group.EarnCodeGroup;
import org.kuali.hr.tklm.time.rule.TkRule;

public class WeeklyOvertimeRule extends TkRule {

	private static final long serialVersionUID = 5229797885418317405L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "WeeklyOvertimeRule";

	private String tkWeeklyOvertimeRuleId;
	private String maxHoursEarnGroup;
	private String convertFromEarnGroup;
	private String convertToEarnCode;
	private BigDecimal step;
	private BigDecimal maxHours;
	private String userPrincipalId;
	private Boolean ovtEarnCode;
	
	private Long tkWeeklyOvertimeRuleGroupId = 1L;
	
	private EarnCodeGroup maxHoursEarnGroupObj;
	private EarnCodeGroup convertFromEarnGroupObj;
	private EarnCode convertToEarnCodeObj;
	

	public String getTkWeeklyOvertimeRuleId() {
		return tkWeeklyOvertimeRuleId;
	}

	public void setTkWeeklyOvertimeRuleId(String tkWeeklyOvertimeRuleId) {
		this.tkWeeklyOvertimeRuleId = tkWeeklyOvertimeRuleId;
	}

	public String getMaxHoursEarnGroup() {
		return maxHoursEarnGroup;
	}

	public void setMaxHoursEarnGroup(String maxHoursEarnGroup) {
		this.maxHoursEarnGroup = maxHoursEarnGroup;
	}

	public String getConvertFromEarnGroup() {
		return convertFromEarnGroup;
	}

	public void setConvertFromEarnGroup(String convertFromEarnGroup) {
		this.convertFromEarnGroup = convertFromEarnGroup;
	}

	public String getConvertToEarnCode() {
		return convertToEarnCode;
	}

	public void setConvertToEarnCode(String convertToEarnCode) {
		this.convertToEarnCode = convertToEarnCode;
	}

	public BigDecimal getStep() {
		return step;
	}

	public void setStep(BigDecimal step) {
		this.step = step;
	}

	public BigDecimal getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(BigDecimal maxHours) {
		this.maxHours = maxHours;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}
	
	
	public Boolean getOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(Boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
	}

	public EarnCodeGroup getMaxHoursEarnGroupObj() {
		return maxHoursEarnGroupObj;
	}

	public void setMaxHoursEarnGroupObj(EarnCodeGroup maxHoursEarnGroupObj) {
		this.maxHoursEarnGroupObj = maxHoursEarnGroupObj;
	}

	public EarnCodeGroup getConvertFromEarnGroupObj() {
		return convertFromEarnGroupObj;
	}

	public void setConvertFromEarnGroupObj(EarnCodeGroup convertFromEarnGroupObj) {
		this.convertFromEarnGroupObj = convertFromEarnGroupObj;
	}

	public EarnCode getConvertToEarnCodeObj() {
		return convertToEarnCodeObj;
	}

	public void setConvertToEarnCodeObj(EarnCode convertToEarnCodeObj) {
		this.convertToEarnCodeObj = convertToEarnCodeObj;
	}

	public Long getTkWeeklyOvertimeRuleGroupId() {
		return tkWeeklyOvertimeRuleGroupId;
	}

	public void setTkWeeklyOvertimeRuleGroupId(Long tkWeeklyOvertimeRuleGroupId) {
		this.tkWeeklyOvertimeRuleGroupId = tkWeeklyOvertimeRuleGroupId;
	}

	@Override
	public String getUniqueKey() {
		return convertFromEarnGroup + "_" + maxHoursEarnGroup;
	}

	@Override
	public String getId() {
		return getTkWeeklyOvertimeRuleId();
	}

	@Override
	public void setId(String id) {
		setTkWeeklyOvertimeRuleId(id);
	}
}
