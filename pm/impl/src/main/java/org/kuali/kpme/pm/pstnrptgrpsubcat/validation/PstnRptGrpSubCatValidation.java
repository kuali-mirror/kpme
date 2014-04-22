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
package org.kuali.kpme.pm.pstnrptgrpsubcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.validation.HrKeyedBusinessObjectValidation;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroupContract;
import org.kuali.kpme.pm.api.positionreportsubcat.PositionReportSubCategoryContract;
import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategoryBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

public class PstnRptGrpSubCatValidation extends HrKeyedBusinessObjectValidation {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Group Sub Category");
		PositionReportGroupSubCategoryBo prgsc = (PositionReportGroupSubCategoryBo) this.getNewDataObject();
		
		if (prgsc != null) {
			valid = true;
			valid &= this.validatePstnRptSubCat(prgsc);
			valid &= this.validateGroupKeyCode(prgsc);
			valid &= this.validatePstnRptGroup(prgsc);
			
		}
		return valid;
	}
	
	private boolean validatePstnRptSubCat(PositionReportGroupSubCategoryBo prgsc) {
		// validatePositionReportSubCat handles wild card for Institution and Location
		PositionReportSubCategoryContract aPrsc = PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCat(prgsc.getPositionReportSubCat(), prgsc.getEffectiveLocalDate());
		String errorMes = "PositionReportSubCategory '" + prgsc.getPositionReportSubCat() + "'";
		if(aPrsc == null) {
			this.putFieldError("dataObject.positionReportSubCat", "error.existence", errorMes);
			return false;
		} 
		else if(!StringUtils.equals(aPrsc.getGroupKeyCode(), prgsc.getGroupKeyCode())) {
			String[] params = new String[3];
			params[0] = prgsc.getGroupKeyCode();
			params[1] = aPrsc.getGroupKeyCode();
			params[2] = errorMes;
			this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
			return false;
		}
		return true;
	}
	
	private boolean validatePstnRptGroup(PositionReportGroupSubCategoryBo prgsc) {
		PositionReportGroupContract aPrg = PmServiceLocator.getPositionReportGroupService().getPositionReportGroup(prgsc.getPositionReportGroup(), prgsc.getEffectiveLocalDate());
		String errorMes = "PositionReportGroup '" + prgsc.getPositionReportGroup() + "'";
		if(aPrg == null) {
			this.putFieldError("dataObject.positionReportGroup", "error.existence", errorMes);
			return false;
		} 
		else if(!StringUtils.equals(aPrg.getGroupKeyCode(), prgsc.getGroupKeyCode())) {
			String[] params = new String[3];
			params[0] = aPrg.getGroupKeyCode();
			params[1] = aPrg.getGroupKeyCode();
			params[2] = errorMes;
			this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
			return false;
		}		
		return true;
		
	}
}
