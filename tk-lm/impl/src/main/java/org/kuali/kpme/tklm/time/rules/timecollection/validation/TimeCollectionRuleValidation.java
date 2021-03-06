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
package org.kuali.kpme.tklm.time.rules.timecollection.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.kpme.tklm.time.rules.validation.TkKeyedBusinessObjectValidation;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeCollectionRuleValidation extends TkKeyedBusinessObjectValidation {

	boolean validateWorkArea(TimeCollectionRule ruleObj) {
		if (!ValidationUtils.validateWorkArea(ruleObj.getWorkArea(), ruleObj.getDept(), ruleObj.getEffectiveLocalDate())) {
			this.putFieldError("workArea", "error.existence", "workarea '" + ruleObj.getWorkArea() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateDepartment(TimeCollectionRule ruleObj) {
		if (!ValidationUtils.validateDepartment(ruleObj.getDept(), ruleObj.getGroupKeyCode(), ruleObj.getEffectiveLocalDate())) {
			this.putFieldError("dept", "error.existence", "department '" + ruleObj.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	// JIRA1152
	boolean validatePayType(TimeCollectionRule ruleObj) {
		if(!StringUtils.isEmpty(ruleObj.getPayType()) && ruleObj.getPayType().equals(HrConstants.WILDCARD_CHARACTER)) {
			return true;
		}		
		if (!ValidationUtils.validatePayType(ruleObj.getPayType(), ruleObj.getEffectiveLocalDate())) {
			this.putFieldError("payType", "error.existence", "payType '" + ruleObj.getPayType() + "'");
			return false;
		} else {
			return true;
		}
	}

	public static boolean validateWorkAreaDeptWildcarding(TimeCollectionRule tcr) {
        return StringUtils.equals(tcr.getDept(), HrConstants.WILDCARD_CHARACTER)
                && HrConstants.WILDCARD_LONG.equals(tcr.getWorkArea());
    }
	
	boolean validateWildcards(TimeCollectionRule tcr) {

        if(validateWorkAreaDeptWildcarding(tcr)){
			// add error when work area defined, department is wild carded.
			this.putFieldError("workArea", "error.wc.wadef");
			return false;
		}
        
        if(validateGroupKeyDeptWildcarding(tcr)){
        	// add error when dept is defined, groupkey is wild carded.
			this.putFieldError("groupKeyCode", "error.wc.deptdef");
			return false;
		}
        
		return true;
    }
	
	private boolean validateGroupKeyDeptWildcarding(TimeCollectionRule tcr) {
        return StringUtils.equals(tcr.getGroupKeyCode(), HrConstants.WILDCARD_CHARACTER)
                && StringUtils.equals(tcr.getDept(), HrConstants.WILDCARD_CHARACTER);
    }

	/**
	 * It looks like the method that calls this class doesn't actually care
	 * about the return type.
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for TimeCollectionRule");
		PersistableBusinessObject pbo = (PersistableBusinessObject) this.getNewBo();



		if (pbo instanceof TimeCollectionRule) {
			TimeCollectionRule timeCollectionRule = (TimeCollectionRule) pbo;
			timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession().getLoggedInUserPrincipalName());
			if (timeCollectionRule != null) {
				valid = true;
				valid &= this.validateWildcards(timeCollectionRule);
				valid &= this.validateDepartment(timeCollectionRule);
				valid &= this.validateWorkArea(timeCollectionRule);
				valid &= this.validatePayType(timeCollectionRule);
				valid &= this.validateGroupKeyCode(timeCollectionRule);
			}
		}

		return valid;
	}

}
