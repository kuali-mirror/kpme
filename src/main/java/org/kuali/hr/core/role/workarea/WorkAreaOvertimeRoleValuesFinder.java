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
package org.kuali.hr.core.role.workarea;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.role.KPMERole;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class WorkAreaOvertimeRoleValuesFinder extends KeyValuesBase {

	private static final long serialVersionUID = -8292128951407749052L;
	
	private static final List<KeyValue> WORK_AREA_OVERTIME_ROLE_KEY_VALUES = new ArrayList<KeyValue>();
	
	static {
		WORK_AREA_OVERTIME_ROLE_KEY_VALUES.add(new ConcreteKeyValue("Employee", "Employee"));
		WORK_AREA_OVERTIME_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.APPROVER.getRoleName(), KPMERole.APPROVER.getRoleName()));
		WORK_AREA_OVERTIME_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.APPROVER_DELEGATE.getRoleName(), KPMERole.APPROVER_DELEGATE.getRoleName()));
		WORK_AREA_OVERTIME_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName(), KPMERole.TIME_DEPARTMENT_ADMINISTRATOR.getRoleName()));
		WORK_AREA_OVERTIME_ROLE_KEY_VALUES.add(new ConcreteKeyValue(KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName(), KPMERole.LEAVE_DEPARTMENT_ADMINISTRATOR.getRoleName()));
	}

	@Override
	public List<KeyValue> getKeyValues() {
		return WORK_AREA_OVERTIME_ROLE_KEY_VALUES;
	}
	
}