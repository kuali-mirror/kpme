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
package org.kuali.kpme.tklm.time.rules.clocklocation.validation;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.web.KPMEHrObjectNewerVersionPromptBase;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;

public class ClockLocationRuleEffectiveDatePrompt extends KPMEHrObjectNewerVersionPromptBase {
    
	@Override
    protected boolean doesNewerVersionExist(HrBusinessObject pbo) {
		boolean futureEffectiveDateExists = false;
        ClockLocationRule clr = (ClockLocationRule) pbo;
        if(clr.getEffectiveLocalDate() != null) {
	        List<ClockLocationRule> lastClr = TkServiceLocator.getClockLocationRuleService().getNewerVersionClockLocationRule(clr.getDept(), clr.getWorkArea(), clr.getPrincipalId(), clr.getJobNumber(), clr.getEffectiveLocalDate());
	   		futureEffectiveDateExists = CollectionUtils.isNotEmpty(lastClr);
        }
   		return futureEffectiveDateExists;
    }
	
}
