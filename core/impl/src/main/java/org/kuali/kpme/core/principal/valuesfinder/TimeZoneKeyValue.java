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
package org.kuali.kpme.core.principal.valuesfinder;


import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.coreservice.api.CoreServiceApiServiceLocator;
import org.kuali.rice.coreservice.api.parameter.ParameterKey;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


public class TimeZoneKeyValue extends KeyValuesBase{
	@SuppressWarnings("unchecked")
	@Override
	public List getKeyValues(){
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        String timeZoneString = CoreServiceApiServiceLocator.getParameterRepositoryService().getParameterValueAsString(ParameterKey.create("KPME", "KPME-HR", "KeyValues", "TIME_ZONES"));
        String[] timeZoneArray = timeZoneString.split(";");
        for(String timeZone : timeZoneArray){
            keyValues.add(new ConcreteKeyValue(timeZone, timeZone));
        }
        return keyValues;
	}
}
