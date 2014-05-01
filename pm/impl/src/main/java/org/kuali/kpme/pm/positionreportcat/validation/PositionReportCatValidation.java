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
package org.kuali.kpme.pm.positionreportcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.api.positionreporttype.PositionReportTypeContract;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategoryBo;
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PositionReportCatValidation extends MaintenanceDocumentRuleBase  {
	
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Category");
		PositionReportCategoryBo prc = (PositionReportCategoryBo) this.getNewDataObject();
		
		if (prc != null) {
			valid = true;
//			valid &= this.validateInstitution(prc);
//			valid &= this.validateLocation(prc);
			valid &= this.validatePositionReportType(prc);
		}
		return valid;
	}
	
	private boolean validatePositionReportType(PositionReportCategoryBo prc) {
		// validatePositionReportType handles wild card for institution and location
		PositionReportTypeContract aType = PmServiceLocator.getPositionReportTypeService().getPositionReportType(prc.getPositionReportType(), prc.getEffectiveLocalDate());
		String positionReportTypeError = "PositionReportType '" + prc.getPositionReportType() + "'";
		if(aType == null) {
			this.putFieldError("positionReportType", "error.existence", positionReportTypeError);
			return false;
		} else {
			if(!StringUtils.equals(aType.getGroupKeyCode(),prc.getGroupKeyCode())) {
				String[] params = new String[3];
				params[0] = prc.getGroupKeyCode();
				params[1] = aType.getGroupKeyCode();
				params[2] = positionReportTypeError;
				this.putFieldError("groupKeyCode", "groupKey.inconsistent", params);
				return false;
			}
		}
		return true;
	}	
	
//	private boolean validateInstitution(PositionReportCategory prc) {
//		if (StringUtils.isNotEmpty(prc.getInstitution())) {
//			if(!ValidationUtils.validateInstitution(prc.getInstitution(), prc.getEffectiveLocalDate())) {
//				this.putFieldError("institution", "error.existence", "Instituion '"
//						+ prc.getInstitution() + "'");
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	private boolean validateLocation(PositionReportCategory prc) {
//		if (StringUtils.isNotEmpty(prc.getLocation())) {
//			if(!ValidationUtils.validateLocation(prc.getLocation(), prc.getEffectiveLocalDate())) {
//				this.putFieldError("location", "error.existence", "Location '"
//						+ prc.getLocation() + "'");
//				return false;
//			}
//		}
//		return true;
//	}
	
}
