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
package org.kuali.kpme.pm.position.validation;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.kpme.pm.position.PositionDutyBo;
import org.kuali.kpme.pm.position.funding.PositionFundingBo;
import org.kuali.kpme.pm.positiondepartment.PositionDepartmentBo;
import org.kuali.kpme.core.departmentaffiliation.DepartmentAffiliation;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

public class PositionValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position");
		PositionBo aPosition = (PositionBo) this.getNewDataObject();
        PositionBo oldPosition = (PositionBo) this.getOldDataObject();

		if (aPosition != null) {
			valid = true;
			valid &= this.validateOverviewPage(aPosition);
			valid &= this.validateClassificationPage(aPosition);
			valid &= this.validateDutyListPercentage(aPosition);
			valid &= this.validatePrimaryDepartment(aPosition);
            valid &= this.validateProcess(aPosition, oldPosition);
            valid &= this.validateFundingLines(aPosition);
		}
		return valid;
	}

	private boolean validateDutyListPercentage(PositionBo aPosition) {
		if (CollectionUtils.isNotEmpty(aPosition.getDutyList())) {
			BigDecimal sum = BigDecimal.ZERO;
			for (PositionDutyBo aDuty : aPosition.getDutyList()) {
				if (aDuty != null && aDuty.getPercentage() != null) {
					sum = sum.add(aDuty.getPercentage());
				}
			}
			if (sum.compareTo(new BigDecimal(100)) > 0) {
				String[] parameters = new String[1];
				parameters[0] = sum.toString();
				this.putFieldError("dataObject.dutyList",
						"duty.percentage.exceedsMaximum", parameters);
				return false;
			}
		}
		return true;
	}

	// KPME-3016  
	// Now each section is its own page that if you want to show errors globally, you have to catch them globally
	private boolean validateOverviewPage(PositionBo aPosition) {

		// required fields
		if (aPosition.getEffectiveDate() == null
                || StringUtils.isEmpty(aPosition.getInstitution())
                || StringUtils.isEmpty(aPosition.getLocation())
                || StringUtils.isEmpty(aPosition.getPrimaryDepartment())
                || StringUtils.isEmpty(aPosition.getPositionClass())
                || StringUtils.isEmpty(aPosition.getDescription())
				|| StringUtils.isEmpty(aPosition.getPositionStatus())
				|| StringUtils.isEmpty(aPosition.getAppointmentType())
				|| StringUtils.isEmpty(aPosition.getTemporary())
				|| StringUtils.isEmpty(aPosition.getContract())) {

			this.putGlobalError("error.overview.fields.required");
			return false;
		}

		// validate appointment type
		if (!StringUtils.isEmpty(aPosition.getAppointmentType())) {
			List <PositionDepartmentBo> depts = aPosition.getDepartmentList();
			if (depts != null && depts.size() > 0) {
				boolean found = false;
				for (PositionDepartmentBo aPos : depts) {
					if (PmValidationUtils.validatePositionAppointmentType(aPosition.getAppointmentType(), aPos.getInstitution(), aPos.getLocation(), aPosition.getEffectiveLocalDate())) {
						found = true;
						break;
					}
				}
				if (!found) {
					this.putFieldError("dataObject.appointmentType", "error.existence", "Appointment Type '" + aPosition.getAppointmentType() + "'");
					return false;						
				}
			}
		}
		
		// validate contract and contrqact type
		if (StringUtils.equals(aPosition.getContract(), "Y")) {
			if (StringUtils.isEmpty(aPosition.getContractType())) {
				this.putFieldError("dataObject.contractType", "error.overview.fields.required");
				return false;
			} else {
				if (!PmValidationUtils.validatePositionContractType(aPosition.getContractType(), aPosition.getInstitution(), aPosition.getLocation(), aPosition.getEffectiveLocalDate())) {
					this.putFieldError("dataObject.contractType", "error.existence", "Contract Type '" + aPosition.getContractType() + "'");
					return false;
				}
			}
		}
		
		// validate renewal eligible
		if (aPosition.getExpectedEndDate() != null) {
			if (StringUtils.isEmpty(aPosition.getRenewEligible())) {
				this.putFieldError("dataObject.renewEligible", "error.overview.fields.required");
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validateClassificationPage(PositionBo aPosition) {

		if (StringUtils.isEmpty(aPosition.getPmPositionClassId())
				|| StringUtils.isEmpty(aPosition.getTenureEligible())
				|| StringUtils.isEmpty(aPosition.getBenefitsEligible())
				|| StringUtils.isEmpty(aPosition.getLeaveEligible())) {

			this.putFieldError("dataObject.pmPositionClassId", "error.classication.fields.required");
			return false;
		}
		// validate leave plan
		if (StringUtils.equals(aPosition.getLeaveEligible(), "Y")) {
			if (StringUtils.isEmpty(aPosition.getLeavePlan())) {
				this.putFieldError("dataObject.leavePlan", "error.classication.fields.required");
				return false;
			} 
		}
		
		return true;
	}
	
	private boolean validatePrimaryDepartment(PositionBo aPosition) {

		if (CollectionUtils.isNotEmpty(aPosition.getDepartmentList())) {
			for (PositionDepartmentBo aDepartment : aPosition.getDepartmentList()) {
				if(aDepartment != null && aDepartment.getDeptAfflObj() != null) {
					DepartmentAffiliation pda = (DepartmentAffiliation)aDepartment.getDeptAfflObj();
					if (pda.isPrimaryIndicator()) {
						return true;
					}
				}
			}
		}

		this.putFieldError("dataObject.primaryDepartment", "error.primaryDepartment.required");
		return false;
	}

    private boolean validateProcess(PositionBo newPosition, PositionBo oldPosition) {
        String process = newPosition.getProcess();
            if (StringUtils.equals(process, PMConstants.PSTN_PROCESS_REORG)) {

                if (StringUtils.equals(newPosition.getPrimaryDepartment(),oldPosition.getPrimaryDepartment())
                        && StringUtils.equals(newPosition.getReportsToPositionId(),oldPosition.getReportsToPositionId())) {
                    this.putFieldError("dataObject.primaryDepartment","error.reorganization.noChange");
                    return false;
                }

            } else if (StringUtils.equals(process,PMConstants.PSTN_PROCESS_RECLASS)) {
                if(StringUtils.equals(newPosition.getPmPositionClassId(),oldPosition.getPmPositionClassId())) {
                    this.putFieldError("dataObject.positionClass","error.reclassification.noChange");
                    return false;
                }
            } else if (StringUtils.equals(process,PMConstants.PSTN_PROCESS_STATUS)) {
                if(StringUtils.equals(newPosition.getPositionStatus(),oldPosition.getPositionStatus())) {
                    this.putFieldError("dataObject.positionStatus","error.changeStatus.noChange");
                }
            }

        return true;
    }

    protected boolean validateFundingLines(PositionBo aPosition) {
    	boolean valid = true;
    	String prefix = "fundingList";
    	if(CollectionUtils.isNotEmpty(aPosition.getFundingList())) {
	    	for (ListIterator<? extends PositionFundingBo> iterator = aPosition.getFundingList().listIterator(); iterator.hasNext(); ) {
				int index = iterator.nextIndex();
				PositionFundingBo pf = iterator.next();
				valid &= validateAddFundingLine(pf, aPosition, prefix, index);
			}
    	}
    	return valid;
    }
    
	protected boolean validateAddFundingLine(PositionFundingBo pf, PositionBo aPosition,String prefix, int index) {
		boolean valid = true;
		String propertyNamePrefix = prefix + "[" + index + "].";
    	if(StringUtils.isNotEmpty(pf.getAccount())) {
    		boolean results = ValidationUtils.validateAccount(pf.getChart(), pf.getAccount());
    		if(!results) {
    			this.putFieldError(propertyNamePrefix + "account","error.existence", "Account '" + pf.getAccount() + "'");
    			valid = false;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubAccount())) {
    		boolean results = ValidationUtils.validateSubAccount(pf.getSubAccount(), pf.getAccount(), pf.getChart());
    		if(!results) {
	   			 this.putFieldError(propertyNamePrefix + "subAccount","error.existence", "Sub Account '" + pf.getSubAccount() + "'");
	   			 valid = false;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getObjectCode()) && aPosition.getEffectiveDate() != null) {
    		boolean results = ValidationUtils.validateObjectCode(pf.getObjectCode(), pf.getChart(), Integer.valueOf(aPosition.getEffectiveLocalDate().getYear()));
    		if(!results) {
    			 this.putFieldError(propertyNamePrefix + "objectCode","error.existence", "Object Code '" + pf.getObjectCode() + "'");
    			 valid = false;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubObjectCode())) {
    		boolean results = ValidationUtils.validateSubObjectCode(String.valueOf(aPosition.getEffectiveLocalDate().getYear()),
    				pf.getChart(),
    				pf.getAccount(),
    				pf.getObjectCode(),
    				pf.getSubObjectCode());
    		if(!results) {
    			 this.putFieldError(propertyNamePrefix + "subObjectCode","error.existence", "Sub Object Code '" + pf.getSubObjectCode() + "'");
    			 valid= false;
    		}
    	}
    	return valid;
    
	}


}
