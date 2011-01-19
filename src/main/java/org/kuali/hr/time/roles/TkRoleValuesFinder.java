package org.kuali.hr.time.roles;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TkRoleValuesFinder extends KeyValuesBase {

	static final List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>(TkConstants.ROLE_ASSIGNMENT_FOR_USER_ROLES.size());
	static {
		for (String roleKey : TkConstants.ROLE_ASSIGNMENT_FOR_USER_ROLES) {
			labels.add(new KeyLabelPair(roleKey, TkConstants.ALL_ROLES_MAP.get(roleKey)));
		}
	}

	@Override
	public List<KeyLabelPair> getKeyValues() {
		return labels;
	}
}
