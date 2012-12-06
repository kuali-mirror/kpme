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
package org.kuali.hr.lm.leaveplan.validation;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class LeavePlanValidation extends MaintenanceDocumentRuleBase {

	// KPME-1250 Kagata
	// This method determines if the leave plan can be inactivated
	boolean validateInactivation(LeavePlan leavePlan) {
		boolean valid = true;
		// Get a list of active employees based on leave plan and its effective
		// date.
		// If the list is not null, there are active employees and the leave
		// plan can't be inactivated, so return false otherwise true
		if (!leavePlan.isActive()) {
			// this has to use the effective date of the job passed in
			List<PrincipalHRAttributes> pList = TkServiceLocator
					.getPrincipalHRAttributeService()
					.getActiveEmployeesForLeavePlan(leavePlan.getLeavePlan(),
							leavePlan.getEffectiveDate());

			if (pList != null && pList.size() > 0) {
				// error.leaveplan.inactivate=Can not inactivate leave plan {0}.
				// There are active employees in the plan.
				this.putFieldError("active", "error.leaveplan.inactivate",
						leavePlan.getLeavePlan());
				valid = false;
			}
		}

		return valid;
	}

	// KPME-1407 Kagata
	boolean validatePlanningMonths(String planningMonths) {
		boolean valid = true;
		if (planningMonths != null) {
			int iPlanningMonths = Integer.parseInt(planningMonths);
			// error.leaveplan.planningMonths='{0}' should be between 1 and 24.
			if (iPlanningMonths > 24 || iPlanningMonths <= 0) {
				this.putFieldError("planningMonths",
						"error.leaveplan.planningMonths", "Planning Months");
				valid = false;
			}
		}
		return valid;
	}

	boolean validateEffectiveDate(Date effectiveDate, String leavePlan) {
		boolean valid = true;
		valid = ValidationUtils.validateOneYearFutureEffectiveDate(effectiveDate);
		if(!valid) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
		} else {
			if(leavePlan != null && StringUtils.isNotEmpty(leavePlan.trim())) {
		        LeavePlan lastLeavePlan = TkServiceLocator.getLeavePlanService().getLeavePlan(leavePlan,effectiveDate);
		        if(lastLeavePlan != null) {
			        if(TKUtils.getTimelessDate(lastLeavePlan.getEffectiveDate()).compareTo(TKUtils.getTimelessDate(effectiveDate)) <= 0){
			        	valid = false;
			        	this.putFieldError("effectiveDate", "error.leavePlan.effectiveDate.newr.exists", "Effective Date");
			        }
		        }
			}
		}
		return valid;
	}

	boolean validateCalendarYearStart(String dateString) {
		if (StringUtils.isBlank(dateString)) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		sdf.setLenient(false);
		try {
			sdf.parse(dateString);
		} catch (ParseException e) {
			this.putFieldError("calendarYearStart",
					"error.calendar.year.start", "Calendar Year Start");
			return false;
		}
		return true;
	}

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = true;
		LOG.debug("entering custom validation for Leave Plan");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this
				.getNewBo();
		if (pbo instanceof LeavePlan) {
			LeavePlan leavePlan = (LeavePlan) pbo;
			if (leavePlan != null) {
				valid = true;
				valid &= this.validateInactivation(leavePlan);
				if (StringUtils.isNotEmpty(leavePlan.getPlanningMonths())) {
					valid &= this.validatePlanningMonths(leavePlan
							.getPlanningMonths());
				}
				if (leavePlan.getEffectiveDate() != null) {
					valid &= this.validateEffectiveDate(
							leavePlan.getEffectiveDate(),
							leavePlan.getLeavePlan());
				}
				valid &= this.validateCalendarYearStart(leavePlan
						.getCalendarYearStart());
			}
		}
		return valid;
	}
}
