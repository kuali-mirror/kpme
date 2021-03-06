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
package org.kuali.kpme.tklm.time.rules.overtime.weekly;

import java.math.BigDecimal;

import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupBo;
import org.kuali.kpme.tklm.api.time.rules.overtime.weekly.WeeklyOvertimeRuleContract;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.time.rules.TkRule;

import com.google.common.collect.ImmutableMap;

public class WeeklyOvertimeRule extends TkRule implements WeeklyOvertimeRuleContract {

	private static final long serialVersionUID = 5229797885418317405L;

	public static final String CACHE_NAME = TkConstants.Namespace.NAMESPACE_PREFIX + "WeeklyOvertimeRule";

	private String tkWeeklyOvertimeRuleId;
	private String maxHoursEarnGroup;
	private String convertFromEarnGroup;
	private String convertToEarnCode;
    private String applyToEarnGroup;

    private boolean overrideWorkAreaDefaultOvertime;

	private BigDecimal step;
	private BigDecimal maxHours;
	private boolean ovtEarnCode;
	
	private Long tkWeeklyOvertimeRuleGroupId = 1L;
	
	private EarnCodeGroupBo maxHoursEarnGroupObj;
	private EarnCodeGroupBo convertFromEarnGroupObj;
	private EarnCodeGroupBo applyToEarnGroupObj;

    private EarnCodeBo convertToEarnCodeObj;

	
	// TODO returning an empty map for the time-being, until the BK is finalized
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.build();
	}
	

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

	public boolean isOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
	}

	public EarnCodeGroupBo getMaxHoursEarnGroupObj() {
		return maxHoursEarnGroupObj;
	}

	public void setMaxHoursEarnGroupObj(EarnCodeGroupBo maxHoursEarnGroupObj) {
		this.maxHoursEarnGroupObj = maxHoursEarnGroupObj;
	}

	public EarnCodeGroupBo getConvertFromEarnGroupObj() {
		return convertFromEarnGroupObj;
	}

	public void setConvertFromEarnGroupObj(EarnCodeGroupBo convertFromEarnGroupObj) {
		this.convertFromEarnGroupObj = convertFromEarnGroupObj;
	}

	public EarnCodeBo getConvertToEarnCodeObj() {
		return convertToEarnCodeObj;
	}

	public void setConvertToEarnCodeObj(EarnCodeBo convertToEarnCodeObj) {
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

    @Override
    public String getApplyToEarnGroup() {
        return applyToEarnGroup;
    }

    public void setApplyToEarnGroup(String applyToEarnGroup) {
        this.applyToEarnGroup = applyToEarnGroup;
    }



    public EarnCodeGroupBo getApplyToEarnGroupObj() {
        return applyToEarnGroupObj;
    }

    public void setApplyToEarnGroupObj(EarnCodeGroupBo applyToEarnGroupObj) {
        this.applyToEarnGroupObj = applyToEarnGroupObj;
    }

    public boolean isOverrideWorkAreaDefaultOvertime() {
        return overrideWorkAreaDefaultOvertime;
    }

    public void setOverrideWorkAreaDefaultOvertime(boolean overrideWorkAreaDefaultOvertime) {
        this.overrideWorkAreaDefaultOvertime = overrideWorkAreaDefaultOvertime;
    }
}
