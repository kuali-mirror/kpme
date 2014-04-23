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
package org.kuali.kpme.pm.classification.validation;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.core.bo.validation.HrKeyedBusinessObjectValidation;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.api.positionreportgroup.PositionReportGroupContract;
import org.kuali.kpme.pm.api.positiontype.PositionTypeContract;
import org.kuali.kpme.pm.classification.ClassificationBo;
import org.kuali.kpme.pm.classification.duty.ClassificationDutyBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class ClassificationValidation extends HrKeyedBusinessObjectValidation {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position Classification");
		ClassificationBo clss = (ClassificationBo) this.getNewDataObject();
		
		if (clss != null) {
			valid = true;
			valid &= this.validateSalGroup(clss);
			valid &= this.validateLeavePlan(clss);
			valid &= this.validateReportingGroup(clss);
			valid &= this.validatePositionType(clss);
			valid &= this.validatePercentTime(clss);
            valid &= this.validatePayGrade(clss);
            valid &= this.validateGroupKeyCode(clss);
		}
		return valid;
	}
	
	private boolean validateLeavePlan(ClassificationBo clss) {
		if (StringUtils.isNotEmpty(clss.getLeavePlan())
				&& !ValidationUtils.validateLeavePlan(clss.getLeavePlan(), clss.getEffectiveLocalDate())) {
			this.putFieldError("dataObject.leavePlan", "error.existence", "Leave Plan '"
					+ clss.getLeavePlan() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	private boolean validateSalGroup(ClassificationBo clss) {
		SalaryGroup aSalGroup = HrServiceLocator.getSalaryGroupService().getSalaryGroup(clss.getSalaryGroup(), clss.getEffectiveLocalDate());
		String errorMes = "SalaryGroup '" + clss.getSalaryGroup() + "'";
		if(aSalGroup != null) {
			if (!aSalGroup.getGroupKeyCode().equals(clss.getGroupKeyCode())) {
				String[] params = new String[3];
				params[0] = clss.getGroupKeyCode();
				params[1] = aSalGroup.getGroupKeyCode();
				params[2] = errorMes;
				this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
				return false;
			}
		} else {
			this.putFieldError("dataObject.salaryGroup", "error.existence", errorMes);
			return false;
		}
		
		return true;
	}
	
	private boolean validateReportingGroup(ClassificationBo clss) {
		if(StringUtils.isNotBlank(clss.getPositionReportGroup())) {
			PositionReportGroupContract aPrg = PmServiceLocator.getPositionReportGroupService().getPositionReportGroup(clss.getPositionReportGroup(), clss.getEffectiveLocalDate());
			String errorMes = "PositionReportGroup '" + clss.getPositionReportGroup() + "'";
			if(aPrg == null) {
				this.putFieldError("dataObject.positionReportGroup", "error.existence", errorMes);
				return false;
			} else {
				if (!aPrg.getGroupKeyCode().equals(clss.getGroupKeyCode())) {
					String[] params = new String[3];
					params[0] = clss.getGroupKeyCode();
					params[1] = aPrg.getGroupKeyCode();
					params[2] = errorMes;
					this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean validatePositionType(ClassificationBo clss) {
		PositionTypeContract aPType = PmServiceLocator.getPositionTypeService().getPositionType(clss.getPositionType(),  clss.getEffectiveLocalDate());
		String errorMes = "PositionType '" + clss.getPositionType() + "'";
		if(aPType == null) {
			this.putFieldError("dataObject.positionType", "error.existence", errorMes);
			return false;
		} else {
			if (!aPType.getGroupKeyCode().equals(clss.getGroupKeyCode())) {
				String[] params = new String[3];
				params[0] = clss.getGroupKeyCode();
				params[1] = aPType.getGroupKeyCode();
				params[2] = errorMes;
				this.putFieldError("dataObject.groupKeyCode", "groupKeyCode.inconsistent", params);
				return false;
			}
		} 
		
		return true;
	}
	
	private boolean validatePercentTime(ClassificationBo clss) {
		if(CollectionUtils.isNotEmpty(clss.getDutyList())) {
			BigDecimal sum = BigDecimal.ZERO;
			for(ClassificationDutyBo aDuty : clss.getDutyList()) {
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

    private boolean validatePayGrade(ClassificationBo clss) {
        if (StringUtils.isNotEmpty(clss.getPayGrade()) && !ValidationUtils.validatePayGrade(clss.getPayGrade(), clss.getSalaryGroup(), clss.getEffectiveLocalDate())) {
            String[] params = new String[2];
            params[0] = clss.getPayGrade();
            params[1] = clss.getSalaryGroup();
            this.putFieldError("dataObject.payGrade", "salaryGroup.contains.payGrade", params);
            return false;
        } else {
            return true;
        }
    }
	
}
