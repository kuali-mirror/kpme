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
package org.kuali.kpme.pm.pstnqlfrtype.validation;

import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PstnQlfrTypeValidation extends MaintenanceDocumentRuleBase {
	public static final String VALUE_SEPARATOR = ",";	// separator for select values
	
//	@Override
//	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
//		boolean valid = false;
//		LOG.debug("entering custom validation for Salary Group");
//		PstnQlfrType pqt = (PstnQlfrType) this.getNewDataObject();
//		
//		if (pqt != null) {
//			valid = true;
//			valid &= this.validateSelectValues(pqt);
//		}
//		return valid;
//	}
	
//	private boolean validateSelectValues(PstnQlfrType pqt) {
//		if (StringUtils.isNotEmpty(pqt.getSelectValues())) {
//			String[] values = pqt.getSelectValues().split(VALUE_SEPARATOR);
//			for(String aValue : values) {
//				String realName = StringUtils.trim(aValue);
//				if(StringUtils.isNotEmpty(realName)) {
//					if(!PmValidationUtils.validatePositionQualificationValue(realName)) {	// if any value is invalid, there's error
//						this.putFieldError("dataObject.selectValues", "error.existence", "Position Qualification Type '"
//								+ realName + "'");
//						return false;
//					}
//				}
//			}
//		}
//		return true;
//	}
}
