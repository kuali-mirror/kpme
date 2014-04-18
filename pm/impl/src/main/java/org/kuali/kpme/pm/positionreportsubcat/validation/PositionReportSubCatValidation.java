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
package org.kuali.kpme.pm.positionreportsubcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategoryContract;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PositionReportSubCatValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Sub Category");
		PositionReportSubCategory prsc = (PositionReportSubCategory) this.getNewDataObject();
		
		if (prsc != null) {
			valid = true;
			valid &= this.validatePositionReportCategory(prsc);
			valid &= this.validateGroupKeyCode(prsc);
		}
		return valid;
	}
	
	private boolean validatePositionReportCategory(PositionReportSubCategory prsc) {
		PositionReportCategoryContract aCat = PmServiceLocator.getPositionReportCatService().getPositionReportCat(prsc.getPositionReportCat(), prsc.getEffectiveLocalDate());
		
		String errorMes = "PositionReportCategory '" + prsc.getPositionReportCat() + "'";
		if(aCat == null) {
			this.putFieldError("positionReportCat", "error.existence", errorMes);
			return false;
		} 
		return true;
	}	

	private boolean validateGroupKeyCode(PositionReportSubCategory prsc) {
		if (StringUtils.isNotEmpty(prsc.getGroupKeyCode())) {
			if(!ValidationUtils.validateGroupKey(prsc.getGroupKeyCode(), prsc.getEffectiveLocalDate())){
				this.putFieldError("groupKeyCode", "error.existence", "Group Key Code '"
						+ prsc.getGroupKeyCode() + "'");
				return false;
			}
		}
		return true;
	}
	
}
