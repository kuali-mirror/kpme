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
package org.kuali.hr.location.validation;

import org.kuali.hr.core.document.question.KpmeEffectiveDatePromptBase;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class LocationEffectiveDatePrompt extends KpmeEffectiveDatePromptBase {
    
	@Override
    protected boolean futureEffectiveDateExists(PersistableBusinessObject pbo) {
    	boolean futureEffectiveDateExists = false;
    	
        Location location = (Location) pbo;
        Location lastLocation = TkServiceLocator.getLocationService().getLocation(location.getLocation(), TKUtils.END_OF_TIME);
        if (lastLocation != null && lastLocation.getEffectiveLocalDate() != null && location.getEffectiveLocalDate() != null) {
        	futureEffectiveDateExists = lastLocation.getEffectiveLocalDate().isAfter(location.getEffectiveLocalDate());
        }
        
        return futureEffectiveDateExists;
    }
	
}