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
package org.kuali.kpme.core.bo.paygrade.validation;

import org.kuali.kpme.core.bo.paygrade.PayGrade;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.web.KpmeEffectiveDatePromptBase;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class PayGradeEffectiveDatePrompt extends KpmeEffectiveDatePromptBase {
    
	@Override
    protected boolean futureEffectiveDateExists(PersistableBusinessObject pbo) {
    	boolean futureEffectiveDateExists = false;
    	
        PayGrade payGrade = (PayGrade) pbo;
        PayGrade lastPayGrade = HrServiceLocator.getPayGradeService().getPayGrade(payGrade.getPayGrade(), payGrade.getSalGroup(), TKUtils.END_OF_TIME);
        if (lastPayGrade != null && lastPayGrade.getEffectiveLocalDate() != null && payGrade.getEffectiveLocalDate() != null) {
        	futureEffectiveDateExists = lastPayGrade.getEffectiveLocalDate().isAfter(payGrade.getEffectiveLocalDate());
        }
        
        return futureEffectiveDateExists;
    }
	
}