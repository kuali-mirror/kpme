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
package org.kuali.kpme.core.role.location;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.role.KPMERole;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class LocationRoleValuesFinder extends KeyValuesBase {

	private static final long serialVersionUID = -522208884831448939L;
	
	private static final List<KeyValue> LOCATION_ROLE_KEY_VALUES = new ArrayList<KeyValue>();
	
	static {
		LOCATION_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName(), KPMERole.TIME_LOCATION_ADMINISTRATOR.getRoleName()));
		LOCATION_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName(), KPMERole.TIME_LOCATION_VIEW_ONLY.getRoleName()));
		LOCATION_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName(), KPMERole.LEAVE_LOCATION_ADMINISTRATOR.getRoleName()));
		LOCATION_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName(), KPMERole.LEAVE_LOCATION_VIEW_ONLY.getRoleName()));
	}

	@Override
	public List<KeyValue> getKeyValues() {
		return LOCATION_ROLE_KEY_VALUES;
	}
	
}