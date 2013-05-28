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
package org.kuali.kpme.core.location.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class TimezoneKeyValueFinder extends KeyValuesBase{

	@Override
	public List getKeyValues() {
		List<KeyValue> timezones = new ArrayList<KeyValue>();
		for(String timeZone : HrConstants.TIME_ZONES){
			timezones.add(new ConcreteKeyValue(timeZone,timeZone));
		}
		return timezones;
	}

}
