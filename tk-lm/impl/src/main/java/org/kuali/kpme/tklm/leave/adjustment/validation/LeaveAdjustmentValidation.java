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
package org.kuali.kpme.tklm.leave.adjustment.validation;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.leave.adjustment.LeaveAdjustment;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeaveAdjustmentValidation extends MaintenanceDocumentRuleBase{
	
	boolean validateLeavePlan(String leavePlan, LocalDate asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateLeavePlan(leavePlan, asOfDate)) {
			this.putFieldError("leavePlan", "error.existence", "leavePlan '"
					+ leavePlan + "'");
			valid = false;
		}
		return valid;
	}
	
	boolean validateEarnCode(String earnCode, String accrualCategory, LocalDate asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateEarnCodeOfAccrualCategory(earnCode, accrualCategory, asOfDate)) {
			this.putFieldError("earnCode", "error.earnCode.accrualCategory.mismatch", 
					earnCode);
			valid = false;
		}
		return valid;
	}

	boolean validatePrincipal(String principalId) {
		boolean valid = true;
		if(principalId != null && StringUtils.isNotEmpty(principalId)) {
			if(StringUtils.equals(principalId, GlobalVariables.getUserSession().getPrincipalId())){
				this.putFieldError("principalId", "error.cannot.modify.own.balance",
						"principalId '" + principalId + "'");
				valid = false;
			}
			if(valid){
				if (!ValidationUtils.validatePrincipalId(principalId)) {
					this.putFieldError("principalId", "error.existence",
							"principalId '" + principalId + "'");
					valid = false;
				}
			}
		}
		return valid;
	}

	boolean validateAccrualCategory(String accrualCategory, LocalDate asOfDate, String principalId) {
		boolean valid = true;
		if (!ValidationUtils.validateAccCategory(accrualCategory, principalId, asOfDate)) {
			this.putFieldError("accrualCategory", "error.existence", "accrualCategory '"
					+ accrualCategory + "'");
			valid = false;
		}
		return valid;
	}

    boolean validateDepartmentAdmin(String principalId) {

        boolean valid = false;
        String LoggedInPrincipalID = GlobalVariables.getUserSession().getPrincipalId();
        LocalDate loggedInDay =  LocalDate.now();
        DateTime asOfDate =  LocalDate.now().toDateTimeAtStartOfDay();

        if(principalId != null && StringUtils.isNotEmpty(principalId)) {
            List<Job> targetUserJob = HrServiceLocator.getJobService().getActiveLeaveJobs(principalId, loggedInDay);

            if(!targetUserJob.isEmpty()) {
            //the target user should have at least one job and not have more than one leave eligible dept
                  String targetUserDept = targetUserJob.get(0).getDept();
                  String targetUserLocation = targetUserJob.get(0).getLocation();
                  //check to see if the logged in user is the dept admin for the leave adjustment target user's dept
                  if(HrContext.isSystemAdmin() 
                		|| HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(LoggedInPrincipalID, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), targetUserDept, asOfDate)
                        || HrServiceLocator.getKPMERoleService().principalHasRoleInDepartment(LoggedInPrincipalID, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), targetUserDept, asOfDate)
                        || HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(LoggedInPrincipalID, KPMENamespace.KPME_TK.getNamespaceCode(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), targetUserLocation, asOfDate)
                        || HrServiceLocator.getKPMERoleService().principalHasRoleInLocation(LoggedInPrincipalID, KPMENamespace.KPME_LM.getNamespaceCode(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), targetUserLocation, asOfDate))
                  {
                      valid = true;
                  }
            }//List
        }
        if(!valid) {
        	GlobalVariables.getMessageMap().putError("document.newMaintainableObject.principalId", "principal.is.not.dept.admin");
        }
        return valid;
    }
	
	private boolean validateFraction(String earnCode, BigDecimal amount, LocalDate asOfDate) {
		boolean valid = true;
		if (!ValidationUtils.validateEarnCodeFraction(earnCode, amount, asOfDate)) {
			EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, asOfDate);
			if(ec != null && ec.getFractionalTimeAllowed() != null) {
				BigDecimal fracAllowed = new BigDecimal(ec.getFractionalTimeAllowed());
				String[] parameters = new String[2];
				parameters[0] = earnCode;
				parameters[1] = Integer.toString(fracAllowed.scale());
				this.putFieldError("adjustmentAmount", "error.amount.fraction", parameters);
				valid = false;
			 }
		}
		return valid;
	}
		
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;

		LOG.debug("entering custom validation for LeaveAdjustment");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();
		if (pbo instanceof LeaveAdjustment) {
			LeaveAdjustment leaveAdjustment = (LeaveAdjustment) pbo;

			if (leaveAdjustment != null) {
				valid = true;
				if(leaveAdjustment.getPrincipalId() != null) {
					valid &= this.validatePrincipal(leaveAdjustment.getPrincipalId());
                    valid &= this.validateDepartmentAdmin(leaveAdjustment.getPrincipalId());
				}
				if(leaveAdjustment.getAccrualCategory() != null) {
					valid &= this.validateAccrualCategory(leaveAdjustment.getAccrualCategory(),leaveAdjustment.getEffectiveLocalDate(), leaveAdjustment.getPrincipalId());
				}
				if(leaveAdjustment.getLeavePlan() != null) {
					valid &= this.validateLeavePlan(leaveAdjustment.getLeavePlan(), leaveAdjustment.getEffectiveLocalDate());
				}
				if(leaveAdjustment.getEarnCode() != null) {
					valid &= this.validateEarnCode(leaveAdjustment.getEarnCode(), leaveAdjustment.getAccrualCategory(), leaveAdjustment.getEffectiveLocalDate());
					if(leaveAdjustment.getAdjustmentAmount() != null) {
						valid &= this.validateFraction(leaveAdjustment.getEarnCode(), leaveAdjustment.getAdjustmentAmount(), leaveAdjustment.getEffectiveLocalDate());
					}
				}
			}
		}

		return valid;
	}

}
