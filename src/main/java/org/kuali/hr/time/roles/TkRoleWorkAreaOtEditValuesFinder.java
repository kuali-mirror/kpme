package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class TkRoleWorkAreaOtEditValuesFinder extends KeyValuesBase {

	static final List<KeyValue> labels = new ArrayList<KeyValue>(TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT.size());
	static {
		for (String roleKey : TkConstants.ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT) {
			labels.add(new ConcreteKeyValue(roleKey, TkConstants.ALL_ROLES_MAP.get(roleKey)));
		}
	}

	@Override
	public List<KeyValue> getKeyValues() {
		return labels;
	}
}
