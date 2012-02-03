package org.kuali.hr.time.collection.rule.validation;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.util.GlobalVariables;

public class TimeCollectionRuleRule extends MaintenanceDocumentRuleBase {

	boolean validateWorkArea(TimeCollectionRule ruleObj) {
		if (!ValidationUtils.validateWorkArea(ruleObj.getWorkArea(), ruleObj.getDept(), ruleObj.getEffectiveDate())) {
			this.putFieldError("workArea", "error.existence", "workarea '" + ruleObj.getWorkArea() + "'");
			return false;
		} else {
			return true;
		}
	}

	boolean validateDepartment(TimeCollectionRule ruleObj) {
		if (!ValidationUtils.validateDepartment(ruleObj.getDept(), ruleObj.getEffectiveDate())) {
			this.putFieldError("dept", "error.existence", "department '" + ruleObj.getDept() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	// JIRA1152
	boolean validatePayType(TimeCollectionRule ruleObj) {
		if(!StringUtils.isEmpty(ruleObj.getPayType()) && ruleObj.getPayType().equals(TkConstants.WILDCARD_CHARACTER)) {
			return true;
		}		
		if (!ValidationUtils.validatePayType(ruleObj.getPayType(), ruleObj.getEffectiveDate())) {
			this.putFieldError("payType", "error.existence", "payType '" + ruleObj.getPayType() + "'");
			return false;
		} else {
			return true;
		}
	}
	
	boolean validateClockUserAndHrsDistFlags(TimeCollectionRule ruleObj) {
		if (!ruleObj.isClockUserFl() && ruleObj.isHrsDistributionF()) {
			this.putFieldError("hrsDistributionF", "timecollRule.hrDistribution.invalid");
			return false;
		}
		return true;
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
		PersistableBusinessObject pbo = this.getNewBo();



		if (pbo instanceof TimeCollectionRule) {
			TimeCollectionRule timeCollectionRule = (TimeCollectionRule) pbo;
			timeCollectionRule.setUserPrincipalId(GlobalVariables.getUserSession().getLoggedInUserPrincipalName());
			if (timeCollectionRule != null) {
				valid = true;
				valid &= this.validateDepartment(timeCollectionRule);
				valid &= this.validateWorkArea(timeCollectionRule);
				valid &= this.validatePayType(timeCollectionRule);
				valid &= this.validateClockUserAndHrsDistFlags(timeCollectionRule);
			}
		}

		return valid;
	}

}