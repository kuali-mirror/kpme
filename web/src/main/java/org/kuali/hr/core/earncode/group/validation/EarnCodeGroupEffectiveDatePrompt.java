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
package org.kuali.hr.core.earncode.group.validation;

import org.kuali.hr.core.KpmeEffectiveDatePromptBase;
import org.kuali.hr.core.earncode.group.EarnCodeGroup;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class EarnCodeGroupEffectiveDatePrompt extends KpmeEffectiveDatePromptBase {
    
	@Override
    protected boolean futureEffectiveDateExists(PersistableBusinessObject pbo) {
    	boolean futureEffectiveDateExists = false;
    	
        EarnCodeGroup earnCodeGroup = (EarnCodeGroup) pbo;
        EarnCodeGroup lastEarnCodeGroup = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup(earnCodeGroup.getEarnCodeGroup(), TKUtils.END_OF_TIME);
        if (lastEarnCodeGroup != null && lastEarnCodeGroup.getEffectiveLocalDate() != null && earnCodeGroup.getEffectiveLocalDate() != null) {
        	futureEffectiveDateExists = lastEarnCodeGroup.getEffectiveLocalDate().isAfter(earnCodeGroup.getEffectiveLocalDate());
        }
        
        return futureEffectiveDateExists;
    }
	
}