/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.workarea.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class WorkAreaRoleValuesFinder extends KeyValuesBase {

	static final List<KeyValue> labels = new ArrayList<KeyValue>(TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA.size());
	static {
		for (String roleKey : TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA) {
			labels.add(new ConcreteKeyValue(roleKey, TkConstants.ALL_ROLES_MAP.get(roleKey)));
		}
	}

	@Override
	public List<KeyValue> getKeyValues() {
		return labels;
	}

}
