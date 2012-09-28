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
package org.kuali.hr.lm.leavecode.validation;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class LeaveCodeValidation extends MaintenanceDocumentRuleBase {	 
	boolean validateEffectiveDate(Date effectiveDate) {
		boolean valid = true;
		if (effectiveDate != null && !ValidationUtils.validateOneYearFutureDate(effectiveDate)) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}
/*	KPME-1477
	boolean validateLeaveCodeRole(LeaveCode leaveCode) {
		boolean valid = true;
		if (!leaveCode.getEmployee() && !leaveCode.getDepartmentAdmin() && !leaveCode.getApprover()) {
			this.putFieldError("employee", "error.leavecode.role.required");
			valid = false;
		}
		return valid;
	}
*/	
	//KPME-1541 we need to set the leave plan but not silently override if the user uses the Leave Plan field
	boolean validateLeavePlan(LeaveCode leaveCode) {
		
		boolean valid = true;
		
		
		if (StringUtils.isNotBlank(leaveCode.getLeavePlan())) {

			if (!ValidationUtils.validateLeavePlan(leaveCode.getLeavePlan(), leaveCode.getEffectiveDate())) {
				this.putFieldError("leavePlan", "error.existence", "leavePlan '"
						+ leaveCode.getLeavePlan() + "'");
				valid = false;
				return valid;
			}
			
			if (leaveCode.getEffectiveDate() != null && StringUtils.isNotBlank(leaveCode.getAccrualCategory())) {
				AccrualCategory myTestAccrualCategoryObj =  TkServiceLocator.getAccrualCategoryService().getAccrualCategory(leaveCode.getAccrualCategory(), leaveCode.getEffectiveDate());
				if (!myTestAccrualCategoryObj.getLeavePlan().equals(leaveCode.getLeavePlan())) {
					this.putFieldError("leavePlan", "error.leaveCode.leavePlanMismatch", myTestAccrualCategoryObj.getLeavePlan());
					valid = false;
					return valid;
				}
				leaveCode.setLeavePlan(myTestAccrualCategoryObj.getLeavePlan());
			}
		}
		return valid;
	}
	
	//KPME-1541 must have an accrual category for leave plan lookup
	boolean validateAccrualCategory(String accrualCategory, Date asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateAccCategory(accrualCategory, asOfDate)) {
			this.putFieldError("accrualCategory", "error.existence", "accrualCategory '"
					+ accrualCategory + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateEarnCode(String earnCode, Date asOfDate) {
		boolean valid = true;
		if (!StringUtils.isEmpty(earnCode) && !ValidationUtils.validateEarnCode(earnCode, asOfDate)) {
			this.putFieldError("earnCode", "error.existence", "earnCode '"
					+ earnCode + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateDefaultAmountOfTime(Long defaultAmountofTime) {
		boolean valid = true;
		if ( defaultAmountofTime != null ){
			if (defaultAmountofTime.compareTo(new Long(24)) > 0  || defaultAmountofTime.compareTo(new Long(0)) < 0) {
				this.putFieldError("defaultAmountofTime", "error.leaveCode.hours", "Default Amount of Time '"
						+ defaultAmountofTime + "'");
				valid = false;
			}
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Leave Code");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof LeaveCode) {
			LeaveCode leaveCode = (LeaveCode) pbo;
			if (leaveCode != null) {
				valid = true;
				//valid &= this.validateEffectiveDate(leaveCode.getEffectiveDate());
				//valid &= this.validateLeaveCodeRole(leaveCode); // xichen. remove this validation, this field is not on the page. KPME1477
				valid &= this.validateAccrualCategory(leaveCode.getAccrualCategory(), leaveCode.getEffectiveDate());
				valid &= this.validateEarnCode(leaveCode.getEarnCode(), leaveCode.getEffectiveDate());
				valid &= this.validateDefaultAmountOfTime(leaveCode.getDefaultAmountofTime());
				valid &= this.validateLeavePlan(leaveCode); //validate accrual category and effdt before calling this
			}
		}
		return valid;
	}
}
