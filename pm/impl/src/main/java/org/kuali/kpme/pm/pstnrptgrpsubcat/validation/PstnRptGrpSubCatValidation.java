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
package org.kuali.kpme.pm.pstnrptgrpsubcat.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PstnRptGrpSubCatValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Report Group Sub Category");
		PositionReportGroupSubCategory prgsc = (PositionReportGroupSubCategory) this.getNewDataObject();
		
		if (prgsc != null) {
			valid = true;
			valid &= this.validatePstnRptSubCat(prgsc);
			valid &= this.validateInstitution(prgsc);
			valid &= this.validateLocation(prgsc);
			valid &= this.validatePstnRptGroup(prgsc);
			
		}
		return valid;
	}
	
	private boolean validatePstnRptSubCat(PositionReportGroupSubCategory prgsc) {
		// validatePositionReportSubCat handles wild card for Institution and Location
		PositionReportSubCategory aPrsc = PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCat(prgsc.getPositionReportSubCat(), prgsc.getEffectiveLocalDate());
		String errorMes = "PositionReportSubCategory '" + prgsc.getPositionReportSubCat() + "'";
		if(aPrsc == null) {
			this.putFieldError("dataObject.positionReportSubCat", "error.existence", errorMes);
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(aPrsc.getInstitution(), prgsc.getInstitution())) {
				String[] params = new String[3];
				params[0] = prgsc.getInstitution();
				params[1] = aPrsc.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aPrsc.getLocation(), prgsc.getLocation())) {
				String[] params = new String[3];
				params[0] = prgsc.getLocation();
				params[1] = aPrsc.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		}
		return true;
	}
	
	private boolean validateInstitution(PositionReportGroupSubCategory prgsc) {
		if (StringUtils.isNotEmpty(prgsc.getInstitution())
				&& !ValidationUtils.validateInstitution(prgsc.getInstitution(), prgsc.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ prgsc.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLocation(PositionReportGroupSubCategory prgsc) {
		if (StringUtils.isNotEmpty(prgsc.getLocation())
				&& !ValidationUtils.validateLocation(prgsc.getLocation(), prgsc.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.location", "error.existence", "Location '"
					+ prgsc.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validatePstnRptGroup(PositionReportGroupSubCategory prgsc) {
		PositionReportGroup aPrg = PmServiceLocator.getPositionReportGroupService().getPositionReportGroup(prgsc.getPositionReportGroup(), prgsc.getEffectiveLocalDate());
		String errorMes = "PositionReportGroup '" + prgsc.getPositionReportGroup() + "'";
		if(aPrg == null) {
			this.putFieldError("dataObject.positionReportGroup", "error.existence", errorMes);
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(aPrg.getInstitution(), prgsc.getInstitution())) {
				String[] params = new String[3];
				params[0] = prgsc.getInstitution();
				params[1] = aPrg.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aPrg.getLocation(), prgsc.getLocation())) {
				String[] params = new String[3];
				params[0] = prgsc.getLocation();
				params[1] = aPrg.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		} 
		
		return true;
		
	}
}
