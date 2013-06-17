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
package org.kuali.kpme.pm.classification.validation;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.salarygroup.SalaryGroup;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.positiontype.PositionType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class ClassificationValidation extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Classification");
		Classification clss = (Classification) this.getNewDataObject();
		
		if (clss != null) {
			valid = true;
			valid &= this.validateInstitution(clss);
			valid &= this.validateLocation(clss);
			valid &= this.validateSalGroup(clss);
			valid &= this.validateLeavePlan(clss);
			valid &= this.validateReportingGroup(clss);
			valid &= this.validatePositionType(clss);
			valid &= this.validatePercentTime(clss);
		}
		return valid;
	}
	
	private boolean validateInstitution(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getInstitution())
				&& !ValidationUtils.validateInstitution(clss.getInstitution(), clss.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.institution", "error.existence", "Instituion '"
					+ clss.getInstitution() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLocation(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getLocation())
				&& !ValidationUtils.validateLocation(clss.getLocation(), clss.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.location", "error.existence", "Location '"
					+ clss.getLocation() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateLeavePlan(Classification clss) {
		if (StringUtils.isNotEmpty(clss.getLeavePlan())
				&& !ValidationUtils.validateLeavePlan(clss.getLeavePlan(), clss.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.leavePlan", "error.existence", "Leave Plan '"
					+ clss.getLeavePlan() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateSalGroup(Classification clss) {
		SalaryGroup aSalGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(clss.getSalaryGroup(), clss.getEffectiveLocalDate());
		String errorMes = "SalaryGroup '" + clss.getSalaryGroup() + "'";
		if(aSalGroup != null) {
			if(!ValidationUtils.wildCardMatch(aSalGroup.getInstitution(), clss.getInstitution())) {
				String[] params = new String[3];
				params[0] = clss.getInstitution();
				params[1] = aSalGroup.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aSalGroup.getLocation(), clss.getLocation())) {
				String[] params = new String[3];
				params[0] = clss.getLocation();
				params[1] = aSalGroup.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		} else {
			this.putFieldError("dataObject.salaryGroup", "error.existence", errorMes);
			return false;
		}
		
		return true;
	}
	
	private boolean validateReportingGroup(Classification clss) {
		PositionReportGroup aPrg = PmServiceLocator.getPositionReportGroupService().getPositionReportGroup(clss.getPositionReportGroup(), clss.getEffectiveLocalDate());
		String errorMes = "PositionReportGroup '" + clss.getPositionReportGroup() + "'";
		if(aPrg == null) {
			this.putFieldError("dataObject.positionReportGroup", "error.existence", errorMes);
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(aPrg.getInstitution(), clss.getInstitution())) {
				String[] params = new String[3];
				params[0] = clss.getInstitution();
				params[1] = aPrg.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aPrg.getLocation(), clss.getLocation())) {
				String[] params = new String[3];
				params[0] = clss.getLocation();
				params[1] = aPrg.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		} 
		
		return true;
	}
	
	private boolean validatePositionType(Classification clss) {
		PositionType aPType = PmServiceLocator.getPositionTypeService().getPositionType(clss.getPositionType(),  clss.getEffectiveLocalDate());
		String errorMes = "PositionType '" + clss.getPositionType() + "'";
		if(aPType == null) {
			this.putFieldError("dataObject.positionType", "error.existence", errorMes);
			return false;
		} else {
			if(!ValidationUtils.wildCardMatch(aPType.getInstitution(), clss.getInstitution())) {
				String[] params = new String[3];
				params[0] = clss.getInstitution();
				params[1] = aPType.getInstitution();
				params[2] = errorMes;
				this.putFieldError("dataObject.institution", "institution.inconsistent", params);
				return false;
			}
			if(!ValidationUtils.wildCardMatch(aPType.getLocation(), clss.getLocation())) {
				String[] params = new String[3];
				params[0] = clss.getLocation();
				params[1] = aPType.getLocation();
				params[2] = errorMes;
				this.putFieldError("dataObject.location", "location.inconsistent", params);
				return false;
			}
		} 
		
		return true;
	}
	
	private boolean validatePercentTime(Classification clss) {
		if(CollectionUtils.isNotEmpty(clss.getDutyList())) {
			BigDecimal sum = BigDecimal.ZERO;
			for(ClassificationDuty aDuty : clss.getDutyList()) {
				if(aDuty != null && aDuty.getPercentage() != null) {
					sum = sum.add(aDuty.getPercentage());
				}
			}
			if(sum.compareTo(new BigDecimal(100)) > 0) {
				String[] parameters = new String[1];
				parameters[0] = sum.toString();
				
				this.putFieldError("dataObject.dutyList", "duty.percentage.exceedsMaximum", parameters);
				return false;
			}
		}		
		return true;
	}
	
}
