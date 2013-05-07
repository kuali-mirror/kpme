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
package org.kuali.kpme.core.bo.paytype.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.utils.ValidationUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class PayTypeRule extends MaintenanceDocumentRuleBase {

	boolean validateEarnCode(String regEarnCode, LocalDate asOfDate) {
		boolean valid = ValidationUtils.validateEarnCode(regEarnCode, asOfDate);

		if (!valid) {
			this.putFieldError("regEarnCode", "earncode.notfound");
		} else {
			valid = !ValidationUtils.validateEarnCode(regEarnCode, true,
					asOfDate);
			if (!valid) {
				this.putFieldError("regEarnCode", "earncode.ovt.not.required",
						regEarnCode);
			}
		}

		return valid;
	}
	
	private boolean validateInstitution(String institution, LocalDate asOfDate) {
		boolean valid = true;
		
		if (!StringUtils.isBlank(institution)) {
			valid = ValidationUtils.validateInstitution(institution, asOfDate);

			if (!valid) {
				this.putFieldError("institution", "paytype.institution.invalid", institution);
			} 			
		}
		
		return valid;
	}
	
	private boolean validateCampus(String campus, LocalDate asOfDate) {
		boolean valid = true;
		
		if (!StringUtils.isBlank(campus)) {
			valid = ValidationUtils.validateEarnCode(campus, asOfDate);

			if (!valid) {
				this.putFieldError("campus", "paytype.campus.invalid", campus);
			} 			
		}

		return valid;
	}
	

	boolean validateActive(String hrPayType, LocalDate asOfDate) {
		boolean valid = true;
		List<Job> jobs = HrServiceLocator.getJobService()
				.getActiveJobsForPayType(hrPayType, asOfDate);
		if (jobs != null && !jobs.isEmpty()) {
			this.putFieldError("active", "paytype.inactivate.locked", hrPayType);
			valid = false;
		}
		return valid;
	}
    
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;

		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof PayType) {
			PayType pt = (PayType) pbo;

			valid = validateEarnCode(pt.getRegEarnCode(), pt.getEffectiveLocalDate());
			valid &= validateInstitution(pt.getInstitution(), pt.getEffectiveLocalDate());
			valid &= validateCampus(pt.getCampus(), pt.getEffectiveLocalDate());
			if (document.isOldBusinessObjectInDocument() && !pt.isActive()) {
				valid &= validateActive(pt.getPayType(), pt.getEffectiveLocalDate());
			}
		}

		return valid;
	}
}
