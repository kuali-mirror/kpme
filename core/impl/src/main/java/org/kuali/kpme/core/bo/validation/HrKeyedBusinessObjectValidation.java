package org.kuali.kpme.core.bo.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public abstract class HrKeyedBusinessObjectValidation extends MaintenanceDocumentRuleBase {
	
	private static final String GROUP_KEY_CODE_PARAM_SUFFIX = "'";
	private static final String GROUP_KEY_CODE_PARAM_PREFIX = "Group Key Code '";
	private static final String ERROR_EXISTENCE = "error.existence";
	private static final String GROUP_KEY_CODE_PROP_NAME = "dataObject.groupKeyCode";

	protected boolean validateGroupKeyCode(HrKeyedBusinessObject keyedBo) {
		if (StringUtils.isNotEmpty(keyedBo.getGroupKeyCode())) {
			if(!ValidationUtils.validateGroupKey(keyedBo.getGroupKeyCode(), keyedBo.getEffectiveLocalDate())){
				this.putFieldError(GROUP_KEY_CODE_PROP_NAME, ERROR_EXISTENCE, GROUP_KEY_CODE_PARAM_PREFIX + keyedBo.getGroupKeyCode() + GROUP_KEY_CODE_PARAM_SUFFIX);
				return false;
			}
		}
		return true;
	}

}
