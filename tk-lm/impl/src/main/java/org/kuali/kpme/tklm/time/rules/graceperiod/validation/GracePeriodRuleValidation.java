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
package org.kuali.kpme.tklm.time.rules.graceperiod.validation;

import java.math.BigDecimal;

import org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class GracePeriodRuleValidation extends MaintenanceDocumentRuleBase{
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document){
			GracePeriodRule gracePeriodRule = (GracePeriodRule)this.getNewBo();
			//Confirm that hour factor is greater than 0 and less than 1
			if(gracePeriodRule.getHourFactor().compareTo(BigDecimal.ZERO) <= 0 ||
			   gracePeriodRule.getHourFactor().compareTo(new BigDecimal(60)) > 0){
				this.putFieldError("hourFactor", "graceperiod.hour.factor.invalid", gracePeriodRule.getHourFactor()+"");
				return false;
			}
			return true;
	}
}
