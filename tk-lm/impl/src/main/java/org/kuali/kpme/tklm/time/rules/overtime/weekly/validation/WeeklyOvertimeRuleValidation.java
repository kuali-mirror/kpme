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
package org.kuali.kpme.tklm.time.rules.overtime.weekly.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRuleGroup;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;

public class WeeklyOvertimeRuleValidation extends MaintenanceDocumentRuleBase {

    private static final String ADD_LINE_LOCATION = "add.lstWeeklyOvertimeRules.";
    private BigDecimal maxHours;
    private String maxHoursEarnGroup;

    @Override
    public boolean processCustomAddCollectionLineBusinessRules(MaintenanceDocument document, String collectionName, PersistableBusinessObject line) {
        boolean valid = true;

        if (line instanceof WeeklyOvertimeRule) {
            WeeklyOvertimeRule rule = (WeeklyOvertimeRule)line;
            valid = validateWeeklyOvertimeRule(rule, ADD_LINE_LOCATION);
        }

        return valid;
    }

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;
		WeeklyOvertimeRuleGroup ruleGroup = (WeeklyOvertimeRuleGroup)this.getNewBo();

		int index = 0;
		for(WeeklyOvertimeRule rule : ruleGroup.getLstWeeklyOvertimeRules()) {
            valid &= validateMatchingMaxHours(rule.getMaxHours(), "lstWeeklyOvertimeRules["+index+"].");
            valid &= validateMatchingMaxHoursGroup(rule.getMaxHoursEarnGroup(), "lstWeeklyOvertimeRules["+index+"].");

            rule.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            valid &= validateWeeklyOvertimeRule(rule, "lstWeeklyOvertimeRules["+index+"].");
			index++;
		}

		return valid;
	}

    /*
    weeklyOvertimeRule.maxHours.mustMatch=The Max Hours for all steps must match.
weeklyOvertimeRule.maxHoursEarnGroup.mustMatch=The Max Hour Earn Group for all steps must match.

     */

    boolean validateMatchingMaxHours(BigDecimal ruleMaxHours, String errorFieldPrefix)
    {
        if (errorFieldPrefix == null)
        {
            errorFieldPrefix = "";
        }

        if (this.maxHours == null)
        {
            this.maxHours = ruleMaxHours;
        }

        if (this.maxHours.compareTo(ruleMaxHours) == 0)
        {
            return true;
        }

        this.putFieldError(errorFieldPrefix + "maxHours", "weeklyOvertimeRule.maxHours.mustMatch");
        return false;
    }

    boolean validateMatchingMaxHoursGroup(String ruleMaxHoursEarnGroup, String errorFieldPrefix)
    {
        if (errorFieldPrefix == null)
        {
            errorFieldPrefix = "";
        }

        if (this.maxHoursEarnGroup == null)
        {
            this.maxHoursEarnGroup = ruleMaxHoursEarnGroup;
        }

        if (StringUtils.equals(ruleMaxHoursEarnGroup, this.maxHoursEarnGroup))
        {
            return true;
        }

        this.putFieldError(errorFieldPrefix + "maxHoursEarnGroup", "weeklyOvertimeRule.maxHoursEarnGroup.mustMatch");
        return false;
    }

    boolean validateWeeklyOvertimeRule(WeeklyOvertimeRule rule, String errorFieldPrefix) {
        boolean valid = true;

        if (errorFieldPrefix == null)
            errorFieldPrefix = "";

        if(!StringUtils.isEmpty(rule.getMaxHoursEarnGroup())) {
            if(!ValidationUtils.validateEarnGroup(rule.getMaxHoursEarnGroup(), rule.getEffectiveLocalDate())) {
                this.putFieldError(errorFieldPrefix + "maxHoursEarnGroup", "error.existence", "maxHoursEarnGroup '" + rule.getMaxHoursEarnGroup() + "'");
                valid = false;
            }
        }
        if(!StringUtils.isEmpty(rule.getConvertFromEarnGroup())) {
            if(!ValidationUtils.validateEarnGroup(rule.getConvertFromEarnGroup(), rule.getEffectiveLocalDate())) {
                this.putFieldError(errorFieldPrefix + "convertFromEarnGroup", "error.existence", "convertFromEarnGroup '" + rule.getConvertFromEarnGroup() + "'");
                valid = false;
            }
        }
        
		if (!StringUtils.isEmpty(rule.getConvertFromEarnGroup())
				&& ValidationUtils.earnGroupHasOvertimeEarnCodes(rule.getConvertFromEarnGroup(), rule.getEffectiveLocalDate())){
			this.putFieldError(errorFieldPrefix + "convertFromEarnGroup", "earngroup.earncode.overtime",  rule.getConvertFromEarnGroup());
			valid = false;;
		}

        if(!StringUtils.isEmpty(rule.getConvertToEarnCode())) {
            if(!ValidationUtils.validateEarnCode(rule.getConvertToEarnCode(), rule.getEffectiveLocalDate())) {
                this.putFieldError(errorFieldPrefix + "convertToEarnCode", "error.existence", "convertToEarnCode '" + rule.getConvertToEarnCode() + "'");
                valid = false;
            } else if (!ValidationUtils.validateEarnCode(rule.getConvertToEarnCode(), true, rule.getEffectiveLocalDate())) {
                this.putFieldError(errorFieldPrefix + "convertToEarnCode", "earncode.ovt.required", rule.getConvertToEarnCode());
                valid = false;
            }
        }

        return valid;
    }
/*
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for DailyOvertimeRule");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof DailyOvertimeRule) {
			DailyOvertimeRule dailyOvertimeRule = (DailyOvertimeRule) pbo;
			dailyOvertimeRule.setUserPrincipalId(GlobalVariables
					.getUserSession().getPrincipalId());
			if (dailyOvertimeRule != null) {
				valid = true;
				//valid &= this.validateLocation(dailyOvertimeRule);
				valid &= this.validatePayType(dailyOvertimeRule);
				valid &= this.validateDepartment(dailyOvertimeRule);
				valid &= this.validateWorkArea(dailyOvertimeRule);
				valid &= this.validateEarnCode(dailyOvertimeRule);
				valid &= this.validateEarnGroup(dailyOvertimeRule);
				valid &= this.validateGroupKeyCode(dailyOvertimeRule);
			}
		}

		return valid;
	}
 */
}
