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
package org.kuali.hr.tklm.time.overtime.daily.rule.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.tklm.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.hr.tklm.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

public class DailyOvertimeRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateWorkArea(DailyOvertimeRule ruleObj) {
		boolean valid = true;
		if (!ValidationUtils.validateWorkArea(ruleObj.getWorkArea(), ruleObj
				.getEffectiveLocalDate())) {
			this.putFieldError("workArea", "error.existence", "workArea '"
					+ ruleObj.getWorkArea() + "'");
			valid = false;
		} else if (!ruleObj.getWorkArea().equals(TkConstants.WILDCARD_LONG)) {
			int count= TkServiceLocator.getWorkAreaService().getWorkAreaCount(ruleObj.getDept(), ruleObj.getWorkArea());
			valid = (count > 0);
			if (!valid) {
				this.putFieldError("workArea", "dept.workarea.invalid.sync",
						ruleObj.getWorkArea() + "");
			}
		}
		return valid;
	}

	boolean validateDepartment(DailyOvertimeRule ruleObj) {
		if (ruleObj.getDept() != null
				&& !ruleObj.getDept().equals(TkConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validateDepartment(ruleObj.getDept(),
						ruleObj.getEffectiveLocalDate())) {
			this.putFieldError("dept", "error.existence", "department '"
					+ ruleObj.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateEarnCode(DailyOvertimeRule dailyOvertimeRule) {
		if (dailyOvertimeRule.getEarnCode() != null
				&& !ValidationUtils.validateEarnCode(dailyOvertimeRule
						.getEarnCode(), dailyOvertimeRule.getEffectiveLocalDate())) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ dailyOvertimeRule.getEarnCode() + "'");
			return false;
		} else {
			if (dailyOvertimeRule.getEarnCode() != null
					&& !ValidationUtils.validateEarnCode(dailyOvertimeRule
							.getEarnCode(), true, dailyOvertimeRule
							.getEffectiveLocalDate())) {
				this.putFieldError("earnCode", "earncode.ovt.required",
						dailyOvertimeRule.getEarnCode());
				return false;
			}
			return true;
		}
	}

	boolean validateEarnGroup(DailyOvertimeRule dailyOvertimeRule) {
		if (dailyOvertimeRule.getFromEarnGroup() != null
				&& !ValidationUtils.validateEarnGroup(dailyOvertimeRule
						.getFromEarnGroup(), dailyOvertimeRule
						.getEffectiveLocalDate())) {
			this.putFieldError("fromEarnGroup", "error.existence",
					"from EarnCodeGroup '" + dailyOvertimeRule.getFromEarnGroup()
							+ "'");
			return false;
		}
		if (!StringUtils.isEmpty(dailyOvertimeRule.getFromEarnGroup())
				&& ValidationUtils.earnGroupHasOvertimeEarnCodes(dailyOvertimeRule.getFromEarnGroup(), dailyOvertimeRule.getEffectiveLocalDate())){
			this.putFieldError("fromEarnGroup", "earngroup.earncode.overtime", dailyOvertimeRule.getFromEarnGroup());
			return false;
		}
		return true;
	}
	
	boolean validateLocation(DailyOvertimeRule dailyOvertimeRule) {
		if (dailyOvertimeRule.getLocation() != null
				&& !ValidationUtils.validateLocation(dailyOvertimeRule
						.getLocation(), dailyOvertimeRule.getEffectiveLocalDate())) {
			this.putFieldError("location", "error.existence", "location '"
					+ dailyOvertimeRule.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validatePayType(DailyOvertimeRule dailyOvertimeRule) {
		if (dailyOvertimeRule.getPaytype() != null
				&& !dailyOvertimeRule.getPaytype().equals(
						TkConstants.WILDCARD_CHARACTER)
				&& !ValidationUtils.validatePayType(dailyOvertimeRule
						.getPaytype(), dailyOvertimeRule.getEffectiveLocalDate())) {
			this.putFieldError("paytype", "error.existence", "paytype '"
					+ dailyOvertimeRule.getPaytype() + "'");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
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
				valid &= this.validateLocation(dailyOvertimeRule);
				valid &= this.validatePayType(dailyOvertimeRule);
				valid &= this.validateDepartment(dailyOvertimeRule);
				valid &= this.validateWorkArea(dailyOvertimeRule);
				valid &= this.validateEarnCode(dailyOvertimeRule);
				valid &= this.validateEarnGroup(dailyOvertimeRule);
			}
		}

		return valid;
	}

}