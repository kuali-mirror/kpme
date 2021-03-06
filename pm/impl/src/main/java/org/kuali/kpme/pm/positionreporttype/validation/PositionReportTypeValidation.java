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
package org.kuali.kpme.pm.positionreporttype.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class PositionReportTypeValidation extends MaintenanceDocumentRuleBase  {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Type");
		PositionReportTypeBo prt = (PositionReportTypeBo) this.getNewDataObject();
		
		if (prt != null) {
			valid = true;
			valid &= this.validateGroupKey(prt);
		}
		return valid;
	}
	
	protected boolean validateGroupKey(PositionReportTypeBo prt) {
		if (StringUtils.isNotEmpty(prt.getGroupKeyCode())
				&& !ValidationUtils.validateGroupKey(prt.getGroupKeyCode(), prt.getEffectiveLocalDate())) {
			this.putFieldError("groupKeyCode", "error.existence", "Group Key '"
					+ prt.getGroupKeyCode() + "'");
			return false;
		} else {
			return true;
		}
	}
}
