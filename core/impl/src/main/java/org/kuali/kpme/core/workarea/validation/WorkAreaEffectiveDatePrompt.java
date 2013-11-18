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
package org.kuali.kpme.core.workarea.validation;

import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.web.KPMEHrObjectNewerVersionPromptBase;
import org.kuali.kpme.core.workarea.WorkArea;

public class WorkAreaEffectiveDatePrompt extends KPMEHrObjectNewerVersionPromptBase {
	
    @Override
    protected boolean doesNewerVersionExist(HrBusinessObject pbo) {
    	boolean futureEffectiveDateExists = false;
    	
        WorkArea workArea = (WorkArea) pbo;
        WorkAreaContract lastWorkArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(workArea.getWorkArea(), TKUtils.END_OF_TIME);
        if (lastWorkArea != null && lastWorkArea.getEffectiveLocalDate() != null && workArea.getEffectiveLocalDate() != null) {
        	futureEffectiveDateExists = lastWorkArea.getEffectiveLocalDate().isAfter(workArea.getEffectiveLocalDate());
        }
        
        return futureEffectiveDateExists;
    }
    
}