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
package org.kuali.hr.kpme.tklm.time.rules.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public abstract class TkKeyedBusinessObjectValidation extends MaintenanceDocumentRuleBase {
	
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
