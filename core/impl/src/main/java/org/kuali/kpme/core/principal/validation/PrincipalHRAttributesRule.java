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
package org.kuali.kpme.core.principal.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class PrincipalHRAttributesRule extends MaintenanceDocumentRuleBase {

	private boolean validatePrincipalId(PrincipalHRAttributes principalHRAttr) {
		if (StringUtils.isNotEmpty(principalHRAttr.getPrincipalId())
				&& !ValidationUtils.validatePrincipalId(principalHRAttr
						.getPrincipalId())) {
			this.putFieldError("principalId", "error.existence",
					"principalId '" + principalHRAttr.getPrincipalId() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validatePayCalendar(PrincipalHRAttributes principalHRAttr) {
		if (StringUtils.isNotEmpty(principalHRAttr.getPayCalendar())
				&& !ValidationUtils.validateCalendarByType(principalHRAttr.getPayCalendar(), "Pay")) {
			this.putFieldError("payCalendar", "error.existence",
					"Pay Calendar '" + principalHRAttr.getPayCalendar() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLeaveCalendar(PrincipalHRAttributes principalHRAttr) {
		if (StringUtils.isNotEmpty(principalHRAttr.getLeaveCalendar())
				&& !ValidationUtils.validateCalendarByType(principalHRAttr.getLeaveCalendar(), "Leave")) {
			this.putFieldError("leaveCalendar", "error.existence",
					"Leave Calendar '" + principalHRAttr.getLeaveCalendar() + "'");
			return false;
        } else {
		    return true;
        }
	}

	private boolean validateLeavePlan(PrincipalHRAttributes principalHRAttr) {
        if (StringUtils.isNotEmpty(principalHRAttr.getLeavePlan())
				&& !ValidationUtils.validateLeavePlan(principalHRAttr.getLeavePlan(), null)) {
			this.putFieldError("leavePlan", "error.existence",
					"leavePlan '" + principalHRAttr.getLeavePlan() + "'");
			return false;
		} else {
			return true;
		}
	}

    private boolean validateServiceDate(PrincipalHRAttributes principalHRAttr) {
        if (StringUtils.isNotEmpty(principalHRAttr.getLeavePlan())
                && principalHRAttr.getServiceDate() == null) {
            this.putFieldError("leavePlan", "validation.prerequisite", "'Service Date'");
            return false;
        }
        return true;
    }

    private boolean validateLeavefields(PrincipalHRAttributes principalHRAttr){
        if (StringUtils.isNotEmpty(principalHRAttr.getLeavePlan()) ||
                StringUtils.isNotEmpty(principalHRAttr.getLeaveCalendar()) ||
                principalHRAttr.getServiceDate() != null) {
            if (StringUtils.isNotEmpty(principalHRAttr.getLeavePlan()) &&
                    StringUtils.isNotEmpty(principalHRAttr.getLeaveCalendar()) &&
                    principalHRAttr.getServiceDate() != null) {
                return true;
            } else {
                if (!StringUtils.isNotEmpty(principalHRAttr.getLeavePlan())){
                    this.putFieldError("leavePlan","error.principalHrAttibutes.leavePlan.notPresent");
                }
                if (!StringUtils.isNotEmpty(principalHRAttr.getLeaveCalendar())){
                    this.putFieldError("leaveCalendar","error.principalHrAttibutes.leaveCalendar.notPresent");
                }
                if (principalHRAttr.getServiceDate() == null){
                    this.putFieldError("serviceDate","error.principalHrAttibutes.serviceDate.notPresent");
                }
                    return false;
            }
        } else {
            return true;
        }
    }

	boolean validateEffectiveDate(PrincipalHRAttributes principalHRAttr) {
		boolean valid = true;
		if (principalHRAttr.getEffectiveDate() != null && !ValidationUtils.validateOneYearFutureDate(principalHRAttr.getEffectiveLocalDate())) {
			this.putFieldError("effectiveDate", "error.date.exceed.year", "Effective Date");
			valid = false;
		}
		return valid;
	}
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;


		LOG.debug("entering custom validation for Job");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewDataObject();
		if (pbo instanceof PrincipalHRAttributes) {
			PrincipalHRAttributes principalHRAttr = (PrincipalHRAttributes) pbo;
			if (principalHRAttr != null) {
				valid = true;
				valid &= this.validatePrincipalId(principalHRAttr);
				// KPME-1442 Kagata
				//valid &= this.validateEffectiveDate(principalHRAttr);
				valid &= this.validatePayCalendar(principalHRAttr);
				valid &= this.validateLeaveCalendar(principalHRAttr);
				valid &= this.validateLeavePlan(principalHRAttr);
//                valid &= this.validateServiceDate(principalHRAttr);
                valid &= this.validateLeavefields(principalHRAttr);
			}
		}
		return valid;
	}

}
